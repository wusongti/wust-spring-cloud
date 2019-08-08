package com.wust.springcloud.sso.server.core.service;

import com.wust.springcloud.common.entity.sys.resource.SysResource;
import com.wust.springcloud.common.service.BaseService;
import java.util.List;

public interface SysResourceService extends BaseService {
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
    List<SysResource> findResourcesByUserId(Long userId);


    /**
     * 非超级管理员：获取白名单资源权限
     * @param userId
     * @return
     */
    List<SysResource> findAnonResourcesByUserId(Long userId);
}
