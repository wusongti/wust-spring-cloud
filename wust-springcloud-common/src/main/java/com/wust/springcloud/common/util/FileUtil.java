package com.wust.springcloud.common.util;

import com.wust.springcloud.common.exception.BusinessException;
import org.apache.commons.collections.CollectionUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by WST on 2019/5/21.
 */
public class FileUtil {
    private FileUtil(){}

    public static File createTempFile(String name,String suffix){
        try {
            File file = File.createTempFile(name, suffix);
            return file;
        } catch (IOException e) {
            throw new BusinessException("创建临时文件失败，"+e);
        }
    }

    public static void deleteOnExit(File file){
        file.deleteOnExit();
    }

    public static File createTempLogFile(String name,String suffix,String content){
        FileOutputStream os = null;
        File tempFile = null;
        try {
            tempFile = createTempFile( name, suffix);
            os = new FileOutputStream(tempFile, true);
            os.write((content + "\n").getBytes());
        } catch (Exception e) {
            throw new BusinessException("创建日志文件失败" + e);
        } finally {
            if (os != null) {
                try {
                    os.flush();
                    os.close();
                } catch (IOException e) {
                }
            }
        }
        return tempFile;
    }
}
