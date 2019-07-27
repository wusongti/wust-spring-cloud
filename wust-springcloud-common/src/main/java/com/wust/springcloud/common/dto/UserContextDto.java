package com.wust.springcloud.common.dto;

import com.alibaba.fastjson.JSONArray;
import com.wust.springcloud.common.entity.sys.menu.SysMenu;
import com.wust.springcloud.common.entity.sys.resource.SysResource;
import com.wust.springcloud.common.entity.sys.user.SysUser;

import java.util.List;
import java.util.Map;

/**
 * Created by WST on 2019/5/6.
 */
public class UserContextDto implements java.io.Serializable{
    private static final long serialVersionUID = 4068960275963284139L;
    private JSONArray menuJSONArray;                                       // 当前登录用户所拥有的菜单集合
    private List<SysResource> resources;                                   // 非白名单资源集合
    private List<SysResource> anonResources;                               // 白名单资源集合
    private Map<String,List<SysResource>> groupResourcesByPid;             // 当前登录用户所拥有的资源集合，key=菜单id，value=菜单下的资源集合
    private SysUser user;                                                  // 当前登录用户

    public JSONArray getMenuJSONArray() {
        return menuJSONArray;
    }

    public void setMenuJSONArray(JSONArray menuJSONArray) {
        this.menuJSONArray = menuJSONArray;
    }

    public List<SysResource> getResources() {
        return resources;
    }

    public void setResources(List<SysResource> resources) {
        this.resources = resources;
    }

    public List<SysResource> getAnonResources() {
        return anonResources;
    }

    public void setAnonResources(List<SysResource> anonResources) {
        this.anonResources = anonResources;
    }

    public Map<String, List<SysResource>> getGroupResourcesByPid() {
        return groupResourcesByPid;
    }

    public void setGroupResourcesByPid(Map<String, List<SysResource>> groupResourcesByPid) {
        this.groupResourcesByPid = groupResourcesByPid;
    }

    public SysUser getUser() {
        return user;
    }

    public void setUser(SysUser user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserContextDto{" +
                "menuJSONArray=" + menuJSONArray +
                ", resources=" + resources +
                ", anonResources=" + anonResources +
                ", groupResourcesByPid=" + groupResourcesByPid +
                ", user=" + user +
                '}';
    }
}
