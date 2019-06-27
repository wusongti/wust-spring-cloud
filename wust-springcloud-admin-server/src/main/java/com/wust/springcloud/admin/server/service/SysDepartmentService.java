package com.wust.springcloud.admin.server.service;

import com.wust.springcloud.common.entity.sys.company.SysCompany;
import com.wust.springcloud.common.entity.sys.company.SysCompanyList;
import com.wust.springcloud.common.entity.sys.company.SysCompanySearch;
import com.wust.springcloud.common.entity.sys.department.SysDepartment;
import com.wust.springcloud.common.entity.sys.department.SysDepartmentList;
import com.wust.springcloud.common.entity.sys.department.SysDepartmentSearch;

import java.util.List;

/**
 * Created by WST on 2019/6/3.
 */
public interface SysDepartmentService {
    List<SysDepartmentList> listPage(SysDepartmentSearch search);

    List<SysDepartmentList> findByCondition(SysDepartmentSearch search);

    int insert(SysDepartment entity);

    int update(SysDepartment entity);

    int delete(String id);
}
