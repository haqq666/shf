package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.Permission;
import java.util.List;
import java.util.Map;

/**
 * @author HaQQ
 * @version 1.0
 * @date 2022/6/21 18:42
 */
public interface PermissionService extends BaseService<Permission> {

    List<Map<String,Object>> findPermissionByRoleId(Long roleId);

    void saveRolePermission(Long roleId, List<Long> permissionIds);

    List<Permission> finMenuByAdminId(Long adminId);

    List<Permission> findAll();

    List<String> findPermissionCodeListByAdminId(Long id);

    List<String> findAllPermissionCode();

}
