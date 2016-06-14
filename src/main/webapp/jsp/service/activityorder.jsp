<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/head/header.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>活动订单</title>
</head>
<body>
	<div class="container-fluid">
		<div class="well well-sm" style="background: #337ab7;">
			<div class="row">
				<div class="col-md-10">
					<h1>
						<span style="font-weight: bold; font-family: 微软雅黑; color: #ECF1F5;">&nbsp;<span
							class="glyphicon glyphicon-tree-deciduous" aria-hidden="true"></span> 春晖园温泉度假酒店
						</span>
						<p
							style="margin-left: 60px; margin-top: 10px; font-weight: bold; font-size: 18px; font-family: 微软雅黑; color: #ECF1F5;">[微信公众号管理平台]</p>
					</h1>
				</div>
				<div class="col-md-2">
					<p>&nbsp;&nbsp;</p>
					<p style="font-weight: bold; font-family: 宋体; color: #ECF1F5; font-size: 130%;">
						<span style="cursor: pointer;" onclick="javascript:void();"><span class="glyphicon glyphicon-user"
							aria-hidden="true"></span> <u style="font-size: 20px;">${user.realname}</u></span>&nbsp;<a class="btn btn-warning"
							style="font-weight: bold; font-family: 微软雅黑; font-size: 18px; padding: 1px 5px 1px 5px;"
							href="${ctx}/account/index/logout">退出</a>
					</p>
					<p style="font-size: 16px; font-family: 微软雅黑; color: #ECF1F5;">
						当前日期：<%=str_date1%></p>
				</div>
			</div>
		</div>
		<div style="margin-left: 18px; margin-top: 35px;">
			<div class="row">
				<ul class="nav nav-tabs" style="border-bottom: 5px solid #ddd;">
					<c:forEach items="${roles}" var="role" varStatus="idx">
						<li user="presentation" <c:if test="${role.roleCode eq roleCode}">class="active"</c:if>><a
							href="${ctx }${role.relaUrl }"><span
								style="font-family: 微软雅黑; font-size: 25px; font-weight: bold; color: green;"><span
									class="glyphicon glyphicon-${role.icon }" aria-hidden="true"></span> ${role.roleName }</span></a></li>
					</c:forEach>
				</ul>
			</div>
		</div>
		<div class="row" style="margin-top: 40px;">
			<div class="col-md-2">
				<ul class="nav nav-pills nav-stacked">
					<c:forEach items="${roles}" var="role" varStatus="idx">
						<li user="presentation" <c:if test="${role.roleCode eq roleCode}">class="active"</c:if>><a
							href="${ctx }${role.relaUrl }"><span class="tabName"><span class="glyphicon glyphicon-${role.icon }"
									aria-hidden="true"></span> ${role.roleName }</span></a></li>
					</c:forEach>
				</ul>
			</div>
			<div class="col-md-10">
				<div class="container-fluid">
					<form action="${ctx }/service/activityorder" method='post' class="form-horizontal">
						<div class="row">
							<div class="col-md-4">
								<div class="form-group">
									<label for="inputreserveId" class="col-sm-2 control-label">openid</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" id="inputreserveId" name="openid" placeholder="reserveId">
									</div>
								</div>
							</div>
							<div class="col-md-4">
								<div class="form-group">
									<label for="inputsubmitterMobile" class="col-sm-2 control-label">手机</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" id="inputsubmitterMobile" name="submitterMobile"
											placeholder="submitterMobile">
									</div>
								</div>
							</div>
							<div class="col-md-4">
								<div class="form-group">
									<label for="inputsubmitterName" class="col-sm-3 control-label">表单填写人1</label>
									<div class="col-sm-9">
										<input type="text" class="form-control" id="inputsubmitterName" name="submitterName1"
											placeholder="submitterName">
									</div>
								</div>
								<input class="btn btn-info btn-xs"
									style="font-weight: bold; font-family: 微软雅黑; color: #ffff; font-size: 130%; float: right;" type="submit"
									class="btn btn-default btn-xs" value="条件查询" />
							</div>
						</div>
					</form>
					<table class="table table-hover table-bordered">
						<thead>
							<th>#</th>
							<th>订单ID</th>
							<th>openid</th>
							<th>操作人</th>
							<th>活动</th>
							<th>家长姓名</th>
							<th>孩子姓名</th>
							<th>孩子年龄</th>
							<th>联系方式</th>
							<th>活动日期</th>
							<th>特别说明</th>
						</thead>
						<tbody>
							<c:forEach items="${allOrders}" var="order" varStatus="idx">
								<tr id="tr">
									<td>${idx.index+1 }</td>
									<td>${order.activityOrderId }</td>
									<td>${order.openid }</td>
									<td>${order.nickname }</td>
									<td>${order.activityName }</td>
									<td>${order.submitterName1 }</td>
									<td>${order.submitterName2 }</td>
									<td>${order.submitterAge2 }</td>
									<td>${order.submitterMobile }</td>
									<td>${order.submitterDate1 }</td>
									<td>${order.specialNotes }</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				<div class="container-fluid">
					<div class="row">
						<div class="col-md-6">
							<p style="font-family: 微软雅黑; font-size: 16px;">共${rows }条&nbsp;&nbsp;当前第${pageNO }页，共${pages }页</p>
						</div>
						<div class="col-md-6">
							<div style="float: right;">
								<form action="${ctx}/service/hotelOrder" method="post">
									<span style="font-family: 微软雅黑; font-size: 16px;"> 每页显示<input style="width: 50px;" type="number" min="1"
										max="${rows }" value="${pageSize }" name="pageSize" />条 <input class="btn btn-info btn-xs" type="submit"
										onclick="javascript:$('#pageNO').val(1);" value="首页" /> <input class="btn btn-info btn-xs" type="submit"
										onclick="javascript:$('#pageNO').val(${pageNO-1});" value="上一页" /> <input class="btn btn-info btn-xs"
										type="submit" onclick="javascript:$('#pageNO').val(${pageNO+1});" value="下一页" /> <input
										class="btn btn-info btn-xs" type="submit" onclick="javascript:$('#pageNO').val(${pages});" value="尾页" /> 转到<input
										style="width: 50px;" type="number" min="1" max="${pages }" value="${pageNO }" name="pageNO" id="pageNO" />页 <input
										class="btn btn-info btn-xs" type="submit" class="btn btn-default btn-xs" value="转" />
									</span>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="well well-sm" style="margin-bottom: 0px; postion: relative; background: #337ab7;">
			<p
				style="margin-left: 60px; text-align: center; margin-top: 10px; font-weight: bold; font-size: 18px; font-family: 微软雅黑; color: #ECF1F5;">微信公众号管理平台v1.0</p>
		</div>
	</div>
</body>
</html>