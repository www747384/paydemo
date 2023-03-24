package com.xxx.paydemo.enums;

public enum CurrencyEnum {

    CNY("CNY", "人民币", "￥"),
    USD("USD", "美元", "$");

    private String code;

    private String desc;

    private String sym;

    CurrencyEnum(String code, String desc, String sym) {
        this.code = code;
        this.desc = desc;
        this.sym = sym;
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

    public String getSym() {
        return sym;
    }

    public void setSym(String sym) {
        this.sym = sym;
    }

    public static String getDescByCode(String code) {
        for (CurrencyEnum currencyEnum : CurrencyEnum.values()) {
            if (currencyEnum.getCode().equals(code)) {
                return currencyEnum.getDesc();
            }
        }
        return null;
    }

    public static String getSymByCode(String code) {
        for (CurrencyEnum currencyEnum : CurrencyEnum.values()) {
            if (currencyEnum.getCode().equals(code)) {
                return currencyEnum.getSym();
            }
        }
        return null;
    }
}
