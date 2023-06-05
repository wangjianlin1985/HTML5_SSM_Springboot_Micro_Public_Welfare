<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%@ page import="com.chengxusheji.po.Donation" %>
<%@ page import="com.chengxusheji.po.UserInfo" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    List<Donation> donationList = (List<Donation>)request.getAttribute("donationList");
    //获取所有的userObj信息
    List<UserInfo> userInfoList = (List<UserInfo>)request.getAttribute("userInfoList");
    int currentPage =  (Integer)request.getAttribute("currentPage"); //当前页
    int totalPage =   (Integer)request.getAttribute("totalPage");  //一共多少页
    int recordNumber =   (Integer)request.getAttribute("recordNumber");  //一共多少记录
    UserInfo userObj = (UserInfo)request.getAttribute("userObj");
    String dunationTime = (String)request.getAttribute("dunationTime"); //捐款时间查询关键字
    String sheHeState = (String)request.getAttribute("sheHeState"); //审核状态查询关键字
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1 , user-scalable=no">
<title>捐款查询</title>
<link href="<%=basePath %>plugins/bootstrap.css" rel="stylesheet">
<link href="<%=basePath %>plugins/bootstrap-dashen.css" rel="stylesheet">
<link href="<%=basePath %>plugins/font-awesome.css" rel="stylesheet">
<link href="<%=basePath %>plugins/animate.css" rel="stylesheet">
<link href="<%=basePath %>plugins/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
</head>
<body style="margin-top:70px;">
<div class="container">
<jsp:include page="../header.jsp"></jsp:include>
	<div class="row"> 
		<div class="col-md-9 wow fadeInDown" data-wow-duration="0.5s">
			<div>
				<!-- Nav tabs -->
				<ul class="nav nav-tabs" role="tablist">
			    	<li><a href="<%=basePath %>index.jsp">首页</a></li>
			    	<li role="presentation" class="active"><a href="#donationListPanel" aria-controls="donationListPanel" role="tab" data-toggle="tab">捐款列表</a></li>
			    	<li role="presentation" ><a href="<%=basePath %>Donation/donation_frontAdd.jsp" style="display:none;">添加捐款</a></li>
				</ul>
			  	<!-- Tab panes -->
			  	<div class="tab-content">
				    <div role="tabpanel" class="tab-pane active" id="donationListPanel">
				    		<div class="row">
				    			<div class="col-md-12 top5">
				    				<div class="table-responsive">
				    				<table class="table table-condensed table-hover">
				    					<tr class="success bold"><td>序号</td><td>捐款id</td><td>捐款人</td><td>捐款金额</td><td>捐款时间</td><td>审核状态</td><td>操作</td></tr>
				    					<% 
				    						/*计算起始序号*/
				    	            		int startIndex = (currentPage -1) * 5;
				    	            		/*遍历记录*/
				    	            		for(int i=0;i<donationList.size();i++) {
					    	            		int currentIndex = startIndex + i + 1; //当前记录的序号
					    	            		Donation donation = donationList.get(i); //获取到捐款对象
 										%>
 										<tr>
 											<td><%=currentIndex %></td>
 											<td><%=donation.getDonationId() %></td>
 											<td><%=donation.getUserObj().getName() %></td>
 											<td><%=donation.getDonationMoney() %></td>
 											<td><%=donation.getDunationTime() %></td>
 											<td><%=donation.getSheHeState() %></td>
 											<td>
 												<a href="<%=basePath  %>Donation/<%=donation.getDonationId() %>/frontshow"><i class="fa fa-info"></i>&nbsp;查看</a>&nbsp;
 												<a href="#" onclick="donationEdit('<%=donation.getDonationId() %>');" style="display:none;"><i class="fa fa-pencil fa-fw"></i>编辑</a>&nbsp;
 												<a href="#" onclick="donationDelete('<%=donation.getDonationId() %>');" style="display:none;"><i class="fa fa-trash-o fa-fw"></i>删除</a>
 											</td> 
 										</tr>
 										<%}%>
				    				</table>
				    				</div>
				    			</div>
				    		</div>

				    		<div class="row">
					            <div class="col-md-12">
						            <nav class="pull-left">
						                <ul class="pagination">
						                    <li><a href="#" onclick="GoToPage(<%=currentPage-1 %>,<%=totalPage %>);" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
						                     <%
						                    	int startPage = currentPage - 5;
						                    	int endPage = currentPage + 5;
						                    	if(startPage < 1) startPage=1;
						                    	if(endPage > totalPage) endPage = totalPage;
						                    	for(int i=startPage;i<=endPage;i++) {
						                    %>
						                    <li class="<%= currentPage==i?"active":"" %>"><a href="#"  onclick="GoToPage(<%=i %>,<%=totalPage %>);"><%=i %></a></li>
						                    <%  } %> 
						                    <li><a href="#" onclick="GoToPage(<%=currentPage+1 %>,<%=totalPage %>);"><span aria-hidden="true">&raquo;</span></a></li>
						                </ul>
						            </nav>
						            <div class="pull-right" style="line-height:75px;" >共有<%=recordNumber %>条记录，当前第 <%=currentPage %>/<%=totalPage %> 页</div>
					            </div>
				            </div> 
				    </div>
				</div>
			</div>
		</div>
	<div class="col-md-3 wow fadeInRight">
		<div class="page-header">
    		<h1>捐款查询</h1>
		</div>
		<form name="donationQueryForm" id="donationQueryForm" action="<%=basePath %>Donation/frontlist" class="mar_t15">
            <div class="form-group">
            	<label for="userObj_user_name">捐款人：</label>
                <select id="userObj_user_name" name="userObj.user_name" class="form-control">
                	<option value="">不限制</option>
	 				<%
	 				for(UserInfo userInfoTemp:userInfoList) {
	 					String selected = "";
 					if(userObj!=null && userObj.getUser_name()!=null && userObj.getUser_name().equals(userInfoTemp.getUser_name()))
 						selected = "selected";
	 				%>
 				 <option value="<%=userInfoTemp.getUser_name() %>" <%=selected %>><%=userInfoTemp.getName() %></option>
	 				<%
	 				}
	 				%>
 			</select>
            </div>
			<div class="form-group">
				<label for="dunationTime">捐款时间:</label>
				<input type="text" id="dunationTime" name="dunationTime" class="form-control"  placeholder="请选择捐款时间" value="<%=dunationTime %>" onclick="SelectDate(this,'yyyy-MM-dd')" />
			</div>
			<div class="form-group">
				<label for="sheHeState">审核状态:</label>
				<input type="text" id="sheHeState" name="sheHeState" value="<%=sheHeState %>" class="form-control" placeholder="请输入审核状态">
			</div>






            <input type=hidden name=currentPage value="<%=currentPage %>" />
            <button type="submit" class="btn btn-primary">查询</button>
        </form>
	</div>

		</div>
	</div> 
