package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.Permission;
import com.atguigu.service.PermissionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;

/**
 * @author HaQQ
 * @version 1.0
 * @date 2022/6/22 2:00
 */
@Controller
@RequestMapping("/permission")
public class PermissionController extends BaseController {
    public static final String PAGE_INDEX = "permission/index";
    public static final String PAGE_CREATE = "permission/create";
    public static final String REDIRECT_PERMISSION = "redirect:/permission";
    public static final String PAGE_EDIT = "permission/edit";

    @Reference
    private PermissionService permissionService;

    /**
     * 查询所有的菜单并存到请求域
     * @param model
     * @return
     */
    @RequestMapping
    public String index(Model model){

        List<Permission> menu = permissionService.findAll();
        model.addAttribute("list",menu);
       return PAGE_INDEX;
    }

    /**
     * 新建菜单
     * @param permission
     * @param model
     * @return
     */
    @GetMapping("/create")
    public String create(Permission permission,Model model){
       model.addAttribute("permission",permission);
       return PAGE_CREATE;
    }

    /**
     * 保存新增
     * @param permission
     * @param model
     * @return
     */
    @PostMapping("/save")
    public String save(Permission permission,Model model){
        permissionService.insert(permission);
       return successPage(model,"新增菜单成功");
    }

    /**
     * 删除
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id,Model model){
       permissionService.delete(id);
       return REDIRECT_PERMISSION;
    }

    /**
     * 修改页面。数据回显
     * @param id
     * @return
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id,Model model){
        Permission permission = permissionService.getById(id);
        model.addAttribute("permission",permission);
        return PAGE_EDIT;
    }

    @RequestMapping("/update")
    public String update(Permission permission,Model model){
       permissionService.update(permission);
       return successPage(model,"权限修改成功");
    }

}
