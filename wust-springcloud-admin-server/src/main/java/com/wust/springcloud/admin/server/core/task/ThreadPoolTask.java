package com.wust.springcloud.admin.server.core.task;

import com.wust.springcloud.admin.server.core.service.imports.DefaultImportService;
import com.wust.springcloud.common.context.DefaultBusinessContext;
import com.wust.springcloud.common.dto.ResponseDto;
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
    public void importExcelTask(DefaultBusinessContext ctx,String serviceBeanName,String batchNo) {
        logger.info("-----------------开始导入");
        DefaultImportService  defaultImportService = ((DefaultImportService) SpringContextHolder.getBean(serviceBeanName));
        ResponseDto mm = new ResponseDto();
        try {
            mm = defaultImportService.importByExcelCallback(ctx, batchNo);
        }finally {
            defaultImportService.importByExcelAfter(ctx,mm,batchNo);
        }
        logger.info("-----------------导入用成");
    }
}
