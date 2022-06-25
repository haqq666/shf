package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.Community;

import java.util.List;

/**
 * @author HaQQ
 * @version 1.0
 * @date 2022/6/15 13:58
 */
public interface CommunityService extends BaseService<Community> {

    List<Community> findAll();
}
