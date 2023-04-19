package com.xxm.feign;


import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FeignServiceFallBack implements FallbackFactory<ManagerFeign> {

    @Override
    public ManagerFeign create(Throwable throwable) {
        return new ManagerFeign() {
            @Override
            public String index() {
                return "manager服务被降级停用了";
            }
        };
    }
}
