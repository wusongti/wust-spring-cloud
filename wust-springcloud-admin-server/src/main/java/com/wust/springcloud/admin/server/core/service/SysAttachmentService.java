package com.wust.springcloud.admin.server.core.service;

import com.wust.springcloud.common.dto.MessageMap;
import com.wust.springcloud.common.entity.sys.attachment.SysAttachment;
import com.wust.springcloud.common.entity.sys.attachment.SysAttachmentList;
import com.wust.springcloud.common.entity.sys.attachment.SysAttachmentSearch;

import java.io.File;
import java.util.List;

/**
 * Created by WST on 2019/5/23.
 */
public interface SysAttachmentService {
    List<SysAttachmentList> findByCondition(SysAttachmentSearch search);

    int batchDelete(List<String> keys);

    MessageMap uploadAttachment(File file,SysAttachment sysAttachment);

    MessageMap uploadAttachment(byte[] file_buff,SysAttachment sysAttachment);

    MessageMap batchUploadAttachment(List<File> files,List<SysAttachment> entities);

    MessageMap downloadAttachment(SysAttachmentSearch search);

    MessageMap deleteAttachment(SysAttachmentSearch search);
}
