package com.helperserver.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "演讲嘉宾实体类")
public class Speaker {
    @ApiModelProperty("嘉宾ID")
    private String speakerId;
    
    @ApiModelProperty("嘉宾姓名")
    private String name;
    
    @ApiModelProperty("职位/头衔")
    private String title;
    
    @ApiModelProperty("个人简介")
    private String bio;
}