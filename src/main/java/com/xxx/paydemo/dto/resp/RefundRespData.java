package com.xxx.paydemo.dto.resp;
/**
 * CopyRight(c) 2000-2023 YCKJ. All Rights Reserved
 */

import lombok.Data;

import java.math.BigDecimal;

/**
 * 描述:
 *
 * @author Albert
 * @date 2023/3/23 12:47
 */
@Data
public class RefundRespData {

    //退款订单号
    private Long id;

    //退款金额
    private BigDecimal refundAmount;

    //退款返回结果 Y 成功 N 失败
    private String fundChange = "N";

}
