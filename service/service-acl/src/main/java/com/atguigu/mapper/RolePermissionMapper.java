package com.atguigu.mapper;

import com.atguigu.base.BaseMapper;
import com.atguigu.entity.RolePermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author HaQQ
 * @version 1.0
 * @date 2022/6/21 19:12
 */
public interface RolePermissionMapper {

     List<Long> findPermissionIdsByRoleId(Long roleId);

     void delete(@Param("roleId") Long roleId,@Param("removePermissionIdList") List<Long> removePermissionIdList);

     RolePermission findPermissionByRoleIdAndPermission(@Param("roleId") Long roleId,@Param("permissionId") Long permissionId);

     void insert(@Param("roleId") Long roleId,@Param("permissionId") Long permissionId);

     void update(RolePermission rolePermission);

}
