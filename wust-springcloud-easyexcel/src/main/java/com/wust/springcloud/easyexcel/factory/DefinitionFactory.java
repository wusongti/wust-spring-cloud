package com.wust.springcloud.easyexcel.factory;/**
 * Created by wust on 2017/8/4.
 */

import com.wust.springcloud.easyexcel.definition.ExcelDefinitionReader;

/**
 *
 * Function:抽象工厂
 * Reason:负责生产ExcelDefinitionReader
 * Date:2017/8/4
 * @author wusongti@163.com
 */
public interface DefinitionFactory {
    ExcelDefinitionReader createExcelDefinitionReader();
}
