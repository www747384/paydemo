package com.xxx.paydemo.enums;

public enum OperatorEnum {

    ADD("+", "入账"),
    SUBSTRACT("-", "支付");

    private String code;

    private String desc;

    OperatorEnum(String code, String desc) {
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
