package com.wenjelly.smartpicturestorage.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wenjelly
 * @date 2025 2025/2/12 上午9:52
 * @description UserAddRequest
 */
@Data
public class UserAddRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 用户昵称
     */
    private String userName;
    /**
     * 账号
     */
    private String userAccount;
    /**
     * 用户头像
     */
    private String userAvatar;
    /**
     * 用户简介
     */
    private String userProfile;
    /**
     * 用户角色: user, admin
     */
    private String userRole;
}

