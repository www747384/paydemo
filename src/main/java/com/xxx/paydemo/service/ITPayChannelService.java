package com.xxx.paydemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxx.paydemo.entity.TPayChannel;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 张三
 * @since 2023-03-20
 */
public interface ITPayChannelService{

    TPayChannel queryByCode(String channel);
}
