package com.chengxusheji.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.chengxusheji.utils.ExportExcelUtil;
import com.chengxusheji.utils.UserException;
import com.chengxusheji.service.DonationService;
import com.chengxusheji.po.Donation;
import com.chengxusheji.service.UserInfoService;
import com.chengxusheji.po.UserInfo;

//Donation管理控制层
@Controller
@RequestMapping("/Donation")
public class DonationController extends BaseController {

    /*业务层对象*/
    @Resource DonationService donationService;

    @Resource UserInfoService userInfoService;
	@InitBinder("userObj")
	public void initBinderuserObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("userObj.");
	}
	@InitBinder("donation")
	public void initBinderDonation(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("donation.");
	}
	/*跳转到添加Donation视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new Donation());
		/*查询所有的UserInfo信息*/
		List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
		request.setAttribute("userInfoList", userInfoList);
		return "Donation_add";
	}

	/*客户端ajax方式提交添加捐款信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated Donation donation, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
        donationService.addDonation(donation);
        message = "捐款添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}

	/*前台用户客户端ajax方式提交添加捐款信息*/
	@RequestMapping(value = "/userAdd", method = RequestMethod.POST)
	public void userAdd(Donation donation, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception {
		String message = "";
		boolean success = false;
		
		UserInfo userObj = new UserInfo();
		userObj.setUser_name(session.getAttribute("user_name").toString());
		donation.setUserObj(userObj);
		
		donation.setSheHeState("待审核");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		donation.setDunationTime(sdf.format(new java.util.Date())); 
		
        donationService.addDonation(donation);
        message = "捐款添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	
	
	/*客户端ajax方式查询统计图信息 */
	@RequestMapping(value = "/statistic", method = RequestMethod.POST)
	public void statistic(HttpServletRequest request,HttpServletResponse response) throws Exception {
		int year = Integer.parseInt(request.getParameter("year"));
		JSONObject jsonObj = new JSONObject();
    	JSONArray xData = new JSONArray();
    	JSONArray yData = new JSONArray();
    	
    	for(int i=1;i<=12;i++) {
    		float monthMoney = 0.0f;
    		String monthString = year  + "-" + (i<10?("0" + i):i+"");
    		
    		ArrayList<Donation> donationList = donationService.queryDonation(null, monthString, "已审核");
    		
    		for(Donation donation: donationList) {
    			monthMoney += donation.getDonationMoney();
    		}
    		xData.put(i+"月");
    		yData.put(monthMoney);
    	}
    	
    	//将要被返回到客户端的对象 
		JSONObject json=new JSONObject();
		json.accumulate("xData", xData);
		json.accumulate("yData", yData); 
		
		response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter(); 
		out.println(json.toString());
		out.flush(); 
		out.close();
	}
	
	

	/*ajax方式按照查询条件分页查询捐款信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(@ModelAttribute("userObj") UserInfo userObj,String dunationTime,String sheHeState,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if (dunationTime == null) dunationTime = "";
		if (sheHeState == null) sheHeState = "";
		if(rows != 0)donationService.setRows(rows);
		List<Donation> donationList = donationService.queryDonation(userObj, dunationTime, sheHeState, page);
	    /*计算总的页数和总的记录数*/
	    donationService.queryTotalPageAndRecordNumber(userObj, dunationTime, sheHeState);
	    /*获取到总的页码数目*/
	    int totalPage = donationService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = donationService.getRecordNumber();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(Donation donation:donationList) {
			JSONObject jsonDonation = donation.getJsonObject();
			jsonArray.put(jsonDonation);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}

	/*ajax方式按照查询条件分页查询捐款信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<Donation> donationList = donationService.queryAllDonation();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(Donation donation:donationList) {
			JSONObject jsonDonation = new JSONObject();
			jsonDonation.accumulate("donationId", donation.getDonationId());
			jsonArray.put(jsonDonation);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

	/*前台按照查询条件分页查询捐款信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(@ModelAttribute("userObj") UserInfo userObj,String dunationTime,String sheHeState,Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (dunationTime == null) dunationTime = "";
		if (sheHeState == null) sheHeState = "";
		List<Donation> donationList = donationService.queryDonation(userObj, dunationTime, sheHeState, currentPage);
	    /*计算总的页数和总的记录数*/
	    donationService.queryTotalPageAndRecordNumber(userObj, dunationTime, sheHeState);
	    /*获取到总的页码数目*/
	    int totalPage = donationService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = donationService.getRecordNumber();
	    request.setAttribute("donationList",  donationList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("userObj", userObj);
	    request.setAttribute("dunationTime", dunationTime);
	    request.setAttribute("sheHeState", sheHeState);
	    List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
	    request.setAttribute("userInfoList", userInfoList);
		return "Donation/donation_frontquery_result"; 
	}
	
	
	/*前台按照查询条件分页查询捐款信息*/
	@RequestMapping(value = { "/userFrontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String userFrontlist(@ModelAttribute("userObj") UserInfo userObj,String dunationTime,String sheHeState,Integer currentPage, Model model, HttpServletRequest request,HttpSession session) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (dunationTime == null) dunationTime = "";
		if (sheHeState == null) sheHeState = "";
		userObj = new UserInfo();
		userObj.setUser_name(session.getAttribute("user_name").toString());
		
		List<Donation> donationList = donationService.queryDonation(userObj, dunationTime, sheHeState, currentPage);
	    /*计算总的页数和总的记录数*/
	    donationService.queryTotalPageAndRecordNumber(userObj, dunationTime, sheHeState);
	    /*获取到总的页码数目*/
	    int totalPage = donationService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = donationService.getRecordNumber();
	    request.setAttribute("donationList",  donationList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("userObj", userObj);
	    request.setAttribute("dunationTime", dunationTime);
	    request.setAttribute("sheHeState", sheHeState);
	    List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
	    request.setAttribute("userInfoList", userInfoList);
		return "Donation/donation_userFrontquery_result"; 
	}
	

     /*前台查询Donation信息*/
	@RequestMapping(value="/{donationId}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable Integer donationId,Model model,HttpServletRequest request) throws Exception {
		/*根据主键donationId获取Donation对象*/
        Donation donation = donationService.getDonation(donationId);

        List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
        request.setAttribute("userInfoList", userInfoList);
        request.setAttribute("donation",  donation);
        return "Donation/donation_frontshow";
	}

	/*ajax方式显示捐款修改jsp视图页*/
	@RequestMapping(value="/{donationId}/update",method=RequestMethod.GET)
	public void update(@PathVariable Integer donationId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键donationId获取Donation对象*/
        Donation donation = donationService.getDonation(donationId);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonDonation = donation.getJsonObject();
		out.println(jsonDonation.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新捐款信息*/
	@RequestMapping(value = "/{donationId}/update", method = RequestMethod.POST)
	public void update(@Validated Donation donation, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		try {
			donationService.updateDonation(donation);
			message = "捐款更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "捐款更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除捐款信息*/
	@RequestMapping(value="/{donationId}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable Integer donationId,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  donationService.deleteDonation(donationId);
	            request.setAttribute("message", "捐款删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "捐款删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条捐款记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String donationIds,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = donationService.deleteDonations(donationIds);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*按照查询条件导出捐款信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel(@ModelAttribute("userObj") UserInfo userObj,String dunationTime,String sheHeState, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        if(dunationTime == null) dunationTime = "";
        if(sheHeState == null) sheHeState = "";
        List<Donation> donationList = donationService.queryDonation(userObj,dunationTime,sheHeState);
        ExportExcelUtil ex = new ExportExcelUtil();
        String _title = "Donation信息记录"; 
        String[] headers = { "捐款id","捐款人","捐款金额","捐款时间","审核状态"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<donationList.size();i++) {
        	Donation donation = donationList.get(i); 
        	dataset.add(new String[]{donation.getDonationId() + "",donation.getUserObj().getName(),donation.getDonationMoney() + "",donation.getDunationTime(),donation.getSheHeState()});
        }
        /*
        OutputStream out = null;
		try {
			out = new FileOutputStream("C://output.xls");
			ex.exportExcel(title,headers, dataset, out);
		    out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		OutputStream out = null;//创建一个输出流对象 
		try { 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"Donation.xls");//filename是下载的xls的名，建议最好用英文 
			response.setContentType("application/msexcel;charset=UTF-8");//设置类型 
			response.setHeader("Pragma","No-cache");//设置头 
			response.setHeader("Cache-Control","no-cache");//设置头 
			response.setDateHeader("Expires", 0);//设置日期头  
			String rootPath = request.getSession().getServletContext().getRealPath("/");
			ex.exportExcel(rootPath,_title,headers, dataset, out);
			out.flush();
		} catch (IOException e) { 
			e.printStackTrace(); 
		}finally{
			try{
				if(out!=null){ 
					out.close(); 
				}
			}catch(IOException e){ 
				e.printStackTrace(); 
			} 
		}
    }
}
