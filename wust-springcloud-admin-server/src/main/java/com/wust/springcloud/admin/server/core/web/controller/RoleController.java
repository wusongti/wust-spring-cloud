package com.wust.springcloud.admin.server.core.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.wust.springcloud.admin.server.core.mq.producer.ImportExcelProducer;
import com.wust.springcloud.admin.server.core.service.SysOrganizationService;
import com.wust.springcloud.admin.server.core.service.SysRoleImportService;
import com.wust.springcloud.admin.server.core.service.SysRoleService;
import com.wust.springcloud.common.annotations.OperationLogAnnotation;
import com.wust.springcloud.common.context.DefaultBusinessContext;
import com.wust.springcloud.common.dto.ResponseDto;
import com.wust.springcloud.common.entity.sys.importexport.SysImportExport;
import com.wust.springcloud.common.entity.sys.organization.SysOrganization;
import com.wust.springcloud.common.entity.sys.organization.SysOrganizationList;
import com.wust.springcloud.common.entity.sys.organization.SysOrganizationSearch;
import com.wust.springcloud.common.entity.sys.role.SysRole;
import com.wust.springcloud.common.entity.sys.role.SysRoleList;
import com.wust.springcloud.common.entity.sys.role.SysRoleSearch;
import com.wust.springcloud.common.enums.OperationLogEnum;
import com.wust.springcloud.common.util.CodeGenerator;
import com.wust.springcloud.common.util.MyStringUtils;
import com.wust.springcloud.common.util.cache.DataDictionaryUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by WST on 2019/5/27.
 */
@RequestMapping("/RoleController")
@RestController
public class RoleController {
    @Autowired
    private SysRoleService sysRoleServiceImpl;

    @Autowired
    private SysRoleImportService sysRoleImportServiceImpl;

    @Autowired
    private SysOrganizationService sysOrganizationServiceImpl;

    @Autowired
    private ImportExcelProducer importExcelProducer;


    @OperationLogAnnotation(moduleName= OperationLogEnum.MODULE_ADMIN_ROLE,businessName="分页查询",operationType= OperationLogEnum.Search)
    @RequestMapping(value = "/listPage",method = RequestMethod.POST)
    public ResponseDto listPage(@RequestBody SysRoleSearch sysRoleSearch){
        ResponseDto baseDto = new ResponseDto();
        DefaultBusinessContext ctx = DefaultBusinessContext.getContext();
        List<SysRoleList> sysRoleLists =  sysRoleServiceImpl.listPage(sysRoleSearch);
        if(CollectionUtils.isNotEmpty(sysRoleLists)){
            for (SysRoleList sysRoleList : sysRoleLists) {
                sysRoleList.setStatusLabel(DataDictionaryUtil.getLookupNameByCode(ctx.getLocale().toString(),sysRoleList.getStatus()));
            }
        }
        baseDto.setPage(sysRoleSearch.getPageDto());
        baseDto.setLstDto(sysRoleLists);
        return baseDto;
    }


    @OperationLogAnnotation(moduleName= OperationLogEnum.MODULE_ADMIN_ROLE,businessName="新建",operationType= OperationLogEnum.Insert)
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public ResponseDto create(@RequestBody SysRole sysRole){
        ResponseDto mm = new ResponseDto();
        DefaultBusinessContext ctx = DefaultBusinessContext.getContext();

        sysRole.setCode(CodeGenerator.genRoleCode());
        sysRole.setStatus("100201");
        sysRole.setCreaterId(ctx.getUser().getId());
        sysRole.setCreaterName(ctx.getUser().getRealName());
        int result =  sysRoleServiceImpl.insert(sysRole);
        if(result < 1){
            mm.setFlag(ResponseDto.INFOR_WARNING);
            mm.setMessage("新增了"+result + "条记录");
        }
        return mm;
    }


    @OperationLogAnnotation(moduleName= OperationLogEnum.MODULE_ADMIN_ROLE,businessName="修改",operationType= OperationLogEnum.Update)
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public ResponseDto update(@RequestBody SysRole sysRole){
        ResponseDto mm = new ResponseDto();
        DefaultBusinessContext ctx = DefaultBusinessContext.getContext();

        if(sysRole.getName().toLowerCase().contains("admin")){
            mm.setFlag(ResponseDto.INFOR_WARNING);
            mm.setMessage("不能修改管理角色");
            return mm;
        }

        sysRole.setModifyId(ctx.getUser().getId());
        sysRole.setModifyName(ctx.getUser().getRealName());
        sysRole.setModifyTime(new Date());
        int result =  sysRoleServiceImpl.updateByPrimaryKey(sysRole);
        if(result < 1){
            mm.setFlag(ResponseDto.INFOR_WARNING);
            mm.setMessage("更新了0条记录");
        }
        return mm;
    }


