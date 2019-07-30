package com.wust.springcloud.common.util;

import com.wust.springcloud.common.exception.BusinessException;
import com.wust.springcloud.common.util.cache.SpringRedisTools;
import org.apache.commons.lang3.StringUtils;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 *
 * Function:
 * Reason:可在分布式并发环境下使用
 * Date:2018/8/6
 * @author wusongti@163.com
 */
public class CodeGenerator {

    private static final String CODE_TYPE_IMPORT_EXPORT_CODE = "importExportCode";
    private static final String CODE_TYPE_COMPANY_CODE = "companyCode";
    private static final String CODE_TYPE_DEPARTMENT_CODE = "departmentCode";
    private static final String CODE_TYPE_PROJECT_CODE = "projectCode";
    private static final String CODE_TYPE_ROLE_CODE = "roleCode";
    private static final String CODE_TYPE_USER_CODE = "userCode";

    private CodeGenerator() {
    }




    /**
     * 导入导出批次号
     * @return
     */
    public static String genImportExportCode(){
        int codeLength = 6;

        String batchNo = "";

        String dateStr = new SimpleDateFormat("yyyyMMdd").format(new Date());

        String key = CODE_TYPE_IMPORT_EXPORT_CODE + dateStr;

        long newValue = getNewValueByKey(key,1,1,TimeUnit.DAYS,"Import Export BatchNo");

        batchNo = dateStr + StringUtils.leftPad(newValue + "",codeLength,"0");

        return batchNo;
    }



    public static String genCompanyCode(){
        int codeLength = 3;

        String code = "";

        String key =  CODE_TYPE_COMPANY_CODE;

        long newValue = getNewValueByKey(key,1,1,TimeUnit.DAYS,"Company Code");

        code = StringUtils.leftPad(newValue + "",codeLength,"0");

        return code;
    }

    public static String genDetartmentCode(){
        int codeLength = 3;

        String code = "";

        String key =  CODE_TYPE_DEPARTMENT_CODE;

        long newValue = getNewValueByKey(key,1,1,TimeUnit.DAYS,"Department Code");

        code = StringUtils.leftPad(newValue + "",codeLength,"0");

        return code;
    }

    public static String genProjectCode(){
        int codeLength = 3;

        String code = "";

        String key =  CODE_TYPE_PROJECT_CODE;

        long newValue = getNewValueByKey(key,1,1,TimeUnit.DAYS,"Project Code");

        code = StringUtils.leftPad(newValue + "",codeLength,"0");

        return code;
    }


    public static String genRoleCode(){
        int codeLength = 3;

        String code = "";

        String key =  CODE_TYPE_ROLE_CODE;

        long newValue = getNewValueByKey(key,1,1,TimeUnit.DAYS,"Role Code");

        code = StringUtils.leftPad(newValue + "",codeLength,"0");

        return code;
    }

    public static String genUserCode(){
        int codeLength = 6;

        String userCode = "";

        String key =  CODE_TYPE_USER_CODE;

        long newValue = getNewValueByKey(key,1,1,TimeUnit.DAYS,"User Code");

        userCode = StringUtils.leftPad(newValue + "",codeLength,"0");

        return userCode;
    }


    /**
     * 原子自增方法，失败时默认重试5次
     * @param key redis的key
     * @param value 自增值
     * @param timeout 超时阀
     * @param timeUnit 超时阀单位
     * @param desc 用于打印日志的描述
     * @return 自增value值后的值
     */
    private static long getNewValueByKey(String key,long value,long timeout,TimeUnit timeUnit,String desc){
        SpringRedisTools springRedisTools = SpringContextHolder.getBean("springRedisTools");

        long newValue = 0;

        int tryCount = 5;

        do {
            newValue = springRedisTools.incrementForLong(key, value, timeout, timeUnit);
            if(newValue < 1 && tryCount > 0){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }
                tryCount --;
            }else{
                break;
            }
        }while (true);

        if(newValue < 1){
            throw new BusinessException("生成["+desc+"]失败");
        }

        return newValue;
    }
}
