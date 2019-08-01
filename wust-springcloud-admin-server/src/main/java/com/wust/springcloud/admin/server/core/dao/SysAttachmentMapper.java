package com.wust.springcloud.admin.server.core.dao;

import com.wust.springcloud.common.dao.IBaseMapper;
import com.wust.springcloud.common.entity.sys.attachment.SysAttachment;
import com.wust.springcloud.common.entity.sys.attachment.SysAttachmentList;
import com.wust.springcloud.common.entity.sys.attachment.SysAttachmentSearch;
import org.springframework.dao.DataAccessException;
import java.util.List;

/**
 * Created by WST on 2019/5/23.
 */
public interface SysAttachmentMapper extends IBaseMapper<SysAttachment> {
    List<SysAttachmentList> listPage(SysAttachmentSearch search) throws DataAccessException;

    List<SysAttachmentList> findByCondition(SysAttachmentSearch search) throws DataAccessException;
}
