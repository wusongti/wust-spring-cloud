package com.wust.springcloud.admin.server.core.service.defaults.impl;

import com.wust.springcloud.admin.server.core.dao.SysAttachmentMapper;
import com.wust.springcloud.admin.server.core.service.defaults.SysAttachmentService;
import com.wust.springcloud.common.dao.IBaseMapper;
import com.wust.springcloud.common.dto.ResponseDto;
import com.wust.springcloud.common.entity.sys.attachment.SysAttachment;
import com.wust.springcloud.common.entity.sys.attachment.SysAttachmentList;
import com.wust.springcloud.common.entity.sys.attachment.SysAttachmentSearch;
import com.wust.springcloud.common.service.BaseServiceImpl;
import com.wust.springcloud.common.util.FastDFSManager;
import com.wust.springcloud.common.util.FileUtil;
import org.apache.commons.collections.CollectionUtils;
import org.csource.common.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by WST on 2019/5/23.
 */
@Service("sysAttachmentServiceImpl")
public class SysAttachmentServiceImpl extends BaseServiceImpl implements SysAttachmentService {
    @Autowired
    private SysAttachmentMapper sysAttachmentMapper;

    @Override
    protected IBaseMapper getBaseMapper() {
        return sysAttachmentMapper;
    }


    @Override
    public ResponseDto uploadAttachment(File file, SysAttachment sysAttachment) {
        ResponseDto messageMap = new ResponseDto();
        String[] uploadResults = null;
        try {
            uploadResults = FastDFSManager.upload(file);
            if(uploadResults != null && uploadResults.length > 0){
                sysAttachment.setAttachmentKey(UUID.randomUUID().toString());
                sysAttachment.setAttachmentPath(uploadResults[1]);
                sysAttachment.setAttachmentSize((file.length() / 1024)+"");
                sysAttachment.setAttachmentSuffix(file.getName().substring(file.getName().indexOf(".") + 1));
                sysAttachment.setAttachmentName(file.getName());
                sysAttachment.setCreateTime(new Date());

                List<SysAttachment> sysAttachments = new ArrayList<>(1);
                sysAttachments.add(sysAttachment);
                sysAttachmentMapper.insertList(sysAttachments) ;
            }else{
                messageMap.setFlag(ResponseDto.INFOR_WARNING);
                messageMap.setMessage("上传文件到文件服务器失败");
            }
        } catch (IOException e) {
            messageMap.setFlag(ResponseDto.INFOR_WARNING);
            messageMap.setMessage("上传文件到文件服务器失败");
        } catch (MyException e) {
            messageMap.setFlag(ResponseDto.INFOR_WARNING);
            messageMap.setMessage("上传文件到文件服务器失败");
        }finally {
            FileUtil.deleteOnExit(file);
        }
        return messageMap;
    }

    @Override
    public ResponseDto uploadAttachment(byte[] file_buff, SysAttachment sysAttachment) {
        ResponseDto messageMap = new ResponseDto();
        String[] uploadResults = null;
        try {
            uploadResults = FastDFSManager.upload(file_buff,sysAttachment.getAttachmentName());
            if(uploadResults != null && uploadResults.length > 0){
                sysAttachment.setAttachmentKey(UUID.randomUUID().toString());
                sysAttachment.setAttachmentPath(uploadResults[1]);
                sysAttachment.setAttachmentSize((file_buff.length/1024)+"");
                sysAttachment.setCreateTime(new Date());

                List<SysAttachment> sysAttachments = new ArrayList<>(1);
                sysAttachments.add(sysAttachment);
                sysAttachmentMapper.insertList(sysAttachments) ;
            }else{
                messageMap.setFlag(ResponseDto.INFOR_WARNING);
                messageMap.setMessage("上传文件到文件服务器失败");
            }
        } catch (IOException e) {
            messageMap.setFlag(ResponseDto.INFOR_WARNING);
            messageMap.setMessage("上传文件到文件服务器失败");
        } catch (MyException e) {
            messageMap.setFlag(ResponseDto.INFOR_WARNING);
            messageMap.setMessage("上传文件到文件服务器失败");
        }
        return messageMap;
    }

    @Override
    public ResponseDto batchUploadAttachment(List<File> files,List<SysAttachment> sysAttachments) {
        return null;
    }

    @Override
    public ResponseDto downloadAttachment(SysAttachmentSearch sysAttachmentSearch) {
        ResponseDto messageMap = new ResponseDto();
        List<SysAttachmentList> sysAttachmentLists = sysAttachmentMapper.findByCondition(sysAttachmentSearch);
        if(CollectionUtils.isNotEmpty(sysAttachmentLists)) {
            SysAttachmentList sysAttachmentList = sysAttachmentLists.get(0);
            String path = sysAttachmentList.getAttachmentPath();
            byte[] result = FastDFSManager.download(path);

            Map map = new HashMap<>();
            map.put("fileByte",result);
            map.put("fileName",sysAttachmentList.getAttachmentName());
            messageMap.setMapMessage(map);
        }else{
            messageMap.setFlag(ResponseDto.INFOR_WARNING);
            messageMap.setMessage("服务器没有该文件上传记录");
        }
      return messageMap;
    }

    @Override
    public ResponseDto deleteAttachment(SysAttachmentSearch search) {
        ResponseDto messageMap = new ResponseDto();
        List<SysAttachmentList> sysAttachmentLists = sysAttachmentMapper.findByCondition(search);
        if(CollectionUtils.isNotEmpty(sysAttachmentLists)) {
            for (SysAttachmentList sysAttachmentList : sysAttachmentLists) {
                List<String> ids = new ArrayList<>(1);
                ids.add(sysAttachmentList.getId());
                sysAttachmentMapper.batchDelete(ids);

                String path = sysAttachmentList.getAttachmentPath();
                try {
                    FastDFSManager.delete(path);
                } catch (IOException e) {
                } catch (MyException e) {
                }
            }
        }else{
            messageMap.setFlag(ResponseDto.INFOR_WARNING);
            messageMap.setMessage("删除文件失败，服务器没有该文件上传记录");
        }
        return messageMap;
    }
}
