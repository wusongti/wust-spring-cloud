package com.wust.springcloud.common.util;/**
 * Created by wust on 2017/8/24.
 */

import com.wust.springcloud.common.enums.ApplicationEnum;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.codec.binary.Base64;
import org.joda.time.DateTime;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.UUID;


/**
 *
 * Function:
 * Reason:
 * Date:2019/03/15
 * @author wusongti
 */
public  class JwtHelper {

    private JwtHelper(){}

    /**
     * 创建jwt
     * @param subject
     * @param minutes  分钟
     * @return
     * @throws Exception
     */
    public static String createJWT(String key,String subject, int minutes) throws Exception {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        SecretKey secretKey = generalKey(key);
        JwtBuilder builder = Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(now)       // 签发时间
                .setSubject(subject)    // 签名主题
                .signWith(signatureAlgorithm,secretKey);
        if (minutes >= 0) { // 设置过期时间
            Date exp =  new DateTime().plusMinutes(minutes).toDate();
            builder.setExpiration(exp);
        }

        String token =  builder.compact();
        token = Base64.encodeBase64String(RC4.encry_RC4_string(token, ApplicationEnum.RC4_TOKEN_KEY.getStringValue()).getBytes());
        return token;
    }

    /**
     * 解析jwt
     * @param token
     * @return Claims
     * @throws Exception
     */
    public static Claims parseJWT(String key,String token) throws Exception{
        String tokenStr = RC4.decry_RC4(new String(Base64.decodeBase64(token)), ApplicationEnum.RC4_TOKEN_KEY.getStringValue());
        SecretKey secretKey = generalKey(key);
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(tokenStr).getBody();
        return claims;
    }


    private static SecretKey generalKey(String key) throws UnsupportedEncodingException {
        byte[] encodedKey = Base64.decodeBase64(key);
        SecretKey secretKey = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return secretKey;
    }
}
