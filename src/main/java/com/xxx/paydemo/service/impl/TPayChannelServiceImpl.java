package com.xxx.paydemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xxx.paydemo.entity.TPayChannel;
import com.xxx.paydemo.mapper.TPayChannelMapper;
import com.xxx.paydemo.service.ITPayChannelService;
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
public class TPayChannelServiceImpl implements ITPayChannelService {

    @Autowired
    private TPayChannelMapper payChannelMapper;

    @Override
    public TPayChannel queryByCode(String payWay) {
        LambdaQueryWrapper<TPayChannel> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TPayChannel::getPayWay, payWay);
        return payChannelMapper.selectOne(queryWrapper);
    }
}
