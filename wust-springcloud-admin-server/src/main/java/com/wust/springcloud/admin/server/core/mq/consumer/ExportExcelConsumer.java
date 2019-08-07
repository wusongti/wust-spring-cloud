package com.wust.springcloud.admin.server.core.mq.consumer;

import com.wust.springcloud.admin.server.core.service.defaults.ExportExcelService;
import com.wust.springcloud.common.dto.ExcelDto;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    @RabbitHandler
    public void process(ExcelDto excelDto) {
        System.out.println("Receiver : " + excelDto);
        exportExcelServiceImpl.export(excelDto);
    }


    private void before(){

    }

    private void doExport(){

    }

    private void after(){

    }
}
