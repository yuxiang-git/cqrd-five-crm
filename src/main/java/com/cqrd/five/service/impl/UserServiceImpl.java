package com.cqrd.five.service.impl;

import com.cqrd.five.dto.Result;
import com.cqrd.five.dto.User;
import com.cqrd.five.ex.CustomException;
import com.cqrd.five.ex.ResultStatusEnum;
import com.cqrd.five.mapper.UserMapper;
import com.cqrd.five.service.UserService;
import com.cqrd.five.util.JedisPoolUtils;
import com.cqrd.five.util.MD5Util;
import com.google.gson.Gson;
import jdk.nashorn.internal.runtime.Context;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;
    Jedis jedis = JedisPoolUtils.getJedis();

    public UserServiceImpl(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }


    /**
     * 注册用户检查
     * @return User
     */
    @Override
    public User checkRegist(String userName) {
       return userMapper.findUserByName(userName);
    }
    /**
     * 注册
     * @param user 参数封装
     * @return Result
     */
    @Override
    public Boolean regist(User user){
       return userMapper.regist(user) == 1;
    }
    /**
     * 登录
     * @param user 用户名和密码
     * @return Result
     */
    public User login(User user) {
        return userMapper.login(user);
    }

    /**
     * 查询所有用户信息
     * @return 用户信息
     */
    @Override
    public List<User> deriveByAll() {
        return userMapper.findByAll();
    }

    /**
     * 导出用户勾选的用户
     * @param username
     * @return
     */
    @Override
    public List<User> findUserByName(String username) {
        return userService.findUserByName(username);
    }

    /**
     * 导出勾选的用户
     * @param username
     * @return
     */
    @Override
    public List<User> findUserByExportChecked(String[] username) {
        return userMapper.findUserByExportChecked(username);
    }

    @Override
    public User findByName(String username) {
        return userService.findByName(username);
    }

    Result result = new Result();
    //发送验证码到邮箱
    @Autowired
    private JavaMailSender mailSender;
    private StringRedisTemplate stringRedisTemplate;
    @Value("${spring.mail.username}")
    private String mailUserName;
    @Override
    public Result sendCode(String username, String email) throws MessagingException {

        //校验username是否存在
        int i = userMapper.checkUsername(username);
        if(i != 1){
            result.setMsg("用户名错误");
            result.setSuccess(false);
            return result;
        }
        if(i != 1){
            //抛出用户数据不存在异常
            throw new CustomException(ResultStatusEnum.UserNotFoundException);
        }
        //校验email是否存在
        int i1 = userMapper.checkEmail(email);
        if(i != 1){
            result.setMsg("邮箱错误");
            result.setSuccess(false);
            return result;
        }
        if(i != 1){
            //抛出用户数据不存在异常
            throw new CustomException(ResultStatusEnum.UserNotFoundException);
        }
        //通过email查询用户名是否匹配
        String username1 = userMapper.selectUserNameByEmail(email);
        if(!username.equals(username1)) {
            result.setMsg("用户名和邮箱不匹配");
            result.setSuccess(false);
            return result;
        }
        if(!username.equals(username1)) {
            //抛出用户名和邮箱不匹配的异常
            throw new CustomException(ResultStatusEnum.UserNameAndEmailUnmatched);
        }
        //把邮箱发送次数计数redisKey前缀和邮箱是否被锁定一小时redisKey前缀放进组成放进缓存中
        Gson gson = new Gson();
        String SHIRO_LOGIN_COUNT1= gson.toJson("shiro_login_count_");
        String SHIRO_IS_LOCK1 = gson.toJson("shiro_is_lock_");
        String email1 = gson.toJson(email);
        jedis.set("SHIRO_LOGIN_COUNT1",SHIRO_LOGIN_COUNT1);
        jedis.set("SHIRO_IS_LOCK1",SHIRO_IS_LOCK1);
        jedis.set("email1",email1);
        String SHIRO_LOGIN_COUNT = jedis.get("SHIRO_LOGIN_COUNT1");
        String SHIRO_IS_LOCK = jedis.get("SHIRO_IS_LOCK1");
        String email2 = jedis.get("email1");
        //访问一次，计数一次
        ValueOperations<String, String> opsForValue = stringRedisTemplate.opsForValue();
        if ("LOCK".equals(opsForValue.get(SHIRO_IS_LOCK+email2))){
            result.setSuccess(false);
            result.setMsg("用户名或密码错误次数大于3次,账户已锁定");
            return result;
        }
        opsForValue.increment(SHIRO_LOGIN_COUNT+email2, 1);
        //计数大于3时，设置用户被锁定一小时
        if(Integer.parseInt(opsForValue.get(SHIRO_LOGIN_COUNT+email2))>=3){
            opsForValue.set(SHIRO_IS_LOCK+email2, "LOCK");
            stringRedisTemplate.expire(SHIRO_IS_LOCK+email2, 1, TimeUnit.MINUTES);
        }

        //更新数据库验证码
        String code = String.valueOf(new Random().nextInt(899999) + 100000);//生成验证码
        String md5code = MD5Util.MD5EncodeUtf8(code);
        //把验证码保存在缓存中
        String md5codeJson= gson.toJson(md5code);
        jedis.set("md5codeJson", md5codeJson);
        //把验证码时间保存在缓存中
        Date outdate = new Date(System.currentTimeMillis() + 3 * 60 * 1000);//3分钟后过期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String outdatemd5codeJson = sdf.format(outdate);
        jedis.set("outdatemd5codeJson", outdatemd5codeJson);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<html><head><title></title></head><body>");
        stringBuilder.append("您好<br/>");
        stringBuilder.append("您的验证码是：").append(code).append("<br/>");
        stringBuilder.append("此验证码只能输入一次，在3分钟内有效,请谨慎操作。验证成功则自动失效。<br/>");
        stringBuilder.append("如果未进行以上操作，请忽略此邮件");
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        //发送验证码到邮箱
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "utf-8");
        mimeMessageHelper.setFrom(mailUserName);//这里只是设置username 并没有设置host和password，因为host和password在springboot启动创建JavaMailSender实例的时候已经读取了
        mimeMessageHelper.setTo(email);
        mimeMessage.setSubject("邮箱验证-找回密码");
        mimeMessageHelper.setText(stringBuilder.toString(), true);
        mailSender.send(mimeMessage);
        result.setSuccess(true);
        result.setMsg("邮箱发送成功");
        return result;
    }

    @Override
    public Result resetPassword(String username, String emailcode, String newpassword) {
        //校验username是否存在
        int i = userMapper.checkUsername(username);
        if(i != 1){
            result.setMsg("用户名错误");
            result.setSuccess(false);
            return result;
        }
        if(i != 1){
            //抛出用户数据不存在异常
            throw new CustomException(ResultStatusEnum.UserNotFoundException);
        }
        //判断验证码是否还有效
        String outdate = jedis.get("outdatemd5codeJson");
        Date newdate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String stringnewdate = sdf.format(newdate);

        if(stringnewdate.compareTo(outdate) > 0){
            result.setMsg("验证码过期");
            result.setSuccess(false);
            return result;
        }
        if(stringnewdate.compareTo(outdate) > 0){
            //抛出验证码过期异常
            throw new CustomException(ResultStatusEnum.Code_Stale_Dated);
        }
        //查询验证码，判断验证码是否正确
        String codelistJson = jedis.get("md5codeJson");
        String code = codelistJson.substring(1, codelistJson.length()-1);
        String md5emailcode = MD5Util.MD5EncodeUtf8(emailcode);
        if(!md5emailcode.equals(code)){
            //清空验证码
                String codenull = "null";
                jedis.set("md5codeJson",  codenull);
                result.setMsg("验证码不正确,请返回重新发邮件");
                result.setSuccess(false);
                return result;

        }if(!md5emailcode.equals(code)){
            //抛出出验证码不正确异常
            throw  new CustomException(ResultStatusEnum.Code_Untrue);
        }
        //更新用户密码
        String md5newpassword = MD5Util.getMD5(newpassword);
        boolean up = userMapper.updatePassword(username, md5newpassword);
        if(!up){
            result.setMsg("密码更新失败！");
            result.setSuccess(false);
            return result;
        }if(!up){
            //更新数据库数据失败
            throw new CustomException(ResultStatusEnum.Not_Update_Database);
        }
        //更新验证码时间
        Date newdate1 = new Date();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String stringnewdate1 = sdf1.format(newdate1);
        jedis.set("outdatemd5codeJson", stringnewdate1);

        //清空登录计数
        ValueOperations<String, String> opsForValue = stringRedisTemplate.opsForValue();
        String SHIRO_LOGIN_COUNT = jedis.get("SHIRO_LOGIN_COUNT1");
        String SHIRO_IS_LOCK = jedis.get("SHIRO_IS_LOCK1");
        String email = jedis.get("email1");
        opsForValue.set(SHIRO_LOGIN_COUNT + email, "0");
        //设置未锁定状态
        opsForValue.set(SHIRO_IS_LOCK + email, "UNLOCK");

        result.setSuccess(true);
        result.setMsg("密码修改成功");
        return result;
    }


    /**
     * 导入
     * @param fileName
     * @param file
     * @return
     * @throws Exception
     */
    @Transactional(readOnly = false,rollbackFor = Exception.class)
    @Override
    public boolean batchImport(String fileName, MultipartFile file) {
        boolean notNull = false;
        try {
        List<User> userList = new ArrayList<>();
            if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
                //抛出文件格式不正确的异常
                throw new CustomException(ResultStatusEnum.Uploaded_File_Incorrect);
            }
        boolean isExcel2003 = true;
        if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
            isExcel2003 = false;
        }
        InputStream is = file.getInputStream();
        Workbook wb = null;
        if (isExcel2003) {
            wb = new HSSFWorkbook(is);
        } else {
            wb = new XSSFWorkbook(is);
        }
        Sheet sheet = wb.getSheetAt(0);
        if(sheet!=null){
            notNull = true;
        }
        User user;
            //r = 2 表示从第三行开始循环 如果你的第三行开始是数据
        for (int r = 2; r <= sheet.getLastRowNum(); r++) {
            //通过sheet表单对象得到 行对象
            Row row = sheet.getRow(r);
                if (row == null){
                    continue;
                }
            //sheet.getLastRowNum() 的值是 10，所以Excel表中的数据至少是10条；不然报错 NullPointerException

            user = new User();

            row.getCell(0).setCellType(CellType.STRING);
            String username = row.getCell(0).getStringCellValue();
            //得到每一行第一个单元格的值
            //判断是否为空
            if(username == null || username.isEmpty()){
                //username.matches("^[a-zA-Z]{1}\\w{5,12}$")
                //姓名未填写
                throw new CustomException(ResultStatusEnum.User_name_is_not_filled_in);
            }

            row.getCell(1).setCellType(CellType.STRING);
            String chinesename = row.getCell(1).getStringCellValue();
            //得到每一行第一个单元格的值
            //判断是否为空
            if(chinesename == null || chinesename.isEmpty()){
                //chinesename.matches("^[\\u4e00-\\u9fa5]{2,6}$")
                //姓名未填写
                throw new CustomException(ResultStatusEnum.User_name_is_not_filled_in);
            }
            row.getCell(2).setCellType(CellType.STRING);
            //得到每一行的 第二个单元格的值
            String email = row.getCell(2).getStringCellValue();
            if(email==null || email.isEmpty()){
                //email.matches("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*")
                //邮箱未填写
                throw new CustomException(ResultStatusEnum.Email_not_filled);
            }

            row.getCell(3).setCellType(CellType.STRING);
            //得到每一行的 第三个单元格的值
            String phone = row.getCell(3).getStringCellValue();
            if(phone==null || phone.isEmpty()){
                //phone.matches("^[1][3,4,5,7,8][0-9_]{9}$")
                //电话未填写
                throw new CustomException(ResultStatusEnum.Phone_not_filled);
            }

            row.getCell(4).setCellType(CellType.STRING);
            //得到每一行的 第四个单元格的值
            String gitAccount = row.getCell(4).getStringCellValue();
            if(gitAccount==null || gitAccount.isEmpty()){
                //gitAccount.matches("^\\w{10,25}$")
                //gitlab账号未填写
                throw new CustomException(ResultStatusEnum.Gitlab_account_not_filled);
            }

            row.getCell(5).setCellType(CellType.NUMERIC);
            //得到每一行的 第五个单元格的值
            Double score = row.getCell(5).getNumericCellValue();
            if(score==null){
                //成绩未填写
               throw new CustomException(ResultStatusEnum.Score_not_filled);
            }

            row.getCell(6).setCellType(CellType.NUMERIC);
            //得到每一行的 第六个单元格的值
            Double credit = row.getCell(6).getNumericCellValue();
            if(credit==null){
                //学分未填写
               throw new CustomException(ResultStatusEnum.Credits_not_filled);
            }

            row.getCell(7).setCellType(CellType.NUMERIC);
            //得到每一行的 第六个单元格的值
            Integer access = Integer.valueOf((int) row.getCell(7).getNumericCellValue());
            if(access==null){
                //学分未填写
                throw new CustomException(ResultStatusEnum.Credits_not_filled);
            }

            //完整的循环一次 就组成了一个对象
            user.setUserName(username);
            user.setPassWord(null);
            user.setChineseName(chinesename);
            user.setEmail(email);
            user.setPhone(phone);
            user.setGitAccount(gitAccount);
            user.setScore(score);
            user.setCredit(credit);
            user.setCode(null);
            user.setAccess(access);
            user.setStatus(null);
            userList.add(user);
        }
        for (User userResord : userList) {
            String name = userResord.getUserName();
            int cnt = userMapper.selectByName(name);
            if (cnt == 0) {
                userMapper.addUser(userResord);
                System.out.println(" 插入 "+userResord);
            } else {
                userMapper.updateUserByName(userResord);
                System.out.println(" 更新 "+userResord);
            }
        }
        }catch (CustomException | IOException e){
            e.printStackTrace();
        }
        return notNull;
    }

}
