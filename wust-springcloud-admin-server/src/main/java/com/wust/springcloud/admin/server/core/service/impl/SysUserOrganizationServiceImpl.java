package com.wust.springcloud.admin.server.core.service.impl;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wust.springcloud.admin.server.core.dao.SysCompanyMapper;
import com.wust.springcloud.admin.server.core.dao.SysOrganizationMapper;
import com.wust.springcloud.admin.server.core.dao.SysUserMapper;
import com.wust.springcloud.admin.server.core.dao.SysUserOrganizationMapper;
import com.wust.springcloud.admin.server.core.service.SysUserOrganizationService;
import com.wust.springcloud.common.context.DefaultBusinessContext;
import com.wust.springcloud.common.dao.IBaseMapper;
import com.wust.springcloud.common.entity.sys.organization.SysOrganization;
import com.wust.springcloud.common.entity.sys.user.SysUser;
import com.wust.springcloud.common.entity.sys.userorganization.SysUserOrganization;
import com.wust.springcloud.common.enums.DataDictionaryEnum;
import com.wust.springcloud.common.enums.RedisKeyEnum;
import com.wust.springcloud.common.service.BaseServiceImpl;
import com.wust.springcloud.common.util.cache.SpringRedisTools;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

/**
 * Created by WST on 2019/4/18.
 */
@Service("sysUserOrganizationServiceImpl")
public class SysUserOrganizationServiceImpl extends BaseServiceImpl implements SysUserOrganizationService {
    @Autowired
    private SysUserOrganizationMapper sysUserOrganizationMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysOrganizationMapper sysOrganizationMapper;

    @Autowired
    private SysCompanyMapper sysCompanyMapper;

    @Autowired
    private SpringRedisTools springRedisTools;

    @Override
    protected IBaseMapper getBaseMapper() {
        return sysUserOrganizationMapper;
    }

    private DefaultBusinessContext ctx;

    @Transactional(rollbackFor=Exception.class)
    @Override
    public void init(JSONObject jsonObject) {
        this.ctx = jsonObject.getObject("ctx",DefaultBusinessContext.class);

        sysUserOrganizationMapper.deleteAll();

        SysUser sysUser = new SysUser();
        List<SysUser> sysUserList = sysUserMapper.select(sysUser);
        if(CollectionUtils.isNotEmpty(sysUserList)){
            for (SysUser user : sysUserList) {
                String type = user.getType();
                Long userId = user.getId();

                SysOrganization sysOrganizationSearch = new SysOrganization();
                sysOrganizationSearch.setRelationId(userId);
                sysOrganizationSearch.setType(DataDictionaryEnum.ORGANIZATION_TYPE_USER.getStringValue());
                List<SysOrganization> sysOrganizations =  sysOrganizationMapper.select(sysOrganizationSearch);
                if(CollectionUtils.isNotEmpty(sysOrganizations)) {
                    if (DataDictionaryEnum.USER_TYPE_PLATFORM_ADMIN.getStringValue().equals(type)) { // 平台管理员
                    } else if (DataDictionaryEnum.USER_TYPE_PLATFORM_USER.getStringValue().equals(type)) { // 平台普通用户
                    } else if (DataDictionaryEnum.USER_TYPE_AGENT.getStringValue().equals(type)
                            || DataDictionaryEnum.USER_TYPE_PARENT_COMPANY.getStringValue().equals(type)
                            || DataDictionaryEnum.USER_TYPE_BRANCH_COMPANY.getStringValue().equals(type)) { // 运营管理员
                        List<SysUserOrganization> sysUserOrganizations = new ArrayList<>(10);
                        for (SysOrganization sysOrganization : sysOrganizations) {
                            SysUserOrganization sysUserOrganization = new SysUserOrganization();
                            sysUserOrganization.setUserId(userId);
                            sysUserOrganization.setCreaterId(ctx.getUser().getId());
                            sysUserOrganization.setCreaterName(ctx.getUser().getRealName());
                            sysUserOrganization.setCreateTime(new Date());
                            sysUserOrganizations.add(sysUserOrganization);

                            lookupByBusinessAdmin(sysOrganization.getPid(),sysUserOrganization,sysUserOrganizations);
                        }
                        sysUserOrganizationMapper.insertList(sysUserOrganizations);
                    } else if (DataDictionaryEnum.USER_TYPE_BUSINESS.getStringValue().equals(type)) { // 业务操作员
                        List<SysUserOrganization> sysUserOrganizations = new ArrayList<>(10);
                        for (SysOrganization sysOrganization : sysOrganizations) {
                            SysUserOrganization sysUserOrganization = new SysUserOrganization();
                            sysUserOrganization.setUserId(userId);
                            sysUserOrganization.setCreaterId(ctx.getUser().getId());
                            sysUserOrganization.setCreaterName(ctx.getUser().getRealName());
                            sysUserOrganization.setCreateTime(new Date());
                            sysUserOrganizations.add(sysUserOrganization);

                            lookupByBusinessUser(sysOrganization.getPid(),sysUserOrganization,sysUserOrganizations);
                        }
                        sysUserOrganizationMapper.insertList(sysUserOrganizations);
                    }
                }
            }
        }


        SysUserOrganization sysUserOrganization = new SysUserOrganization();
        List<SysUserOrganization> sysUserOrganizations = sysUserOrganizationMapper.select(sysUserOrganization);
        Map<Long,JSONArray> map = groupUserOrganizationByUserId(sysUserOrganizations);
        if(map != null && map.size() > 0){
            Set<Long> keySet = map.keySet();
            for (Long userId : keySet) {
                SysUser user = this.sysUserMapper.selectByPrimaryKey(userId);
                if(DataDictionaryEnum.USER_TYPE_BUSINESS.getStringValue().equals(user.getType())){ // 业务员账号
                    JSONArray jsonArray = map.get(userId);
                    user.setCompanyId(jsonArray.getLong(0));
                    this.sysUserMapper.updateByPrimaryKey(user);
                }else{ // 非业务员账号，则缓存其有权看到的公司id
                    JSONArray jsonArray = map.get(userId);
                    springRedisTools.deleteByKey(String.format(RedisKeyEnum.REDIS_TABLE_KEY_CURRENT_USER_BRANCH_COMPANY_ID.getStringValue(),userId));
                    springRedisTools.addData(String.format(RedisKeyEnum.REDIS_TABLE_KEY_CURRENT_USER_BRANCH_COMPANY_ID.getStringValue(),userId),jsonArray.toJSONString());
                }
            }
        }
    }

