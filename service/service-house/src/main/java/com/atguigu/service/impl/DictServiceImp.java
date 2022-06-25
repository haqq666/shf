package com.atguigu.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.atguigu.entity.Dict;
import com.atguigu.mapper.DictMapper;
import com.atguigu.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author HaQQ
 * @version 1.0
 * @date 2022/6/15 8:43
 */
@Service(interfaceClass = DictService.class)
public class DictServiceImp implements DictService {

    @Autowired
    private DictMapper dictMapper;
    @Autowired
    private JedisPool jedisPool;

    @Override
    public List<Map<String, Object>> findZnodes(Long id) {

        List<Dict> listByParent = dictMapper.findListByParentId(id);

        ArrayList<Map<String,Object>> dicts = new ArrayList<>();
        for (Dict dict : listByParent) {
            Map<String,Object> zNode = new HashMap<>();
            zNode.put("id",dict.getId());
            zNode.put("name",dict.getName());
            zNode.put("isParent",dictMapper.countIdParent(dict.getId()) > 0);
            dicts.add(zNode);
        }
        return dicts;
    }

    @Override
    public List<Dict> findDictListByParentDictCode(String parentDictCode) {
        Jedis jedis = null;
        try {
            //从redis里面找
            //获得连接
            jedis = jedisPool.getResource();
            //查询
            String value = jedis.get("zfw:dict:parentDictCode:" + parentDictCode);
            if (!StringUtils.isEmpty(value)){
                return JSON.parseArray(value,Dict.class);
            }
            //redis里面没有，则将查到的放进redis中
            List<Dict> dictList = dictMapper.findListByParentCode(parentDictCode);
            if (!CollectionUtils.isEmpty(dictList)){
                jedis.set("zfw:dict:parentDictCode:"+parentDictCode,JSON.toJSONString(dictList));
            }
            return dictList;
        } finally {
            if (jedis !=null){
                jedis.close();
                if (jedis.isConnected()){
                    jedis.disconnect();
                }
            }

        }
    }
    @Override
    public List<Dict> findDictListByParentId(Long parentId) {
        Jedis jedis = null;
        try {
            //获得连接
            jedis = jedisPool.getResource();
            //查询
            String value = jedis.get("zfw:dict:parentDictId:" + parentId);
            if (!StringUtils.isEmpty(value)){
                return JSON.parseArray(value,Dict.class);
            }
            //redis中没有，则从数据库中查找
            List<Dict> dictList = dictMapper.findListByParentId(parentId);
            //把数据存到redis中
            if (!CollectionUtils.isEmpty(dictList)){
                jedis.set("zfw:dict:parentDictId:"+parentId,JSON.toJSONString(dictList));
            }
            return dictList;
        } finally {
            if (jedis != null) {
                jedis.close();
                if (jedis.isConnected()){
                    jedis.disconnect();
                }
            }
        }
    }



}
