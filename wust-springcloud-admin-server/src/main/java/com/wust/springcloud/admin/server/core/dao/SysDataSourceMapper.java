package com.wust.springcloud.admin.server.core.dao;

import com.wust.springcloud.common.entity.sys.datasource.SysDataSource;
import com.wust.springcloud.common.entity.sys.datasource.SysDataSourceList;
import com.wust.springcloud.common.entity.sys.datasource.SysDataSourceSearch;
import org.springframework.dao.DataAccessException;
import java.util.List;
import java.util.Map;

/**
 * Created by WST on 2019/6/17.
 */
public interface SysDataSourceMapper {
    List<SysDataSourceList> listPage(SysDataSourceSearch search) throws DataAccessException;

    List<SysDataSourceList> findByCondition(SysDataSourceSearch search) throws DataAccessException;

    int batchInsert(List<SysDataSource> entities) throws DataAccessException;

    int batchUpdate(List<SysDataSource> entities) throws DataAccessException;

    int batchDelete(List<String> keys) throws DataAccessException;

    int createDataBase(Map parameters) throws DataAccessException;

    int createDataBaseUser(Map parameters) throws DataAccessException;

    int executeSQL(Map parameters) throws DataAccessException;

    int rollbackDataBase(Map parameters) throws DataAccessException;
}
