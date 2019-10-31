package com.wjj.wm.mapper;

import com.wjj.wm.pojo.CsdnBlog;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CsdnBlogMapper {
    int deleteByPrimaryKey(String id);

    int insert(CsdnBlog record);

    int insertSelective(CsdnBlog record);

    CsdnBlog selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CsdnBlog record);

    int updateByPrimaryKeyWithBLOBs(CsdnBlog record);

    int updateByPrimaryKey(CsdnBlog record);

    List<CsdnBlog> listAll();
}