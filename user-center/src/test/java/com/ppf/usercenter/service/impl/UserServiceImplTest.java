package com.ppf.usercenter.service.impl;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;

/**
 * @author panpengfei
 * @date 2023/5/19
 */
@SpringBootTest
class UserServiceImplTest {

    @Resource
    UserServiceImpl userService;

    @Test
    void userRegister() {
        String account = "ppf1";
        String password = "12345678";
        String stuNumber = "2106005112";
        long id = userService.userRegister(account, password, password, stuNumber);
        System.out.println(id);
//        Assertions.assertEquals(11, id);
    }

    @Test
    void userLogin() {
        String account = "ppf1";
        String password = "12345678";
    }
}