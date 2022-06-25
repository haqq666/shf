package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.*;
import com.atguigu.entity.bo.HouseQueryBo;
import com.atguigu.entity.vo.HouseVo;
import com.atguigu.result.Result;
import com.atguigu.service.*;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author HaQQ
 * @version 1.0
 * @date 2022/6/19 22:14
 */
@RestController
@RequestMapping("/house")
public class HouseController extends BaseController {

    @Reference
    private HouseService houseService;
    @Reference
    private CommunityService communityService;
    @Reference
    private HouseBrokerService houseBrokerService;
    @Reference
    private HouseUserService houseUserService;
    @Reference
    private HouseImgService houseImgService;
    @Reference
    private UserFollowService userFollowService;

    @RequestMapping("/list/{pageNum}/{pageSize}")
    public Result list(@PathVariable("pageNum")Integer pageNum,
                       @PathVariable("pageSize")Integer pageSize,
                       @RequestBody HouseQueryBo houseQueryBo){

        PageInfo<HouseVo> listPage = houseService.findListPage(pageNum, pageSize, houseQueryBo);

        return Result.ok(listPage);
    }

    @RequestMapping("/info/{id}")
    public Result info(@PathVariable("id")Long id, HttpSession session){
        Map<String,Object> resultMap = new HashMap<>();
//查房源信息
        House house = houseService.getById(id);
        resultMap.put("house",house);
//查小区
        Community community = communityService.getById(house.getCommunityId());
        resultMap.put("community",community);
//查询房屋经纪人信息
        List<HouseBroker> houseBrokerList = houseBrokerService.findHouseBrokerList(id);
        resultMap.put("houseBrokerList",houseBrokerList);
//查询房屋拥有者的信息
        List<HouseUser> houseUserList = houseUserService.findHouseUserList(id);
        resultMap.put("houseUserList",houseUserList);
//房源的图片信息
        List<HouseImage> imageList = houseImgService.findImageList(id, 1);
        resultMap.put("houseImage1List",imageList);
//判断是否关注
        //获取用户信息列表
        UserInfo userInfo = (UserInfo) session.getAttribute("USER");
        boolean isFollowed = false;
//
        if (userInfo != null){
//            查询是否关注这个房源
            UserFollow userFollow = userFollowService.findByUserIdAndHouseId(userInfo.getId(), id);
            if(userFollow != null && userFollow.getIsDeleted()!=1){
                isFollowed=true;
            }
        }
        resultMap.put("isFollowed",isFollowed);
        return Result.ok(resultMap);
    }
    
}
