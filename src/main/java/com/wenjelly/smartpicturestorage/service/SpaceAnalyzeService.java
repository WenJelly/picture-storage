package com.wenjelly.smartpicturestorage.service;

import com.wenjelly.smartpicturestorage.model.Space;
import com.wenjelly.smartpicturestorage.model.User;
import com.wenjelly.smartpicturestorage.model.dto.space.analyze.*;
import com.wenjelly.smartpicturestorage.model.vo.space.analyze.*;

import java.util.List;

public interface SpaceAnalyzeService {
    /**
     * 获取空间使用分析数据
     *
     * @param spaceUsageAnalyzeRequest 空间使用分析请求
     * @param loginUser                登录用户
     * @return 空间使用分析响应
     */
    SpaceUsageAnalyzeResponse getSpaceUsageAnalyze(SpaceUsageAnalyzeRequest spaceUsageAnalyzeRequest, User loginUser);

    /**
     * 获取空间分类分析数据
     *
     * @param spaceCategoryAnalyzeRequest 空间分类分析请求
     * @param loginUser                   登录用户
     * @return 空间分类分析响应
     */
    List<SpaceCategoryAnalyzeResponse> getSpaceCategoryAnalyze(SpaceCategoryAnalyzeRequest spaceCategoryAnalyzeRequest, User loginUser);

    /**
     * 获取空间标签分析数据
     *
     * @param spaceTagAnalyzeRequest 空间标签分析请求
     * @param loginUser              登录用户
     * @return 空间标签分析响应
     */
    List<SpaceTagAnalyzeResponse> getSpaceTagAnalyze(SpaceTagAnalyzeRequest spaceTagAnalyzeRequest, User loginUser);

    /**
     * 获取空间大小分析数据
     *
     * @param spaceSizeAnalyzeRequest 空间大小分析请求
     * @param loginUser               登录用户
     * @return 空间大小分析响应
     */
    List<SpaceSizeAnalyzeResponse> getSpaceSizeAnalyze(SpaceSizeAnalyzeRequest spaceSizeAnalyzeRequest, User loginUser);

    /**
     * 用户上传行为分析
     *
     * @param spaceUserAnalyzeRequest 空间用户分析请求
     * @param loginUser               登录用户
     * @return 空间用户分析响应
     */
    List<SpaceUserAnalyzeResponse> getSpaceUserAnalyze(SpaceUserAnalyzeRequest spaceUserAnalyzeRequest, User loginUser);

    /**
     * 空间排名分析(仅管理员可用)
     *
     * @param spaceRankAnalyzeRequest 空间排名分析请求
     * @param loginUser               登录用户
     * @return 空间排名分析响应
     */
    List<Space> getSpaceRankAnalyze(SpaceRankAnalyzeRequest spaceRankAnalyzeRequest, User loginUser);
}
