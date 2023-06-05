package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.SignUp;

public interface SignUpMapper {
	/*添加活动报名信息*/
	public void addSignUp(SignUp signUp) throws Exception;

	/*按照查询条件分页查询活动报名记录*/
	public ArrayList<SignUp> querySignUp(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有活动报名记录*/
	public ArrayList<SignUp> querySignUpList(@Param("where") String where) throws Exception;

	/*按照查询条件的活动报名记录数*/
	public int querySignUpCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条活动报名记录*/
	public SignUp getSignUp(int signId) throws Exception;

	/*更新活动报名记录*/
	public void updateSignUp(SignUp signUp) throws Exception;

	/*删除活动报名记录*/
	public void deleteSignUp(int signId) throws Exception;

}
