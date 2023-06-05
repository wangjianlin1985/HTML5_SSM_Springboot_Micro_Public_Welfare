package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.ActivityInfo;
import com.chengxusheji.po.UserInfo;
import com.chengxusheji.po.SignUp;

import com.chengxusheji.mapper.SignUpMapper;
@Service
public class SignUpService {

	@Resource SignUpMapper signUpMapper;
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

    /*添加活动报名记录*/
    public void addSignUp(SignUp signUp) throws Exception {
    	signUpMapper.addSignUp(signUp);
    }

    /*按照查询条件分页查询活动报名记录*/
    public ArrayList<SignUp> querySignUp(ActivityInfo activityObj,UserInfo userObj,String signUpTime,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(null != activityObj && activityObj.getActivityId()!= null && activityObj.getActivityId()!= 0)  where += " and t_signUp.activityObj=" + activityObj.getActivityId();
    	if(null != userObj &&  userObj.getUser_name() != null  && !userObj.getUser_name().equals(""))  where += " and t_signUp.userObj='" + userObj.getUser_name() + "'";
    	if(!signUpTime.equals("")) where = where + " and t_signUp.signUpTime like '%" + signUpTime + "%'";
    	int startIndex = (currentPage-1) * this.rows;
    	return signUpMapper.querySignUp(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<SignUp> querySignUp(ActivityInfo activityObj,UserInfo userObj,String signUpTime) throws Exception  { 
     	String where = "where 1=1";
    	if(null != activityObj && activityObj.getActivityId()!= null && activityObj.getActivityId()!= 0)  where += " and t_signUp.activityObj=" + activityObj.getActivityId();
    	if(null != userObj &&  userObj.getUser_name() != null && !userObj.getUser_name().equals(""))  where += " and t_signUp.userObj='" + userObj.getUser_name() + "'";
    	if(!signUpTime.equals("")) where = where + " and t_signUp.signUpTime like '%" + signUpTime + "%'";
    	return signUpMapper.querySignUpList(where);
    }

    /*查询所有活动报名记录*/
    public ArrayList<SignUp> queryAllSignUp()  throws Exception {
        return signUpMapper.querySignUpList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(ActivityInfo activityObj,UserInfo userObj,String signUpTime) throws Exception {
     	String where = "where 1=1";
    	if(null != activityObj && activityObj.getActivityId()!= null && activityObj.getActivityId()!= 0)  where += " and t_signUp.activityObj=" + activityObj.getActivityId();
    	if(null != userObj &&  userObj.getUser_name() != null && !userObj.getUser_name().equals(""))  where += " and t_signUp.userObj='" + userObj.getUser_name() + "'";
    	if(!signUpTime.equals("")) where = where + " and t_signUp.signUpTime like '%" + signUpTime + "%'";
        recordNumber = signUpMapper.querySignUpCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取活动报名记录*/
    public SignUp getSignUp(int signId) throws Exception  {
        SignUp signUp = signUpMapper.getSignUp(signId);
        return signUp;
    }

    /*更新活动报名记录*/
    public void updateSignUp(SignUp signUp) throws Exception {
        signUpMapper.updateSignUp(signUp);
    }

    /*删除一条活动报名记录*/
    public void deleteSignUp (int signId) throws Exception {
        signUpMapper.deleteSignUp(signId);
    }

    /*删除多条活动报名信息*/
    public int deleteSignUps (String signIds) throws Exception {
    	String _signIds[] = signIds.split(",");
    	for(String _signId: _signIds) {
    		signUpMapper.deleteSignUp(Integer.parseInt(_signId));
    	}
    	return _signIds.length;
    }
}
