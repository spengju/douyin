package com.peng.user.provider.service;

import com.peng.live.util.ConvertBeanUtils;
import com.peng.user.dto.UserDTO;
import com.peng.user.provider.entity.UserDO;
import com.peng.user.provider.mapper.UserMapper;
import com.peng.user.provider.util.UserRedisKeyBuilder;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @Author: spengju
 * @Slogan: Day day no bug.
 * @Date: 2024/11/17 15:42
 * @Desc:
 */
@Service
public class UserService {
    @Resource
    private UserRedisKeyBuilder keyBuilder;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private UserMapper userMapper;

    public static final String EMPTY_CACHE = "{}";

    public UserDTO getUserById(Long userId) {
        if (null == userId && userId < 0) {
            return null;
        }
        UserDTO userDTO;
        //从redis中查询
        String userInfoKey = keyBuilder.buildUserInfoKey(userId);
        userDTO = (UserDTO) redisTemplate.opsForValue().get(userInfoKey);
        //如果存在，则直接返回
        if (null != userDTO) {
            if (userDTO.getUserId() < 0) {
                return null;
            }
            return userDTO;
        }
        //redis中没有从数据库中查询,并且更新redis缓存
        UserDO userDO = userMapper.selectById(userId);
        if (null != userDO) {
            userDTO = ConvertBeanUtils.convert(userDO, UserDTO.class);
            redisTemplate.opsForValue().set(userInfoKey, userDTO, 30, TimeUnit.MINUTES);
            return userDTO;
        } else {
            //缓存击穿: 1.布隆过滤器 2.设置空值
            //设置空值
            UserDTO notExitUser = new UserDTO();
            notExitUser.setUserId(-1L);
            redisTemplate.opsForValue().set(userInfoKey, notExitUser, 30, TimeUnit.SECONDS);
            return null;
        }
    }
}
