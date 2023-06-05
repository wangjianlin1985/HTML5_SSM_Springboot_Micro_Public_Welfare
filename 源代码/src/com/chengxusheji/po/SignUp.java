package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class SignUp {
    /*报名id*/
    private Integer signId;
    public Integer getSignId(){
        return signId;
    }
    public void setSignId(Integer signId){
        this.signId = signId;
    }

    /*报名的活动*/
    private ActivityInfo activityObj;
    public ActivityInfo getActivityObj() {
        return activityObj;
    }
    public void setActivityObj(ActivityInfo activityObj) {
        this.activityObj = activityObj;
    }

    /*报名人*/
    private UserInfo userObj;
    public UserInfo getUserObj() {
        return userObj;
    }
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }

    /*报名宣誓*/
    @NotEmpty(message="报名宣誓不能为空")
    private String signUpVow;
    public String getSignUpVow() {
        return signUpVow;
    }
    public void setSignUpVow(String signUpVow) {
        this.signUpVow = signUpVow;
    }

    /*报名时间*/
    @NotEmpty(message="报名时间不能为空")
    private String signUpTime;
    public String getSignUpTime() {
        return signUpTime;
    }
    public void setSignUpTime(String signUpTime) {
        this.signUpTime = signUpTime;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonSignUp=new JSONObject(); 
		jsonSignUp.accumulate("signId", this.getSignId());
		jsonSignUp.accumulate("activityObj", this.getActivityObj().getTitle());
		jsonSignUp.accumulate("activityObjPri", this.getActivityObj().getActivityId());
		jsonSignUp.accumulate("userObj", this.getUserObj().getName());
		jsonSignUp.accumulate("userObjPri", this.getUserObj().getUser_name());
		jsonSignUp.accumulate("signUpVow", this.getSignUpVow());
		jsonSignUp.accumulate("signUpTime", this.getSignUpTime().length()>19?this.getSignUpTime().substring(0,19):this.getSignUpTime());
		return jsonSignUp;
    }}