package com.wust.springcloud.admin.server.core.service;

import com.wust.springcloud.common.dto.ResponseDto;
import com.wust.springcloud.common.service.BaseService;

/**
 * Created by WST on 2019/5/27.
 */
public interface SysRoleService extends BaseService {
    ResponseDto findFunctionTreeByOrganizationId(Long organizationId);
}
