package com.wjj.wm.controller;

import com.wjj.wm.common.annotation.PassToken;
import com.wjj.wm.common.annotation.UserLoginToken;
import com.wjj.wm.common.model.Result;
import com.wjj.wm.common.utils.ResultUtil;
import com.wjj.wm.pojo.QkUser;
import com.wjj.wm.service.QkUserService;
import com.wjj.wm.vo.LoginVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName QkUserController
 * @Description 用户控制器
 * @Author weng_jun_ji_
 * @Date 2019/9/26 18:15
 * @Vervsion 1.0
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class QkUserController {

    @Autowired
    private QkUserService qkUserService;

    @UserLoginToken
    @PostMapping("/register")
    public Result register(@RequestBody QkUser user) {
        try {
            qkUserService.register(user);
            return ResultUtil.ok();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResultUtil.error(e.getMessage());
        }
    }

    @PassToken
    @PostMapping("/login")
    public Result login(@RequestBody QkUser user) {
        try {
            LoginVo vo = qkUserService.login(user);
            return ResultUtil.ok(vo);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResultUtil.error(e.getMessage());
        }
    }
}
