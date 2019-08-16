package com.wust.springcloud.autotask.server.core.service.impl;


import com.wust.springcloud.autotask.server.core.dao.SysImportExportMapper;
import com.wust.springcloud.autotask.server.core.service.SysImportExportService;
import com.wust.springcloud.common.dao.IBaseMapper;
import com.wust.springcloud.common.service.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by WST on 2019/5/20.
 */
@Service("sysImportExportServiceImpl")
public class SysImportExportServiceImpl extends BaseServiceImpl implements SysImportExportService {
    @Autowired
    private SysImportExportMapper sysImportExportMapper;

    @Override
    protected IBaseMapper getBaseMapper() {
        return sysImportExportMapper;
    }
}
