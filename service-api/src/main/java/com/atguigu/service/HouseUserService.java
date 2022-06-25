package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.HouseUser;

import java.util.List;

/**
 * @author HaQQ
 * @version 1.0
 * @date 2022/6/16 19:54
 */
public interface HouseUserService extends BaseService<HouseUser> {
    List<HouseUser>findHouseUserList(Long id);
}
