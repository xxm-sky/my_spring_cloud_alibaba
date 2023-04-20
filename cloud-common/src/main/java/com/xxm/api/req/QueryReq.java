package com.xxm.api.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class QueryReq {

    @ApiModelProperty("分页参数")
    private int pageNum = 1;

    @ApiModelProperty("分页大小")
    private int pageSize = 10;
}
