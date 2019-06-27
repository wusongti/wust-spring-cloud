package com.wust.springcloud.easyexcel.resolver.poi;/**
 * Created by wust on 2017/8/7.
 */

import com.wust.springcloud.easyexcel.EasyExcelEnum;
import com.wust.springcloud.easyexcel.definition.ExcelDefinitionReader;
import com.wust.springcloud.easyexcel.exception.EasyExcelException;
import com.wust.springcloud.easyexcel.result.ExcelImportResult;
import com.wust.springcloud.easyexcel.util.MyExcelUtils;
import com.wust.springcloud.easyexcel.util.ReflectUtil;
import com.wust.springcloud.easyexcel.xmlobject.common.XMLExcel4Import;
import com.wust.springcloud.easyexcel.xmlobject.common.XMLField4Import;
import com.wust.springcloud.easyexcel.xmlobject.common.XMLList4Import;
import com.wust.springcloud.easyexcel.xmlobject.common.XMLSheet4Import;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 *
 * Function:基于POI实现的Excel处理器：导入产品实现
 * Reason:
 * Date:2017/8/7
 * @author wusongti@163.com
 */
public abstract class POIExcelResolver4commonImport extends POIExcelResolver {
    protected  InputStream excelInputStream;

    @Override
    public ExcelImportResult readExcel() {
        super.definitionReader = getExcelDefinition();
        return  doReadExcel();
    }


    private ExcelImportResult doReadExcel() {
        ExcelImportResult excelImportResult = new ExcelImportResult();
        Workbook workbook = null;
        try {
            workbook = WorkbookFactory.create(excelInputStream);
        } catch (IOException e) {
            throw new EasyExcelException("创建工作薄失败",e);
        } catch (InvalidFormatException e) {
            throw new EasyExcelException("无效的格式",e);
        }catch (EncryptedDocumentException e){
            throw new EasyExcelException("创建工作薄失败",e);
        }

        Map<String, List<?>> listMap = new HashMap<>(5);

        XMLExcel4Import xmlExcel4Import = (XMLExcel4Import) super.definitionReader.registerConfigDefinitions();
        if(!xmlExcel4Import.isEmpty()){
            List<XMLSheet4Import> xmlSheet4ImportList = xmlExcel4Import.getXmlSheet4ImportList();
            int len = xmlSheet4ImportList.size();
            for (int i = 0; i < len; i++) {
                XMLSheet4Import xmlSheet4Import = xmlSheet4ImportList.get(i);
                if(!xmlSheet4Import.isEmpty()){
                    Sheet sheet = workbook.getSheetAt(xmlSheet4Import.getAttributeIndex());

                    List<Object> listBean = readRow(xmlSheet4Import.getXmlList4Import(), sheet);

                    listMap.put(xmlSheet4Import.getAttributeIndex().toString(),listBean);

                }
            }
            excelImportResult.setListMap(listMap);
        }
        return excelImportResult;
    }


    /**
     * 读取数据行入口
     * @param xmlList4Import
     * @param sheet
     * @param <T>
     * @return
     */
    protected <T> List<T> readRow(XMLList4Import xmlList4Import, Sheet sheet) {

        int startRow = xmlList4Import.getAttributeStartRow();

        int endRow = sheet.getLastRowNum();

        int totalRows = endRow - startRow + 1;

        List<T> listBean = new ArrayList<T>(totalRows);

        for (int i = startRow; i <= endRow; i++) {

            Row row = sheet.getRow(i);

            if(super.isRowEmpty(row)){
                throw new EasyExcelException("请删除所有空行");
            }

            Object bean = doReadRow(sheet.getSheetName(),xmlList4Import,row);

            listBean.add((T) bean);
        }
        return listBean;
    }

    /**
     * 读取具体行
     * @param xmlList4Import
     * @param row
     * @return
     */
    protected Object doReadRow(String sheetName, XMLList4Import xmlList4Import, Row row) {
        StringBuffer errorMsg = new StringBuffer();

        Class<?> clazz = null;
        try {
            clazz = Class.forName(xmlList4Import.getAttributeClass());
        } catch (ClassNotFoundException e) {
            throw new EasyExcelException("class 为 [" + xmlList4Import.getAttributeClass() + "] 类不存在 ",e);
        }

        //创建注册时配置的bean类型
        Object bean = ReflectUtil.newInstance(clazz);

        // 字段集合
        List<XMLField4Import> xmlField4ImportList = xmlList4Import.getElementFields();

        for(XMLField4Import xmlField4Import : xmlField4ImportList){
            Object cellValueObj = null;
            Cell cell = row.getCell(xmlField4Import.getAttributeIndex());
            if(cell != null){
                cellValueObj = getCellValue(cell);
            }

            try {
                //校验
                Object valueAfterValidate = validate(sheetName,xmlField4Import, cellValueObj, row.getRowNum());

                // 利用反射设置bean属性值
                ReflectUtil.setProperty(bean, xmlField4Import.getAttributeProperty(), valueAfterValidate);
            }catch (EasyExcelException e){
                errorMsg.append(e.getMessage()).append("\n");
            }
        }

        if(errorMsg != null && StringUtils.isNotBlank(errorMsg.toString())){
            ReflectUtil.setProperty(bean, "successFlag", false);
            ReflectUtil.setProperty(bean, "errorMessage", errorMsg);
        }else{
            ReflectUtil.setProperty(bean, "successFlag", true);
        }
        return bean;
    }

