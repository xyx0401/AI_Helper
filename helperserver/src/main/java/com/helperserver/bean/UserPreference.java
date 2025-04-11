package com.helperserver.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "用户偏好实体类")
public class UserPreference {
    @ApiModelProperty("偏好ID")
    private String preferenceId;
    
    @ApiModelProperty("关联用户ID")
    private String userId;
    
    @ApiModelProperty("用户兴趣标签")
    private String interests;
    
    @ApiModelProperty("用户首选语言")
    private String language;
}