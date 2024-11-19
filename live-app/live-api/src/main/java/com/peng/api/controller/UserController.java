package com.peng.api.controller;

import com.peng.api.entity.WebResDTO;
import com.peng.user.dto.UserDTO;
import com.peng.user.inter.IUserRPCService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: spengju
 * @Slogan: Day day no bug.
 * @Date: 2024/11/16 22:44
 * @Desc:
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);
    @DubboReference
    private IUserRPCService userRPCService;

    @GetMapping("/queryUser")
    public WebResDTO getUserById(Long userId) {
        logger.info("query user by id:{}", userId);
        UserDTO userDTO = userRPCService.getUserById(userId);
        if (null != userDTO) {
            return new WebResDTO(WebResDTO.SUCCESS_CODE, userDTO);
        } else {
            return new WebResDTO(WebResDTO.ERROR_CODE, "用户不存在");
        }
    }

    @PostMapping("/sendSMS")
    public WebResDTO sendSMS(String mobile) {
        if (!StringUtils.hasText(mobile)) {
            return new WebResDTO(WebResDTO.ERROR_CODE, "请求参数异常");
        }
        //调用远程服务发送验证码
        if (userRPCService.sendLoginCode(mobile)) {
            return new WebResDTO(WebResDTO.SUCCESS_CODE, "SUCCESS");
        } else {
            return new WebResDTO(WebResDTO.ERROR_CODE, "发送短信失败，请重试");
        }
    }

}
