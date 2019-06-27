package com.wust.springcloud.common.util;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by WST on 2019/2/22.
 */
public class MyStringUtils extends StringUtils{
    public static String null2String(Object obj) {
        if(obj instanceof Double){
            return String.valueOf(obj);
        }
        if (null == obj || "".equals(((String) obj).trim()) || "null".equals(((String) obj).trim()) || "undefined".equals(obj)) {
            return "";
        }
        return obj.toString().trim();
    }
}
