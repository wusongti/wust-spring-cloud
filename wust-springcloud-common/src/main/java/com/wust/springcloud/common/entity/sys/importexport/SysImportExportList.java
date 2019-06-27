package com.wust.springcloud.common.entity.sys.importexport;

/**
 * Created by WST on 2019/5/20.
 */
public class SysImportExportList extends SysImportExport{
    private static final long serialVersionUID = -6355731580232808981L;

    private String size;
    private String suffix;
    private String statusLabel;
    private String operationTypeLabel;

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getStatusLabel() {
        return statusLabel;
    }

    public void setStatusLabel(String statusLabel) {
        this.statusLabel = statusLabel;
    }

    public String getOperationTypeLabel() {
        return operationTypeLabel;
    }

    public void setOperationTypeLabel(String operationTypeLabel) {
        this.operationTypeLabel = operationTypeLabel;
    }
}
