package com.helperserver.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;

@Component
public class SecurityUtils {
    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private static final String JWT_SECRET = "your-secret-key";
    private static final long JWT_EXPIRATION = 86400000L; // 24小时

    /**
     * 计算给定字符串的MD5哈希值
     * MD5是一种广泛使用的加密算法，尽管它现在被认为不够安全，但仍可用于某些场景，如校验文件完整性
     * 
     * @param input 需要计算MD5哈希的输入字符串
     * @return 输入字符串的MD5哈希值，以十六进制字符串形式返回
     * @throws RuntimeException 如果Java安全API中不存在MD5算法，则抛出运行时异常
     */
    public static String md5Hash(String input) {
        try {
            // 获取MD5加密器实例
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算输入字符串的MD5哈希值
            byte[] messageDigest = md.digest(input.getBytes());
            // 创建StringBuilder用于构建十六进制字符串
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                // 将每个字节转换为十六进制字符串，并附加到StringBuilder
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            // 返回最终的十六进制字符串
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            // 如果Java安全API中不存在MD5算法，则抛出运行时异常
            throw new RuntimeException("MD5加密失败", e);
        }
    }

    /**
     * 验证密码是否匹配
     * @param rawPassword 原始密码
     * @param encodedPassword 加密后的密码
     * @return 如果密码匹配返回true，否则返回false
     */
    public static boolean verifyPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public static String generateToken(String userId, String username, String role) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);

        return Jwts.builder()
                .setSubject(userId)
                .claim("username", username)
                .claim("role", role)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }

    /**
     * 生成HMAC-SHA256签名
     * @param data 要签名的数据
     * @param secret 密钥
     * @return Base64编码的HMAC-SHA256签名
     */
    public static String generateHmacSha256(String data, String secret) {
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            byte[] hash = sha256_HMAC.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            throw new RuntimeException("生成HMAC-SHA256签名失败", e);
        }
    }
}