package com.wust.springcloud.admin.server.core.service;

import com.wust.springcloud.common.dto.ResponseDto;
import com.wust.springcloud.common.entity.sys.dataprivilege.rules.SysDataPrivilegeRulesList;
import com.wust.springcloud.common.entity.sys.dataprivilege.rules.SysDataPrivilegeRulesSearch;
import java.util.List;

/**
 * Created by WST on 2019/6/11.
 */
public interface SysDataPrivilegeRulesService {
    List<SysDataPrivilegeRulesList> listPage(SysDataPrivilegeRulesSearch search);

    List<SysDataPrivilegeRulesList> findByCondition(SysDataPrivilegeRulesSearch search);

    ResponseDto update(String dataPrivilegeId, String types);
}
