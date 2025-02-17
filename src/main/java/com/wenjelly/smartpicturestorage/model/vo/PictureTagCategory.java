package com.wenjelly.smartpicturestorage.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PictureTagCategory implements Serializable {

    private List<String> tagList;

    private List<String> categoryList;
}
