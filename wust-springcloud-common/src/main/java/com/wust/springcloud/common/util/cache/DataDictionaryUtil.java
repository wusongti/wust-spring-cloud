package com.wust.springcloud.common.util.cache;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wust.springcloud.common.entity.sys.datasource.SysDataSource;
import com.wust.springcloud.common.entity.sys.datasource.SysDataSourceList;
import com.wust.springcloud.common.entity.sys.lookup.SysLookupList;
import com.wust.springcloud.common.enums.RedisKeyEnum;
import com.wust.springcloud.common.util.SpringContextHolder;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
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
    public static List<SysLookupList> getLookupListByParentCode(String lan,String parentCode) {
        List<SysLookupList> sysLookupLists = null;
        String key = String.format(RedisKeyEnum.REDIS_TABLE_KEY_GROUP_GROUP_LOOKUP_BY_PID.getStringValue(),lan);
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
    public static String getLookupNameByCode(String lan,String codeValue) {
        String key = String.format(RedisKeyEnum.REDIS_TABLE_KEY_GROUP_LOOKUP_NAME_BY_CODE.getStringValue(),lan);
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
    public static String getLookupCodeByRootCodeAndName(String lan,String rootCode, String name) {
        String k = String.format(RedisKeyEnum.REDIS_TABLE_KEY_GROUP_LOOKUP_CODE_BY_ROOT_CODE_AND_NAME.getStringValue(),lan);
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
    public static SysDataSource getDataSourceById(String dataSourceId){
        SysDataSource sysDataSource = new SysDataSource();
        Object obj = getRedisSpringBean().getByKey(RedisKeyEnum.REDIS_TABLE_KEY_DATA_SOURCE.name());
        if(obj != null){
            JSONObject jsonObject = (JSONObject)obj;
            sysDataSource = jsonObject.getObject(dataSourceId, SysDataSource.class);
        }
        return sysDataSource;
    }



    private static SpringRedisTools getRedisSpringBean(){
        if(springRedisTools == null){
            springRedisTools = SpringContextHolder.getBean("springRedisTools");
        }
        return springRedisTools;
    }
}
