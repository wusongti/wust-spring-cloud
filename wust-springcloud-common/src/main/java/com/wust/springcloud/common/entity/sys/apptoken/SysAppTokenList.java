package com.wust.springcloud.common.entity.sys.apptoken;

/**
 * Created by WST on 2019/4/18.
 */
public class SysAppTokenList extends SysAppToken{
    private static final long serialVersionUID = 2242327663629366838L;

    private String statusLabel;

    public String getStatusLabel() {
        return statusLabel;
    }

    public void setStatusLabel(String statusLabel) {
        this.statusLabel = statusLabel;
    }


}
