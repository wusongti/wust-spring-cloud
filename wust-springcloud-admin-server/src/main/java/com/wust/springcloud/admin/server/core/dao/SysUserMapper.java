package com.wust.springcloud.admin.server.core.dao;

import com.wust.springcloud.common.dao.IBaseMapper;
import com.wust.springcloud.common.entity.sys.user.SysUser;
import com.wust.springcloud.common.entity.sys.user.SysUserList;
import com.wust.springcloud.common.entity.sys.user.SysUserSearch;
import org.springframework.dao.DataAccessException;
import java.util.List;

/**
 * Created by WST on 2019/4/18.
 */
public interface SysUserMapper   extends IBaseMapper<SysUser> {
    List<SysUserList> listPage(SysUserSearch search) throws DataAccessException;

    List<SysUserList> findByCondition(SysUserSearch search) throws DataAccessException;

    String getDefaultLoginName() throws DataAccessException;
}
