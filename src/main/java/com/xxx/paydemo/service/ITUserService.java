package com.xxx.paydemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxx.paydemo.entity.TUser;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 张三
 * @since 2023-03-20
 */
public interface ITUserService {
    TUser selectUser(String userId);
}
