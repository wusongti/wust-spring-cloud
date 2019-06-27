package com.wust.springcloud.admin.server.service;

import com.wust.springcloud.common.context.DefaultBusinessContext;
import com.wust.springcloud.common.dto.ExcelDto;
import com.wust.springcloud.common.dto.MessageMap;

/**
 * Created by WST on 2019/5/21.
 */
public interface ExportExcelService {

    MessageMap export(ExcelDto excelDto);

    void exportCallback(DefaultBusinessContext ctx);
}
