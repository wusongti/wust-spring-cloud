package com.wust.springcloud.admin.server.core.web.controller;


import com.wust.springcloud.admin.server.core.service.SysAttachmentService;
import com.wust.springcloud.admin.server.core.service.SysImportExportService;
import com.wust.springcloud.common.annotations.OperationLogAnnotation;
import com.wust.springcloud.common.context.DefaultBusinessContext;
import com.wust.springcloud.common.dto.ResponseDto;
import com.wust.springcloud.common.entity.sys.attachment.SysAttachmentSearch;
import com.wust.springcloud.common.entity.sys.importexport.SysImportExportList;
import com.wust.springcloud.common.entity.sys.importexport.SysImportExportSearch;
import com.wust.springcloud.common.enums.OperationLogEnum;
import com.wust.springcloud.common.util.cache.DataDictionaryUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * Created by WST on 2019/5/17.
 */
@RequestMapping("/MyImportExportController")
@RestController
public class MyImportExportController {
    @Autowired
    private SysImportExportService sysImportExportServiceImpl;

    @Autowired
    private SysAttachmentService sysAttachmentServiceImpl;

    @OperationLogAnnotation(moduleName= OperationLogEnum.MODULE_ADMIN_IMPORT_EXPORT,businessName="分页查询导入导出列表",operationType= OperationLogEnum.Search)
    @RequestMapping(value = "/listPage",method = RequestMethod.POST)
    public ResponseDto listPage(@RequestBody SysImportExportSearch sysImportExportSearch){
        ResponseDto baseDto = new ResponseDto();
        DefaultBusinessContext ctx = DefaultBusinessContext.getContext();

        List<SysImportExportList> sysImportExportLists =  sysImportExportServiceImpl.listPage(sysImportExportSearch);
        if(CollectionUtils.isNotEmpty(sysImportExportLists)){
            for (SysImportExportList sysImportExportList : sysImportExportLists) {
                sysImportExportList.setStatusLabel(DataDictionaryUtil.getLookupNameByCode(ctx.getLocale().toString(),sysImportExportList.getStatus()));
                sysImportExportList.setOperationTypeLabel(DataDictionaryUtil.getLookupNameByCode(ctx.getLocale().toString(),sysImportExportList.getOperationType()));
            }
        }
        baseDto.setPage(sysImportExportSearch.getPageDto());
        baseDto.setLstDto(sysImportExportLists);
        return baseDto;
    }



    @OperationLogAnnotation(moduleName= OperationLogEnum.MODULE_ADMIN_IMPORT_EXPORT,businessName="下载",operationType= OperationLogEnum.Download)
    @RequestMapping(value="/downloadFile",method=RequestMethod.GET)
    public ResponseDto downloadFile(HttpServletRequest request, HttpServletResponse response){
        ResponseDto mm = new ResponseDto();
        String relationId = request.getParameter("relationId");
        String relationField = request.getParameter("relationField");
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        OutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();

            SysAttachmentSearch sysAttachmentSearch = new SysAttachmentSearch();
            sysAttachmentSearch.setRelationTable("sys_import_export");
            sysAttachmentSearch.setRelationId(relationId);
            sysAttachmentSearch.setRelationField(relationField);

            mm = sysAttachmentServiceImpl.downloadAttachment(sysAttachmentSearch);
            if(ResponseDto.INFOR_SUCCESS.equals(mm.getFlag())){
                Map resultMap = mm.getMapMessage();
                byte[] fileByte = (byte[]) resultMap.get("fileByte");
                String fileName = resultMap.get("fileName")+"";
                response.setHeader("Content-Disposition","attachment;filename=" + new String(fileName.getBytes("UTF-8"), "ISO8859-1"));
                outputStream.write(fileByte);
            }
        } catch (Exception e) {
            mm.setFlag(ResponseDto.INFOR_ERROR);
            mm.setMessage("系统异常!");
        }finally {
            if(outputStream != null){
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                }
            }
        }
        return mm;
    }
}
