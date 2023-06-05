package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.UserInfo;
import com.chengxusheji.po.Donation;

import com.chengxusheji.mapper.DonationMapper;
@Service
public class DonationService {

	@Resource DonationMapper donationMapper;
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

    /*添加捐款记录*/
    public void addDonation(Donation donation) throws Exception {
    	donationMapper.addDonation(donation);
    }

    /*按照查询条件分页查询捐款记录*/
    public ArrayList<Donation> queryDonation(UserInfo userObj,String dunationTime,String sheHeState,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(null != userObj &&  userObj.getUser_name() != null  && !userObj.getUser_name().equals(""))  where += " and t_donation.userObj='" + userObj.getUser_name() + "'";
    	if(!dunationTime.equals("")) where = where + " and t_donation.dunationTime like '%" + dunationTime + "%'";
    	if(!sheHeState.equals("")) where = where + " and t_donation.sheHeState like '%" + sheHeState + "%'";
    	int startIndex = (currentPage-1) * this.rows;
    	return donationMapper.queryDonation(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<Donation> queryDonation(UserInfo userObj,String dunationTime,String sheHeState) throws Exception  { 
     	String where = "where 1=1";
    	if(null != userObj &&  userObj.getUser_name() != null && !userObj.getUser_name().equals(""))  where += " and t_donation.userObj='" + userObj.getUser_name() + "'";
    	if(!dunationTime.equals("")) where = where + " and t_donation.dunationTime like '%" + dunationTime + "%'";
    	if(!sheHeState.equals("")) where = where + " and t_donation.sheHeState like '%" + sheHeState + "%'";
    	return donationMapper.queryDonationList(where);
    }

    /*查询所有捐款记录*/
    public ArrayList<Donation> queryAllDonation()  throws Exception {
        return donationMapper.queryDonationList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(UserInfo userObj,String dunationTime,String sheHeState) throws Exception {
     	String where = "where 1=1";
    	if(null != userObj &&  userObj.getUser_name() != null && !userObj.getUser_name().equals(""))  where += " and t_donation.userObj='" + userObj.getUser_name() + "'";
    	if(!dunationTime.equals("")) where = where + " and t_donation.dunationTime like '%" + dunationTime + "%'";
    	if(!sheHeState.equals("")) where = where + " and t_donation.sheHeState like '%" + sheHeState + "%'";
        recordNumber = donationMapper.queryDonationCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取捐款记录*/
    public Donation getDonation(int donationId) throws Exception  {
        Donation donation = donationMapper.getDonation(donationId);
        return donation;
    }

    /*更新捐款记录*/
    public void updateDonation(Donation donation) throws Exception {
        donationMapper.updateDonation(donation);
    }

    /*删除一条捐款记录*/
    public void deleteDonation (int donationId) throws Exception {
        donationMapper.deleteDonation(donationId);
    }

    /*删除多条捐款信息*/
    public int deleteDonations (String donationIds) throws Exception {
    	String _donationIds[] = donationIds.split(",");
    	for(String _donationId: _donationIds) {
    		donationMapper.deleteDonation(Integer.parseInt(_donationId));
    	}
    	return _donationIds.length;
    }
}
