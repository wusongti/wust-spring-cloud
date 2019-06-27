package com.wust.springcloud.admin.server.service.impl;

import com.wust.springcloud.admin.server.dao.SysImportExportMapper;
import com.wust.springcloud.admin.server.service.SysImportExportService;
import com.wust.springcloud.common.entity.sys.importexport.SysImportExport;
import com.wust.springcloud.common.entity.sys.importexport.SysImportExportList;
import com.wust.springcloud.common.entity.sys.importexport.SysImportExportSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by WST on 2019/5/20.
 */
@Service("sysImportExportServiceImpl")
public class SysImportExportServiceImpl implements SysImportExportService {
    @Autowired
    private SysImportExportMapper sysImportExportMapper;

    @Override
    public List<SysImportExportList> listPage(SysImportExportSearch search) {
        return sysImportExportMapper.listPage(search);
    }

    @Override
    public List<SysImportExportList> findByCondition(SysImportExportSearch search) {
        return sysImportExportMapper.findByCondition(search);
    }

    @Override
    public int insert(SysImportExport entity) {
        return sysImportExportMapper.insert(entity);
    }

    @Override
    public int update(SysImportExport entity) {
        return sysImportExportMapper.update(entity);
    }
}
