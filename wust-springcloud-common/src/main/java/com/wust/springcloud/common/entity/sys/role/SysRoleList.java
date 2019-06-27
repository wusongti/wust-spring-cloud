package com.wust.springcloud.common.entity.sys.role;

/**
 * Created by WST on 2019/4/28.
 */
public class SysRoleList extends SysRole {
    private static final long serialVersionUID = 9206693877645361931L;

    private String statusLabel;

    public String getStatusLabel() {
        return statusLabel;
    }

    public void setStatusLabel(String statusLabel) {
        this.statusLabel = statusLabel;
    }
}
