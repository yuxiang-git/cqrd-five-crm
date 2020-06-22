package com.cqrd.five.mapper;

import com.cqrd.five.dto.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CrmMapper {

    /*登录*/
    public List<User> login(String username, String password);
    /*注册*/
    public boolean register(List<User> list);
    /*添加、修改*/
    public boolean addupdate(User user);
    /*显示所有数据*/
    public List<User> showUserInfo();
    /*清空所有数据*/
    public boolean truncateUser();
    /*录入用户名、密码非空数据*/
    public boolean addUser(User user);
    /*批量删除*/
    public boolean delete(Integer[] ids);
    /*查询执行批量删除的ID*/
    public Integer[] deleteSelect(String[] username);
    /*根据全部查找*/
    public List<User> selectUserInfoByAll(String all);
    /*根据姓名查找*/
    public List<User> selectUserInfoByName(String userName);
    /*根据电话查找*/
    public List<User> selectUserInfoByPhone(String phone);
    /*根据gitlab账号查找*/
    public List<User> selectUserInfoByGitAccount(String gitaccount);
    /*根据邮箱查找*/
    public List<User> selectUserInfoByEmail(String email);
    /*根据成绩查找*/
    public List<User> selectUserInfoByScore(Double score);
    /*根据学分查找*/
    public List<User> selectUserInfoByCredit(Double credit);
    /*（普通用户）显示所有数据*/
    public List<User> showOrdiInfo();
    /*（普通用户）根据全部查找*/
    public List<User> selectOrdiInfoByAll(String all);
    /*（普通用户）根据姓名查找*/
    public List<User> selectOrdiInfoByName(String userName);
    /*（普通用户）根据电话查找*/
    public List<User> selectOrdiInfoByPhone(String phone);
    /*（普通用户）根据gitlab账号查找*/
    public List<User> selectOrdiInfoByGitAccount(String gitaccount);
    /*（普通用户）根据邮箱查找*/
    public List<User> selectOrdiInfoByEmail(String email);
    /*（普通用户）根据成绩查找*/
    public List<User> selectOrdiInfoByScore(Double score);
    /*（普通用户）根据学分查找*/
    public List<User> selectOrdiInfoByCredit(Double credit);
    /*找回密码*/
    public boolean findPassWordByEmail(String username, String email);
}
