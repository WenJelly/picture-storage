package com.wenjelly.smartpicturestorage.controller;

import com.wenjelly.smartpicturestorage.annotation.AuthCheck;
import com.wenjelly.smartpicturestorage.common.BaseResponse;
import com.wenjelly.smartpicturestorage.common.ErrorCode;
import com.wenjelly.smartpicturestorage.common.ResultUtils;
import com.wenjelly.smartpicturestorage.constant.UserConstant;
import com.wenjelly.smartpicturestorage.exception.BusinessException;
import com.wenjelly.smartpicturestorage.exception.ThrowUtils;
import com.wenjelly.smartpicturestorage.model.Space;
import com.wenjelly.smartpicturestorage.model.dto.space.SpaceUpdateRequest;
import com.wenjelly.smartpicturestorage.model.enums.SpaceLevelEnum;
import com.wenjelly.smartpicturestorage.model.vo.space.SpaceLevel;
import com.wenjelly.smartpicturestorage.service.SpaceService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/space")
public class SpaceController {
    /*
    扩展 todo
    1）删除空间时，关联删除空间内的图片
    2）管理员创建空间：管理员可以为指定用户创建空间。可以在创建空间时多传一个 userId 参数，但是要注意做好权限控制，仅管理员可以为别人创建空间。
    3）目前更新上传图片的逻辑还是存在一些问题的。比如更新图片时，并没有删除原有图片、也没有减少原有图片占用的空间和额度，可以通过事务中补充逻辑或者通过定时任务扫描删除。
     */

    @Resource
    private SpaceService spaceService;

    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateSpace(@RequestBody SpaceUpdateRequest spaceUpdateRequest) {
        if (spaceUpdateRequest == null || spaceUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 将实体类和 DTO 进行转换
        Space space = new Space();
        BeanUtils.copyProperties(spaceUpdateRequest, space);
        // 自动填充数据
        spaceService.fillSpaceBySpaceLevel(space);
        // 数据校验
        spaceService.validSpace(space, false);
        // 判断是否存在
        long id = spaceUpdateRequest.getId();
        Space oldSpace = spaceService.getById(id);
        ThrowUtils.throwIf(oldSpace == null, ErrorCode.NOT_FOUND_ERROR);
        // 操作数据库
        boolean result = spaceService.updateById(space);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    @GetMapping("/list/level")
    public BaseResponse<List<SpaceLevel>> listSpaceLevel() {
        List<SpaceLevel> spaceLevelList = Arrays.stream(SpaceLevelEnum.values()) // 获取所有枚举
                .map(spaceLevelEnum -> new SpaceLevel(
                        spaceLevelEnum.getValue(),
                        spaceLevelEnum.getText(),
                        spaceLevelEnum.getMaxCount(),
                        spaceLevelEnum.getMaxSize()))
                .collect(Collectors.toList());
        return ResultUtils.success(spaceLevelList);
    }
}
