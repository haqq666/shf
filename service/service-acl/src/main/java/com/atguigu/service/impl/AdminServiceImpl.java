package com.atguigu.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.base.BaseMapper;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.entity.Admin;
import com.atguigu.entity.AdminRole;
import com.atguigu.entity.Role;
import com.atguigu.mapper.AdminMapper;
import com.atguigu.mapper.AdminRoleMapper;
import com.atguigu.mapper.RoleMapper;
import com.atguigu.service.AdminService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @author HaQQ
 * @version 1.0
 * @date 2022/6/13 16:53
 */
@Service(interfaceClass = AdminService.class)
@Transactional(propagation = Propagation.SUPPORTS)
public class AdminServiceImpl extends BaseServiceImpl<Admin> implements AdminService {

    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private AdminRoleMapper adminRoleMapper;
    @Autowired
    private RoleMapper roleMapper;

    @Override
    protected BaseMapper<Admin> getEntityMapper() {
        return adminMapper;
    }

    @Override
    public List<Admin> findAll() {
        return adminMapper.findAll();
    }

    @Override
    public Map<String, List<Role>> findRolesByAdminId(Long id) {
//       找到所有
        List<Role> allRole = roleMapper.findAll();
//       找到用户的角色
        List<Long> roleList = adminRoleMapper.findRolesByAdminId(id);
//      找出包含的
        List<Role> assignRoleList = allRole.stream()
                .filter(role -> roleList.contains(role.getId()))
                .collect(Collectors.toList());
//      找出不包含的
        List<Role> unAssignRoleList = allRole.stream()
                .filter(role -> !roleList.contains(role.getId()))
                .collect(Collectors.toList());
//      放到map中
        Map<String, List<Role>> map = new HashMap<>();
        map.put("assignRoleList", assignRoleList);
        map.put("unAssignRoleList", unAssignRoleList);
        return map;
    }

    @Override
    public void saveAssignRole(Long adminId, List<Long> roleIds) {

//        找到原来拥有的角色
        List<Long> roleList = adminRoleMapper.findRolesByAdminId(adminId);
//        找到需要删除的角色：之前有这个角色现在没了
        List<Long> removeRoles = roleList.stream()
                .filter(roleId -> !roleIds.contains(roleId))
                .collect(Collectors.toList());
        //如果removeRoles为空则没有要删的，如果不为空，则要执行删除方法
        if (!CollectionUtils.isEmpty(removeRoles)){
            adminRoleMapper.delete(adminId,removeRoles);
        }
        //找到需要增加的角色
        roleIds.forEach(roleId-> {
            AdminRole adminRole = adminRoleMapper.findRolesByAdminIdAndRoleId(adminId, roleId);
            if (adminRole==null){
//                在列表里没找到这个角色，这个角色之前一直没有新加过
                adminRoleMapper.insert(adminId,roleId);
             }else {
//                找到需要修改的角色：之前有这个角色，之后删除了
                if (adminRole.getIsDeleted()==1){
                    adminRole.setIsDeleted(0);
                    adminRoleMapper.update(adminRole);
                }
            }
        });
    }

    @Override
    public Admin findAdminByUsername(String username) {
        return adminMapper.findAdminByUserName(username);
    }

}
