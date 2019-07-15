package com.wust.springcloud.admin.server.core.service.impl;

import com.wust.springcloud.admin.server.core.dao.SysImportExportMapper;
import com.wust.springcloud.admin.server.core.service.SysAttachmentService;
import com.wust.springcloud.admin.server.core.task.ThreadPoolTask;
import com.wust.springcloud.common.context.DefaultBusinessContext;
import com.wust.springcloud.common.dto.ResponseDto;
import com.wust.springcloud.common.entity.sys.attachment.SysAttachment;
import com.wust.springcloud.common.entity.sys.importexport.SysImportExport;
import com.wust.springcloud.common.entity.sys.importexport.SysImportExportList;
import com.wust.springcloud.common.entity.sys.importexport.SysImportExportSearch;
import com.wust.springcloud.common.util.MyStringUtils;
import com.wust.springcloud.common.util.cache.DataDictionaryUtil;
import com.wust.springcloud.easyexcel.definition.ExcelDefinitionReader;
import com.wust.springcloud.easyexcel.resolver.poi.POIExcelResolver4commonImport;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.Date;
import java.util.List;

/**
 * Created by WST on 2019/5/27.
 */
@Service("defaultImportServiceImpl")
public class DefaultImportServiceImpl extends POIExcelResolver4commonImport {
    @Autowired
    private SysImportExportMapper sysImportExportMapper;

    @Autowired
    private SysAttachmentService sysAttachmentServiceImpl;

    @Autowired
    private ThreadPoolTask threadPoolTask;

    @Override
    protected ExcelDefinitionReader getExcelDefinition() {
        return null;
    }

    @Override
    protected String getLookupItemCodeByName(String rootCode, String name) {
        DefaultBusinessContext ctx = DefaultBusinessContext.getContext();
        return DataDictionaryUtil.getLookupCodeByRootCodeAndName(ctx.getCompanyId(),rootCode,name);
    }



    public void importByExcel(String serviceBeanName,SysImportExport sysImportExport, byte[] file_buff) {
        DefaultBusinessContext ctx = DefaultBusinessContext.getContext();
        sysImportExportMapper.insert(sysImportExport);
        super.excelInputStream =  new ByteArrayInputStream(file_buff);
        threadPoolTask.importExcelTask(ctx,serviceBeanName,sysImportExport.getBatchNo());
    }

    public void importByExcelAfter(DefaultBusinessContext ctx, ResponseDto responseDto, String batchNo) {
        SysImportExportSearch condition = new SysImportExportSearch();
        condition.setBatchNo(batchNo);
        List<SysImportExportList> tSysImportExportListList = sysImportExportMapper.findByCondition(condition);
        SysImportExportList tSysImportExportListExist = tSysImportExportListList.get(0);

        SysImportExport sysImportExport = new SysImportExport();
        BeanUtils.copyProperties(tSysImportExportListExist,sysImportExport);

        if(MyStringUtils.isBlank(MyStringUtils.null2String(responseDto.getObj()))){
            sysImportExport.setStatus("100504");
            sysImportExport.setMsg(responseDto.getMessage());
        }else{
            sysImportExport.setStatus(responseDto.getObj()+"");
        }

        sysImportExport.setEndTime(new Date());
        sysImportExportMapper.update(sysImportExport);


        SysAttachment sysAttachment = new SysAttachment();
        sysAttachment.setRelationTable("sys_import_export");
        sysAttachment.setRelationId(sysImportExport.getBatchNo());
        sysAttachment.setRelationField("log");
        sysAttachment.setAttachmentName(sysImportExport.getBatchNo()+".txt");

        sysAttachmentServiceImpl.uploadAttachment(responseDto.getMessage().getBytes(),sysAttachment);
    }
}
