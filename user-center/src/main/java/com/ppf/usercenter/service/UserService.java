package com.ppf.usercenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ppf.usercenter.model.domain.User;

import javax.servlet.http.HttpServletRequest;

/**
* @author 25137
* @description 针对表【user】的数据库操作Service
* @createDate 2023-05-19 14:07:28
*/
public interface UserService extends IService<User> {

    /**
     * 用户注册
     *
     * @param account 用户名
     * @param password 密码
     * @param confirmedPassword 确认密码
     * @param stuNum 学号
     * @return 用户id
     */
    long userRegister(String account, String password, String confirmedPassword, String stuNum);

    /**
     * 用户登录
     *
     * @param account 用户名
     * @param password  密码
     * @param request
     * @return 用户信息
     */
    User userLogin(String account, String password, HttpServletRequest request);


    /**
     * 用户注销
     *
     * @param request
     * @return 注销成功返回 1
     */
    int userLogout(HttpServletRequest request);

    /**
     * 用户数据脱敏
     *
     * @param user
     * @return 返回脱敏后的用户
     */
    User getSafetyUser(User user);



}
