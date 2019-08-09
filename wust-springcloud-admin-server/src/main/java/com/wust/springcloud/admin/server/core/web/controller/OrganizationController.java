package com.wust.springcloud.admin.server.core.web.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wust.springcloud.admin.server.core.mq.producer.UpdateUserOrganizationProducer;
import com.wust.springcloud.admin.server.core.service.*;
import com.wust.springcloud.common.annotations.OperationLogAnnotation;
import com.wust.springcloud.common.context.DefaultBusinessContext;
import com.wust.springcloud.common.dto.ResponseDto;
import com.wust.springcloud.common.entity.sys.company.SysCompany;
import com.wust.springcloud.common.entity.sys.company.SysCompanySearch;
import com.wust.springcloud.common.entity.sys.department.SysDepartment;
import com.wust.springcloud.common.entity.sys.department.SysDepartmentSearch;
import com.wust.springcloud.common.entity.sys.organization.SysOrganization;
import com.wust.springcloud.common.entity.sys.organization.SysOrganizationList;
import com.wust.springcloud.common.entity.sys.organization.SysOrganizationSearch;
import com.wust.springcloud.common.entity.sys.project.SysProject;
import com.wust.springcloud.common.entity.sys.project.SysProjectSearch;
import com.wust.springcloud.common.entity.sys.role.SysRole;
import com.wust.springcloud.common.entity.sys.role.SysRoleSearch;
import com.wust.springcloud.common.entity.sys.role.resource.SysRoleResourceCreate;
import com.wust.springcloud.common.entity.sys.user.SysUser;
import com.wust.springcloud.common.entity.sys.user.SysUserSearch;
import com.wust.springcloud.common.enums.DataDictionaryEnum;
import com.wust.springcloud.common.enums.OperationLogEnum;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by WST on 2019/6/3.
 */
@RequestMapping("/OrganizationController")
@RestController
public class OrganizationController {
    @Autowired
    private SysOrganizationService sysOrganizationServiceImpl;

    @Autowired
    private SysCompanyService sysCompanyServiceImpl;

    @Autowired
    private SysDepartmentService sysDepartmentServiceImpl;

    @Autowired
    private SysRoleService sysRoleServiceImpl;

    @Autowired
    private SysUserService sysUserServiceImpl;

    @Autowired
    private SysProjectService sysProjectServiceImpl;

    @Autowired
    private UpdateUserOrganizationProducer updateUserOrganizationProducer;

