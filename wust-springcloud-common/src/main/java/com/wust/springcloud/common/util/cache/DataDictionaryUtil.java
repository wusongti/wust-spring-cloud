package com.wust.springcloud.common.util.cache;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wust.springcloud.common.entity.sys.datasource.SysDataSourceList;
import com.wust.springcloud.common.entity.sys.lookup.SysLookupList;
import com.wust.springcloud.common.enums.RedisKeyEnum;
import com.wust.springcloud.common.util.SpringContextHolder;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * Function:数据字典工具
 * Reason:可以根据key从缓存获取name，也可以根据name从缓存获取key。
 *        在修改改工具前请理解原理。
 * Date:2018/7/4
 *
 * @author wusongti@163.com
 */
@Component
public class DataDictionaryUtil {

    private static SpringRedisTools springRedisTools = null;

    private DataDictionaryUtil(){

    }



    public static void setSpringRedisTools(SpringRedisTools springRedisTools){
        DataDictionaryUtil.springRedisTools = springRedisTools;
    }












    /**************************************Lookup S****************************************/

    /**
     * 根据parentCode获取子列表
     * @param parentCode
     * @return
     */
    public static List<SysLookupList> getLookupListByParentCode(String companyId,String parentCode) {
        List<SysLookupList> sysLookupLists = null;
        String key = String.format(RedisKeyEnum.REDIS_TABLE_KEY_GROUP_GROUP_LOOKUP_BY_PID.getStringValue(),companyId);
        Object obj = getRedisSpringBean().getByKey(key);
        if(obj != null){
            JSONObject jsonObject = JSONObject.parseObject(obj+"");
            String jsonArrayStr = jsonObject.getString(parentCode);
            sysLookupLists = JSONArray.parseArray(jsonArrayStr ,SysLookupList.class);
            return sysLookupLists;
        }
        return null;
    }

    /**
     * 根据code获取name
     * @param codeValue
     * @return
     */
    public static String getLookupNameByCode(String companyId,String codeValue) {
        String key = String.format(RedisKeyEnum.REDIS_TABLE_KEY_GROUP_LOOKUP_NAME_BY_CODE.getStringValue(),companyId);
        Object obj = getRedisSpringBean().getByKey(key);
        if(obj != null){
            JSONObject jsonObject = JSONObject.parseObject(obj.toString());
            String name = jsonObject.getString(codeValue);
            return name;
        }
        return null;
    }

    /**
     * 根据rootCode和name获取code
     * @param rootCode
     * @param name
     * @return
     */
    public static String getLookupCodeByRootCodeAndName(String companyId,String rootCode, String name) {
        String k = String.format(RedisKeyEnum.REDIS_TABLE_KEY_GROUP_LOOKUP_CODE_BY_ROOT_CODE_AND_NAME.getStringValue(),companyId);
        Object obj = getRedisSpringBean().getByKey(k);
        if(obj != null){
            JSONObject jsonObject = JSONObject.parseObject(obj.toString());
            String key = rootCode + "." + name;
            String code = jsonObject.getString(key);
            return code;
        }
        return null;
    }
    /**************************************Lookup E****************************************/


    /**
     * 根据dataSourceId获取数据源
     * @param dataSourceId
     * @return
     */
    public static SysDataSourceList getDataSourceById(String dataSourceId){
        Object obj = getRedisSpringBean().getByKey(RedisKeyEnum.REDIS_TABLE_KEY_DATA_SOURCE.name());
        if(obj != null){
            JSONObject jsonObject = JSONObject.parseObject(obj.toString());
            SysDataSourceList sysDataSourceList = jsonObject.getObject(dataSourceId,SysDataSourceList.class);
            return sysDataSourceList;
        }
        return null;
    }



    private static SpringRedisTools getRedisSpringBean(){
        if(springRedisTools == null){
            springRedisTools = SpringContextHolder.getBean("springRedisTools");
        }
        return springRedisTools;
    }
}
