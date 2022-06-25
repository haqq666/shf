package com.atguigu.mapper;


import com.atguigu.entity.AdminRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author HaQQ
 * @version 1.0
 * @date 2022/6/21 10:46
 */
public interface AdminRoleMapper {
    /**
     * 跟据用户id查询角色列表
     * @param id
     * @return
     */
    List<Long> findRolesByAdminId(Long id);

    /**
     * 根据adminId和roleId删除角色
     * @param adminId
     * @param removeRoles
     */

    void delete(@Param("adminId") Long adminId, @Param("roleIds") List<Long> removeRoles);

    /**
     * 跟据adminId和roleId查找角色
     * @param adminId
     * @param roleId
     * @return
     */
    AdminRole findRolesByAdminIdAndRoleId(@Param("adminId") Long adminId, @Param("roleId") Long roleId);

    /**
     * 给用户新增角色
     * @param adminId
     * @param roleId
     */
    void insert(@Param("adminId") Long adminId, @Param("roleId") Long roleId);

    void update(AdminRole adminRole);
}
