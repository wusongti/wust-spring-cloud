package com.wust.springcloud.admin.server.core.service.defaults;



import com.wust.springcloud.common.entity.sys.user.SysUser;
import com.wust.springcloud.common.service.BaseService;


/**
 * Created by WST on 2019/4/18.
 */
public interface SysUserService extends BaseService {
    int insert(SysUser entity);
}
