package com.helperserver.controller;

import com.helperserver.bean.User;
import com.helperserver.dto.LoginRequest;
import com.helperserver.dto.LoginResponse;
import com.helperserver.dto.RegisterRequest;
import com.helperserver.service.AuthService;
import com.helperserver.service.UserService;
import com.helperserver.utils.Result;
import com.helperserver.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Api(tags = "认证接口")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    @ApiOperation("用户注册")
    public Result<User> register(@RequestBody RegisterRequest request) {
        // 检查用户名、邮箱和手机号是否已存在
        if (authService.isUsernameExists(request.getUsername())) {
            return Result.error("用户名已存在");
        }
        if (authService.isEmailExists(request.getEmail())) {
            return Result.error("邮箱已存在");
        }
        if (authService.isPhoneExists(request.getPhone())) {
            return Result.error("手机号已存在");
        }

        // 注册用户
        User user = authService.register(request.getUsername(), request.getPassword(),
                request.getEmail(), request.getPhone());
        if (user == null) {
            return Result.error("注册失败");
        }

        return Result.success(user);
    }

    @PostMapping("/login")
    @ApiOperation("用户登录")
    public Result<LoginResponse> login(@RequestBody LoginRequest request) {
        // 获取用户信息
        User user = null;
        if (request.getEmail() != null && !request.getEmail().trim().isEmpty()) {
            user = userService.getUserByEmail(request.getEmail());
        } else if (request.getPhone() != null && !request.getPhone().trim().isEmpty()) {
            user = userService.getUserByPhone(request.getPhone());
        }

        if (user == null) {
            return Result.error("用户不存在");
        }

        // 验证密码
        String passwordHash = SecurityUtils.md5Hash(request.getPassword());
        if (!passwordHash.equals(user.getPasswordHash())) {
            return Result.error("密码错误");
        }

        // 生成token
        String token = SecurityUtils.generateToken(user.getUserId(), user.getUsername(), user.getRole());

        // 构建响应
        LoginResponse response = new LoginResponse();
        response.setToken(token);
        response.setUserId(user.getUserId());
        response.setUsername(user.getUsername());
        response.setRole(user.getRole());

        return Result.success(response);
    }
}