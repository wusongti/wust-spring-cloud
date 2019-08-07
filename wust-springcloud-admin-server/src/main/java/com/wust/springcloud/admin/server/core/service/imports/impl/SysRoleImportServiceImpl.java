package com.wust.springcloud.admin.server.core.service.imports.impl;

import com.alibaba.fastjson.JSONObject;
import com.wust.springcloud.admin.server.core.dao.SysRoleMapper;
import com.wust.springcloud.admin.server.core.service.imports.SysRoleImportService;
import com.wust.springcloud.common.context.DefaultBusinessContext;
import com.wust.springcloud.common.dto.ResponseDto;
import com.wust.springcloud.common.entity.sys.role.SysRole;
import com.wust.springcloud.common.entity.sys.role.SysRoleImport;
import com.wust.springcloud.common.entity.sys.role.SysRoleList;
import com.wust.springcloud.common.entity.sys.role.SysRoleSearch;
import com.wust.springcloud.common.util.CodeGenerator;
import com.wust.springcloud.common.util.MyStringUtils;
import com.wust.springcloud.common.util.cache.DataDictionaryUtil;
import com.wust.springcloud.easyexcel.definition.ExcelDefinitionReader;
import com.wust.springcloud.easyexcel.factory.DefinitionFactory;
import com.wust.springcloud.easyexcel.factory.xml.XMLDefinitionFactory4commonImport;
import com.wust.springcloud.easyexcel.resolver.poi.POIExcelResolver4commonImport;
import com.wust.springcloud.easyexcel.result.ExcelImportResult;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.ByteArrayInputStream;
import java.util.*;

/**
 * Created by WST on 2019/5/27.
 */
@Service("sysRoleImportServiceImpl")
public class SysRoleImportServiceImpl extends POIExcelResolver4commonImport implements SysRoleImportService {
    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    protected ExcelDefinitionReader getExcelDefinition() {
        String xmlFullPath = "importExport/import/xml/admin_role.xml";
        DefinitionFactory definitionReaderFactory = new XMLDefinitionFactory4commonImport(xmlFullPath);
        return definitionReaderFactory.createExcelDefinitionReader();
    }

    @Override
    protected String getLookupItemCodeByName(String rootCode, String name) {
        DefaultBusinessContext ctx = DefaultBusinessContext.getContext();
        return DataDictionaryUtil.getLookupCodeByRootCodeAndName(ctx.getLocale().toString(),rootCode,name);
    }


    @Transactional(rollbackFor=Exception.class)
    @Override
    public ResponseDto importByExcel(JSONObject jsonObject) {
        ResponseDto mm = new ResponseDto();

        DefaultBusinessContext ctx = jsonObject.getObject("ctx",DefaultBusinessContext.class);

        ExcelImportResult excelImportResult = null;

        try {
            byte[] fileBytes = jsonObject.getBytes("fileBytes");

            super.excelInputStream =  new ByteArrayInputStream(fileBytes);

            // 1.读取excel数据
            excelImportResult = super.readExcel();

            // 2.处理业务数据
            Map<String, List<?>> listMap = excelImportResult.getListMap();

            List<SysRoleImport> sysRoleImports = (List<SysRoleImport>)listMap.get("0"); // 获取第1个sheet里面的数据
            if(CollectionUtils.isNotEmpty(sysRoleImports)){
                int successCount = 0;
                int errorCount = 0;
                String errorMsg = "";

                Map resultMap = doImport(ctx,sysRoleImports);
                successCount = Integer.parseInt(resultMap.get("successCount")+"");
                errorCount = Integer.parseInt(resultMap.get("errorCount")+"");
                errorMsg = resultMap.get("errorMsg")+"";

                if(successCount == sysRoleImports.size()){
                    mm.setCode("100502");
                    errorMsg = "全部导入成功，共["+successCount+"]条记录" + errorMsg;
                }else if(errorCount == sysRoleImports.size()){
                    mm.setCode("100504");
                    errorMsg = "全部导入失败，共["+errorCount+"]条记录" + errorMsg;
                }else{
                    mm.setCode("100503");
                    errorMsg = "部分导入成功，共["+successCount+"]条记录导入成功，["+errorCount+"]条记录导入失败" + errorMsg;
                }
                mm.setMessage(errorMsg);
            }else{
                mm.setCode("100504");
                mm.setMessage("这是一个空Excel");
            }
        }catch (Exception e){
            mm.setCode("100504");
            if(MyStringUtils.isNotBlank(e.getMessage())){
                mm.setMessage(e.getMessage().substring(0,500));
            }else{
                mm.setMessage("导入失败:" + e.toString().substring(0,500));
            }
        }
        return mm;
    }


    private Map doImport(DefaultBusinessContext ctx,List<SysRoleImport> sysRoleImports){
        Map map = new HashMap();
        map.put("successCount",0);
        map.put("errorCount",0);
        map.put("errorMsg","");

        int successCount = 0;
        int errorCount = 0;
        // 错误信息
        StringBuffer errorMsg = new StringBuffer();

        List<SysRole> sysRolesNew = new ArrayList();
        List<SysRole> sysRolesOld = new ArrayList();
        for (SysRoleImport sysRoleImport : sysRoleImports) {
            if(sysRoleImport.getSuccessFlag()){
                SysRoleSearch sysRoleSearch = new SysRoleSearch();
                sysRoleSearch.setName(sysRoleImport.getName());
                List<SysRoleList> sysRoleLists = sysRoleMapper.findByCondition(sysRoleSearch);
                if(CollectionUtils.isNotEmpty(sysRoleLists)){
                    sysRoleImport.setModifyTime(new Date());
                    sysRolesOld.add(sysRoleImport);
                }else{
                    sysRoleImport.setCode(CodeGenerator.genRoleCode());
                    sysRoleImport.setStatus("100201");
                    sysRoleImport.setCreaterId(ctx.getUserId());
                    sysRoleImport.setCreaterName(ctx.getLoginName());
                    sysRoleImport.setCreateTime(new Date());
                    sysRolesNew.add(sysRoleImport);
                }
                successCount ++;
            }else{
                errorMsg.append(sysRoleImport.getErrorMessage()).append("\n");
                errorCount ++;
            }
        }

        map.put("successCount",successCount);
        map.put("errorCount",errorCount);
        map.put("errorMsg",errorMsg);

        // 新增
        if (CollectionUtils.isNotEmpty(sysRolesNew)) {
            sysRoleMapper.insertList(sysRolesNew);
        }

        // 修改
        if (CollectionUtils.isNotEmpty(sysRolesOld)) {
            sysRoleMapper.batchUpdate(sysRolesOld);
        }
        return map;
    }
}
