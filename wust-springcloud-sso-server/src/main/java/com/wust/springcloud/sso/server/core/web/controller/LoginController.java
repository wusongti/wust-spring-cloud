package com.wust.springcloud.sso.server.core.web.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wust.springcloud.common.annotations.OperationLogAnnotation;
import com.wust.springcloud.common.context.DefaultBusinessContext;
import com.wust.springcloud.common.dto.ResponseDto;
import com.wust.springcloud.common.dto.UserContextDto;
import com.wust.springcloud.common.entity.sys.menu.SysMenu;
import com.wust.springcloud.common.entity.sys.resource.SysResource;
import com.wust.springcloud.common.entity.sys.user.SysUserList;
import com.wust.springcloud.common.entity.sys.user.SysUserSearch;
import com.wust.springcloud.common.enums.ApplicationEnum;
import com.wust.springcloud.common.enums.OperationLogEnum;
import com.wust.springcloud.common.util.JwtHelper;
import com.wust.springcloud.common.util.MyStringUtils;
import com.wust.springcloud.common.util.RC4;
import com.wust.springcloud.common.util.cache.SpringRedisTools;
import com.wust.springcloud.sso.server.core.service.SysMenuService;
import com.wust.springcloud.sso.server.core.service.SysResourceService;
import com.wust.springcloud.sso.server.core.service.SysUserService;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RequestMapping("/LoginController")
@RestController
public class LoginController {
    @Autowired
    private SpringRedisTools springRedisTools;

    @Autowired
    private SysUserService sysUserServiceImpl;

    @Autowired
    private SysMenuService sysMenuServiceImpl;

    @Autowired
    private SysResourceService sysResourceServiceImpl;


    /**
     * 处理登录请求
     * @param jsonObject
     * @return
     */
    @OperationLogAnnotation(moduleName= OperationLogEnum.MODULE_COMMON,businessName="内部登录",operationType= OperationLogEnum.Login)
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ResponseDto login(@RequestBody JSONObject jsonObject) {
        ResponseDto messageMap = new ResponseDto();

        String loginName = jsonObject.getString("loginName");
        String password = jsonObject.getString("password");

        if(MyStringUtils.isBlank(MyStringUtils.null2String(loginName))){
            messageMap.setFlag(ResponseDto.INFOR_WARNING);
            messageMap.setMessage("请输入登录账号");
            return messageMap;
        }

        if(MyStringUtils.isBlank(MyStringUtils.null2String(password))){
            messageMap.setFlag(ResponseDto.INFOR_WARNING);
            messageMap.setMessage("请输入登录口令");
            return messageMap;
        }

        String passwordRC4 = Base64.encodeBase64String(RC4.encry_RC4_string(password, ApplicationEnum.RC4_LOGIN_PASSWORD.getStringValue()).getBytes());
        SysUserSearch sysUserSearch = new SysUserSearch();
        sysUserSearch.setLoginName(loginName);
        sysUserSearch.setPassword(passwordRC4);
        DefaultBusinessContext.getContext().setDataSourceId(ApplicationEnum.DEFAULT.name());
        List<SysUserList> sysUserLists =  sysUserServiceImpl.findByCondition(sysUserSearch);
        if(sysUserLists != null && sysUserLists.size() > 0){
            SysUserList sysUserList = sysUserLists.get(0);
            JSONObject subJSONObject = new JSONObject();
            subJSONObject.put("loginName",loginName);
            String token = createJWT(subJSONObject.toJSONString());
            if (StringUtils.isEmpty(MyStringUtils.null2String(token))) {
                messageMap.setFlag(ResponseDto.INFOR_WARNING);
                messageMap.setMessage("登录失败");
            }else{
                /**
                 * 解析获取当前登录用户的相关资源
                 */
                UserContextDto userContextDto = this.getUserContextDto(sysUserList);

                /**
                 * 将用户登录相关信息存储到缓存，以便解析作为上下文
                 */
                String key = String.format(ApplicationEnum.WEB_LOGIN_KEY.getStringValue(),token);
                springRedisTools.addData(key,  JSONObject.toJSONString(userContextDto), ApplicationEnum.X_AUTH_TOKEN_EXPIRE_TIME.getIntValue(), TimeUnit.MINUTES);

                /**
                 * 设置返回给前端的资源
                 */
                JSONObject responseResource = setResponseResource(userContextDto,token);
                messageMap.setObj(responseResource);
            }
        }else{
            messageMap.setFlag(ResponseDto.INFOR_WARNING);
            messageMap.setMessage("账号或密码错误");
            return messageMap;
        }
        return messageMap;
    }












    private String createJWT(String subject) {
        String token = null;
        try {
            token = JwtHelper.createJWT(ApplicationEnum.JWT_ACCESS_SECRET.getStringValue(),subject,-1);
        } catch (Exception e) {
        }
        return token;
    }



