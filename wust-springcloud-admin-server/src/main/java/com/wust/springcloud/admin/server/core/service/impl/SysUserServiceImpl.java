package com.wust.springcloud.admin.server.core.service.impl;


import com.wust.springcloud.admin.server.core.dao.SysImportExportMapper;
import com.wust.springcloud.admin.server.core.dao.SysUserMapper;
import com.wust.springcloud.admin.server.core.service.SysUserImportService;
import com.wust.springcloud.admin.server.core.service.SysAttachmentService;
import com.wust.springcloud.admin.server.core.service.SysUserService;
import com.wust.springcloud.common.entity.sys.user.SysUser;
import com.wust.springcloud.common.entity.sys.user.SysUserList;
import com.wust.springcloud.common.entity.sys.user.SysUserSearch;
import com.wust.springcloud.common.enums.ApplicationEnum;
import com.wust.springcloud.common.util.RC4;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by WST on 2019/4/18.
 */
@Service("sysUserServiceImpl")
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysImportExportMapper sysImportExportMapper;

    @Autowired
    private SysAttachmentService sysAttachmentServiceImpl;

    @Autowired
    private SysUserImportService sysUserImportServiceImpl;

    @Override
    public List<SysUserList> listPage(SysUserSearch search) {
        return sysUserMapper.listPage(search);
    }

    @Override
    public List<SysUserList> findByCondition(SysUserSearch search) {
        return sysUserMapper.findByCondition(search);
    }

    @Override
    public int insert(SysUser entity) {
        String passwordRC4 = org.apache.commons.codec.binary.Base64.encodeBase64String(RC4.encry_RC4_string("123456", ApplicationEnum.RC4_LOGIN_PASSWORD.getStringValue()).getBytes());
        entity.setPassword(passwordRC4);
        List<SysUser> entities = new ArrayList<>(1);
        entities.add(entity);
        return sysUserMapper.batchInsert(entities);
    }

    @Override
    public int update(SysUser entity) {
        List<SysUser> entities = new ArrayList<>(1);
        entities.add(entity);
        return sysUserMapper.batchUpdate(entities);
    }

    @Override
    public int delete(String id) {
        List<String> keys = new ArrayList<>(1);
        keys.add(id);
        return sysUserMapper.batchDelete(keys);
    }
}
