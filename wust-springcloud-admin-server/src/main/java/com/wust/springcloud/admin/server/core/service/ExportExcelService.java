package com.wust.springcloud.admin.server.core.service;

import com.wust.springcloud.common.context.DefaultBusinessContext;
import com.wust.springcloud.common.dto.ExcelDto;
import com.wust.springcloud.common.dto.ResponseDto;

/**
 * Created by WST on 2019/5/21.
 */
public interface ExportExcelService {

    ResponseDto export(ExcelDto excelDto);

    void exportCallback(DefaultBusinessContext ctx);
}
