package com.wust.springcloud.admin.server.core.api.web;

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
import com.wust.springcloud.common.util.MyStringUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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

    @OperationLogAnnotation(moduleName= OperationLogEnum.MODULE_ADMIN_COMPANY,businessName="分页查询",operationType= OperationLogEnum.Search)
    @RequestMapping(value = "/listPage",method = RequestMethod.POST)
    public @ResponseBody
    ResponseDto listPage(@RequestBody SysCompanySearch search){
        ResponseDto baseDto = new ResponseDto();
        List<SysCompanyList> sysCompanyLists =  sysCompanyServiceImpl.listPage(search);
        baseDto.setPage(search.getPageDto());
        baseDto.setLstDto(sysCompanyLists);
        return baseDto;
    }


    @OperationLogAnnotation(moduleName= OperationLogEnum.MODULE_ADMIN_COMPANY,businessName="新建",operationType= OperationLogEnum.Insert)
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public @ResponseBody
    ResponseDto create(@RequestBody SysCompanyCreate entity){
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


        if(MyStringUtils.isNotBlank(MyStringUtils.null2String(entity.getPname()))){
            sysCompanySearch = new SysCompanySearch();
            sysCompanySearch.setName(entity.getPname());
            List<SysCompanyList> parentCompanyLists = sysCompanyServiceImpl.findByCondition(sysCompanySearch);
            if(CollectionUtils.isEmpty(parentCompanyLists)){
                mm.setFlag(ResponseDto.INFOR_WARNING);
                mm.setMessage("您输入的父级公司不存在");
                return mm;
            }
            entity.setPcode(parentCompanyLists.get(0).getCode());
        }


        entity.setCode(CodeGenerator.genCompanyCode());
        entity.setCreaterId(ctx.getUserId());
        entity.setCreaterName(ctx.getLoginName());
        sysCompanyServiceImpl.insert(entity);
        return mm;
    }


    @OperationLogAnnotation(moduleName= OperationLogEnum.MODULE_ADMIN_COMPANY,businessName="修改",operationType= OperationLogEnum.Update)
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public @ResponseBody
    ResponseDto update(@RequestBody SysCompanyUpdate entity){
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

        if(MyStringUtils.isNotBlank(MyStringUtils.null2String(entity.getPname()))){
            sysCompanySearch = new SysCompanySearch();
            sysCompanySearch.setName(entity.getPname());
            List<SysCompanyList> parentCompanyLists = sysCompanyServiceImpl.findByCondition(sysCompanySearch);
            if(CollectionUtils.isEmpty(parentCompanyLists)){
                mm.setFlag(ResponseDto.INFOR_WARNING);
                mm.setMessage("您输入的父级公司不存在");
                return mm;
            }
            entity.setPcode(parentCompanyLists.get(0).getCode());
        }

        entity.setModifyId(ctx.getUserId());
        entity.setModifyName(ctx.getLoginName());
        sysCompanyServiceImpl.update(entity);
        return mm;
    }


    @OperationLogAnnotation(moduleName= OperationLogEnum.MODULE_ADMIN_COMPANY,businessName="删除",operationType= OperationLogEnum.Delete)
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    public @ResponseBody
    ResponseDto delete(@PathVariable String id){
        ResponseDto mm = new ResponseDto();

        SysCompanySearch sysCompanySearch = new SysCompanySearch();
        sysCompanySearch.setId(id);
        List<SysCompanyList> companyLists = sysCompanyServiceImpl.findByCondition(sysCompanySearch);
        if(CollectionUtils.isNotEmpty(companyLists)){
            sysCompanySearch = new SysCompanySearch();
            sysCompanySearch.setPcode( companyLists.get(0).getCode());
            companyLists = sysCompanyServiceImpl.findByCondition(sysCompanySearch);
            if(CollectionUtils.isNotEmpty(companyLists)){
                mm.setFlag(ResponseDto.INFOR_WARNING);
                mm.setMessage("您要删除的公司存在子公司，不允许直接删除父级公司");
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

        sysCompanyServiceImpl.delete(id);
        return mm;
    }
}
