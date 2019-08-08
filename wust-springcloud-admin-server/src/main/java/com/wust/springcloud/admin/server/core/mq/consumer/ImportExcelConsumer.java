package com.wust.springcloud.admin.server.core.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.wust.springcloud.admin.server.core.service.SysImportExportService;
import com.wust.springcloud.admin.server.core.service.SysRoleImportService;
import com.wust.springcloud.admin.server.core.service.SysUserImportService;
import com.wust.springcloud.common.dto.ResponseDto;
import com.wust.springcloud.common.entity.sys.importexport.SysImportExport;
import com.wust.springcloud.common.entity.sys.importexport.SysImportExportList;
import com.wust.springcloud.common.entity.sys.importexport.SysImportExportSearch;
import com.wust.springcloud.common.util.MyStringUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
@RabbitListener(queues = "${queue.importexcel.name}",containerFactory = "singleListenerContainer")
public class ImportExcelConsumer {
    static Logger logger = LogManager.getLogger(ImportExcelConsumer.class);

    @Autowired
    private SysImportExportService sysImportExportServiceImpl;

    @Autowired
    private SysRoleImportService sysRoleImportServiceImpl;

    @Autowired
    private SysUserImportService sysUserImportServiceImpl;

    @RabbitHandler
    public void process(JSONObject jsonObject) {
        ResponseDto responseDto = new ResponseDto();
        try {
            this.before(jsonObject);
            responseDto = this.doImport(jsonObject);
        }catch (Exception e){
            responseDto.setCode("100504");
            if(MyStringUtils.isNotBlank(e.getMessage())){
                responseDto.setMessage(e.getMessage().substring(0,500));
            }else{
                responseDto.setMessage("导入失败:" + e.toString().substring(0,500));
            }
        }finally {
            this.after(jsonObject,responseDto);
        }
    }

    /**
     * 导入之前，记录初始状态和开始时间
     */
    private void before(JSONObject jsonObject){
        SysImportExport sysImportExport = jsonObject.getObject("sysImportExport", SysImportExport.class);
        sysImportExportServiceImpl.insert(sysImportExport);
    }

    /**
     * 执行导入
     */
    private ResponseDto doImport(JSONObject jsonObject){
        ResponseDto responseDto = new ResponseDto();
        String moduleName = jsonObject.getString("moduleName");
        if ("company".equalsIgnoreCase(moduleName)) { // 公司

        } else if ("department".equalsIgnoreCase(moduleName)) { // 部门

        } else if ("role".equalsIgnoreCase(moduleName)) { // 角色
            return sysRoleImportServiceImpl.importByExcel(jsonObject);
        } else if ("user".equalsIgnoreCase(moduleName)) { // 用户
            return sysUserImportServiceImpl.importByExcel(jsonObject);
        } else if ("project".equalsIgnoreCase(moduleName)) { // 项目

        } else {
            logger.error("未知的导入类型：{}", moduleName);
        }
        return responseDto;
    }

    /**
     * 导入后记录导入结果和结束时间
     */
    private void after(JSONObject jsonObject, ResponseDto responseDto){
        SysImportExport sysImportExport = jsonObject.getObject("sysImportExport", SysImportExport.class);

        SysImportExportSearch condition = new SysImportExportSearch();
        condition.setBatchNo(sysImportExport.getBatchNo());
        SysImportExport sysImportExportUpdate = sysImportExportServiceImpl.selectOne(condition) == null ? null : (SysImportExportList)sysImportExportServiceImpl.selectOne(condition);
        if(sysImportExportUpdate != null){
            sysImportExportUpdate.setStatus(responseDto.getCode());
            sysImportExportUpdate.setMsg(responseDto.getMessage());
            sysImportExportUpdate.setEndTime(new Date());
            sysImportExportUpdate.setModifyTime(new Date());
            sysImportExportServiceImpl.updateByPrimaryKey(sysImportExportUpdate);
        }
    }
}
