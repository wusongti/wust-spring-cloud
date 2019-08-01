package com.wust.springcloud.admin.server.core.service.defaults;

import com.wust.springcloud.common.dto.ResponseDto;
import com.wust.springcloud.common.service.BaseService;

/**
 * Created by WST on 2019/6/11.
 */
public interface SysDataPrivilegeRulesService extends BaseService {
    ResponseDto update(String dataPrivilegeId, String types);
}
