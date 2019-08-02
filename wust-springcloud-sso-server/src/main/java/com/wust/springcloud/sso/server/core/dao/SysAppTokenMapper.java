package com.wust.springcloud.sso.server.core.dao;

import com.wust.springcloud.common.dao.IBaseMapper;
import com.wust.springcloud.common.entity.sys.apptoken.SysAppToken;
import com.wust.springcloud.common.entity.sys.apptoken.SysAppTokenList;
import com.wust.springcloud.common.entity.sys.apptoken.SysAppTokenSearch;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;
import java.util.List;

/**
 * Created by WST on 2019/4/18.
 */
@Mapper
public interface SysAppTokenMapper extends IBaseMapper<SysAppToken> {
    List<SysAppTokenList> findByCondition(SysAppTokenSearch sysAppTokenSearch) throws DataAccessException;
}
