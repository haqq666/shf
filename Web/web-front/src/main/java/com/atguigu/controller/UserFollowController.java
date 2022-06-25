package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.UserFollow;
import com.atguigu.entity.UserInfo;
import com.atguigu.entity.vo.UserFollowVo;
import com.atguigu.result.Result;
import com.atguigu.service.UserFollowService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author HaQQ
 * @version 1.0
 * @date 2022/6/20 21:12
 */
@RestController
@RequestMapping("/userFollow")
public class UserFollowController {
    @Reference
    private UserFollowService userFollowService;

    @GetMapping("/auth/follow/{houseId}")
    public Result follow(@PathVariable("houseId")Long houseId, HttpSession session){
//查询用户是否关注过这个页面
        //获取用户信息
        UserInfo userInfo = (UserInfo)session.getAttribute("USER");
        //获取id
        Long id = userInfo.getId();

//跟据用户id查找关注列表
        UserFollow userFollow = userFollowService.findByUserIdAndHouseId(userInfo.getId(),houseId);
//        之前关注过
        if (userFollow != null){
            userFollow.setIsDeleted(0);
            userFollowService.update(userFollow);
        } else {
//        之前没有关注过
            userFollow = new UserFollow();
            userFollow.setUserId(id);
            userFollow.setHouseId(houseId);
            userFollowService.insert(userFollow);
        }
        return Result.ok();
    }
//    显示我的关注列表
    @GetMapping("/auth/list/{pageNum}/{pageSize}")
    public Result list(@PathVariable("pageNum")Integer pageNum,
                       @PathVariable("pageSize")Integer pageSize,
                       HttpSession session){
        UserInfo userInfo = (UserInfo)session.getAttribute("USER");
        PageInfo<UserFollowVo> pageInfo= userFollowService.findListPage(pageNum,pageSize,userInfo.getId());
        return Result.ok(pageInfo);
    }
//    取消关注
    @RequestMapping("/auth/cancelFollow/{id}")
    public Result cancelFollow(@PathVariable("id")Long id){

       userFollowService.delete(id);

       return Result.ok();
    }


}
