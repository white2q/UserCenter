package com.ppf.usercenter.model.domain.request;

import lombok.Data;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * @author panpengfei
 * @date 2023/5/19
 */
@Data
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = 4204282466937019641L;

    private String account;
    private String password;
}
