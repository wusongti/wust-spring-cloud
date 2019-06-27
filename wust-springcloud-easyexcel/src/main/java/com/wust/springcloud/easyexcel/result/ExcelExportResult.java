package com.wust.springcloud.easyexcel.result;/**
 * Created by wust on 2017/5/18.
 */

import org.apache.poi.ss.usermodel.Workbook;

/**
 *
 * Function:
 * Reason:
 * Date:2017/5/18
 * @author wusongti@163.com
 */
public class ExcelExportResult implements java.io.Serializable {
    private static final long serialVersionUID = -2530638939591673727L;
    private Workbook workbook;

    public Workbook getWorkbook() {
        return workbook;
    }

    public void setWorkbook(Workbook workbook) {
        this.workbook = workbook;
    }
}