    /**
     *  运营方账号有三种：代理商的账号、总公司的账号、分公司的账号。分三种情况搜寻：
     *  1.代理商账号搜寻方式：先向上递归搜寻到代理商，再向下递归搜寻到分公司(搜寻路径：账号->角色->部门->代理商)；
     *  2.总公司账号搜寻方式：先向上递归搜寻到代理商，再向下递归搜寻到分公司(搜寻路径：账号->角色->部门->总公司->代理商)；
     *  3.分公司账号搜寻方式：直接向上递归搜寻到代理商(搜寻路径：账号->角色->部门->分公司->总公司->代理商)。
     *
     *  递归到顶层即代理层后，需要分情况判断是否需要向下递归扎到总公司或者分公司。1路径需要向下递归找到总公司和分公司，2需要向下递归找到分公司。
     * @param organizationId
     * @param sysUserOrganization
     */
    private void lookupByBusinessAdmin(Long organizationId,final SysUserOrganization sysUserOrganization,final List<SysUserOrganization> sysUserOrganizations){
        SysOrganization sysOrganizationSearch = new SysOrganization();
        sysOrganizationSearch.setId(organizationId);
        SysOrganization sysOrganization = sysOrganizationMapper.selectOne(sysOrganizationSearch);
        if(sysOrganization != null){
            if(DataDictionaryEnum.ORGANIZATION_TYPE_ROLE.getStringValue().equals(sysOrganization.getType())){
                sysUserOrganization.setRoleId(sysOrganization.getRelationId());
                lookupByBusinessAdmin(sysOrganization.getPid(),sysUserOrganization,sysUserOrganizations);
            }else if(DataDictionaryEnum.ORGANIZATION_TYPE_DEPARTMENT.getStringValue().equals(sysOrganization.getType())){
                sysUserOrganization.setDepartmentId(sysOrganization.getRelationId());
                lookupByBusinessAdmin(sysOrganization.getPid(),sysUserOrganization,sysUserOrganizations);
            }else if(DataDictionaryEnum.ORGANIZATION_TYPE_BRANCH_COMPANY.getStringValue().equals(sysOrganization.getType())){
                sysUserOrganization.setBranchCompanyId(sysOrganization.getRelationId());
                lookupByBusinessAdmin(sysOrganization.getPid(),sysUserOrganization,sysUserOrganizations);
            }else if(DataDictionaryEnum.ORGANIZATION_TYPE_PARENT_COMPANY.getStringValue().equals(sysOrganization.getType())){
                sysUserOrganization.setParentCompanyId(sysOrganization.getRelationId());
                lookupByBusinessAdmin(sysOrganization.getPid(),sysUserOrganization,sysUserOrganizations);
            }else if(DataDictionaryEnum.ORGANIZATION_TYPE_AGENT.getStringValue().equals(sysOrganization.getType())){
                sysUserOrganization.setAgentId(sysOrganization.getRelationId());

                // 向上递归到次结束，开始向下递归找到所有总公司
                if(sysUserOrganization.getParentCompanyId() == null){
                    lookupParentCompany(sysUserOrganizations);
                }


                // 找到总公司后，开始向下找到所有分公司
                if(sysUserOrganization.getBranchCompanyId() == null){
                    lookupBranchCompany(sysUserOrganizations);
                }
            }
        }
    }


