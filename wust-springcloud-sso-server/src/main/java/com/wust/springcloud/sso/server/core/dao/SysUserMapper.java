package com.wust.springcloud.sso.server.core.dao;


import com.wust.springcloud.common.dao.IBaseMapper;
import com.wust.springcloud.common.entity.sys.user.SysUser;
import com.wust.springcloud.common.entity.sys.user.SysUserList;
import com.wust.springcloud.common.entity.sys.user.SysUserSearch;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * Created by WST on 2019/4/18.
 */
@Mapper
public interface SysUserMapper extends IBaseMapper<SysUser> {
    List<SysUserList> findByCondition(SysUserSearch sysUserSearch);
}
