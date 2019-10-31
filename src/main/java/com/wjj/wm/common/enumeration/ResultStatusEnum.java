package com.wjj.wm.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * @ClassName ResultStatusEnum
 * @Description 常量枚举
 * @Author weng_jun_ji_
 * @Date 2019/9/29 11:53
 * @Vervsion 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
public enum ResultStatusEnum {

    SUCCESS(200, "请求成功"),
    FAILED(500, "请求失败");

    private int code;
    private String message;

}
