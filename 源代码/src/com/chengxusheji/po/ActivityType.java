package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class ActivityType {
    /*活动类型id*/
    private Integer typeId;
    public Integer getTypeId(){
        return typeId;
    }
    public void setTypeId(Integer typeId){
        this.typeId = typeId;
    }

    /*活动类型名称*/
    @NotEmpty(message="活动类型名称不能为空")
    private String typeName;
    public String getTypeName() {
        return typeName;
    }
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonActivityType=new JSONObject(); 
		jsonActivityType.accumulate("typeId", this.getTypeId());
		jsonActivityType.accumulate("typeName", this.getTypeName());
		return jsonActivityType;
    }}