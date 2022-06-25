package com.atguigu.base;

import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * @author HaQQ
 * @version 1.0
 * @date 2022/6/13 15:15
 */
public interface BaseService<T> {
    /**
     * 增加
     * @param t
     */
    void insert(T t);

    /**
     * 通过id查
     * @param id
     * @return
     */
    T getById(Long id);

    /**
     * 跟据id删除
     * @param id
     */
    void delete(Long id);

    /**
     * 修改
     * @param t
     */
    void update(T t);

    /**
     * 分页查询
     * @param filters
     * @return
     */
    PageInfo<T> findPage(Map<String,Object> filters);
}
