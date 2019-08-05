package com.wust.springcloud.admin.server.core.service.defaults;


import com.wust.springcloud.common.entity.sys.dataprivilege.SysDataPrivilege;
import com.wust.springcloud.common.service.BaseService;

import java.util.List;

/**
 * Created by WST on 2019/6/10.
 */
public interface SysDataPrivilegeService extends BaseService {
    void init(List<SysDataPrivilege> sysDataPrivilegeList);
}
