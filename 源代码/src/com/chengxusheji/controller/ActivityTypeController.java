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
import com.chengxusheji.service.ActivityTypeService;
import com.chengxusheji.po.ActivityType;

//ActivityType管理控制层
@Controller
@RequestMapping("/ActivityType")
public class ActivityTypeController extends BaseController {

    /*业务层对象*/
    @Resource ActivityTypeService activityTypeService;

	@InitBinder("activityType")
	public void initBinderActivityType(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("activityType.");
	}
	/*跳转到添加ActivityType视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new ActivityType());
		return "ActivityType_add";
	}

	/*客户端ajax方式提交添加活动类型信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated ActivityType activityType, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
        activityTypeService.addActivityType(activityType);
        message = "活动类型添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	/*ajax方式按照查询条件分页查询活动类型信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if(rows != 0)activityTypeService.setRows(rows);
		List<ActivityType> activityTypeList = activityTypeService.queryActivityType(page);
	    /*计算总的页数和总的记录数*/
	    activityTypeService.queryTotalPageAndRecordNumber();
	    /*获取到总的页码数目*/
	    int totalPage = activityTypeService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = activityTypeService.getRecordNumber();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(ActivityType activityType:activityTypeList) {
			JSONObject jsonActivityType = activityType.getJsonObject();
			jsonArray.put(jsonActivityType);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}

	/*ajax方式按照查询条件分页查询活动类型信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<ActivityType> activityTypeList = activityTypeService.queryAllActivityType();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(ActivityType activityType:activityTypeList) {
			JSONObject jsonActivityType = new JSONObject();
			jsonActivityType.accumulate("typeId", activityType.getTypeId());
			jsonActivityType.accumulate("typeName", activityType.getTypeName());
			jsonArray.put(jsonActivityType);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

	/*前台按照查询条件分页查询活动类型信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		List<ActivityType> activityTypeList = activityTypeService.queryActivityType(currentPage);
	    /*计算总的页数和总的记录数*/
	    activityTypeService.queryTotalPageAndRecordNumber();
	    /*获取到总的页码数目*/
	    int totalPage = activityTypeService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = activityTypeService.getRecordNumber();
	    request.setAttribute("activityTypeList",  activityTypeList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
		return "ActivityType/activityType_frontquery_result"; 
	}

     /*前台查询ActivityType信息*/
	@RequestMapping(value="/{typeId}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable Integer typeId,Model model,HttpServletRequest request) throws Exception {
		/*根据主键typeId获取ActivityType对象*/
        ActivityType activityType = activityTypeService.getActivityType(typeId);

        request.setAttribute("activityType",  activityType);
        return "ActivityType/activityType_frontshow";
	}

	/*ajax方式显示活动类型修改jsp视图页*/
	@RequestMapping(value="/{typeId}/update",method=RequestMethod.GET)
	public void update(@PathVariable Integer typeId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键typeId获取ActivityType对象*/
        ActivityType activityType = activityTypeService.getActivityType(typeId);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonActivityType = activityType.getJsonObject();
		out.println(jsonActivityType.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新活动类型信息*/
	@RequestMapping(value = "/{typeId}/update", method = RequestMethod.POST)
	public void update(@Validated ActivityType activityType, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		try {
			activityTypeService.updateActivityType(activityType);
			message = "活动类型更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "活动类型更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除活动类型信息*/
	@RequestMapping(value="/{typeId}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable Integer typeId,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  activityTypeService.deleteActivityType(typeId);
	            request.setAttribute("message", "活动类型删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "活动类型删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条活动类型记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String typeIds,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = activityTypeService.deleteActivityTypes(typeIds);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*按照查询条件导出活动类型信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel( Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        List<ActivityType> activityTypeList = activityTypeService.queryActivityType();
        ExportExcelUtil ex = new ExportExcelUtil();
        String _title = "ActivityType信息记录"; 
        String[] headers = { "活动类型id","活动类型名称"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<activityTypeList.size();i++) {
        	ActivityType activityType = activityTypeList.get(i); 
        	dataset.add(new String[]{activityType.getTypeId() + "",activityType.getTypeName()});
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
			response.setHeader("Content-disposition","attachment; filename="+"ActivityType.xls");//filename是下载的xls的名，建议最好用英文 
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