    /**
     * 对字段进行数据有效性校验，校验成功后返回java类型的值
     * @param xmlField4Import
     * @param value
     * @param rowNum
     * @return
     */
    private Object validate(String sheetName, XMLField4Import xmlField4Import, Object value, int rowNum) {
        if("true".equalsIgnoreCase(xmlField4Import.getAttributeRequired())){
            if(value == null || StringUtils.isBlank(value.toString())){
                throw new EasyExcelException(sheetName + "，第[" + (rowNum+1)+"]行第["+(xmlField4Import.getAttributeIndex() + 1) + "]列，该项是必填项");
            }
        }

        if(value != null && !StringUtils.isBlank(value.toString())) {

            // 优先使用正则表达式
            if(!Pattern.compile(xmlField4Import.getAttributeRegex()).matcher(value.toString()).find()){
                throw new EasyExcelException(sheetName + "，第[" + (rowNum + 1) + "]行第[" + (xmlField4Import.getAttributeIndex() + 1) + "]列，" + xmlField4Import.getAttributeRegexErrMsg());
            }

            if(EasyExcelEnum.Long.name().equalsIgnoreCase(xmlField4Import.getAttributeType())){
                try {
                    return Long.parseLong(value.toString());
                }catch (NumberFormatException e){
                    throw new EasyExcelException(sheetName + "，第[" + (rowNum + 1) + "]行第[" + (xmlField4Import.getAttributeIndex() + 1) + "]列，您应该输入Long类型的值，当前值[" + value +"]",e);
                }
            }else if(EasyExcelEnum.Double.name().equalsIgnoreCase(xmlField4Import.getAttributeType())){
                if(StringUtils.isNotBlank(value.toString().trim())){
                    return new BigDecimal(value.toString()).setScale(xmlField4Import.getAttributeScale(),xmlField4Import.getAttributeRoundingMode());
                }else{
                    new BigDecimal("0");
                }
            }else if(EasyExcelEnum.Integer.name().equalsIgnoreCase(xmlField4Import.getAttributeType())){
                try {
                    return Integer.parseInt(value.toString());
                }catch (NumberFormatException e){
                    throw new EasyExcelException(sheetName + "，第[" + (rowNum + 1) + "]行第[" + (xmlField4Import.getAttributeIndex() + 1) + "]列，您应该输入Int类型的值，当前值[" + value +"]",e);
                }
            }else if(EasyExcelEnum.Date.name().equalsIgnoreCase(xmlField4Import.getAttributeType())){
                String pattern = "yyyy-MM-dd";
                if((StringUtils.isNotBlank(xmlField4Import.getAttributePattern()))){
                    pattern = xmlField4Import.getAttributePattern();
                }

                if(!MyExcelUtils.isDate(value.toString())){
                    throw new EasyExcelException(sheetName + "，第[" + (rowNum + 1) + "]行第[" + (xmlField4Import.getAttributeIndex() + 1) + "]列，请输入正确的日期[" + xmlField4Import.getAttributePattern() + "]");
                }

                try {
                    return new SimpleDateFormat(pattern).parse(value.toString());
                } catch (ParseException e) {
                    throw new EasyExcelException(sheetName + "，第[" + (rowNum + 1) + "]行第[" + (xmlField4Import.getAttributeIndex() + 1) + "]列，该项格式必须为[" + xmlField4Import.getAttributePattern() + "]",e);
                }
            }else if(EasyExcelEnum.Map.name().equalsIgnoreCase(xmlField4Import.getAttributeType())){
                if((StringUtils.isNotBlank(xmlField4Import.getAttributeFormat()))){
                    String valueStr = "";
                    String[] formats = xmlField4Import.getAttributeFormat().split(";");
                    for (String format : formats) {
                        if(value.toString().trim().equals(format.split(":")[1])){
                            valueStr = format.split(":")[0];
                            break;
                        }
                    }
                    if(StringUtils.isBlank(valueStr)){
                        throw new EasyExcelException(sheetName + "，第[" + (rowNum + 1) + "]行第[" + (xmlField4Import.getAttributeIndex() + 1) + "]列，属性[" + value + "]在映射表["+xmlField4Import.getAttributeFormat()+"]找不到。");
                    }
                    return valueStr;
                }else {
                    throw new EasyExcelException(sheetName + "，第[" + (rowNum + 1) + "]行第[" + (xmlField4Import.getAttributeIndex() + 1) + "]列，当type=Map类型时，必须指定format属性，如format=yes:是;no:否。");
                }
            }else if(EasyExcelEnum.LookupItem.name().equalsIgnoreCase(xmlField4Import.getAttributeType())){
                String valueStr = "";
                String rootCode = "";
                if(StringUtils.isNotBlank(xmlField4Import.getAttributeRootCode())){
                    rootCode = xmlField4Import.getAttributeRootCode();
                }else{
                    throw new EasyExcelException(sheetName + "，第[" + (rowNum + 1) + "]行第[" + (xmlField4Import.getAttributeIndex() + 1) + "]列，当type=LookupItem类型时，必须指定lookupCode属性。");
                }

                valueStr = getLookupItemCodeByName(rootCode,value.toString());
                return valueStr;
            }else{ // 默认是String
                return value.toString();
            }
        }
        return value;
    }


    /**
     * 获取定义对象，工厂方法（将对象的创建延迟给子类）
     */
    protected abstract ExcelDefinitionReader getExcelDefinition();


    /**
     * 延迟给子类获取编码
     * @param rootCode
     * @param name
     * @return
     */
    protected abstract String getLookupItemCodeByName(String rootCode, String name);
}
