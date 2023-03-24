package com.xxx.paydemo.service.strategy;
/**
 * CopyRight(c) 2000-2023 YCKJ. All Rights Reserved
 */

import com.xxx.paydemo.dto.resp.PayRespData;
import com.xxx.paydemo.dto.resp.RefundRespData;
import com.xxx.paydemo.entity.TWalletRecord;
import org.springframework.stereotype.Service;

/**
 * 描述:
 *
 * @author Albert
 * @date 2023/3/22 15:19
 */
public interface IStrategyService {

    PayRespData toPay(TWalletRecord walletRecord);

    RefundRespData toRefund(TWalletRecord walletRecord);

}
