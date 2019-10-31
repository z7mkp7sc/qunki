package com.wjj.wm.mapper;

import com.wjj.wm.pojo.QkOperLog;

public interface QkOperLogMapper {
    int insert(QkOperLog record);

    int insertSelective(QkOperLog record);
}