    @OperationLogAnnotation(moduleName= OperationLogEnum.MODULE_ADMIN_ORGANIZATION,businessName="分页查询",operationType= OperationLogEnum.Search)
    @RequestMapping(value = "/listPage",method = RequestMethod.POST)
    public ResponseDto listPage(@RequestBody SysOrganizationSearch search){
        ResponseDto baseDto = new ResponseDto();

        List<SysOrganizationList> sysOrganizationLists =  sysOrganizationServiceImpl.listPage(search);
        if(CollectionUtils.isNotEmpty(sysOrganizationLists)){
            List<SysCompany> sysCompanyLists = new ArrayList<>(sysOrganizationLists.size());
            List<SysDepartment> sysDepartmentLists = new ArrayList<>(sysOrganizationLists.size());
            List<SysProject> sysProjectLists = new ArrayList<>(sysOrganizationLists.size());
            List<SysRole> sysRoleLists = new ArrayList<>(sysOrganizationLists.size());
            List<SysUser> sysUserLists = new ArrayList<>(sysOrganizationLists.size());

            for (SysOrganizationList sysOrganizationList : sysOrganizationLists) {
                String type = sysOrganizationList.getType();
                Long relationId = sysOrganizationList.getRelationId();

                if(DataDictionaryEnum.ORGANIZATION_TYPE_AGENT.getStringValue().equalsIgnoreCase(type)
                || DataDictionaryEnum.ORGANIZATION_TYPE_PARENT_COMPANY.getStringValue().equalsIgnoreCase(type)
                || DataDictionaryEnum.ORGANIZATION_TYPE_BRANCH_COMPANY.getStringValue().equalsIgnoreCase(type)){ // 代理商、总公司、分公司
                    SysCompanySearch sysCompanySearch = new SysCompanySearch();
                    sysCompanySearch.setId(relationId);
                    SysCompany sysCompany = sysCompanyServiceImpl.selectOne(sysCompanySearch) == null ? null : (SysCompany)sysCompanyServiceImpl.selectOne(sysCompanySearch);
                    if(sysCompany != null){
                        sysCompanyLists.add(sysCompany);
                    }
                }else if(DataDictionaryEnum.ORGANIZATION_TYPE_PROJECT.getStringValue().equalsIgnoreCase(type)){ // 项目
                    SysProjectSearch sysProjectSearch = new SysProjectSearch();
                    sysProjectSearch.setId(relationId);
                    SysProject sysProject = sysProjectServiceImpl.selectOne(sysProjectSearch) == null ? null : (SysProject)sysProjectServiceImpl.selectOne(sysProjectSearch);
                    if(sysProject != null){
                        sysProjectLists.add(sysProject);
                    }
                }else if(DataDictionaryEnum.ORGANIZATION_TYPE_DEPARTMENT.getStringValue().equalsIgnoreCase(type)){ // 部门
                    SysDepartmentSearch sysDepartmentSearch = new SysDepartmentSearch();
                    sysDepartmentSearch.setId(relationId);
                    SysDepartment sysDepartment = sysDepartmentServiceImpl.selectOne(sysDepartmentSearch) == null ? null : (SysDepartment)sysDepartmentServiceImpl.selectOne(sysDepartmentSearch);
                    if(sysDepartment != null){
                        sysDepartmentLists.add(sysDepartment);
                    }
                }else if(DataDictionaryEnum.ORGANIZATION_TYPE_ROLE.getStringValue().equalsIgnoreCase(type)){ // 角色
                    SysRoleSearch sysRoleSearch = new SysRoleSearch();
                    sysRoleSearch.setId(relationId);
                    SysRole sysRole = sysRoleServiceImpl.selectOne(sysRoleSearch) == null ? null : (SysRole)sysRoleServiceImpl.selectOne(sysRoleSearch);
                    if(sysRole != null){
                        sysRoleLists.add(sysRole);
                    }
                }else if(DataDictionaryEnum.ORGANIZATION_TYPE_USER.getStringValue().equalsIgnoreCase(type)){ // 用户
                    SysUserSearch sysUserSearch = new SysUserSearch();
                    sysUserSearch.setId(relationId);
                    SysUser sysUser = sysUserServiceImpl.selectOne(sysUserSearch) == null ? null : (SysUser)sysUserServiceImpl.selectOne(sysUserSearch);
                    if(sysUser != null){
                        sysUserLists.add(sysUser);
                    }
                }
            }

            Map<String,List> map = new HashMap<>(5);
            map.put("companyList",null);
            map.put("departmentList",null);
            map.put("projectList",null);
            map.put("roleList",null);
            map.put("userList",null);
            if(CollectionUtils.isNotEmpty(sysCompanyLists)){
                map.put("companyList",sysCompanyLists);
            }
            if(CollectionUtils.isNotEmpty(sysDepartmentLists)){
                map.put("departmentList",sysDepartmentLists);
            }
            if(CollectionUtils.isNotEmpty(sysProjectLists)){
                map.put("projectList",sysProjectLists);
            }
            if(CollectionUtils.isNotEmpty(sysRoleLists)){
                map.put("roleList",sysRoleLists);
            }
            if(CollectionUtils.isNotEmpty(sysUserLists)){
                map.put("userList",sysUserLists);
            }
            baseDto.setObj(map);
        }
        baseDto.setPage(search.getPageDto());
        return baseDto;
    }


