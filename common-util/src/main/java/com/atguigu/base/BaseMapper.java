package com.atguigu.base;

import com.github.pagehelper.Page;

import java.util.Map;

/**
 * @author HaQQ
 * @version 1.0
 * @date 2022/6/13 15:10
 */
public interface BaseMapper<T> {
    /**
     * 增加
     * @param t
     */
    void insert(T t);

    /**
     * 跟据id查
     * @param id
     * @return
     */
    T getById(Long id);

    /**
     * 删除
     * @param id
     */
    void delete(Long id);

    /**
     * 分页查
     * @param filters
     * @return
     */
    Page<T> findPage(Map<String, Object> filters);

    /**
     * 修改
     * @param t
     */
    void update(T t);

}
