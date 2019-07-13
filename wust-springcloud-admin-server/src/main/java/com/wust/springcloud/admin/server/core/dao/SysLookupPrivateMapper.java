package com.wust.springcloud.admin.server.core.dao;

import com.wust.springcloud.common.entity.sys.lookup.SysLookup;
import com.wust.springcloud.common.entity.sys.lookup.SysLookupList;
import com.wust.springcloud.common.entity.sys.lookup.SysLookupSearch;
import org.springframework.dao.DataAccessException;
import java.util.List;

/**
 * Created by WST on 2019/5/28.
 */
public interface SysLookupPrivateMapper {
    List<SysLookupList> listPage(SysLookupSearch search) throws DataAccessException;

    List<SysLookupList> findByCondition(SysLookupSearch search) throws DataAccessException;

    int batchInsert(List<SysLookup> entities) throws DataAccessException;

    int update(SysLookup entity) throws DataAccessException;

    int batchDelete(List<String> codes) throws DataAccessException;
}
