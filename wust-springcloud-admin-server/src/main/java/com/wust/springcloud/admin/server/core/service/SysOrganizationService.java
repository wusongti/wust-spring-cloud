package com.wust.springcloud.admin.server.core.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wust.springcloud.common.dto.ResponseDto;
import com.wust.springcloud.common.entity.sys.organization.SysOrganizationSearch;
import com.wust.springcloud.common.service.BaseService;

/**
 * Created by WST on 2019/6/3.
 */
public interface SysOrganizationService extends BaseService {
    /**
     * 构建组织架构树
     * 根据当前登录用户类型，获取其对应的组织架构
     * @param search
     * @return
     */
    ResponseDto buildLeftTree(SysOrganizationSearch search);

    /**
     * 为角色设置功能权限
     * @param jsonObject
     * @return
     */
    ResponseDto setFunctionPermissions(JSONObject jsonObject);
}
