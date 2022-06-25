package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.entity.UserFollow;
import com.atguigu.entity.vo.UserFollowVo;
import com.atguigu.mapper.UserFollowMapper;
import com.atguigu.service.UserFollowService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author HaQQ
 * @version 1.0
 * @date 2022/6/20 21:24
 */
@Service(interfaceClass = UserFollowService.class)
public class UserFollowServiceImpl implements UserFollowService {

    @Autowired
    private UserFollowMapper userFollowMapper;
    @Override
    public UserFollow findByUserIdAndHouseId(Long userid,Long houseId) {
        return userFollowMapper.findByUserIdAndHouseId(userid,houseId);
    }

    @Override
    public void update(UserFollow userFollow) {
       userFollowMapper.update(userFollow);
    }

    @Override
    public void insert(UserFollow userFollow) {
        userFollowMapper.insert(userFollow);
    }

    @Override
    public PageInfo<UserFollowVo> findListPage(Integer pageNum, Integer pageSize, Long id) {
        PageHelper.startPage(pageNum,pageSize);
       return new PageInfo<>(userFollowMapper.findListPage(id));
    }

    @Override
    public void delete(Long id) {
        userFollowMapper.delete(id);
    }
}
