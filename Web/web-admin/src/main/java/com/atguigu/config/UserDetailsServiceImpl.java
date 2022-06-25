package com.atguigu.config;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Admin;
import com.atguigu.service.AdminService;
import com.atguigu.service.PermissionService;
import com.github.pagehelper.util.StringUtil;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author HaQQ
 * @version 1.0
 * @date 2022/6/22 16:46
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Reference
    private AdminService adminService;
    @Reference
    private PermissionService permissionService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       Admin admin = adminService.findAdminByUsername(username);
       if (admin == null){
           throw new UsernameNotFoundException("用户名不存在！");
       }
//       获取用户的权限列表
        List<String> permissionCodeList = null;
        if (admin.getId()==1){
            permissionCodeList =permissionService.findAllPermissionCode();
        }else {
             permissionCodeList = permissionService.findPermissionCodeListByAdminId(admin.getId());
        }
        List<SimpleGrantedAuthority> grantedAuthorityList = permissionCodeList.stream()
                .filter(permissionCode -> !StringUtils.isEmpty(permissionCode))
                .map(SimpleGrantedAuthority::new).collect(Collectors.toList());
//       校验密码
        return new User(username, admin.getPassword(),grantedAuthorityList);
    }
}
