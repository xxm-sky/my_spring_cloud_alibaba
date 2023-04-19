package com.xxm.controller;

import com.xxm.feign.UserFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private UserFeign userFeign;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String managerIndex() {
        System.out.println("hello manage");
        return "manage hello";
    }


    @RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
    public String getUserInfo() {
        String index = userFeign.index();
        System.out.println("跨服务调用。。。。");
        return index;
    }


}
