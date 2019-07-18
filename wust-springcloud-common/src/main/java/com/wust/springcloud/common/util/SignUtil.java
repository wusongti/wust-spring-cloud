package com.wust.springcloud.common.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * 签名工具
 */
public class SignUtil {
    private SignUtil(){}

    private static Logger logger = LoggerFactory.getLogger(SignUtil.class);

    /** 加密密钥 */
    public final static String SECRET_KEY = "API_SIGN_TIMU_SECRET_KEY";

    /** 字符编码 */
    private final static String INPUT_CHARSET = "UTF-8";

    /** 超时时间：5分钟 */
    private final static int TIME_OUT = 5 * 60 * 1000;



    /**
     * 除去数组中的空值和签名参数
     * @param sArray 签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    public static Map<String, String> paraFilter(Map<String, String> sArray) {
        Map<String, String> result = new HashMap<>();
        if (sArray == null || sArray.size() <= 0) {
            return result;
        }
        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (value == null || value.equals("") || key.equalsIgnoreCase("sign")) {
                continue;
            }
            result.put(key, value);
        }
        return result;
    }

    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, String> params) {
        return createLinkString(params, false);
    }

    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * @param params 需要排序并参与字符拼接的参数组
     * @param encode 是否需要UrlEncode
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, String> params, boolean encode) {
        List<String> keys = new ArrayList<>(params.keySet());
        Collections.sort(keys);
        String result = "";
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            if (encode)
                value = urlEncode(value, INPUT_CHARSET);
            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                result = result + key + "=" + value;
            } else {
                result = result + key + "=" + value + "&";
            }
        }
        return result;
    }




    /**
     * URL转码
     * @param content
     * @param charset
     * @return
     */
    private static String urlEncode(String content, String charset) {
        try {
            return URLEncoder.encode(content, charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("指定的编码集不对,您目前指定的编码集是:" + charset);
        }
    }




    /**
     * 生成要请求的签名参数数组
     * @param paraMap 需要签名的参数
     * @return 要请求的签名参数数组
     */
    public static Map<String, String> sign(Map<String, String> paraMap) {
        // 时间戳
        paraMap.put("timestamp", String.valueOf(System.currentTimeMillis()));

        // 每次请求的唯一标识
        paraMap.put("nonce",(new Random().nextLong() + 1) + "");

        //除去数组中的空值和签名参数
        Map<String, String> paraMapTemp = paraFilter(paraMap);
        //把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        String paraStr = createLinkString(paraMapTemp);
        //生成签名结果
        String signStr = MD5.MD5(paraStr + SECRET_KEY);
        //签名结果加入请求提交参数组中
        paraMapTemp.put("sign", signStr);
        return paraMapTemp;
    }







    /**
     * 根据反馈回来的信息，生成签名结果
     * @param params 通知返回来的参数数组
     * @return 验证结果
     */
    public static boolean verify(Map<String, String> params) {
        String sign = "";
        if (params.get("sign") != null) {
            sign = params.get("sign");
        }else {
            logger.info("sign is null");
            return false;
        }

        String timestamp = "";
        if (params.get("timestamp") != null) {
            timestamp = params.get("timestamp");
        }else {
            return false;
        }

        //过滤空值、sign
        Map<String, String> sParaNew = paraFilter(params);

        //获取待签名字符串
        String preSignStr = createLinkString(sParaNew);

        //获得签名验证结果
        String mysign = MD5.MD5(preSignStr + SECRET_KEY);
        if (mysign.equals(sign)) {
            //是否超时
            long curr = System.currentTimeMillis();
            if ((curr - Long.valueOf(timestamp)) > TIME_OUT){
                logger.info("api is time out");
                return false;
            }
            return true;
        } else {
            return false;
        }
    }


    public static void main(String[] args) {
        Map<String,String> parMap = new HashMap<>();
        parMap.put("appId","a");
        parMap.put("companyId","-1");
        parMap.put("color","2");
        parMap.put("b","4");
        System.out.println(SignUtil.sign(parMap));



        Map<String,String> parMap1 = new HashMap<>();
        parMap1.put("companyId","-1");
        parMap1.put("b","4");
        parMap1.put("color","2");
        parMap1.put("appId","a");
        parMap1.put("sign","0a11c6f064a4db1740382eff2960d519");
        parMap1.put("nonce","2207764657701661416");
        parMap1.put("timestamp","1563428721850");
        System.out.println(SignUtil.verify(parMap1));
    }
}
