package com.peng.user.provider.impl;

import com.peng.user.dto.UserDTO;
import com.peng.user.inter.IUserRPCService;
import com.peng.user.provider.service.UserService;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @Author: spengju
 * @Slogan: Day day no bug.
 * @Date: 2024/11/16 23:22
 * @Desc:
 */
@DubboService
public class UserRPCService implements IUserRPCService {
    @Resource
    private UserService userService;

    @Override
    public UserDTO getUserById(Long userId) {
        return userService.getUserById(userId);
    }
}
