package com.wust.springcloud.admin.server.service.impl;

import com.wust.springcloud.admin.server.dao.SysImportExportMapper;
import com.wust.springcloud.admin.server.service.ExportExcelService;
import com.wust.springcloud.admin.server.service.SysAttachmentService;
import com.wust.springcloud.admin.server.task.ThreadPoolTask;
import com.wust.springcloud.common.context.DefaultBusinessContext;
import com.wust.springcloud.common.dto.ExcelDto;
import com.wust.springcloud.common.dto.MessageMap;
import com.wust.springcloud.common.entity.sys.attachment.SysAttachment;
import com.wust.springcloud.common.entity.sys.importexport.SysImportExport;
import com.wust.springcloud.common.entity.sys.importexport.SysImportExportList;
import com.wust.springcloud.common.entity.sys.importexport.SysImportExportSearch;
import com.wust.springcloud.common.exception.BusinessException;
import com.wust.springcloud.common.util.FileUtil;
import com.wust.springcloud.common.util.cache.DataDictionaryUtil;
import com.wust.springcloud.easyexcel.ExcelParameters;
import com.wust.springcloud.easyexcel.definition.ExcelDefinitionReader;
import com.wust.springcloud.easyexcel.factory.DefinitionFactory;
import com.wust.springcloud.easyexcel.factory.xml.XMLDefinitionFactory4commonExport;
import com.wust.springcloud.easyexcel.resolver.poi.POIExcelResolver4commonExport;
import com.wust.springcloud.easyexcel.result.ExcelExportResult;
import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    static Logger logger = LogManager.getLogger(ExportExcelServiceImpl.class);

    @Autowired
    private SysImportExportMapper sysImportExportMapper;

    @Autowired
    private ThreadPoolTask threadPoolTask;

    @Autowired
    private SysAttachmentService sysAttachmentServiceImpl;

    @Override
    public MessageMap export(ExcelDto excelDto) {
        MessageMap mm = new MessageMap();

        /**
         * 耗时多的业务使用异步
         */
        try {
            excelParameters = new ExcelParameters();
            excelParameters.setBatchNo(excelDto.getBatchNo());
            excelParameters.setParameters(excelDto.getParameters());
            excelParameters.setXmlName(excelDto.getXmlName());
            excelParameters.setFileType(excelDto.getExcelSuffix());

            DefaultBusinessContext ctx = DefaultBusinessContext.getContext();
            threadPoolTask.exportExcelTask(ctx);
        } catch (Exception e) {
            logger.error("导出失败："+e);
            throw new BusinessException("导出失败："+e.getMessage());
        }
        return mm;
    }

    @Override
    public void exportCallback(DefaultBusinessContext ctx) {
        MessageMap mm = new MessageMap();
        mm.setFlag(MessageMap.INFOR_SUCCESS);

        SysImportExportSearch sysImportExportSearch = new SysImportExportSearch();
        sysImportExportSearch.setBatchNo(excelParameters.getBatchNo());
        List<SysImportExportList> sysImportExportLists =  sysImportExportMapper.findByCondition(sysImportExportSearch);

        logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>开始导出，获取到的记录{}",sysImportExportLists);

        if(CollectionUtils.isNotEmpty(sysImportExportLists)){
            final SysImportExportList sysImportExportList = sysImportExportLists.get(0);

            File tempFile = null;
            FileOutputStream fos = null;
            try {
                String fileName = sysImportExportList.getBatchNo();
                String suffix = "." + excelParameters.getFileType();
                tempFile = FileUtil.createTempFile(fileName,suffix);
                logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>放到临时文件夹，",tempFile.getPath());
                ExcelExportResult excelExportResult = super.createWorkbook();
                logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>创建工作薄完成");
                Workbook wb = excelExportResult.getWorkbook();
                logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>得到工作薄工作薄", wb != null);
                fos = new FileOutputStream(tempFile);
                wb.write(fos);
                logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>写入文件流");
            } catch (Exception e) {
                mm.setFlag(MessageMap.INFOR_ERROR);
                mm.setMessage(e.getMessage());
                logger.error(e);
            }finally {
                if(fos != null){
                    try {
                        fos.flush();
                        fos.close();
                    } catch (IOException e) {
                    }
                }

                final SysImportExport sysImportExport = new SysImportExport();
                BeanUtils.copyProperties(sysImportExportList,sysImportExport);

                SysAttachment sysAttachment = new SysAttachment();
                sysAttachment.setRelationTable("sys_import_export");
                sysAttachment.setRelationId(sysImportExport.getBatchNo());
                sysAttachment.setRelationField("excel");
                sysAttachment.setCreaterId(ctx.getUserId());
                sysAttachment.setCreaterName(ctx.getLoginName());
                MessageMap uploadAttachmentMessageMap = sysAttachmentServiceImpl.uploadAttachment(tempFile,sysAttachment);
                if(MessageMap.INFOR_SUCCESS.equals(uploadAttachmentMessageMap.getFlag())){
                    sysImportExport.setStatus("100502");
                }else{
                    logger.error("上传Excel文件到文件服务器发生错误："+uploadAttachmentMessageMap.getMessage());
                    sysImportExport.setStatus("100504");

                    sysAttachment.setRelationField("log");
                    File logFile = FileUtil.createTempLogFile(excelParameters.getBatchNo(),mm.getMessage(),".txt");
                    MessageMap uploadLogAttachmentMessageMap = sysAttachmentServiceImpl.uploadAttachment(logFile,sysAttachment);
                    if(!MessageMap.INFOR_SUCCESS.equals(uploadLogAttachmentMessageMap.getFlag())){
                        sysImportExportList.setDescription("上传文件到文件服务器失败");
                    }
                }
                sysImportExportMapper.update(sysImportExport);
                logger.info("导出完成。。。。");
            }
        }else {
            logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>根据批次号查不到记录{}",excelParameters.getBatchNo());
        }
    }

    @Override
    protected ExcelDefinitionReader getExcelDefinition() {
        DefinitionFactory definitionReaderFactory = new XMLDefinitionFactory4commonExport("importExport/export/xml/" + excelParameters.getXmlName() + ".xml");
        return definitionReaderFactory.createExcelDefinitionReader();
    }

    @Override
    protected List<Map<String, Object>> findBySql(Map<String, Object> parseParameters) {
        return this.sysImportExportMapper.findBySql(parseParameters);
    }

    @Override
    protected String getLookupItemNameByCode(String code) {
        DefaultBusinessContext ctx = DefaultBusinessContext.getContext();
        return DataDictionaryUtil.getLookupNameByCode(ctx.getCompanyId(),code);
    }
}
