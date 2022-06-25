package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.base.BaseMapper;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.entity.Permission;
import com.atguigu.entity.RolePermission;
import com.atguigu.helper.PermissionHelper;
import com.atguigu.mapper.PermissionMapper;
import com.atguigu.mapper.RolePermissionMapper;
import com.atguigu.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author HaQQ
 * @version 1.0
 * @date 2022/6/21 18:43
 */
@Service(interfaceClass = PermissionService.class)
public class PermissionServiceImpl extends BaseServiceImpl<Permission> implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Override
    protected BaseMapper<Permission> getEntityMapper() {
        return permissionMapper;
    }

    @Override
    public List<Map<String, Object>> findPermissionByRoleId(Long roleId) {
//      查找出所有的权限
        List<Permission> permissionList = permissionMapper.findAll();
//       找到这个角色拥有的权限的id
        List<Long> assignPermissionIdList = rolePermissionMapper.findPermissionIdsByRoleId(roleId);
//      将permissionList 转成map
        List<Map<String, Object>> zNodes = permissionList.stream()
                .map(permission -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", permission.getId());
                    map.put("pId", permission.getParentId());
                    map.put("name", permission.getName());
                    map.put("open", true);
                    map.put("checked", assignPermissionIdList.contains(permission.getId()));
                    return map;
                }).collect(Collectors.toList());
        return zNodes;
    }

    @Override
    public void saveRolePermission(Long roleId, List<Long> permissionIds) {

//        跟据roleId查找角色
        List<Long> permissionList = rolePermissionMapper.findPermissionIdsByRoleId(roleId);
//        删除需要删除的权限
        List<Long> removePermissionIdList = permissionList.stream()
                .filter(permissionId -> !permissionIds.contains(permissionId))
                .collect(Collectors.toList());
        if (!removePermissionIdList.isEmpty()) {
            rolePermissionMapper.delete(roleId, removePermissionIdList);
        }
//     找到需要增加的角色
        permissionIds.forEach(permissionId -> {
//           跟据roleId找到所有的权限
            RolePermission rolePermission = rolePermissionMapper.findPermissionByRoleIdAndPermission(roleId, permissionId);
            if (rolePermission == null) {
//              之前没有新增过这个权限 需要新增权限
                rolePermissionMapper.insert(roleId, permissionId);
            } else {
//               之前有个这个权限只是被删除了,将is_deleted重新设置为0即可
                if (rolePermission.getIsDeleted() == 1) {
                    rolePermission.setIsDeleted(0);
                    rolePermissionMapper.update(rolePermission);
                }
            }
        });


    }

    /**
     * 跟据用户的id查找权限列表
     *
     * @param adminId
     * @return
     */
    @Override
    public List<Permission> finMenuByAdminId(Long adminId) {

        List<Permission> permissionList = null;
        if (adminId == 1) {
//        如果用户id=1则用户是超级管理员，拥有所有权限
            permissionList = permissionMapper.findAll();
        } else {
//        如果用户 id！=1 则查询用户的权限列表
            permissionList = permissionMapper.findPermissionByAdminId(adminId);
        }
//        构建父子关系
        return PermissionHelper.build(permissionList);
    }

    public List<Permission> findAll() {
        List<Permission> allPermission = permissionMapper.findAll();
        //构建父子关系
        return PermissionHelper.build(allPermission);
    }

    @Override
    public List<String> findAllPermissionCode() {
        return permissionMapper.findAllPermissionCode();
    }

    @Override
    public List<String> findPermissionCodeListByAdminId(Long id) {
        return permissionMapper.findPermissionCodeListByAdminId(id);
    }
}
