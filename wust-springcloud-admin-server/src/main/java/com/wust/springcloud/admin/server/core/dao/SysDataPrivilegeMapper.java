package com.wust.springcloud.admin.server.core.dao;


import com.wust.springcloud.common.dao.BaseMapper;
import org.springframework.dao.DataAccessException;

/**
 * Created by WST on 2019/6/10.
 */
public interface SysDataPrivilegeMapper extends BaseMapper {
    int deleteAll() throws DataAccessException;
}
