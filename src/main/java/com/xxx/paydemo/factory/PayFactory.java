package com.xxx.paydemo.factory;
/**
 * CopyRight(c) 2000-2023 YCKJ. All Rights Reserved
 */

import com.xxx.paydemo.service.strategy.IStrategyService;

import java.util.HashMap;

/**
 * 描述:
 *
 * @author Albert
 * @date 2023/2/20 19:14
 */
public class PayFactory {

    public static HashMap<String, IStrategyService> map = new HashMap<>();

    public static IStrategyService getByPayWay(String payWay) {
        return map.get(payWay);
    }
}
