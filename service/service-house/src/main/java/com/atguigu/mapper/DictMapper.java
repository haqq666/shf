package com.atguigu.mapper;

import com.atguigu.entity.Dict;

import java.util.List;

/**
 * @author HaQQ
 * @version 1.0
 * @date 2022/6/15 0:51
 */
public interface DictMapper {
    /**
     * 跟据父节点的id查询子节点列表
     * @param parentId
     * @return
     */
    List<Dict> findListByParentId(Long parentId);

    /**
     * 查看是否是父节点
     * @param id
     * @return
     */
    Integer countIdParent(Long id);

    List<Dict> findListByParentCode(String parentDictCode);
}


















