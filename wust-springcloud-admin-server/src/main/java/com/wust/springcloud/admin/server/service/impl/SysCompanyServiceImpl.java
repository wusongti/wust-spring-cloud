package com.wust.springcloud.admin.server.service.impl;

import com.wust.springcloud.admin.server.dao.SysCompanyMapper;
import com.wust.springcloud.admin.server.service.SysCompanyService;
import com.wust.springcloud.common.entity.sys.company.SysCompany;
import com.wust.springcloud.common.entity.sys.company.SysCompanyList;
import com.wust.springcloud.common.entity.sys.company.SysCompanySearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by WST on 2019/6/3.
 */
@Service("sysCompanyServiceImpl")
public class SysCompanyServiceImpl implements SysCompanyService {
    @Autowired
    private SysCompanyMapper sysCompanyMapper;

    @Override
    public List<SysCompanyList> listPage(SysCompanySearch search) {
        return sysCompanyMapper.listPage(search);
    }

    @Override
    public List<SysCompanyList> findByCondition(SysCompanySearch search) {
        return sysCompanyMapper.findByCondition(search);
    }

    @Override
    public int insert(SysCompany entity) {
        List<SysCompany> entities = new ArrayList<>(1);
        entities.add(entity);
        return sysCompanyMapper.batchInsert(entities);
    }

    @Override
    public int update(SysCompany entity) {
        List<SysCompany> entities = new ArrayList<>(1);
        entities.add(entity);
        return sysCompanyMapper.batchUpdate(entities);
    }

    @Override
    public int delete(String id) {
        List<String> keys = new ArrayList<>(1);
        keys.add(id);
        return sysCompanyMapper.batchDelete(keys);
    }
}
