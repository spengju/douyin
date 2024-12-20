package com.peng.user.inter;

import com.peng.user.dto.UserDTO;

/**
 * @Author: spengju
 * @Slogan: Day day no bug.
 * @Date: 2024/11/16 23:07
 * @Desc:
 */

public interface IUserRPCService {

    UserDTO getUserById(Long userId);

    /**
     * 发送短信验证码
     * @param mobile
     * @return
     */
    boolean sendLoginCode(String mobile);
}
