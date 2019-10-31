package com.wjj.wm.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wjj.wm.mapper.CsdnBlogMapper;
import com.wjj.wm.pojo.CsdnBlog;
import com.wjj.wm.service.CsdnBlogService;
import com.wjj.wm.common.utils.CsdnBlogPageProcessor;
import com.wjj.wm.common.utils.SnowFlakeIdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Spider;

import java.util.List;
import java.util.Random;

/**
 * @ClassName CsdnBlogServiceImpl
 * @Description TODO
 * @Author weng_jun_ji_
 * @Date 2019/9/23 15:27
 * @Vervsion 1.0
 */
@Service
@Slf4j
public class CsdnBlogServiceImpl implements CsdnBlogService {

    @Autowired
    private CsdnBlogMapper csdnBlogMapper;

    @Autowired
    private SnowFlakeIdWorker idWorker;

    @Override
    public void crawler() {
        CsdnBlogPageProcessor processor = new CsdnBlogPageProcessor();
        Spider.create(processor).addUrl("https://blog.hellobi.com/hot/weekly?page=1").thread(5).run();
        List<CsdnBlog> blogList = processor.getBlogList();
        log.info("一共爬取了{}篇博客...", blogList.size());
        blogList.forEach(blog -> {
            blog.setId(String.valueOf(idWorker.nextId()));
            csdnBlogMapper.insert(blog);
        });
        log.info("插入完毕");
    }

    @Override
    public int insertTest() {
        CsdnBlog blog = new CsdnBlog();
        blog.setId(String.valueOf(idWorker.nextId()));
        blog.setUrl("urlurlurl");
        blog.setTitle("标题");
        blog.setAuthor("作者" + System.currentTimeMillis());
        blog.setBloghomeurl("123445");
        blog.setCommentnum(22);
        blog.setRecommendnum(34);
        blog.setReadnum(124);
        blog.setContent("内容内容" + (new Random().nextInt()));
        blog.setPublishtime("20190923");
        return csdnBlogMapper.insert(blog);
    }

    @Override
    public Page<CsdnBlog> listAll() {
        return PageHelper.startPage(1, 3).doSelectPage(() -> csdnBlogMapper.listAll());
    }
}
