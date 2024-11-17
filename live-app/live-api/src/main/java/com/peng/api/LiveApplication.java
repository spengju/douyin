package com.peng.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: spengju
 * @Slogan: Day day no bug.
 * @Date: 2024/11/16 12:02
 * @Desc:
 */
@SpringBootApplication
//@EnableDubbo
//@EnableDiscoveryClient
public class LiveApplication {
    public static void main(String[] args) {
        SpringApplication.run(LiveApplication.class,args);
    }
}
