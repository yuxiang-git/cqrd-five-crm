package com.cqrd.five.service.impl;

import com.cqrd.five.dto.User;
import com.cqrd.five.mapper.CrmMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class DeleteUserNullService {

    @Autowired
    private static CrmMapper crmMapper;

    public static boolean selectNull() {
        System.out.println("查询所有记录");
        List<User> userList = crmMapper.showUserInfo();
        System.out.println(userList);
        System.out.println("清空所有记录");
        boolean b = crmMapper.truncateUser();
        System.out.println(b);
        for (User user:userList) {
            System.out.println(user.getUserName()+" "+user.getPassWord());
            if (user.getUserName() != null && user.getPassWord() != null) {
                b = crmMapper.addUser(user);
            }
        }
        return b;
    }

}
