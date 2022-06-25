package com.atguigu.mapper;

import com.atguigu.entity.UserFollow;
import com.atguigu.entity.vo.UserFollowVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author HaQQ
 * @version 1.0
 * @date 2022/6/20 21:29
 */
public interface UserFollowMapper {

    UserFollow findByUserIdAndHouseId(@Param("userId") Long userid,@Param("houseId") Long houseId);

    void update(UserFollow userFollow);

    void insert(UserFollow userFollow);

    Page<UserFollowVo> findListPage(Long id);

    void delete(Long id);
}
