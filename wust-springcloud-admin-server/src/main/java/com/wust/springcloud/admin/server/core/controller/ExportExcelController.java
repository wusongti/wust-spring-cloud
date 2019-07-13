package com.wust.springcloud.admin.server.core.controller;

import com.wust.springcloud.admin.server.core.service.ExportExcelService;
import com.wust.springcloud.admin.server.core.service.SysImportExportService;
import com.wust.springcloud.common.annotations.OperationLogAnnotation;
import com.wust.springcloud.common.context.DefaultBusinessContext;
import com.wust.springcloud.common.dto.ExcelDto;
import com.wust.springcloud.common.dto.MessageMap;
import com.wust.springcloud.common.entity.sys.importexport.SysImportExport;
import com.wust.springcloud.common.enums.OperationLogEnum;
import com.wust.springcloud.common.util.CodeGenerator;
import com.wust.springcloud.common.util.MyStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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


    @OperationLogAnnotation(moduleName= OperationLogEnum.MODULE_COMMON,businessName="导出Excel",operationType= OperationLogEnum.Export)
    @RequestMapping(value = "/exportExcel", method = RequestMethod.POST)
    public @ResponseBody
    MessageMap exportExcel(@RequestBody ExcelDto excelDto) {
        MessageMap mm = new MessageMap();
        if (excelDto == null) {
            mm.setFlag(MessageMap.INFOR_WARNING);
            mm.setMessage("导出参数[XML Name, Excel Version, Module Name]不能为空。");
            return mm;
        } else if (StringUtils.isBlank(MyStringUtils.null2String(excelDto.getXmlName()))) {
            mm.setFlag(MessageMap.INFOR_WARNING);
            mm.setMessage("XML Name参数不能少噢。");
            return mm;
        } else if (StringUtils.isBlank(MyStringUtils.null2String(excelDto.getExcelSuffix()))) {
            mm.setFlag(MessageMap.INFOR_WARNING);
            mm.setMessage("Excel Suffix参数不能少噢。");
            return mm;
        } else if (!excelDto.getModuleName().matches("[A-Za-z0-9_]+")) {
            mm.setFlag(MessageMap.INFOR_WARNING);
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
        sysImportExportServiceImpl.insert(tSysImportExport);


        Map par = excelDto.getParameters();
        if(par == null){
            par = new HashMap(1);
        }

        excelDto.setBatchNo(batchNo);
        excelDto.setParameters(par);


        mm = exportExcelServiceImpl.export(excelDto);
        return mm;
    }
}
