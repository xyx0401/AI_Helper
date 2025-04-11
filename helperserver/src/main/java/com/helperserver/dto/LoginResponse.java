package com.helperserver.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "登录响应DTO")
public class LoginResponse {
    @ApiModelProperty("访问令牌")
    private String token;
    
    @ApiModelProperty("用户ID")
    private String userId;
    
    @ApiModelProperty("用户名")
    private String username;
    
    @ApiModelProperty("用户角色")
    private String role;
}