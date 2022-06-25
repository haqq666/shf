package com.atguigu.mapper;

import com.atguigu.base.BaseMapper;
import com.atguigu.entity.HouseBroker;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author HaQQ
 * @version 1.0
 * @date 2022/6/16 20:27
 */
public interface HouseBrokerMapper extends BaseMapper<HouseBroker> {
    List<HouseBroker> findHouseBrokerList(Long id);

    HouseBroker findByHouseIdAndBrokerId(@Param("houseId") Long houseId, @Param("brokerId") Long brokerId);

    HouseBroker getByBrokerId(Long id);
}
