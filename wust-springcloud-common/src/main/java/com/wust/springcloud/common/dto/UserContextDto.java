package com.wust.springcloud.common.dto;

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

    private Map<Integer,List<SysMenu>> groupMenusByLevel;                  // 当前登录用户所拥有的菜单集合，key=菜单级别，value=该级别的菜单集合
    private Map<String,List<SysMenu>> groupMenusByPid;                     // 当前登录用户所拥有的菜单集合，key=父菜单id，value=子菜单集合
    private List<SysResource> resources;                                   // 非白名单资源集合
    private List<SysResource> anonResources;                               // 白名单资源集合
    private Map<String,List<SysResource>> groupResourcesByPid;             // 当前登录用户所拥有的资源集合，key=菜单id，value=菜单下的资源集合
    private SysUser user;                                                  // 当前登录用户

    public Map<Integer, List<SysMenu>> getGroupMenusByLevel() {
        return groupMenusByLevel;
    }

    public void setGroupMenusByLevel(Map<Integer, List<SysMenu>> groupMenusByLevel) {
        this.groupMenusByLevel = groupMenusByLevel;
    }

    public Map<String, List<SysMenu>> getGroupMenusByPid() {
        return groupMenusByPid;
    }

    public void setGroupMenusByPid(Map<String, List<SysMenu>> groupMenusByPid) {
        this.groupMenusByPid = groupMenusByPid;
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
                "groupMenusByLevel=" + groupMenusByLevel +
                ", groupMenusByPid=" + groupMenusByPid +
                ", resources=" + resources +
                ", anonResources=" + anonResources +
                ", groupResourcesByPid=" + groupResourcesByPid +
                ", user=" + user +
                '}';
    }
}
