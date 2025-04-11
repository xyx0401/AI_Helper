package com.helperserver.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "加密密钥实体类")
public class EncryptionKey {
    @ApiModelProperty("密钥ID")
    private String keyId;
    
    @ApiModelProperty("关联用户ID")
    private String userId;
    
    @ApiModelProperty("加密后的密钥")
    private String encryptedKey;
}