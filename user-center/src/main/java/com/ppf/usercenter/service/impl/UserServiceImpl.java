package com.ppf.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ppf.usercenter.common.ErrorCode;
import com.ppf.usercenter.exception.BusinessException;
import com.ppf.usercenter.mapper.UserMapper;
import com.ppf.usercenter.model.domain.User;
import com.ppf.usercenter.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.ppf.usercenter.constant.UserConstant.*;

/**
* @author ppf
* @description 针对表【user】的数据库操作Service实现
* @createDate 2023-05-19 14:07:28
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{

    /**
     * 盐值
     */
    private static final String SALT = "ppfnb";

    @Override
    public long userRegister(String account, String password, String confirmedPassword, String stuNum) {
        // 非空
        if(StringUtils.isAnyBlank(account, password, confirmedPassword)) {
           throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }

        if(account.length() < ACCOUNT_MIN_LENGTH) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户长度少于4位");
        }

        if(password.length() < ACCOUNT_MIN_PASSWORD) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码长度少于8位");
        }

        if(confirmedPassword.length() < ACCOUNT_MIN_PASSWORD) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "确认密码长度少于8位");
        }

        if (!password.equals(confirmedPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "确认密码与密码不相等");
        }

        if(stuNum.length() != STU_NUM_LENGTH) {
           throw new BusinessException(ErrorCode.PARAMS_ERROR, "非10位学号");
        }

        // 用户名：字母数字下划线
        String usernameRegex = "^\\w{4,16}$";
        Pattern pattern = Pattern.compile(usernameRegex);
        Matcher matcher = pattern.matcher(account);
        if(!matcher.find()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户名违规： 请确保只由数字，字母，下划线组成");
        }

        // 判断是否已存在该用户名
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("ACCOUNT", account);
        Long count = this.baseMapper.selectCount(queryWrapper);
        if(count != 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "该用户名已存在");
        }

        queryWrapper.eq("STU_NUM", stuNum);
        count = this.baseMapper.selectCount(queryWrapper);
        if(count != 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "该学号已存在");
        }
        // 对密码加密
        String encodePassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes());

        User safetyUser = new User();
        safetyUser.setAccount(account);
        safetyUser.setPassword(encodePassword);

        boolean res = this.save(safetyUser);
        if(!res) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "数据库保存失败");
        }
        User user = baseMapper.selectOne(queryWrapper);
        return user.getId();
    }

    @Override
    public User userLogin(String account, String password, HttpServletRequest request) {

        if(StringUtils.isAnyBlank(account, password)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        if(account.length() < ACCOUNT_MIN_LENGTH) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号长度小于4位");
        }

        if(password.length() < ACCOUNT_MIN_PASSWORD) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码长度小于8位");
        }

        String usernameRegex = "^\\w{4,16}$";
        Pattern pattern = Pattern.compile(usernameRegex);
        Matcher matcher = pattern.matcher(account);
        if(!matcher.find()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户名格式错误");
        }

        String encodePassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes());
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ACCOUNT", account);
        queryWrapper.eq("PASSWORD", encodePassword);
        User user = this.baseMapper.selectOne(queryWrapper);

        if(user == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "user login failed, account cannot match password");
        }
        User safetyUser = getSafetyUser(user);
        // 记录用户登录状态
        request.getSession().setAttribute(USER_LOGIN_STATE, safetyUser);

        return safetyUser;
    }

    @Override
    public int userLogout(HttpServletRequest request) {
        // 移除登录态
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return 0;
    }

    @Override
    public User getSafetyUser(User user) {
        if(user == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 数据脱敏
        User safetyUser = new User();
        safetyUser.setId(user.getId());
        safetyUser.setAccount(user.getAccount());
        safetyUser.setNickName(user.getNickName());
        safetyUser.setAvatar(user.getAvatar());
        safetyUser.setGender(user.getGender());
        safetyUser.setPhone(user.getPhone());
        safetyUser.setEmail(user.getEmail());
        safetyUser.setStuNum(user.getStuNum());
        safetyUser.setUserRole(user.getUserRole());
        safetyUser.setStatus(user.getStatus());
        safetyUser.setCreateTime(user.getCreateTime());
        safetyUser.setUpdateTime(new Date());
        return safetyUser;
    }

}




