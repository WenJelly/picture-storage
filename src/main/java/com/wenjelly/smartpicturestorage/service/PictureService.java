package com.wenjelly.smartpicturestorage.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wenjelly.smartpicturestorage.api.aliyunai.model.CreateOutPaintingTaskResponse;
import com.wenjelly.smartpicturestorage.model.Picture;
import com.wenjelly.smartpicturestorage.model.User;
import com.wenjelly.smartpicturestorage.model.dto.picture.*;
import com.wenjelly.smartpicturestorage.model.vo.picutre.PictureVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author 14456
 * @description 针对表【picture(图片)】的数据库操作Service
 * @createDate 2025-02-17 14:07:25
 */
public interface PictureService extends IService<Picture> {

    /**
     * 上传图片
     *
     * @param inputSource          图片文件/URL
     * @param pictureUploadRequest 图片上传请求
     * @param loginUser            登录用户
     * @return 图片信息
     */
    PictureVO uploadPicture(Object inputSource, PictureUploadRequest pictureUploadRequest, User loginUser);

    /**
     * 查询图片
     *
     * @param pictureQueryRequest 图片查询请求
     * @return 图片信息
     */
    QueryWrapper<Picture> getQueryWrapper(PictureQueryRequest pictureQueryRequest);

    /**
     * 为图片添加用户关联信息
     *
     * @param picture 图片类
     * @param request 请求
     * @return 图片信息
     */
    PictureVO getPictureVO(Picture picture, HttpServletRequest request);

    /**
     * 分页获取图片封装
     *
     * @param picturePage 分页
     * @param request     请求
     * @return 图片信息（分页）
     */
    Page<PictureVO> getPictureVOPage(Page<Picture> picturePage, HttpServletRequest request);

    /**
     * 校验图片
     *
     * @param picture 图片
     */
    void validPicture(Picture picture);

    /**
     * 图片审核
     *
     * @param pictureReviewRequest 图片审核请求
     * @param loginUser            登录用户
     */
    void doPictureReview(PictureReviewRequest pictureReviewRequest, User loginUser);

    /**
     * 填充审核参数
     *
     * @param picture   图片
     * @param loginUser 登录用户
     */
    void fillReviewParams(Picture picture, User loginUser);

    /**
     * 批量抓取和创建图片
     *
     * @param pictureUploadByBatchRequest 图片批量抓取请求（在网页中抓取）
     * @param loginUser                   登录用户
     * @return 图片数量
     */
    Integer uploadPictureByBatch(PictureUploadByBatchRequest pictureUploadByBatchRequest, User loginUser);

    /**
     * 清理图片文件
     *
     * @param oldPicture 旧图片
     */
    void clearPictureFile(Picture oldPicture);

    /**
     * 校验空间权限
     *
     * @param loginUser 登录用户
     * @param picture   图片
     */
    void checkPictureAuth(User loginUser, Picture picture);

    /**
     * 删除图片
     *
     * @param pictureId 图片 id
     * @param loginUser 登录用户
     */
    void deletePicture(long pictureId, User loginUser);

    /**
     * 编辑图片
     *
     * @param pictureEditRequest 图片编辑请求
     * @param loginUser          登录用户
     */
    void editPicture(PictureEditRequest pictureEditRequest, User loginUser);

    /**
     * 根据图片主色调搜索图片
     *
     * @param spaceId   图片空间id
     * @param picColor  图片主色调
     * @param loginUser 登录用户
     * @return 搜索完成的图片列表
     */
    List<PictureVO> searchPictureByColor(Long spaceId, String picColor, User loginUser);


    /**
     * 批量编辑图片
     *
     * @param pictureEditByBatchRequest 图片批量编辑请求
     * @param loginUser                 登录用户
     */
    void editPictureByBatch(PictureEditByBatchRequest pictureEditByBatchRequest, User loginUser);

    void batchEditPictureMetadata(PictureEditByBatchRequest pictureEditByBatchRequest, User loginUser);

    /**
     * AI 扩图
     *
     * @param createPictureOutPaintingTaskRequest 扩图请求
     * @param loginUser                           登录用户
     * @return 扩图任务响应
     */
    CreateOutPaintingTaskResponse createPictureOutPaintingTask(CreatePictureOutPaintingTaskRequest createPictureOutPaintingTaskRequest, User loginUser);


}
