package com.wenjelly.smartpicturestorage.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wenjelly
 * @date 2025 2025/2/12 上午9:52
 * @description UserUpdateRequest
 */
@Data
public class UserUpdateRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    private Long id;
    /**
     * 用户昵称
     */
    private String userName;
    /**
     * 用户头像
     */
    private String userAvatar;
    /**
     * 简介
     */
    private String userProfile;
    /**
     * 用户角色：user/admin
     */
    private String userRole;
}

