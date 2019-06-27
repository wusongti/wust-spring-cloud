package com.wust.springcloud.common.entity.sys.attachment;

import com.wust.springcloud.common.dto.PageDto;

/**
 * Created by WST on 2019/5/23.
 */
public class SysAttachmentSearch extends SysAttachment{
    private static final long serialVersionUID = 6592161969628339017L;

    private PageDto pageDto;

    public PageDto getPageDto() {
        return pageDto;
    }

    public void setPageDto(PageDto pageDto) {
        this.pageDto = pageDto;
    }
}
