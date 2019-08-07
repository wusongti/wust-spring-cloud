package com.wust.springcloud.admin.server.core.service;


import com.wust.springcloud.common.dto.ResponseDto;
import com.wust.springcloud.common.entity.sys.datasource.SysDataSource;
import com.wust.springcloud.common.service.BaseService;


/**
 * Created by WST on 2019/6/17.
 */
public interface SysDataSourceService extends BaseService {
    ResponseDto insert(SysDataSource entity);
    void cacheDataSource();
}
