package com.wust.springcloud.common.entity.sys.attachment;

import com.wust.springcloud.common.entity.BaseEntity;

/**
 * Created by WST on 2019/5/23.
 */
public class SysAttachment extends BaseEntity {
    private static final long serialVersionUID = -5743315252070614027L;

    private String relationTable;
    private String relationId;
    private String relationField;
    private String attachmentKey;
    private String attachmentName;
    private String attachmentSize;
    private String attachmentPath;
    private String attachmentSuffix;
    private Long companyId;

    public String getRelationTable() {
        return relationTable;
    }

    public void setRelationTable(String relationTable) {
        this.relationTable = relationTable;
    }

    public String getRelationId() {
        return relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }

    public String getRelationField() {
        return relationField;
    }

    public void setRelationField(String relationField) {
        this.relationField = relationField;
    }

    public String getAttachmentKey() {
        return attachmentKey;
    }

    public void setAttachmentKey(String attachmentKey) {
        this.attachmentKey = attachmentKey;
    }

    public String getAttachmentName() {
        return attachmentName;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }

    public String getAttachmentSize() {
        return attachmentSize;
    }

    public void setAttachmentSize(String attachmentSize) {
        this.attachmentSize = attachmentSize;
    }

    public String getAttachmentPath() {
        return attachmentPath;
    }

    public void setAttachmentPath(String attachmentPath) {
        this.attachmentPath = attachmentPath;
    }

    public String getAttachmentSuffix() {
        return attachmentSuffix;
    }

    public void setAttachmentSuffix(String attachmentSuffix) {
        this.attachmentSuffix = attachmentSuffix;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    @Override
    public String toString() {
        return super.toString() + "\nSysAttachment{" +
                "relationTable='" + relationTable + '\'' +
                ", relationId='" + relationId + '\'' +
                ", relationField='" + relationField + '\'' +
                ", attachmentKey='" + attachmentKey + '\'' +
                ", attachmentName='" + attachmentName + '\'' +
                ", attachmentSize='" + attachmentSize + '\'' +
                ", attachmentPath='" + attachmentPath + '\'' +
                ", attachmentSuffix='" + attachmentSuffix + '\'' +
                ", companyId='" + companyId + '\'' +
                '}';
    }
}
