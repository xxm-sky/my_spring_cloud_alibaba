package com.xxm.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author YouMing LIN
 * @date 2022-07-12 22:26
 **/
@Data
public class BaseDto {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("名称")
    private String name;
}
