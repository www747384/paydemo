package com.xxx.paydemo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.xxx.paydemo.dto.req.UnifiedPaymentDto;
import com.xxx.paydemo.dto.req.UnifiedRefundDto;
import com.xxx.paydemo.dto.req.WalletQueryDto;
import com.xxx.paydemo.entity.TWalletRecord;
import com.xxx.paydemo.enums.*;
import com.xxx.paydemo.mapper.TWalletRecordMapper;
import com.xxx.paydemo.utils.SignUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;

@SpringBootTest
class PaydemoApplicationTests {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TWalletRecordMapper walletRecordMapper;

    /**
     * 接口1测试
     */
    @Test
    void test1() {

        WalletQueryDto walletQueryDto = new WalletQueryDto();
        walletQueryDto.setWalletId("1");
        walletQueryDto.setUserId("1");
        String url = "http://127.0.0.1:8081/account/info";
        String str = restTemplate.postForObject(url, walletQueryDto, String.class);
        JSONObject object = JSON.parseObject(str);
        if (object.getString("code").equals(StatusEnum.ENABLE.getCode())) {
            JSONObject dataObject = object.getJSONObject("data");
            String account = dataObject.getString("account");
            String currency = dataObject.getString("currency");
            System.out.println("该账户余额为: " + CurrencyEnum.getSymByCode(currency) + account);
        } else {
            System.out.println(str);
        }

    }

    /**
     * 接口2测试
     */
    @Test
    void test2() {

        String url = "http://127.0.0.1:8081/create/payment";

        UnifiedPaymentDto paymentDto = new UnifiedPaymentDto();
        paymentDto.setOperator(OperatorEnum.SUBSTRACT.getCode());
        paymentDto.setAmt(new BigDecimal(100));
        paymentDto.setPaychannel(PayWayEnum.ALIPAY_QRCODE_PAY.getCode());
        paymentDto.setUserId("1");
        paymentDto.setWalletId("1");
        paymentDto.setReturnUrl("https://www.baidu.com");

        //生成签名
        try {
            JSONObject object = (JSONObject) JSON.toJSON(paymentDto);
            String sign = SignUtil.generateSign(object, "1", SignTypeEnum.MD5.getCode());
            paymentDto.setSign(sign);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String str = restTemplate.postForObject(url, paymentDto, String.class);
        JSONObject object = JSON.parseObject(str);

        if (object.getString("code").equals(StatusEnum.ENABLE.getCode())) {
            JSONObject dataObject = object.getJSONObject("data");
            String payUrl = dataObject.getString("payUrl");
            System.out.println(payUrl);
        } else {
            System.out.println(str);
        }

    }

    /**
     * 接口3测试
     */
    @Test
    void test3() {

        String url = "http://127.0.0.1:8081/refund/payment";

        UnifiedRefundDto refundDto = new UnifiedRefundDto();
        refundDto.setAmt(new BigDecimal(20));
        refundDto.setPaychannel(PayWayEnum.ALIPAY_QRCODE_PAY.getCode());
        refundDto.setUserId("1");
        refundDto.setWalletId("1");
        refundDto.setOperator(OperatorEnum.ADD.getCode());
        refundDto.setRecordId(1L);

        //生成签名
        try {
            JSONObject object = (JSONObject) JSON.toJSON(refundDto);
            String sign = SignUtil.generateSign(object, "1", SignTypeEnum.MD5.getCode());
            refundDto.setSign(sign);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String str = restTemplate.postForObject(url, refundDto, String.class);
        JSONObject object = JSON.parseObject(str);
        JSONObject data = object.getJSONObject("data");

        if (data.getString("fund_change").equals("Y")) {
            System.out.println(data.getString("refund_amount") + "退款成功");
        } else {
            System.out.println(str);
        }

    }

    /**
     * 接口4测试
     */
    @Test
    void test4() {

        String url = "http://127.0.0.1:8081/fund/record?id=" + 1;

        String str = restTemplate.getForObject(url, String.class);

        JSONObject object = JSON.parseObject(str);
        JSONArray data = object.getJSONArray("data");

        System.out.println(data);

    }

}
