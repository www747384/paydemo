package com.xxx.paydemo.bo;

import lombok.Data;

import java.math.BigDecimal;

public class PayResultBo {

    //支付中台流水
    private String trxNo;

    //支付方式
    private String payWay;

    //支付渠道
    private String payChannel;

    //第三方支付或银行返回的流水号
    private String respTrxNo;

    //状态
    private String status;

    //状态描述
    private String statusDesc;

    //支付成功时间
    private String paySuccessTime;

    //支付金额
    private BigDecimal totalAmt;

    private String payBank;

}
