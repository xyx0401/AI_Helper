package com.helperserver.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.Pattern;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.AssertTrue;

@Data
@ApiModel(description = "登录请求DTO")
public class LoginRequest {
    @AssertTrue(message = "邮箱和手机号必须填写其中一个")
    public boolean isEmailOrPhonePresent() {
        return (email != null && !email.trim().isEmpty()) || 
               (phone != null && !phone.trim().isEmpty());
    }
    @ApiModelProperty(value = "邮箱", notes = "邮箱和手机号必须填写其中一个")
    @Email(message = "邮箱格式不正确")
    private String email;
    
    @ApiModelProperty(value = "手机号", notes = "邮箱和手机号必须填写其中一个")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;
    
    @ApiModelProperty(value = "密码", required = true)
    @NotBlank(message = "密码不能为空")
    private String password;
}