package com.wust.springcloud.admin.server.core.web.controller;

import com.wust.springcloud.admin.server.core.mq.producer.ExportExcelProducer;
import com.wust.springcloud.admin.server.core.service.defaults.ExportExcelService;
import com.wust.springcloud.admin.server.core.service.defaults.SysImportExportService;
import com.wust.springcloud.common.annotations.OperationLogAnnotation;
import com.wust.springcloud.common.context.DefaultBusinessContext;
import com.wust.springcloud.common.dto.ExcelDto;
import com.wust.springcloud.common.dto.ResponseDto;
import com.wust.springcloud.common.entity.sys.importexport.SysImportExport;
import com.wust.springcloud.common.enums.OperationLogEnum;
import com.wust.springcloud.common.util.CodeGenerator;
import com.wust.springcloud.common.util.MyStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by WST on 2019/5/24.
 */
@RequestMapping("/ExportExcelController")
@RestController
public class ExportExcelController {
    @Autowired
    private SysImportExportService sysImportExportServiceImpl;

    @Autowired
    private ExportExcelService exportExcelServiceImpl;

    @Autowired
    private ExportExcelProducer exportExcelProducer;

    @OperationLogAnnotation(moduleName= OperationLogEnum.MODULE_COMMON,businessName="导出Excel",operationType= OperationLogEnum.Export)
    @RequestMapping(value = "/exportExcel", method = RequestMethod.POST)
    public @ResponseBody
    ResponseDto exportExcel(@RequestBody ExcelDto excelDto) {
        ResponseDto mm = new ResponseDto();
        if (excelDto == null) {
            mm.setFlag(ResponseDto.INFOR_WARNING);
            mm.setMessage("导出参数[XML Name, Excel Version, Module Name]不能为空。");
            return mm;
        } else if (StringUtils.isBlank(MyStringUtils.null2String(excelDto.getXmlName()))) {
            mm.setFlag(ResponseDto.INFOR_WARNING);
            mm.setMessage("XML Name参数不能少噢。");
            return mm;
        } else if (StringUtils.isBlank(MyStringUtils.null2String(excelDto.getFileType()))) {
            mm.setFlag(ResponseDto.INFOR_WARNING);
            mm.setMessage("File Type参数不能少噢。");
            return mm;
        } else if (!excelDto.getModuleName().matches("[A-Za-z0-9_]+")) {
            mm.setFlag(ResponseDto.INFOR_WARNING);
            mm.setMessage("上传文件失败，模块名只能是字母、数字、下划线或三者的组合。");
            return mm;
        }

        DefaultBusinessContext ctx = DefaultBusinessContext.getContext();

        String batchNo = CodeGenerator.genImportExportCode();
        SysImportExport tSysImportExport = new SysImportExport();
        tSysImportExport.setBatchNo(batchNo);
        tSysImportExport.setModuleName(excelDto.getModuleName());
        tSysImportExport.setStartTime(new Date());
        tSysImportExport.setOperationType("100602");
        tSysImportExport.setStatus("100501");
        tSysImportExport.setCreaterId(ctx.getUserId());
        tSysImportExport.setCreaterName(ctx.getLoginName());
        tSysImportExport.setCreateTime(new Date());
        sysImportExportServiceImpl.insert(tSysImportExport);

        excelDto.setBatchNo(batchNo);
        exportExcelProducer.send(excelDto);

        return mm;
    }
}
