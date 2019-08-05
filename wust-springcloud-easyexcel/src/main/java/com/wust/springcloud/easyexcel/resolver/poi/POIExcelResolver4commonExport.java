package com.wust.springcloud.easyexcel.resolver.poi;/**
 * Created by wust on 2017/8/7.
 */

import com.wust.springcloud.easyexcel.ExcelParameters;
import com.wust.springcloud.easyexcel.EasyExcelEnum;
import com.wust.springcloud.easyexcel.definition.ExcelDefinitionReader;
import com.wust.springcloud.easyexcel.exception.EasyExcelException;
import com.wust.springcloud.easyexcel.result.ExcelExportResult;
import com.wust.springcloud.easyexcel.util.MyExcelUtils;
import com.wust.springcloud.easyexcel.util.SqlProcessor;
import com.wust.springcloud.easyexcel.xmlobject.common.XMLExcel4Export;
import com.wust.springcloud.easyexcel.xmlobject.common.XMLField4Export;
import com.wust.springcloud.easyexcel.xmlobject.common.XMLList4Export;
import com.wust.springcloud.easyexcel.xmlobject.common.XMLSheet4Export;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.util.CollectionUtils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * Function:基于POI实现的Excel处理器：普通导出产品实现
 * Reason:
 * Date:2017/8/7
 * @author wusongti@163.com
 */
public abstract class POIExcelResolver4commonExport extends POIExcelResolver {
    protected ExcelParameters excelParameters = new ExcelParameters();;

    @Override
    public ExcelExportResult createWorkbook() {
        super.definitionReader = getExcelDefinition();
        if(definitionReader == null){
            throw new EasyExcelException("读取到的XML定义为空");
        }
        return doCreateWorkbook();
    }


