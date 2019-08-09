package com.wust.springcloud.admin.server.core.service.impl;


import com.wust.springcloud.admin.server.core.dao.SysUserMapper;
import com.wust.springcloud.admin.server.core.dao.SysUserOrganizationMapper;
import com.wust.springcloud.admin.server.core.service.SysUserOrganizationService;
import com.wust.springcloud.common.dao.IBaseMapper;
import com.wust.springcloud.common.entity.sys.user.SysUser;
import com.wust.springcloud.common.enums.DataDictionaryEnum;
import com.wust.springcloud.common.service.BaseServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Created by WST on 2019/4/18.
 */
@Service("sysUserOrganizationServiceImpl")
public class SysUserOrganizationServiceImpl extends BaseServiceImpl implements SysUserOrganizationService {
    @Autowired
    private SysUserOrganizationMapper sysUserOrganizationMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    protected IBaseMapper getBaseMapper() {
        return sysUserOrganizationMapper;
    }

    @Override
    public void init() {
        SysUser sysUser = new SysUser();
        List<SysUser> sysUserList = sysUserMapper.select(sysUser);
        if(CollectionUtils.isNotEmpty(sysUserList)){
            for (SysUser user : sysUserList) {
                String type = user.getType();
                if(DataDictionaryEnum.USER_TYPE_PLATFORM_ADMIN.getStringValue().equals(type)){

                }else if(DataDictionaryEnum.USER_TYPE_PLATFORM_USER.getStringValue().equals(type)){

                }else if(DataDictionaryEnum.USER_TYPE_BUSINESS_ADMIN.getStringValue().equals(type)){

                }else if(DataDictionaryEnum.USER_TYPE_PROJECT_USER.getStringValue().equals(type)){

                }
            }
        }
    }
}
