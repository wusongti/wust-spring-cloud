package com.wust.springcloud.sso.server.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wust.springcloud.common.annotations.OperationLogAnnotation;
import com.wust.springcloud.common.context.DefaultBusinessContext;
import com.wust.springcloud.common.dto.MessageMap;
import com.wust.springcloud.common.dto.UserContextDto;
import com.wust.springcloud.common.entity.sys.apptoken.SysAppToken;
import com.wust.springcloud.common.entity.sys.apptoken.SysAppTokenList;
import com.wust.springcloud.common.entity.sys.apptoken.SysAppTokenSearch;
import com.wust.springcloud.common.entity.sys.menu.SysMenu;
import com.wust.springcloud.common.entity.sys.resource.SysResource;
import com.wust.springcloud.common.entity.sys.user.SysUserList;
import com.wust.springcloud.common.entity.sys.user.SysUserSearch;
import com.wust.springcloud.common.enums.ApplicationEnum;
import com.wust.springcloud.common.enums.OperationLogEnum;
import com.wust.springcloud.common.util.*;
import com.wust.springcloud.common.util.cache.SpringRedisTools;
import com.wust.springcloud.sso.server.service.AuthenticationService;
import io.jsonwebtoken.Claims;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 认证授权控制器
 * Created by WST on 2019/3/15.
 */
@RequestMapping("/AuthenticationController")
@RestController
public class AuthenticationController {
    @Autowired
    private SpringRedisTools springRedisTools;


    @Autowired
    private AuthenticationService authenticationServiceImpl;

    /**
     * 处理登录请求
     * @param jsonObject
     * @return
     */
    @OperationLogAnnotation(moduleName= OperationLogEnum.MODULE_COMMON,businessName="内部登录",operationType= OperationLogEnum.Login)
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public MessageMap login(@RequestBody JSONObject jsonObject) {
        MessageMap messageMap = new MessageMap();

        String loginName = jsonObject.getString("loginName");
        String password = jsonObject.getString("password");

        if(MyStringUtils.isBlank(MyStringUtils.null2String(loginName))){
            messageMap.setFlag(MessageMap.INFOR_WARNING);
            messageMap.setMessage("请输入登录账号");
            return messageMap;
        }

        if(MyStringUtils.isBlank(MyStringUtils.null2String(password))){
            messageMap.setFlag(MessageMap.INFOR_WARNING);
            messageMap.setMessage("请输入登录口令");
            return messageMap;
        }

        String passwordRC4 = Base64.encodeBase64String(RC4.encry_RC4_string(password, ApplicationEnum.RC4_LOGIN_PASSWORD.getStringValue()).getBytes());
        SysUserSearch sysUserSearch = new SysUserSearch();
        sysUserSearch.setLoginName(loginName);
        sysUserSearch.setPassword(passwordRC4);
        List<SysUserList> sysUserLists =  authenticationServiceImpl.findByCondition(sysUserSearch);
        if(sysUserLists != null && sysUserLists.size() > 0){
            SysUserList sysUserList = sysUserLists.get(0);
            JSONObject subJSONObject = new JSONObject();
            subJSONObject.put("loginName",loginName);
            String token = createJWT(subJSONObject.toJSONString());
            if (StringUtils.isEmpty(MyStringUtils.null2String(token))) {
                messageMap.setFlag(MessageMap.INFOR_WARNING);
                messageMap.setMessage("登录失败");
            }else{
                /**
                 * 解析获取当前登录用户的相关资源
                 */
                UserContextDto userContextDto = this.getUserContextDto(sysUserList);

                /**
                 * 将用户登录相关信息存储到缓存，以便解析作为上下文
                 */
                String key = String.format(ApplicationEnum.WEB_LOGIN_KEY.getStringValue(),loginName);
                springRedisTools.addData(key,  JSONObject.toJSONString(userContextDto), ApplicationEnum.X_AUTH_TOKEN_EXPIRE_TIME.getIntValue(), TimeUnit.MINUTES);

                /**
                 * 设置返回给前端的资源
                 */
                JSONObject responseResource = setResponseResource(userContextDto,token);
                messageMap.setObj(responseResource);
            }
        }else{
            messageMap.setFlag(MessageMap.INFOR_WARNING);
            messageMap.setMessage("账号或密码错误");
            return messageMap;
        }
        return messageMap;
    }




