package com.wenjelly.smartpicturestorage.manager;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;
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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 图片/文件上传服务，用于上传图片到腾讯云对象存储，暴露给外面的接口
 *
 * @deprecated 已废弃，改为使用 upload 包的模板方法优化
 */
@Deprecated
@Service
@Slf4j
public class FileManager {

    @Resource
    private CosClientConfig cosClientConfig;

    @Resource
    private CosManager cosManager;

    /**
     * 上传图片
     *
     * @param multipartFile    图片文件
     * @param uploadPathPrefix 上传路径前缀
     * @return 上传结果
     */
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
     * 通过url上传图片
     *
     * @param fileUrl          图片url
     * @param uploadPathPrefix 上传路径前缀
     * @return 上传结果
     */
    public UploadPictureResult uploadPictureByUrl(String fileUrl, String uploadPathPrefix) {
        // 校验图片
        validPicture(fileUrl);
        // 图片上传地址
        String uuid = RandomUtil.randomString(16);
        String originFilename = FileUtil.mainName(fileUrl);
        String uploadFilename = String.format("%s_%s.%s", DateUtil.formatDate(new Date()), uuid,
                FileUtil.getSuffix(originFilename));
        String uploadPath = String.format("/%s/%s", uploadPathPrefix, uploadFilename);
        File file = null;
        try {
            // 创建临时文件
            file = File.createTempFile(uploadPath, null);
            // 从url上下载图片
            HttpUtil.downloadFile(fileUrl, file);
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
            log.error("下载图片失败", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "上传图片失败");
        } finally {
            this.deleteTempFile(file);
        }
    }

    /**
     * 校验通过 URL 下载的图片
     *
     * @param fileUrl 图片url
     */
    public void validPicture(String fileUrl) {
        /*
         * 注意 2 点：
         * 注意发送 HTTP 请求后，需要即时释放资源
         * 有些 URL 地址可能不支持通过 HEAD 请求访问，为了提高导入成功率，即使 HEAD 请求访问失败，也不会报错，并且不用执行后续的校验。仅对能获取到的信息进行校验。
         */
        ThrowUtils.throwIf(fileUrl == null, ErrorCode.PARAMS_ERROR, "文件不能为空");

        try {
            // 1.验证 URL 格式
            new URL(fileUrl);
        } catch (MalformedURLException e) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "文件地址格式不正确");
        }

        // 2.校验 URL 协议
        ThrowUtils.throwIf(fileUrl.startsWith("http://") || fileUrl.startsWith("https://"), ErrorCode.PARAMS_ERROR, "仅支持 HTTP 或 HTTPS 协议的文件地址");

        // 3.发送 HEAD 请求以验证文件是否存在
        HttpResponse response = null;

        try {
            response = HttpUtil.createRequest(Method.HEAD, fileUrl).execute();
            // 未正常返回，无需执行其他判断
            if (response.getStatus() != HttpStatus.HTTP_OK) {
                return;
            }
            // 4.校验文件类型
            String contentType = response.header("Content-Type");
            if (StrUtil.isNotBlank(contentType)) {
                // 允许的图片类型
                final List<String> ALLOW_CONTENT_TYPES = Arrays.asList("image/jpeg", "image/jpg", "image/png", "image/gif", "image/bmp", "image/webp");
                ThrowUtils.throwIf(!ALLOW_CONTENT_TYPES.contains(contentType.toLowerCase()),
                        ErrorCode.PARAMS_ERROR, "文件类型错误");
            }

            // 5.校验文件大小
            String contentLengthStr = response.header("Content-Length");
            if (StrUtil.isNotBlank(contentLengthStr)) {
                try {
                    long contentLength = Long.parseLong(contentLengthStr);
                    final long ONE_M = 1024 * 1024L;
                    ThrowUtils.throwIf(contentLength > 10 * ONE_M, ErrorCode.PARAMS_ERROR, "文件大小不能超过10M");
                } catch (NumberFormatException e) {
                    throw new BusinessException(ErrorCode.PARAMS_ERROR, "文件大小格式错误");
                }
            }
        } finally {
            if (response != null) {
                response.close();
            }
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
