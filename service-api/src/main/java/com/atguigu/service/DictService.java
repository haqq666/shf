package com.atguigu.service;

import com.atguigu.entity.Dict;

import java.util.List;
import java.util.Map;

/**
 * @author HaQQ
 * @version 1.0
 * @date 2022/6/15 1:01
 */
public interface DictService {

    List<Map<String,Object>> findZnodes(Long id);

    List<Dict> findDictListByParentDictCode(String parentDictCode);

     List<Dict> findDictListByParentId(Long parentId);
}
