package com.atguigu.mapper;

import com.atguigu.base.BaseMapper;
import com.atguigu.entity.Community;

import java.util.List;

/**
 * @author HaQQ
 * @version 1.0
 * @date 2022/6/15 16:29
 */
public interface CommunityMapper extends BaseMapper<Community> {

    List<Community> findAll();
}
