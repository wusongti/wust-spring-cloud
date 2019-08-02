package com.wust.springcloud.common.entity.sys.resource;

import com.wust.springcloud.common.entity.BaseEntity;

/**
 * Created by WST on 2019/4/28.
 */
public class SysResource extends BaseEntity {
    private static final long serialVersionUID = 9169618756448096826L;

    private String code;
    private String menuCode;	    //菜单code
    private String name;			//资源名
    private String description;     //资源描述
    private String permission;	    //资源需要权限,如user:add
    private String url;             //资源url

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
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
                ", code='" + code + '\'' +
                ", menuCode='" + menuCode + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", permission='" + permission + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
