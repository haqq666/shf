package com.atguigu.mapper;

import com.atguigu.base.BaseMapper;
import com.atguigu.entity.Role;

import java.util.List;

/**
 * @author HaQQ
 * @version 1.0
 * @date 2022/6/13 0:09
 */
public interface RoleMapper extends BaseMapper<Role> {
    /**
     * 查询所有
     * @return
     */
    List<Role> findAll();


}
