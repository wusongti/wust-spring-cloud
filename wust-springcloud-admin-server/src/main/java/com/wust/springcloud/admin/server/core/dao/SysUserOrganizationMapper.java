package com.wust.springcloud.admin.server.core.dao;


import com.wust.springcloud.common.dao.IBaseMapper;
import com.wust.springcloud.common.entity.sys.userorganization.SysUserOrganization;

public interface SysUserOrganizationMapper extends IBaseMapper<SysUserOrganization> {
    int deleteAll();
}