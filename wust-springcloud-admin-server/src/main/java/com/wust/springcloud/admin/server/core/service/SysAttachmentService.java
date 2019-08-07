package com.wust.springcloud.admin.server.core.service;

import com.wust.springcloud.common.dto.ResponseDto;
import com.wust.springcloud.common.entity.sys.attachment.SysAttachment;
import com.wust.springcloud.common.entity.sys.attachment.SysAttachmentSearch;
import com.wust.springcloud.common.service.BaseService;
import java.io.File;
import java.util.List;

/**
 * Created by WST on 2019/5/23.
 */
public interface SysAttachmentService extends BaseService {

    ResponseDto uploadAttachment(File file,SysAttachment sysAttachment);

    ResponseDto uploadAttachment(byte[] file_buff,SysAttachment sysAttachment);

    ResponseDto batchUploadAttachment(List<File> files,List<SysAttachment> entities);

    ResponseDto downloadAttachment(SysAttachmentSearch search);

    ResponseDto deleteAttachment(SysAttachmentSearch search);
}
