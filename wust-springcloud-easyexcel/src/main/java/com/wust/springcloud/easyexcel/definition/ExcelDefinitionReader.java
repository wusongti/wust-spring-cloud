package com.wust.springcloud.easyexcel.definition;/**
 * Created by wust on 2017/5/8.
 */


import com.wust.springcloud.easyexcel.ConfigDefinitionBean;

/**
 *
 * Function:注册配置接口
 * Reason:
 *
 * Date:2017/5/8
 * @author wusongti@163.com
 */
public interface ExcelDefinitionReader {
    /**
     * 注册配置
     * @return
     */
    ConfigDefinitionBean registerConfigDefinitions();
}
