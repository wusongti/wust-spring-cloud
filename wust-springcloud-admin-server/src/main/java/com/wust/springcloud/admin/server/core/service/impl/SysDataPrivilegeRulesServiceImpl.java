package com.wust.springcloud.admin.server.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.wust.springcloud.admin.server.core.dao.SysDataPrivilegeRulesMapper;
import com.wust.springcloud.admin.server.core.service.SysDataPrivilegeRulesService;
import com.wust.springcloud.common.context.DefaultBusinessContext;
import com.wust.springcloud.common.dto.ResponseDto;
import com.wust.springcloud.common.entity.sys.dataprivilege.rules.SysDataPrivilegeRules;
import com.wust.springcloud.common.entity.sys.dataprivilege.rules.SysDataPrivilegeRulesList;
import com.wust.springcloud.common.entity.sys.dataprivilege.rules.SysDataPrivilegeRulesSearch;
import com.wust.springcloud.common.util.cache.SpringRedisTools;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by WST on 2019/6/11.
 */
@Service("sysDataPrivilegeRulesServiceImpl")
public class SysDataPrivilegeRulesServiceImpl implements SysDataPrivilegeRulesService {
    @Autowired
    private SysDataPrivilegeRulesMapper sysDataPrivilegeRulesMapper;

    @Autowired
    private SpringRedisTools springRedisTools;

    @Override
    public List<SysDataPrivilegeRulesList> listPage(SysDataPrivilegeRulesSearch search) {
        return sysDataPrivilegeRulesMapper.listPage(search);
    }

    @Override
    public List<SysDataPrivilegeRulesList> findByCondition(SysDataPrivilegeRulesSearch search) {
        return sysDataPrivilegeRulesMapper.findByCondition(search);
    }



    @Transactional(rollbackFor=Exception.class)
    @Override
    public ResponseDto update(String dataPrivilegeId, String types) {
        ResponseDto mm = new ResponseDto();
        DefaultBusinessContext ctx = DefaultBusinessContext.getContext();
        SysDataPrivilegeRulesSearch sysDataPrivilegeRulesSearch = new SysDataPrivilegeRulesSearch();
        sysDataPrivilegeRulesSearch.setDataPrivilegeId(dataPrivilegeId);
        List<SysDataPrivilegeRulesList>  sysDataPrivilegeRulesLists = sysDataPrivilegeRulesMapper.findByCondition(sysDataPrivilegeRulesSearch);
        if(CollectionUtils.isNotEmpty(sysDataPrivilegeRulesLists)){
            List<String> ids = new ArrayList<>(sysDataPrivilegeRulesLists.size());
            for (SysDataPrivilegeRulesList sysDataPrivilegeRulesList : sysDataPrivilegeRulesLists) {
                ids.add(sysDataPrivilegeRulesList.getId());
            }

            sysDataPrivilegeRulesMapper.batchDelete(ids);

            String[] typeArray = types.split(",");
            List<SysDataPrivilegeRules> createEntities = new ArrayList<>(typeArray.length);
            for (String s : typeArray) {
                SysDataPrivilegeRules sysDataPrivilegeRules = new SysDataPrivilegeRules();
                sysDataPrivilegeRules.setCreaterId(ctx.getUserId());
                sysDataPrivilegeRules.setCreaterName(ctx.getLoginName());
                sysDataPrivilegeRules.setDataPrivilegeId(dataPrivilegeId);
                sysDataPrivilegeRules.setType(s);
                if("100905".equals(s)){ // 本人可见
                    sysDataPrivilegeRules.setExpression("creater_id = #currentUserId");
                }else if("100910".equals(s)){ // 角色可见
                    sysDataPrivilegeRules.setExpression("creater_id IN(SELECT u1.relation_id FROM sys_organization role LEFT JOIN sys_organization u ON role.id = u.pid  LEFT JOIN sys_organization u1 ON role.id = u1.pid WHERE u.relation_id = #currentUserId)");
                }else if("100915".equals(s)){ // 部门可见
                    sysDataPrivilegeRules.setExpression("creater_id IN(SELECT t_user.relation_id FROM(SELECT department.id FROM sys_organization department LEFT JOIN sys_organization role ON department.id = role.pid LEFT JOIN sys_organization u ON role.id = u.pid  WHERE u.relation_id = #currentUserId)t_department  LEFT JOIN  sys_organization t_role ON t_department.id = t_role.pid LEFT JOIN  sys_organization t_user ON t_role.id = t_user.pid)");
                }else if("100920".equals(s)){ // 公司可见
                    sysDataPrivilegeRules.setExpression("creater_id IN(SELECT t_user. relation_id FROM sys_organization t_company LEFT JOIN sys_organization t_department ON t_company.id = t_department.pid LEFT JOIN sys_organization t_role ON t_department.id = t_role.pid LEFT JOIN sys_organization t_user ON t_role.id = t_user.pid WHERE t_company.relation_id = #currentCompanyId)");
                }else if("100925".equals(s)){ // 领导可见
                    sysDataPrivilegeRules.setExpression("creater_id IN(SELECT t_user. relation_id FROM sys_organization t_company LEFT JOIN sys_organization t_department ON t_company.id = t_department.pid LEFT JOIN sys_organization t_role ON t_department.id = t_role.pid LEFT JOIN sys_organization t_user ON t_role.id = t_user.pid WHERE t_company.relation_id = #currentCompanyId)");
                }
                createEntities.add(sysDataPrivilegeRules);
            }
            sysDataPrivilegeRulesMapper.batchInsert(createEntities);




            /**
             * 放入缓存，以便SQL拦截器使用
             */
            sysDataPrivilegeRulesSearch = new SysDataPrivilegeRulesSearch();
            sysDataPrivilegeRulesLists = sysDataPrivilegeRulesMapper.findByCondition(sysDataPrivilegeRulesSearch);
            if(CollectionUtils.isNotEmpty(sysDataPrivilegeRulesLists)){
                Map<String,List<SysDataPrivilegeRulesList>> map = groupByDataPrivilegeId(sysDataPrivilegeRulesLists);
                String key = "dataPrivilege_" + ctx.getCompanyId();
                springRedisTools.deleteByKey(key);
                springRedisTools.addData(key, JSONObject.toJSONString(map));
            }
        }else{
            mm.setFlag(ResponseDto.INFOR_WARNING);
            mm.setMessage("该记录不存在，可能是刚刚被其他用户删除了");
        }
        return mm;
    }

    /**
     * 根据dataPrivilegeId分组
     * @param sysDataPrivilegeRulesLists
     * @return
     */
    private Map<String,List<SysDataPrivilegeRulesList>> groupByDataPrivilegeId(List<SysDataPrivilegeRulesList> sysDataPrivilegeRulesLists){
        Map<String,List<SysDataPrivilegeRulesList>> map = new HashMap<>(50);
        int size = sysDataPrivilegeRulesLists.size();
        for (int i = 0;i < size; i++) {
            String dataPrivilegeId = sysDataPrivilegeRulesLists.get(i).getDataPrivilegeId();
            if(map.containsKey(dataPrivilegeId)){
                List<SysDataPrivilegeRulesList> list = map.get(dataPrivilegeId);
                list.add(sysDataPrivilegeRulesLists.get(i));
                map.put(dataPrivilegeId,list);
            }else{
                List<SysDataPrivilegeRulesList> list = new ArrayList<>(10);
                list.add(sysDataPrivilegeRulesLists.get(i));
                map.put(dataPrivilegeId,list);
            }
        }
        return map;
    }
}
