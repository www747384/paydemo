package com.xxx.paydemo.enums;
/**
 * CopyRight(c) 2000-2023 YCKJ. All Rights Reserved
 */

/**
 * 描述:
 *
 * @author Albert
 * @date 2023/2/13 10:27
 */
public enum PayWayEnum {

    ALIPAY_QRCODE_PAY("alipay.qrcode.pay", "支付宝扫码支付"),
    WECHAT_QRCODE_PAY("wechat.qrcode.pay", "微信扫码支付");

    private String code;

    private String desc;

    PayWayEnum(String code, String desc) {
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
