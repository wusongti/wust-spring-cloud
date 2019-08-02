package com.wust.springcloud.admin.server.core.dao;


import com.wust.springcloud.common.dao.IBaseMapper;
import com.wust.springcloud.common.entity.sys.dataprivilege.rules.SysDataPrivilegeRules;
import com.wust.springcloud.common.entity.sys.dataprivilege.rules.SysDataPrivilegeRulesList;
import com.wust.springcloud.common.entity.sys.dataprivilege.rules.SysDataPrivilegeRulesSearch;
import org.springframework.dao.DataAccessException;
import java.util.List;


/**
 * Created by WST on 2019/6/10.
 */
public interface SysDataPrivilegeRulesMapper  extends IBaseMapper<SysDataPrivilegeRules> {
    List<SysDataPrivilegeRulesList> listPage(SysDataPrivilegeRulesSearch search) throws DataAccessException;

    List<SysDataPrivilegeRulesList> findByCondition(SysDataPrivilegeRulesSearch search) throws DataAccessException;

    int deleteByDataPrivilegeId(Long dataPrivilegeId);
}