    @OperationLogAnnotation(moduleName= OperationLogEnum.MODULE_ADMIN_ROLE,businessName="删除",operationType= OperationLogEnum.Delete)
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    public ResponseDto delete(@PathVariable Long id){
        ResponseDto mm = new ResponseDto();

        SysRoleSearch sysRoleSearch = new SysRoleSearch();
        sysRoleSearch.setId(id);
        List<SysRole> sysRoleLists = sysRoleServiceImpl.select(sysRoleSearch);
        if(CollectionUtils.isNotEmpty(sysRoleLists)){
            SysRole sysRole = sysRoleLists.get(0);
            if(sysRole.getName().toLowerCase().contains("admin")){
                mm.setFlag(ResponseDto.INFOR_WARNING);
                mm.setMessage("不能删除管理角色");
                return mm;
            }

            SysOrganizationSearch sysOrganizationSearch = new SysOrganizationSearch();
            sysOrganizationSearch.setRelationId(id);
            List<SysOrganization> sysOrganizationLists = sysOrganizationServiceImpl.select(sysOrganizationSearch);
            if(CollectionUtils.isNotEmpty(sysOrganizationLists)){
                mm.setFlag(ResponseDto.INFOR_WARNING);
                mm.setMessage("您要删除的数据存在组织架构关系中，不允许删除");
                return mm;
            }


            List<Long> ids = new ArrayList<>(1);
            ids.add(id);
            sysRoleServiceImpl.batchDelete(ids);
        }else{
            mm.setFlag(ResponseDto.INFOR_WARNING);
            mm.setMessage("该记录已经被其他用户删除");
        }
        return mm;
    }


    /**
     *
     * @param request
     * @param multipartFile
     * @return
     */
    @OperationLogAnnotation(moduleName= OperationLogEnum.MODULE_ADMIN_ROLE,businessName="导入",operationType= OperationLogEnum.Import)
    @RequestMapping(value = "/importByExcel",method= RequestMethod.POST)
    public ResponseDto importByExcel (HttpServletRequest request, @RequestParam(value = "file" , required = true) MultipartFile multipartFile) {
        ResponseDto mm = new ResponseDto();
        DefaultBusinessContext ctx = DefaultBusinessContext.getContext();

        String moduleName = MyStringUtils.null2String(request.getParameter("moduleName"));
        if(StringUtils.isBlank(moduleName)){
            mm.setFlag(ResponseDto.INFOR_WARNING);
            mm.setMessage("上传文件失败，模块名必须填。");
            return mm;
        }else if(!moduleName.matches("[A-Za-z0-9_]+")){
            mm.setFlag(ResponseDto.INFOR_WARNING);
            mm.setMessage("上传文件失败，模块名只能是字母、数字、下划线或三者的组合。");
            return mm;
        }


        try {
            String batchNo = CodeGenerator.genImportExportCode();
            SysImportExport sysImportExport = new SysImportExport();
            sysImportExport.setBatchNo(batchNo);
            sysImportExport.setModuleName(moduleName);
            sysImportExport.setStartTime(new Date());
            sysImportExport.setOperationType("100601");
            sysImportExport.setStatus("100501");
            sysImportExport.setCreaterId(ctx.getUser().getId());
            sysImportExport.setCreaterName(ctx.getUser().getRealName());
            sysImportExport.setCreateTime(new Date());


            JSONObject jsonObject = new JSONObject();
            jsonObject.put("moduleName",moduleName);
            jsonObject.put("fileBytes",multipartFile.getBytes());
            jsonObject.put("sysImportExport",sysImportExport);
            jsonObject.put("ctx",DefaultBusinessContext.getContext());
            importExcelProducer.send(jsonObject);
        }catch (IOException e){
            mm.setFlag(ResponseDto.INFOR_ERROR);
            mm.setMessage("导入失败，转换文件失败。");
            return mm;
        }
        return mm;
    }
}
