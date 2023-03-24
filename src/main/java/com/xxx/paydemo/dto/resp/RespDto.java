package com.xxx.paydemo.dto.resp;

import lombok.Data;

@Data
public class RespDto<T> {

    private int code = 0;

    private String desc = "请求成功";

    private T data;

}
