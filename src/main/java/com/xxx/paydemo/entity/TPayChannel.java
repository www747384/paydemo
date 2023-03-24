package com.xxx.paydemo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
/**
 * <p>
 * 
 * </p>
 *
 * @author 张三
 * @since 2023-03-20
 */
@TableName("t_pay_channel")
@Data
public class TPayChannel implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String platformCode;

    private String payChannel;

    private String payWay;

    private String status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
