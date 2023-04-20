package com.xxm.interceptor;


import com.xxm.consts.BaseConst;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;



@Configuration
public class FeignRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        template.header(BaseConst.FEIGN, BaseConst.FEIGN);
        template.header(JwtInterceptor.AUTHORIZATION, attributes.getRequest().getHeader(JwtInterceptor.AUTHORIZATION));
    }
}
