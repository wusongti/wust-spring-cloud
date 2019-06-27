package com.wust.springcloud.admin.server.service;


import com.wust.springcloud.common.entity.sys.user.SysUser;
import com.wust.springcloud.common.entity.sys.user.SysUserList;
import com.wust.springcloud.common.entity.sys.user.SysUserSearch;
import java.util.List;

/**
 * Created by WST on 2019/4/18.
 */
public interface SysUserService {
    List<SysUserList> listPage(SysUserSearch search);

    List<SysUserList> findByCondition(SysUserSearch search);

    int insert(SysUser entity);

    int update(SysUser entity);

    int delete(String id);
}
