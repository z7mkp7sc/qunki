package com.wjj.wm.pojo;

import java.io.Serializable;

public class CsdnBlog implements Serializable{
    private String id;

    private String url;

    private String title;

    private String author;

    private Integer readnum;

    private Integer recommendnum;

    private String bloghomeurl;

    private Integer commentnum;

    private String publishtime;

    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author == null ? null : author.trim();
    }

    public Integer getReadnum() {
        return readnum;
    }

    public void setReadnum(Integer readnum) {
        this.readnum = readnum;
    }

    public Integer getRecommendnum() {
        return recommendnum;
    }

    public void setRecommendnum(Integer recommendnum) {
        this.recommendnum = recommendnum;
    }

    public String getBloghomeurl() {
        return bloghomeurl;
    }

    public void setBloghomeurl(String bloghomeurl) {
        this.bloghomeurl = bloghomeurl == null ? null : bloghomeurl.trim();
    }

    public Integer getCommentnum() {
        return commentnum;
    }

    public void setCommentnum(Integer commentnum) {
        this.commentnum = commentnum;
    }

    public String getPublishtime() {
        return publishtime;
    }

    public void setPublishtime(String publishtime) {
        this.publishtime = publishtime == null ? null : publishtime.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}