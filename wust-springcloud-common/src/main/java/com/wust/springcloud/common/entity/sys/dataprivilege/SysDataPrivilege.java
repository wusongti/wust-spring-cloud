package com.wust.springcloud.common.entity.sys.dataprivilege;

import com.wust.springcloud.common.entity.BaseEntity;

/**
 * Created by WST on 2019/6/10.
 */
public class SysDataPrivilege extends BaseEntity{
    private static final long serialVersionUID = 5467184476284496996L;

    private String id;
    private String businessName;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }


    @Override
    public String toString() {
        return super.toString() + "\nSysDataPrivilege{" +
                "id='" + id + '\'' +
                ", businessName='" + businessName + '\'' +
                '}';
    }
}
