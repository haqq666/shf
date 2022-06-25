package com.atguigu.mapper;

import com.atguigu.base.BaseMapper;
import com.atguigu.entity.Admin;

import java.util.List;

/**
 * @author HaQQ
 * @version 1.0
 * @date 2022/6/13 16:57
 */
public interface AdminMapper extends BaseMapper<Admin> {


    List<Admin> findAll();

    Admin findAdminByUserName(String username);
}
