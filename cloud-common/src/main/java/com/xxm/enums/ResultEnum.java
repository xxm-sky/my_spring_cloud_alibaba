package com.xxm.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum ResultEnum {

    //这里是可以自己定义的，方便与前端交互即可
    SUCCESS(200, "success"),
    FAIL(500, "系统异常"),
    PARAM_ERROR(201, "参数类型错误"),
    NOT_TOKEN(402, "无token,请重新登录"),
    TOKEN_PAST(401, "token过期,请重新登录"),
    PWD_ERROR(1203, "手机号或密码不正确,请检查!"),
    TOKEN_DECODE_ERROR(1204, "token解析错误,请检查!"),
    TOKEN_ERROR(1205, "token错误,请检查!"),
    PAGE_ERROR(1206, "分页数据错误!"),
    DATA_ERROR(1207, "数据异常!"),
    ;

    private final Integer code;
    private final String msg;
}
