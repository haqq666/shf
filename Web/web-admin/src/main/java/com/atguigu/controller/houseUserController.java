package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.HouseUser;
import com.atguigu.service.HouseUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author HaQQ
 * @version 1.0
 * @date 2022/6/16 23:33
 */
@Controller
@RequestMapping("/houseUser")
public class houseUserController extends BaseController {

    public static final String PAGE_CREATE = "houseUser/create";
    public static final String PAGE_EDIT = "houseUser/edit";
    public static final String HOUSE_DETAILS = "redirect:/house/";
    @Reference
    private HouseUserService houseUserService;

    @RequestMapping("/create")
    public String create(HouseUser houseUser, Model model){
       model.addAttribute(houseUser);
       return PAGE_CREATE;
    }

    @RequestMapping("/save")
    public String save(HouseUser houseUser,Model model){
       houseUserService.insert(houseUser);
       return successPage(model,"新增房东成功");
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable("id")Long id,Model model){
        HouseUser houseUser = houseUserService.getById(id);
        model.addAttribute("houseUser",houseUser);
        return PAGE_EDIT;
    }
    @RequestMapping("/update")
    public String update(HouseUser houseUser,Model model){
       houseUserService.update(houseUser);
       return successPage(model,"房东信息修改成功");
    }

    @RequestMapping("/delete/{houseId}/{id}")
    public String delete(@PathVariable("id")Long id,@PathVariable("houseId")Long houseId){
        houseUserService.delete(id);
       return HOUSE_DETAILS + houseId;
    }
}
