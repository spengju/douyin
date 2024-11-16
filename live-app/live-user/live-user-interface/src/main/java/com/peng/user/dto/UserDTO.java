package com.peng.user.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: spengju
 * @Slogan: Day day no bug.
 * @Date: 2024/11/16 23:08
 * @Desc:
 */
@Data
public class UserDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long userId;
    private String nickName;

    private String trueName;
    private String avatar;

    private Integer sex;

    private Date createTime;
    private Date updateTime;

}
