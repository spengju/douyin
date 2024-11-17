package com.peng.api.controller;

import com.peng.user.dto.UserDTO;
import com.peng.user.inter.IUserRPCService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: spengju
 * @Slogan: Day day no bug.
 * @Date: 2024/11/16 22:44
 * @Desc:
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);
    @DubboReference
    private IUserRPCService userRPCService;

    @GetMapping("/queryUser")
    public UserDTO getUserById(String userId) {
        logger.info("query user by id:{}", userId);
        return userRPCService.getUserById(1L);
    }

}
