package com.helperserver.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "会议信息实体类")
public class Conference {
    @ApiModelProperty("会议标签，多个标签以逗号分隔")
    private String tags;
    @ApiModelProperty("会议唯一ID")
    private String conferenceId;
    
    @ApiModelProperty("会议标题")
    private String title;
    
    @ApiModelProperty("会议地点ID")
    private String locationId;
    
    @ApiModelProperty("开始时间")
    private java.util.Date startTime;
    
    @ApiModelProperty("结束时间")
    private java.util.Date endTime;
    
    @ApiModelProperty("演讲者ID")
    private String speakerId;
    
    @ApiModelProperty("会议描述")
    private String description;
}