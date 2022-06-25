package com.atguigu.mapper;

import com.atguigu.base.BaseMapper;
import com.atguigu.entity.HouseImage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author HaQQ
 * @version 1.0
 * @date 2022/6/16 17:18
 */
public interface HouseImgMapper extends BaseMapper<HouseImage> {

    /**
     * 查找房源的所有图片，
     * @param id 房源的id
     * @param type 房源的类型 1：房源的图片 2：房产的图片
     * @return
     */
    List<HouseImage> findImageList(@Param("houseId") Long id, @Param("type") Integer type);
}
