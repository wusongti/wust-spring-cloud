package com.wust.springcloud.common.entity.sys.company;

/**
 * Created by WST on 2019/6/3.
 */
public class SysCompanyUpdate extends SysCompany{
    private static final long serialVersionUID = 7277353807965311273L;

    private String pname;

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }
}
