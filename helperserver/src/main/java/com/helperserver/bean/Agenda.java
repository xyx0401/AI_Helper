package com.helperserver.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "会议议程实体类")
public class Agenda {
    @ApiModelProperty("议程ID")
    private String agendaId;
    
    @ApiModelProperty("关联会议ID")
    private String conferenceId;
    
    @ApiModelProperty("议程标题")
    private String sessionTitle;
    
    @ApiModelProperty("关联演讲嘉宾ID")
    private String speakerId;
    
    @ApiModelProperty("议程开始时间")
    private String startTime;
    
    @ApiModelProperty("议程结束时间")
    private String endTime;
}