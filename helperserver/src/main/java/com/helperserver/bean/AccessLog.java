package com.helperserver.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "访问日志实体类")
public class AccessLog {
    @ApiModelProperty("日志ID")
    private String logId;
    
    @ApiModelProperty("关联用户ID")
    private String userId;
    
    @ApiModelProperty("操作类型")
    private String action;
    
    @ApiModelProperty("操作时间")
    private String timestamp;
    
    @ApiModelProperty("用户IP地址")
    private String ipAddress;
}