    private UserContextDto getUserContextDto(final SysUserList sysUserList){
        final JSONArray menuJSONArray = new JSONArray(100);
        List<SysMenu> menus = null;                            // 非白名单菜单
        Map<Integer,List<SysMenu>> groupMenusByLevel = null;   // 根据菜单级别分组menus
        Map<String,List<SysMenu>> groupMenusByPcode = null;      // 根据pcode分组 menus

        List<SysResource> resources = null;                    // 非白名单资源
        List<SysResource> resources4anon = null;               // 白名单资源

        Map<String,List<SysResource>> groupResourcesByMenuId = null; // 根据menuId分组resources

        UserContextDto userContextDto = new UserContextDto();

        Long userId = sysUserList.getId();
        String type = sysUserList.getType();
        if("100401".equals(type)){ // 系统管理员
            // 切换数据源，然后查找对应数据源下的菜单资源
            DefaultBusinessContext.getContext().setDataSourceId(ApplicationEnum.DEFAULT.name());
            menus = sysMenuServiceImpl.findAllMenus4SystemAdmin();
            groupMenusByLevel = groupMenusByLevel(menus);
            groupMenusByPcode = groupMenusByPcode(menus);
            menuJSONArray.addAll(JSONArray.parseArray(JSONObject.toJSONString(groupMenusByLevel.get(1))));
            menuToJSON(menuJSONArray,groupMenusByPcode);
            resources = sysResourceServiceImpl.findAllResources4systemAdmin();
            resources4anon = sysResourceServiceImpl.findAllAnonResources4systemAdmin();
            groupResourcesByMenuId = groupResourcesByMenuCode(resources);
        }else{ // 非系统管理员
            menus = sysMenuServiceImpl.findMenuByUserId(userId);
            groupMenusByLevel = groupMenusByLevel(menus);
            groupMenusByPcode = groupMenusByPcode(menus);
            menuJSONArray.addAll(JSONArray.parseArray(JSONObject.toJSONString(groupMenusByLevel.get(1))));
            menuToJSON(menuJSONArray,groupMenusByPcode);

            resources = sysResourceServiceImpl.findResourcesByUserId(userId);
            resources4anon = sysResourceServiceImpl.findAnonResourcesByUserId(userId);
            groupResourcesByMenuId = groupResourcesByMenuCode(resources);
        }

        userContextDto.setUser(sysUserList);
        userContextDto.setMenuJSONArray(menuJSONArray);
        userContextDto.setResources(resources);
        userContextDto.setAnonResources(resources4anon);
        userContextDto.setGroupResourcesByPid(groupResourcesByMenuId);
        return userContextDto;
    }


    /**
     * 对菜单按级别分组
     * @param menus
     * @return
     */
    private Map<Integer,List<SysMenu>> groupMenusByLevel(List<SysMenu> menus){
        Map<Integer,List<SysMenu>> integerListMap = new HashMap<>();
        if(org.apache.commons.collections.CollectionUtils.isNotEmpty(menus)){
            for (SysMenu menu : menus) {
                Integer key = menu.getLevel();
                if(integerListMap.containsKey(key)){
                    integerListMap.get(key).add(menu);
                }else{
                    List<SysMenu> menuList = new ArrayList<>(10);
                    menuList.add(menu);
                    integerListMap.put(key,menuList);
                }
            }
            return integerListMap;
        }
        return null;
    }

    /**
     * 对菜单按pcode分组
     * @param menus
     * @return
     */
    private Map<String,List<SysMenu>> groupMenusByPcode(List<SysMenu> menus){
        Map<String,List<SysMenu>> stringListMap = new HashMap<>();
        if(org.apache.commons.collections.CollectionUtils.isNotEmpty(menus)){
            for (SysMenu menu : menus) {
                String key = menu.getPcode();
                if(stringListMap.containsKey(key)){
                    stringListMap.get(key).add(menu);
                }else{
                    List<SysMenu> menuList = new ArrayList<>(10);
                    menuList.add(menu);
                    stringListMap.put(key,menuList);
                }
            }
            return stringListMap;
        }
        return null;
    }



    private void menuToJSON(final JSONArray jsonArray,final Map<String,List<SysMenu>> groupByPidMenus){
        if(jsonArray != null && jsonArray.size() > 0){
            for (Object o : jsonArray) {
                JSONObject jsonObject = (JSONObject)o;
                jsonObject.put("children",new JSONArray(10));
                String code = jsonObject.getString("code");


                List<SysMenu> children = groupByPidMenus.get(code);
                if(CollectionUtils.isNotEmpty(children)){
                    JSONArray childrenJSONArray = JSONArray.parseArray(JSONObject.toJSONString(children));
                    jsonObject.put("children",childrenJSONArray);
                    menuToJSON(childrenJSONArray,groupByPidMenus);
                }
            }
        }
    }


    /**
     * 对资源按菜单分组
     * @param resources
     * @return
     */
    private Map<String,List<SysResource>> groupResourcesByMenuCode(List<SysResource> resources){
        Map<String,List<SysResource>> stringListHashMap = new HashMap<>();
        if(org.apache.commons.collections.CollectionUtils.isNotEmpty(resources)){
            for (SysResource resource : resources) {
                String key = resource.getCode();
                if(stringListHashMap.containsKey(key)){
                    stringListHashMap.get(key).add(resource);
                }else{
                    List<SysResource> resourceList = new ArrayList<>(10);
                    resourceList.add(resource);
                    stringListHashMap.put(key,resourceList);
                }
            }
            return stringListHashMap;
        }
        return null;
    }

    private JSONObject setResponseResource(final UserContextDto userContextDto,final String xAuthToken){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("xAuthToken",xAuthToken);
        jsonObject.put("userId",userContextDto.getUser().getId());
        jsonObject.put("loginName",userContextDto.getUser().getLoginName());
        jsonObject.put("realName",userContextDto.getUser().getRealName());

        /**
         * 简化并设置登录用户拥有的所有资源权限
         */
        if(CollectionUtils.isNotEmpty(userContextDto.getResources())){
            List<SysResource> permissions = userContextDto.getResources();
            List<String> simplePermissions = new ArrayList<>(userContextDto.getResources().size());
            for (SysResource permission : permissions) {
                simplePermissions.add(permission.getPermission());
            }
            jsonObject.put("permissions",simplePermissions);
        }

        jsonObject.put("menus",userContextDto.getMenuJSONArray());
        return jsonObject;
    }
}
