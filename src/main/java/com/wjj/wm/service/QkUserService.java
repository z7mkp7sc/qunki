package com.wjj.wm.service;

import com.wjj.wm.pojo.QkUser;
import com.wjj.wm.vo.LoginVo;

public interface QkUserService {

    void register(QkUser user);

    LoginVo login(QkUser user);

    QkUser selectById(String userId);
}
