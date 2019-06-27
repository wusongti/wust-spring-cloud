package com.wust.springcloud.common.entity.sys.department;

import com.wust.springcloud.common.dto.PageDto;
import com.wust.springcloud.common.entity.BaseEntity;

/**
 * Created by WST on 2019/6/3.
 */
public class SysDepartmentSearch extends SysDepartment{
    private static final long serialVersionUID = 1969729601689661126L;

    private PageDto pageDto;

    public PageDto getPageDto() {
        return pageDto;
    }

    public void setPageDto(PageDto pageDto) {
        this.pageDto = pageDto;
    }
}
