package com.peng.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: spengju
 * @Slogan: Day day no bug.
 * @Date: 2024/11/16 22:44
 * @Desc:
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

}
