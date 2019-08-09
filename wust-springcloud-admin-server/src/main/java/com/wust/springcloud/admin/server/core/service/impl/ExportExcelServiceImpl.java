package com.wust.springcloud.admin.server.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.wust.springcloud.admin.server.core.dao.SysImportExportMapper;
import com.wust.springcloud.admin.server.core.service.ExportExcelService;
import com.wust.springcloud.admin.server.core.service.SysAttachmentService;
import com.wust.springcloud.common.context.DefaultBusinessContext;
import com.wust.springcloud.common.dto.ExcelDto;
import com.wust.springcloud.common.dto.ResponseDto;
import com.wust.springcloud.common.entity.sys.attachment.SysAttachment;
import com.wust.springcloud.common.util.FileUtil;
import com.wust.springcloud.common.util.MyStringUtils;
import com.wust.springcloud.common.util.cache.DataDictionaryUtil;
import com.wust.springcloud.easyexcel.definition.ExcelDefinitionReader;
import com.wust.springcloud.easyexcel.factory.DefinitionFactory;
import com.wust.springcloud.easyexcel.factory.xml.XMLDefinitionFactory4commonExport;
import com.wust.springcloud.easyexcel.resolver.poi.POIExcelResolver4commonExport;
import com.wust.springcloud.easyexcel.result.ExcelExportResult;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by WST on 2019/5/21.
 */
@Service("exportExcelServiceImpl")
public class ExportExcelServiceImpl extends POIExcelResolver4commonExport implements ExportExcelService {

    @Autowired
    private SysImportExportMapper sysImportExportMapper;

    @Autowired
    private SysAttachmentService sysAttachmentServiceImpl;

    @Autowired
    private MessageSource messageSource;

    @Override
    public ResponseDto export(JSONObject jsonObject) {
        ResponseDto mm = new ResponseDto();

        DefaultBusinessContext ctx = jsonObject.getObject("ctx",DefaultBusinessContext.class);

        ExcelDto excelDto = jsonObject.getObject("excelDto",ExcelDto.class);

        BeanUtils.copyProperties(excelDto,super.excelParameters);


        File tempFile = null;
        FileOutputStream fos = null;
        try {
            String fileName = excelDto.getBatchNo();
            String suffix = "." + excelParameters.getFileType();
            tempFile = FileUtil.createTempFile(fileName,suffix);
            ExcelExportResult excelExportResult = super.createWorkbook();
            Workbook wb = excelExportResult.getWorkbook();
            fos = new FileOutputStream(tempFile);
            wb.write(fos);


            SysAttachment sysAttachment = new SysAttachment();
            sysAttachment.setRelationTable("sys_import_export");
            sysAttachment.setRelationId(excelDto.getBatchNo());
            sysAttachment.setRelationField("excel");
            sysAttachment.setCreaterId(ctx.getUserId());
            sysAttachment.setCreaterName(ctx.getLoginName());
            ResponseDto uploadAttachmentMessageMap = sysAttachmentServiceImpl.uploadAttachment(tempFile,sysAttachment);
            if(ResponseDto.INFOR_SUCCESS.equals(uploadAttachmentMessageMap.getFlag())){
                mm.setCode("100502");
            }else{
                mm.setCode("100504");
                mm.setMessage("上传文件到文件服务器失败：" + uploadAttachmentMessageMap.getMessage());
            }
        } catch (Exception e) {
            mm.setCode("100504");
            if(MyStringUtils.isNotBlank(e.getMessage())){
                mm.setMessage(e.getMessage().substring(0,500));
            }else{
                mm.setMessage("导出失败:" + e.toString().substring(0,500));
            }
        }finally {
            if(fos != null){
                try {
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                }
            }
        }
        return mm;
    }


    @Override
    protected ExcelDefinitionReader getExcelDefinition() {
        DefinitionFactory definitionReaderFactory = new XMLDefinitionFactory4commonExport("easyexcel/export/xml/" + excelParameters.getXmlName() + ".xml");
        return definitionReaderFactory.createExcelDefinitionReader();
    }

    @Override
    protected List<Map<String, Object>> findBySql(Map<String, Object> parseParameters) {
        return this.sysImportExportMapper.findBySql(parseParameters);
    }

    @Override
    protected String getLookupItemNameByCode(String code) {
        DefaultBusinessContext ctx = DefaultBusinessContext.getContext();
        return DataDictionaryUtil.getLookupNameByCode(ctx.getLocale().toString(),code);
    }
}
