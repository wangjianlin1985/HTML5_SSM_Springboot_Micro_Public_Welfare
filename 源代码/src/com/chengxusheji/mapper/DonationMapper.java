package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.Donation;

public interface DonationMapper {
	/*添加捐款信息*/
	public void addDonation(Donation donation) throws Exception;

	/*按照查询条件分页查询捐款记录*/
	public ArrayList<Donation> queryDonation(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有捐款记录*/
	public ArrayList<Donation> queryDonationList(@Param("where") String where) throws Exception;

	/*按照查询条件的捐款记录数*/
	public int queryDonationCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条捐款记录*/
	public Donation getDonation(int donationId) throws Exception;

	/*更新捐款记录*/
	public void updateDonation(Donation donation) throws Exception;

	/*删除捐款记录*/
	public void deleteDonation(int donationId) throws Exception;

}
