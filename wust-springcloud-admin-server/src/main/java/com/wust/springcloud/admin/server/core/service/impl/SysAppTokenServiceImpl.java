package com.wust.springcloud.admin.server.core.service.impl;

import com.wust.springcloud.admin.server.core.dao.SysAppTokenMapper;
import com.wust.springcloud.admin.server.core.service.SysAppTokenService;
import com.wust.springcloud.common.entity.sys.apptoken.SysAppToken;
import com.wust.springcloud.common.entity.sys.apptoken.SysAppTokenList;
import com.wust.springcloud.common.entity.sys.apptoken.SysAppTokenSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by WST on 2019/4/18.
 */
@Service("sysAppTokenServiceImpl")
public class SysAppTokenServiceImpl implements SysAppTokenService {
    @Autowired
    private SysAppTokenMapper sysAppTokenMapper;

    @Override
    public List<SysAppTokenList> listPage(SysAppTokenSearch search) {
        return sysAppTokenMapper.listPage(search);
    }

    @Override
    public List<SysAppTokenList> findByCondition(SysAppTokenSearch search) {
        return sysAppTokenMapper.findByCondition(search);
    }

    @Override
    public int insert(SysAppToken entity) {
        return sysAppTokenMapper.insert(entity);
    }

    @Override
    public int update(SysAppToken entity) {
        return sysAppTokenMapper.update(entity);
    }

    @Override
    public int delete(String id) {
        return sysAppTokenMapper.delete(id);
    }
}
