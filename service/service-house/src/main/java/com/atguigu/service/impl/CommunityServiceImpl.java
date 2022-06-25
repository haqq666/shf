package com.atguigu.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.base.BaseMapper;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.entity.Community;
import com.atguigu.entity.House;
import com.atguigu.mapper.CommunityMapper;
import com.atguigu.service.CommunityService;
import com.atguigu.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author HaQQ
 * @version 1.0
 * @date 2022/6/15 16:25
 */
@Service(interfaceClass = CommunityService.class)
@Transactional(propagation = Propagation.SUPPORTS)
public class CommunityServiceImpl extends BaseServiceImpl<Community> implements CommunityService {

    @Autowired
    private CommunityMapper communityMapper;
    @Autowired
    private HouseService houseService;

    @Override
    protected BaseMapper<Community> getEntityMapper() {
        return communityMapper;
    }

    @Override
    public void delete(Long id) {
        List<House> byCommunityId = houseService.getByCommunityId(id);
        if (byCommunityId.size() !=0){
            throw new RuntimeException("无法删除，该小区下还有房源");
        }
        super.delete(id);
    }

    @Override
    public List<Community> findAll() {
        return communityMapper.findAll();
    }
}
