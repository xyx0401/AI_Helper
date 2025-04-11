package com.helperserver.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "会议资料实体类")
public class Resource {
    @ApiModelProperty("资料ID")
    private String resourceId;
    
    @ApiModelProperty("关联会议ID")
    private String conferenceId;
    
    @ApiModelProperty("关联议程ID")
    private String agendaId;
    
    @ApiModelProperty("文件下载链接")
    private String fileUrl;
    
    @ApiModelProperty("文件类型")
    private String fileType;
}