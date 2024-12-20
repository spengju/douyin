package com.peng.user.provider.service;

import com.peng.common.redis.SMSCacheKeyBuilder;
import com.peng.live.util.ConvertBeanUtils;
import com.peng.user.dto.UserDTO;
import com.peng.user.provider.entity.SmsDO;
import com.peng.user.provider.entity.UserDO;
import com.peng.user.provider.mapper.SmsMapper;
import com.peng.user.provider.mapper.UserMapper;
import com.peng.user.provider.util.UserRedisKeyBuilder;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @Author: spengju
 * @Slogan: Day day no bug.
 * @Date: 2024/11/17 15:42
 * @Desc:
 */
@Slf4j
@Service
public class UserService {
    @Resource
    private UserRedisKeyBuilder keyBuilder;
    @Resource
    private SMSCacheKeyBuilder smsCacheKeyBuilder;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private UserMapper userMapper;
    @Resource
    private SmsMapper smsMapper;

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

    public boolean sendLoginCode(String mobile) {
        //参数校验
        if (null == mobile || mobile.isEmpty()) {
            return false;
        }
        //生成和手机号关联的RedisKey
        String smsCacheKey = smsCacheKeyBuilder.buildSmsLoginCodeKey(mobile);
        //从redis中查询有没有发送过验证码
        if (redisTemplate.hasKey(smsCacheKey)) {
            //如果有记录，表示已经发送过验证码，返回false
            log.info("手机号 {} 申请短信过于频繁", mobile);
            return false;
        }
        //如果没有就发送验证码,并将验证码保存到redis中
        int smsCode = new Random().nextInt(1000, 9999);
        log.info("生成验证码:{}", smsCode);
        redisTemplate.opsForValue().set(smsCacheKey, smsCode, 60, TimeUnit.SECONDS);
        //发送验证码
        boolean sendSms = sendSms(mobile, smsCode);
        //保存短信发送记录
        insertSmsRecord(mobile, smsCode);
        return true;
    }

    private boolean sendSms(String mobile, int smsCode) {
        return true;
    }

    private void insertSmsRecord(String mobile, int smsCode) {
        SmsDO smsDO = new SmsDO();
        //TODO: 生成记录ID
        smsDO.setCode(smsCode);
        smsDO.setPhone(mobile);
        smsMapper.insert(smsDO);
    }
}
