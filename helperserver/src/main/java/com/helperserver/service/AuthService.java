package com.helperserver.service;

import com.helperserver.bean.User;
import com.helperserver.bean.UserAuth;

public interface AuthService {
    /**
     * 用户注册
     * @param username 用户名
     * @param password 密码
     * @param email 邮箱
     * @param phone 手机号（可选）
     * @return 注册成功返回用户信息，失败返回null
     */
    User register(String username, String password, String email, String phone);

    /**
     * 检查用户名是否已存在
     * @param username 用户名
     * @return 存在返回true，不存在返回false
     */
    boolean isUsernameExists(String username);

    /**
     * 检查邮箱是否已存在
     * @param email 邮箱
     * @return 存在返回true，不存在返回false
     */
    boolean isEmailExists(String email);

    /**
     * 用户登录
     * @param identifier 用户标识（邮箱或手机号）
     * @param credential 凭证（密码或验证码）
     * @param authType 认证类型（EMAIL, PHONE, PASSWORD）
     * @return 登录成功返回用户信息，失败返回null
     */
    User login(String identifier, String credential, String authType);

    /**
     * 发送验证码
     * @param identifier 用户标识（邮箱或手机号）
     * @param authType 认证类型（EMAIL或PHONE）
     * @return 发送成功返回true，失败返回false
     */
    boolean sendVerificationCode(String identifier, String authType);

    /**
     * 验证用户凭证
     * @param identifier 用户标识
     * @param credential 凭证
     * @param authType 认证类型
     * @return 验证成功返回true，失败返回false
     */
    boolean verifyCredential(String identifier, String credential, String authType);

    /**
     * 创建认证记录
     * @param userAuth 用户认证信息
     * @return 创建成功返回true，失败返回false
     */
    boolean createAuth(UserAuth userAuth);

    /**
     * 更新认证记录
     * @param userAuth 用户认证信息
     * @return 更新成功返回true，失败返回false
     */
    boolean updateAuth(UserAuth userAuth);

    /**
     * 检查手机号是否已存在
     * @param phone 手机号
     * @return 存在返回true，不存在返回false
     */
    boolean isPhoneExists(String phone);

    /**
     * 删除认证记录
     * @param authId 认证ID
     * @return 删除成功返回true，失败返回false
     */
    boolean deleteAuth(String authId);
}