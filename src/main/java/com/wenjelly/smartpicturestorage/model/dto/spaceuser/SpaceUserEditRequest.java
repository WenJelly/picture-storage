package com.wenjelly.smartpicturestorage.model.dto.spaceuser;


import lombok.Data;

import java.io.Serializable;

@Data
public class SpaceUserEditRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    private Long id;
    /**
     * 空间角色：viewer/editor/admin
     */
    private String spaceRole;
}
