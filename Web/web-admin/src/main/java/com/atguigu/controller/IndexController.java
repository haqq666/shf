package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Admin;
import com.atguigu.entity.Permission;
import com.atguigu.service.AdminService;
import com.atguigu.service.PermissionService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * @author HaQQ
 * @version 1.0
 * @date 2022/6/22 0:14
 */
@Controller
public class IndexController {
    public static final String PAGE_INDEX = "frame/index";
    @Reference
    private AdminService adminService;
    @Reference
    private PermissionService permissionService;

    @RequestMapping("/")
    public String index(Model model){
//       获取当前登录的用户信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)authentication.getPrincipal();
        Admin admin = adminService.findAdminByUsername(user.getUsername());
//        将角色信息保存到请求域
        model.addAttribute("admin",admin);
//        调用业务层查询当前用户的权限菜单
        List<Permission> permissionList = permissionService.finMenuByAdminId(admin.getId());
        model.addAttribute("permissionList",permissionList);

        return PAGE_INDEX;
    }

}
