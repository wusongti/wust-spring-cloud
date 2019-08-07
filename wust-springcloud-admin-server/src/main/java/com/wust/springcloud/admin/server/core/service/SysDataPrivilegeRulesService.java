package com.wust.springcloud.admin.server.core.service;

import com.wust.springcloud.common.dto.ResponseDto;
import com.wust.springcloud.common.service.BaseService;

/**
 * Created by WST on 2019/6/11.
 */
public interface SysDataPrivilegeRulesService extends BaseService {
    ResponseDto update(Long dataPrivilegeId, String types);
}
