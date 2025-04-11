package com.wenjelly.smartpicturestorage.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wenjelly.smartpicturestorage.model.Space;
import com.wenjelly.smartpicturestorage.model.User;
import com.wenjelly.smartpicturestorage.model.dto.space.SpaceAddRequest;
import com.wenjelly.smartpicturestorage.model.dto.space.SpaceQueryRequest;
import com.wenjelly.smartpicturestorage.model.vo.space.SpaceVO;

import javax.servlet.http.HttpServletRequest;

public interface SpaceService extends IService<Space> {


    /**
     * 添加/编辑空间
     *
     * @param spaceAddRequest 空间添加请求
     * @param loginUser       登录用户
     * @return 空间 id
     */
    long addSpace(SpaceAddRequest spaceAddRequest, User loginUser);

    /**
     * 新增空间，或用于编辑空间，用add参加进行操作判断
     *
     * @param space 空间
     * @param add   是否新增
     */
    void validSpace(Space space, boolean add);

    /**
     * 根据空间级别自动填充数据
     *
     * @param space 空间
     */
    void fillSpaceBySpaceLevel(Space space);

    /**
     * 空间权限校验
     *
     * @param loginUser 登录用户
     * @param space     空间
     */
    void checkSpaceAuth(User loginUser, Space space);

    /**
     * 空间查询条件构造器
     *
     * @param spaceQueryRequest 空间查询请求
     * @return 查询结果
     */
    QueryWrapper<Space> getQueryWrapper(SpaceQueryRequest spaceQueryRequest);

    /**
     * VO封装
     *
     * @param space   空间
     * @param request 请求
     * @return SpaceVO
     */
    SpaceVO getSpaceVO(Space space, HttpServletRequest request);
}
