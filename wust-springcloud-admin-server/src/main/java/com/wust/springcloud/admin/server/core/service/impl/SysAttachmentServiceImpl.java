package com.wust.springcloud.admin.server.core.service.impl;

import com.wust.springcloud.admin.server.core.dao.SysAttachmentMapper;
import com.wust.springcloud.admin.server.core.service.SysAttachmentService;
import com.wust.springcloud.common.dto.MessageMap;
import com.wust.springcloud.common.entity.sys.attachment.SysAttachment;
import com.wust.springcloud.common.entity.sys.attachment.SysAttachmentList;
import com.wust.springcloud.common.entity.sys.attachment.SysAttachmentSearch;
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
public class SysAttachmentServiceImpl implements SysAttachmentService {
    @Autowired
    private SysAttachmentMapper sysAttachmentMapper;

    @Override
    public List<SysAttachmentList> findByCondition(SysAttachmentSearch sysAttachmentSearch) {
        return sysAttachmentMapper.findByCondition(sysAttachmentSearch);
    }

    @Override
    public int batchDelete(List<String> ids) {
        return sysAttachmentMapper.batchDelete(ids);
    }

    @Override
    public MessageMap uploadAttachment(File file,SysAttachment sysAttachment) {
        MessageMap messageMap = new MessageMap();
        String[] uploadResults = null;
        try {
            uploadResults = FastDFSManager.upload(file);
            if(uploadResults != null && uploadResults.length > 0){
                sysAttachment.setAttachmentKey(UUID.randomUUID().toString());
                sysAttachment.setAttachmentPath(uploadResults[1]);
                sysAttachment.setAttachmentSize((file.length() / 1024)+"");
                sysAttachment.setAttachmentSuffix(file.getName().substring(file.getName().indexOf(".") + 1));
                sysAttachment.setAttachmentName(file.getName());

                List<SysAttachment> sysAttachments = new ArrayList<>(1);
                sysAttachments.add(sysAttachment);
                sysAttachmentMapper.batchInsert(sysAttachments) ;
            }else{
                messageMap.setFlag(MessageMap.INFOR_WARNING);
                messageMap.setMessage("上传文件到文件服务器失败");
            }
        } catch (IOException e) {
            messageMap.setFlag(MessageMap.INFOR_WARNING);
            messageMap.setMessage("上传文件到文件服务器失败");
        } catch (MyException e) {
            messageMap.setFlag(MessageMap.INFOR_WARNING);
            messageMap.setMessage("上传文件到文件服务器失败");
        }finally {
            FileUtil.deleteOnExit(file);
        }
        return messageMap;
    }

    @Override
    public MessageMap uploadAttachment(byte[] file_buff, SysAttachment sysAttachment) {
        MessageMap messageMap = new MessageMap();
        String[] uploadResults = null;
        try {
            uploadResults = FastDFSManager.upload(file_buff,sysAttachment.getAttachmentName());
            if(uploadResults != null && uploadResults.length > 0){
                sysAttachment.setAttachmentKey(UUID.randomUUID().toString());
                sysAttachment.setAttachmentPath(uploadResults[1]);
                sysAttachment.setAttachmentSize((file_buff.length/1024)+"");

                List<SysAttachment> sysAttachments = new ArrayList<>(1);
                sysAttachments.add(sysAttachment);
                sysAttachmentMapper.batchInsert(sysAttachments) ;
            }else{
                messageMap.setFlag(MessageMap.INFOR_WARNING);
                messageMap.setMessage("上传文件到文件服务器失败");
            }
        } catch (IOException e) {
            messageMap.setFlag(MessageMap.INFOR_WARNING);
            messageMap.setMessage("上传文件到文件服务器失败");
        } catch (MyException e) {
            messageMap.setFlag(MessageMap.INFOR_WARNING);
            messageMap.setMessage("上传文件到文件服务器失败");
        }
        return messageMap;
    }

    @Override
    public MessageMap batchUploadAttachment(List<File> files,List<SysAttachment> sysAttachments) {
        return null;
    }

    @Override
    public MessageMap downloadAttachment(SysAttachmentSearch sysAttachmentSearch) {
        MessageMap messageMap = new MessageMap();
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
            messageMap.setFlag(MessageMap.INFOR_WARNING);
            messageMap.setMessage("服务器没有该文件上传记录");
        }
      return messageMap;
    }

    @Override
    public MessageMap deleteAttachment(SysAttachmentSearch search) {
        MessageMap messageMap = new MessageMap();
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
            messageMap.setFlag(MessageMap.INFOR_WARNING);
            messageMap.setMessage("删除文件失败，服务器没有该文件上传记录");
        }
        return messageMap;
    }
}
