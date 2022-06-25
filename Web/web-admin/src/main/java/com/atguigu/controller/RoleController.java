package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.atguigu.base.BaseController;
import com.atguigu.entity.Permission;
import com.atguigu.entity.Role;
import com.atguigu.service.PermissionService;
import com.atguigu.service.RoleService;
import com.github.pagehelper.PageInfo;
import com.qiniu.util.Json;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author HaQQ
 * @version 1.0
 * @date 2022/6/13 0:37
 */
@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {

    private final static String PAGE_INDEX = "role/index";
    private final static String LIST_ACTION = "redirect:/role";
    public static final String ROLE_ASSIGNSHOW = "role/assignShow";
    private final static String PAGE_EDIT = "role/edit";
    public static final String PAGE_CREATE = "role/create";

    @Reference
    private RoleService roleService;
    @Reference
    private PermissionService permissionService;

    @PreAuthorize("hasAuthority('role.show')")
    @RequestMapping
    public String index(Model model, @RequestParam Map<String,Object> filters){

        if (filters.get("pageNum") == null || filters.get("pageNum") == ""){
            filters.put("pageNum",1);
        }
        if (filters.get("pageSize") == null || filters.get("pageSize") == ""){
            filters.put("pageSize",10);
        }

        PageInfo<Role> pageInfo = roleService.findPage(filters);
        model.addAttribute("page",pageInfo);
        model.addAttribute("filters",filters);
        return PAGE_INDEX;
    }
    @PreAuthorize("hasAuthority('role.create')")
    @RequestMapping("/create")
    public String create(){
       return PAGE_CREATE;
    }

    @PreAuthorize("hasAuthority('role.create')")
    @RequestMapping("/save")
    public String save(Role role,Model model){
       roleService.insert(role);
       return successPage(model,"新增角色成功");
    }
    @PreAuthorize("hasAuthority('role.edit')")
    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Long id){
        Role role = roleService.getById(id);
        model.addAttribute("role",role);
       return PAGE_EDIT;
    }
    @PreAuthorize("hasAuthority('role.edit')")
    @RequestMapping("/update")
    public String update(Role role,Model model){
       roleService.update(role);
       return successPage(model,"角色修改成功");
    }

    @PreAuthorize("hasAuthority('role.delete')")
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
       roleService.delete(id);
       return LIST_ACTION;
    }

    /**
     * 显示权限分配页面，将所有权限返回给前端
     * @param roleId
     * @param model
     * @return
     */
    @PreAuthorize("hasAuthority('role.assign')")
    @GetMapping("assignShow/{roleId}")
    public String assignShow(@PathVariable("roleId")Long roleId,
                             Model model){
//        调用业务层
        List<Map<String, Object>> permissionMap = permissionService.findPermissionByRoleId(roleId);
        model.addAttribute("zNodes", JSON.toJSONString(permissionMap));
        model.addAttribute("roleId",roleId);
        return ROLE_ASSIGNSHOW;
    }

    /**
     * 保存角色的权限
     * @param roleId
     * @param permissionIds
     * @return
     */
    @PreAuthorize("hasAuthority('role.assign')")
    @PostMapping("/assignPermission")
    public String assignPermission(@RequestParam("roleId") Long roleId,
                                   @RequestParam("permissionIds") List<Long> permissionIds,
                                   Model model){

//        调用业务层的方法
        permissionService.saveRolePermission(roleId,permissionIds);

        return successPage(model,"角色权限修改成功");
    }




}
