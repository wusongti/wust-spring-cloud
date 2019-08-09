package com.wust.springcloud.admin.server.core.dao;

import com.wust.springcloud.common.dao.IBaseMapper;
import com.wust.springcloud.common.entity.sys.lookup.SysLookup;
import com.wust.springcloud.common.entity.sys.lookup.SysLookupList;
import com.wust.springcloud.common.entity.sys.lookup.SysLookupSearch;
import org.springframework.dao.DataAccessException;
import java.util.List;

/**
 * Created by WST on 2019/4/29.
 */
public interface SysLookupMapper extends IBaseMapper<SysLookup> {
    List<SysLookupList> listPage(SysLookupSearch search) throws DataAccessException;

    int deleteAll() throws DataAccessException;
}
