package com.cqrd.five;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.cqrd.five.mapper")
@SpringBootApplication
public class CqrdFiveCrmApplication {

    public static void main(String[] args) {
        SpringApplication.run(CqrdFiveCrmApplication.class, args);
    }

}
