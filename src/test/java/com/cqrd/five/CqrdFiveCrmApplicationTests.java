package com.cqrd.five;

import com.cqrd.five.dto.User;
import com.cqrd.five.mapper.UserMapper;
import com.cqrd.five.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
@RunWith(SpringRunner.class)
@SpringBootTest
class CqrdFiveCrmApplicationTests {
    @Autowired
    private UserMapper userMapper;
    private UserService userService;
    @Test
    void contextLoads() {
    }



}