<div id="donationEditDialog" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title"><i class="fa fa-edit"></i>&nbsp;捐款信息编辑</h4>
      </div>
      <div class="modal-body" style="height:450px; overflow: scroll;">
      	<form class="form-horizontal" name="donationEditForm" id="donationEditForm" enctype="multipart/form-data" method="post"  class="mar_t15">
		  <div class="form-group">
			 <label for="donation_donationId_edit" class="col-md-3 text-right">捐款id:</label>
			 <div class="col-md-9"> 
			 	<input type="text" id="donation_donationId_edit" name="donation.donationId" class="form-control" placeholder="请输入捐款id" readOnly>
			 </div>
		  </div> 
		  <div class="form-group">
		  	 <label for="donation_userObj_user_name_edit" class="col-md-3 text-right">捐款人:</label>
		  	 <div class="col-md-9">
			    <select id="donation_userObj_user_name_edit" name="donation.userObj.user_name" class="form-control">
			    </select>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="donation_donationMoney_edit" class="col-md-3 text-right">捐款金额:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="donation_donationMoney_edit" name="donation.donationMoney" class="form-control" placeholder="请输入捐款金额">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="donation_dunationTime_edit" class="col-md-3 text-right">捐款时间:</label>
		  	 <div class="col-md-9">
                <div class="input-group date donation_dunationTime_edit col-md-12" data-link-field="donation_dunationTime_edit">
                    <input class="form-control" id="donation_dunationTime_edit" name="donation.dunationTime" size="16" type="text" value="" placeholder="请选择捐款时间" readonly>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                </div>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="donation_dunationMemo_edit" class="col-md-3 text-right">捐款备注:</label>
		  	 <div class="col-md-9">
			    <textarea id="donation_dunationMemo_edit" name="donation.dunationMemo" rows="8" class="form-control" placeholder="请输入捐款备注"></textarea>
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="donation_sheHeState_edit" class="col-md-3 text-right">审核状态:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="donation_sheHeState_edit" name="donation.sheHeState" class="form-control" placeholder="请输入审核状态">
			 </div>
		  </div>
		</form> 
	    <style>#donationEditForm .form-group {margin-bottom:5px;}  </style>
      </div>
      <div class="modal-footer"> 
      	<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
      	<button type="button" class="btn btn-primary" onclick="ajaxDonationModify();">提交</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<jsp:include page="../footer.jsp"></jsp:include> 
