package com.wenjelly.smartpicturestorage.model.dto.picture;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wenjelly
 * @date 2025 2025/2/18 上午10:30
 * @description PictureReviewRequest
 */
@Data
public class PictureReviewRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    private Long id;
    /**
     * 状态：0-待审核, 1-通过, 2-拒绝
     */
    private Integer reviewStatus;
    /**
     * 审核信息
     */
    private String reviewMessage;
}

