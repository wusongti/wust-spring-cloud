package com.wust.springcloud.admin.server.core.service;

import com.wust.springcloud.common.entity.sys.company.SysCompany;
import com.wust.springcloud.common.entity.sys.company.SysCompanyList;
import com.wust.springcloud.common.entity.sys.company.SysCompanySearch;
import java.util.List;

/**
 * Created by WST on 2019/6/3.
 */
public interface SysCompanyService {
    List<SysCompanyList> listPage(SysCompanySearch search);

    List<SysCompanyList> findByCondition(SysCompanySearch search);

    int insert(SysCompany entity);

    int update(SysCompany entity);

    int delete(String id);
}
