package com.wust.springcloud.admin.server.core.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.wust.springcloud.admin.server.core.service.ExportExcelService;
import com.wust.springcloud.admin.server.core.service.SysImportExportService;
import com.wust.springcloud.common.dto.ExcelDto;
import com.wust.springcloud.common.dto.ResponseDto;
import com.wust.springcloud.common.entity.sys.importexport.SysImportExport;
import com.wust.springcloud.common.entity.sys.importexport.SysImportExportList;
import com.wust.springcloud.common.entity.sys.importexport.SysImportExportSearch;
import com.wust.springcloud.common.util.MyStringUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author ：wust
 * @date ：Created in 2019/7/19 15:21
 * @description：
 * @version:
 */
@Component
@RabbitListener(queues = "${queue.exportexcel.name}",containerFactory = "singleListenerContainer")
public class ExportExcelConsumer {
    @Autowired
    private ExportExcelService exportExcelServiceImpl;

    @Autowired
    private SysImportExportService sysImportExportServiceImpl;

    @RabbitHandler
    public void process(JSONObject jsonObject) {
        ResponseDto responseDto = new ResponseDto();
        try {
            this.before(jsonObject);
            responseDto = this.doExport(jsonObject);
        }catch (Exception e){
            responseDto.setCode("100504");
            if(MyStringUtils.isNotBlank(e.getMessage())){
                responseDto.setMessage(e.getMessage().substring(0,500));
            }else{
                responseDto.setMessage("导出失败:" + e.toString().substring(0,500));
            }
        }finally {
            this.after(jsonObject,responseDto);
        }
    }


    /**
     * 导出前，记录初始状态和开始时间
     * @param jsonObject
     */
    private void before(JSONObject jsonObject){
        SysImportExport sysImportExport = jsonObject.getObject("sysImportExport",SysImportExport.class);
        sysImportExportServiceImpl.insert(sysImportExport);
    }

    /**
     * 执行导出
     * @param jsonObject
     */
    private ResponseDto doExport(JSONObject jsonObject){
        return exportExcelServiceImpl.export(jsonObject);
    }

    /**
     * 导出后，记录结果状态，并记录结束时间
     * @param jsonObject
     * @param responseDto
     */
    private void after(JSONObject jsonObject, ResponseDto responseDto){
        SysImportExport sysImportExport = jsonObject.getObject("sysImportExport", SysImportExport.class);

        SysImportExportSearch condition = new SysImportExportSearch();
        condition.setBatchNo(sysImportExport.getBatchNo());
        List<SysImportExportList> tSysImportExportListList = sysImportExportServiceImpl.findByCondition(condition);
        if(CollectionUtils.isNotEmpty(tSysImportExportListList)){
            SysImportExportList sysImportExportUpdate = tSysImportExportListList.get(0);

            sysImportExportUpdate.setStatus(responseDto.getCode());
            sysImportExportUpdate.setMsg(responseDto.getMessage());
            sysImportExportUpdate.setEndTime(new Date());
            sysImportExportUpdate.setModifyTime(new Date());
            sysImportExportServiceImpl.updateByPrimaryKey(sysImportExportUpdate);
        }
    }
}
