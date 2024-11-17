package com.peng.user.provider.mysql;

import com.peng.user.provider.entity.UserDO;
import com.peng.user.provider.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: spengju
 * @Slogan: Day day no bug.
 * @Date: 2024/11/17 12:43
 * @Desc:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MySqlTest {
    Logger logger = LoggerFactory.getLogger(MySqlTest.class);
    @Resource
    private UserMapper userMapper;

    @Test
    public void test() {
        UserDO userDO = userMapper.selectById(1L);
        logger.info("------{}", userDO);
        logger.info("------{}", userDO);
        logger.info("------{}", userDO);
        logger.info("------{}", userDO);
        logger.info("------{}", userDO);
    }
}
