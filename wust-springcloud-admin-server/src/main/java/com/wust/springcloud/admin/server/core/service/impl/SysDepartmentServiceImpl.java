package com.wust.springcloud.admin.server.core.service.impl;

import com.wust.springcloud.admin.server.core.dao.SysDepartmentMapper;
import com.wust.springcloud.admin.server.core.service.SysDepartmentService;
import com.wust.springcloud.common.dao.IBaseMapper;
import com.wust.springcloud.common.service.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by WST on 2019/6/3.
 */
@Service("sysDepartmentServiceImpl")
public class SysDepartmentServiceImpl extends BaseServiceImpl implements SysDepartmentService {
    @Autowired
    private SysDepartmentMapper sysDepartmentMapper;

    @Override
    protected IBaseMapper getBaseMapper() {
        return sysDepartmentMapper;
    }

}
