package com.wust.springcloud.admin.server.core.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wust.springcloud.admin.server.core.dao.SysMenuMapper;
import com.wust.springcloud.admin.server.core.dao.SysResourceMapper;
import com.wust.springcloud.admin.server.core.dao.SysRoleMapper;
import com.wust.springcloud.admin.server.core.service.SysRoleService;
import com.wust.springcloud.common.dto.MenuTreeDto;
import com.wust.springcloud.common.dto.MessageMap;
import com.wust.springcloud.common.dto.ResourceTreeDto;
import com.wust.springcloud.common.entity.sys.role.SysRole;
import com.wust.springcloud.common.entity.sys.role.SysRoleList;
import com.wust.springcloud.common.entity.sys.role.SysRoleSearch;
import com.wust.springcloud.common.enums.ApplicationEnum;
import com.wust.springcloud.common.util.MyStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import java.util.*;

/**
 * Created by WST on 2019/5/27.
 */
@Service("sysRoleServiceImpl")
public class SysRoleServiceImpl implements SysRoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private SysResourceMapper sysResourceMapper;

    @Override
    public List<SysRoleList> listPage(SysRoleSearch search) {
        return sysRoleMapper.listPage(search);
    }

    @Override
    public List<SysRoleList> findByCondition(SysRoleSearch search) {
        return sysRoleMapper.findByCondition(search);
    }

    @Override
    public MessageMap findFunctionTreeByOrganizationId(String organizationId) {
        MessageMap messageMap = new MessageMap();
        if(MyStringUtils.isBlank(MyStringUtils.null2String(organizationId))){
            messageMap.setFlag(MessageMap.INFOR_WARNING);
            messageMap.setMessage("请选择角色");
            return messageMap;
        }else{
            // 叶子菜单编码集合
            Set<String> leafNodeSet = new HashSet<>();

            JSONArray jsonArray = new JSONArray();
            List<MenuTreeDto> menuList =  sysMenuMapper.findByOrganizationId(organizationId);
            if(!CollectionUtils.isEmpty(menuList)){
                for(MenuTreeDto menu : menuList){
                    if(menu.getLevel() != null && menu.getLevel() == -1){   // 叶子节点
                        leafNodeSet.add(menu.getId());
                    }

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id",menu.getId());
                    jsonObject.put("pId",menu.getpId());
                    jsonObject.put("name",menu.getDescription());
                    jsonObject.put("type",ApplicationEnum.MENUT_TYPE_M.getStringValue());
                    jsonObject.put("checked",menu.getChecked());
                    if(MyStringUtils.isBlank(MyStringUtils.null2String(menu.getpId()))){
                        jsonObject.put("open",true);
                    }
                    jsonArray.add(jsonObject);
                }
            }

            // 根据叶子菜单获取资源
            if(!CollectionUtils.isEmpty(leafNodeSet)){
                List<ResourceTreeDto> resourceListAll =  sysResourceMapper.findByOrganizationId(organizationId);
                if(!CollectionUtils.isEmpty(resourceListAll)){
                    Map<String,List<ResourceTreeDto>> resultMapAll = groupResourcesByMenuId(resourceListAll);

                    for(String menuId : leafNodeSet){
                        List<ResourceTreeDto> resourceList = resultMapAll.get(menuId);
                        if(CollectionUtils.isEmpty(resourceList)){
                            continue;
                        }
                        for(ResourceTreeDto resource : resourceList){
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("id",resource.getId());
                            jsonObject.put("pId",menuId);
                            jsonObject.put("name",resource.getDescription());
                            jsonObject.put("type",ApplicationEnum.MENUT_TYPE_R.getStringValue());
                            jsonObject.put("checked",resource.getChecked());
                            jsonArray.add(jsonObject);
                        }
                    }
                }
            }
            messageMap.setObj(jsonArray.toJSONString());
        }

        return messageMap;
    }

    @Override
    public int insert(SysRole entity) {
        List<SysRole> entities = new ArrayList<>(1);
        entities.add(entity);
        return sysRoleMapper.batchInsert(entities);
    }

    @Override
    public int update(SysRole entity) {
        List<SysRole> entities = new ArrayList<>(1);
        entities.add(entity);
        return sysRoleMapper.batchUpdate(entities);
    }

    @Transactional(rollbackFor=Exception.class)
    @Override
    public int batchDelete(List<String> ids) {
        return sysRoleMapper.batchDelete(ids);
    }


    /**
     * 根据菜单ID分组资源
     * @param resourceList
     * @return
     */
    private Map<String,List<ResourceTreeDto>> groupResourcesByMenuId(List<ResourceTreeDto> resourceList){
        Map<String,List<ResourceTreeDto>> resultMap = new HashMap<>();
        for(ResourceTreeDto rt : resourceList){
            if(resultMap.containsKey(rt.getMenuId())){
                List<ResourceTreeDto> list = resultMap.get(rt.getMenuId());
                list.add(rt);
                resultMap.put(rt.getMenuId(), list);
            }else{
                List<ResourceTreeDto> resultList = new ArrayList<>();
                resultList.add(rt);
                resultMap.put(rt.getMenuId(), resultList);
            }
        }
        return resultMap;
    }
}
