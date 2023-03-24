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

        if (data.getString("fundChange").equals("Y")) {
            System.out.println(data.getString("refundAmount") + "退款成功");
        } else {
            System.out.println(str);
        }

    }

    @Test
    void test4() {

        String url = "http://127.0.0.1:8081/create/payment?id=" + 1;

        String str = restTemplate.getForObject(url, String.class);

        JSONObject object = JSON.parseObject(str);
        JSONArray data = object.getJSONArray("data");

        System.out.println(data);

    }

    @Test
    void contextLoads1() {
        TWalletRecord walletRecord = walletRecordMapper.selectById(new BigDecimal(1));
        System.out.println(walletRecord.toString());
    }

    @Test
    void contextLoads() {
        //创建一个代码生成器
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/paydemo?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8", "root", "root")
                //全局配置(GlobalConfig)
                .globalConfig(builder -> {
                    builder.author("张三") // 设置作者，可以写自己名字
                            .enableSwagger() // 开启 swagger 模式，这个是接口文档生成器，如果开启的话，就还需要导入swagger依赖
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("D://"); // 指定输出目录，一般指定到java目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.xxx.payrecord") // 设置父包名
                            .moduleName("system") // 设置父包模块名，这里一般不设置
                            .pathInfo(Collections.singletonMap(OutputFile.xml, "D://")); // 设置mapperXml生成路径，这里是Mapper配置文件的路径，建议使用绝对路径
                })
                //策略配置(StrategyConfig)
                .strategyConfig(builder -> {
                    builder.addInclude("t_wallet")
                            .addInclude("t_wallet_record")
                            .addInclude("t_user")
                            .addInclude("t_pay_channel");
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute(); //执行以上配置
    }

}
