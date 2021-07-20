package com.cgq.boot;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cgq.boot.pojo.User;
import com.cgq.boot.service.UserService;
import org.apache.tomcat.util.security.MD5Encoder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;

@SpringBootTest
class SpringbootBlogApplicationTests {

    @Autowired
    UserService userService;

    @Test
    void contextLoads() {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

//        queryWrapper.eq("username","cgq123");
//        queryWrapper.eq("password","123456");

        System.out.println(userService.getOne(queryWrapper));
    }

}
