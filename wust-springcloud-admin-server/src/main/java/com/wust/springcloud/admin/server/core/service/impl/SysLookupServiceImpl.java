package com.wust.springcloud.admin.server.core.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wust.springcloud.admin.server.core.dao.SysLookupMapper;
import com.wust.springcloud.admin.server.core.service.SysLookupService;
import com.wust.springcloud.common.dto.ResponseDto;
import com.wust.springcloud.common.entity.sys.lookup.SysLookup;
import com.wust.springcloud.common.entity.sys.lookup.SysLookupList;
import com.wust.springcloud.common.entity.sys.lookup.SysLookupSearch;
import com.wust.springcloud.common.enums.RedisKeyEnum;
import com.wust.springcloud.common.util.MyStringUtils;
import com.wust.springcloud.common.util.cache.SpringRedisTools;
import com.wust.springcloud.common.xml.XMLAbstractResolver;
import com.wust.springcloud.common.xml.XMLDefinitionFactory;
import com.wust.springcloud.common.xml.factory.XMLLookupFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by WST on 2019/4/29.
 */
@Service("sysLookupServiceImpl")
public class SysLookupServiceImpl implements SysLookupService {
    @Autowired
    private SpringRedisTools springRedisTools;

    @Autowired
    private SysLookupMapper sysLookupMapper;

    @Override
    public List<SysLookupList> listPage(SysLookupSearch search) {
        return sysLookupMapper.listPage(search);
    }

    @Override
    public List<SysLookupList> findByCondition(SysLookupSearch search) {
        return sysLookupMapper.findByCondition(search);
    }

    @Transactional(rollbackFor=Exception.class)
    @Override
    public ResponseDto init() {
        /**
         *  读取XML配置的数据字典
         */
        XMLDefinitionFactory xmlLookupFactory = new XMLLookupFactory();
        XMLAbstractResolver resolver = xmlLookupFactory.createXMLResolver();
        Map<String,List> resultMap =  resolver.getResult();
        List zhCNList = resultMap.get("zh_CN");
        List enUSList = resultMap.get("en_US");


        List<SysLookup> sysLookups = new ArrayList<>();
        sysLookups.addAll(zhCNList);
        sysLookups.addAll(enUSList);
        sysLookupMapper.deleteAll();
        sysLookupMapper.batchInsert(sysLookups);

        Set<String> keySet = resultMap.keySet();
        for (String lan : keySet) {
            List list = resultMap.get(lan);
            String groupLookupCodeByRootCodeAndNameJSONString = groupLookupCodeByRootCodeAndName(list);
            String groupLookupNameByCodeJSONString = groupLookupNameByCode(list);
            String groupLookupByPidJSONString = groupLookupByParentCode(list);

            String key = String.format(RedisKeyEnum.REDIS_TABLE_KEY_GROUP_LOOKUP_CODE_BY_ROOT_CODE_AND_NAME.getStringValue(),lan);
            springRedisTools.deleteByKey(key);
            springRedisTools.addData(key, groupLookupCodeByRootCodeAndNameJSONString);

            String key1 = String.format(RedisKeyEnum.REDIS_TABLE_KEY_GROUP_LOOKUP_NAME_BY_CODE.getStringValue(),lan);
            springRedisTools.deleteByKey(key1);
            springRedisTools.addData(key1, groupLookupNameByCodeJSONString);

            String key2 = String.format(RedisKeyEnum.REDIS_TABLE_KEY_GROUP_GROUP_LOOKUP_BY_PID.getStringValue(),lan);
            springRedisTools.deleteByKey(key2);
            springRedisTools.addData(key2, groupLookupByPidJSONString);
        }
        return null;
    }










    private String groupLookupCodeByRootCodeAndName(List<SysLookup> sysLookups){
        JSONObject jsonObject = new JSONObject();
        for (SysLookup sysLookup : sysLookups) {
            String rootCode = sysLookup.getRootCode();
            String key = MyStringUtils.null2String(rootCode) + "." + MyStringUtils.null2String(sysLookup.getName());
            if(jsonObject.containsKey(key)){
                continue;
            }else{
                jsonObject.put(key,sysLookup.getCode());
            }
        }
        return jsonObject.toJSONString();
    }



    private String groupLookupNameByCode(List<SysLookup> sysLookups){
        JSONObject jsonObject = new JSONObject();
        for (SysLookup sysLookup : sysLookups) {
            String key = MyStringUtils.null2String(sysLookup.getCode());
            if(jsonObject.containsKey(key)){
                continue;
            }else{
                jsonObject.put(key,sysLookup.getName());
            }
        }
        return jsonObject.toJSONString();
    }

    private String groupLookupByParentCode(List<SysLookup> sysLookups){
        JSONObject result = new JSONObject();
        JSONArray jsonArray = null;
        for (SysLookup sysLookup : sysLookups) {
            String key =  MyStringUtils.null2String(sysLookup.getParentCode());
            if(!result.containsKey(key)){
                jsonArray = new JSONArray();
                result.put(key,jsonArray);
            }else{
                jsonArray = result.getJSONArray(key);
            }
            jsonArray.add(JSONObject.toJSON(sysLookup));
        }
        return result.toJSONString();
    }
}
