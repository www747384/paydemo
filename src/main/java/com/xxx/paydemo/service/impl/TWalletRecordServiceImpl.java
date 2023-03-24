package com.xxx.paydemo.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.xxx.paydemo.dto.req.UnifiedPaymentDto;
import com.xxx.paydemo.dto.req.UnifiedRefundDto;
import com.xxx.paydemo.dto.resp.PayRespData;
import com.xxx.paydemo.dto.resp.RefundRespData;
import com.xxx.paydemo.dto.resp.WalletRespDto;
import com.xxx.paydemo.entity.TPayChannel;
import com.xxx.paydemo.entity.TWallet;
import com.xxx.paydemo.entity.TWalletRecord;
import com.xxx.paydemo.enums.OperatorEnum;
import com.xxx.paydemo.enums.PayWayEnum;
import com.xxx.paydemo.enums.SignTypeEnum;
import com.xxx.paydemo.enums.StatusEnum;
import com.xxx.paydemo.exception.BizException;
import com.xxx.paydemo.factory.PayFactory;
import com.xxx.paydemo.mapper.TWalletMapper;
import com.xxx.paydemo.mapper.TWalletRecordMapper;
import com.xxx.paydemo.service.ITWalletRecordService;
import com.xxx.paydemo.service.strategy.IStrategyService;
import com.xxx.paydemo.utils.SignUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 张三
 * @since 2023-03-20
 */
@Service
@Slf4j
public class TWalletRecordServiceImpl implements ITWalletRecordService {

    @Autowired
    private TWalletRecordMapper walletRecordMapper;

    @Autowired
    private TWalletMapper walletMapper;

    @Override
    public void createWalletRecord(TWalletRecord walletRecord) {
        walletRecordMapper.insert(walletRecord);
    }

    @Override
    @Transactional
    public synchronized PayRespData doPay(UnifiedPaymentDto unifiedPaymentDto) throws BizException {

        QueryWrapper<TWallet> walletQueryWrapper = new QueryWrapper();
        walletQueryWrapper.eq("wallet_id", unifiedPaymentDto.getWalletId());
        TWallet tWallet = walletMapper.selectOne(walletQueryWrapper);

        if (ObjectUtils.isEmpty(tWallet)) {
            throw new BizException("钱包不存在");
        }

        String sign = unifiedPaymentDto.getSign();

        try {
            JSONObject object = (JSONObject) JSON.toJSON(unifiedPaymentDto);
            String newSign = SignUtil.generateSign(object, tWallet.getWalletId(), SignTypeEnum.MD5.getCode());
            if (!sign.equals(newSign)) {
                throw new BizException("请求非法");
            }
        } catch (Exception e) {
            log.error("Exception", e);
            throw new BizException("验签失败");
        }

        if (!StatusEnum.ENABLE.getCode().equals(tWallet.getStatus())) {
            throw new BizException("钱包不能使用");
        }

        if (tWallet.getAccount().compareTo(unifiedPaymentDto.getAmt()) == -1) {
            throw new BizException("钱包余额不足");
        }

        TWalletRecord walletRecord = new TWalletRecord();
        walletRecord.setOperator(unifiedPaymentDto.getOperator());
        walletRecord.setWalletId(unifiedPaymentDto.getWalletId());
        walletRecord.setAmt(unifiedPaymentDto.getAmt());
        walletRecord.setUserId(unifiedPaymentDto.getUserId());
        walletRecord.setCreateTime(LocalDateTime.now());
        walletRecord.setOAccount(tWallet.getAccount());
        walletRecord.setNAccount(tWallet.getAccount().subtract(unifiedPaymentDto.getAmt()));
        walletRecord.setPaychannel(unifiedPaymentDto.getPaychannel());

        walletRecordMapper.insert(walletRecord);

        IStrategyService iStrategyPayService = PayFactory.getByPayWay(unifiedPaymentDto.getPaychannel());
        if (ObjectUtils.isEmpty(iStrategyPayService)) {
            throw new BizException("暂不支持此支付方式");
        }

        PayRespData payRespData = iStrategyPayService.toPay(walletRecord);

        LambdaUpdateWrapper<TWallet> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(TWallet::getAccount, walletRecord.getNAccount());
        updateWrapper.set(TWallet::getUpdateTime, LocalDateTime.now());
        updateWrapper.eq(TWallet::getWalletId, unifiedPaymentDto.getWalletId());
        walletMapper.update(tWallet, updateWrapper);

        return payRespData;
    }

    @Override
    @Transactional
    public synchronized RefundRespData doRefund(UnifiedRefundDto unifiedRefundDto) throws BizException {

        TWalletRecord walletRecord = walletRecordMapper.selectById(unifiedRefundDto.getRecordId());
        if (ObjectUtils.isEmpty(walletRecord)) {
            throw new BizException("该笔交易不存在");
        }

        QueryWrapper<TWallet> walletQueryWrapper = new QueryWrapper();
        walletQueryWrapper.eq("wallet_id", unifiedRefundDto.getWalletId());
        TWallet tWallet = walletMapper.selectOne(walletQueryWrapper);
        if (ObjectUtils.isEmpty(tWallet)) {
            throw new BizException("钱包不存在");
        }

        walletRecord.setId(null);
        walletRecord.setAmt(unifiedRefundDto.getAmt());
        walletRecord.setOperator(unifiedRefundDto.getOperator());
        walletRecord.setOAccount(tWallet.getAccount());
        walletRecord.setNAccount(tWallet.getAccount().add(unifiedRefundDto.getAmt()));
        walletRecord.setPaychannel(unifiedRefundDto.getPaychannel());
        walletRecord.setCreateTime(LocalDateTime.now());
        walletRecordMapper.insert(walletRecord);

        LambdaUpdateWrapper<TWallet> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(TWallet::getAccount, walletRecord.getNAccount());
        updateWrapper.set(TWallet::getUpdateTime, LocalDateTime.now());
        updateWrapper.eq(TWallet::getWalletId, unifiedRefundDto.getWalletId());
        walletMapper.update(tWallet, updateWrapper);

        IStrategyService iStrategyPayService = PayFactory.getByPayWay(unifiedRefundDto.getPaychannel());
        if (ObjectUtils.isEmpty(iStrategyPayService)) {
            throw new BizException("暂不支持此支付方式");
        }
        RefundRespData refundRespData = iStrategyPayService.toRefund(walletRecord);

        return refundRespData;

    }

    @Override
    public List<TWalletRecord> selectRecordById(Long id) throws BizException {

        QueryWrapper<TWallet> walletQueryWrapper = new QueryWrapper();

        walletQueryWrapper.eq("wallet_id", id);
        TWallet tWallet = walletMapper.selectOne(walletQueryWrapper);
        if (ObjectUtils.isEmpty(tWallet)) {
            throw new BizException("钱包不存在");
        }

        QueryWrapper<TWalletRecord> queryWrapper = new QueryWrapper();
        queryWrapper.eq("wallet_id", id);//tom_age必须是数据库中的字段
        return walletRecordMapper.selectList(queryWrapper);

    }

}
