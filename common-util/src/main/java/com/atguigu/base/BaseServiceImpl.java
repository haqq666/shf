package com.atguigu.base;

import com.atguigu.util.CastUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author HaQQ
 * @version 1.0
 * @date 2022/6/13 15:33
 */
@Service
public abstract class BaseServiceImpl<T> implements BaseService<T>{

    protected   abstract  BaseMapper<T> getEntityMapper();

    @Override
    public void insert(T role) {
        getEntityMapper().insert(role);
    }

    @Override
    public T getById(Long id) {
       return getEntityMapper().getById(id);
    }

    @Override
    public void delete(Long id) {
        getEntityMapper().delete(id);
    }

    @Override
    public PageInfo<T> findPage(Map<String, Object> filters) {
        int pageNum = CastUtil.castInt(filters.get("pageNum"), 1);
        int pageSize = CastUtil.castInt(filters.get("pageSize"), 10);
        PageHelper.startPage(pageNum,pageSize);
        return new PageInfo<T>(getEntityMapper().findPage(filters),10);
    }

    @Override
    public void update(T role) {
        getEntityMapper().update(role);
    }
}
