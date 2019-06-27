package com.wust.springcloud.easyexcel.resolver.xls;/**
 * Created by wust on 2018/1/14.
 */

import com.wust.springcloud.easyexcel.resolver.ExcelResolver;
import com.wust.springcloud.easyexcel.result.ExcelExportResult;
import com.wust.springcloud.easyexcel.result.ExcelImportResult;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 *
 * Function:
 * Reason:
 * Date:2018/1/14
 * @author wusongti@163.com
 */
public abstract class XLSExcelResolver implements ExcelResolver {
    @Override
    public ExcelImportResult readExcel() {
        return null;
    }

    @Override
    public ExcelExportResult createWorkbook() {
        return null;
    }

    @Override
    public void setCellValue(Cell cell, Object value) {

    }

    @Override
    public Object getCellValue(Cell cell) {
        return null;
    }

    @Override
    public boolean isRowEmpty(Row row) {
        return false;
    }
}
