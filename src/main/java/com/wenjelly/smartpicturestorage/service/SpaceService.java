package com.wenjelly.smartpicturestorage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wenjelly.smartpicturestorage.model.Picture;
import com.wenjelly.smartpicturestorage.model.Space;
import com.wenjelly.smartpicturestorage.model.User;
import com.wenjelly.smartpicturestorage.model.dto.space.SpaceAddRequest;

public interface SpaceService extends IService<Space> {


    /**
     * 添加/编辑空间
     *
     * @param spaceAddRequest 空间添加请求
     * @param loginUser      登录用户
     * @return 空间 id
     */
    long addSpace(SpaceAddRequest spaceAddRequest, User loginUser);

    /**
     * 新增空间，或用于编辑空间，用add参加进行操作判断
     *
     * @param space 空间
     * @param add  是否新增
     */
    void validSpace(Space space, boolean add);

    /**
     * 根据空间级别自动填充数据
     *
     * @param space 空间
     */
    void fillSpaceBySpaceLevel(Space space);
}
