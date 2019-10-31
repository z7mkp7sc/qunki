package com.wjj.wm.mapper;

import com.wjj.wm.pojo.QkUserOrganization;

public interface QkUserOrganizationMapper {
    int insert(QkUserOrganization record);

    int insertSelective(QkUserOrganization record);

    Integer selectByOid(String organizationId);
}