    /**
     * 外部系统登录获取token
     * @param jsonObject
     * @return
     */
    @OperationLogAnnotation(moduleName= OperationLogEnum.MODULE_COMMON,businessName="外部登录",operationType= OperationLogEnum.Login)
    @RequestMapping(value = "/login4api",method = RequestMethod.POST)
    public MessageMap login4api(@RequestBody JSONObject jsonObject) {
        MessageMap messageMap = new MessageMap();

        String loginName = jsonObject.getString("loginName");
        String password = jsonObject.getString("password");

        if(MyStringUtils.isBlank(MyStringUtils.null2String(loginName))){
            messageMap.setFlag(MessageMap.INFOR_WARNING);
            messageMap.setMessage("请输入登录账号");
            return messageMap;
        }

        if(MyStringUtils.isBlank(MyStringUtils.null2String(password))){
            messageMap.setFlag(MessageMap.INFOR_WARNING);
            messageMap.setMessage("请输入登录口令");
            return messageMap;
        }

        String passwordRC4 = Base64.encodeBase64String(RC4.encry_RC4_string(password, ApplicationEnum.RC4_LOGIN_PASSWORD.getStringValue()).getBytes());
        SysAppTokenSearch sysAppTokenSearch = new SysAppTokenSearch();
        sysAppTokenSearch.setLoginName(loginName);
        sysAppTokenSearch.setPassword(passwordRC4);
        List<SysAppTokenList> sysAppTokenLists =  authenticationServiceImpl.findByCondition(sysAppTokenSearch);
        if(sysAppTokenLists != null && sysAppTokenLists.size() > 0){
            JSONObject subJSONObject = new JSONObject();
            subJSONObject.put("loginName",loginName);
            String token = createJWT(subJSONObject.toJSONString());
            if (StringUtils.isEmpty(MyStringUtils.null2String(token))) {
                messageMap.setFlag(MessageMap.INFOR_WARNING);
                messageMap.setMessage("登录失败");
            }else{
                String key = String.format(ApplicationEnum.WEB_LOGIN_KEY.getStringValue(),loginName);
                springRedisTools.addData(key, key,  ApplicationEnum.X_AUTH_TOKEN_EXPIRE_TIME.getIntValue(), TimeUnit.MINUTES);

                SysAppToken sysAppToken = sysAppTokenLists.get(0);
                sysAppToken.setToken(token);
                sysAppToken.setExpireTime((new DateTime()).plusMinutes(ApplicationEnum.X_AUTH_TOKEN_EXPIRE_TIME.getIntValue()).toDate());
                authenticationServiceImpl.update(sysAppToken);
            }

            messageMap.setObj(token);
        }else{
            messageMap.setFlag(MessageMap.INFOR_WARNING);
            messageMap.setMessage("账号或密码错误");
            return messageMap;
        }
        return messageMap;
    }


    /**
     * 校验token
     * @param token
     * @return
     */
    @OperationLogAnnotation(moduleName= OperationLogEnum.MODULE_COMMON,businessName="查询是否存在token",operationType= OperationLogEnum.Search)
    @RequestMapping(value = "/hasToken/{token}")
    public boolean hasToken(@PathVariable String token) {
        boolean flag = true;
        JSONObject jsonObject = parseJWT(token);
        if(jsonObject == null){
            flag = false;
        }else{
            String loginName = jsonObject.getString("loginName");
            String key = String.format(ApplicationEnum.WEB_LOGIN_KEY.getStringValue(),loginName);
            if(springRedisTools.hasKey(key)){ // 刷新缓存时间和token时间
                /**
                 * 刷新redis缓存时间
                 */
                Object obj = springRedisTools.getByKey(key);
                if(obj != null){
                    springRedisTools.updateExpire(key, ApplicationEnum.X_AUTH_TOKEN_EXPIRE_TIME.getIntValue(), TimeUnit.MINUTES);
                }else{
                    flag = false;
                    return flag;
                }


                /**
                 * 刷新token时间
                 */
                SysAppTokenSearch sysAppTokenSearch = new SysAppTokenSearch();
                sysAppTokenSearch.setLoginName(loginName);
                List<SysAppTokenList> sysAppTokenLists =  authenticationServiceImpl.findByCondition(sysAppTokenSearch);
                if(sysAppTokenLists != null && sysAppTokenLists.size() > 0){
                    SysAppToken sysAppToken = sysAppTokenLists.get(0);
                    sysAppToken.setExpireTime((new DateTime()).plusMinutes(ApplicationEnum.X_AUTH_TOKEN_EXPIRE_TIME.getIntValue()).toDate());
                    authenticationServiceImpl.update(sysAppToken);
                }

                flag = true;
            }else{
                flag = false;
            }
        }
        return flag;
    }


    /**
     * 登出
     * @param loginName
     * @return
     */
    @OperationLogAnnotation(moduleName= OperationLogEnum.MODULE_COMMON,businessName="登出",operationType= OperationLogEnum.Logout)
    @RequestMapping(value = "/logout/{loginName}",method = RequestMethod.POST)
    public MessageMap logout(@PathVariable String loginName) {
        MessageMap messageMap = new MessageMap();

        String key = String.format(ApplicationEnum.WEB_LOGIN_KEY.getStringValue(),loginName);
        if(springRedisTools.hasKey(key)){
            springRedisTools.deleteByKey(key);
        }

        return messageMap;
    }

