package com.wust.springcloud.admin.server.core.dao;

import com.wust.springcloud.common.dao.IBaseMapper;
import com.wust.springcloud.common.entity.sys.apptoken.SysAppToken;
import com.wust.springcloud.common.entity.sys.apptoken.SysAppTokenList;
import com.wust.springcloud.common.entity.sys.apptoken.SysAppTokenSearch;
import org.springframework.dao.DataAccessException;
import java.util.List;

/**
 * Created by WST on 2019/4/18.
 */
public interface SysAppTokenMapper  extends IBaseMapper<SysAppToken> {
    List<SysAppTokenList> listPage(SysAppTokenSearch search) throws DataAccessException;

    List<SysAppTokenList> findByCondition(SysAppTokenSearch search) throws DataAccessException;
}
