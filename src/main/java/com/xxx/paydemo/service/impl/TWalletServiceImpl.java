package com.xxx.paydemo.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xxx.paydemo.entity.TWallet;
import com.xxx.paydemo.mapper.TWalletMapper;
import com.xxx.paydemo.service.ITWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 张三
 * @since 2023-03-20
 */
@Service
public class TWalletServiceImpl implements ITWalletService {

    @Autowired
    private TWalletMapper walletMapper;

    @Override
    public TWallet selectAccByWalletId(String walletId, String userId) {
        LambdaQueryWrapper<TWallet> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(TWallet::getWalletId, walletId);
        lambdaQueryWrapper.eq(TWallet::getUserId, userId);
        TWallet tWallet = walletMapper.selectOne(lambdaQueryWrapper);
        return tWallet;
    }

}
