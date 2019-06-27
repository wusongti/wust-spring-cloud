package com.wust.springcloud.admin.server.service.impl;


import com.wust.springcloud.admin.server.dao.SysMenuMapper;
import com.wust.springcloud.admin.server.dao.SysResourceMapper;
import com.wust.springcloud.admin.server.dao.SysRoleResourceMapper;
import com.wust.springcloud.admin.server.service.SysMenuService;
import com.wust.springcloud.common.dto.MessageMap;
import com.wust.springcloud.common.entity.sys.menu.SysMenu;
import com.wust.springcloud.common.entity.sys.resource.SysResource;
import com.wust.springcloud.common.entity.sys.role.resource.SysRoleResource;
import com.wust.springcloud.common.entity.sys.role.resource.SysRoleResourceList;
import com.wust.springcloud.common.enums.ApplicationEnum;
import com.wust.springcloud.common.util.cache.SpringRedisTools;
import com.wust.springcloud.common.xml.XMLAbstractResolver;
import com.wust.springcloud.common.xml.XMLDefinitionFactory;
import com.wust.springcloud.common.xml.factory.XMLPermissionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by WST on 2019/4/29.
 */
@Service("sysMenuServiceImpl")
public class SysMenuServiceImpl implements SysMenuService {
    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private SysResourceMapper sysResourceMapper;

    @Autowired
    private SysRoleResourceMapper sysRoleResourceMapper;

    @Autowired
    private SpringRedisTools springRedisTools;

    @Transactional(rollbackFor=DataAccessException.class)
    @Override
    public MessageMap init() {
        /**
         * 解析xml得到菜单资源配置
         */
        XMLDefinitionFactory xmlPermissionFactory = new XMLPermissionFactory();
        XMLAbstractResolver resolver = xmlPermissionFactory.createXMLResolver();
        Map<String,List> resultMap =  resolver.getResult();
        List<SysMenu> parseMenuList = resultMap.get("parseMenuList");
        List<SysResource> parseResourceList = resultMap.get("parseResourceList");


        /**
         * 清空所有菜单
         */
        sysMenuMapper.deleteAll();

        /**
         * 重新insert
         */
        if(!CollectionUtils.isEmpty(parseMenuList)){
            sysMenuMapper.batchInsert(parseMenuList);
        }


        /**
         * 清空已经分配给角色的脏菜单（因为xml可能删除了一些菜单）
         */
        sysRoleResourceMapper.deleteDirtyMenu();





        /**
         * 清空所有资源
         */
        sysResourceMapper.deleteAll();

        /**
         * 重新insert
         */
        if(!CollectionUtils.isEmpty(parseResourceList)){
            sysResourceMapper.batchInsert(parseResourceList);
        }


        /**
         * 清空已经分配给角色的脏资源（因为xml可能删除了一些资源）
         */
        sysRoleResourceMapper.deleteDirtyResource();






        /**
         * 重新为所有角色分配拥有菜单的白名单
         */
        List<SysRoleResourceList> sysRoleResourceLists = sysRoleResourceMapper.groupByOrganizationId(ApplicationEnum.MENUT_TYPE_M.getStringValue());
        if(!CollectionUtils.isEmpty(sysRoleResourceLists)){
            for (SysRoleResourceList sysRoleResourceList : sysRoleResourceLists) {
                String organizationId = sysRoleResourceList.getOrganizationId();
                String menuId = sysRoleResourceList.getResourceId();

                /**
                 * 找到该菜单下面的白名单资源
                 */
                List<String> annonResourceIdList = new ArrayList<>(20);
                List<SysRoleResource> anonList = new ArrayList<>(20);
                List<SysResource> sysResources4anon = sysResourceMapper.findAnonResourcesByMenuId(menuId);
                if(!CollectionUtils.isEmpty(sysResources4anon)){
                    for (SysResource sysResource : sysResources4anon) {
                        SysRoleResource anon = new SysRoleResource();
                        anon.setId(UUID.randomUUID().toString());
                        anon.setOrganizationId(organizationId);
                        anon.setResourceId(sysResource.getId());
                        anon.setType(ApplicationEnum.MENUT_TYPE_R.getStringValue());
                        anonList.add(anon);
                        annonResourceIdList.add(sysResource.getId());
                    }

                    sysRoleResourceMapper.batchDelete(annonResourceIdList);
                    sysRoleResourceMapper.batchInsert(anonList);
                }
            }
        }
        return null;
    }
}
