package com.atguigu.mapper;

import com.atguigu.base.BaseMapper;
import com.atguigu.entity.House;
import com.atguigu.entity.bo.HouseQueryBo;
import com.atguigu.entity.vo.HouseVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author HaQQ
 * @version 1.0
 * @date 2022/6/16 9:35
 */
public interface HouseMapper extends BaseMapper<House> {
    /**
     * 通过小区id查找房源
     * @param id 小区id
     * @return
     */
    List<House> getByCommunityId(Long id);


    Page<HouseVo> findListPage(HouseQueryBo houseQueryBo);
}
