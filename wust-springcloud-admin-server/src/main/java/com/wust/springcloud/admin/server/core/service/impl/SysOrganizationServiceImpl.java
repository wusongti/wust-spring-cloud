package com.wust.springcloud.admin.server.core.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wust.springcloud.admin.server.core.dao.*;
import com.wust.springcloud.admin.server.core.service.SysOrganizationService;
import com.wust.springcloud.common.context.DefaultBusinessContext;
import com.wust.springcloud.common.dao.IBaseMapper;
import com.wust.springcloud.common.dto.ResponseDto;
import com.wust.springcloud.common.entity.sys.company.SysCompany;
import com.wust.springcloud.common.entity.sys.department.SysDepartment;
import com.wust.springcloud.common.entity.sys.organization.SysOrganization;
import com.wust.springcloud.common.entity.sys.organization.SysOrganizationSearch;
import com.wust.springcloud.common.entity.sys.project.SysProject;
import com.wust.springcloud.common.entity.sys.resource.SysResource;
import com.wust.springcloud.common.entity.sys.role.SysRole;
import com.wust.springcloud.common.entity.sys.role.resource.SysRoleResource;
import com.wust.springcloud.common.entity.sys.user.SysUser;
import com.wust.springcloud.common.enums.ApplicationEnum;
import com.wust.springcloud.common.enums.DataDictionaryEnum;
import com.wust.springcloud.common.service.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * Created by WST on 2019/6/3.
 */
@Service("sysOrganizationServiceImpl")
public class SysOrganizationServiceImpl extends BaseServiceImpl implements SysOrganizationService {
    @Autowired
    private SysOrganizationMapper sysOrganizationMapper;

    @Autowired
    private SysCompanyMapper sysCompanyMapper;

    @Autowired
    private SysProjectMapper sysProjectMapper;

    @Autowired
    private SysDepartmentMapper sysDepartmentMapper;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysRoleResourceMapper sysRoleResourceMapper;

    @Autowired
    private SysResourceMapper sysResourceMapper;

    @Override
    protected IBaseMapper getBaseMapper() {
        return sysOrganizationMapper;
    }

