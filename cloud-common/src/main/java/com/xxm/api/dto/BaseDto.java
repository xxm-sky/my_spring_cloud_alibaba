package com.xxm.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class BaseDto {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("名称")
    private String name;
}