    /**
     *  业务操作方账号搜寻路径：账号->角色->部门->项目->分公司->总公司->代理商
     *
     * @param organizationId
     * @param sysUserOrganization
     */
    private void lookupByBusinessUser(Long organizationId,final SysUserOrganization sysUserOrganization,final List<SysUserOrganization> sysUserOrganizations){
        SysOrganization sysOrganizationSearch = new SysOrganization();
        sysOrganizationSearch.setId(organizationId);
        SysOrganization sysOrganization = sysOrganizationMapper.selectOne(sysOrganizationSearch);
        if(sysOrganization != null){
            if(DataDictionaryEnum.ORGANIZATION_TYPE_ROLE.getStringValue().equals(sysOrganization.getType())){
                sysUserOrganization.setRoleId(sysOrganization.getRelationId());
                lookupByBusinessUser(sysOrganization.getPid(),sysUserOrganization,sysUserOrganizations);
            }else if(DataDictionaryEnum.ORGANIZATION_TYPE_DEPARTMENT.getStringValue().equals(sysOrganization.getType())){
                sysUserOrganization.setDepartmentId(sysOrganization.getRelationId());
                lookupByBusinessUser(sysOrganization.getPid(),sysUserOrganization,sysUserOrganizations);
            }else if(DataDictionaryEnum.ORGANIZATION_TYPE_PROJECT.getStringValue().equals(sysOrganization.getType())){
                sysUserOrganization.setProjectId(sysOrganization.getRelationId());
                lookupByBusinessUser(sysOrganization.getPid(),sysUserOrganization,sysUserOrganizations);
            }else if(DataDictionaryEnum.ORGANIZATION_TYPE_BRANCH_COMPANY.getStringValue().equals(sysOrganization.getType())){
                sysUserOrganization.setBranchCompanyId(sysOrganization.getRelationId());
                lookupByBusinessUser(sysOrganization.getPid(),sysUserOrganization,sysUserOrganizations);
            }else if(DataDictionaryEnum.ORGANIZATION_TYPE_PARENT_COMPANY.getStringValue().equals(sysOrganization.getType())){
                sysUserOrganization.setParentCompanyId(sysOrganization.getRelationId());
                lookupByBusinessUser(sysOrganization.getPid(),sysUserOrganization,sysUserOrganizations);
            }else if(DataDictionaryEnum.ORGANIZATION_TYPE_AGENT.getStringValue().equals(sysOrganization.getType())){
                sysUserOrganization.setAgentId(sysOrganization.getRelationId());
            }
        }
    }