    @Override
    public ResponseDto buildOrganizationTree(SysOrganizationSearch search) {
        ResponseDto responseDto = new ResponseDto();
        DefaultBusinessContext ctx = DefaultBusinessContext.getContext();

        final JSONArray jsonArray = new JSONArray();
        JSONObject rootJSONObject = new JSONObject();
        jsonArray.add(rootJSONObject);
        rootJSONObject.put("id","-1");
        rootJSONObject.put("pId",null);
        rootJSONObject.put("name","企业基础平台组织架构");
        rootJSONObject.put("type","");
        rootJSONObject.put("relationId",null);
        rootJSONObject.put("open",true);

        SysOrganization sysOrganizationSearch = new SysOrganization();
        List<SysOrganization> sysOrganizationLists = sysOrganizationMapper.select(sysOrganizationSearch);
        if(org.apache.commons.collections.CollectionUtils.isNotEmpty(sysOrganizationLists)){
            Map<Long,SysOrganization> result = groupById(sysOrganizationLists);
            if(DataDictionaryEnum.USER_TYPE_PLATFORM_ADMIN.getStringValue().equals(ctx.getUserType())
                    || DataDictionaryEnum.USER_TYPE_PLATFORM_USER.getStringValue().equals(ctx.getUserType())){
                setRelationName(jsonArray,sysOrganizationLists);
            }else if(DataDictionaryEnum.USER_TYPE_AGENT.getStringValue().equals(ctx.getUserType())){
                /**
                 * 按照pid分组组织架构
                 */
                Map<Long,List<SysOrganization>> groupByPidMap = groupByPid(sysOrganizationLists);


                /**
                 * 根据当前登录用户获取其对应的组织架构id
                 */
                sysOrganizationSearch = new SysOrganization();
                sysOrganizationSearch.setType(DataDictionaryEnum.ORGANIZATION_TYPE_USER.getStringValue());
                sysOrganizationSearch.setRelationId(ctx.getUserId());
                SysOrganization sysOrganization = sysOrganizationMapper.selectOne(sysOrganizationSearch);


                /**
                 * 根据当前代理商用户向上递归获取代理商对应的组织架构id
                 */
                JSONObject organizationId4agent = new JSONObject();
                lookupOrganizationIdByType(result,sysOrganization,DataDictionaryEnum.ORGANIZATION_TYPE_AGENT.getStringValue(),organizationId4agent);


                /**
                 * 根据代理商组织架构id向下递归获取其所有组织架构
                 */
                final List<SysOrganization> organizations4agent = new ArrayList<>(100);
                SysOrganization organization4agent = sysOrganizationMapper.selectByPrimaryKey(organizationId4agent);
                organizations4agent.add(organization4agent);
                lookupOrganizationByPid(groupByPidMap,organizationId4agent.getLong("id"),organizations4agent);


                /**
                 * 设置组织架构节点名称
                 */
                setRelationName(jsonArray,organizations4agent);
            }else if(DataDictionaryEnum.USER_TYPE_PARENT_COMPANY.getStringValue().equals(ctx.getUserType())){
                /**
                 * 按照pid分组组织架构
                 */
                Map<Long,List<SysOrganization>> groupByPidMap = groupByPid(sysOrganizationLists);


                /**
                 * 根据当前登录用户获取其对应的组织架构id
                 */
                sysOrganizationSearch = new SysOrganization();
                sysOrganizationSearch.setType(DataDictionaryEnum.ORGANIZATION_TYPE_USER.getStringValue());
                sysOrganizationSearch.setRelationId(ctx.getUserId());
                SysOrganization sysOrganization = sysOrganizationMapper.selectOne(sysOrganizationSearch);


                /**
                 * 根据当前总公司用户向上递归获取总公司对应的组织架构id
                 */
                JSONObject organizationId4parentCompany = new JSONObject();
                lookupOrganizationIdByType(result,sysOrganization,DataDictionaryEnum.ORGANIZATION_TYPE_PARENT_COMPANY.getStringValue(),organizationId4parentCompany);


                /**
                 * 根据总公司组织架构id向下递归获取其所有组织架构
                 */
                final List<SysOrganization> organizations4parentCompany = new ArrayList<>(100);
                SysOrganization organization4parentCompany = sysOrganizationMapper.selectByPrimaryKey(organizationId4parentCompany);
                organizations4parentCompany.add(organization4parentCompany);
                lookupOrganizationByPid(groupByPidMap,organizationId4parentCompany.getLong("id"),organizations4parentCompany);


                /**
                 * 设置组织架构节点名称
                 */
                setRelationName(jsonArray,organizations4parentCompany);
            }else if(DataDictionaryEnum.USER_TYPE_BRANCH_COMPANY.getStringValue().equals(ctx.getUserType())){
                /**
                 * 按照pid分组组织架构
                 */
                Map<Long,List<SysOrganization>> groupByPidMap = groupByPid(sysOrganizationLists);


                /**
                 * 根据当前登录用户获取其对应的组织架构id
                 */
                sysOrganizationSearch = new SysOrganization();
                sysOrganizationSearch.setType(DataDictionaryEnum.ORGANIZATION_TYPE_USER.getStringValue());
                sysOrganizationSearch.setRelationId(ctx.getUserId());
                SysOrganization sysOrganization = sysOrganizationMapper.selectOne(sysOrganizationSearch);


                /**
                 * 根据当前分公司用户向上递归获取分公司对应的组织架构id
                 */
                JSONObject organizationId4branchCompany = new JSONObject();
                lookupOrganizationIdByType(result,sysOrganization,DataDictionaryEnum.ORGANIZATION_TYPE_BRANCH_COMPANY.getStringValue(),organizationId4branchCompany);


                /**
                 * 根据代分公司组织架构id向下递归获取其所有组织架构
                 */
                final List<SysOrganization> organizations4branchCompany = new ArrayList<>(100);
                SysOrganization organization4branchCompany = sysOrganizationMapper.selectByPrimaryKey(organizationId4branchCompany);
                organizations4branchCompany.add(organization4branchCompany);
                lookupOrganizationByPid(groupByPidMap,organizationId4branchCompany.getLong("id"),organizations4branchCompany);


                /**
                 * 设置组织架构节点名称
                 */
                setRelationName(jsonArray,organizations4branchCompany);
            }else if(DataDictionaryEnum.USER_TYPE_BUSINESS.getStringValue().equals(ctx.getUserType())){
                /**
                 * 按照pid分组组织架构
                 */
                Map<Long,List<SysOrganization>> groupByPidMap = groupByPid(sysOrganizationLists);


                /**
                 * 根据当前登录用户获取其对应的组织架构id
                 */
                sysOrganizationSearch = new SysOrganization();
                sysOrganizationSearch.setType(DataDictionaryEnum.ORGANIZATION_TYPE_USER.getStringValue());
                sysOrganizationSearch.setRelationId(ctx.getUserId());
                SysOrganization sysOrganization = sysOrganizationMapper.selectOne(sysOrganizationSearch);


                /**
                 * 根据当前分公司用户向上递归获取分公司对应的组织架构id
                 */
                JSONObject organizationId4branchCompany = new JSONObject();
                lookupOrganizationIdByType(result,sysOrganization,DataDictionaryEnum.ORGANIZATION_TYPE_BRANCH_COMPANY.getStringValue(),organizationId4branchCompany);


                /**
                 * 根据代分公司组织架构id向下递归获取其所有组织架构
                 */
                final List<SysOrganization> organizations4branchCompany = new ArrayList<>(100);
                SysOrganization organization4branchCompany = sysOrganizationMapper.selectByPrimaryKey(organizationId4branchCompany);
                organizations4branchCompany.add(organization4branchCompany);
                lookupOrganizationByPid(groupByPidMap,organizationId4branchCompany.getLong("id"),organizations4branchCompany);


                /**
                 * 设置组织架构节点名称
                 */
                setRelationName(jsonArray,organizations4branchCompany);
            }
        }
        responseDto.setObj(jsonArray.toJSONString());
        return responseDto;
    }

