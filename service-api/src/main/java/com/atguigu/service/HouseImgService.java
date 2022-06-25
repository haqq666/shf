package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.HouseImage;

import java.util.List;

/**
 * @author HaQQ
 * @version 1.0
 * @date 2022/6/16 17:23
 */
public interface HouseImgService extends BaseService<HouseImage> {
    /**
     *
     * @param id 房源的id
     * @param type 房源的类型 1：房源图片，2房产图片
     * @return
     */
    List<HouseImage> findImageList(Long id,Integer type);
}
