package com.wust.springcloud.admin.server.service;

import com.wust.springcloud.common.dto.MessageMap;
import com.wust.springcloud.common.entity.sys.dataprivilege.rules.SysDataPrivilegeRulesList;
import com.wust.springcloud.common.entity.sys.dataprivilege.rules.SysDataPrivilegeRulesSearch;
import java.util.List;

/**
 * Created by WST on 2019/6/11.
 */
public interface SysDataPrivilegeRulesService {
    List<SysDataPrivilegeRulesList> listPage(SysDataPrivilegeRulesSearch search);

    List<SysDataPrivilegeRulesList> findByCondition(SysDataPrivilegeRulesSearch search);

    MessageMap update(String dataPrivilegeId, String types);
}
