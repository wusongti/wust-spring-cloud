package com.wust.springcloud.admin.server.core.web.controller;


import com.wust.springcloud.admin.server.core.service.SysDepartmentService;
import com.wust.springcloud.admin.server.core.service.SysOrganizationService;
import com.wust.springcloud.common.annotations.OperationLogAnnotation;
import com.wust.springcloud.common.context.DefaultBusinessContext;
import com.wust.springcloud.common.dto.ResponseDto;
import com.wust.springcloud.common.entity.sys.department.*;
import com.wust.springcloud.common.entity.sys.organization.SysOrganization;
import com.wust.springcloud.common.entity.sys.organization.SysOrganizationList;
import com.wust.springcloud.common.entity.sys.organization.SysOrganizationSearch;
import com.wust.springcloud.common.enums.OperationLogEnum;
import com.wust.springcloud.common.util.CodeGenerator;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;

/**
 * Created by WST on 2019/6/3.
 */
@RequestMapping("/DepartmentController")
@RestController
public class DepartmentController {
    @Autowired
    private SysDepartmentService sysDepartmentServiceImpl;


    @Autowired
    private SysOrganizationService sysOrganizationServiceImpl;

    @OperationLogAnnotation(moduleName= OperationLogEnum.MODULE_ADMIN_DEPARTMENT,businessName="分页查询",operationType= OperationLogEnum.Search)
    @RequestMapping(value = "/listPage",method = RequestMethod.POST)
    public ResponseDto listPage(@RequestBody SysDepartmentSearch search){
        ResponseDto baseDto = new ResponseDto();
        List<SysDepartmentList> sysDepartmentLists =  sysDepartmentServiceImpl.listPage(search);
        baseDto.setPage(search.getPageDto());
        baseDto.setLstDto(sysDepartmentLists);
        return baseDto;
    }


    @OperationLogAnnotation(moduleName= OperationLogEnum.MODULE_ADMIN_DEPARTMENT,businessName="新建",operationType= OperationLogEnum.Insert)
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public ResponseDto create(@RequestBody SysDepartment entity){
        ResponseDto mm = new ResponseDto();
        DefaultBusinessContext ctx = DefaultBusinessContext.getContext();


        SysDepartmentSearch sysDepartmentSearch = new SysDepartmentSearch();
        sysDepartmentSearch.setName(entity.getName());
        List<SysDepartment> sysDepartmentLists = sysDepartmentServiceImpl.select(sysDepartmentSearch);
        if(CollectionUtils.isNotEmpty(sysDepartmentLists)){
            mm.setFlag(ResponseDto.INFOR_WARNING);
            mm.setMessage("您输入的部门名称已经存在");
            return mm;
        }


        entity.setCode(CodeGenerator.genDetartmentCode());
        entity.setCreaterId(ctx.getUser().getId());
        entity.setCreaterName(ctx.getUser().getRealName());
        entity.setCreateTime(new Date());
        sysDepartmentServiceImpl.insert(entity);
        return mm;
    }


    @OperationLogAnnotation(moduleName= OperationLogEnum.MODULE_ADMIN_DEPARTMENT,businessName="修改",operationType= OperationLogEnum.Update)
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public ResponseDto update(@RequestBody SysDepartment entity){
        ResponseDto mm = new ResponseDto();
        DefaultBusinessContext ctx = DefaultBusinessContext.getContext();

        SysDepartmentSearch sysDepartmentSearch = new SysDepartmentSearch();
        sysDepartmentSearch.setName(entity.getName());
        List<SysDepartment> departmentLists = sysDepartmentServiceImpl.select(sysDepartmentSearch);
        if(CollectionUtils.isNotEmpty(departmentLists)){
            if(!departmentLists.get(0).getCode().equals(entity.getCode())){
                mm.setFlag(ResponseDto.INFOR_WARNING);
                mm.setMessage("您输入的部门名称已经存在，不能修改为系统已经存在的部门");
                return mm;
            }
        }

        entity.setModifyId(ctx.getUser().getId());
        entity.setModifyName(ctx.getUser().getRealName());
        entity.setModifyTime(new Date());
        sysDepartmentServiceImpl.updateByPrimaryKey(entity);
        return mm;
    }


    @OperationLogAnnotation(moduleName= OperationLogEnum.MODULE_ADMIN_DEPARTMENT,businessName="删除",operationType= OperationLogEnum.Delete)
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    public ResponseDto delete(@PathVariable Long id){
        ResponseDto mm = new ResponseDto();

        SysOrganizationSearch sysOrganizationSearch = new SysOrganizationSearch();
        sysOrganizationSearch.setRelationId(id);
        List<SysOrganization> sysOrganizationLists = sysOrganizationServiceImpl.select(sysOrganizationSearch);
        if(CollectionUtils.isNotEmpty(sysOrganizationLists)){
            mm.setFlag(ResponseDto.INFOR_WARNING);
            mm.setMessage("您要删除的数据存在组织架构关系中，不允许删除");
            return mm;
        }
        sysDepartmentServiceImpl.deleteByPrimaryKey(id);
        return mm;
    }
}
