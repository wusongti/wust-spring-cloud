package com.wust.springcloud.common.dto;

import com.wust.springcloud.common.entity.sys.menu.SysMenu;

/**
 * Created by WST on 2019/6/5.
 */
public class MenuTreeDto extends SysMenu{
    private static final long serialVersionUID = 5021063515865583310L;

    private String checked;
    private String nocheck;

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    public String getNocheck() {
        return nocheck;
    }

    public void setNocheck(String nocheck) {
        this.nocheck = nocheck;
    }
}
