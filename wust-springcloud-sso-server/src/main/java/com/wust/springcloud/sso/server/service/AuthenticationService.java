package com.wust.springcloud.sso.server.service;

import com.wust.springcloud.common.entity.sys.apptoken.SysAppToken;
import com.wust.springcloud.common.entity.sys.apptoken.SysAppTokenList;
import com.wust.springcloud.common.entity.sys.apptoken.SysAppTokenSearch;
import com.wust.springcloud.common.entity.sys.menu.SysMenu;
import com.wust.springcloud.common.entity.sys.menu.SysMenuSearch;
import com.wust.springcloud.common.entity.sys.resource.SysResource;
import com.wust.springcloud.common.entity.sys.user.SysUserList;
import com.wust.springcloud.common.entity.sys.user.SysUserSearch;
import java.util.List;

/**
 * Created by WST on 2019/5/7.
 */
public interface AuthenticationService {
    /**
     * 查找指定条件的用户
     * @param sysUserSearch
     * @return
     */
    List<SysUserList> findByCondition(SysUserSearch sysUserSearch);

    /**
     * 查找指定条件的appToken用户
     * @param sysAppTokenSearch
     * @return
     */
    List<SysAppTokenList> findByCondition(SysAppTokenSearch sysAppTokenSearch);

    /**
     * 修改SysAppToken
     * @param sysAppToken
     * @return
     */
    int update(SysAppToken sysAppToken);

    List<SysMenu> findByCondition(SysMenuSearch sysMenuSearch);

    /**
     * 超级管理员查询
     * @return
     */
    List<SysMenu> findAllMenus4SystemAdmin();


    /**
     * 非超级管理员查询
     * @param userId
     * @return
     */
    List<SysMenu> findMenuByUserId(String userId);

    /**
     * 超级管理员：获取非白名单资源权限
     * @return
     */
    List<SysResource> findAllResources4systemAdmin();


    /**
     * 超级管理员：获取白名单资源权限
     * @return
     */
    List<SysResource> findAllAnonResources4systemAdmin();


    /**
     * 非超级管理员：获取非白名单资源权限
     * @param userId
     * @return
     */
    List<SysResource> findResourcesByUserId(String userId);


    /**
     * 非超级管理员：获取白名单资源权限
     * @param userId
     * @return
     */
    List<SysResource> findAnonResourcesByUserId(String userId);

    /**
     * 根据菜单获取其下面的资源集合
     * @param menuId
     * @return
     */
    List<SysResource> findAnonResourcesByMenuId(String menuId);

    /**
     * 获取公共白名单集合
     * @return
     */
    List<SysResource> findCommonAnonResources();
}
