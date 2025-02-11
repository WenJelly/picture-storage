package com.wenjelly.smartpicturestorage.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wenjelly.smartpicturestorage.model.model.User;

/**
* @author 14456
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2025-02-11 15:33:27
*/
public interface UserService extends IService<User> {

    /**
     * 用户注册
     *
     * @param userAccount   用户账户
     * @param userPassword  用户密码
     * @param checkPassword 校验密码
     * @return 新用户 id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);

}
