<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%@ page import="com.chengxusheji.po.ActivityInfo" %>
<%@ page import="com.chengxusheji.po.ActivityType" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    List<ActivityInfo> activityInfoList = (List<ActivityInfo>)request.getAttribute("activityInfoList");
    //获取所有的typeObj信息
    List<ActivityType> activityTypeList = (List<ActivityType>)request.getAttribute("activityTypeList");
    int currentPage =  (Integer)request.getAttribute("currentPage"); //当前页
    int totalPage =   (Integer)request.getAttribute("totalPage");  //一共多少页
    int recordNumber =   (Integer)request.getAttribute("recordNumber");  //一共多少记录
    ActivityType typeObj = (ActivityType)request.getAttribute("typeObj");
    String title = (String)request.getAttribute("title"); //活动主题查询关键字
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1 , user-scalable=no">
<title>公益活动查询</title>
<link href="<%=basePath %>plugins/bootstrap.css" rel="stylesheet">
<link href="<%=basePath %>plugins/bootstrap-dashen.css" rel="stylesheet">
<link href="<%=basePath %>plugins/font-awesome.css" rel="stylesheet">
<link href="<%=basePath %>plugins/animate.css" rel="stylesheet">
<link href="<%=basePath %>plugins/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
</head>
<body style="margin-top:70px;">
<div class="container">
<jsp:include page="../header.jsp"></jsp:include>
	<div class="col-md-9 wow fadeInLeft">
		<ul class="breadcrumb">
  			<li><a href="<%=basePath %>index.jsp">首页</a></li>
  			<li><a href="<%=basePath %>ActivityInfo/frontlist">公益活动信息列表</a></li>
  			<li class="active">查询结果显示</li>
  			<a class="pull-right" href="<%=basePath %>ActivityInfo/activityInfo_frontAdd.jsp" style="display:none;">添加公益活动</a>
		</ul>
		<div class="row">
			<%
				/*计算起始序号*/
				int startIndex = (currentPage -1) * 5;
				/*遍历记录*/
				for(int i=0;i<activityInfoList.size();i++) {
            		int currentIndex = startIndex + i + 1; //当前记录的序号
            		ActivityInfo activityInfo = activityInfoList.get(i); //获取到公益活动对象
            		String clearLeft = "";
            		if(i%4 == 0) clearLeft = "style=\"clear:left;\"";
			%>
			<div class="col-md-3 bottom15" <%=clearLeft %>>
			  <a  href="<%=basePath  %>ActivityInfo/<%=activityInfo.getActivityId() %>/frontshow"><img class="img-responsive" src="<%=basePath%><%=activityInfo.getActivityPhoto()%>" /></a>
			     <div class="showFields">
			     	<div class="field">
	            		活动id:<%=activityInfo.getActivityId() %>
			     	</div>
			     	<div class="field">
	            		活动类型:<%=activityInfo.getTypeObj().getTypeName() %>
			     	</div>
			     	<div class="field">
	            		活动主题:<%=activityInfo.getTitle() %>
			     	</div>
			     	<div class="field">
	            		活动时间:<%=activityInfo.getActivityTime() %>
			     	</div>
			        <a class="btn btn-primary top5" href="<%=basePath %>ActivityInfo/<%=activityInfo.getActivityId() %>/frontshow">详情</a>
			        <a class="btn btn-primary top5" onclick="activityInfoEdit('<%=activityInfo.getActivityId() %>');" style="display:none;">修改</a>
			        <a class="btn btn-primary top5" onclick="activityInfoDelete('<%=activityInfo.getActivityId() %>');" style="display:none;">删除</a>
			     </div>
			</div>
			<%  } %>

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

	<div class="col-md-3 wow fadeInRight">
		<div class="page-header">
    		<h1>公益活动查询</h1>
		</div>
		<form name="activityInfoQueryForm" id="activityInfoQueryForm" action="<%=basePath %>ActivityInfo/frontlist" class="mar_t15">
            <div class="form-group">
            	<label for="typeObj_typeId">活动类型：</label>
                <select id="typeObj_typeId" name="typeObj.typeId" class="form-control">
                	<option value="0">不限制</option>
	 				<%
	 				for(ActivityType activityTypeTemp:activityTypeList) {
	 					String selected = "";
 					if(typeObj!=null && typeObj.getTypeId()!=null && typeObj.getTypeId().intValue()==activityTypeTemp.getTypeId().intValue())
 						selected = "selected";
	 				%>
 				 <option value="<%=activityTypeTemp.getTypeId() %>" <%=selected %>><%=activityTypeTemp.getTypeName() %></option>
	 				<%
	 				}
	 				%>
 			</select>
            </div>
			<div class="form-group">
				<label for="title">活动主题:</label>
				<input type="text" id="title" name="title" value="<%=title %>" class="form-control" placeholder="请输入活动主题">
			</div>
            <input type=hidden name=currentPage value="<%=currentPage %>" />
            <button type="submit" class="btn btn-primary">查询</button>
        </form>
	</div>

		</div>
