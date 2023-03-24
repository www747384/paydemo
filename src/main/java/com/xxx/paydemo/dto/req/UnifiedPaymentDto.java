package com.xxx.paydemo.dto.req;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class UnifiedPaymentDto {

    @NotEmpty(message = "支付渠道不能为空")
    private String paychannel;

    @NotEmpty(message = "用户id不能为空")
    private String userId;

    @NotEmpty(message = "钱包id不能为空")
    private String walletId;

    @NotEmpty(message = "支出支付符号不能为空")
    private String operator;

    @NotNull(message = "金额不能为空")
    private BigDecimal amt;

    @NotEmpty(message = "sign不能为空")
    private String sign;

    //支付完成后跳转的页面
    private String returnUrl;

    //接收支付结果的地址
    private String notifyUrl;

}
