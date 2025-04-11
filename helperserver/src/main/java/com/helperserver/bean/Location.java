package com.helperserver.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "会议地点详情实体类")
public class Location {
    @ApiModelProperty("地点ID")
    private String locationId;
    

    
    @ApiModelProperty("纬度")
    private Float latitude;
    
    @ApiModelProperty("经度")
    private Float longitude;
    
    @ApiModelProperty("详细地址")
    private String address;
}