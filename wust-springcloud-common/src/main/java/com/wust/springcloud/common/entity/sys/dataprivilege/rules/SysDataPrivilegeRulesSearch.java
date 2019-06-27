package com.wust.springcloud.common.entity.sys.dataprivilege.rules;

import com.wust.springcloud.common.dto.PageDto;

/**
 * Created by WST on 2019/6/10.
 */
public class SysDataPrivilegeRulesSearch extends SysDataPrivilegeRules{
    private static final long serialVersionUID = -5573502806179980913L;
    private PageDto pageDto;

    private String businessName;


    public PageDto getPageDto() {
        return pageDto;
    }

    public void setPageDto(PageDto pageDto) {
        this.pageDto = pageDto;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }
}
