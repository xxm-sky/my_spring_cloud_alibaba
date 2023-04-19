package com.xxm.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "manager-server", fallbackFactory = FeignServiceFallBack.class)
public interface ManagerFeign {

    @RequestMapping(value = "test/index", method = RequestMethod.GET)
    String index();
}
