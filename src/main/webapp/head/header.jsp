<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=750,target-densitydpi=device-dpi,minimum-scale=0.5,maximum-scale=0.5," />
<link rel='icon' href='${ctx }/static/images/pic.ico ' type=‘image/x-ico’ />
<link rel="stylesheet" href="${ctx }/static/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="${ctx }/static/bootstrap/css/bootstrap-theme.min.css">
<link rel="stylesheet" href="${ctx }/static/zTree/css/zTreeStyle/zTreeStyle.css" media="screen">
<link rel="stylesheet" href="${ctx }/static/css/garden.css">
<script src="${ctx }/static/JQuery/jquery-1.11.1.min.js"></script>
<script src="${ctx }/static/bootstrap/js/bootstrap.min.js"></script>
<script src="${ctx }/static/zTree/js/jquery.ztree.all-3.5.min.js"></script>
<%
	java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd");

	java.util.Date currentTime = new java.util.Date();//得到当前系统时间 

	String str_date1 = formatter.format(currentTime); //将日期时间格式化 

	String str_date2 = currentTime.toString(); //将Date型日期时间转换成字符串形式
%>

</head>
<body>
	<%@ include file="windowsBoxes.jsp"%>
</body>
</html>