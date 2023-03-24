package com.xxx.paydemo.controller;
/**
 * CopyRight(c) 2000-2023 YCKJ. All Rights Reserved
 */

import com.xxx.paydemo.dto.req.UnifiedPaymentDto;
import com.xxx.paydemo.dto.req.UnifiedRefundDto;
import com.xxx.paydemo.dto.req.WalletQueryDto;
import com.xxx.paydemo.dto.resp.PayRespData;
import com.xxx.paydemo.dto.resp.RefundRespData;
import com.xxx.paydemo.dto.resp.RespDto;
import com.xxx.paydemo.dto.resp.WalletRespDto;
import com.xxx.paydemo.entity.TUser;
import com.xxx.paydemo.entity.TWallet;
import com.xxx.paydemo.entity.TWalletRecord;
import com.xxx.paydemo.enums.PayWayEnum;
import com.xxx.paydemo.enums.SignTypeEnum;
import com.xxx.paydemo.enums.StatusEnum;
import com.xxx.paydemo.exception.BizException;
import com.xxx.paydemo.service.ITUserService;
import com.xxx.paydemo.service.ITWalletRecordService;
import com.xxx.paydemo.service.ITWalletService;
import com.xxx.paydemo.utils.SignUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

@RestController
@Slf4j
public class MyController {

    @Autowired
    private ITUserService userService;

    @Autowired
    private ITWalletService walletService;

    @Autowired
    private ITWalletRecordService walletRecordService;

    /**
     * 账户查询接口
     */
    @PostMapping("/account/info")
    public RespDto<WalletRespDto> queryAccount(@RequestBody WalletQueryDto walletQueryDto) {
        log.info("+++++++++查询账户信息++++++++++++");

        RespDto<WalletRespDto> respDto = new RespDto();

        TUser tUser = userService.selectUser(walletQueryDto.getUserId());
        if (ObjectUtils.isEmpty(tUser)) {
            respDto.setCode(-1);
            respDto.setDesc("用户不存在");
            return respDto;
        }

        TWallet tWallet = walletService.selectAccByWalletId(walletQueryDto.getWalletId(), walletQueryDto.getUserId());
        if (ObjectUtils.isEmpty(tWallet)) {
            respDto.setCode(-1);
            respDto.setDesc("该用户钱包不存在");
            return respDto;
        }
        if (!tWallet.getStatus().equals(StatusEnum.ENABLE.getCode())) {
            respDto.setCode(-1);
            respDto.setDesc("该用户钱包不可用");
            return respDto;
        }

        WalletRespDto walletResultDto = new WalletRespDto();
        walletResultDto.setUserId(tWallet.getUserId());
        walletResultDto.setWalletId(tWallet.getWalletId());
        walletResultDto.setAccount(tWallet.getAccount());
        walletResultDto.setCurrency(tWallet.getCurrency());

        respDto.setData(walletResultDto);

        return respDto;
    }

    /**
     * 支付接口
     */
    @PostMapping("/create/payment")
    @ResponseBody
    public RespDto<WalletRespDto> unifiedPayment(@RequestBody @Validated UnifiedPaymentDto paymentDto, BindingResult bindingResult) {

        log.info("#####接收到业务平台" + paymentDto.getAmt() + "元的支付请求，请求参数：{}#####" + paymentDto);

        RespDto respDto = new RespDto();
        log.info("==================" + bindingResult.hasErrors());
        if (bindingResult.hasErrors()) {
            String desc = bindingResult.getFieldError().getDefaultMessage();
            respDto.setCode(-1);
            respDto.setDesc(desc);
            return respDto;
        }

        try {
            PayRespData payRespData = walletRecordService.doPay(paymentDto);
            respDto.setData(payRespData);
        } catch (BizException e) {
            log.error("BizException", e);
            respDto.setCode(-1);
            respDto.setDesc(e.getMessage());
            return respDto;
        }

        return respDto;
    }

    @PostMapping("/refund/payment")
    @ResponseBody
    public RespDto<RefundRespData> refund(@RequestBody @Validated UnifiedRefundDto refundDto, BindingResult bindingResult) {

        log.info("#####接收到业务平台" + refundDto.getAmt() + "元的退款请求,请求参数:{}#####" + refundDto);

        RespDto respDto = new RespDto();
        log.info("==================" + bindingResult.hasErrors());
        if (bindingResult.hasErrors()) {
            String desc = bindingResult.getFieldError().getDefaultMessage();
            respDto.setCode(-1);
            respDto.setDesc(desc);
            return respDto;
        }

        try {
            RefundRespData refundRespData = walletRecordService.doRefund(refundDto);
            respDto.setData(refundRespData);
        } catch (BizException e) {
            log.error("BizException", e);
            respDto.setCode(-1);
            respDto.setDesc(e.getMessage());
            return respDto;
        }

        return respDto;
    }

    @GetMapping("/create/payment")
    public RespDto<List<TWalletRecord>> unifiedPayment(@RequestParam(value = "id") Long id) {

        RespDto respDto = new RespDto();

        try {
            List<TWalletRecord> walletRecords = walletRecords = walletRecordService.selectRecordById(id);
            respDto.setData(walletRecords);
        } catch (BizException e) {
            log.error("BizException", e);
            respDto.setCode(-1);
            respDto.setDesc(e.getMessage());
            return respDto;
        }

        return respDto;

    }


}
