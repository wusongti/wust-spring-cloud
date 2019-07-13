package com.wust.springcloud.sso.server.core.service.impl;

import com.wust.springcloud.common.entity.sys.apptoken.SysAppToken;
import com.wust.springcloud.common.entity.sys.apptoken.SysAppTokenList;
import com.wust.springcloud.common.entity.sys.apptoken.SysAppTokenSearch;
import com.wust.springcloud.common.entity.sys.menu.SysMenu;
import com.wust.springcloud.common.entity.sys.menu.SysMenuSearch;
import com.wust.springcloud.common.entity.sys.resource.SysResource;
import com.wust.springcloud.common.entity.sys.user.SysUserList;
import com.wust.springcloud.common.entity.sys.user.SysUserSearch;
import com.wust.springcloud.sso.server.core.service.AuthenticationService;
import com.wust.springcloud.sso.server.core.dao.SysAppTokenMapper;
import com.wust.springcloud.sso.server.core.dao.SysMenuMapper;
import com.wust.springcloud.sso.server.core.dao.SysResourceMapper;
import com.wust.springcloud.sso.server.core.dao.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Created by WST on 2019/5/7.
 */
@Service("authenticationServiceImpl")
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysAppTokenMapper sysAppTokenMapper;

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private SysResourceMapper sysResourceMapper;

    @Override
    public List<SysUserList> findByCondition(SysUserSearch sysUserSearch) {
        return sysUserMapper.findByCondition(sysUserSearch);
    }

    @Override
    public List<SysAppTokenList> findByCondition(SysAppTokenSearch sysAppTokenSearch) {
        return sysAppTokenMapper.findByCondition(sysAppTokenSearch);
    }

    @Override
    public int update(SysAppToken sysAppToken) {
        return sysAppTokenMapper.update(sysAppToken);
    }

    @Override
    public List<SysMenu> findByCondition(SysMenuSearch sysMenuSearch) {
        return sysMenuMapper.findByCondition(sysMenuSearch);
    }

    @Override
    public List<SysMenu> findAllMenus4SystemAdmin() {
        return sysMenuMapper.findAllMenus4SystemAdmin();
    }

    @Override
    public List<SysMenu> findMenuByUserId(String userId) {
        return sysMenuMapper.findMenuByUserId(userId);
    }

    @Override
    public List<SysResource> findAllResources4systemAdmin() {
        return sysResourceMapper.findAllResources4systemAdmin();
    }

    @Override
    public List<SysResource> findAllAnonResources4systemAdmin() {
        return sysResourceMapper.findAllAnonResources4systemAdmin();
    }

    @Override
    public List<SysResource> findResourcesByUserId(String userId) {
        return sysResourceMapper.findResourcesByUserId(userId);
    }

    @Override
    public List<SysResource> findAnonResourcesByUserId(String userId) {
        return sysResourceMapper.findAnonResourcesByUserId(userId);
    }

    @Override
    public List<SysResource> findAnonResourcesByMenuId(String menuId) {
        return sysResourceMapper.findAnonResourcesByMenuId(menuId);
    }

    @Override
    public List<SysResource> findCommonAnonResources() {
        return sysResourceMapper.findCommonAnonResources();
    }
}
