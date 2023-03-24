package com.xxx.paydemo.dto.req;

import lombok.Data;

@Data
public class WalletQueryDto {

    //用户id
    private String userId;

    //钱包id
    private String walletId;
}
