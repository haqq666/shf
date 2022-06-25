package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.Admin;
import com.atguigu.entity.Role;

import java.util.List;
import java.util.Map;


/**
 * @author HaQQ
 * @version 1.0
 * @date 2022/6/13 16:53
 */
public interface AdminService extends BaseService<Admin> {

    List<Admin> findAll();

    Map<String, List<Role>> findRolesByAdminId(Long id);

    void saveAssignRole(Long adminId, List<Long> roles);

    Admin findAdminByUsername(String username);
}
