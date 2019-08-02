package com.wust.springcloud.sso.server.core.service;

import com.wust.springcloud.common.entity.sys.menu.SysMenu;
import com.wust.springcloud.common.service.BaseService;

import java.util.List;

public interface SysMenuService extends BaseService {
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
    List<SysMenu> findMenuByUserId(Long userId);
}
