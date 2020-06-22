package com.cqrd.five.controller;

import com.cqrd.five.config.JwtProperties;
import com.cqrd.five.dto.UserInfo;
import com.cqrd.five.ex.CustomException;
import com.cqrd.five.ex.ResultStatusEnum;

import com.cqrd.five.util.JedisPoolUtils;
import com.cqrd.five.util.JwtUtils;
import com.cqrd.five.util.MD5Util;
import com.cqrd.five.dto.Result;
import com.cqrd.five.dto.User;
import com.cqrd.five.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
@EnableConfigurationProperties(JwtProperties.class)
public class UserController {
    private final static Logger logger = LoggerFactory.getLogger(findPassWordController.class);
    @Autowired
    private UserServiceImpl userServiceImpl;
    @Autowired
    private JwtProperties jwtProperties;


    /**
     * 注册
     * @param user 参数封装
     * @return Result
     */
    @PostMapping(value = "/regist")
    public Result regist(HttpServletRequest request ,User user){
        String userName = request.getParameter("userName");
        String passWord = request.getParameter("passWord");
        //String repassWord = request.getParameter("repassWord");
        String chineseName = request.getParameter("chineseName");
        String email = request.getParameter("email");
        String gitAccount = request.getParameter("gitAccount");
        String phone = request.getParameter("phone");

        String inputStr = request.getParameter("ICode");
        String md5inputstr = MD5Util.MD5EncodeUtf8(inputStr);

        user.setUserName(userName);
        user.setPassWord(MD5Util.getMD5(passWord));
        user.setChineseName(chineseName);
        user.setEmail(email);
        user.setGitAccount(gitAccount);
        user.setPhone(phone);

        Result result = new Result();
        result.setSuccess(false);
        result.setDetail(null);
        try {
            User checkExistUser = userServiceImpl.checkRegist(user.getUserName());

            //从客戶端获取随机数
            Jedis jedis = JedisPoolUtils.getJedis();
            String codelistJson = jedis.get("codelistJson");
            String random = codelistJson.substring(1, codelistJson.length()-1);
            String datelistJson = jedis.get("datelistJson");

            Date newdate = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String stringnewdate = sdf.format(newdate);
            if(stringnewdate.compareTo(datelistJson) > 0){
                result.setMsg("验证码过期");
                return result;
            }

            if(md5inputstr == null){
                result.setMsg("验证码校验失败");
                return result;
            }
            if (random == null) {
                result.setMsg("验证码校验失败");
                return result;
            }
            if (random.equals(md5inputstr)) {
                if(checkExistUser !=null){
                    //如果用户名已存在
                    result.setMsg("用户名已存在");
                    //抛出用户名已存在的异常
                    throw new CustomException(ResultStatusEnum.UsernameDuplicateException);

                }else{
                    userServiceImpl.regist(user);
                    //System.out.println(user.getId());
                    result.setMsg("注册成功");
                    result.setSuccess(true);
                    result.setDetail(user);
                }
                return result;
            } else {
                result.setMsg("验证码校验失败");
                return result;
            }


        }catch (CustomException e){
            throw new  CustomException(ResultStatusEnum.Verification_code_system_error);
        }catch (Exception e) {
            logger.error("验证码系统错误，请联系管理员", e);
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;

    }
    /**
     * 登录
     * @param user 参数封装
     * @return Result
     */
    @PostMapping(value = "/login")
    //@RequestMapping(value = "/login", method = RequestMethod.GET)
    public Result login(HttpServletRequest request,User user){

        Result result = new Result();
        result.setSuccess(false);
        result.setDetail(null);

        String userName = request.getParameter("userName");
        String passWord = request.getParameter("passWord");

        String inputStr = request.getParameter("ICode");
        String md5inputstr = MD5Util.MD5EncodeUtf8(inputStr);

        user.setUserName(userName);
        user.setPassWord(MD5Util.getMD5(passWord));
        UserInfo userInfo = new UserInfo();
        try {
            User checkUser=  userServiceImpl.login(user);

            //从客戶端获取随机数
            Jedis jedis = JedisPoolUtils.getJedis();
            String codelistJson = jedis.get("codelistJson");
            String randomcode = codelistJson.substring(1, codelistJson.length()-1);
            String datelistJson = jedis.get("datelistJson");

            Date newdate = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String stringnewdate = sdf.format(newdate);
            System.out.println(codelistJson);
            System.out.println(md5inputstr);
            if(stringnewdate.compareTo(datelistJson) > 0){
                result.setMsg("验证码过期");
                return result;
            }

            if(md5inputstr == null){
                result.setMsg("验证码校验失败");
                return result;
            }
            if ( randomcode == null) {
                result.setMsg("验证码校验失败");
                return result;
            }
            if ( randomcode.equals(md5inputstr)) {
                if(checkUser==null){
                    result.setMsg("用户名或密码错误");
                    return result;
                    //抛出用户名或密码错误
                    //throw new CustomException(ResultStatusEnum.NotMatchException);
                }else{
                    if(checkUser.getAccess()==1){
                        userInfo.setUsername(checkUser.getUserName());
                        userInfo.setAccess(checkUser.getAccess());
                        String token = JwtUtils.generateToken(userInfo,this.jwtProperties.getPrivateKey(),this.jwtProperties.getExpire()*60);
                        result.setToken(token);
                        System.out.println(token);
                        result.setMsg("登录成功，欢迎管理员");
                    }else{
                        userInfo.setUsername(checkUser.getUserName());
                        userInfo.setAccess(checkUser.getAccess());
                        String token = JwtUtils.generateToken(userInfo,this.jwtProperties.getPrivateKey(),this.jwtProperties.getExpire()*60);
                        result.setToken(token);
                        /*System.out.println(token);*/

                        result.setMsg("登录成功");
                        String code = "null";
                        jedis.set("codelistJson", code );
                    }

                    result.setSuccess(true);
                    result.setDetail(checkUser);
                }
                return result;
            } else {
                result.setMsg("验证码校验失败");
                return result;
            }


        }catch (CustomException e){
            throw new  CustomException(ResultStatusEnum.Verification_code_system_error);
        }catch (Exception e) {
            logger.error("验证码系统错误，请联系管理员", e);
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }

        return result;
    }








}
