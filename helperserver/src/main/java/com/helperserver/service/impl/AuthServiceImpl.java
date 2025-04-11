package com.helperserver.service.impl;

import com.helperserver.bean.User;
import com.helperserver.bean.UserAuth;
import com.helperserver.mapper.UserMapper;
import com.helperserver.service.AuthService;
import com.helperserver.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User register(String username, String password, String email, String phone) {
        // 检查用户名、邮箱和手机号是否已存在
        if (isUsernameExists(username) || isEmailExists(email) || isPhoneExists(phone)) {
            return null;
        }

        // 验证输入参数
        if (username == null || username.trim().isEmpty() ||
            password == null || password.trim().isEmpty() ||
            email == null || email.trim().isEmpty()) {
            return null;
        }

        // 创建新用户
        User user = new User();
        user.setUserId(UUID.randomUUID().toString());
        user.setUsername(username.trim());
        user.setPasswordHash(SecurityUtils.md5Hash(password));
        user.setEmail(email.trim());
        user.setPhone(phone != null ? phone.trim() : null);
        user.setRole("user");
        user.setCreatedAt(LocalDateTime.now());

        // 保存用户信息
        userMapper.insert(user);

        return user;
    }

    @Override
    public boolean isUsernameExists(String username) {
        if (username == null || username.trim().isEmpty()) {
            return false;
        }
        User user = userMapper.selectByUsername(username.trim());
        return user != null;
    }

    @Override
    public boolean isEmailExists(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        User user = userMapper.selectByEmail(email.trim());
        return user != null;
    }

    @Override
    public User login(String identifier, String credential, String authType) {
        if (identifier == null || identifier.trim().isEmpty() ||
            credential == null || credential.trim().isEmpty() ||
            authType == null || authType.trim().isEmpty()) {
            return null;
        }

        User user = null;
        identifier = identifier.trim();
        
        if ("EMAIL".equals(authType)) {
            user = userMapper.selectByEmail(identifier);
        } else if ("PHONE".equals(authType)) {
            user = userMapper.selectByPhone(identifier);
        }

        if (user == null) {
            return null;
        }

        // 验证密码
        String passwordHash = SecurityUtils.md5Hash(credential);
        if (!passwordHash.equals(user.getPasswordHash())) {
            return null;
        }

        return user;
    }

    @Override
    public boolean sendVerificationCode(String identifier, String authType) {
        // 移除验证码发送功能
        return false;
    }

    @Override
    public boolean verifyCredential(String identifier, String credential, String authType) {
        // 移除验证码验证功能
        return false;
    }

    @Override
    public boolean createAuth(UserAuth userAuth) {
        // 认证记录创建功能实现
        return false;
    }

    @Override
    public boolean updateAuth(UserAuth userAuth) {
        // 移除认证记录更新功能实现
        return false;
    }

    @Override
    public boolean isPhoneExists(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            return false;
        }
        User user = userMapper.selectByPhone(phone.trim());
        return user != null;
    }

    @Override
    public boolean deleteAuth(String authId) {
        // 移除认证记录删除功能实现
        return false;
    }
}