package com.wust.springcloud.admin.server.core.service;

import com.wust.springcloud.common.dto.ResponseDto;
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

    ResponseDto insert(SysDataSource entity);

    int update(SysDataSource entity);

    void cacheDataSource();
}
