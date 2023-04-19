package com.xxm.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {


    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        System.out.println("hello user");
        return "hello user";
    }
}
