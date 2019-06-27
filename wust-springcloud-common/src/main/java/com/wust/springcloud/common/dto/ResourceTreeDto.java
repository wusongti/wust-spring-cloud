package com.wust.springcloud.common.dto;

import com.wust.springcloud.common.entity.sys.resource.SysResource;

/**
 * Created by WST on 2019/6/5.
 */
public class ResourceTreeDto extends SysResource{
    private static final long serialVersionUID = -8262562291405260277L;

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
