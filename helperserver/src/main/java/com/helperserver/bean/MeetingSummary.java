package com.helperserver.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "会议总结实体类")
public class MeetingSummary {
    @ApiModelProperty("总结ID")
    private String summaryId;
    
    @ApiModelProperty("关联会议ID")
    private String conferenceId;
    
    @ApiModelProperty("会议关键点")
    private String keyPoints;
    
    @ApiModelProperty("待办事项")
    private String todoList;
    
    @ApiModelProperty("生成者")
    private String generatedBy;
}