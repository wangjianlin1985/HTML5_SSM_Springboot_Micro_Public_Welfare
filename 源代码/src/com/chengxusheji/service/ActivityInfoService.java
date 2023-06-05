package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.ActivityType;
import com.chengxusheji.po.ActivityInfo;

import com.chengxusheji.mapper.ActivityInfoMapper;
@Service
public class ActivityInfoService {

	@Resource ActivityInfoMapper activityInfoMapper;
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

    /*添加公益活动记录*/
    public void addActivityInfo(ActivityInfo activityInfo) throws Exception {
    	activityInfoMapper.addActivityInfo(activityInfo);
    }

    /*按照查询条件分页查询公益活动记录*/
    public ArrayList<ActivityInfo> queryActivityInfo(ActivityType typeObj,String title,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(null != typeObj && typeObj.getTypeId()!= null && typeObj.getTypeId()!= 0)  where += " and t_activityInfo.typeObj=" + typeObj.getTypeId();
    	if(!title.equals("")) where = where + " and t_activityInfo.title like '%" + title + "%'";
    	int startIndex = (currentPage-1) * this.rows;
    	return activityInfoMapper.queryActivityInfo(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<ActivityInfo> queryActivityInfo(ActivityType typeObj,String title) throws Exception  { 
     	String where = "where 1=1";
    	if(null != typeObj && typeObj.getTypeId()!= null && typeObj.getTypeId()!= 0)  where += " and t_activityInfo.typeObj=" + typeObj.getTypeId();
    	if(!title.equals("")) where = where + " and t_activityInfo.title like '%" + title + "%'";
    	return activityInfoMapper.queryActivityInfoList(where);
    }

    /*查询所有公益活动记录*/
    public ArrayList<ActivityInfo> queryAllActivityInfo()  throws Exception {
        return activityInfoMapper.queryActivityInfoList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(ActivityType typeObj,String title) throws Exception {
     	String where = "where 1=1";
    	if(null != typeObj && typeObj.getTypeId()!= null && typeObj.getTypeId()!= 0)  where += " and t_activityInfo.typeObj=" + typeObj.getTypeId();
    	if(!title.equals("")) where = where + " and t_activityInfo.title like '%" + title + "%'";
        recordNumber = activityInfoMapper.queryActivityInfoCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取公益活动记录*/
    public ActivityInfo getActivityInfo(int activityId) throws Exception  {
        ActivityInfo activityInfo = activityInfoMapper.getActivityInfo(activityId);
        return activityInfo;
    }

    /*更新公益活动记录*/
    public void updateActivityInfo(ActivityInfo activityInfo) throws Exception {
        activityInfoMapper.updateActivityInfo(activityInfo);
    }

    /*删除一条公益活动记录*/
    public void deleteActivityInfo (int activityId) throws Exception {
        activityInfoMapper.deleteActivityInfo(activityId);
    }

    /*删除多条公益活动信息*/
    public int deleteActivityInfos (String activityIds) throws Exception {
    	String _activityIds[] = activityIds.split(",");
    	for(String _activityId: _activityIds) {
    		activityInfoMapper.deleteActivityInfo(Integer.parseInt(_activityId));
    	}
    	return _activityIds.length;
    }
}
