package com.xxx.paydemo.service.strategy.impl;
/**
 * CopyRight(c) 2000-2023 YCKJ. All Rights Reserved
 */

import com.xxx.paydemo.dto.resp.PayRespData;
import com.xxx.paydemo.dto.resp.RefundRespData;
import com.xxx.paydemo.entity.TWalletRecord;
import com.xxx.paydemo.enums.PayWayEnum;
import com.xxx.paydemo.factory.PayFactory;
import com.xxx.paydemo.service.strategy.IStrategyService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

/**
 * 描述:
 *
 * @author Albert
 * @date 2023/3/22 15:19
 */
@Service
public class AlipayServiceImpl implements IStrategyService, InitializingBean {

    @Override
    public PayRespData toPay(TWalletRecord walletRecord) {
        PayRespData payRespData = new PayRespData();
        String payUrl = "支付宝扫码页面";
        payRespData.setPayUrl(payUrl);
        return payRespData;
    }

    @Override
    public RefundRespData toRefund(TWalletRecord walletRecord) {
        RefundRespData refundRespData = new RefundRespData();
        refundRespData.setId(walletRecord.getId());
        refundRespData.setRefund_amount(walletRecord.getAmt());
        refundRespData.setFund_change("Y");
        return refundRespData;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        PayFactory.map.put(PayWayEnum.ALIPAY_QRCODE_PAY.getCode(), this);
    }

}
