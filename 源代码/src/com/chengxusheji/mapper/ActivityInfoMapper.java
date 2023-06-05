package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.ActivityInfo;

public interface ActivityInfoMapper {
	/*添加公益活动信息*/
	public void addActivityInfo(ActivityInfo activityInfo) throws Exception;

	/*按照查询条件分页查询公益活动记录*/
	public ArrayList<ActivityInfo> queryActivityInfo(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有公益活动记录*/
	public ArrayList<ActivityInfo> queryActivityInfoList(@Param("where") String where) throws Exception;

	/*按照查询条件的公益活动记录数*/
	public int queryActivityInfoCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条公益活动记录*/
	public ActivityInfo getActivityInfo(int activityId) throws Exception;

	/*更新公益活动记录*/
	public void updateActivityInfo(ActivityInfo activityInfo) throws Exception;

	/*删除公益活动记录*/
	public void deleteActivityInfo(int activityId) throws Exception;

}
