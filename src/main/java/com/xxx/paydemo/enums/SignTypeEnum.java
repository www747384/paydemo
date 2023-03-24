package com.xxx.paydemo.enums;
/**
 * CopyRight(c) 2000-2023 YCKJ. All Rights Reserved
 */

/**
 * 描述:
 *
 * @author Albert
 * @date 2023/2/13 15:07
 */
public enum SignTypeEnum {

    MD5("MD5", "MD5算法"),
    HMAC("HMAC", "HMAC-SHA256签名");

    private String code;

    private String desc;

    SignTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
