package com.peng.user.provider.impl;

import com.peng.user.dto.UserDTO;
import com.peng.user.inter.IUserRPCService;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @Author: spengju
 * @Slogan: Day day no bug.
 * @Date: 2024/11/16 23:22
 * @Desc:
 */
@DubboService
public class UserRPCService implements IUserRPCService {
    @Override
    public UserDTO getUserById(Long userId) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(1L);
        userDTO.setNickName("test");
        userDTO.setTrueName("spengju");
        userDTO.setAvatar("/img/avatar.png");
        return userDTO;
    }
}
