package com.wust.springcloud.admin.server.core.service.impl;

import com.wust.springcloud.admin.server.core.dao.SysRoleMapper;
import com.wust.springcloud.admin.server.core.service.SysRoleImportService;
import com.wust.springcloud.common.context.DefaultBusinessContext;
import com.wust.springcloud.common.dto.ResponseDto;
import com.wust.springcloud.common.entity.sys.role.SysRole;
import com.wust.springcloud.common.entity.sys.role.SysRoleImport;
import com.wust.springcloud.common.entity.sys.role.SysRoleList;
import com.wust.springcloud.common.entity.sys.role.SysRoleSearch;
import com.wust.springcloud.common.util.CodeGenerator;
import com.wust.springcloud.easyexcel.definition.ExcelDefinitionReader;
import com.wust.springcloud.easyexcel.factory.DefinitionFactory;
import com.wust.springcloud.easyexcel.factory.xml.XMLDefinitionFactory4commonImport;
import com.wust.springcloud.easyexcel.result.ExcelImportResult;
import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by WST on 2019/5/27.
 */
@Service("sysRoleImportServiceImpl")
public class SysRoleImportServiceImpl extends DefaultImportServiceImpl implements SysRoleImportService {
    static Logger logger = LogManager.getLogger(SysRoleImportServiceImpl.class);

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    protected ExcelDefinitionReader getExcelDefinition() {
        String xmlFullPath = "importExport/import/xml/admin_role.xml";
        DefinitionFactory definitionReaderFactory = new XMLDefinitionFactory4commonImport(xmlFullPath);
        return definitionReaderFactory.createExcelDefinitionReader();
    }

    @Transactional(rollbackFor=Exception.class)
    @Override
    public ResponseDto importByExcelCallback(DefaultBusinessContext ctx, String batchNo) {
        ResponseDto mm = new ResponseDto();
        ExcelImportResult excelImportResult = null;
        try {

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
                    mm.setObj("100502");
                    errorMsg = "全部导入成功，共["+successCount+"]条记录" + errorMsg;
                }else if(errorCount == sysRoleImports.size()){
                    mm.setObj("100504");
                    errorMsg = "全部导入失败，共["+errorCount+"]条记录" + errorMsg;
                }else{
                    mm.setObj("100503");
                    errorMsg = "部分导入成功，共["+successCount+"]条记录导入成功，["+errorCount+"]条记录导入失败" + errorMsg;
                }
                mm.setMessage(errorMsg);
            }else{
                mm.setFlag(ResponseDto.INFOR_WARNING);
                mm.setMessage("这是一个空Excel");
            }
        }catch (Exception e){
            mm.setFlag(ResponseDto.INFOR_ERROR);
            mm.setMessage(e.getMessage());
        }
        return mm;
    }


    // TODO 解决事务
    private Map doImport(DefaultBusinessContext ctx,List<SysRoleImport> sysRoleImports){
        Map map = new HashMap();
        map.put("successCount",0);
        map.put("errorCount",0);
        map.put("errorMsg","");

        int commitSize = 1000;//默认每次提交数量
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
                    sysRolesOld.add(sysRoleImport);
                }else{
                    sysRoleImport.setCode(CodeGenerator.genRoleCode());
                    sysRoleImport.setCreaterId(ctx.getUserId());
                    sysRoleImport.setCreaterName(ctx.getLoginName());
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
            int size = sysRolesNew.size();
            if (size <= commitSize) {
                sysRoleMapper.batchInsert(sysRolesNew);
            } else {
                int partSize = size / commitSize;
                for(int i = 0; i < partSize; i++) {
                    List<SysRole> subList = sysRolesNew.subList(0, commitSize);
                    sysRoleMapper.batchInsert(subList);
                    logger.info("导入-->新增角色：分批提交" + commitSize + "条数据");

                    sysRolesNew.subList(0, commitSize).clear();
                    logger.info("导入-->新增角色：剔除已经提交的数据后还剩余" + sysRolesNew.size() + "条数据");
                }
                if(!sysRolesNew.isEmpty()){
                    sysRoleMapper.batchInsert(sysRolesNew);
                    logger.info("导入-->新增角色：分批提交剩余" + sysRolesNew.size() + "条数据");
                }
            }
        }

        // 修改
        if (CollectionUtils.isNotEmpty(sysRolesOld)) {
            int size = sysRolesOld.size();
            if (size <= commitSize) {
                sysRoleMapper.batchUpdate(sysRolesOld);
            } else {
                int partSize = size / commitSize;
                for(int i = 0; i < partSize; i++) {
                    List<SysRole> subList = sysRolesOld.subList(0, commitSize);
                    sysRoleMapper.batchUpdate(subList);
                    logger.info("导入-->修改角色：分批提交" + commitSize + "条数据");

                    sysRolesOld.subList(0, commitSize).clear();
                    logger.info("导入-->修改角色：剔除已经提交的数据后还剩余" + sysRolesOld.size() + "条数据");
                }
                if(!sysRolesOld.isEmpty()){
                    sysRoleMapper.batchUpdate(sysRolesOld);
                    logger.info("导入-->修改角色：分批提交剩余" + sysRolesOld.size() + "条数据");
                }
            }
        }
        return map;
    }
}
