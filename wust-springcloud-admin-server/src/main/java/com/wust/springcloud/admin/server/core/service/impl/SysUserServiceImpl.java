package com.wust.springcloud.admin.server.core.service.impl;


import com.wust.springcloud.admin.server.core.dao.SysImportExportMapper;
import com.wust.springcloud.admin.server.core.dao.SysUserMapper;
import com.wust.springcloud.admin.server.core.service.SysUserImportService;
import com.wust.springcloud.admin.server.core.service.SysAttachmentService;
import com.wust.springcloud.admin.server.core.service.SysUserService;
import com.wust.springcloud.common.dao.IBaseMapper;
import com.wust.springcloud.common.entity.sys.user.SysUser;
import com.wust.springcloud.common.enums.ApplicationEnum;
import com.wust.springcloud.common.service.BaseServiceImpl;
import com.wust.springcloud.common.util.RC4;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by WST on 2019/4/18.
 */
@Service("sysUserServiceImpl")
public class SysUserServiceImpl extends BaseServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysImportExportMapper sysImportExportMapper;

    @Autowired
    private SysAttachmentService sysAttachmentServiceImpl;

    @Autowired
    private SysUserImportService sysUserImportServiceImpl;

    @Override
    protected IBaseMapper getBaseMapper() {
        return sysUserMapper;
    }


    @Override
    public int insert(SysUser entity) {
        String passwordRC4 = org.apache.commons.codec.binary.Base64.encodeBase64String(RC4.encry_RC4_string("123456", ApplicationEnum.RC4_LOGIN_PASSWORD.getStringValue()).getBytes());
        entity.setPassword(passwordRC4);
        entity.setCreateTime(new Date());
        return sysUserMapper.insert(entity);
    }
}
