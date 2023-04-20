package com.xxm.bean;


import com.xxm.enums.ResultEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {

    private Integer code;

    private String msg;

    private T data;

    private Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Result(ResultEnum resultEnum) {
        this.code = resultEnum.getCode();
        this.msg = resultEnum.getMsg();
    }

    private Result(ResultEnum resultEnum, T data) {
        this(resultEnum);
        this.data = data;
    }


    public static <T> Result<T> success(T data) {
        return new Result<T>(ResultEnum.SUCCESS, data);
    }

    public static Result<?> success() {
        return new Result(ResultEnum.SUCCESS);
    }

    public static Result<?> fail(ResultEnum resultEnum) {
        return new Result(resultEnum);
    }

    public static Result<?> fail(Integer code, String msg) {
        return new Result<>(code, msg);
    }

    public static Result<?> fail(String msg) {
        return new Result<>(400, msg);
    }
}