    @OperationLogAnnotation(moduleName= OperationLogEnum.MODULE_ADMIN_ORGANIZATION,businessName="构件组织架构树",operationType= OperationLogEnum.Search)
    @RequestMapping(value = "/buildTree",method = RequestMethod.POST)
    public ResponseDto buildTree(@RequestBody SysOrganizationSearch search){
        ResponseDto mm = new ResponseDto();

        JSONArray jsonArray = new JSONArray();
        JSONObject rootJSONObject = new JSONObject();
        jsonArray.add(rootJSONObject);
        rootJSONObject.put("id","-1");
        rootJSONObject.put("pId",null);
        rootJSONObject.put("name","企业基础平台组织架构");
        rootJSONObject.put("type","");
        rootJSONObject.put("relationId",null);
        rootJSONObject.put("open",true);
        SysOrganizationSearch sysOrganizationSearch = new SysOrganizationSearch();
        List<SysOrganization> sysOrganizationLists = sysOrganizationServiceImpl.select(sysOrganizationSearch);
        if(CollectionUtils.isNotEmpty(sysOrganizationLists)){
            for (SysOrganization sysOrganizationList : sysOrganizationLists) {
                JSONObject jsonObject = new JSONObject();

                String type = sysOrganizationList.getType();
                Long relationId = sysOrganizationList.getRelationId();
                String name = "";
                Long pid = sysOrganizationList.getPid();

                if(DataDictionaryEnum.ORGANIZATION_TYPE_AGENT.getStringValue().equalsIgnoreCase(type)){
                    SysCompanySearch sysCompanySearch = new SysCompanySearch();
                    sysCompanySearch.setId(relationId);
                    SysCompany sysCompany = sysCompanyServiceImpl.selectOne(sysCompanySearch) == null ? null : (SysCompany)sysCompanyServiceImpl.selectOne(sysCompanySearch);
                    if(sysCompany != null){
                        name = "代理商-" + sysCompany.getName();
                        pid = pid == null ? -1 : pid;
                    }
                }else if(DataDictionaryEnum.ORGANIZATION_TYPE_PARENT_COMPANY.getStringValue().equalsIgnoreCase(type)){
                    SysCompanySearch sysCompanySearch = new SysCompanySearch();
                    sysCompanySearch.setId(relationId);
                    SysCompany sysCompany = sysCompanyServiceImpl.selectOne(sysCompanySearch) == null ? null : (SysCompany)sysCompanyServiceImpl.selectOne(sysCompanySearch);
                    if(sysCompany != null){
                        name = "总公司-" + sysCompany.getName();
                    }
                }else if(DataDictionaryEnum.ORGANIZATION_TYPE_BRANCH_COMPANY.getStringValue().equalsIgnoreCase(type)){
                    SysCompanySearch sysCompanySearch = new SysCompanySearch();
                    sysCompanySearch.setId(relationId);
                    SysCompany sysCompany = sysCompanyServiceImpl.selectOne(sysCompanySearch) == null ? null : (SysCompany)sysCompanyServiceImpl.selectOne(sysCompanySearch);
                    if(sysCompany != null){
                        name = "分公司-" + sysCompany.getName();
                    }
                }else if(DataDictionaryEnum.ORGANIZATION_TYPE_PROJECT.getStringValue().equalsIgnoreCase(type)){
                    SysProjectSearch sysProjectSearch = new SysProjectSearch();
                    sysProjectSearch.setId(relationId);
                    SysProject sysProject = sysProjectServiceImpl.selectOne(sysProjectSearch) == null ? null : (SysProject)sysProjectServiceImpl.selectOne(sysProjectSearch);
                    if(sysProject != null){
                        name = "项目-" + sysProject.getName();
                    }
                }else if(DataDictionaryEnum.ORGANIZATION_TYPE_DEPARTMENT.getStringValue().equalsIgnoreCase(type)){
                    SysDepartmentSearch sysDepartmentSearch = new SysDepartmentSearch();
                    sysDepartmentSearch.setId(relationId);
                    SysDepartment sysDepartment = sysDepartmentServiceImpl.selectOne(sysDepartmentSearch) == null ? null : (SysDepartment)sysDepartmentServiceImpl.selectOne(sysDepartmentSearch);
                    if(sysDepartment != null){
                        name = "部门-" + sysDepartment.getName();
                    }
                }else if(DataDictionaryEnum.ORGANIZATION_TYPE_ROLE.getStringValue().equalsIgnoreCase(type)){
                    SysRoleSearch sysRoleSearch = new SysRoleSearch();
                    sysRoleSearch.setId(relationId);
                    SysRole sysRole = sysRoleServiceImpl.selectOne(sysRoleSearch) == null ? null : (SysRole)sysRoleServiceImpl.selectOne(sysRoleSearch);
                    if(sysRole != null){
                        name = "角色-" + sysRole.getName();
                    }
                }else if(DataDictionaryEnum.ORGANIZATION_TYPE_USER.getStringValue().equalsIgnoreCase(type)){
                    SysUserSearch sysUserSearch = new SysUserSearch();
                    sysUserSearch.setId(relationId);
                    SysUser sysUser = sysUserServiceImpl.selectOne(sysUserSearch) == null ? null : (SysUser)sysUserServiceImpl.selectOne(sysUserSearch);
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

        mm.setObj(jsonArray.toJSONString());
        return mm;
    }


    @OperationLogAnnotation(moduleName= OperationLogEnum.MODULE_ADMIN_ORGANIZATION,businessName="添加组织架构",operationType= OperationLogEnum.Insert)
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public ResponseDto create(@RequestBody SysOrganization entity){
        ResponseDto mm = new ResponseDto();
        DefaultBusinessContext ctx = DefaultBusinessContext.getContext();

        SysOrganizationSearch sysOrganizationSearch = new SysOrganizationSearch();
        sysOrganizationSearch.setType(entity.getType());

        // 同一个部门、角色和用户可以在不同节点下面出现
        if(DataDictionaryEnum.ORGANIZATION_TYPE_DEPARTMENT.getStringValue().equals(entity.getType())
                || DataDictionaryEnum.ORGANIZATION_TYPE_ROLE.getStringValue().equals(entity.getType())
                || DataDictionaryEnum.ORGANIZATION_TYPE_USER.getStringValue().equals(entity.getType())){
            sysOrganizationSearch.setPid(entity.getPid());
        }
        sysOrganizationSearch.setRelationId(entity.getRelationId());
        List<SysOrganization> sysOrganizationLists = sysOrganizationServiceImpl.select(sysOrganizationSearch);
        if(CollectionUtils.isNotEmpty(sysOrganizationLists)){
            mm.setFlag(ResponseDto.INFOR_WARNING);
            mm.setMessage("在组织架构中已经存在该条记录，不允许重复添加");
            return mm;
        }

        entity.setCreaterId(ctx.getUserId());
        entity.setCreaterName(ctx.getLoginName());
        sysOrganizationServiceImpl.insert(entity);

        updateUserOrganizationProducer.send(null);
        mm.setObj(entity.getId());
        return mm;
    }


    @OperationLogAnnotation(moduleName= OperationLogEnum.MODULE_ADMIN_ORGANIZATION,businessName="删除",operationType= OperationLogEnum.Delete)
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    public ResponseDto delete(@PathVariable Long id){
        ResponseDto mm = new ResponseDto();

        SysOrganizationSearch sysOrganizationSearch = new SysOrganizationSearch();
        sysOrganizationSearch.setPid(id);
        List<SysOrganization> sysOrganizationLists = sysOrganizationServiceImpl.select(sysOrganizationSearch);
        if(CollectionUtils.isNotEmpty(sysOrganizationLists)){
            mm.setFlag(ResponseDto.INFOR_WARNING);
            mm.setMessage("您要删除的记录存在子节点，无法删除带有子节点的数据，请先删除所有子节点");
        }else{
            sysOrganizationServiceImpl.deleteByPrimaryKey(id);
            updateUserOrganizationProducer.send(null);
        }
        return mm;
    }


    /**
     * 获取当前角色的功能权限树
     * @param roleId
     * @return
     */
    @RequestMapping(value = "/findFunctionTreeByRoleId/{pid}/{roleId}",method = RequestMethod.POST)
    public  ResponseDto findFunctionTreeByRoleId(@PathVariable Long pid,@PathVariable Long roleId){
        ResponseDto messageMap = new ResponseDto();
        SysOrganizationSearch sysOrganizationSearch = new SysOrganizationSearch();
        sysOrganizationSearch.setPid(pid);
        sysOrganizationSearch.setRelationId(roleId);
        List<SysOrganization> sysOrganizationLists = this.sysOrganizationServiceImpl.select(sysOrganizationSearch);
        if(CollectionUtils.isNotEmpty(sysOrganizationLists)){
            messageMap = sysRoleServiceImpl.findFunctionTreeByOrganizationId(sysOrganizationLists.get(0).getId());
        }else{
            messageMap.setFlag(ResponseDto.INFOR_WARNING);
            messageMap.setMessage("组织架构里面已经没有这个数据，刚刚可能是被其他用户删除了");
        }
        return messageMap;
    }



    /**
     * 获取当前角色的功能权限树
     * @param sysRoleResourceAdd
     * @return
     */
    @RequestMapping(value = "/setFunctionPermissions",method = RequestMethod.POST)
    public  ResponseDto setFunctionPermissions(@RequestBody SysRoleResourceCreate sysRoleResourceAdd){
        ResponseDto messageMap = new ResponseDto();
        SysOrganizationSearch sysOrganizationSearch = new SysOrganizationSearch();
        sysOrganizationSearch.setPid(sysRoleResourceAdd.getPid());
        sysOrganizationSearch.setRelationId(sysRoleResourceAdd.getRoleId());
        List<SysOrganization> sysOrganizationLists = this.sysOrganizationServiceImpl.select(sysOrganizationSearch);
        if(CollectionUtils.isNotEmpty(sysOrganizationLists)){
            sysRoleResourceAdd.setOrganizationId(sysOrganizationLists.get(0).getId());
            messageMap = sysOrganizationServiceImpl.setFunctionPermissions(sysRoleResourceAdd);
            updateUserOrganizationProducer.send(null);
        }else{
            messageMap.setFlag(ResponseDto.INFOR_WARNING);
            messageMap.setMessage("组织架构里面已经没有这个数据，刚刚可能是被其他用户删除了");
        }
        return messageMap;
    }
}
