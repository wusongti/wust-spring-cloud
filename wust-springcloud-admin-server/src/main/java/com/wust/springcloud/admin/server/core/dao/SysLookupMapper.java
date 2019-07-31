package com.wust.springcloud.admin.server.core.dao;

import com.wust.springcloud.common.dao.IBaseMapper;
import org.springframework.dao.DataAccessException;

/**
 * Created by WST on 2019/4/29.
 */
public interface SysLookupMapper extends IBaseMapper {
    int deleteAll() throws DataAccessException;
}
