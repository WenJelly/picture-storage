package com.wenjelly.smartpicturestorage.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wenjelly.smartpicturestorage.model.SpaceUser;
import com.wenjelly.smartpicturestorage.model.dto.spaceuser.SpaceUserAddRequest;
import com.wenjelly.smartpicturestorage.model.dto.spaceuser.SpaceUserQueryRequest;
import com.wenjelly.smartpicturestorage.model.vo.spaceuser.SpaceUserVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface SpaceUserService extends IService<SpaceUser> {

    /**
     * 校验空间成员
     *
     * @param spaceUser 空间成员
     * @param add       是否添加
     */
    void validSpaceUser(SpaceUser spaceUser, boolean add);

    /**
     * 添加空间成员
     *
     * @param spaceUserAddRequest 空间成员添加请求
     * @return 空间成员 id
     */
    long addSpaceUser(SpaceUserAddRequest spaceUserAddRequest);

    /**
     * 查询空间成员封装
     *
     * @param spaceUserQueryRequest 空间成员查询请求
     * @return QueryWrapper<SpaceUser>
     */
    QueryWrapper<SpaceUser> getQueryWrapper(SpaceUserQueryRequest spaceUserQueryRequest);

    /**
     * 查询空间成员列表
     *
     * @param spaceUser 空间成员
     * @param request   请求
     * @return List<SpaceUserVO>
     */
    SpaceUserVO getSpaceUserVO(SpaceUser spaceUser, HttpServletRequest request);

    /**
     * 查询空间成员列表
     *
     * @param spaceUserList 空间成员列表
     * @return List<SpaceUserVO>
     */
    List<SpaceUserVO> getSpaceUserVOList(List<SpaceUser> spaceUserList);
}
