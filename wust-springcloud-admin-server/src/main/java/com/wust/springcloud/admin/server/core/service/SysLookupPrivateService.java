package com.wust.springcloud.admin.server.core.service;

import com.wust.springcloud.common.entity.sys.lookup.SysLookup;
import com.wust.springcloud.common.entity.sys.lookup.SysLookupList;
import com.wust.springcloud.common.entity.sys.lookup.SysLookupSearch;
import java.util.List;

/**
 * Created by WST on 2019/5/28.
 */
public interface SysLookupPrivateService {

    List<SysLookupList> listPage(SysLookupSearch search);

    List<SysLookupList> findByCondition(SysLookupSearch search);

    int insert(SysLookup entity);

    int copy(SysLookup entity);

    int update(SysLookup entity);

    int delete(String id);
}
