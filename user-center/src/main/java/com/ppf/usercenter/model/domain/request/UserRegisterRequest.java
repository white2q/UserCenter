package com.ppf.usercenter.model.domain.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author panpengfei
 * @date 2023/5/19
 */
@Data
public class UserRegisterRequest implements Serializable {

    private static final long serialVersionUID = -383410465812639050L;

    private String account;
    private String password;
    private String confirmedPassword;
    private String stuNum;
}
