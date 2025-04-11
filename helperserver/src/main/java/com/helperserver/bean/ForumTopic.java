package com.helperserver.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(description = "论坛议题热度实体类")
public class ForumTopic {
    @ApiModelProperty("议题ID")
    private String topicId;
    
    @ApiModelProperty("关联会议ID")
    private String conferenceId;
    
    @ApiModelProperty("议题标题")
    private String title;
    
    @ApiModelProperty("议题内容")
    private String content;
    
    @ApiModelProperty("浏览次数")
    private Integer viewCount;
    
    
    @ApiModelProperty("收藏数")
    private Integer favoriteCount;
    
    @ApiModelProperty("热度分数")
    private Double hotScore;
    
    @ApiModelProperty("创建时间")
    private Date createTime;
    
    @ApiModelProperty("更新时间")
    private Date updateTime;
}