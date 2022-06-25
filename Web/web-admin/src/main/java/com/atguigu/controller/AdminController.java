package com.atguigu.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.Admin;
import com.atguigu.entity.Role;
import com.atguigu.service.AdminService;
import com.atguigu.service.RoleService;
import com.atguigu.util.FileUtil;
import com.atguigu.util.QiniuUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author HaQQ
 * @version 1.0
 * @date 2022/6/13 16:49
 */
@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {


    public static final String PAGE_CREATE = "admin/create";
    @Reference
    private AdminService adminService;
    @Reference
    private RoleService roleService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public static final String PAGE_ASSIGNSHOW = "admin/assignShow";
    private final static String PAGE_INDEX = "admin/index";
    private final static String LIST_ACTION = "redirect:/admin";
    public static final String PAGE_UPLOAD = "admin/upload";
    private final static String PAGE_EDIT = "admin/edit";


    @PreAuthorize("hasAuthority('admin.show')")
    @RequestMapping
    public String index(@RequestParam Map<String,Object> map, Model model){
        PageInfo<Admin> page = adminService.findPage(map);
        model.addAttribute("page",page);
        model.addAttribute("filters",map);
       return PAGE_INDEX;
       }
        @PreAuthorize("hasAuthority('admin.create')")
       @GetMapping("/create")
       public String create(){
          return PAGE_CREATE;
       }
       @PreAuthorize("hasAuthority('admin.create')")
       @RequestMapping("/save")
       public String save(Admin admin,Model model){
        admin.setPassword(bCryptPasswordEncoder.encode(admin.getPassword()));
          adminService.insert(admin);
          return successPage(model,"新增角色成功");
       }
       @PreAuthorize("hasAuthority('admin.edit')")
       @RequestMapping("/edit/{id}")
       public String edit(@PathVariable("id") Long id, Model model){
           Admin admin = adminService.getById(id);
           model.addAttribute("admin",admin);
           return PAGE_EDIT;
       }
       @PreAuthorize("hasAuthority('admin.edit')")
       @RequestMapping("/update")
       public String update(Admin admin,Model model){
          adminService.update(admin);
          return successPage(model,"用户信息修改成功");
       }
    @PreAuthorize("hasAuthority('admin.delete')")
    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable("id")Long id,Model model){
       adminService.delete(id);
       return LIST_ACTION;
    }
    @PreAuthorize("hasAuthority('admin.upload')")
    @RequestMapping("/uploadShow/{id}")
    public String uploadShow(@PathVariable("id")Long id,Model model){
       model.addAttribute("id",id); 
       return PAGE_UPLOAD;
    }
    @PreAuthorize("hasAuthority('admin.upload')")
    @RequestMapping("/upload/{id}")
    public String upload(@PathVariable("id")Long id,
                         @RequestParam("file") MultipartFile multipartFile,
                           Model model) throws IOException {


        //上传
//       获取文件名
        String originalFilename = multipartFile.getOriginalFilename();
//        生成唯一的文件名
        String uuidName = FileUtil.getUUIDName(originalFilename);
//上传到七牛云
        QiniuUtils.upload2Qiniu(multipartFile.getBytes(),uuidName);
//保存
//        获取url
        String url = QiniuUtils.getUrl(uuidName);
//        设置url
       Admin admin = new Admin();
       admin.setHeadUrl(url);
       admin.setId(id);

       adminService.update(admin);

        return successPage(model,"头像上传成功");
    }

//    权限管理
    @PreAuthorize("hasAnyAuthority('admin.assign')")
    @GetMapping("/assignShow/{id}")
    public String assignShow(@PathVariable("id") Long id,
                             Model model){
        Map<String,List<Role>> roles = adminService.findRolesByAdminId(id);

        model.addAllAttributes(roles);
        model.addAttribute("adminId",id);

        return PAGE_ASSIGNSHOW;
    }
//    保存
    @PreAuthorize("hasAnyAuthority('admin.assign')")
    @PostMapping("/assignRole")
    public String assignRole(@RequestParam("adminId")Long adminId,
                              @RequestParam("roleIds") List<Long> roleIds,
                             Model model){
//        调取业务成处理
        adminService.saveAssignRole(adminId,roleIds);
       return successPage(model,"角色分配成功");
    }

}
