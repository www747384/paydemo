package com.xxx.paydemo.service;

import com.xxx.paydemo.entity.TWallet;


public interface ITWalletService {

    TWallet selectAccByWalletId(String walletId, String userId);

}
