package com.wenjelly.smartpicturestorage.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表实体类
 * TableName: user
 */
@TableName(value = "user")
@Data
public class User implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    /**
     * 账号
     */
    private String userAccount;
    /**
     * 密码
     */
    private String userPassword;
    /**
     * 用户昵称
     */
    private String userName;
    /**
     * 用户头像
     */
    private String userAvatar;
    /**
     * 用户简介
     */
    private String userProfile;
    /**
     * 用户角色：user/admin
     */
    private String userRole;
    /**
     * 编辑时间
     */
    private Date editTime;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 会员过期时间
     */
    private Date vipExpireTime;
    /**
     * 会员兑换码
     */
    private String vipCode;
    /**
     * 会员编号
     */
    private Long vipNumber;
    /**
     * 分享码
     */
    private String shareCode;
    /**
     * 邀请用户 id
     */
    private Long inviteUser;
    /**
     * 是否删除
     */
    @TableLogic
    private Integer isDelete;

}