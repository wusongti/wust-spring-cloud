package com.wust.springcloud.admin.server.core.api.web;


import com.wust.springcloud.admin.server.core.service.SysDepartmentService;
import com.wust.springcloud.admin.server.core.service.SysOrganizationService;
import com.wust.springcloud.common.annotations.OperationLogAnnotation;
import com.wust.springcloud.common.context.DefaultBusinessContext;
import com.wust.springcloud.common.dto.ResponseDto;
import com.wust.springcloud.common.entity.sys.department.*;
import com.wust.springcloud.common.entity.sys.organization.SysOrganizationList;
import com.wust.springcloud.common.entity.sys.organization.SysOrganizationSearch;
import com.wust.springcloud.common.enums.OperationLogEnum;
import com.wust.springcloud.common.util.CodeGenerator;
import com.wust.springcloud.common.util.MyStringUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
    public @ResponseBody
    ResponseDto listPage(@RequestBody SysDepartmentSearch search){
        ResponseDto baseDto = new ResponseDto();
        List<SysDepartmentList> sysDepartmentLists =  sysDepartmentServiceImpl.listPage(search);
        baseDto.setPage(search.getPageDto());
        baseDto.setLstDto(sysDepartmentLists);
        return baseDto;
    }


    @OperationLogAnnotation(moduleName= OperationLogEnum.MODULE_ADMIN_DEPARTMENT,businessName="新建",operationType= OperationLogEnum.Insert)
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public @ResponseBody
    ResponseDto create(@RequestBody SysDepartmentCreate entity){
        ResponseDto mm = new ResponseDto();
        DefaultBusinessContext ctx = DefaultBusinessContext.getContext();


        SysDepartmentSearch sysDepartmentSearch = new SysDepartmentSearch();
        sysDepartmentSearch.setName(entity.getName());
        List<SysDepartmentList> sysDepartmentLists = sysDepartmentServiceImpl.findByCondition(sysDepartmentSearch);
        if(CollectionUtils.isNotEmpty(sysDepartmentLists)){
            mm.setFlag(ResponseDto.INFOR_WARNING);
            mm.setMessage("您输入的部门名称已经存在");
            return mm;
        }


        if(MyStringUtils.isNotBlank(MyStringUtils.null2String(entity.getPname()))){
            sysDepartmentSearch = new SysDepartmentSearch();
            sysDepartmentSearch.setName(entity.getPname());
            List<SysDepartmentList> parentLists = sysDepartmentServiceImpl.findByCondition(sysDepartmentSearch);
            if(CollectionUtils.isEmpty(parentLists)){
                mm.setFlag(ResponseDto.INFOR_WARNING);
                mm.setMessage("您输入的父级部门不存在");
                return mm;
            }
            entity.setPcode(parentLists.get(0).getCode());
        }


        entity.setCode(CodeGenerator.genDetartmentCode());
        entity.setCreaterId(ctx.getUserId());
        entity.setCreaterName(ctx.getLoginName());
        sysDepartmentServiceImpl.insert(entity);
        return mm;
    }


    @OperationLogAnnotation(moduleName= OperationLogEnum.MODULE_ADMIN_DEPARTMENT,businessName="修改",operationType= OperationLogEnum.Update)
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public @ResponseBody
    ResponseDto update(@RequestBody SysDepartmentUpdate entity){
        ResponseDto mm = new ResponseDto();
        DefaultBusinessContext ctx = DefaultBusinessContext.getContext();

        SysDepartmentSearch sysDepartmentSearch = new SysDepartmentSearch();
        sysDepartmentSearch.setName(entity.getName());
        List<SysDepartmentList> departmentLists = sysDepartmentServiceImpl.findByCondition(sysDepartmentSearch);
        if(CollectionUtils.isNotEmpty(departmentLists)){
            if(!departmentLists.get(0).getCode().equals(entity.getCode())){
                mm.setFlag(ResponseDto.INFOR_WARNING);
                mm.setMessage("您输入的部门名称已经存在，不能修改为系统已经存在的部门");
                return mm;
            }
        }

        if(MyStringUtils.isNotBlank(MyStringUtils.null2String(entity.getPname()))){
            sysDepartmentSearch = new SysDepartmentSearch();
            sysDepartmentSearch.setName(entity.getPname());
            List<SysDepartmentList> parenLists = sysDepartmentServiceImpl.findByCondition(sysDepartmentSearch);
            if(CollectionUtils.isEmpty(parenLists)){
                mm.setFlag(ResponseDto.INFOR_WARNING);
                mm.setMessage("您输入的父级部门不存在");
                return mm;
            }
            entity.setPcode(parenLists.get(0).getCode());
        }

        entity.setModifyId(ctx.getUserId());
        entity.setModifyName(ctx.getLoginName());
        sysDepartmentServiceImpl.update(entity);
        return mm;
    }


    @OperationLogAnnotation(moduleName= OperationLogEnum.MODULE_ADMIN_DEPARTMENT,businessName="删除",operationType= OperationLogEnum.Delete)
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    public @ResponseBody
    ResponseDto delete(@PathVariable String id){
        ResponseDto mm = new ResponseDto();

        SysDepartmentSearch sysDepartmentSearch = new SysDepartmentSearch();
        sysDepartmentSearch.setId(id);
        List<SysDepartmentList> departmentLists = sysDepartmentServiceImpl.findByCondition(sysDepartmentSearch);
        if(CollectionUtils.isNotEmpty(departmentLists)){
            sysDepartmentSearch = new SysDepartmentSearch();
            sysDepartmentSearch.setPcode( departmentLists.get(0).getCode());
            departmentLists = sysDepartmentServiceImpl.findByCondition(sysDepartmentSearch);
            if(CollectionUtils.isNotEmpty(departmentLists)){
                mm.setFlag(ResponseDto.INFOR_WARNING);
                mm.setMessage("您要删除的部门存在子部门，不允许直接删除父级部门不");
                return mm;
            }
        }

        SysOrganizationSearch sysOrganizationSearch = new SysOrganizationSearch();
        sysOrganizationSearch.setRelationId(id);
        List<SysOrganizationList> sysOrganizationLists = sysOrganizationServiceImpl.findByCondition(sysOrganizationSearch);
        if(CollectionUtils.isNotEmpty(sysOrganizationLists)){
            mm.setFlag(ResponseDto.INFOR_WARNING);
            mm.setMessage("您要删除的数据存在组织架构关系中，不允许删除");
            return mm;
        }
        sysDepartmentServiceImpl.delete(id);
        return mm;
    }
}
