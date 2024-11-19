package com.peng.api.entity;

import lombok.Data;

/**
 * @Author: spengju
 * @Slogan: Day day no bug.
 * @Date: 2024/11/17 22:05
 * @Desc:
 */
@Data
public class WebResDTO {

    //成功响应码
    public static final int SUCCESS_CODE=200;
    //失败响应码
    public static final int ERROR_CODE=500;

    private int code = 0;
    private Object data;


    public WebResDTO(int code, Object data) {
        this.code = code;
        this.data = data;
    }
}
