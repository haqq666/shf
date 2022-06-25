package com.atguigu.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.base.BaseMapper;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.entity.Role;
import com.atguigu.mapper.RoleMapper;
import com.atguigu.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author HaQQ
 * @version 1.0
 * @date 2022/6/13 0:16
 */
@Service(interfaceClass = RoleService.class)
@Transactional(propagation = Propagation.SUPPORTS)
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {
   @Autowired
    private RoleMapper roleMapper;
    @Override
    protected BaseMapper<Role> getEntityMapper() {
        return roleMapper;
    }
   @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Role> findAll() {
        return roleMapper.findAll();
    }


}





















