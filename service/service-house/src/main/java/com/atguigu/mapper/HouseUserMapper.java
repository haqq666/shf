package com.atguigu.mapper;

import com.atguigu.base.BaseMapper;
import com.atguigu.entity.HouseUser;

import java.util.List;

/**
 * @author HaQQ
 * @version 1.0
 * @date 2022/6/16 20:00
 */
public interface HouseUserMapper extends BaseMapper<HouseUser> {

    List<HouseUser> findHouseUserList(Long id);
}
