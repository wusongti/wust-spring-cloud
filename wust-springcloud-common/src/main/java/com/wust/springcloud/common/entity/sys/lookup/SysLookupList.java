package com.wust.springcloud.common.entity.sys.lookup;

/**
 * Created by WST on 2019/4/29.
 */
public class SysLookupList extends SysLookup{
    private static final long serialVersionUID = -8760482855507424419L;

    private String statusLabel;
    private String visibleLabel;

    public String getStatusLabel() {
        return statusLabel;
    }

    public void setStatusLabel(String statusLabel) {
        this.statusLabel = statusLabel;
    }

    public String getVisibleLabel() {
        return visibleLabel;
    }

    public void setVisibleLabel(String visibleLabel) {
        this.visibleLabel = visibleLabel;
    }
}
