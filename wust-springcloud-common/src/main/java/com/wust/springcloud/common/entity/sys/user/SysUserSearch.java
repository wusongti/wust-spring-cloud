package com.wust.springcloud.common.entity.sys.user;

import com.wust.springcloud.common.dto.PageDto;

/**
 * Created by WST on 2019/4/18.
 */
public class SysUserSearch extends SysUser{
    private static final long serialVersionUID = -7794004438178053115L;

    private PageDto pageDto;

    public PageDto getPageDto() {
        return pageDto;
    }

    public void setPageDto(PageDto pageDto) {
        this.pageDto = pageDto;
    }
}
