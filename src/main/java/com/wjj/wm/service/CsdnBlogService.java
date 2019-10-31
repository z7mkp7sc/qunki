package com.wjj.wm.service;

import com.github.pagehelper.Page;
import com.wjj.wm.pojo.CsdnBlog;

public interface CsdnBlogService {

    void crawler();

    int insertTest();

    Page<CsdnBlog> listAll();
}
