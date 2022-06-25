package com.atguigu.service;

import com.atguigu.entity.UserInfo;

/**
 * @author HaQQ
 * @version 1.0
 * @date 2022/6/20 18:26
 */
public interface UserInfoService {

    UserInfo getByPhone(String phone);

    void register(UserInfo userInfo);
}
