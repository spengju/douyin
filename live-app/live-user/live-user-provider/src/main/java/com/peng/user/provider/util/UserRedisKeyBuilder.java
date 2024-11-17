package com.peng.user.provider.util;

import org.springframework.context.annotation.Configuration;

/**
 * @Author: spengju
 * @Slogan: Day day no bug.
 * @Date: 2024/11/17 16:31
 * @Desc:
 */
@Configuration
public class UserRedisKeyBuilder {

    private static final String USER_INFO_KEY = "user:info:";

    public String buildUserInfoKey(Long userId) {
        return USER_INFO_KEY + userId;
    }
}
