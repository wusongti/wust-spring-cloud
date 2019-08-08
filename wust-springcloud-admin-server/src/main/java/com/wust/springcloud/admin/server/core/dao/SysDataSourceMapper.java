package com.wust.springcloud.admin.server.core.dao;

import com.wust.springcloud.common.dao.IBaseMapper;
import com.wust.springcloud.common.entity.sys.datasource.SysDataSource;
import org.springframework.dao.DataAccessException;
import java.util.Map;

/**
 * Created by WST on 2019/6/17.
 */
public interface SysDataSourceMapper  extends IBaseMapper<SysDataSource> {

    int createDataBase(Map parameters) throws DataAccessException;

    int createDataBaseUser(Map parameters) throws DataAccessException;

    int executeSQL(Map parameters) throws DataAccessException;

    int rollbackDataBase(Map parameters) throws DataAccessException;
}
