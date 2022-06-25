package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.House;
import com.atguigu.entity.bo.HouseQueryBo;
import com.atguigu.entity.vo.HouseVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author HaQQ
 * @version 1.0
 * @date 2022/6/16 9:30
 */
public interface HouseService extends BaseService<House> {
    /**
     * 通过小区id获得房源列表
     * @param id
     * @return
     */
    List<House> getByCommunityId(Long id);

    /**
     * 发布
     * @param id
     * @param status
     */
    void publish(Long id,Integer status);

    /**
     * 前端分页查询
     * @param pageNum
     * @param pageSize
     * @param houseQueryBo
     * @return
     */
    PageInfo<HouseVo> findListPage(Integer pageNum, Integer pageSize, HouseQueryBo houseQueryBo);
}
