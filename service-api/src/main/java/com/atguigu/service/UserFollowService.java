package com.atguigu.service;

import com.atguigu.entity.UserFollow;
import com.atguigu.entity.vo.UserFollowVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author HaQQ
 * @version 1.0
 * @date 2022/6/20 21:21
 */
public interface UserFollowService {
    /**
     * 查看用户是否关注此房源
     * @param userId
     * @param houseId
     * @return
     */
     UserFollow findByUserIdAndHouseId(Long userId,Long houseId);

    /**
     * 用户之前若关注过此房源，更新is_deleted信息
     * @param userFollow
     */
    void update(UserFollow userFollow);

    /**
     * 用户之前为关注过此房源，新增关注信息
     * @param userFollow
     */
    void insert(UserFollow userFollow);

    /**
     * 查讯用户的关注列表，并分页显示
     * @param pageNum
     * @param pageSize
     * @param id
     * @return
     */
    PageInfo<UserFollowVo> findListPage(Integer pageNum, Integer pageSize, Long id);

    void delete(Long id);
}
