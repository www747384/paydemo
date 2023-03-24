package com.xxx.paydemo.dto.resp;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class WalletRespDto implements Serializable {

    private static final long serialVersionUID = 8683452581122898909L;

    private String userId;

    private String walletId;

    private BigDecimal account;

    //币种
    private String currency;
}
