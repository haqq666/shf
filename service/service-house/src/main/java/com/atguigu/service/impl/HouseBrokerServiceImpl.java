package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.base.BaseMapper;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.entity.HouseBroker;
import com.atguigu.mapper.HouseBrokerMapper;
import com.atguigu.service.HouseBrokerService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author HaQQ
 * @version 1.0
 * @date 2022/6/16 20:24
 */
@Service(interfaceClass = HouseBrokerService.class)
public class HouseBrokerServiceImpl extends BaseServiceImpl<HouseBroker> implements HouseBrokerService {

    @Autowired
    private HouseBrokerMapper houseBrokerMapper;

    @Override
    protected BaseMapper<HouseBroker> getEntityMapper() {
        return houseBrokerMapper;
    }

    @Override
    public List<HouseBroker> findHouseBrokerList(Long houseId) {
        return houseBrokerMapper.findHouseBrokerList(houseId);
    }

    @Override
    public HouseBroker findByHouseIdAndBrokerId(Long houseId, Long brokerId) {
        return houseBrokerMapper.findByHouseIdAndBrokerId(houseId,brokerId);
    }

    @Override
    public HouseBroker getByBrokerId(Long id) {
        return houseBrokerMapper.getByBrokerId(id);
    }

}
