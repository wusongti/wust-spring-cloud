
package com.wust.springcloud.common.entity;


import javax.persistence.Id;
import java.util.Date;

/**
 * 基础对象
 * 2019年2月22日 11:35:44
 * @author wust
 */
public class BaseEntity implements java.io.Serializable {
    private static final long serialVersionUID = -7114772512426125749L;

    /**
     * 主键
     */
    @Id
    private Long id;
    /**
     * 创建人id
     **/
    protected Long createrId;
    /**
     * 创建人名称
     **/
    protected String createrName;
    /**
     * 创建时间
     **/
    protected Date createTime;
    /**
     * 修改人id
     **/
    protected Long modifyId;
    /**
     * 修改人名称
     **/
    protected String modifyName;
    /**
     * 修改时间
     **/
    protected Date modifyTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCreaterId() {
        return createrId;
    }

    public void setCreaterId(Long createrId) {
        this.createrId = createrId;
    }

    public String getCreaterName() {
        return createrName;
    }

    public void setCreaterName(String createrName) {
        this.createrName = createrName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getModifyId() {
        return modifyId;
    }

    public void setModifyId(Long modifyId) {
        this.modifyId = modifyId;
    }

    public String getModifyName() {
        return modifyName;
    }

    public void setModifyName(String modifyName) {
        this.modifyName = modifyName;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "id=" + id +
                ", createrId='" + createrId + '\'' +
                ", createrName='" + createrName + '\'' +
                ", createTime=" + createTime +
                ", modifyId='" + modifyId + '\'' +
                ", modifyName='" + modifyName + '\'' +
                ", modifyTime=" + modifyTime +
                '}';
    }
}

