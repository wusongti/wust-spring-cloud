package com.wust.springcloud.common.entity.sys.synchrodata;

import com.wust.springcloud.common.dto.PageDto;

/**
 * @author ：wust
 * @date ：Created in 2019/7/19 16:40
 * @description：
 * @version:
 */
public class SysSynchrodataSearch extends SysSynchrodata{
    private static final long serialVersionUID = -113408216588303029L;

    private PageDto pageDto;

    public PageDto getPageDto() {
        return pageDto;
    }

    public void setPageDto(PageDto pageDto) {
        this.pageDto = pageDto;
    }
}
