package com.wenjelly.smartpicturestorage.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wenjelly.smartpicturestorage.common.ErrorCode;
import com.wenjelly.smartpicturestorage.exception.BusinessException;
import com.wenjelly.smartpicturestorage.exception.ThrowUtils;
import com.wenjelly.smartpicturestorage.mapper.SpaceMapper;
import com.wenjelly.smartpicturestorage.model.Space;
import com.wenjelly.smartpicturestorage.model.User;
import com.wenjelly.smartpicturestorage.model.dto.space.SpaceAddRequest;
import com.wenjelly.smartpicturestorage.model.enums.SpaceLevelEnum;
import com.wenjelly.smartpicturestorage.service.SpaceService;
import com.wenjelly.smartpicturestorage.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class SpaceServiceImpl extends ServiceImpl<SpaceMapper, Space>
        implements SpaceService {

    @Resource
    private TransactionTemplate transactionTemplate;

    @Resource
    private UserService userService;

    @Override
    public long addSpace(SpaceAddRequest spaceAddRequest, User loginUser) {
        /*
        使用本地 synchronized 锁对 userId 进行加锁，这样不同的用户可以拿到不同的锁，对性能的影响较低。
        在加锁的代码中，我们使用 Spring 的 编程式事务管理器 transactionTemplate 封装跟数据库有关的查询和插入操作，
        而不是使用 @Transactional 注解来控制事务，这样可以保证事务的提交在加锁的范围内。
         */
        // 在此处进行实体类和DTO的转换
        Space space = new Space();
        BeanUtils.copyProperties(spaceAddRequest, space);
        // 默认值
        if (StrUtil.isBlank(spaceAddRequest.getSpaceName())) {
            space.setSpaceName("默认空间");
        }
        if (spaceAddRequest.getSpaceLevel() == null) {
            space.setSpaceLevel(SpaceLevelEnum.COMMON.getValue());
        }

        // 填充数据
        this.fillSpaceBySpaceLevel(space);
        // 数据校验
        this.validSpace(space, true);
        Long userId = loginUser.getId();
        space.setUserId(userId);
        // 权限校验
        if (SpaceLevelEnum.COMMON.getValue() != spaceAddRequest.getSpaceLevel() && !userService.isAdmin(loginUser)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "无权限创建指定级别的空间");
        }

        // 针对用户进行加锁 - 使用 ConcurrentHashMap 来存储对象对本地锁进行优化
        // String lock = String.valueOf(userId).intern();
        ConcurrentHashMap<Long, Object> lockMap = new ConcurrentHashMap<>();
        Object lock = lockMap.computeIfAbsent(userId, key -> new Object());
        synchronized (lock) {
            Long newSpaceId = transactionTemplate.execute(status -> {
                boolean exists = this.lambdaQuery().eq(Space::getUserId, userId).exists();
                ThrowUtils.throwIf(exists, ErrorCode.OPERATION_ERROR, "每个用户仅能拥有一个私有空间");
                // 写入数据库
                boolean result = this.save(space);
                ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
                // 返回新写入的数据 id
                return space.getId();
            });
            // 返回结果是包装类，可以做一些处理
            return Optional.ofNullable(newSpaceId).orElse(-1L);
        }
        /*
        扩展
            1）用户注册成功时，可以自动创建空间。即使创建失败了，也可以手动创建作为兜底。
            2）管理员可以为某个用户创建空间（目前没啥必要）
            3）本地锁改为分布式锁，可以基于 Redisson 实现。
         */
    }

    @Override
    public void validSpace(Space space, boolean add) {
        ThrowUtils.throwIf(space == null, ErrorCode.PARAMS_ERROR);
        // 从对象中取值
        String spaceName = space.getSpaceName();
        Integer spaceLevel = space.getSpaceLevel();
        SpaceLevelEnum spaceLevelEnum = SpaceLevelEnum.getEnumByValue(spaceLevel);

        // 如果是创建空间
        if (add) {
            if (StrUtil.isBlank(spaceName)) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "空间名不能为空");
            }
            if (spaceLevel == null) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "空间级别不能为空");
            }
        }

        // 修改数据时，如果要改空间级别
        if (spaceLevel != null && spaceLevelEnum == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "空间级别不存在");
        }
        // 如果是修改空间名称
        if (StrUtil.isNotBlank(spaceName) && spaceName.length() > 30) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "空间名称过长");
        }
    }
    @Override
    public void fillSpaceBySpaceLevel(Space space) {
        // 如果空间本身没有设置限额，才会自动填充，保证了灵活性。
        // 根据空间级别，自动填充限额
        SpaceLevelEnum spaceLevelEnum = SpaceLevelEnum.getEnumByValue(space.getSpaceLevel());
        if (spaceLevelEnum != null) {
            long maxSize = spaceLevelEnum.getMaxSize();
            if (space.getMaxSize() == null) {
                space.setMaxSize(maxSize);
            }
            long maxCount = spaceLevelEnum.getMaxCount();
            if (space.getMaxCount() == null) {
                space.setMaxCount(maxCount);
            }
        }
    }
}
