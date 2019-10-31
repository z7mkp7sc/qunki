package com.wjj.wm.mapper;

import com.wjj.wm.pojo.QkUser;

public interface QkUserMapper {
    int insert(QkUser record);

    int insertSelective(QkUser record);

    QkUser selectByUsername(String username);

    QkUser selectById(String userId);
}