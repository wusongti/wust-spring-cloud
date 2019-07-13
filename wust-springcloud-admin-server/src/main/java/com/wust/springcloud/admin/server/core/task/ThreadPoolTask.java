package com.wust.springcloud.admin.server.core.task;

import com.wust.springcloud.admin.server.core.service.DefaultImportService;
import com.wust.springcloud.admin.server.core.service.ExportExcelService;
import com.wust.springcloud.common.context.DefaultBusinessContext;
import com.wust.springcloud.common.dto.MessageMap;
import com.wust.springcloud.common.util.SpringContextHolder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Created by WST on 2019/5/21.
 */
@Component
public class ThreadPoolTask {
    static Logger logger = LogManager.getLogger(ThreadPoolTask.class);

    @Async
    public void exportExcelTask(DefaultBusinessContext ctx) {
        logger.info("-----------------开始导出");
        ExportExcelService  exportExcelService = ((ExportExcelService) SpringContextHolder.getBean("exportExcelServiceImpl"));
        exportExcelService.exportCallback(ctx);
        logger.info("-----------------导出完成");
    }

    @Async
    public void importExcelTask(DefaultBusinessContext ctx,String serviceBeanName,String batchNo) {
        logger.info("-----------------开始导入");
        DefaultImportService  defaultImportService = ((DefaultImportService) SpringContextHolder.getBean(serviceBeanName));
        MessageMap mm = new MessageMap();
        try {
            mm = defaultImportService.importByExcelCallback(ctx, batchNo);
        }finally {
            defaultImportService.importByExcelAfter(ctx,mm,batchNo);
        }
        logger.info("-----------------导入用成");
    }
}
