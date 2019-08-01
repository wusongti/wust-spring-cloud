package com.wust.springcloud.admin.server.core.dao;

import com.wust.springcloud.common.dao.IBaseMapper;
import com.wust.springcloud.common.entity.sys.company.SysCompany;
import com.wust.springcloud.common.entity.sys.company.SysCompanyList;
import com.wust.springcloud.common.entity.sys.company.SysCompanySearch;
import org.springframework.dao.DataAccessException;
import java.util.List;


/**
 * Created by WST on 2019/6/3.
 */
public interface SysCompanyMapper extends IBaseMapper<SysCompany> {
    List<SysCompanyList> listPage(SysCompanySearch search) throws DataAccessException;

    List<SysCompanyList> findByCondition(SysCompanySearch search) throws DataAccessException;
}
