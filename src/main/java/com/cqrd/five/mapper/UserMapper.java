package com.cqrd.five.mapper;

import com.cqrd.five.dto.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserMapper {

    /**
     * 查询用户名是否存在，若存在，不允许注册
     * @return
     */
    User findUserByName(String userName);

    /**
     * 注册  插入一条user记录
     * @param user
     * @return
     */
    int regist(User user);

    /**
     * 登录
     * @param user
     * @return
     */
    User login(User user);

    /**
     * 查询所有信息
     * @return 用户数据
     */
    List<User> findByAll();

    //校验username是否存在
    int checkUsername(String userName);
    //校验email是否存在
    int checkEmail(String email);
    //通过email查询用户名是否匹配
    String selectUserNameByEmail(String email);
    //更新数据库验证码
    boolean updateCode(@Param("md5code")String md5code, @Param("username")String username);
    //更新数据库验证码时间
    boolean updateDate(@Param("stringoutdate") String stringoutdate, @Param("username")String username);
    //查询验证码过期时间
    String selectDate(String username);
    //查询验证码
    String selectCode(String username);
    //更新用户密码
    boolean updatePassword( @Param("username")String username,@Param("md5newpassword")String md5newpassword);
    //更新验证码时间
    boolean twoUpdateDate(@Param("stringnewdate") String stringnewdate, @Param("username")String username);
    //清空验证码
    boolean twoUpdateCode(String username);
    //导出选择的用户
    List<User> findUserByExportChecked(String[] username);

    /**
     * 导入操作
     * @return
     */
    List<User> selectUsers();

    void updateUserByName(User user);

    void addUser(User user);

    int selectByName(String username);



    User findByName(String username);
}

