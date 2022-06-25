package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.Community;
import com.atguigu.entity.Dict;
import com.atguigu.service.CommunityService;
import com.atguigu.service.DictService;
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
 * @date 2022/6/15 13:51
 */
@Controller
@RequestMapping("/community")
public class CommunityController extends BaseController {


    @Reference
    private CommunityService communityService;
    @Reference
    private DictService dictService;

    public static final String PAGE_CREATE = "community/create";
    public static final String PAGE_EDIT = "community/edit";
    public static final String LIST_ACTION = "redirect:/community";
    public static final String PAGE_INDEX = "community/index";


    @RequestMapping
    public String index(@RequestParam Map<String,Object> filters, Model model){

        if (filters.get("pageNUm") == null || "".equals(filters.get("pageNum"))) {
            filters.put("pageNum",1);
        }
         if (filters.get("pageSize") == null || "".equals(filters.get("pageSize"))) {
            filters.put("pageSize",10);
        }

        List<Dict> areaList = dictService.findDictListByParentDictCode("beijing");
        PageInfo<Community> page = communityService.findPage(filters);

        if (!filters.containsKey("areaId")){
            filters.put("areaId",0);
        }
        if (!filters.containsKey("plateId")){
            filters.put("plateId",0);
        }


        model.addAttribute("page",page);
        model.addAttribute("filters",filters);
        model.addAttribute("areaList",areaList);


        return PAGE_INDEX;
    }

    @RequestMapping("/create")
    public String create(Model model){
        List<Dict> areaList = dictService.findDictListByParentDictCode("beijing");
        model.addAttribute("areaList",areaList);
        return PAGE_CREATE;
    }

    @RequestMapping("/save")
    public String save(Community community,Model model){
       communityService.insert(community);
       return successPage(model,"添加小区成功");
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable("id")Long id,Model model){
        Community community = communityService.getById(id);
        model.addAttribute("community",community);
        List<Dict> areaList = dictService.findDictListByParentDictCode("beijing");
        model.addAttribute("areaList",areaList);
        return PAGE_EDIT;
    }
    @RequestMapping("/update")
    public String update(Community community,Model model){
       communityService.update(community);
       return successPage(model,"小区信息修改成功");
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id){
       communityService.delete(id);
       return LIST_ACTION;

    }





}
