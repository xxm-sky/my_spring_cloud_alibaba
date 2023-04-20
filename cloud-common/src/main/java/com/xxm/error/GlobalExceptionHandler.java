package com.xxm.error;

import cn.felord.payment.PayException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.xxm.bean.Result;
import com.xxm.enums.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result<?> unknownException(Exception e, HttpServletRequest request) {
        log.error("RequestURI:{};Catch Exception:{}", request.getRequestURI(), e.getMessage(), e);
        return Result.fail(ResultEnum.FAIL);
    }

    @ExceptionHandler(PayException.class)
    public Result<?> payException(PayException e) {
        log.error(e.getMessage());
        if (e.getResponse().getBody() == null) {
            log.error("支付返回:{}", e.getResponse().toString());
            return Result.fail(400, "支付异常");
        }
        JSONObject jsonObject = JSON.parseObject(e.getResponse().getBody().toString());
        Object message = jsonObject.get("message");
        return Result.fail(e.getResponse().getStatusCodeValue(), message.toString());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result<?> httpMessageNotReadableException(Exception e, HttpServletRequest request) {
        log.error("RequestURI:{};Catch Exception:{}", request.getRequestURI(), e.getMessage(), e);
        return Result.fail(ResultEnum.PARAM_ERROR);
    }

    @ExceptionHandler(JWTDecodeException.class)
    public Result<?> jwtDecodeException(Exception e, HttpServletRequest request) {
        log.error("RequestURI:{};Catch Exception:{}", request.getRequestURI(), e.getMessage(), e);
        return Result.fail(ResultEnum.TOKEN_DECODE_ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public Result<?> illegalArgumentException(Exception e, HttpServletRequest request) {
        log.error("RequestURI:{};Catch Exception:{}", request.getRequestURI(), e.getMessage(), e);
        return Result.fail(500, e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<?> methodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        // 入参校验异常
        String errorInfo = e.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(","));
        log.error("RequestURI:{};Catch Exception Msg:{}", request.getRequestURI(), errorInfo);
        return Result.fail(400, errorInfo);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result<?> httpRequestMethodNotSupportedException(Exception e, HttpServletRequest request) {
        log.error("RequestURI:{};Catch Exception:{}", request.getRequestURI(), e.getMessage(), e);
        return Result.fail(500, e.getMessage());
    }

    @ExceptionHandler(ServiceException.class)
    public Result<?> serviceException(ServiceException e) {
        if (e.getCode() == null) return Result.fail(500, e.getMessage());
        return Result.fail(e.getCode(), e.getMessage());
    }
}