    /**
     * 从上往下找总公司
     * @param parentCompanyNullList
     */
    private void lookupParentCompany(List<SysUserOrganization> parentCompanyNullList){
        List<SysUserOrganization> newList = new ArrayList<>();
        CollectionUtils.addAll(newList, new Object[parentCompanyNullList.size()]);
        Collections.copy(newList, parentCompanyNullList);
        for (SysUserOrganization temp : newList) {
            if(temp.getParentCompanyId() == null){
                SysOrganization sysOrganizationSearch = new SysOrganization();
                sysOrganizationSearch.setType(DataDictionaryEnum.ORGANIZATION_TYPE_AGENT.getStringValue());
                sysOrganizationSearch.setRelationId(temp.getAgentId());
                SysOrganization sysOrganization = sysOrganizationMapper.selectOne(sysOrganizationSearch);

                sysOrganizationSearch = new SysOrganization();
                sysOrganizationSearch.setPid(sysOrganization.getId());
                sysOrganizationSearch.setType(DataDictionaryEnum.ORGANIZATION_TYPE_PARENT_COMPANY.getStringValue());
                List<SysOrganization> sysOrganizations = sysOrganizationMapper.select(sysOrganizationSearch);
                if(CollectionUtils.isNotEmpty(sysOrganizations)){
                    temp.setParentCompanyId(sysOrganizations.get(0).getRelationId());

                    if(sysOrganizations.size() > 1){
                        for(int i=1;i<sysOrganizations.size();i++){
                            SysOrganization sysOrganizationParentCompany = sysOrganizations.get(i);
                            SysUserOrganization sysUserOrganizationNew = new SysUserOrganization();
                            BeanUtils.copyProperties(temp,sysUserOrganizationNew);
                            sysUserOrganizationNew.setParentCompanyId(sysOrganizationParentCompany.getRelationId());
                            parentCompanyNullList.add(sysUserOrganizationNew);
                        }
                    }
                }
            }
        }
    }


    /**
     * 从上往下找分公司
     * @param branchCompanyNullList
     */
    private void lookupBranchCompany(List<SysUserOrganization> branchCompanyNullList){
        List<SysUserOrganization> newList = new ArrayList<>();
        CollectionUtils.addAll(newList, new Object[branchCompanyNullList.size()]);
        Collections.copy(newList, branchCompanyNullList);
        for (SysUserOrganization temp : newList) {
            if(temp.getBranchCompanyId() == null){
                SysOrganization sysOrganizationSearch = new SysOrganization();
                sysOrganizationSearch.setType(DataDictionaryEnum.ORGANIZATION_TYPE_PARENT_COMPANY.getStringValue());
                sysOrganizationSearch.setRelationId(temp.getParentCompanyId());
                SysOrganization sysOrganization = sysOrganizationMapper.selectOne(sysOrganizationSearch);

                sysOrganizationSearch = new SysOrganization();
                sysOrganizationSearch.setPid(sysOrganization.getId());
                sysOrganizationSearch.setType(DataDictionaryEnum.ORGANIZATION_TYPE_BRANCH_COMPANY.getStringValue());
                List<SysOrganization> sysOrganizations = sysOrganizationMapper.select(sysOrganizationSearch);
                if(CollectionUtils.isNotEmpty(sysOrganizations)){
                    temp.setBranchCompanyId(sysOrganizations.get(0).getRelationId());

                    /**
                     * 有多个分公司，则复制对应的记录
                     */
                    if(sysOrganizations.size() > 1){
                        for(int i=1;i<sysOrganizations.size();i++){
                            SysOrganization sysOrganizationBranchCompany = sysOrganizations.get(i);
                            SysUserOrganization sysUserOrganizationNew = new SysUserOrganization();
                            BeanUtils.copyProperties(temp,sysUserOrganizationNew);
                            sysUserOrganizationNew.setBranchCompanyId(sysOrganizationBranchCompany.getRelationId());
                            branchCompanyNullList.add(sysUserOrganizationNew);
                        }
                    }
                }
            }
        }
    }

    private Map<Long, JSONArray> groupUserOrganizationByUserId(List<SysUserOrganization> sysUserOrganizations){
        Map<Long, JSONArray> map = new HashMap<>();
        if(CollectionUtils.isNotEmpty(sysUserOrganizations)){
            for (SysUserOrganization sysUserOrganization : sysUserOrganizations) {
                long userId = sysUserOrganization.getUserId();
                if(map.containsKey(userId)){
                    JSONArray jsonArray = map.get(userId);
                    jsonArray.add(sysUserOrganization.getBranchCompanyId());
                }else{
                    JSONArray jsonArray = new JSONArray();
                    jsonArray.add(sysUserOrganization.getBranchCompanyId());
                    map.put(userId,jsonArray);
                }
            }
        }
        return map;
    }
}
