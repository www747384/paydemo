package com.xxx.paydemo.dto.resp;
/**
 * CopyRight(c) 2000-2023 YCKJ. All Rights Reserved
 */

import lombok.Data;

/**
 * 描述:
 *
 * @author Albert
 * @date 2023/2/17 18:14
 */
@Data
public class PayRespData {

    // 支付地址
    private String payUrl;

    // 支付中台的流水号
    private String trxNo;

    // 订单号
    private String orderId;

    // 支付方式
    private String payWay;

    // 签名算法
    private String sign;

    // 签名算法
    private String signType;

}
