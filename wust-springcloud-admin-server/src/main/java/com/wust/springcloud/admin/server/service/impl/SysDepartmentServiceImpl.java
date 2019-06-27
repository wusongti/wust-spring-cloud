package com.wust.springcloud.admin.server.service.impl;

import com.wust.springcloud.admin.server.dao.SysDepartmentMapper;
import com.wust.springcloud.admin.server.service.SysDepartmentService;
import com.wust.springcloud.common.entity.sys.department.SysDepartment;
import com.wust.springcloud.common.entity.sys.department.SysDepartmentList;
import com.wust.springcloud.common.entity.sys.department.SysDepartmentSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by WST on 2019/6/3.
 */
@Service("sysDepartmentServiceImpl")
public class SysDepartmentServiceImpl implements SysDepartmentService {
    @Autowired
    private SysDepartmentMapper sysDepartmentMapper;


    @Override
    public List<SysDepartmentList> listPage(SysDepartmentSearch search) {
        return sysDepartmentMapper.listPage(search);
    }

    @Override
    public List<SysDepartmentList> findByCondition(SysDepartmentSearch search) {
        return sysDepartmentMapper.findByCondition(search);
    }

    @Override
    public int insert(SysDepartment entity) {
        List<SysDepartment> entities = new ArrayList<>(1);
        entities.add(entity);
        return sysDepartmentMapper.batchInsert(entities);
    }

    @Override
    public int update(SysDepartment entity) {
        List<SysDepartment> entities = new ArrayList<>(1);
        entities.add(entity);
        return sysDepartmentMapper.batchUpdate(entities);
    }

    @Override
    public int delete(String id) {
        List<String> keys = new ArrayList<>(1);
        keys.add(id);
        return sysDepartmentMapper.batchDelete(keys);
    }
}
