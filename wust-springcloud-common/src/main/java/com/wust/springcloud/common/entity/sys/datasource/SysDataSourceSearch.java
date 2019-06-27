package com.wust.springcloud.common.entity.sys.datasource;

import com.wust.springcloud.common.dto.PageDto;

/**
 * Created by WST on 2019/6/17.
 */
public class SysDataSourceSearch extends SysDataSource{
    private static final long serialVersionUID = -1347667347205369909L;

    private PageDto pageDto;

    private String companyName;


    public PageDto getPageDto() {
        return pageDto;
    }

    public void setPageDto(PageDto pageDto) {
        this.pageDto = pageDto;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
