package com.helperserver.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "会议指南实体类")
public class ConferenceGuide {
    @ApiModelProperty("指南ID")
    private String guideId;
    
    @ApiModelProperty("关联的会议ID")
    private String conferenceId;
    
    @ApiModelProperty("会议日程")
    private String schedule;
    
    @ApiModelProperty("会场规则")
    private String rules;
    
    @ApiModelProperty("餐饮服务信息")
    private String diningServices;
    
    @ApiModelProperty("其他服务信息")
    private String otherServices;
    
    @ApiModelProperty("会场地图")
    private String venueMap;
    
    @ApiModelProperty("交通指南")
    private String transportationGuide;
    
    @ApiModelProperty("联系方式")
    private String contactInfo;
    
    @ApiModelProperty("创建时间")
    private String createTime;
    
    @ApiModelProperty("更新时间")
    private String updateTime;
}