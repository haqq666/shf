package com.atguigu.mapper;

import com.atguigu.base.BaseMapper;
import com.atguigu.entity.Permission;

import java.util.List;

/**
 * @author HaQQ
 * @version 1.0
 * @date 2022/6/21 18:45
 */
public interface PermissionMapper extends BaseMapper<Permission> {
    /**
     * 查找全部权限
     * @return
     */
    List<Permission> findAll();

    /**
     * 跟据用户的id查找权限列表
     * @param adminId
     * @return
     */
    List<Permission> findPermissionByAdminId(Long adminId);

    List<String> findPermissionCodeListByAdminId(Long id);

    List<String> findAllPermissionCode();
}
