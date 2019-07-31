package com.wust.springcloud.admin.server.core.dao;

import com.wust.springcloud.common.dao.BaseMapper;
import org.springframework.dao.DataAccessException;

/**
 * Created by WST on 2019/4/18.
 */
public interface SysUserMapper   extends BaseMapper {

    String getDefaultLoginName() throws DataAccessException;
}
