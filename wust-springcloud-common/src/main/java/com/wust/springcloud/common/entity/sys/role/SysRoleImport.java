package com.wust.springcloud.common.entity.sys.role;

/**
 * Created by WST on 2019/5/27.
 */
public class SysRoleImport extends SysRole{
    private static final long serialVersionUID = 7927870887791931676L;
    // 行号，必须加
    private Integer row;
    // 是否成功，必须加
    private Boolean successFlag;
    // 错误原因，必须加
    private String errorMessage;

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Boolean getSuccessFlag() {
        return successFlag;
    }

    public void setSuccessFlag(Boolean successFlag) {
        this.successFlag = successFlag;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return super.toString() + "\nSysUserImport{" +
                "row=" + row +
                ", successFlag=" + successFlag +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }

}
