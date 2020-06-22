package com.cqrd.five.service;

import com.cqrd.five.dto.Result;
import com.cqrd.five.dto.User;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.util.List;

public interface UserService {
    /**
     *注册用户检查用户是否已经存在
     * @param userName 用户名
     * @return 用户的数据
     */
     User checkRegist(String userName);

    /**
     * 注册
     * @param user 用户数据
     * @return 注册是否成功的结果
     */
     Boolean regist(User user);
    /**
     * 查询所有用户数据
     * @return 用户数据
     */
    List<User> deriveByAll();

    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    List<User> findUserByName(String username);

    //导出选择的用户
    List<User> findUserByExportChecked(String[] username);

    //发送验证码到邮箱
    public Result sendCode(String username, String email) throws MessagingException;
    //验证验证码并修改密码
    public Result resetPassword(String username, String emailcode, String newpassword);

    /**
     * 导入操作
     */
    boolean batchImport(String fileName, MultipartFile file) throws Exception;




    User findByName(String username);
}
