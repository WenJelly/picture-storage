package com.wenjelly.smartpicturestorage.model.dto.space;

import lombok.Data;

import java.io.Serializable;

@Data
public class SpaceEditRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 空间 id
     */
    private Long id;
    /**
     * 空间名称
     */
    private String spaceName;
}

