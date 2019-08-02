package com.wust.springcloud.admin.server.core.service.defaults.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wust.springcloud.admin.server.core.dao.SysDataPrivilegeMapper;
import com.wust.springcloud.admin.server.core.dao.SysDataPrivilegeRulesMapper;
import com.wust.springcloud.admin.server.core.service.defaults.SysDataPrivilegeService;
import com.wust.springcloud.common.context.DefaultBusinessContext;
import com.wust.springcloud.common.dao.IBaseMapper;
import com.wust.springcloud.common.entity.sys.dataprivilege.SysDataPrivilege;
import com.wust.springcloud.common.entity.sys.dataprivilege.rules.SysDataPrivilegeRules;
import com.wust.springcloud.common.entity.sys.dataprivilege.rules.SysDataPrivilegeRulesList;
import com.wust.springcloud.common.entity.sys.dataprivilege.rules.SysDataPrivilegeRulesSearch;
import com.wust.springcloud.common.enums.RedisKeyEnum;
import com.wust.springcloud.common.service.BaseServiceImpl;
import com.wust.springcloud.common.util.cache.SpringRedisTools;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

/**
 * Created by WST on 2019/6/10.
 */
@Service("sysDataPrivilegeServiceImpl")
public class SysDataPrivilegeServiceImpl extends BaseServiceImpl implements SysDataPrivilegeService {
    @Autowired
    private SysDataPrivilegeMapper sysDataPrivilegeMapper;

    @Autowired
    private SysDataPrivilegeRulesMapper sysDataPrivilegeRulesMapper;

    @Autowired
    private SpringRedisTools springRedisTools;

    @Autowired
    private Environment env;

    @Override
    protected IBaseMapper getBaseMapper() {
        return sysDataPrivilegeMapper;
    }


    @Transactional(rollbackFor=Exception.class)
    @Override
    public void init() {
        DefaultBusinessContext ctx = DefaultBusinessContext.getContext();

        String serverName = env.getProperty("spring.application.name");
        String key = String.format(RedisKeyEnum.REDIS_TABLE_KEY_DATA_PRIVILEGE_ANNOTATIONS.getStringValue(),serverName);
        try {
            Object obj = springRedisTools.getByKey(key);
            if (obj != null) {
                /**
                 * 清空主表
                 *
                 */
                sysDataPrivilegeMapper.deleteAll();


                Map<String, String> idMap = new HashMap(100);
                JSONArray jsonArray = JSONObject.parseArray(obj.toString());
                for (Object o : jsonArray) {
                    SysDataPrivilege sysDataPrivilege = JSONObject.parseObject(o.toString(), SysDataPrivilege.class);
                    if (idMap.containsKey(sysDataPrivilege.getUuid())) {
                        continue;
                    }
                    idMap.put(sysDataPrivilege.getUuid(), "");
                    sysDataPrivilege.setCreateTime(new Date());
                    sysDataPrivilegeMapper.insert(sysDataPrivilege);




                    /**
                     * 处理子表，如果子表有数据则更新外键，没有则初始化一条数据
                     */
                    SysDataPrivilegeRulesSearch sysDataPrivilegeRulesSearch = new SysDataPrivilegeRulesSearch();
                    sysDataPrivilegeRulesSearch.setDataPrivilegeUuid(sysDataPrivilege.getUuid());
                    List<SysDataPrivilegeRulesList> sysDataPrivilegeRulesLists = sysDataPrivilegeRulesMapper.findByCondition(sysDataPrivilegeRulesSearch);
                    if (CollectionUtils.isEmpty(sysDataPrivilegeRulesLists)) { // 对于新增的权限注解，默认规则类型是“本人可见”
                        SysDataPrivilegeRules sysDataPrivilegeRules = new SysDataPrivilegeRules();
                        sysDataPrivilegeRules.setDataPrivilegeId(sysDataPrivilege.getId());
                        sysDataPrivilegeRules.setDataPrivilegeUuid(sysDataPrivilege.getUuid());
                        sysDataPrivilegeRules.setType("100905");
                        sysDataPrivilegeRules.setExpression(" creater_id = #currentUserId");
                        sysDataPrivilegeRules.setCreateTime(new Date());
                        sysDataPrivilegeRulesMapper.insert(sysDataPrivilegeRules);
                    }else{
                        for (SysDataPrivilegeRulesList sysDataPrivilegeRulesList : sysDataPrivilegeRulesLists) {
                            sysDataPrivilegeRulesList.setDataPrivilegeId(sysDataPrivilege.getId());
                            sysDataPrivilegeRulesMapper.updateByPrimaryKey(sysDataPrivilegeRulesList);
                        }
                    }
                }



                /**
                 * 放入缓存，以便SQL拦截器使用
                 */
                SysDataPrivilegeRulesSearch sysDataPrivilegeRulesSearch = new SysDataPrivilegeRulesSearch();
                List<SysDataPrivilegeRulesList> sysDataPrivilegeRulesLists = sysDataPrivilegeRulesMapper.findByCondition(sysDataPrivilegeRulesSearch);
                if (CollectionUtils.isNotEmpty(sysDataPrivilegeRulesLists)) {
                    Map<String, List<SysDataPrivilegeRulesList>> map = groupByDataPrivilegeUuid(sysDataPrivilegeRulesLists);
                    String key1 = "dataPrivilege_" + ctx.getCompanyId();
                    springRedisTools.deleteByKey(key1);
                    springRedisTools.addData(key1, JSONObject.toJSONString(map));
                }
            }
        }finally {
            springRedisTools.deleteByKey(key);
        }
    }



    /**
     * 根据dataPrivilegeUuid分组
     * @param sysDataPrivilegeRulesLists
     * @return
     */
    private Map<String,List<SysDataPrivilegeRulesList>> groupByDataPrivilegeUuid(List<SysDataPrivilegeRulesList> sysDataPrivilegeRulesLists){
        Map<String,List<SysDataPrivilegeRulesList>> map = new HashMap<>(50);
        int size = sysDataPrivilegeRulesLists.size();
        for (int i = 0;i < size; i++) {
            String dataPrivilegeUuid = sysDataPrivilegeRulesLists.get(i).getDataPrivilegeUuid();
            if(map.containsKey(dataPrivilegeUuid)){
                List<SysDataPrivilegeRulesList> list = map.get(dataPrivilegeUuid);
                list.add(sysDataPrivilegeRulesLists.get(i));
                map.put(dataPrivilegeUuid,list);
            }else{
                List<SysDataPrivilegeRulesList> list = new ArrayList<>(10);
                list.add(sysDataPrivilegeRulesLists.get(i));
                map.put(dataPrivilegeUuid,list);
            }
        }
        return map;
    }
}
