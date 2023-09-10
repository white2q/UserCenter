package com.ppf.usercenter.constant;

/**
 * @author panpengfei
 * @date 2023/5/19
 */
public interface UserConstant {
    /**
     * 注册账号最短长度
     */
    long ACCOUNT_MIN_LENGTH = 4;
    /**
     * 注册密码最短长度
     */
    long ACCOUNT_MIN_PASSWORD = 8;

    /**
     * 学号长度
     */
    long STU_NUM_LENGTH = 10;

    /**
     * 用户登录状态
     */
    String USER_LOGIN_STATE = "userLoginState";

    /**
     * 默认权限
     */
    int DEFAULT_ROLE = 0;

    /**
     * 管理员权限
     */
    int ADMIN_ROLE = 1;
}
