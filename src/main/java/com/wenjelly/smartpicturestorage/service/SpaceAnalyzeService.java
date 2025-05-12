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
     * @param spaceUsageAnalyzeRequest 空间使用分析请求封装类
     * @param loginUser                目前登录的用户
     * @return 空间使用分析结果
     */
    SpaceUsageAnalyzeResponse getSpaceUsageAnalyze(SpaceUsageAnalyzeRequest spaceUsageAnalyzeRequest, User loginUser);

    /**
     * 获取空间分类分析数据
     *
     * @param spaceCategoryAnalyzeRequest 空间分类分析请求封装类
     * @param loginUser                   目前登录的用户
     * @return 空间分类分析结果
     */
    List<SpaceCategoryAnalyzeResponse> getSpaceCategoryAnalyze(SpaceCategoryAnalyzeRequest spaceCategoryAnalyzeRequest, User loginUser);

    /**
     * 获取空间标签分析数据
     *
     * @param spaceTagAnalyzeRequest 空间标签分析请求封装类
     * @param loginUser              目前登录的用户
     * @return 空间标签分析结果
     */
    List<SpaceTagAnalyzeResponse> getSpaceTagAnalyze(SpaceTagAnalyzeRequest spaceTagAnalyzeRequest, User loginUser);

    /**
     * 获取空间大小分析数据
     *
     * @param spaceSizeAnalyzeRequest 空间大小分析请求封装类
     * @param loginUser               目前登录的用户
     * @return 空间大小分析结果
     */
    List<SpaceSizeAnalyzeResponse> getSpaceSizeAnalyze(SpaceSizeAnalyzeRequest spaceSizeAnalyzeRequest, User loginUser);

    /**
     * 用户上传行为分析
     *
     * @param spaceUserAnalyzeRequest 空间用户分析请求封装类
     * @param loginUser               目前登录的用户
     * @return 空间用户分析结果
     */
    List<SpaceUserAnalyzeResponse> getSpaceUserAnalyze(SpaceUserAnalyzeRequest spaceUserAnalyzeRequest, User loginUser);

    /**
     * 空间排名分析(仅管理员可用)
     *
     * @param spaceRankAnalyzeRequest 空间排名分析请求封装类
     * @param loginUser               目前登录的用户
     * @return 空间排名分析结果
     */
    List<Space> getSpaceRankAnalyze(SpaceRankAnalyzeRequest spaceRankAnalyzeRequest, User loginUser);
}
