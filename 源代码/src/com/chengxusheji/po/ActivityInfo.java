package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class ActivityInfo {
    /*活动id*/
    private Integer activityId;
    public Integer getActivityId(){
        return activityId;
    }
    public void setActivityId(Integer activityId){
        this.activityId = activityId;
    }

    /*活动类型*/
    private ActivityType typeObj;
    public ActivityType getTypeObj() {
        return typeObj;
    }
    public void setTypeObj(ActivityType typeObj) {
        this.typeObj = typeObj;
    }

    /*活动主题*/
    @NotEmpty(message="活动主题不能为空")
    private String title;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    /*活动图片*/
    private String activityPhoto;
    public String getActivityPhoto() {
        return activityPhoto;
    }
    public void setActivityPhoto(String activityPhoto) {
        this.activityPhoto = activityPhoto;
    }

    /*活动内容*/
    @NotEmpty(message="活动内容不能为空")
    private String content;
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    /*活动时间*/
    @NotEmpty(message="活动时间不能为空")
    private String activityTime;
    public String getActivityTime() {
        return activityTime;
    }
    public void setActivityTime(String activityTime) {
        this.activityTime = activityTime;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonActivityInfo=new JSONObject(); 
		jsonActivityInfo.accumulate("activityId", this.getActivityId());
		jsonActivityInfo.accumulate("typeObj", this.getTypeObj().getTypeName());
		jsonActivityInfo.accumulate("typeObjPri", this.getTypeObj().getTypeId());
		jsonActivityInfo.accumulate("title", this.getTitle());
		jsonActivityInfo.accumulate("activityPhoto", this.getActivityPhoto());
		jsonActivityInfo.accumulate("content", this.getContent());
		jsonActivityInfo.accumulate("activityTime", this.getActivityTime());
		return jsonActivityInfo;
    }}