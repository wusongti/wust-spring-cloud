package com.wust.springcloud.common.entity.sys.department;

/**
 * Created by WST on 2019/6/3.
 */
public class SysDepartmentUpdate extends SysDepartment{
    private static final long serialVersionUID = 8113180558773593542L;

    private String pname;

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }
}
