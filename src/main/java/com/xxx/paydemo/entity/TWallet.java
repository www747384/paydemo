package com.xxx.paydemo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author 张三
 * @since 2023-03-20
 */
@TableName("t_wallet")
@Data
public class TWallet implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userId;

    private String walletId;

    private BigDecimal account;

    private String currency;

    private String status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
