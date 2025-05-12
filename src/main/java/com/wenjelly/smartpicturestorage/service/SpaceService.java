package com.wenjelly.smartpicturestorage.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wenjelly.smartpicturestorage.model.Space;
import com.wenjelly.smartpicturestorage.model.User;
import com.wenjelly.smartpicturestorage.model.dto.space.SpaceAddRequest;
import com.wenjelly.smartpicturestorage.model.dto.space.SpaceQueryRequest;
import com.wenjelly.smartpicturestorage.model.vo.space.SpaceVO;

import javax.servlet.http.HttpServletRequest;

public interface SpaceService extends IService<Space> {


    /**
     * 创建空间
     *
     * @param spaceAddRequest 空间创建请求封装类
     * @param loginUser       目前登录的用户
     * @return 空间 id
     */
    long addSpace(SpaceAddRequest spaceAddRequest, User loginUser);

    /**
     * 删除空间
     *
     * @param spaceId   空间id
     * @param loginUser 目前登录的用户
     */
    void deleteSpace(long spaceId, User loginUser);

    /**
     * 校验对操作空间的合法性，通过add判断是新增还是编辑操作
     *
     * @param space 空间信息
     * @param add   是否新增
     */
    void validSpace(Space space, boolean add);

    /**
     * 根据空间级别自动填充数据
     *
     * @param space 空间信息
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
     * @param spaceQueryRequest 空间查询请求封装类
     * @return 查询结果
     */
    QueryWrapper<Space> getQueryWrapper(SpaceQueryRequest spaceQueryRequest);

    /**
     * VO封装
     *
     * @param space   空间信息
     * @param request http请求
     * @return SpaceVO
     */
    SpaceVO getSpaceVO(Space space, HttpServletRequest request);

    /**
     * 分页获取空间封装
     *
     * @param spacePage 分页封装类
     * @param request   http请求
     * @return 分页查询结果
     */
    Page<SpaceVO> getSpaceVOPage(Page<Space> spacePage, HttpServletRequest request);
}
