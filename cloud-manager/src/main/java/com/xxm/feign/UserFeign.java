package com.xxm.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "user-server",fallbackFactory = FeignServiceFallBack.class)
public interface UserFeign {

    @RequestMapping(value = "test/index", method = RequestMethod.GET)
    String index();
}
