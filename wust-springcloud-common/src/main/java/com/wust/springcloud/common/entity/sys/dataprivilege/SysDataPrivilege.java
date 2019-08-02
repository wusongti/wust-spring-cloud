package com.wust.springcloud.common.entity.sys.dataprivilege;

import com.wust.springcloud.common.entity.BaseEntity;


/**
 * Created by WST on 2019/6/10.
 */
public class SysDataPrivilege extends BaseEntity{
    private static final long serialVersionUID = 5467184476284496996L;

    private String uuid;

    private String businessName;


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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
                "uuid='" + uuid + '\'' +
                ", businessName='" + businessName + '\'' +
                '}';
    }
}
