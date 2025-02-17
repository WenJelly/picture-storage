package com.wenjelly.smartpicturestorage.manager;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.RandomUtil;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.model.ciModel.persistence.ImageInfo;
import com.wenjelly.smartpicturestorage.common.ErrorCode;
import com.wenjelly.smartpicturestorage.config.CosClientConfig;
import com.wenjelly.smartpicturestorage.exception.BusinessException;
import com.wenjelly.smartpicturestorage.exception.ThrowUtils;
import com.wenjelly.smartpicturestorage.model.dto.file.UploadPictureResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 图片上传服务，用于上传图片到腾讯云对象存储，暴露给外面的接口
 */
@Service
@Slf4j
public class FileManager {

    @Resource
    private CosClientConfig cosClientConfig;

    @Resource
    private CosManager cosManager;


    public UploadPictureResult uploadPicture(MultipartFile multipartFile, String uploadPathPrefix) {
        // 校验图片
        validPicture(multipartFile);

        // 图片上传地址
        String pictureUUId = RandomUtil.randomString(16);
        String originFilename = multipartFile.getOriginalFilename();
        String uploadFilename = String.format("%s_%s.%s", DateUtil.formatDate(new Date()), pictureUUId,
                FileUtil.getSuffix(originFilename));
        String uploadPath = String.format("/%s/%s", uploadPathPrefix, uploadFilename);
        File file = null;

        try {
            // 创建临时文件
            File tempFile = File.createTempFile(uploadPath, null);
            multipartFile.transferTo(file);
            // 上传图片
            PutObjectResult putObjectResult = cosManager.putPictureObject(uploadPath, file);
            ImageInfo imageInfo = putObjectResult.getCiUploadResult().getOriginalInfo().getImageInfo();
            // 构建返回结果
            UploadPictureResult uploadPictureResult = new UploadPictureResult();
            int width = imageInfo.getWidth();
            int height = imageInfo.getHeight();
            double picScale = NumberUtil.round(width * 1.0 / height, 2).doubleValue();
            uploadPictureResult.setPicName(FileUtil.mainName(originFilename));
            uploadPictureResult.setPicHeight(height);
            uploadPictureResult.setPicWidth(width);
            uploadPictureResult.setPicScale(picScale);
            uploadPictureResult.setPicFormat(imageInfo.getFormat());
            uploadPictureResult.setPicSize(FileUtil.size(file));
            uploadPictureResult.setUrl(cosClientConfig.getHost() + "/" + uploadPath);
            return uploadPictureResult;
        } catch (IOException e) {
            log.error("上传图片失败", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "上传图片失败");
        } finally {
            this.deleteTempFile(file);
        }
    }

    /**
     * 校验文件
     *
     * @param multipartFile 文件
     */
    public void validPicture(MultipartFile multipartFile) {
        ThrowUtils.throwIf(multipartFile == null, ErrorCode.PARAMS_ERROR, "文件不能为空");
        // 1.校验文件大小
        final long ONE_M = 1024 * 1024L;
        long fileSize = multipartFile.getSize();
        ThrowUtils.throwIf(fileSize > 10 * ONE_M, ErrorCode.PARAMS_ERROR, "文件大小不能超过10M");
        // 2.校验文件后缀
        String fileSuffix = FileUtil.getSuffix(multipartFile.getOriginalFilename());
        // 允许的文件格式列表
        final List<String> ALLOW_FORMAT_LIST = Arrays.asList("jpg", "jpeg", "png", "gif", "bmp", "webp");
        ThrowUtils.throwIf(!ALLOW_FORMAT_LIST.contains(fileSuffix), ErrorCode.PARAMS_ERROR, "文件格式不支持");
    }

    public void deleteTempFile(File file) {
        if (file == null) {
            return;
        }
        // 删除临时文件
        boolean deleteResult = file.delete();
        if (!deleteResult) {
            log.error("file delete error,filepath = {}", file.getAbsolutePath());
        }
    }
}
