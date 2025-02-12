package com.wenjelly.smartpicturestorage.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wenjelly
 * @date 2025 2025/2/12 上午9:00
 * @description UserLoginRequest
 */
@Data
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 密码
     */
    private String userPassword;
}

