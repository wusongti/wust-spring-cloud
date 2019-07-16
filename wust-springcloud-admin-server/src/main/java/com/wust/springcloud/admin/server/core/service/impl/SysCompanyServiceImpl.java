package com.wust.springcloud.admin.server.core.service.impl;

import com.wust.springcloud.admin.server.core.dao.SysCompanyMapper;
import com.wust.springcloud.admin.server.core.service.SysCompanyService;
import com.wust.springcloud.common.context.DefaultBusinessContext;
import com.wust.springcloud.common.entity.sys.company.SysCompany;
import com.wust.springcloud.common.entity.sys.company.SysCompanyList;
import com.wust.springcloud.common.entity.sys.company.SysCompanySearch;
import com.wust.springcloud.common.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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

    @Autowired
    private MessageSource messageSource;

    @Override
    public List<SysCompanyList> listPage(SysCompanySearch search) {
        System.out.println("国际化测试"+messageSource.getMessage("admin.server.message1",null, DefaultBusinessContext.getContext().getLocale()));
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