    @Override
    public ResponseDto setFunctionPermissions(JSONObject jsonObject) {
        ResponseDto mm = new ResponseDto();
        DefaultBusinessContext ctx = DefaultBusinessContext.getContext();
        Long organizationId = jsonObject.getLong("organizationId");

        JSONArray  jsonArray = jsonObject.getJSONArray("sysRoleResources");
        List<SysRoleResource> list = new ArrayList<>();
        for (Object object : jsonArray) {
            JSONObject j = (JSONObject)object;
            SysRoleResource sysRoleResource = new SysRoleResource();
            sysRoleResource.setOrganizationId(organizationId);
            sysRoleResource.setResourceId(j.getString("resourceId"));
            sysRoleResource.setType(j.getString("type"));
            sysRoleResource.setCreaterId(ctx.getUserId());
            sysRoleResource.setCreaterName(ctx.getRealName());
            sysRoleResource.setCreateTime(new Date());
            list.add(sysRoleResource);

            // 将所勾选的菜单下面的所有私有白名单权限也自动授予角色
            if(ApplicationEnum.MENU_TYPE_M.getStringValue().equalsIgnoreCase(sysRoleResource.getType())){
                List<SysResource> anonList = sysResourceMapper.findAnonResourcesByMenuId(sysRoleResource.getResourceId());
                if(!CollectionUtils.isEmpty(anonList)){
                    for(SysResource anonR : anonList){
                        SysRoleResource anon = new SysRoleResource();
                        anon.setOrganizationId(organizationId);
                        anon.setResourceId(anonR.getCode());
                        anon.setType(ApplicationEnum.MENU_TYPE_R.getStringValue());
                        anon.setCreateTime(new Date());
                        list.add(anon);
                    }
                }
            }
        }

        sysRoleResourceMapper.deleteByPrimaryKey(organizationId);

        sysRoleResourceMapper.insertList(list);
        return mm;
    }

