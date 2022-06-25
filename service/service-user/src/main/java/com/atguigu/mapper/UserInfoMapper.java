package com.atguigu.mapper;

import com.atguigu.entity.UserInfo;

/**
 * @author HaQQ
 * @version 1.0
 * @date 2022/6/20 18:45
 */
public interface UserInfoMapper {


    UserInfo getByPhone(String phone);

    void insert(UserInfo userInfo);

}
