package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.Role;

import java.util.List;

/**
 * @author HaQQ
 * @version 1.0
 * @date 2022/6/13 0:15
 */
public interface RoleService extends BaseService<Role> {
    /**
     * 查全部
     * @return
     */
    List<Role> findAll();

}
