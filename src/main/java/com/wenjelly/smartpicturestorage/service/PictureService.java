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
 * description: 针对表【picture(图片)】的数据库操作Service
 * createDate: 2025-02-17 14:07:25
 */
public interface PictureService extends IService<Picture> {

    /**
     * 校验图片
     *
     * @param picture 图片信息
     */
    void validPicture(Picture picture);

    /**
     * 上传图片
     *
     * @param inputSource          文件输入源
     * @param pictureUploadRequest 图片上传请求封装类
     * @param loginUser            目前登录的用户
     * @return 图片包装类
     */
    PictureVO uploadPicture(Object inputSource,
                            PictureUploadRequest pictureUploadRequest,
                            User loginUser);

    /**
     * 获取图片包装类（单条）
     *
     * @param picture 图片信息
     * @param request http请求
     * @return 图片包装类
     */
    PictureVO getPictureVO(Picture picture, HttpServletRequest request);

    /**
     * 获取图片包装类（分页）
     *
     * @param picturePage 图片分页对象请求封装类
     * @param request     http请求
     * @return 图片包装类分页内容
     */
    Page<PictureVO> getPictureVOPage(Page<Picture> picturePage, HttpServletRequest request);

    /**
     * 获取查询对象
     *
     * @param pictureQueryRequest 图片查询请求封装类
     * @return 查询对象
     */
    QueryWrapper<Picture> getQueryWrapper(PictureQueryRequest pictureQueryRequest);


    /**
     * 图片审核
     *
     * @param pictureReviewRequest 图片审核请求封装类
     * @param loginUser            目前登录的用户
     */
    void doPictureReview(PictureReviewRequest pictureReviewRequest, User loginUser);

    /**
     * 填充审核参数
     *
     * @param picture   图片信息
     * @param loginUser 目前登录的用户
     */
    void fillReviewParams(Picture picture, User loginUser);

    /**
     * 批量抓取和创建图片
     *
     * @param pictureUploadByBatchRequest 图片批量上传请求封装类
     * @param loginUser                   目前登录的用户
     * @return 成功创建的图片数
     */
    Integer uploadPictureByBatch(PictureUploadByBatchRequest pictureUploadByBatchRequest,
                                 User loginUser);

    /**
     * 清理图片文件
     *
     * @param oldPicture 旧图片信息
     */
    void clearPictureFile(Picture oldPicture);

    /**
     * 删除图片
     *
     * @param pictureId 图片id
     * @param loginUser 目前登录的用户
     */
    void deletePicture(long pictureId, User loginUser);

    /**
     * 编辑图片
     *
     * @param pictureEditRequest 图片编辑请求封装类
     * @param loginUser          目前登录的用户
     */
    void editPicture(PictureEditRequest pictureEditRequest, User loginUser);

    /**
     * 根据颜色搜索图片
     *
     * @param spaceId   空间id
     * @param picColor  图片主色调
     * @param loginUser 登录用户
     * @return 图片列表
     */
    List<PictureVO> searchPictureByColor(Long spaceId, String picColor, User loginUser);

    /**
     * 批量编辑图片
     *
     * @param pictureEditByBatchRequest 图片批量编辑请求封装类
     * @param loginUser                 目前登录的用户
     */
    void editPictureByBatch(PictureEditByBatchRequest pictureEditByBatchRequest, User loginUser);

    /**
     * 创建扩图任务
     *
     * @param createPictureOutPaintingTaskRequest 扩图任务请求封装类
     * @param loginUser                           目前登录的用户
     */
    CreateOutPaintingTaskResponse createPictureOutPaintingTask(CreatePictureOutPaintingTaskRequest createPictureOutPaintingTaskRequest, User loginUser);

    /**
     * 获取首页轮播图列表
     *
     * @return 首页轮播图列表
     */
    List<PictureVO> getHomePageBanner();
}
