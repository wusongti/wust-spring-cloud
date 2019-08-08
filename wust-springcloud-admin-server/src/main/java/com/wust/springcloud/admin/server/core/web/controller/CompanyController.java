package com.wust.springcloud.admin.server.core.web.controller;

import com.wust.springcloud.admin.server.core.service.SysCompanyService;
import com.wust.springcloud.admin.server.core.service.SysOrganizationService;
import com.wust.springcloud.common.annotations.OperationLogAnnotation;
import com.wust.springcloud.common.context.DefaultBusinessContext;
import com.wust.springcloud.common.dto.ResponseDto;
import com.wust.springcloud.common.entity.sys.company.*;
import com.wust.springcloud.common.entity.sys.organization.SysOrganizationList;
import com.wust.springcloud.common.entity.sys.organization.SysOrganizationSearch;
import com.wust.springcloud.common.enums.OperationLogEnum;
import com.wust.springcloud.common.util.CodeGenerator;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by WST on 2019/6/3.
 */
@RequestMapping("/CompanyController")
@RestController
public class CompanyController {

    @Autowired
    private SysCompanyService sysCompanyServiceImpl;

    @Autowired
    private SysOrganizationService sysOrganizationServiceImpl;

    @Autowired
    private MessageSource messageSource;

    @OperationLogAnnotation(moduleName= OperationLogEnum.MODULE_ADMIN_COMPANY,businessName="分页查询",operationType= OperationLogEnum.Search)
    @RequestMapping(value = "/listPage",method = RequestMethod.POST)
    public ResponseDto listPage(@RequestBody SysCompanySearch search){
        DefaultBusinessContext ctx = DefaultBusinessContext.getContext();
        System.out.println("国际化测试=" + messageSource.getMessage("admin.server.message1",null,ctx.getLocale()));

        ResponseDto baseDto = new ResponseDto();
        List<SysCompanyList> sysCompanyLists =  sysCompanyServiceImpl.listPage(search);
        baseDto.setPage(search.getPageDto());
        baseDto.setLstDto(sysCompanyLists);
        return baseDto;
    }


    @OperationLogAnnotation(moduleName= OperationLogEnum.MODULE_ADMIN_COMPANY,businessName="新建",operationType= OperationLogEnum.Insert)
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public ResponseDto create(@RequestBody SysCompany entity){
        ResponseDto mm = new ResponseDto();
        DefaultBusinessContext ctx = DefaultBusinessContext.getContext();


        SysCompanySearch sysCompanySearch = new SysCompanySearch();
        sysCompanySearch.setName(entity.getName());
        List<SysCompanyList> companyLists = sysCompanyServiceImpl.findByCondition(sysCompanySearch);
        if(CollectionUtils.isNotEmpty(companyLists)){
            mm.setFlag(ResponseDto.INFOR_WARNING);
            mm.setMessage("您输入的公司名称已经存在");
            return mm;
        }

        entity.setCode(CodeGenerator.genCompanyCode());
        entity.setCreaterId(ctx.getUserId());
        entity.setCreaterName(ctx.getLoginName());
        entity.setCreateTime(new Date());
        sysCompanyServiceImpl.insert(entity);
        return mm;
    }


    @OperationLogAnnotation(moduleName= OperationLogEnum.MODULE_ADMIN_COMPANY,businessName="修改",operationType= OperationLogEnum.Update)
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public ResponseDto update(@RequestBody SysCompany entity){
        ResponseDto mm = new ResponseDto();
        DefaultBusinessContext ctx = DefaultBusinessContext.getContext();

        SysCompanySearch sysCompanySearch = new SysCompanySearch();
        sysCompanySearch.setName(entity.getName());
        List<SysCompanyList> companyLists = sysCompanyServiceImpl.findByCondition(sysCompanySearch);
        if(CollectionUtils.isNotEmpty(companyLists)){
            if(!companyLists.get(0).getCode().equals(entity.getCode())){
                mm.setFlag(ResponseDto.INFOR_WARNING);
                mm.setMessage("您输入的公司名称已经存在，不能修改为系统已经存在的公司");
                return mm;
            }
        }

        entity.setModifyId(ctx.getUserId());
        entity.setModifyName(ctx.getLoginName());
        entity.setModifyTime(new Date());
        sysCompanyServiceImpl.updateByPrimaryKey(entity);
        return mm;
    }


    @OperationLogAnnotation(moduleName= OperationLogEnum.MODULE_ADMIN_COMPANY,businessName="删除",operationType= OperationLogEnum.Delete)
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

        sysCompanyServiceImpl.deleteByPrimaryKey(id);
        return mm;
    }
}
