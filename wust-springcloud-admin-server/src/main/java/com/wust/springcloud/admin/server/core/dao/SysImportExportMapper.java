package com.wust.springcloud.admin.server.core.dao;

import com.wust.springcloud.common.annotations.PrivilegeAnnotation;
import com.wust.springcloud.common.dao.IBaseMapper;
import com.wust.springcloud.common.entity.sys.importexport.SysImportExport;
import com.wust.springcloud.common.entity.sys.importexport.SysImportExportList;
import com.wust.springcloud.common.entity.sys.importexport.SysImportExportSearch;
import org.springframework.dao.DataAccessException;
import java.util.List;
import java.util.Map;

/**
 * Created by WST on 2019/5/20.
 */
public interface SysImportExportMapper  extends IBaseMapper<SysImportExport> {
    //@PrivilegeAnnotation(uuid = "a6d1c14b-8b47-11e9-a68d-0050568e63cd",businessName = "导入导出")
    List<SysImportExportList> listPage(SysImportExportSearch search) throws DataAccessException;

    List<SysImportExportList> findByCondition(SysImportExportSearch search) throws DataAccessException;

    List<Map<String, Object>> findBySql(Map<String,Object> parameters) throws DataAccessException;
}
