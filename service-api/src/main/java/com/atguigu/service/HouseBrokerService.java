package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.HouseBroker;

import java.util.List;

/**
 * @author HaQQ
 * @version 1.0
 * @date 2022/6/16 20:22
 */
public interface HouseBrokerService extends BaseService<HouseBroker> {
    List<HouseBroker> findHouseBrokerList(Long houseId);

    HouseBroker findByHouseIdAndBrokerId(Long houseId, Long brokerId);

    HouseBroker getByBrokerId(Long id);
}
