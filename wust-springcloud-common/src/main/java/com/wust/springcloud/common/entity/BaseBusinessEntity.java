package com.wust.springcloud.common.entity;

/**
 * @author ：wust
 * @date ：Created in 2019/8/1 10:07
 * @description：
 * @version:
 */
public class BaseBusinessEntity extends BaseEntity{
    private static final long serialVersionUID = -5289712815406052036L;

    protected Long companyId;

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }


}
