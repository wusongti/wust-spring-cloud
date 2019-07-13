package com.wust.springcloud.admin.server.core.service;

import com.wust.springcloud.common.entity.sys.apptoken.SysAppToken;
import com.wust.springcloud.common.entity.sys.apptoken.SysAppTokenList;
import com.wust.springcloud.common.entity.sys.apptoken.SysAppTokenSearch;

import java.util.List;

/**
 * Created by WST on 2019/4/18.
 */
public interface SysAppTokenService {
    List<SysAppTokenList> listPage(SysAppTokenSearch search);

    List<SysAppTokenList> findByCondition(SysAppTokenSearch search);

    int insert(SysAppToken entity);

    int update(SysAppToken entity);

    int delete(String id);
}
