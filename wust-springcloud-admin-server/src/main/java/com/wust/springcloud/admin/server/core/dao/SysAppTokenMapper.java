package com.wust.springcloud.admin.server.core.dao;

import com.wust.springcloud.common.entity.sys.apptoken.SysAppToken;
import com.wust.springcloud.common.entity.sys.apptoken.SysAppTokenList;
import com.wust.springcloud.common.entity.sys.apptoken.SysAppTokenSearch;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * Created by WST on 2019/4/18.
 */
public interface SysAppTokenMapper {
    List<SysAppTokenList> listPage(SysAppTokenSearch search) throws DataAccessException;

    List<SysAppTokenList> findByCondition(SysAppTokenSearch search) throws DataAccessException;

    int insert(SysAppToken entity) throws DataAccessException;

    int update(SysAppToken entity) throws DataAccessException;

    int delete(String id) throws DataAccessException;
}
