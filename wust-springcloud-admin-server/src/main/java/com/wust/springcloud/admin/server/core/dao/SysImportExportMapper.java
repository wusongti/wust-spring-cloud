package com.wust.springcloud.admin.server.core.dao;

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
    List<SysImportExportList> listPage(SysImportExportSearch search) throws DataAccessException;

    List<Map<String, Object>> findBySql(Map<String,Object> parameters) throws DataAccessException;
}
