package com.wust.springcloud.admin.server.core.service.imports.impl;

import com.wust.springcloud.admin.server.core.dao.SysAttachmentMapper;
import com.wust.springcloud.admin.server.core.dao.SysImportExportMapper;
import com.wust.springcloud.admin.server.core.dao.SysUserMapper;
import com.wust.springcloud.admin.server.core.service.imports.SysUserImportService;
import com.wust.springcloud.admin.server.core.service.defaults.SysAttachmentService;
import com.wust.springcloud.admin.server.core.task.ThreadPoolTask;
import com.wust.springcloud.common.context.DefaultBusinessContext;
import com.wust.springcloud.common.dto.ResponseDto;
import com.wust.springcloud.common.entity.sys.user.SysUser;
import com.wust.springcloud.common.entity.sys.user.SysUserImport;
import com.wust.springcloud.common.entity.sys.user.SysUserList;
import com.wust.springcloud.common.entity.sys.user.SysUserSearch;
import com.wust.springcloud.common.enums.ApplicationEnum;
import com.wust.springcloud.common.util.RC4;
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

import java.util.*;

/**
 * Created by WST on 2019/5/24.
 */
@Service("sysUserImportServiceImpl")
public class SysUserImportServiceImpl extends DefaultImportServiceImpl implements SysUserImportService {
    static Logger logger = LogManager.getLogger(SysUserImportServiceImpl.class);

    @Autowired
    private SysImportExportMapper sysImportExportMapper;

    @Autowired
    private ThreadPoolTask threadPoolTask;

    @Autowired
    private SysAttachmentMapper sysAttachmentMapper;

    @Autowired
    private SysAttachmentService sysAttachmentServiceImpl;

    @Autowired
    private SysUserMapper sysUserMapper;


    @Override
    protected ExcelDefinitionReader getExcelDefinition() {
        String xmlFullPath = "importExport/import/xml/admin_user.xml";
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
    private Map doImport(DefaultBusinessContext ctx,List<SysUserImport> sysUserImports){
        Map map = new HashMap();
        map.put("successCount",0);
        map.put("errorCount",0);
        map.put("errorMsg","");

        int commitSize = 1000;//默认每次提交数量
        int successCount = 0;
        int errorCount = 0;
        // 错误信息
        StringBuffer errorMsg = new StringBuffer();

        List<SysUser> sysUsersNew = new ArrayList();
        List<SysUser> sysUsersOld = new ArrayList();
        for (SysUserImport sysUserImport : sysUserImports) {
            if(sysUserImport.getSuccessFlag()){
                SysUserSearch sysUserSearch = new SysUserSearch();
                sysUserSearch.setLoginName(sysUserImport.getLoginName());
                List<SysUserList> sysUserLists = sysUserMapper.findByCondition(sysUserSearch);
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
            int size = sysUsersNew.size();
            if (size <= commitSize) {
                sysUserMapper.insertList(sysUsersNew);
            } else {
                int partSize = size / commitSize;
                for(int i = 0; i < partSize; i++) {
                    List<SysUser> subList = sysUsersNew.subList(0, commitSize);
                    sysUserMapper.insertList(subList);
                    logger.info("导入-->新增用户：分批提交" + commitSize + "条数据");

                    sysUsersNew.subList(0, commitSize).clear();
                    logger.info("导入-->新增用户：剔除已经提交的数据后还剩余" + sysUsersNew.size() + "条数据");
                }
                if(!sysUsersNew.isEmpty()){
                    sysUserMapper.insertList(sysUsersNew);
                    logger.info("导入-->新增用户：分批提交剩余" + sysUsersNew.size() + "条数据");
                }
            }
        }

        // 修改
        if (CollectionUtils.isNotEmpty(sysUsersOld)) {
            int size = sysUsersOld.size();
            if (size <= commitSize) {
                sysUserMapper.batchUpdate(sysUsersOld);
            } else {
                int partSize = size / commitSize;
                for(int i = 0; i < partSize; i++) {
                    List<SysUser> subList = sysUsersOld.subList(0, commitSize);
                    sysUserMapper.batchUpdate(subList);
                    logger.info("导入-->修改用户：分批提交" + commitSize + "条数据");

                    sysUsersOld.subList(0, commitSize).clear();
                    logger.info("导入-->修改用户：剔除已经提交的数据后还剩余" + sysUsersOld.size() + "条数据");
                }
                if(!sysUsersOld.isEmpty()){
                    sysUserMapper.batchUpdate(sysUsersOld);
                    logger.info("导入-->修改用户：分批提交剩余" + sysUsersOld.size() + "条数据");
                }
            }
        }
        return map;
    }
}
