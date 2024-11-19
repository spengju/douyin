package com.peng.common.redis;

import org.springframework.context.annotation.Configuration;

/**
 * @Author: spengju
 * @Slogan: Day day no bug.
 * @Date: 2024/11/19 22:51
 * @Desc:
 */
@Configuration
public class SMSCacheKeyBuilder extends RedisKeyBuilder{

    private static String SMS_LOGIN_CODE_KEY = "sms:login:code";

    public String buildSmsLoginCodeKey(String mobile) {
        return super.getPrefix() + SMS_LOGIN_CODE_KEY + super.getSplitItem() + mobile;
    }
}
