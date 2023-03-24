package com.xxx.paydemo.utils;
/**
 * CopyRight(c) 2000-2023 YCKJ. All Rights Reserved
 */

import com.xxx.paydemo.enums.SignTypeEnum;
import org.springframework.util.ObjectUtils;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

/**
 * 描述:
 *
 * @author Albert
 * @date 2023/2/13 11:54
 */
public class SignUtil {

    public static String generateSign(Map<String, Object> data, String key, String signType) throws Exception {

        //1.对map进行排序 字典序
        Set<String> keySet = data.keySet();

        String[] keyArray = keySet.toArray(new String[keySet.size()]);

        Arrays.sort(keyArray);

        //2.拼接字符串 去掉值为空的

        StringBuffer sb = new StringBuffer();

        for (String k : keyArray) {
            // sign不参与签名
            if (k.equals("sign")) {
                continue;
            }

            //如果map中的值不为空 就拼接为 k1=v1&k2=v2
            if (ObjectUtils.isEmpty(data.get(k))) {
                continue;
            }

            // 拼接为 k1=v1&k2=v2
            sb.append(k).append("=").append(data.get(k).toString().trim()).append("&");

        }
        sb.append("key=" + key);

        //3.对字符串进行加密
        String sign = "";
        if (SignTypeEnum.MD5.getCode().equals(signType)) {
            sign = MD5(sb.toString()).toUpperCase();
        }

        return sign;
    }

    public static String MD5(String data) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] array = md.digest(data.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (byte item : array) {
            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString().toUpperCase();
    }
}
