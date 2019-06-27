package com.wust.springcloud.common.entity.sys.user;

/**
 * Created by WST on 2019/4/18.
 */
public class SysUserList extends SysUser{
    private static final long serialVersionUID = 382039842212175851L;

    private String sexLabel;
    private String statusLabel;
    private String typeLabel;
    private String onlineStatusLabel;

    public String getSexLabel() {
        return sexLabel;
    }

    public void setSexLabel(String sexLabel) {
        this.sexLabel = sexLabel;
    }

    public String getStatusLabel() {
        return statusLabel;
    }

    public void setStatusLabel(String statusLabel) {
        this.statusLabel = statusLabel;
    }

    public String getTypeLabel() {
        return typeLabel;
    }

    public void setTypeLabel(String typeLabel) {
        this.typeLabel = typeLabel;
    }

    public String getOnlineStatusLabel() {
        return onlineStatusLabel;
    }

    public void setOnlineStatusLabel(String onlineStatusLabel) {
        this.onlineStatusLabel = onlineStatusLabel;
    }
}
