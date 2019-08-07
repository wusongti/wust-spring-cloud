package com.wust.springcloud.admin.server.core.service.impl;

import com.wust.springcloud.admin.server.core.dao.SysOrganizationMapper;
import com.wust.springcloud.admin.server.core.dao.SysResourceMapper;
import com.wust.springcloud.admin.server.core.dao.SysRoleResourceMapper;
import com.wust.springcloud.admin.server.core.service.SysOrganizationService;
import com.wust.springcloud.common.dao.IBaseMapper;
import com.wust.springcloud.common.dto.ResponseDto;
import com.wust.springcloud.common.entity.sys.resource.SysResource;
import com.wust.springcloud.common.entity.sys.role.resource.SysRoleResource;
import com.wust.springcloud.common.entity.sys.role.resource.SysRoleResourceCreate;
import com.wust.springcloud.common.enums.ApplicationEnum;
import com.wust.springcloud.common.service.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by WST on 2019/6/3.
 */
@Service("sysOrganizationServiceImpl")
public class SysOrganizationServiceImpl extends BaseServiceImpl implements SysOrganizationService {
    @Autowired
    private SysOrganizationMapper sysOrganizationMapper;

    @Autowired
    private SysRoleResourceMapper sysRoleResourceMapper;

    @Autowired
    private SysResourceMapper sysResourceMapper;

    @Override
    protected IBaseMapper getBaseMapper() {
        return sysOrganizationMapper;
    }



    @Override
    public ResponseDto setFunctionPermissions(SysRoleResourceCreate sysRoleResourceCreate) {
        ResponseDto mm = new ResponseDto();

        List<SysRoleResource>  sysRoleResources = sysRoleResourceCreate.getSysRoleResources();
        List<SysRoleResource> list = new ArrayList<>();
        for(SysRoleResource sysRoleResource : sysRoleResources){
            sysRoleResource.setOrganizationId(sysRoleResourceCreate.getOrganizationId());
            sysRoleResource.setCreateTime(new Date());
            list.add(sysRoleResource);

            // 将所勾选的菜单下面的所有私有白名单权限也自动授予角色
            if(ApplicationEnum.MENUT_TYPE_M.getStringValue().equalsIgnoreCase(sysRoleResource.getType())){
                List<SysResource> anonList = sysResourceMapper.findAnonResourcesByMenuId(sysRoleResource.getResourceId());
                if(!CollectionUtils.isEmpty(anonList)){
                    for(SysResource anonR : anonList){
                        SysRoleResource anon = new SysRoleResource();
                        anon.setOrganizationId(sysRoleResourceCreate.getOrganizationId());
                        anon.setResourceId(anonR.getCode());
                        anon.setType(ApplicationEnum.MENUT_TYPE_R.getStringValue());
                        anon.setCreateTime(new Date());
                        list.add(anon);
                    }
                }
            }
        }

        sysRoleResourceMapper.deleteByPrimaryKey(sysRoleResourceCreate.getOrganizationId());

        sysRoleResourceMapper.insertList(list);
        return mm;
    }
}
