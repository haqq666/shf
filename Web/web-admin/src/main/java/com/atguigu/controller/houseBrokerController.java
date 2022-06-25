package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.Admin;
import com.atguigu.entity.HouseBroker;
import com.atguigu.service.AdminService;
import com.atguigu.service.HouseBrokerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author HaQQ
 * @version 1.0
 * @date 2022/6/16 20:36
 */
@Controller
@RequestMapping("/houseBroker")
public class houseBrokerController extends BaseController {

    public static final String PAGE_CREATE = "houseBroker/create";
    public static final String PAGE_EDIT = "houseBroker/edit";
    public static final String PAGE_HOUSEDETAILS = "redirect:/house/";
    @Reference
    private HouseBrokerService houseBrokerService;
    @Reference
    private AdminService adminService;


    @GetMapping("/create")
    public String create(Model model,HouseBroker houseBroker){

        saveAdminListToModel(model);
        model.addAttribute("houseBroker",houseBroker);
        return PAGE_CREATE;
    }
    @PostMapping("/save")
    public String save(HouseBroker houseBroker,Model model){

        HouseBroker houseBroker1 = houseBrokerService.findByHouseIdAndBrokerId(houseBroker.getHouseId(),houseBroker.getBrokerId());
        if (houseBroker1 !=null){
            throw new RuntimeException("当前经纪人已经是该房源的经纪人，新增失败");
        }
        Admin admin = adminService.getById(houseBroker.getBrokerId());

        houseBroker.setBrokerName(admin.getName());
        houseBroker.setBrokerHeadUrl(admin.getHeadUrl());

       houseBrokerService.insert(houseBroker);

       return successPage(model,"新增经纪人成功");
    }

    @RequestMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id")Long id){
        HouseBroker houseBroker = houseBrokerService.getById(id);
        model.addAttribute("houseBroker",houseBroker);
        saveAdminListToModel(model);
        return PAGE_EDIT;
    }

    @RequestMapping("/update")
    public String update(HouseBroker houseBroker,Model model){

        Long brokerId = houseBroker.getBrokerId();
        HouseBroker dbHouseBroker = houseBrokerService.getByBrokerId(houseBroker.getBrokerId());
        HouseBroker houseBroker1 = houseBrokerService.findByHouseIdAndBrokerId(dbHouseBroker.getHouseId(), dbHouseBroker.getBrokerId());
        if (houseBroker1 !=null){
            throw new RuntimeException("当前经纪人已经是当前房源的经纪人，修改失败");
        }
        Admin admin = adminService.getById(houseBroker.getBrokerId());
        houseBroker.setBrokerName(admin.getName());
        houseBroker.setBrokerHeadUrl(admin.getHeadUrl());

        houseBrokerService.update(houseBroker);
       return successPage(model,"经纪人修改成功");
    }

    @RequestMapping("/delete/{houseId}/{id}")
    public String delete(@PathVariable("id") Long id,@PathVariable("houseId")Long houseId){
       houseBrokerService.delete(id);
       return PAGE_HOUSEDETAILS + houseId;
    }

    /**
     *将admin列表加入model中
     * @param model
     */
    private void saveAdminListToModel(Model model) {
        List<Admin> adminList = adminService.findAll();
        model.addAttribute("adminList", adminList);
    }




}
