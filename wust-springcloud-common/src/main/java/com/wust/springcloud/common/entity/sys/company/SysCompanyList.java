package com.wust.springcloud.common.entity.sys.company;

/**
 * Created by WST on 2019/6/3.
 */
public class SysCompanyList extends SysCompany{
    private static final long serialVersionUID = 2777758859931886349L;

    private String typeLabel;

    public String getTypeLabel() {
        return typeLabel;
    }

    public void setTypeLabel(String typeLabel) {
        this.typeLabel = typeLabel;
    }
}
