package com.peng.user.provider;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author shipengju
 */
@SpringBootApplication
@EnableDubbo
@EnableDiscoveryClient
@MapperScan("com.peng.user.provider.mapper")
public class UserProviderApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(UserProviderApplication.class);
        application.run(args);
    }
}
