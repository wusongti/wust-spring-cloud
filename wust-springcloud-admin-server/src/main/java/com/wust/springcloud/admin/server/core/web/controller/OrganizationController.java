package com.wust.springcloud.admin.server.core.web.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wust.springcloud.admin.server.core.service.defaults.*;
import com.wust.springcloud.common.annotations.OperationLogAnnotation;
import com.wust.springcloud.common.context.DefaultBusinessContext;
import com.wust.springcloud.common.dto.ResponseDto;
import com.wust.springcloud.common.entity.sys.company.SysCompanyList;
import com.wust.springcloud.common.entity.sys.company.SysCompanySearch;
import com.wust.springcloud.common.entity.sys.department.SysDepartmentList;
import com.wust.springcloud.common.entity.sys.department.SysDepartmentSearch;
import com.wust.springcloud.common.entity.sys.organization.SysOrganization;
import com.wust.springcloud.common.entity.sys.organization.SysOrganizationList;
import com.wust.springcloud.common.entity.sys.organization.SysOrganizationSearch;
import com.wust.springcloud.common.entity.sys.project.SysProjectList;
import com.wust.springcloud.common.entity.sys.project.SysProjectSearch;
import com.wust.springcloud.common.entity.sys.role.SysRoleList;
import com.wust.springcloud.common.entity.sys.role.SysRoleSearch;
import com.wust.springcloud.common.entity.sys.role.resource.SysRoleResourceCreate;
import com.wust.springcloud.common.entity.sys.user.SysUserList;
import com.wust.springcloud.common.entity.sys.user.SysUserSearch;
import com.wust.springcloud.common.enums.OperationLogEnum;
import com.wust.springcloud.common.util.MyStringUtils;
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

    @OperationLogAnnotation(moduleName= OperationLogEnum.MODULE_ADMIN_ORGANIZATION,businessName="分页查询",operationType= OperationLogEnum.Search)
    @RequestMapping(value = "/listPage",method = RequestMethod.POST)
    public @ResponseBody
    ResponseDto listPage(@RequestBody SysOrganizationSearch search){
        ResponseDto baseDto = new ResponseDto();

        List<SysOrganizationList> sysOrganizationLists =  sysOrganizationServiceImpl.listPage(search);
        if(CollectionUtils.isNotEmpty(sysOrganizationLists)){
            List<SysCompanyList> sysCompanyLists = new ArrayList<>(sysOrganizationLists.size());
            List<SysDepartmentList> sysDepartmentLists = new ArrayList<>(sysOrganizationLists.size());
            List<SysProjectList> sysProjectLists = new ArrayList<>(sysOrganizationLists.size());
            List<SysRoleList> sysRoleLists = new ArrayList<>(sysOrganizationLists.size());
            List<SysUserList> sysUserLists = new ArrayList<>(sysOrganizationLists.size());

            for (SysOrganizationList sysOrganizationList : sysOrganizationLists) {
                String type = sysOrganizationList.getType();
                Long relationId = sysOrganizationList.getRelationId();

                if("101101".equalsIgnoreCase(type)
                || "101104".equalsIgnoreCase(type)
                || "101107".equalsIgnoreCase(type)){ // 代理商、总公司、分公司
                    SysCompanySearch sysCompanySearch = new SysCompanySearch();
                    sysCompanySearch.setId(relationId);
                    List<SysCompanyList> sysCompanyServiceImplByCondition = sysCompanyServiceImpl.findByCondition(sysCompanySearch);
                    if(CollectionUtils.isNotEmpty(sysCompanyServiceImplByCondition)){
                        sysCompanyLists.add(sysCompanyServiceImplByCondition.get(0));
                    }
                }else if("101109".equalsIgnoreCase(type)){ // 项目
                    SysProjectSearch sysProjectSearch = new SysProjectSearch();
                    sysProjectSearch.setId(relationId);
                    List<SysProjectList> list = sysProjectServiceImpl.findByCondition(sysProjectSearch);
                    if(CollectionUtils.isNotEmpty(list)){
                        sysProjectLists.add(list.get(0));
                    }
                }else if("101111".equalsIgnoreCase(type)){ // 部门
                    SysDepartmentSearch sysDepartmentSearch = new SysDepartmentSearch();
                    sysDepartmentSearch.setId(relationId);
                    List<SysDepartmentList> sysDepartmentServiceImplByCondition = sysDepartmentServiceImpl.findByCondition(sysDepartmentSearch);
                    if(CollectionUtils.isNotEmpty(sysDepartmentServiceImplByCondition)){
                        sysDepartmentLists.add(sysDepartmentServiceImplByCondition.get(0));
                    }
                }else if("101113".equalsIgnoreCase(type)){ // 角色
                    SysRoleSearch sysRoleSearch = new SysRoleSearch();
                    sysRoleSearch.setId(relationId);
                    List<SysRoleList> sysRoleServiceImplByCondition = sysRoleServiceImpl.findByCondition(sysRoleSearch);
                    if(CollectionUtils.isNotEmpty(sysRoleServiceImplByCondition)){
                        sysRoleLists.add(sysRoleServiceImplByCondition.get(0));
                    }
                }else if("101115".equalsIgnoreCase(type)){ // 用户
                    SysUserSearch sysUserSearch = new SysUserSearch();
                    sysUserSearch.setId(relationId);
                    List<SysUserList> sysUserServiceImplByCondition = sysUserServiceImpl.findByCondition(sysUserSearch);
                    if(CollectionUtils.isNotEmpty(sysUserServiceImplByCondition)){
                        sysUserLists.add(sysUserServiceImplByCondition.get(0));
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
    public @ResponseBody
    ResponseDto buildTree(@RequestBody SysOrganizationSearch search){
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
        List<SysOrganizationList> sysOrganizationLists = sysOrganizationServiceImpl.findByCondition(sysOrganizationSearch);
        if(CollectionUtils.isNotEmpty(sysOrganizationLists)){
            for (SysOrganizationList sysOrganizationList : sysOrganizationLists) {
                JSONObject jsonObject = new JSONObject();

                String type = sysOrganizationList.getType();
                Long relationId = sysOrganizationList.getRelationId();
                String name = "";
                Long pid = sysOrganizationList.getPid();

                if("101101".equalsIgnoreCase(type)){
                    SysCompanySearch sysCompanySearch = new SysCompanySearch();
                    sysCompanySearch.setId(relationId);
                    List<SysCompanyList> sysCompanyLists = sysCompanyServiceImpl.findByCondition(sysCompanySearch);
                    if(CollectionUtils.isNotEmpty(sysCompanyLists)){
                        name = "代理商-" + sysCompanyLists.get(0).getName();
                        pid = pid == null ? -1 : pid;
                    }
                }else if("101104".equalsIgnoreCase(type)){
                    SysCompanySearch sysCompanySearch = new SysCompanySearch();
                    sysCompanySearch.setId(relationId);
                    List<SysCompanyList> sysCompanyLists = sysCompanyServiceImpl.findByCondition(sysCompanySearch);
                    if(CollectionUtils.isNotEmpty(sysCompanyLists)){
                        name = "总公司-" + sysCompanyLists.get(0).getName();
                    }
                }else if("101107".equalsIgnoreCase(type)){
                    SysCompanySearch sysCompanySearch = new SysCompanySearch();
                    sysCompanySearch.setId(relationId);
                    List<SysCompanyList> sysCompanyLists = sysCompanyServiceImpl.findByCondition(sysCompanySearch);
                    if(CollectionUtils.isNotEmpty(sysCompanyLists)){
                        name = "分公司-" + sysCompanyLists.get(0).getName();
                    }
                }else if("101109".equalsIgnoreCase(type)){
                    SysProjectSearch sysProjectSearch = new SysProjectSearch();
                    sysProjectSearch.setId(relationId);
                    List<SysProjectList> sysProjectLists = sysProjectServiceImpl.findByCondition(sysProjectSearch);
                    if(CollectionUtils.isNotEmpty(sysProjectLists)){
                        name = "项目-" + sysProjectLists.get(0).getName();
                    }
                }else if("101111".equalsIgnoreCase(type)){
                    SysDepartmentSearch sysDepartmentSearch = new SysDepartmentSearch();
                    sysDepartmentSearch.setId(relationId);
                    List<SysDepartmentList> sysDepartmentLists = sysDepartmentServiceImpl.findByCondition(sysDepartmentSearch);
                    if(CollectionUtils.isNotEmpty(sysDepartmentLists)){
                        name = "部门-" + sysDepartmentLists.get(0).getName();
                    }
                }else if("101113".equalsIgnoreCase(type)){
                    SysRoleSearch sysRoleSearch = new SysRoleSearch();
                    sysRoleSearch.setId(relationId);
                    List<SysRoleList> sysRoleLists = sysRoleServiceImpl.findByCondition(sysRoleSearch);
                    if(CollectionUtils.isNotEmpty(sysRoleLists)){
                        name = "角色-" + sysRoleLists.get(0).getName();
                    }
                }else if("101115".equalsIgnoreCase(type)){
                    SysUserSearch sysUserSearch = new SysUserSearch();
                    sysUserSearch.setId(relationId);
                    List<SysUserList> sysUserLists = sysUserServiceImpl.findByCondition(sysUserSearch);
                    if(CollectionUtils.isNotEmpty(sysUserLists)){
                        name = "用户-" + sysUserLists.get(0).getRealName() + "(" + sysUserLists.get(0).getLoginName() + ")";
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
    public @ResponseBody
    ResponseDto create(@RequestBody SysOrganization entity){
        ResponseDto mm = new ResponseDto();
        DefaultBusinessContext ctx = DefaultBusinessContext.getContext();

        SysOrganizationSearch sysOrganizationSearch = new SysOrganizationSearch();
        sysOrganizationSearch.setType(entity.getType());

        // 同一个部门、角色和用户可以在不同节点下面出现
        if("101111".equals(entity.getType())
                || "101113".equals(entity.getType())
                || "101115".equals(entity.getType())){
            sysOrganizationSearch.setPid(entity.getPid());
        }
        sysOrganizationSearch.setRelationId(entity.getRelationId());
        List<SysOrganizationList> sysOrganizationLists = sysOrganizationServiceImpl.findByCondition(sysOrganizationSearch);
        if(CollectionUtils.isNotEmpty(sysOrganizationLists)){
            mm.setFlag(ResponseDto.INFOR_WARNING);
            mm.setMessage("在组织架构中已经存在该条记录，不允许重复添加");
            return mm;
        }

        entity.setCreaterId(ctx.getUserId());
        entity.setCreaterName(ctx.getLoginName());

        List<SysOrganization> entities = new ArrayList<>(1);
        entities.add(entity);
        sysOrganizationServiceImpl.batchInsert(entities);
        return mm;
    }


    @OperationLogAnnotation(moduleName= OperationLogEnum.MODULE_ADMIN_ORGANIZATION,businessName="删除",operationType= OperationLogEnum.Delete)
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    public @ResponseBody
    ResponseDto delete(@PathVariable Long id){
        ResponseDto mm = new ResponseDto();

        SysOrganizationSearch sysOrganizationSearch = new SysOrganizationSearch();
        sysOrganizationSearch.setPid(id);
        List<SysOrganizationList> sysOrganizationLists = sysOrganizationServiceImpl.findByCondition(sysOrganizationSearch);
        if(CollectionUtils.isNotEmpty(sysOrganizationLists)){
            mm.setFlag(ResponseDto.INFOR_WARNING);
            mm.setMessage("您要删除的记录存在子节点，无法删除带有子节点的数据，请先删除所有子节点");
        }else{
            sysOrganizationServiceImpl.deleteByPrimaryKey(id);
        }
        return mm;
    }


    /**
     * 获取当前角色的功能权限树
     * @param roleId
     * @return
     */
    @RequestMapping(value = "/findFunctionTreeByRoleId/{pid}/{roleId}",method = RequestMethod.POST)
    public  @ResponseBody  ResponseDto findFunctionTreeByRoleId(@PathVariable Long pid,@PathVariable Long roleId){
        ResponseDto messageMap = new ResponseDto();
        SysOrganizationSearch sysOrganizationSearch = new SysOrganizationSearch();
        sysOrganizationSearch.setPid(pid);
        sysOrganizationSearch.setRelationId(roleId);
        List<SysOrganizationList> sysOrganizationLists = this.sysOrganizationServiceImpl.findByCondition(sysOrganizationSearch);
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
    public  @ResponseBody  ResponseDto setFunctionPermissions(@RequestBody SysRoleResourceCreate sysRoleResourceAdd){
        ResponseDto messageMap = new ResponseDto();
        SysOrganizationSearch sysOrganizationSearch = new SysOrganizationSearch();
        sysOrganizationSearch.setPid(sysRoleResourceAdd.getPid());
        sysOrganizationSearch.setRelationId(sysRoleResourceAdd.getRoleId());
        List<SysOrganizationList> sysOrganizationLists = this.sysOrganizationServiceImpl.findByCondition(sysOrganizationSearch);
        if(CollectionUtils.isNotEmpty(sysOrganizationLists)){
            sysRoleResourceAdd.setOrganizationId(sysOrganizationLists.get(0).getId());
            messageMap = sysOrganizationServiceImpl.setFunctionPermissions(sysRoleResourceAdd);
        }else{
            messageMap.setFlag(ResponseDto.INFOR_WARNING);
            messageMap.setMessage("组织架构里面已经没有这个数据，刚刚可能是被其他用户删除了");
        }
        return messageMap;
    }
}
