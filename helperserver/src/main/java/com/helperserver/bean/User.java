package com.helperserver.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Data
@ApiModel(description = "用户实体类")
public class User {

    @ApiModelProperty("用户ID")
    private String userId;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("密码")
    private String passwordHash;

    @ApiModelProperty("邮箱")
    @Email(message = "邮箱格式不正确")
    @NotBlank(message = "邮箱不能为空")
    private String email;

    @ApiModelProperty("手机号")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;

    @ApiModelProperty("创建时间")
    private LocalDateTime createdAt;

    @ApiModelProperty("用户角色")
    private String role;
}