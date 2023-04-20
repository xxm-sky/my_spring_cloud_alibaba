package com.xxm.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
@ApiModel("获取token出参")
public class AccessTokenDto {

    @ApiModelProperty("token值")
    private String token;

    @ApiModelProperty("权限类型")
    private Integer roleId;

}
