package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.en.HouseStatus;
import com.atguigu.entity.*;
import com.atguigu.service.*;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @author HaQQ
 * @version 1.0
 * @date 2022/6/16 10:40
 */
@Controller
@RequestMapping("/house")
public class HouseController extends BaseController {

    public static final String PAGE_INDEX = "house/index";
    public static final String PAGE_CREATE = "house/create";
    public static final String LIST_PAGE = "redirect:/house";
    public static final String PAGE_EDIT = "house/edit";
    public static final String PAGE_SHOW = "house/show";
    @Reference
    private HouseService houseService;
    @Reference
    private CommunityService communityService;
    @Reference
    private DictService dictService;
    @Reference
    private HouseImgService houseImgService;
    @Reference
    private HouseUserService houseUserService;
    @Reference
    private HouseBrokerService houseBrokerService;


    @RequestMapping
    public String index(@RequestParam Map<String,Object> filters, Model model){

        if (filters.get("pageNum")==null || "".equals(filters.get("pageNUm"))){
            filters.put("pageNum",1);
        }
        if (filters.get("pageSize")==null || "".equals(filters.get("pageSize"))){
            filters.put("pageSize",10);
        }


        PageInfo<House> page = houseService.findPage(filters);
        model.addAttribute("filters",filters);
        model.addAttribute("page",page);

//        查询小区
        saveAllDictToModel(model);

        return PAGE_INDEX;
    }

    /**
     * 将小区的信息加载到model
     * @param model
     */
    private void saveAllDictToModel(Model model) {
        model.addAttribute("communityList",communityService.findAll());
        model.addAttribute("houseTypeList",dictService.findDictListByParentDictCode("houseType"));
        model.addAttribute("floorList",dictService.findDictListByParentDictCode("floor"));
        model.addAttribute("buildStructureList",dictService.findDictListByParentDictCode("buildStructure"));
        model.addAttribute("directionList",dictService.findDictListByParentDictCode("direction"));
        model.addAttribute("decorationList",dictService.findDictListByParentDictCode("decoration"));
        model.addAttribute("houseUseList",dictService.findDictListByParentDictCode("houseUse"));
    }


    @RequestMapping("/create")
    public String creat(Model model){
        saveAllDictToModel(model);
       return PAGE_CREATE;
    }

    @RequestMapping("/save")
    public String save(House house,Model model){
        house.setStatus(HouseStatus.UNPUBLISHED.code);
        houseService.insert(house);
        return successPage(model,"添加成功");
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable("id")Long id,Model model){
        House house = houseService.getById(id);
        model.addAttribute("house",house);
        saveAllDictToModel(model);
        return PAGE_EDIT;
    }
    
    @RequestMapping("/update")
    public String update(House house,Model model){
       houseService.update(house);
       return successPage(model,"修改小区成功");
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id){
       houseService.delete(id);
       return LIST_PAGE;
    }

    @RequestMapping("/publish/{id}/{status}")
    public String publish(@PathVariable("id")Long id,@PathVariable("status") Integer status){
        houseService.publish(id,status);
       return LIST_PAGE;
    }
    
    @RequestMapping("/{id}")
    public String show(@PathVariable("id")Long houseId,Model model){
        House house = houseService.getById(houseId);
        model.addAttribute("house",house);

        Community community = communityService.getById(house.getCommunityId());
        model.addAttribute("community",community);

        List<HouseImage> all = houseImgService.findImageList(houseId,1);
        model.addAttribute("houseImage1List",all);
        List<HouseImage> imageList = houseImgService.findImageList(houseId, 2);
        model.addAttribute("houseImage2List",imageList);

        List<HouseUser> houseUserList = houseUserService.findHouseUserList(houseId);
        model.addAttribute("houseUserList",houseUserList);

        List<HouseBroker> houseBrokerList = houseBrokerService.findHouseBrokerList(houseId);
        model.addAttribute("houseBrokerList",houseBrokerList);


        return PAGE_SHOW;
    }
    


}
