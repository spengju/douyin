package com.peng.user.provider.service;

import com.peng.common.redis.SMSCacheKeyBuilder;
import com.peng.user.provider.entity.SmsDO;
import com.peng.user.provider.mapper.SmsMapper;
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
public class SmsService {

    @Resource
    private SMSCacheKeyBuilder smsCacheKeyBuilder;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private SmsMapper smsMapper;

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
    //TODO 发送短信验证码
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
