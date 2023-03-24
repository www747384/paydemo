package com.xxx.paydemo.service;

import com.xxx.paydemo.dto.req.UnifiedPaymentDto;
import com.xxx.paydemo.dto.req.UnifiedRefundDto;
import com.xxx.paydemo.dto.resp.PayRespData;
import com.xxx.paydemo.dto.resp.RefundRespData;
import com.xxx.paydemo.dto.resp.WalletRespDto;
import com.xxx.paydemo.entity.TWalletRecord;
import com.xxx.paydemo.exception.BizException;

import java.util.List;

public interface ITWalletRecordService {

    void createWalletRecord(TWalletRecord walletRecord);

    PayRespData doPay(UnifiedPaymentDto unifiedPaymentDto) throws BizException;

    RefundRespData doRefund(UnifiedRefundDto unifiedRefundDto) throws BizException;

    List<TWalletRecord> selectRecordById(Long id) throws BizException;

}
