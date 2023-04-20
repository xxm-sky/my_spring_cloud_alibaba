package com.xxm.error;

import com.xxm.enums.ResultEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 不打印堆栈信息的异常
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class ServiceException extends RuntimeException {

    private Integer code;

    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public ServiceException(ResultEnum resultEnum) {
        this(resultEnum.getCode(), resultEnum.getMsg());
    }

    /**
     * 自定义异常不打印堆栈错误信息,性能优化
     */
    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
