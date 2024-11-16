package com.peng.api.controller;

import com.peng.user.dto.UserDTO;
import com.peng.user.inter.IUserRPCService;
import org.apache.dubbo.config.annotation.DubboReference;
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
@RequestMapping("/user")
public class UserController {

    @DubboReference
    private IUserRPCService userRPCService;

    @GetMapping("/queryUser")
    public UserDTO getUserById(Long userId) {

        return userRPCService.getUserById(userId);
    }

}
