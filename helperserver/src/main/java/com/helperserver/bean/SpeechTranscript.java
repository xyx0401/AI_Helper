package com.helperserver.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "语音转写记录实体类")
public class SpeechTranscript {
    @ApiModelProperty("转写ID")
    private String transcriptId;
    
    @ApiModelProperty("关联会议ID")
    private String conferenceId;
    
    @ApiModelProperty("关联发言人ID")
    private String speakerId;
    
    @ApiModelProperty("原始语音转写文本")
    private String originalText;
    
    @ApiModelProperty("翻译后的文本")
    private String translatedText;
}