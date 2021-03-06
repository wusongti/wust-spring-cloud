package com.wust.springcloud.admin.server.core.web.controller;


import com.alibaba.fastjson.JSONObject;
import com.wust.springcloud.admin.server.core.mq.producer.ImportExcelProducer;
import com.wust.springcloud.admin.server.core.service.SysOrganizationService;
import com.wust.springcloud.admin.server.core.service.SysUserService;
import com.wust.springcloud.common.annotations.OperationLogAnnotation;
import com.wust.springcloud.common.context.DefaultBusinessContext;
import com.wust.springcloud.common.dto.ResponseDto;
import com.wust.springcloud.common.entity.sys.importexport.SysImportExport;
import com.wust.springcloud.common.entity.sys.organization.SysOrganization;
import com.wust.springcloud.common.entity.sys.organization.SysOrganizationList;
import com.wust.springcloud.common.entity.sys.organization.SysOrganizationSearch;
import com.wust.springcloud.common.entity.sys.user.SysUser;
import com.wust.springcloud.common.entity.sys.user.SysUserList;
import com.wust.springcloud.common.entity.sys.user.SysUserSearch;
import com.wust.springcloud.common.enums.DataDictionaryEnum;
import com.wust.springcloud.common.enums.OperationLogEnum;
import com.wust.springcloud.common.util.CodeGenerator;
import com.wust.springcloud.common.util.MyStringUtils;
import com.wust.springcloud.common.util.cache.DataDictionaryUtil;
import com.wust.springcloud.common.util.cache.SpringRedisTools;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by WST on 2019/5/9.
 */
@RequestMapping("/UserController")
@RestController
public class UserController {
    @Autowired
    private SysUserService sysUserServiceImpl;

    @Autowired
    private SysOrganizationService sysOrganizationServiceImpl;

    @Autowired
    private SpringRedisTools springRedisTools;

    @Autowired
    private ImportExcelProducer importExcelProducer;


    @OperationLogAnnotation(moduleName= OperationLogEnum.MODULE_ADMIN_USER,businessName="分页查询",operationType= OperationLogEnum.Search)
    @RequestMapping(value = "/listPage",method = RequestMethod.POST)
    public ResponseDto listPage(@RequestBody SysUserSearch sysUserSearch){
        ResponseDto baseDto = new ResponseDto();
        DefaultBusinessContext ctx = DefaultBusinessContext.getContext();
        List<SysUserList> sysUserLists =  sysUserServiceImpl.listPage(sysUserSearch);
        if(CollectionUtils.isNotEmpty(sysUserLists)){
            for (SysUserList sysUserList : sysUserLists) {
                sysUserList.setSexLabel(DataDictionaryUtil.getLookupNameByCode(ctx.getLocale().toString(),sysUserList.getSex()));
                sysUserList.setStatusLabel(DataDictionaryUtil.getLookupNameByCode(ctx.getLocale().toString(),sysUserList.getStatus()));
                sysUserList.setTypeLabel(DataDictionaryUtil.getLookupNameByCode(ctx.getLocale().toString(),sysUserList.getType()));
            }
        }
        baseDto.setPage(sysUserSearch.getPageDto());
        baseDto.setLstDto(sysUserLists);
        return baseDto;
    }

    @OperationLogAnnotation(moduleName= OperationLogEnum.MODULE_ADMIN_USER,businessName="新增",operationType= OperationLogEnum.Insert)
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public ResponseDto create(@RequestBody SysUser sysUser){
        ResponseDto mm = new ResponseDto();
        DefaultBusinessContext ctx = DefaultBusinessContext.getContext();
        sysUser.setStatus("100201");
        sysUser.setCompanyId(ctx.getCompanyId());
        sysUser.setCreaterId(ctx.getUser().getId());
        sysUser.setCreaterName(ctx.getUser().getRealName());
        sysUserServiceImpl.insert(sysUser);
        return mm;
    }


    @OperationLogAnnotation(moduleName= OperationLogEnum.MODULE_ADMIN_USER,businessName="更新",operationType= OperationLogEnum.Update)
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public ResponseDto update(@RequestBody SysUser sysUser){
        ResponseDto mm = new ResponseDto();
        DefaultBusinessContext ctx = DefaultBusinessContext.getContext();

        if("100201".equals(sysUser.getType())){
            mm.setFlag(ResponseDto.INFOR_WARNING);
            mm.setMessage("不能更新管理员账号");
            return mm;
        }

        sysUser.setModifyId(ctx.getUser().getId());
        sysUser.setModifyName(ctx.getUser().getRealName());
        sysUser.setModifyTime(new Date());
        sysUserServiceImpl.updateByPrimaryKey(sysUser);
        return mm;
    }

    @OperationLogAnnotation(moduleName= OperationLogEnum.MODULE_ADMIN_USER,businessName="删除",operationType= OperationLogEnum.Delete)
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    public ResponseDto delete(@PathVariable Long id){
        ResponseDto mm = new ResponseDto();

        SysUserSearch sysUserSearch = new SysUserSearch();
        sysUserSearch.setId(id);
        List<SysUser> sysUserLists = sysUserServiceImpl.select(sysUserSearch);
        if(CollectionUtils.isNotEmpty(sysUserLists)){
            SysUser sysUser = sysUserLists.get(0);
            if(DataDictionaryEnum.USER_TYPE_PLATFORM_ADMIN.getStringValue().equals(sysUser.getType())){
                mm.setFlag(ResponseDto.INFOR_WARNING);
                mm.setMessage("不允许删除管理员账号");
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

            sysUserServiceImpl.deleteByPrimaryKey(id);
        }else{
            mm.setFlag(ResponseDto.INFOR_WARNING);
            mm.setMessage("该账号已经被其他用户删除");
            return mm;
        }
        return mm;
    }


    /**
     *
     * @param request
     * @param multipartFile
     * @return
     */
    @OperationLogAnnotation(moduleName= OperationLogEnum.MODULE_ADMIN_USER,businessName="导入",operationType= OperationLogEnum.Import)
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
