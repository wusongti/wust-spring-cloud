package com.wust.springcloud.admin.server.core.service;


import com.alibaba.fastjson.JSONObject;
import com.wust.springcloud.common.service.BaseService;

/**
 * Created by WST on 2019/8/9.
 */
public interface SysUserOrganizationService extends BaseService {
    /**
     * 初始化用户组织关系
     */
    void init(JSONObject jsonObject);
}
