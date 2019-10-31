package com.wjj.wm.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName LoginVo
 * @Description TODO
 * @Author weng_jun_ji_
 * @Date 2019/9/29 11:43
 * @Vervsion 1.0
 */
@Data
@AllArgsConstructor
public class LoginVo implements Serializable{

    private String username;

    private String token;
}
