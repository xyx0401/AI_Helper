package com.helperserver.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "推荐记录实体类")
public class Recommendation {
    @ApiModelProperty("推荐ID")
    private String recommendationId;
    
    @ApiModelProperty("关联用户ID")
    private String userId;
    
    @ApiModelProperty("关联会议ID")
    private String conferenceId;
    
    @ApiModelProperty("关联议程ID")
    private String agendaId;
    
    @ApiModelProperty("推荐理由")
    private String reason;
}