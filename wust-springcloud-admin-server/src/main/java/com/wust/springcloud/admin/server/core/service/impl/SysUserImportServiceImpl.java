package com.wust.springcloud.admin.server.core.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.wust.springcloud.admin.server.core.dao.SysUserMapper;
import com.wust.springcloud.admin.server.core.service.SysUserImportService;
import com.wust.springcloud.common.context.DefaultBusinessContext;
import com.wust.springcloud.common.dto.ResponseDto;
import com.wust.springcloud.common.entity.sys.user.SysUser;
import com.wust.springcloud.common.entity.sys.user.SysUserImport;
import com.wust.springcloud.common.entity.sys.user.SysUserList;
import com.wust.springcloud.common.entity.sys.user.SysUserSearch;
import com.wust.springcloud.common.enums.ApplicationEnum;
import com.wust.springcloud.common.util.MyStringUtils;
import com.wust.springcloud.common.util.RC4;
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
 * Created by WST on 2019/5/24.
 */
@Service("sysUserImportServiceImpl")
public class SysUserImportServiceImpl extends POIExcelResolver4commonImport implements SysUserImportService {


    @Autowired
    private SysUserMapper sysUserMapper;


    @Override
    protected ExcelDefinitionReader getExcelDefinition() {
        String xmlFullPath = "easyexcel/import/xml/admin_user.xml";
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

            List<SysUserImport> sysUserImports = (List<SysUserImport>)listMap.get("0"); // 获取第1个sheet里面的数据
            if(CollectionUtils.isNotEmpty(sysUserImports)){
                int successCount = 0;
                int errorCount = 0;
                String errorMsg = "";

                Map resultMap = doImport(ctx,sysUserImports);
                successCount = Integer.parseInt(resultMap.get("successCount")+"");
                errorCount = Integer.parseInt(resultMap.get("errorCount")+"");
                errorMsg = resultMap.get("errorMsg")+"";

                if(successCount == sysUserImports.size()){
                    mm.setObj("100502");
                    errorMsg = "全部导入成功，共["+successCount+"]条记录" + errorMsg;
                }else if(errorCount == sysUserImports.size()){
                    mm.setObj("100504");
                    errorMsg = "全部导入失败，共["+errorCount+"]条记录" + errorMsg;
                }else{
                    mm.setObj("100503");
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


    private Map doImport(DefaultBusinessContext ctx,List<SysUserImport> sysUserImports){
        Map map = new HashMap();
        map.put("successCount",0);
        map.put("errorCount",0);
        map.put("errorMsg","");

        int successCount = 0;
        int errorCount = 0;
        // 错误信息
        StringBuffer errorMsg = new StringBuffer();

        List<SysUser> sysUsersNew = new ArrayList();
        List<SysUser> sysUsersOld = new ArrayList();
        for (SysUserImport sysUserImport : sysUserImports) {
            if(sysUserImport.getSuccessFlag()){
                SysUser sysUserSearch = new SysUser();
                sysUserSearch.setLoginName(sysUserImport.getLoginName());
                List<SysUser> sysUserLists = sysUserMapper.select(sysUserSearch);
                if(CollectionUtils.isNotEmpty(sysUserLists)){
                    sysUserImport.setModifyTime(new Date());
                    sysUsersOld.add(sysUserImport);
                }else{
                    String passwordRC4 = org.apache.commons.codec.binary.Base64.encodeBase64String(RC4.encry_RC4_string("123456", ApplicationEnum.RC4_LOGIN_PASSWORD.getStringValue()).getBytes());
                    sysUserImport.setPassword(passwordRC4);
                    sysUserImport.setCreaterId(ctx.getUserId());
                    sysUserImport.setCreaterName(ctx.getLoginName());
                    sysUserImport.setCreateTime(new Date());
                    sysUsersNew.add(sysUserImport);
                }
                successCount ++;
            }else{
                errorMsg.append(sysUserImport.getErrorMessage()).append("\n");
                errorCount ++;
            }
        }

        map.put("successCount",successCount);
        map.put("errorCount",errorCount);
        map.put("errorMsg",errorMsg);

        // 新增
        if (CollectionUtils.isNotEmpty(sysUsersNew)) {
            sysUserMapper.insertList(sysUsersNew);
        }

        // 修改
        if (CollectionUtils.isNotEmpty(sysUsersOld)) {
            sysUserMapper.batchUpdate(sysUsersOld);
        }
        return map;
    }
}
