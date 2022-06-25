package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.base.BaseMapper;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.entity.HouseImage;
import com.atguigu.mapper.HouseImgMapper;
import com.atguigu.service.HouseImgService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author HaQQ
 * @version 1.0
 * @date 2022/6/16 17:25
 */
@Service(interfaceClass = HouseImgService.class)
public class HouseImgServiceImpl extends BaseServiceImpl<HouseImage> implements HouseImgService {

    @Autowired
    private HouseImgMapper houseImgMapper;

    @Override
    protected BaseMapper<HouseImage> getEntityMapper() {
        return houseImgMapper;
    }

    /**
     * 查找房源的所有图片，
     * @param id 房源的id
     * @param type 房源的类型 1：房源的图片 2：房产的图片
     * @return
     */
    @Override
    public List<HouseImage> findImageList(Long id,Integer type) {
        List<HouseImage> all = houseImgMapper.findImageList(id,type);
        return all;
    }
}
