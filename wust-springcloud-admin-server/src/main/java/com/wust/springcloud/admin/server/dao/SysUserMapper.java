package com.wust.springcloud.admin.server.dao;

import com.wust.springcloud.common.entity.sys.user.SysUser;
import com.wust.springcloud.common.entity.sys.user.SysUserList;
import com.wust.springcloud.common.entity.sys.user.SysUserSearch;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * Created by WST on 2019/4/18.
 */
public interface SysUserMapper {
    List<SysUserList> listPage(SysUserSearch search) throws DataAccessException;

    List<SysUserList> findByCondition(SysUserSearch search) throws DataAccessException;

    String getDefaultLoginName() throws DataAccessException;

    int batchInsert(List<SysUser> entities) throws DataAccessException;

    int batchUpdate(List<SysUser> entities) throws DataAccessException;

    int batchDelete(List<String> keys) throws DataAccessException;
}
