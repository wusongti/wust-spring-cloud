package com.wust.springcloud.admin.server.core.service;

import com.wust.springcloud.common.context.DefaultBusinessContext;
import com.wust.springcloud.common.dto.MessageMap;
import com.wust.springcloud.common.entity.sys.importexport.SysImportExport;

/**
 * Created by WST on 2019/5/27.
 */
public interface DefaultImportService {
    void importByExcel(String serviceBeanName,SysImportExport sysImportExport, byte[] file_buff);
    MessageMap importByExcelCallback(DefaultBusinessContext ctx, String batchNo);
    void importByExcelAfter(DefaultBusinessContext ctx,MessageMap messageMap,String batchNo);
}