<script src="<%=basePath %>plugins/jquery.min.js"></script>
<script src="<%=basePath %>plugins/bootstrap.js"></script>
<script src="<%=basePath %>plugins/wow.min.js"></script>
<script src="<%=basePath %>plugins/bootstrap-datetimepicker.min.js"></script>
<script src="<%=basePath %>plugins/locales/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=basePath %>js/jsdate.js"></script>
<script>
var basePath = "<%=basePath%>";
/*跳转到查询结果的某页*/
function GoToPage(currentPage,totalPage) {
    if(currentPage==0) return;
    if(currentPage>totalPage) return;
    document.donationQueryForm.currentPage.value = currentPage;
    document.donationQueryForm.submit();
}

/*可以直接跳转到某页*/
function changepage(totalPage)
{
    var pageValue=document.donationQueryForm.pageValue.value;
    if(pageValue>totalPage) {
        alert('你输入的页码超出了总页数!');
        return ;
    }
    document.donationQueryForm.currentPage.value = pageValue;
    documentdonationQueryForm.submit();
}

/*弹出修改捐款界面并初始化数据*/
function donationEdit(donationId) {
	$.ajax({
		url :  basePath + "Donation/" + donationId + "/update",
		type : "get",
		dataType: "json",
		success : function (donation, response, status) {
			if (donation) {
				$("#donation_donationId_edit").val(donation.donationId);
				$.ajax({
					url: basePath + "UserInfo/listAll",
					type: "get",
					success: function(userInfos,response,status) { 
						$("#donation_userObj_user_name_edit").empty();
						var html="";
		        		$(userInfos).each(function(i,userInfo){
		        			html += "<option value='" + userInfo.user_name + "'>" + userInfo.name + "</option>";
		        		});
		        		$("#donation_userObj_user_name_edit").html(html);
		        		$("#donation_userObj_user_name_edit").val(donation.userObjPri);
					}
				});
				$("#donation_donationMoney_edit").val(donation.donationMoney);
				$("#donation_dunationTime_edit").val(donation.dunationTime);
				$("#donation_dunationMemo_edit").val(donation.dunationMemo);
				$("#donation_sheHeState_edit").val(donation.sheHeState);
				$('#donationEditDialog').modal('show');
			} else {
				alert("获取信息失败！");
			}
		}
	});
}

/*删除捐款信息*/
function donationDelete(donationId) {
	if(confirm("确认删除这个记录")) {
		$.ajax({
			type : "POST",
			url : basePath + "Donation/deletes",
			data : {
				donationIds : donationId,
			},
			success : function (obj) {
				if (obj.success) {
					alert("删除成功");
					$("#donationQueryForm").submit();
					//location.href= basePath + "Donation/frontlist";
				}
				else 
					alert(obj.message);
			},
		});
	}
}

/*ajax方式提交捐款信息表单给服务器端修改*/
function ajaxDonationModify() {
	$.ajax({
		url :  basePath + "Donation/" + $("#donation_donationId_edit").val() + "/update",
		type : "post",
		dataType: "json",
		data: new FormData($("#donationEditForm")[0]),
		success : function (obj, response, status) {
            if(obj.success){
                alert("信息修改成功！");
                $("#donationQueryForm").submit();
            }else{
                alert(obj.message);
            } 
		},
		processData: false,
		contentType: false,
	});
}

$(function(){
	/*小屏幕导航点击关闭菜单*/
    $('.navbar-collapse a').click(function(){
        $('.navbar-collapse').collapse('hide');
    });
    new WOW().init();

    /*捐款时间组件*/
    $('.donation_dunationTime_edit').datetimepicker({
    	language:  'zh-CN',  //语言
    	format: 'yyyy-mm-dd hh:ii:ss',
    	weekStart: 1,
    	todayBtn:  1,
    	autoclose: 1,
    	minuteStep: 1,
    	todayHighlight: 1,
    	startView: 2,
    	forceParse: 0
    });
})
</script>
</body>
</html>