</div>
<div id="activityInfoEditDialog" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog" style="width:900px;" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title"><i class="fa fa-edit"></i>&nbsp;公益活动信息编辑</h4>
      </div>
      <div class="modal-body" style="height:450px; overflow: scroll;">
      	<form class="form-horizontal" name="activityInfoEditForm" id="activityInfoEditForm" enctype="multipart/form-data" method="post"  class="mar_t15">
		  <div class="form-group">
			 <label for="activityInfo_activityId_edit" class="col-md-3 text-right">活动id:</label>
			 <div class="col-md-9"> 
			 	<input type="text" id="activityInfo_activityId_edit" name="activityInfo.activityId" class="form-control" placeholder="请输入活动id" readOnly>
			 </div>
		  </div> 
		  <div class="form-group">
		  	 <label for="activityInfo_typeObj_typeId_edit" class="col-md-3 text-right">活动类型:</label>
		  	 <div class="col-md-9">
			    <select id="activityInfo_typeObj_typeId_edit" name="activityInfo.typeObj.typeId" class="form-control">
			    </select>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="activityInfo_title_edit" class="col-md-3 text-right">活动主题:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="activityInfo_title_edit" name="activityInfo.title" class="form-control" placeholder="请输入活动主题">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="activityInfo_activityPhoto_edit" class="col-md-3 text-right">活动图片:</label>
		  	 <div class="col-md-9">
			    <img  class="img-responsive" id="activityInfo_activityPhotoImg" border="0px"/><br/>
			    <input type="hidden" id="activityInfo_activityPhoto" name="activityInfo.activityPhoto"/>
			    <input id="activityPhotoFile" name="activityPhotoFile" type="file" size="50" />
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="activityInfo_content_edit" class="col-md-3 text-right">活动内容:</label>
		  	 <div class="col-md-9">
			 	<textarea name="activityInfo.content" id="activityInfo_content_edit" style="width:100%;height:500px;"></textarea>
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="activityInfo_activityTime_edit" class="col-md-3 text-right">活动时间:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="activityInfo_activityTime_edit" name="activityInfo.activityTime" class="form-control" placeholder="请输入活动时间">
			 </div>
		  </div>
		</form> 
	    <style>#activityInfoEditForm .form-group {margin-bottom:5px;}  </style>
      </div>
      <div class="modal-footer"> 
      	<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
      	<button type="button" class="btn btn-primary" onclick="ajaxActivityInfoModify();">提交</button>
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
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor1_4_3/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor1_4_3/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor1_4_3/lang/zh-cn/zh-cn.js"></script>
<script>
//实例化编辑器
var activityInfo_content_edit = UE.getEditor('activityInfo_content_edit'); //活动内容编辑器
var basePath = "<%=basePath%>";
/*跳转到查询结果的某页*/
function GoToPage(currentPage,totalPage) {
    if(currentPage==0) return;
    if(currentPage>totalPage) return;
    document.activityInfoQueryForm.currentPage.value = currentPage;
    document.activityInfoQueryForm.submit();
}

/*可以直接跳转到某页*/
function changepage(totalPage)
{
    var pageValue=document.activityInfoQueryForm.pageValue.value;
    if(pageValue>totalPage) {
        alert('你输入的页码超出了总页数!');
        return ;
    }
    document.activityInfoQueryForm.currentPage.value = pageValue;
    documentactivityInfoQueryForm.submit();
}

/*弹出修改公益活动界面并初始化数据*/
function activityInfoEdit(activityId) {
	$.ajax({
		url :  basePath + "ActivityInfo/" + activityId + "/update",
		type : "get",
		dataType: "json",
		success : function (activityInfo, response, status) {
			if (activityInfo) {
				$("#activityInfo_activityId_edit").val(activityInfo.activityId);
				$.ajax({
					url: basePath + "ActivityType/listAll",
					type: "get",
					success: function(activityTypes,response,status) { 
						$("#activityInfo_typeObj_typeId_edit").empty();
						var html="";
		        		$(activityTypes).each(function(i,activityType){
		        			html += "<option value='" + activityType.typeId + "'>" + activityType.typeName + "</option>";
		        		});
		        		$("#activityInfo_typeObj_typeId_edit").html(html);
		        		$("#activityInfo_typeObj_typeId_edit").val(activityInfo.typeObjPri);
					}
				});
				$("#activityInfo_title_edit").val(activityInfo.title);
				$("#activityInfo_activityPhoto").val(activityInfo.activityPhoto);
				$("#activityInfo_activityPhotoImg").attr("src", basePath +　activityInfo.activityPhoto);
				activityInfo_content_edit.setContent(activityInfo.content, false);
				$("#activityInfo_activityTime_edit").val(activityInfo.activityTime);
				$('#activityInfoEditDialog').modal('show');
			} else {
				alert("获取信息失败！");
			}
		}
	});
}

/*删除公益活动信息*/
function activityInfoDelete(activityId) {
	if(confirm("确认删除这个记录")) {
		$.ajax({
			type : "POST",
			url : basePath + "ActivityInfo/deletes",
			data : {
				activityIds : activityId,
			},
			success : function (obj) {
				if (obj.success) {
					alert("删除成功");
					$("#activityInfoQueryForm").submit();
					//location.href= basePath + "ActivityInfo/frontlist";
				}
				else 
					alert(obj.message);
			},
		});
	}
}

/*ajax方式提交公益活动信息表单给服务器端修改*/
function ajaxActivityInfoModify() {
	$.ajax({
		url :  basePath + "ActivityInfo/" + $("#activityInfo_activityId_edit").val() + "/update",
		type : "post",
		dataType: "json",
		data: new FormData($("#activityInfoEditForm")[0]),
		success : function (obj, response, status) {
            if(obj.success){
                alert("信息修改成功！");
                $("#activityInfoQueryForm").submit();
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

})
</script>
</body>
</html>

