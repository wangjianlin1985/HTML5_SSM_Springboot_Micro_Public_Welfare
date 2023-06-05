package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class News {
    /*新闻id*/
    private Integer newsId;
    public Integer getNewsId(){
        return newsId;
    }
    public void setNewsId(Integer newsId){
        this.newsId = newsId;
    }

    /*新闻标题*/
    @NotEmpty(message="新闻标题不能为空")
    private String title;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    /*新闻分类*/
    @NotEmpty(message="新闻分类不能为空")
    private String newClass;
    public String getNewClass() {
        return newClass;
    }
    public void setNewClass(String newClass) {
        this.newClass = newClass;
    }

    /*新闻内容*/
    @NotEmpty(message="新闻内容不能为空")
    private String content;
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    /*发布时间*/
    @NotEmpty(message="发布时间不能为空")
    private String publishDate;
    public String getPublishDate() {
        return publishDate;
    }
    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonNews=new JSONObject(); 
		jsonNews.accumulate("newsId", this.getNewsId());
		jsonNews.accumulate("title", this.getTitle());
		jsonNews.accumulate("newClass", this.getNewClass());
		jsonNews.accumulate("content", this.getContent());
		jsonNews.accumulate("publishDate", this.getPublishDate().length()>19?this.getPublishDate().substring(0,19):this.getPublishDate());
		return jsonNews;
    }}