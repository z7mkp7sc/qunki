package com.wjj.wm.common.utils;

import com.wjj.wm.pojo.CsdnBlog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName CsdnBlogPageProcessor
 * @Description 爬虫工具类(指定网站)
 * @Author weng_jun_ji_
 * @Date 2019/9/23 15:57
 * @Vervsion 1.0
 */
public class CsdnBlogPageProcessor implements PageProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(CsdnBlogPageProcessor.class);

    //一共抓取到的文章数量
    public static int catchCount = 0;

    //抓取网站的相关配置,包括:编码/抓取间隔/重试次数等
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

    private List<CsdnBlog> blogList = new ArrayList<>();

    //抽取逻辑
    @Override
    public void process(Page page) {

        //1. 如果是博文列表页面 【入口页面】，将所有博文的详细页面的url放入target集合中。
        // 并且添加下一页的url放入target集合中。
        if (page.getUrl().regex("https://blog\\.hellobi\\.com/hot/weekly\\?page=\\d+").match()) {
            //目标链接
            page.addTargetRequests(page.getHtml().xpath("//h2[@class='title']/a").links().all());
            //下一页博文列表页链接
            page.addTargetRequests(page.getHtml().xpath("//a[@rel='next']").links().all());
        }
        //2. 如果是博文详细页面
        else {
//            String content1 = page.getHtml().get();
            try {
                /*实例化BlogInfo，方便持久化存储。*/
                CsdnBlog blog = new CsdnBlog();
                //博文标题
                String title = page.getHtml().xpath("//h1[@class='clearfix']/a/text()").get();
                //博文url
                String url = page.getHtml().xpath("//h1[@class='clearfix']/a/@href").get();
                //博文作者
                String author = page.getHtml().xpath("//section[@class='sidebar']/div/div/a[@class='aw-user-name']/text()").get();
                //作者博客地址
                String blogHomeUrl = page.getHtml().xpath("//section[@class='sidebar']/div/div/a[@class='aw-user-name']/@href").get();
                //博文内容，这里只获取带html标签的内容，后续可再进一步处理
                String content = page.getHtml().xpath("//div[@class='message-content editor-style']").get();
                //推荐数(点赞数)
                String recommendNum = page.getHtml().xpath("//a[@class='agree']/b/text()").get();
                //评论数
                String commentNum = page.getHtml().xpath("//div[@class='aw-mod']/div/h2/text()").get().split("个")[0].trim();
                //阅读数（浏览数）
                String readNum = page.getHtml().xpath("//div[@class='row']/div/div/div/div/span/text()").get().split(":")[1].trim();
                //发布时间，发布时间需要处理，这一步获取原始信息
                String time = page.getHtml().xpath("//time[@class='time']/text()").get().split(":")[1].trim();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                Calendar cal = Calendar.getInstance();// 取当前日期。
                cal = Calendar.getInstance();
                String publishTime = null;
                Pattern p = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");
                Matcher m = p.matcher(time);
                //如果time是“YYYY-mm-dd”这种格式的，则不需要处理
                if (m.matches()) {
                    publishTime = time;
                } else if (time.contains("天")) { //如果time包含“天”，如1天前，
                    int days = Integer.parseInt(time.split("天")[0].trim());//则获取对应的天数
                    cal.add(Calendar.DAY_OF_MONTH, -days);// 取当前日期的前days天.
                    publishTime = df.format(cal.getTime());  //并将时间转换为“YYYY-mm-dd”这个格式
                } else {//time是其他格式，如几分钟前，几小时前，都为当日日期
                    publishTime = df.format(cal.getTime());
                }

                //对象赋值
                blog.setUrl(url);
                blog.setTitle(title);
                blog.setAuthor(author);
                blog.setBloghomeurl(blogHomeUrl);
                blog.setCommentnum(Integer.valueOf(commentNum));
                blog.setRecommendnum(Integer.valueOf(recommendNum));
                blog.setReadnum(Integer.valueOf(readNum));
                blog.setContent(content);
                blog.setPublishtime(publishTime);
                blogList.add(blog);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Site getSite() {
        return this.site;
    }

    public List<CsdnBlog> getBlogList() {
        return this.blogList;
    }
}
