package com.wjj.wm.mapper;

import com.wjj.wm.pojo.QkUserRole;

public interface QkUserRoleMapper {
    int insert(QkUserRole record);

    int insertSelective(QkUserRole record);
}