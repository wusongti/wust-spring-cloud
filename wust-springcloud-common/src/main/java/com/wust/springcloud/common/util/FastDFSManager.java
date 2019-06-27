package com.wust.springcloud.common.util;


import org.apache.commons.io.FilenameUtils;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by WST on 2019/5/17.
 */
public class FastDFSManager {
    private static Logger logger = LoggerFactory.getLogger(FastDFSManager.class);

    private static TrackerClient trackerClient;
    private static TrackerServer trackerServer;
    private static StorageServer storageServer;
    private static StorageClient storageClient;

    private static final String[] paths = {"fdfs_client.conf","app/common/fastdfs/fdfs_client.conf"};

    static {
        try {
            String classPath = FastDFSManager.class.getClassLoader().getResource(paths[0]).getPath();
            ClientGlobal.init(classPath);
            trackerClient = new TrackerClient(ClientGlobal.g_tracker_group);
            trackerServer = trackerClient.getConnection();
            storageServer= trackerClient.getStoreStorage(trackerServer);
            storageClient = new StorageClient(trackerServer, storageServer);
        } catch (Exception e) {
            try {
                String classPath = FastDFSManager.class.getClassLoader().getResource(paths[1]).getPath();
                ClientGlobal.init(classPath);
                trackerClient = new TrackerClient(ClientGlobal.g_tracker_group);
                trackerServer = trackerClient.getConnection();
                storageServer = trackerClient.getStoreStorage(trackerServer);
                storageClient = new StorageClient(trackerServer, storageServer);
            }catch (Exception e1) {
                logger.error("初始化文件服务器失败：" + e1);
            }
        }
    }


    public static String[] upload(File file) throws IOException, MyException {
        String fileName = file.getName();
        InputStream inputStream = new FileInputStream(file);
        long fileLength = inputStream.available();
        NameValuePair[] metaList = new NameValuePair[1];
        metaList[0] = new NameValuePair("fileName", fileName);
        String[] uploadResults = storageClient.upload_file(getFileBuffer(inputStream, fileLength), FilenameUtils.getExtension(fileName), metaList);
        return uploadResults;
    }

    public static String[] upload(byte[] file_buff,String fileName) throws IOException, MyException {
        NameValuePair[] metaList = new NameValuePair[1];
        metaList[0] = new NameValuePair("fileName", fileName);
        String[] uploadResults = storageClient.upload_file(file_buff, FilenameUtils.getExtension(fileName), metaList);
        return uploadResults;
    }




    public static int delete(String fileUrl) throws IOException, MyException {
        return storageClient.delete_file("group1", fileUrl);
    }


    public static byte[] download(String fileUrl){
        byte[] group1s = null;
        try {
            group1s = storageClient.download_file("group1", fileUrl);
        }catch (Exception e) {
            logger.error("从FastDFS下载文件失败，fileUrl={}，e={}",fileUrl,e);
        }
        return group1s;
    }


    /**
     * 获取文件元数据
     * @param groupName
     * @param fileId
     * @return
     */
    public Map<String,String> getFileMetadata(String groupName, String fileId) {
        try {
            NameValuePair[] metaList = storageClient.get_metadata(groupName,fileId);
            if (metaList != null) {
                HashMap<String,String> map = new HashMap<String, String>();
                for (NameValuePair metaItem : metaList) {
                    map.put(metaItem.getName(),metaItem.getValue());
                }
                return map;
            }
        } catch (Exception e) {
            logger.error("从FastDFS获取元数据失败，groupName={}，fileId={},e={}",groupName,fileId,e);
        }
        return null;
    }


    private static byte[] getFileBuffer(InputStream inputStream, long fileLength) throws IOException {
        byte[] buffer = new byte[256 * 1024];
        byte[] fileBuffer = new byte[(int) fileLength];
        int count = 0;
        int length = 0;
        while ((length = inputStream.read(buffer)) != -1) {
            for (int i = 0; i < length; i++) {
                fileBuffer[count + i] = buffer[i];
            }
            count += length;
        }
        return fileBuffer;
    }
}
