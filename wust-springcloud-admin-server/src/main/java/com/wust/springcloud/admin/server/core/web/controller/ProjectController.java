package com.wust.springcloud.admin.server.core.web.controller;

import com.wust.springcloud.admin.server.core.service.SysOrganizationService;
import com.wust.springcloud.admin.server.core.service.SysProjectService;
import com.wust.springcloud.common.annotations.OperationLogAnnotation;
import com.wust.springcloud.common.context.DefaultBusinessContext;
import com.wust.springcloud.common.dto.ResponseDto;
import com.wust.springcloud.common.entity.sys.organization.SysOrganizationList;
import com.wust.springcloud.common.entity.sys.organization.SysOrganizationSearch;
import com.wust.springcloud.common.entity.sys.project.SysProject;
import com.wust.springcloud.common.entity.sys.project.SysProjectList;
import com.wust.springcloud.common.entity.sys.project.SysProjectSearch;
import com.wust.springcloud.common.enums.OperationLogEnum;
import com.wust.springcloud.common.util.CodeGenerator;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author ：wust
 * @date ：Created in 2019/7/30 14:27
 * @description：
 * @version:
 */
@RequestMapping("/ProjectController")
@RestController
public class ProjectController {
    @Autowired
    private SysProjectService sysProjectServiceImpl;

    @Autowired
    private SysOrganizationService sysOrganizationServiceImpl;


    @OperationLogAnnotation(moduleName= OperationLogEnum.MODULE_ADMIN_PROJECT,businessName="分页查询",operationType= OperationLogEnum.Search)
    @RequestMapping(value = "/listPage",method = RequestMethod.POST)
    public ResponseDto listPage(@RequestBody SysProjectSearch search){
        ResponseDto baseDto = new ResponseDto();
        List<SysProjectList> sysProjectLists =  sysProjectServiceImpl.listPage(search);
        baseDto.setPage(search.getPageDto());
        baseDto.setLstDto(sysProjectLists);
        return baseDto;
    }


    @OperationLogAnnotation(moduleName= OperationLogEnum.MODULE_ADMIN_PROJECT,businessName="新建",operationType= OperationLogEnum.Insert)
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public ResponseDto create(@RequestBody  SysProject entity){
        ResponseDto mm = new ResponseDto();
        DefaultBusinessContext ctx = DefaultBusinessContext.getContext();

        SysProjectSearch sysProjectSearch = new SysProjectSearch();
        sysProjectSearch.setName(entity.getName());
        List<SysProjectList> sysDepartmentLists = sysProjectServiceImpl.findByCondition(sysProjectSearch);
        if(CollectionUtils.isNotEmpty(sysDepartmentLists)){
            mm.setFlag(ResponseDto.INFOR_WARNING);
            mm.setMessage("您输入的名称已经存在");
            return mm;
        }

        entity.setCode(CodeGenerator.genProjectCode());
        entity.setCreaterId(ctx.getUserId());
        entity.setCreaterName(ctx.getLoginName());
        entity.setCreateTime(new Date());
        sysProjectServiceImpl.insert(entity);
        return mm;
    }


    @OperationLogAnnotation(moduleName= OperationLogEnum.MODULE_ADMIN_PROJECT,businessName="修改",operationType= OperationLogEnum.Update)
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public ResponseDto update(@RequestBody  SysProject entity){
        ResponseDto mm = new ResponseDto();
        DefaultBusinessContext ctx = DefaultBusinessContext.getContext();

        SysProjectSearch sysProjectSearch = new SysProjectSearch();
        sysProjectSearch.setName(entity.getName());
        List<SysProjectList> sysProjectLists = sysProjectServiceImpl.findByCondition(sysProjectSearch);
        if(CollectionUtils.isNotEmpty(sysProjectLists)){
            if(!sysProjectLists.get(0).getCode().equals(entity.getCode())){
                mm.setFlag(ResponseDto.INFOR_WARNING);
                mm.setMessage("您输入的名称已经存在，不能修改为系统已经存在的名称");
                return mm;
            }
        }

        entity.setModifyId(ctx.getUserId());
        entity.setModifyName(ctx.getLoginName());
        entity.setModifyTime(new Date());
        sysProjectServiceImpl.updateByPrimaryKey(entity);
        return mm;
    }


    @OperationLogAnnotation(moduleName= OperationLogEnum.MODULE_ADMIN_PROJECT,businessName="删除",operationType= OperationLogEnum.Delete)
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    public ResponseDto delete(@PathVariable Long id){
        ResponseDto mm = new ResponseDto();

        SysOrganizationSearch sysOrganizationSearch = new SysOrganizationSearch();
        sysOrganizationSearch.setRelationId(id);
        List<SysOrganizationList> sysOrganizationLists = sysOrganizationServiceImpl.findByCondition(sysOrganizationSearch);
        if(CollectionUtils.isNotEmpty(sysOrganizationLists)){
            mm.setFlag(ResponseDto.INFOR_WARNING);
            mm.setMessage("您要删除的数据存在组织架构关系中，不允许删除");
            return mm;
        }
        sysProjectServiceImpl.deleteByPrimaryKey(id);
        return mm;
    }
}
