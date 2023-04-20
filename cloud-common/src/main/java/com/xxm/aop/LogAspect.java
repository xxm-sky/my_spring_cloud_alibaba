package com.xxm.aop;


import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Aspect
@Component
public class LogAspect {

    /**
     * 匹配请求方式
     */
    private static final String CONTENT_TYPE = "application/json";

    /**
     * 申明一个切点 里面是 execution表达式
     */
    @Pointcut("execution(public * com.xxm.*.controller.*.*(..)) && !@annotation(springfox.documentation.annotations.ApiIgnore)")
    private void controllerAspect() {
    }

    /**
     * 请求method前打印内容
     *
     * @param joinPoint 请求数据
     */
    @Before(value = "controllerAspect()")
    public void enterLogBefore(JoinPoint joinPoint) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        HttpServletRequest request = requestAttributes.getRequest();
        log.info("-----------------请求内容--------------");
        try {
            String ip = this.getRequestIp(request);
            // 打印请求内容
            log.info("客户端IP：{}", ip);
            log.info("请求地址：{}", request.getRequestURL().toString());
            log.info("请求方式：{}", request.getMethod());
            log.info("请求类型：{}", request.getContentType());
            log.info("请求类方法：{}", joinPoint.getSignature());
            log.info("请求时间：{}", DateUtil.now());
            if (request.getContentType() != null && request.getContentType().contains(CONTENT_TYPE)) {
                Object[] pointArgs = joinPoint.getArgs();
                if (pointArgs.length > 0) {
                    log.info("请求类方法参数：\n{}", this.toJson(joinPoint.getArgs()[0]));
                } else {
                    log.info("请求类方法参数：无参");
                }
            }
        } catch (Exception e) {
            log.error("### LogAop.class methodBefore() ### ERROR：{}", e.getMessage(), e);
        }
    }

    /**
     * 在方法执行完结后打印返回内容
     */
    @AfterReturning(returning = "o", pointcut = "controllerAspect()")
    public void returnLogAfter(Object o) {
        log.info("--------------返回内容----------------");
        try {
            log.info("Response 响应时间 ：{}", DateUtil.now());
            log.info("Response内容：\n{}", this.toJson(o));
        } catch (Exception e) {
            log.error("### LogAop.class methodAfterReturing() ### ERROR：{}", e.getMessage(), e);
        }
    }


    /**
     * 格式化json
     *
     * @param object 数据
     * @return String
     */
    private String toJson(Object object) {
        return JSON.toJSONString(object);
    }

    /**
     * 获取请求方的IP地址
     *
     * @param request 当前请求
     * @return String
     */
    private String getRequestIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        String str = "unknown";
        if (ip == null || ip.length() == 0 || str.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || str.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || str.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || str.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || str.equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || str.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }


}
