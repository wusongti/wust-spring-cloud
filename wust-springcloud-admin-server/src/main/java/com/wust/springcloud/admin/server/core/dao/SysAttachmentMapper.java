package com.wust.springcloud.admin.server.core.dao;

import com.wust.springcloud.common.entity.sys.attachment.SysAttachment;
import com.wust.springcloud.common.entity.sys.attachment.SysAttachmentList;
import com.wust.springcloud.common.entity.sys.attachment.SysAttachmentSearch;
import org.springframework.dao.DataAccessException;
import java.util.List;

/**
 * Created by WST on 2019/5/23.
 */
public interface SysAttachmentMapper {
    List<SysAttachmentList> findByCondition(SysAttachmentSearch search) throws DataAccessException;

    int batchInsert(List<SysAttachment> entities) throws DataAccessException;

    int batchDelete(List<String> keys) throws DataAccessException;
}
