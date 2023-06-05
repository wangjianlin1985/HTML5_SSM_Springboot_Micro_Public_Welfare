package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class Donation {
    /*捐款id*/
    private Integer donationId;
    public Integer getDonationId(){
        return donationId;
    }
    public void setDonationId(Integer donationId){
        this.donationId = donationId;
    }

    /*捐款人*/
    private UserInfo userObj;
    public UserInfo getUserObj() {
        return userObj;
    }
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }

    /*捐款金额*/
    @NotNull(message="必须输入捐款金额")
    private Float donationMoney;
    public Float getDonationMoney() {
        return donationMoney;
    }
    public void setDonationMoney(Float donationMoney) {
        this.donationMoney = donationMoney;
    }

    /*捐款时间*/
    private String dunationTime;
    public String getDunationTime() {
        return dunationTime;
    }
    public void setDunationTime(String dunationTime) {
        this.dunationTime = dunationTime;
    }

    /*捐款备注*/
    private String dunationMemo;
    public String getDunationMemo() {
        return dunationMemo;
    }
    public void setDunationMemo(String dunationMemo) {
        this.dunationMemo = dunationMemo;
    }

    /*审核状态*/
    @NotEmpty(message="审核状态不能为空")
    private String sheHeState;
    public String getSheHeState() {
        return sheHeState;
    }
    public void setSheHeState(String sheHeState) {
        this.sheHeState = sheHeState;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonDonation=new JSONObject(); 
		jsonDonation.accumulate("donationId", this.getDonationId());
		jsonDonation.accumulate("userObj", this.getUserObj().getName());
		jsonDonation.accumulate("userObjPri", this.getUserObj().getUser_name());
		jsonDonation.accumulate("donationMoney", this.getDonationMoney());
		jsonDonation.accumulate("dunationTime", this.getDunationTime().length()>19?this.getDunationTime().substring(0,19):this.getDunationTime());
		jsonDonation.accumulate("dunationMemo", this.getDunationMemo());
		jsonDonation.accumulate("sheHeState", this.getSheHeState());
		return jsonDonation;
    }}