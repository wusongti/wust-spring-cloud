package com.wust.springcloud.admin.server.core.service;

import com.wust.springcloud.common.context.DefaultBusinessContext;
import com.wust.springcloud.common.dto.ResponseDto;
import com.wust.springcloud.common.entity.sys.importexport.SysImportExport;

/**
 * Created by WST on 2019/5/27.
 */
public interface DefaultImportService {
    void importByExcel(String serviceBeanName,SysImportExport sysImportExport, byte[] file_buff);
    ResponseDto importByExcelCallback(DefaultBusinessContext ctx, String batchNo);
    void importByExcelAfter(DefaultBusinessContext ctx,ResponseDto responseDto,String batchNo);
}
