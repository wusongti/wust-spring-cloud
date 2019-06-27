package com.wust.springcloud.admin.server.dao;


import com.wust.springcloud.common.entity.sys.organization.SysOrganization;
import com.wust.springcloud.common.entity.sys.organization.SysOrganizationList;
import com.wust.springcloud.common.entity.sys.organization.SysOrganizationSearch;
import org.springframework.dao.DataAccessException;
import java.util.List;

/**
 * Created by WST on 2019/6/3.
 */
public interface SysOrganizationMapper {
    List<SysOrganizationList> listPage(SysOrganizationSearch search) throws DataAccessException;

    List<SysOrganizationList> findByCondition(SysOrganizationSearch search) throws DataAccessException;

    int batchInsert(List<SysOrganization> entities) throws DataAccessException;

    int batchDelete(List<String> keys) throws DataAccessException;
}
