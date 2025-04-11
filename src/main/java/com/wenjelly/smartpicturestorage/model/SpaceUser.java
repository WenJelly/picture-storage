package com.wenjelly.smartpicturestorage.model;

import lombok.Data;

@Data
public class SpaceUser {

    private Long id;

    private String spaceRole;
    private Long userId;
    private Long spaceId;
}
