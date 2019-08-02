package com.wust.springcloud.sso.server.core.service.impl;

import com.wust.springcloud.common.dao.IBaseMapper;
import com.wust.springcloud.common.entity.sys.menu.SysMenu;
import com.wust.springcloud.common.service.BaseServiceImpl;
import com.wust.springcloud.sso.server.core.dao.SysMenuMapper;
import com.wust.springcloud.sso.server.core.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：wust
 * @date ：Created in 2019/8/2 11:08
 * @description：
 * @version:
 */
@Service("sysMenuServiceImpl")
public class SysMenuServiceImpl extends BaseServiceImpl implements SysMenuService {
    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public List<SysMenu> findAllMenus4SystemAdmin() {
        return sysMenuMapper.findAllMenus4SystemAdmin();
    }

    @Override
    public List<SysMenu> findMenuByUserId(Long userId) {
        return sysMenuMapper.findMenuByUserId(userId);
    }

    @Override
    protected IBaseMapper getBaseMapper() {
        return sysMenuMapper;
    }
}
