package com.wenjelly.smartpicturestorage.api.imagesearch;

import com.wenjelly.smartpicturestorage.api.imagesearch.model.ImageSearchResult;
import com.wenjelly.smartpicturestorage.api.imagesearch.sub.GetImageFirstUrlApi;
import com.wenjelly.smartpicturestorage.api.imagesearch.sub.GetImageListApi;
import com.wenjelly.smartpicturestorage.api.imagesearch.sub.GetImagePageUrlApi;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class ImageSearchApiFacade {
    /**
     * 搜索图片
     *
     * @param imageUrl
     * @return
     */
    public static List<ImageSearchResult> searchImage(String imageUrl) {
        String imagePageUrl = GetImagePageUrlApi.getImagePageUrl(imageUrl);
        String imageFirstUrl = GetImageFirstUrlApi.getImageFirstUrl(imagePageUrl);
        List<ImageSearchResult> imageList = GetImageListApi.getImageList(imageFirstUrl);
        return imageList;
    }

    public static void main(String[] args) {
        List<ImageSearchResult> imageList = searchImage("https://picture-storage-1325426290.cos.ap-guangzhou.myqcloud.com/public/1921565781396983809/2025-05-11_pKyEbTKtiajX7vTiwebp");
        System.out.println("结果列表" + imageList);
    }
}
