package com.wust.springcloud.sso.server.core.api.web;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wust.springcloud.common.dto.ResponseDto;
import com.wust.springcloud.common.dto.UserContextDto;
import com.wust.springcloud.common.entity.sys.menu.SysMenu;
import com.wust.springcloud.common.enums.ApplicationEnum;
import com.wust.springcloud.common.util.cache.SpringRedisTools;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author ：wust
 * @date ：Created in 2019/7/24 14:01
 * @description：
 * @version:
 */
@RequestMapping("/ResourcesController")
@RestController
public class ResourcesController {
    @Autowired
    private SpringRedisTools springRedisTools;



    @RequestMapping(value = "/loadSubMenuById/{loginName}/{menuId}",method = RequestMethod.POST)
    public ResponseDto loadSubMenuById(@PathVariable String loginName, @PathVariable String menuId) {
        ResponseDto messageMap = new ResponseDto();

        String key = String.format(ApplicationEnum.WEB_LOGIN_KEY.getStringValue(),loginName);
        Object obj = springRedisTools.getByKey(key);
        if(obj != null){
            UserContextDto userContextDto = JSONObject.parseObject(obj+"",UserContextDto.class);
            Map<String, List<SysMenu>> stringListMap = userContextDto.getGroupMenusByPid();
            List<SysMenu> sysMenus = stringListMap.get(menuId);
            if(CollectionUtils.isNotEmpty(sysMenus)){
                JSONArray jsonArray = new JSONArray(sysMenus.size());
                for (SysMenu menu : sysMenus) {
                    JSONObject menuJsonObject = new JSONObject();
                    menuJsonObject.put("id",menu.getId());
                    menuJsonObject.put("name",menu.getName());
                    menuJsonObject.put("description",menu.getDescription());
                    menuJsonObject.put("img",menu.getImg());
                    menuJsonObject.put("url",menu.getUrl());
                    menuJsonObject.put("level",menu.getLevel());
                    menuJsonObject.put("permission",menu.getPermission());
                    jsonArray.add(menuJsonObject);
                }
                messageMap.setObj(jsonArray);
            }
        }

        return messageMap;
    }
}
