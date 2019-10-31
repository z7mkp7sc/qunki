package com.wjj.wm.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wjj.wm.common.annotation.PassToken;
import com.wjj.wm.common.model.Result;
import com.wjj.wm.common.utils.ResultUtil;
import com.wjj.wm.pojo.CsdnBlog;
import com.wjj.wm.service.CsdnBlogService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName CsdnBlogController
 * @Description 控制入口
 * @Author weng_jun_ji_
 * @Date 2019/9/23 11:48
 * @Vervsion 1.0
 */
@RestController
@RequestMapping("/csdn/blog")
@Slf4j
public class CsdnBlogController {

    @Autowired
    private CsdnBlogService csdnBlogService;

    @PassToken
    @GetMapping("/demo")
    public Result demo() {
//        int demo = csdnBlogService.insertTest();
//        return "插入了" + demo + "条记录";
        try {
            int p = 1/0;
            return ResultUtil.ok();
        }catch (Exception e){
            return ResultUtil.error(e.getMessage());
        }
    }

    @PassToken
    @GetMapping("/hellobi/crawler")
    public String crawler() {
        csdnBlogService.crawler();
        return "success";
    }

    @PassToken
    @GetMapping("/listAll")
    public PageInfo<CsdnBlog> listAll() {
        Page<CsdnBlog> page = csdnBlogService.listAll();
        List<CsdnBlog> result = page.getResult();
        return new PageInfo<>(result);
    }

}
