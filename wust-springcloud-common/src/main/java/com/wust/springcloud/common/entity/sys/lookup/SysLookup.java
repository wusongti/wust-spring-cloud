package com.wust.springcloud.common.entity.sys.lookup;

import com.wust.springcloud.common.entity.BaseEntity;

/**
 * Created by WST on 2019/4/29.
 */
public class SysLookup extends BaseEntity{
    private static final long serialVersionUID = 5377600789270890448L;

    private String id;
    private String code;
    private String parentCode;
    private String rootCode;
    private String value;
    private String name;
    private String status;
    private String description;
    private Integer sort;
    private String visible;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getRootCode() {
        return rootCode;
    }

    public void setRootCode(String rootCode) {
        this.rootCode = rootCode;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getVisible() {
        return visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }

    @Override
    public String toString() {
        return super.toString() + "\nSysLookup{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", parentCode='" + parentCode + '\'' +
                ", rootCode='" + rootCode + '\'' +
                ", value='" + value + '\'' +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", description='" + description + '\'' +
                ", sort=" + sort +
                ", visible='" + visible + '\'' +
                '}';
    }
}
