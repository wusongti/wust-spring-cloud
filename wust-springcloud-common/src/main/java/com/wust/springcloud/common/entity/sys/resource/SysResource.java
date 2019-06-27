package com.wust.springcloud.common.entity.sys.resource;

import com.wust.springcloud.common.entity.BaseEntity;

/**
 * Created by WST on 2019/4/28.
 */
public class SysResource extends BaseEntity {
    private static final long serialVersionUID = 9169618756448096826L;

    private String id;			    //资源id
    private String menuId;			//菜单id
    private String name;			//资源名
    private String description;     //资源描述
    private String permission;	    //资源需要权限,如user:add
    private String url;             //资源url

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return super.toString() + "\nSysResource{" +
                "id='" + id + '\'' +
                ", menuId='" + menuId + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", permission='" + permission + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
