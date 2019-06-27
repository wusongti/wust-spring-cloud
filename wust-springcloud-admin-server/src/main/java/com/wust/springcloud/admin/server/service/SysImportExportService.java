package com.wust.springcloud.admin.server.service;

import com.wust.springcloud.common.entity.sys.importexport.SysImportExport;
import com.wust.springcloud.common.entity.sys.importexport.SysImportExportList;
import com.wust.springcloud.common.entity.sys.importexport.SysImportExportSearch;

import java.util.List;

/**
 * Created by WST on 2019/5/20.
 */
public interface SysImportExportService {
    List<SysImportExportList> listPage(SysImportExportSearch search);

    List<SysImportExportList> findByCondition(SysImportExportSearch search);

    int insert(SysImportExport entity);

    int update(SysImportExport entity);
}
