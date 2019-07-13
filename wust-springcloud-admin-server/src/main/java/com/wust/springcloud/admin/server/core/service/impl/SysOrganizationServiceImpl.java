package com.wust.springcloud.admin.server.core.service.impl;

import com.wust.springcloud.admin.server.core.dao.SysOrganizationMapper;
import com.wust.springcloud.admin.server.core.dao.SysResourceMapper;
import com.wust.springcloud.admin.server.core.dao.SysRoleResourceMapper;
import com.wust.springcloud.admin.server.core.service.SysOrganizationService;
import com.wust.springcloud.common.dto.MessageMap;
import com.wust.springcloud.common.entity.sys.organization.SysOrganization;
import com.wust.springcloud.common.entity.sys.organization.SysOrganizationList;
import com.wust.springcloud.common.entity.sys.organization.SysOrganizationSearch;
import com.wust.springcloud.common.entity.sys.resource.SysResource;
import com.wust.springcloud.common.entity.sys.role.resource.SysRoleResource;
import com.wust.springcloud.common.entity.sys.role.resource.SysRoleResourceCreate;
import com.wust.springcloud.common.enums.ApplicationEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by WST on 2019/6/3.
 */
@Service("sysOrganizationServiceImpl")
public class SysOrganizationServiceImpl implements SysOrganizationService {
    @Autowired
    private SysOrganizationMapper sysOrganizationMapper;

    @Autowired
    private SysRoleResourceMapper sysRoleResourceMapper;

    @Autowired
    private SysResourceMapper sysResourceMapper;

    @Override
    public List<SysOrganizationList> listPage(SysOrganizationSearch search) {
        return sysOrganizationMapper.listPage(search);
    }

    @Override
    public List<SysOrganizationList> findByCondition(SysOrganizationSearch search) {
        return sysOrganizationMapper.findByCondition(search);
    }

    @Override
    public int batchInsert(List<SysOrganization> entities) {
        return sysOrganizationMapper.batchInsert(entities);
    }

    @Override
    public int batchDelete(List<String> keys) {
        return sysOrganizationMapper.batchDelete(keys);
    }


    @Override
    public MessageMap setFunctionPermissions(SysRoleResourceCreate sysRoleResourceCreate) {
        MessageMap mm = new MessageMap();

        List<SysRoleResource>  sysRoleResources = sysRoleResourceCreate.getSysRoleResources();
        List<SysRoleResource> list = new ArrayList<>();
        for(SysRoleResource sysRoleResource : sysRoleResources){
            sysRoleResource.setId(UUID.randomUUID().toString());
            sysRoleResource.setOrganizationId(sysRoleResourceCreate.getOrganizationId());
            list.add(sysRoleResource);

            // 将所勾选的菜单下面的所有私有白名单权限也自动授予角色
            if(ApplicationEnum.MENUT_TYPE_M.getStringValue().equalsIgnoreCase(sysRoleResource.getType())){
                List<SysResource> anonList = sysResourceMapper.findAnonResourcesByMenuId(sysRoleResource.getResourceId());
                if(!CollectionUtils.isEmpty(anonList)){
                    for(SysResource anonR : anonList){
                        SysRoleResource anon = new SysRoleResource();
                        anon.setId(UUID.randomUUID().toString());
                        anon.setOrganizationId(sysRoleResourceCreate.getOrganizationId());
                        anon.setResourceId(anonR.getId());
                        anon.setType(ApplicationEnum.MENUT_TYPE_R.getStringValue());
                        list.add(anon);
                    }
                }
            }
        }

        List<String> organizationIds = new ArrayList<>(1);
        organizationIds.add(sysRoleResourceCreate.getOrganizationId());
        sysRoleResourceMapper.batchDeleteByOrganizationIds(organizationIds);

        sysRoleResourceMapper.batchInsert(list);
        return mm;
    }
}
