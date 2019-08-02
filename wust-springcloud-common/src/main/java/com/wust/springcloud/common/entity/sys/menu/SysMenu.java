package com.wust.springcloud.common.entity.sys.menu;

import com.wust.springcloud.common.entity.BaseEntity;

/**
 * Created by WST on 2019/4/28.
 */
public class SysMenu extends BaseEntity {
    private static final long serialVersionUID = 9169618756448096826L;

    private String code;
    private String pcode;
    private String name;		// 菜单名称
    private String description; // 菜单描述
    private String url;			// 菜单指向的url
    private String permission;	// 菜单需要的权限,如user:list
    private Integer level;		// 菜单的层次
    private Integer sort;      // 菜单的排序
    private String img;         // 菜单图片
    private String type;        // 菜单类型
    private String visible;     // 是否可见


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVisible() {
        return visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }


    @Override
    public String toString() {
        return "SysMenu{" +
                ", code='" + code + '\'' +
                ", pcode='" + pcode + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                ", permission='" + permission + '\'' +
                ", level=" + level +
                ", sort=" + sort +
                ", img='" + img + '\'' +
                ", type='" + type + '\'' +
                ", visible='" + visible + '\'' +
                '}';
    }
}
