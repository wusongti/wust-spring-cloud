package com.wust.springcloud.sso.server.core.dao;


import com.wust.springcloud.common.dao.IBaseMapper;
import com.wust.springcloud.common.entity.sys.user.SysUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by WST on 2019/4/18.
 */
@Mapper
public interface SysUserMapper extends IBaseMapper<SysUser> {
}