    /**
     * 设置关系名称到组织架构
     * @param jsonArray
     * @param sysOrganizationLists
     */
    private void setRelationName(final JSONArray jsonArray,final List<SysOrganization> sysOrganizationLists){
        if(org.apache.commons.collections.CollectionUtils.isNotEmpty(sysOrganizationLists)){
            for (SysOrganization sysOrganizationList : sysOrganizationLists) {
                JSONObject jsonObject = new JSONObject();

                String type = sysOrganizationList.getType();
                Long relationId = sysOrganizationList.getRelationId();
                String name = "";
                Long pid = sysOrganizationList.getPid();

                if(DataDictionaryEnum.ORGANIZATION_TYPE_AGENT.getStringValue().equalsIgnoreCase(type)){
                    SysCompany sysCompanySearch = new SysCompany();
                    sysCompanySearch.setId(relationId);
                    SysCompany sysCompany = sysCompanyMapper.selectOne(sysCompanySearch);
                    if(sysCompany != null){
                        name = "代理商-" + sysCompany.getName();
                        pid = pid == null ? -1 : pid;
                    }
                }else if(DataDictionaryEnum.ORGANIZATION_TYPE_PARENT_COMPANY.getStringValue().equalsIgnoreCase(type)){
                    SysCompany sysCompanySearch = new SysCompany();
                    sysCompanySearch.setId(relationId);
                    SysCompany sysCompany = sysCompanyMapper.selectOne(sysCompanySearch);
                    if(sysCompany != null){
                        name = "总公司-" + sysCompany.getName();
                    }
                }else if(DataDictionaryEnum.ORGANIZATION_TYPE_BRANCH_COMPANY.getStringValue().equalsIgnoreCase(type)){
                    SysCompany sysCompanySearch = new SysCompany();
                    sysCompanySearch.setId(relationId);
                    SysCompany sysCompany = sysCompanyMapper.selectOne(sysCompanySearch);
                    if(sysCompany != null){
                        name = "分公司-" + sysCompany.getName();
                    }
                }else if(DataDictionaryEnum.ORGANIZATION_TYPE_PROJECT.getStringValue().equalsIgnoreCase(type)){
                    SysProject sysProjectSearch = new SysProject();
                    sysProjectSearch.setId(relationId);
                    SysProject sysProject = sysProjectMapper.selectOne(sysProjectSearch);
                    if(sysProject != null){
                        name = "项目-" + sysProject.getName();
                    }
                }else if(DataDictionaryEnum.ORGANIZATION_TYPE_DEPARTMENT.getStringValue().equalsIgnoreCase(type)){
                    SysDepartment sysDepartmentSearch = new SysDepartment();
                    sysDepartmentSearch.setId(relationId);
                    SysDepartment sysDepartment =  sysDepartmentMapper.selectOne(sysDepartmentSearch);
                    if(sysDepartment != null){
                        name = "部门-" + sysDepartment.getName();
                    }
                }else if(DataDictionaryEnum.ORGANIZATION_TYPE_ROLE.getStringValue().equalsIgnoreCase(type)){
                    SysRole sysRoleSearch = new SysRole();
                    sysRoleSearch.setId(relationId);
                    SysRole sysRole = sysRoleMapper.selectOne(sysRoleSearch);
                    if(sysRole != null){
                        name = "角色-" + sysRole.getName();
                    }
                }else if(DataDictionaryEnum.ORGANIZATION_TYPE_USER.getStringValue().equalsIgnoreCase(type)){
                    SysUser sysUserSearch = new SysUser();
                    sysUserSearch.setId(relationId);
                    SysUser sysUser = sysUserMapper.selectOne(sysUserSearch);
                    if(sysUser != null){
                        name = "用户-" + sysUser.getRealName() + "(" + sysUser.getLoginName() + ")";
                    }
                }

                jsonObject.put("id",sysOrganizationList.getId());
                jsonObject.put("pId",pid);
                jsonObject.put("name",name);
                jsonObject.put("type",type);
                jsonObject.put("relationId",relationId);
                jsonObject.put("open",name);
                jsonArray.add(jsonObject);
            }
        }
    }

    private Map<Long,SysOrganization> groupById(final List<SysOrganization> sysOrganizationLists){
        Map<Long,SysOrganization> result = new HashMap<>(sysOrganizationLists.size());
        if(org.apache.commons.collections.CollectionUtils.isNotEmpty(sysOrganizationLists)){
            for (SysOrganization sysOrganizationList : sysOrganizationLists) {
                result.put(sysOrganizationList.getId(),sysOrganizationList);
            }
        }
        return result;
    }

    private Map<Long,List<SysOrganization>> groupByPid(final List<SysOrganization> sysOrganizationLists){
        Map<Long,List<SysOrganization>> result = new HashMap<>(sysOrganizationLists.size());
        if(org.apache.commons.collections.CollectionUtils.isNotEmpty(sysOrganizationLists)){
            for (SysOrganization sysOrganizationList : sysOrganizationLists) {
                Long pid = sysOrganizationList.getPid();
                if(result.containsKey(pid)){
                    List<SysOrganization> list = result.get(pid);
                    list.add(sysOrganizationList);
                }else{
                    List<SysOrganization> list = new ArrayList<>();
                    list.add(sysOrganizationList);
                    result.put(pid,list);
                }
            }
        }
        return result;
    }


    /**
     * 向上递归，获取指定类型的组织架构id
     * @param map
     * @param current
     * @param type
     * @return
     */
    private void lookupOrganizationIdByType(Map<Long,SysOrganization> map,SysOrganization current,String type,JSONObject result){
        if(type.equals(current.getType())){
            result.put("id",current.getId());
            return;
        }

        if(map.containsKey(current.getPid())){
            SysOrganization c = map.get(current.getPid());
            lookupOrganizationIdByType(map,c,type,result);
        }
    }


    private void lookupOrganizationByPid(Map<Long,List<SysOrganization>> map,Long currentId,final List<SysOrganization> sysOrganizationLists){
        if(map.containsKey(currentId)){
            List<SysOrganization> children = map.get(currentId);
            if(org.apache.commons.collections.CollectionUtils.isNotEmpty(children)){
                for (SysOrganization child : children) {
                    sysOrganizationLists.add(child);
                    lookupOrganizationByPid(map,child.getId(),sysOrganizationLists);
                }
            }
        }
    }
}
