package com.xxx.paydemo.service.impl;

import com.xxx.paydemo.entity.TUser;
import com.xxx.paydemo.mapper.TUserMapper;
import com.xxx.paydemo.mapper.TWalletMapper;
import com.xxx.paydemo.service.ITUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TUserServiceImpl implements ITUserService {

    @Autowired
    private TUserMapper userMapper;

    @Override
    public TUser selectUser(String userId) {
        return userMapper.selectById(userId);
    }
}