    @RequestMapping(value = "/loadSubMenuById/{loginName}/{menuId}",method = RequestMethod.POST)
    public MessageMap loadSubMenuById(@PathVariable String loginName,@PathVariable String menuId) {
        MessageMap messageMap = new MessageMap();

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


    /**
     * 根据token获取UserContextDto
     * @param token
     * @return
     */
    @RequestMapping(value = "/getUserContextDtoByToken/{token}")
    public String getUserContextDtoByToken(@PathVariable String token){
        String jsonString = null;
        JSONObject jsonObject = parseJWT(token);
        if(jsonObject == null){
        }else {
            String loginName = jsonObject.getString("loginName");
            String key = String.format(ApplicationEnum.WEB_LOGIN_KEY.getStringValue(), loginName);
            if (springRedisTools.hasKey(key)) {
                Object obj = springRedisTools.getByKey(key);
                jsonString = obj + "";
                return jsonString;
            }
        }
        return jsonString;
    }


    private String createJWT(String subject) {
        String token = null;
        try {
            token = JwtHelper.createJWT(ApplicationEnum.JWT_ACCESS_SECRET.getStringValue(),subject,-1);
        } catch (Exception e) {
        }
        return token;
    }

    private JSONObject parseJWT(String token){
        JSONObject jsonObject = null;
        try {
            Claims claims = JwtHelper.parseJWT(ApplicationEnum.JWT_ACCESS_SECRET.getStringValue(),token);
            jsonObject = JSONObject.parseObject(claims.getSubject());
        } catch (Exception e) {
        }
        return jsonObject;
    }



    private UserContextDto getUserContextDto(final SysUserList sysUserList){
        // 切换数据源，然后查找对应数据源下的菜单资源
        DefaultBusinessContext.getContext().setDataSourceId(sysUserList.getCompanyId());

        List<SysMenu> menus = null;                            // 非白名单菜单
        Map<Integer,List<SysMenu>> groupMenusByLevel = null;   // 根据菜单级别分组menus
        Map<String,List<SysMenu>> groupMenusByPid = null;      // 根据pid分组 menus

        List<SysResource> resources = null;                    // 非白名单资源
        List<SysResource> resources4anon = null;               // 白名单资源

        Map<String,List<SysResource>> groupResourcesByMenuId = null; // 根据menuId分组resources

        UserContextDto userContextDto = new UserContextDto();

        String userId = sysUserList.getId();
        String type = sysUserList.getType();
        if("100401".equals(type)){ // 系统管理员
            menus = authenticationServiceImpl.findAllMenus4SystemAdmin();
            groupMenusByLevel = groupByLevelMenus(menus);
            groupMenusByPid = groupByPidMenus(menus);


            resources = authenticationServiceImpl.findAllResources4systemAdmin();
            resources4anon = authenticationServiceImpl.findAllAnonResources4systemAdmin();
            groupResourcesByMenuId = groupByMenuIdResources(resources);
        }else{ // 非系统管理员
            menus = authenticationServiceImpl.findMenuByUserId(userId);
            groupMenusByLevel = groupByLevelMenus(menus);
            groupMenusByPid = groupByPidMenus(menus);


            resources = authenticationServiceImpl.findResourcesByUserId(userId);
            resources4anon = authenticationServiceImpl.findAnonResourcesByUserId(userId);
            groupResourcesByMenuId = groupByMenuIdResources(resources);
        }

        userContextDto.setUser(sysUserList);
        userContextDto.setGroupMenusByLevel(groupMenusByLevel);
        userContextDto.setGroupMenusByPid(groupMenusByPid);
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
    private Map<Integer,List<SysMenu>> groupByLevelMenus(List<SysMenu> menus){
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
     * 对菜单按父菜单id分组
     * @param menus
     * @return
     */
    private Map<String,List<SysMenu>> groupByPidMenus(List<SysMenu> menus){
        Map<String,List<SysMenu>> stringListMap = new HashMap<>();
        if(org.apache.commons.collections.CollectionUtils.isNotEmpty(menus)){
            for (SysMenu menu : menus) {
                String key = menu.getpId();
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

    /**
     * 对资源按菜单分组
     * @param resources
     * @return
     */
    private Map<String,List<SysResource>> groupByMenuIdResources(List<SysResource> resources){
        Map<String,List<SysResource>> stringListHashMap = new HashMap<>();
        if(org.apache.commons.collections.CollectionUtils.isNotEmpty(resources)){
            for (SysResource resource : resources) {
                String key = resource.getMenuId();
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


        /**
         * 简化并设置一级菜单
         */
        if(!org.springframework.util.CollectionUtils.isEmpty(userContextDto.getGroupMenusByLevel())){
            List<SysMenu> firstLevel = userContextDto.getGroupMenusByLevel().get(1);
            if(CollectionUtils.isNotEmpty(firstLevel)){
                JSONArray jsonArray = new JSONArray(firstLevel.size());
                for (SysMenu menu : firstLevel) {
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
                jsonObject.put("oneLevelMenus",jsonArray);
            }
        }
        return jsonObject;
    }
}
