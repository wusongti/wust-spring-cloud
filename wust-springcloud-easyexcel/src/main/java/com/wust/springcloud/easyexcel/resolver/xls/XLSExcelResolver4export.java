package com.wust.springcloud.easyexcel.resolver.xls;/**
 * Created by wust on 2018/1/14.
 */

/**
 *
 * Function:
 * Reason:
 * Date:2018/1/14
 * @author wusongti@163.com
 */
public abstract class XLSExcelResolver4export extends XLSExcelResolver {
    protected void doExport(){
        try{
            exportBefore();
            export();
        }finally {
            exportAfter();
        }
    }
    public abstract void exportBefore();
    public abstract void export();
    public abstract void exportAfter();
}