    /**
     * 创建工作薄
     * @return
     */
    private ExcelExportResult doCreateWorkbook() throws EasyExcelException {
        ExcelExportResult excelExportResult = new ExcelExportResult();

        Workbook workbook = new HSSFWorkbook();

        XMLExcel4Export xmlExcel4Export = (XMLExcel4Export) super.definitionReader.registerConfigDefinitions();
        if(xmlExcel4Export.isEmpty()){
            throw new EasyExcelException("创建工作薄失败，没有读取到配置信息");
        }

        List<XMLSheet4Export> xmlSheetList = xmlExcel4Export.getXmlSheet4ExportList();

        // 遍历sheet
        for (int i = 0; i < xmlSheetList.size(); i++) {
            XMLSheet4Export xmlSheet = xmlSheetList.get(i);

            Sheet sheet = workbook.createSheet();
            sheet.setDefaultColumnWidth(15);
            workbook.setSheetName(i, xmlSheet.getAttributeLabel());

            int rowIndex = 0;

            XMLList4Export xmlList = xmlSheet.getXmlList4Export();
            if (xmlList == null) {
                continue;
            }


            /**
             * 设置列表标题
             */
            Row rowListTitle = sheet.createRow(rowIndex++);

            List<XMLField4Export> xmlFieldList = xmlList.getElementFields();

            if (CollectionUtils.isEmpty(xmlFieldList)) {
                throw new EasyExcelException("没有配置列表字段");
            }

            int columnTitleIndex = 0;

            for (XMLField4Export xmlField : xmlFieldList) {

                Cell cellTitle = rowListTitle.createCell(columnTitleIndex++);

                setHeadCellStyle(workbook, cellTitle, IndexedColors.GREEN.getIndex(), Font.COLOR_NORMAL, CellStyle.ALIGN_CENTER);

                cellTitle.setCellValue(MyExcelUtils.null2String(xmlField.getAttributeLabel()));

            }


            /**
             * 设置列表数据
             */
            String sqlStr = xmlList.getElementSql().getSqlText();

            Map<String, Object> parseParameters = excelParameters.getParameters();

            String newSql = null;
            try {
                newSql = SqlProcessor.parseSQL(xmlList.getElementSql().getAttributeId(), sqlStr, parseParameters);
            } catch (Exception e) {
                throw  new EasyExcelException("解析SQL出问题了："+e);
            }

            parseParameters.put("sql", newSql);

            // 根据SQL查询数据
            List<Map<String, Object>> mapList = findBySql(parseParameters);
            if (CollectionUtils.isEmpty(mapList)) {
                continue;
            }

            for (Map<String, Object> stringObjectMap : mapList) {

                Row rowData = sheet.createRow(rowIndex++);
                int columnDataIndex = 0;

                // 遍历数据
                for (XMLField4Export xmlField : xmlFieldList) {

                    Cell cell = rowData.createCell(columnDataIndex++);
                    /**
                     * 格式化数据
                     */
                    if (EasyExcelEnum.Double.name().equalsIgnoreCase(xmlField.getAttributeType())) {

                        String format = xmlField.getAttributeFormat();

                        DecimalFormat df = new DecimalFormat(format);

                        if (stringObjectMap.get(xmlField.getAttributeColumn()) != null) {

                            cell.setCellValue(df.format(stringObjectMap.get(xmlField.getAttributeColumn())));

                        } else {

                            cell.setCellValue("");

                        }
                    } else if (EasyExcelEnum.Float.name().equalsIgnoreCase(xmlField.getAttributeType())) {

                        String format = xmlField.getAttributeFormat();

                        DecimalFormat df = new DecimalFormat(format);

                        if (stringObjectMap.get(xmlField.getAttributeColumn()) != null) {

                            cell.setCellValue(df.format(stringObjectMap.get(xmlField.getAttributeColumn())));

                        } else {

                            cell.setCellValue("");

                        }
                    } else if (EasyExcelEnum.Date.name().equalsIgnoreCase(xmlField.getAttributeType())) {
                        String format = xmlField.getAttributeFormat();
                        if (stringObjectMap.get(xmlField.getAttributeColumn()) != null) {

                            Date d = null;
                            try {
                                d = new SimpleDateFormat(format).parse(stringObjectMap.get(xmlField.getAttributeColumn()).toString());
                            } catch (ParseException e) {
                               throw new EasyExcelException("日期格式转换异常",e);
                            }
                            String dateStr = new SimpleDateFormat(format).format(d);
                            cell.setCellValue(dateStr);
                        } else {
                            cell.setCellValue("");
                        }
                    }else if(EasyExcelEnum.LookupItem.name().equalsIgnoreCase(xmlField.getAttributeType())){
                        String label = "";
                        String itemCode = stringObjectMap.get(xmlField.getAttributeColumn()) == null ? "" : stringObjectMap.get(xmlField.getAttributeColumn()).toString();
                        label = getLookupItemNameByCode(itemCode);
                        cell.setCellValue(label);
                    } else {
                        // 默认是String
                        cell.setCellValue(MyExcelUtils.null2String(stringObjectMap.get(xmlField.getAttributeColumn())));
                    }
                }
            }
        }
        excelExportResult.setWorkbook(workbook);
        return excelExportResult;
    }


    private void setHeadCellStyle(Workbook wb, Cell headCell,short fillForegroundColor,short textColor,short textAlign) {
        Font font = wb.createFont();// 创建字体样式
        font.setFontName("Verdana");
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        font.setColor(textColor);

        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyle.setFillForegroundColor(fillForegroundColor);
        cellStyle.setBorderBottom(CellStyle.BORDER_THIN); // 底部边框
        cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex()); // 底部边框颜色
        cellStyle.setBorderLeft(CellStyle.BORDER_THIN); // 左边边框
        cellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex()); // 左边边框颜色
        cellStyle.setBorderRight(CellStyle.BORDER_THIN); // 右边边框
        cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex()); // 右边边框颜色
        cellStyle.setBorderTop(CellStyle.BORDER_THIN); // 上边边框
        cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex()); // 上边边框颜色
        cellStyle.setAlignment(textAlign);

        headCell.setCellStyle(cellStyle);
        headCell.setCellType(HSSFCell.CELL_TYPE_STRING);
    }

    /**
     * 获取定义对象，工厂方法（将对象的创建延迟给子类）
     */
    protected abstract ExcelDefinitionReader getExcelDefinition();

    /**
     * 根据sql和参数去数据库获取要导出的记录
     * @param parseParameters
     * @return
     */
    protected abstract List<Map<String, Object>> findBySql(Map<String, Object> parseParameters);



    protected abstract String getLookupItemNameByCode(String code);
}
