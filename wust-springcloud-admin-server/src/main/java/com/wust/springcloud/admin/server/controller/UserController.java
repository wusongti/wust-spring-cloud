package com.wust.springcloud.admin.server.controller;


import com.wust.springcloud.admin.server.service.SysOrganizationService;
import com.wust.springcloud.admin.server.service.SysUserImportService;
import com.wust.springcloud.admin.server.service.SysUserService;
import com.wust.springcloud.common.annotations.OperationLogAnnotation;
import com.wust.springcloud.common.context.DefaultBusinessContext;
import com.wust.springcloud.common.dto.BaseDto;
import com.wust.springcloud.common.dto.MessageMap;
import com.wust.springcloud.common.entity.sys.attachment.SysAttachment;
import com.wust.springcloud.common.entity.sys.importexport.SysImportExport;
import com.wust.springcloud.common.entity.sys.organization.SysOrganizationList;
import com.wust.springcloud.common.entity.sys.organization.SysOrganizationSearch;
import com.wust.springcloud.common.entity.sys.user.SysUser;
import com.wust.springcloud.common.entity.sys.user.SysUserList;
import com.wust.springcloud.common.entity.sys.user.SysUserSearch;
import com.wust.springcloud.common.enums.ApplicationEnum;
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
import java.util.UUID;

/**
 * Created by WST on 2019/5/9.
 */
@RequestMapping("/UserController")
@RestController
public class UserController {
    @Autowired
    private SysUserService sysUserServiceImpl;

    @Autowired
    private SysUserImportService sysUserImportServiceImpl;

    @Autowired
    private SysOrganizationService sysOrganizationServiceImpl;

    @Autowired
    private SpringRedisTools springRedisTools;



    @OperationLogAnnotation(moduleName= OperationLogEnum.MODULE_ADMIN_USER,businessName="分页查询",operationType= OperationLogEnum.Search)
    @RequestMapping(value = "/listPage",method = RequestMethod.POST)
    public @ResponseBody
    BaseDto listPage(@RequestBody SysUserSearch sysUserSearch){
        BaseDto baseDto = new BaseDto();
        MessageMap mm = new MessageMap();
        DefaultBusinessContext ctx = DefaultBusinessContext.getContext();
        List<SysUserList> sysUserLists =  sysUserServiceImpl.listPage(sysUserSearch);
        if(CollectionUtils.isNotEmpty(sysUserLists)){
            for (SysUserList sysUserList : sysUserLists) {
                sysUserList.setSexLabel(DataDictionaryUtil.getLookupNameByCode(ctx.getCompanyId(),sysUserList.getSex()));
                sysUserList.setStatusLabel(DataDictionaryUtil.getLookupNameByCode(ctx.getCompanyId(),sysUserList.getStatus()));
                sysUserList.setTypeLabel(DataDictionaryUtil.getLookupNameByCode(ctx.getCompanyId(),sysUserList.getType()));

                if(springRedisTools.hasKey(String.format(ApplicationEnum.WEB_LOGIN_KEY.getStringValue(),sysUserList.getLoginName()))){
                    sysUserList.setOnlineStatusLabel(DataDictionaryUtil.getLookupNameByCode(ctx.getCompanyId(),"101001"));
                }else{
                    sysUserList.setOnlineStatusLabel(DataDictionaryUtil.getLookupNameByCode(ctx.getCompanyId(),"101002"));
                }
            }
        }
        baseDto.setPage(sysUserSearch.getPageDto());
        baseDto.setLstDto(sysUserLists);
        baseDto.setMessageMap(mm);
        return baseDto;
    }

    @OperationLogAnnotation(moduleName= OperationLogEnum.MODULE_ADMIN_USER,businessName="新增",operationType= OperationLogEnum.Insert)
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public @ResponseBody
    MessageMap create(@RequestBody SysUser sysUser){
        MessageMap mm = new MessageMap();
        DefaultBusinessContext ctx = DefaultBusinessContext.getContext();
        sysUser.setCompanyId(ctx.getCompanyId());
        sysUser.setCreaterId(ctx.getUserId());
        sysUser.setCreaterName(ctx.getLoginName());
        sysUserServiceImpl.insert(sysUser);
        return mm;
    }


