package com.peng.common.redis;

import org.springframework.beans.factory.annotation.Value;

/**
 * @Author: spengju
 * @Slogan: Day day no bug.
 * @Date: 2024/11/19 22:52
 * @Desc:
 */
public class RedisKeyBuilder {

    @Value("${spring.application.name}")
    private String applicationName;

    private static final String SPLIT_ITEM = ":";

    public String getSplitItem() {
        return SPLIT_ITEM;
    }

    public String getPrefix() {
        return applicationName + SPLIT_ITEM;
    }
}
