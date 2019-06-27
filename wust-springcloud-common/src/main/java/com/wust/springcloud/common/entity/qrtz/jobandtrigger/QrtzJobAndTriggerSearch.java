package com.wust.springcloud.common.entity.qrtz.jobandtrigger;

import com.wust.springcloud.common.dto.PageDto;

/**
 * Created by WST on 2019/6/13.
 */
public class QrtzJobAndTriggerSearch extends QrtzJobAndTrigger{
    private static final long serialVersionUID = -6704567086912927110L;

    private PageDto pageDto;

    public PageDto getPageDto() {
        return pageDto;
    }

    public void setPageDto(PageDto pageDto) {
        this.pageDto = pageDto;
    }
}
