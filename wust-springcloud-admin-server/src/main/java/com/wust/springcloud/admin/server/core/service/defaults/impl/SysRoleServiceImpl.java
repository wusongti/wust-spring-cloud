package com.wust.springcloud.admin.server.core.service.defaults.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wust.springcloud.admin.server.core.dao.SysMenuMapper;
import com.wust.springcloud.admin.server.core.dao.SysResourceMapper;
import com.wust.springcloud.admin.server.core.dao.SysRoleMapper;
import com.wust.springcloud.admin.server.core.service.defaults.SysRoleService;
import com.wust.springcloud.common.dao.IBaseMapper;
import com.wust.springcloud.common.dto.MenuTreeDto;
import com.wust.springcloud.common.dto.ResourceTreeDto;
import com.wust.springcloud.common.dto.ResponseDto;
import com.wust.springcloud.common.enums.ApplicationEnum;
import com.wust.springcloud.common.service.BaseServiceImpl;
import com.wust.springcloud.common.util.MyStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import java.util.*;

/**
 * Created by WST on 2019/5/27.
 */
@Service("sysRoleServiceImpl")
public class SysRoleServiceImpl extends BaseServiceImpl implements SysRoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private SysResourceMapper sysResourceMapper;

    @Override
    protected IBaseMapper getBaseMapper() {
        return sysRoleMapper;
    }


    @Override
    public ResponseDto findFunctionTreeByOrganizationId(Long organizationId) {
        ResponseDto messageMap = new ResponseDto();
        if(MyStringUtils.isBlank(MyStringUtils.null2String(organizationId))){
            messageMap.setFlag(ResponseDto.INFOR_WARNING);
            messageMap.setMessage("请选择角色");
            return messageMap;
        }else{
            // 叶子菜单编码集合
            Set<String> leafNodeSet = new HashSet<>();

            JSONArray jsonArray = new JSONArray();
            List<MenuTreeDto> menuList =  sysMenuMapper.findByOrganizationId(organizationId);
            if(!CollectionUtils.isEmpty(menuList)){
                for(MenuTreeDto menu : menuList){
                    if("100702".equals(menu.getIsParent())){   // 叶子节点
                        leafNodeSet.add(menu.getCode());
                    }

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id",menu.getCode());
                    jsonObject.put("pId",menu.getPcode());
                    jsonObject.put("name",menu.getDescription());
                    jsonObject.put("type",ApplicationEnum.MENUT_TYPE_M.getStringValue());
                    jsonObject.put("checked",menu.getChecked());
                    if(MyStringUtils.isBlank(MyStringUtils.null2String(menu.getPcode()))){
                        jsonObject.put("open",true);
                    }
                    jsonArray.add(jsonObject);
                }
            }

            // 根据叶子菜单获取资源
            if(!CollectionUtils.isEmpty(leafNodeSet)){
                List<ResourceTreeDto> resourceListAll =  sysResourceMapper.findByOrganizationId(organizationId);
                if(!CollectionUtils.isEmpty(resourceListAll)){
                    Map<String,List<ResourceTreeDto>> resultMapAll = groupResourcesByMenuCode(resourceListAll);

                    for(String menuCode : leafNodeSet){
                        List<ResourceTreeDto> resourceList = resultMapAll.get(menuCode);
                        if(CollectionUtils.isEmpty(resourceList)){
                            continue;
                        }
                        for(ResourceTreeDto resource : resourceList){
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("code",resource.getCode());
                            jsonObject.put("pId",menuCode);
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





    /**
     * 根据菜单CODE分组资源
     * @param resourceList
     * @return
     */
    private Map<String,List<ResourceTreeDto>> groupResourcesByMenuCode(List<ResourceTreeDto> resourceList){
        Map<String,List<ResourceTreeDto>> resultMap = new HashMap<>();
        for(ResourceTreeDto rt : resourceList){
            if(resultMap.containsKey(rt.getMenuCode())){
                List<ResourceTreeDto> list = resultMap.get(rt.getMenuCode());
                list.add(rt);
                resultMap.put(rt.getMenuCode(), list);
            }else{
                List<ResourceTreeDto> resultList = new ArrayList<>();
                resultList.add(rt);
                resultMap.put(rt.getMenuCode(), resultList);
            }
        }
        return resultMap;
    }
}
