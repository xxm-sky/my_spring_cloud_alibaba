package com.xxm.controller;

import com.xxm.annotation.PassToken;
import com.xxm.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private UserService userService;


    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        System.out.println("hello user");
        return "hello user";
    }

    @PassToken
    @RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
    public Map<String, Object> getUserInfo(Integer id) {
        return userService.getUserInfo(id);
    }


}
