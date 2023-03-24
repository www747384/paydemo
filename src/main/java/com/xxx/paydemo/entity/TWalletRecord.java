package com.xxx.paydemo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@TableName("t_wallet_record")
@Data
public class TWalletRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String userId;

    private String walletId;

    private BigDecimal oAccount;

    private BigDecimal nAccount;

    private String operator;

    private BigDecimal amt;

    private String paychannel;

    private LocalDateTime createTime;

    //支付完成后跳转的页面
    private String returnUrl;

    //接收支付结果的地址
    private String notifyUrl;

}
