package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.ActivityType;

import com.chengxusheji.mapper.ActivityTypeMapper;
@Service
public class ActivityTypeService {

	@Resource ActivityTypeMapper activityTypeMapper;
    /*每页显示记录数目*/
    private int rows = 10;;
    public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}

    /*保存查询后总的页数*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*保存查询到的总记录数*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*添加活动类型记录*/
    public void addActivityType(ActivityType activityType) throws Exception {
    	activityTypeMapper.addActivityType(activityType);
    }

    /*按照查询条件分页查询活动类型记录*/
    public ArrayList<ActivityType> queryActivityType(int currentPage) throws Exception { 
     	String where = "where 1=1";
    	int startIndex = (currentPage-1) * this.rows;
    	return activityTypeMapper.queryActivityType(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<ActivityType> queryActivityType() throws Exception  { 
     	String where = "where 1=1";
    	return activityTypeMapper.queryActivityTypeList(where);
    }

    /*查询所有活动类型记录*/
    public ArrayList<ActivityType> queryAllActivityType()  throws Exception {
        return activityTypeMapper.queryActivityTypeList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber() throws Exception {
     	String where = "where 1=1";
        recordNumber = activityTypeMapper.queryActivityTypeCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取活动类型记录*/
    public ActivityType getActivityType(int typeId) throws Exception  {
        ActivityType activityType = activityTypeMapper.getActivityType(typeId);
        return activityType;
    }

    /*更新活动类型记录*/
    public void updateActivityType(ActivityType activityType) throws Exception {
        activityTypeMapper.updateActivityType(activityType);
    }

    /*删除一条活动类型记录*/
    public void deleteActivityType (int typeId) throws Exception {
        activityTypeMapper.deleteActivityType(typeId);
    }

    /*删除多条活动类型信息*/
    public int deleteActivityTypes (String typeIds) throws Exception {
    	String _typeIds[] = typeIds.split(",");
    	for(String _typeId: _typeIds) {
    		activityTypeMapper.deleteActivityType(Integer.parseInt(_typeId));
    	}
    	return _typeIds.length;
    }
}
