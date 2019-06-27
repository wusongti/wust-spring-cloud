package com.wust.springcloud.admin.server.service;

import com.wust.springcloud.common.dto.MessageMap;
import com.wust.springcloud.common.entity.sys.datasource.SysDataSource;
import com.wust.springcloud.common.entity.sys.datasource.SysDataSourceList;
import com.wust.springcloud.common.entity.sys.datasource.SysDataSourceSearch;
import java.util.List;

/**
 * Created by WST on 2019/6/17.
 */
public interface SysDataSourceService {
    List<SysDataSourceList> listPage(SysDataSourceSearch search);

    List<SysDataSourceList> findByCondition(SysDataSourceSearch search);

    MessageMap insert(SysDataSource entity);

    int update(SysDataSource entity);

    void cacheDataSource();
}
