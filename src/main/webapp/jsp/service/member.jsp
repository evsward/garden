<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/head/header.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>会员管理</title>
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
					<form action="${ctx }/service/member" method='post' class="form-horizontal">
						<div class="row">
							<div class="col-md-4">
								<div class="form-group">
									<label for="inputopenid" class="col-sm-2 control-label">openid</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" id="inputopenid" name="openid" placeholder="openid">
									</div>
								</div>
								<div class="form-group">
									<label for="inputnickname" class="col-sm-2 control-label">昵称</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" id="inputnickname" name="nickname" placeholder="nickname">
									</div>
								</div>
							</div>
							<div class="col-md-4">
								<div class="form-group">
									<label for="inputcity" class="col-sm-2 control-label">城市</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" id="inputcity" name="city" placeholder="city">
									</div>
								</div>
								<div class="form-group">
									<label for="inputcountry" class="col-sm-2 control-label">国家</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" id="inputcountry" name="country" placeholder="country">
									</div>
								</div>
							</div>
							<div class="col-md-4">
								<div class="form-group">
									<div class="row">
										<div class="col-md-6">
											<div style="float: right;">
												<input type="radio" name="subscribe" value="0" placeholder="openid">已关注</input>
											</div>
										</div>
										<div class="col-md-6">
											<div style="float: left;">
												<input type="radio" name="subscribe" value="1" placeholder="openid">未关注</input>
											</div>
										</div>
									</div>
								</div>
								<div class="form-group">
									<div class="row">
										<div class="col-md-6">
											<div style="float: right;">
												<input type="radio" name="sex" value="0" placeholder="openid">女</input>
											</div>
										</div>
										<div class="col-md-6">
											<div style="float: left;">
												<input type="radio" name="sex" value="1" placeholder="openid">男</input>
											</div>
										</div>
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
							<th class="text-nowrap">#</th>
							<th class="text-nowrap">头像</th>
							<th class="text-nowrap">openid</th>
							<th class="text-nowrap">是否关注</th>
							<th class="text-nowrap">昵称</th>
							<th class="text-nowrap">性别</th>
							<th class="text-nowrap">城市</th>
							<th class="text-nowrap">省份</th>
							<th class="text-nowrap">国籍</th>
							<th class="text-nowrap">创建时间</th>
							<th class="text-nowrap">更新时间</th>
						</thead>
						<tbody>
							<c:forEach items="${allMembers}" var="member" varStatus="idx">
								<tr id="tr${role.roleId }">
									<td class="text-nowrap">${idx.index+1 }</td>
									<td class="text-nowrap"><img src="${member.headimgurl }" alt="${member.nickname }" height="32px"
										width="32px" /></td>
									<td class="text-nowrap">${member.openid }</td>
									<td class="text-nowrap"><c:choose>
											<c:when test="${member.subscribe == 0 }">已关注</c:when>
											<c:when test="${member.subscribe == 1 }">未关注</c:when>
										</c:choose></td>
									<td class="text-nowrap">${member.nickname }</td>
									<td class="text-nowrap"><c:choose>
											<c:when test="${member.sex == 0 }">女</c:when>
											<c:when test="${member.sex == 1 }">男</c:when>
										</c:choose></td>
									<td class="text-nowrap">${member.city }</td>
									<td class="text-nowrap">${member.province }</td>
									<td class="text-nowrap">${member.country }</td>
									<td class="text-nowrap"><fmt:formatDate value="${member.createTime }" pattern="yyyy/MM/dd" /></td>
									<td class="text-nowrap"><fmt:formatDate value="${member.updateTime }" pattern="yyyy/MM/dd HH:mm:ss" /></td>
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
								<form action="${ctx}/service/member" method="post">
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