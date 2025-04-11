package com.helperserver.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "问答知识库实体类")
public class QaKnowledge {
    @ApiModelProperty("问答ID")
    private String qaId;
    
    @ApiModelProperty("用户问题")
    private String question;
    
    @ApiModelProperty("答案")
    private String answer;
    
    @ApiModelProperty("模型匹配得分")
    private Float score;
    
    @ApiModelProperty("关联会议ID")
    private String conferenceId;
    
    @ApiModelProperty("创建时间戳")
    private Long timestamp;
}