    @OperationLogAnnotation(moduleName= OperationLogEnum.MODULE_ADMIN_USER,businessName="更新",operationType= OperationLogEnum.Update)
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public @ResponseBody
    MessageMap update(@RequestBody SysUser sysUser){
        MessageMap mm = new MessageMap();
        DefaultBusinessContext ctx = DefaultBusinessContext.getContext();

        if("100201".equals(sysUser.getType())){
            mm.setFlag(MessageMap.INFOR_WARNING);
            mm.setMessage("不能更新管理员账号");
            return mm;
        }

        sysUser.setModifyId(ctx.getUserId());
        sysUser.setModifyName(ctx.getLoginName());
        sysUserServiceImpl.update(sysUser);
        return mm;
    }

    @OperationLogAnnotation(moduleName= OperationLogEnum.MODULE_ADMIN_USER,businessName="删除",operationType= OperationLogEnum.Delete)
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    public @ResponseBody
    MessageMap delete(@PathVariable String id){
        MessageMap mm = new MessageMap();

        SysUserSearch sysUserSearch = new SysUserSearch();
        sysUserSearch.setId(id);
        List<SysUserList> sysUserLists = sysUserServiceImpl.findByCondition(sysUserSearch);
        if(CollectionUtils.isNotEmpty(sysUserLists)){
            SysUserList sysUserList = sysUserLists.get(0);
            if("100401".equals(sysUserList.getType())){
                mm.setFlag(MessageMap.INFOR_WARNING);
                mm.setMessage("不允许删除管理员账号");
                return mm;
            }

            SysOrganizationSearch sysOrganizationSearch = new SysOrganizationSearch();
            sysOrganizationSearch.setRelationId(id);
            List<SysOrganizationList> sysOrganizationLists = sysOrganizationServiceImpl.findByCondition(sysOrganizationSearch);
            if(CollectionUtils.isNotEmpty(sysOrganizationLists)){
                mm.setFlag(MessageMap.INFOR_WARNING);
                mm.setMessage("您要删除的数据存在组织架构关系中，不允许删除");
                return mm;
            }

            sysUserServiceImpl.delete(id);
        }else{
            mm.setFlag(MessageMap.INFOR_WARNING);
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
    public @ResponseBody
    MessageMap importByExcel (HttpServletRequest request, @RequestParam(value = "file" , required = true) MultipartFile multipartFile) {
        MessageMap mm = new MessageMap();
        DefaultBusinessContext ctx = DefaultBusinessContext.getContext();

        String moduleName = MyStringUtils.null2String(request.getParameter("moduleName"));
        if(StringUtils.isBlank(moduleName)){
            mm.setFlag(MessageMap.INFOR_WARNING);
            mm.setMessage("上传文件失败，模块名必须填。");
            return mm;
        }else if(!moduleName.matches("[A-Za-z0-9_]+")){
            mm.setFlag(MessageMap.INFOR_WARNING);
            mm.setMessage("上传文件失败，模块名只能是字母、数字、下划线或三者的组合。");
            return mm;
        }


        try {

            String batchNo = CodeGenerator.genImportExportCode();
            SysImportExport tSysImportExport = new SysImportExport();
            tSysImportExport.setBatchNo(batchNo);
            tSysImportExport.setModuleName(moduleName);
            tSysImportExport.setStartTime(new Date());
            tSysImportExport.setOperationType("100601");
            tSysImportExport.setStatus("100501");
            tSysImportExport.setCreaterId(ctx.getUserId());
            tSysImportExport.setCreaterName(ctx.getLoginName());

            SysAttachment sysAttachment = new SysAttachment();
            sysAttachment.setAttachmentKey(UUID.randomUUID().toString());
            sysAttachment.setRelationTable("sys_import_export");
            sysAttachment.setRelationId(batchNo);
            sysAttachment.setAttachmentName(multipartFile.getOriginalFilename());
            sysAttachment.setAttachmentSuffix(multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().indexOf(".") + 1));
            sysAttachment.setAttachmentSize((multipartFile.getSize() / 1024)+"");
            sysAttachment.setCreaterId(ctx.getUserId());
            sysAttachment.setCreaterName(ctx.getLoginName());

            this.sysUserImportServiceImpl.importByExcel("sysUserImportServiceImpl",tSysImportExport,multipartFile.getBytes());
        }catch (IOException e){
            mm.setFlag(MessageMap.INFOR_ERROR);
            mm.setMessage("导入失败，转换文件失败。");
            return mm;
        }
        return mm;
    }
}
