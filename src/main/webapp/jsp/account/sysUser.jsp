<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/head/header.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<script src="${ctx }/static/js/sysUser.js"></script>
<title>用户管理</title>
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
					<button type="button" class="btn btn-success myButton" onclick="javascript:addSysUserInfo();">添加系统用户</button>
					<!-- <button type="button" class="btn btn-info myButton" onclick="javascript:modifyMySysUserInfo();">修改个人信息</button> -->
					<table class="table table-hover ">
						<thead>
							<th class="text-nowrap">用户ID</th>
							<th class="text-nowrap">用户名</th>
							<th class="text-nowrap">密码</th>
							<th class="text-nowrap">真实姓名</th>
							<th class="text-nowrap">创建时间</th>
							<th class="text-nowrap" width="180px">更新时间</th>
							<th class="text-nowrap" width="180px">用户状态</th>
							<th class="text-nowrap">手机号</th>
							<th class="text-nowrap">邮箱</th>
							<th class="text-nowrap">更新</th>
							<th class="text-nowrap">权限</th>
							<th class="text-nowrap" width="60px">删除</th>
						</thead>
						<tbody>
							<c:forEach items="${allUsers}" var="user" varStatus="idx">
								<tr>
									<td class="text-nowrap">${idx.index+1 }</td>
									<td class="text-nowrap"><input id="username${user.userId }" class="form-control linput-g" type="text"
										value="${user.username }"></td>
									<td class="text-nowrap"><input id="password${user.userId }" title="${user.password }"
										class="form-control linput-g" type="password" value="${user.password }"></td>
									<td class="text-nowrap"><input id="realname${user.userId }" class="form-control linput-g" type="text"
										value="${user.realname }"></td>
									<td class="text-nowrap"><fmt:formatDate value="${user.createTime }" pattern="yyyy/MM/dd" /></td>
									<td class="text-nowrap"><fmt:formatDate value="${user.updateTime }" pattern="yyyy/MM/dd HH:mm:ss" /></td>
									<td class="text-nowrap"><input type="radio" name="userState${user.userId }" value="1"
										<c:if test="${user.userState==1}">checked</c:if>>有效</input> <input type="radio"
										name="userState${user.userId }" value="2" <c:if test="${user.userState==2}">checked</c:if>>无效</input></td>
									<td class="text-nowrap"><input id="mobile${user.userId }" class="form-control linput-g" type="number"
										value="${user.mobile }"></td>
									<td class="text-nowrap"><input id="email${user.userId }" class="form-control linput-g" type="email"
										value="${user.email }"></td>
									<td class="text-nowrap"><input class="btn btn-info btn-xs" type="button" value="更新"
										onclick='javascript:updateSysUser(${user.userId });'></td>
									<td class="text-nowrap"><input class="btn btn-success btn-xs" type="button" value="权限配置"
										onclick='javascript:configUserRole(${user.userId });'></td>
									<td class="text-nowrap"><a href="javascript:delSysUser(${user.userId })"><span
											class="glyphicon glyphicon-remove" aria-hidden="true"></span></a></td>
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
								<form action="${ctx}/account/sysuser" method="post">
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
	<!-- 权限配置弹出框 -->
	<div id="userRoleModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="userRoleModalLabel"
		data-backdrop="static">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
					<h3 id="userRoleModalLabel">
						权限配置-<small>从右侧单击选取要配置到当前用户的权限</small>
					</h3>
				</div>
				<input type="hidden" value="" id="userIdChoosed" />
				<div class="modal-body">
					<div class="container-fluid">
						<div class="row">
							<div class="col-md-6">
								<div class="container-fluid">
									<p style="background-color: gray; padding: 10px;">
										<font color="#ffffff"><strong>用户当前权限</strong></font>
									</p>
									<div class="well well-sm">
										<table class="table table-hover table-condensed">
											<thead>
												<th>权限ID</th>
												<th>权限编码</th>
												<th>权限名称</th>
											</thead>
											<tBody id="userRolesList"></tBody>
										</table>
									</div>
								</div>
							</div>
							<div class="col-md-6">
								<div class="container-fluid">
									<p style="background-color: gray; padding: 10px;">
										<font color="#ffffff"><strong>系统权限列表【有效权限】</strong></font>
									</p>
									<div class="well well-sm">
										<table class="table table-hover table-condensed">
											<thead>
												<th>权限ID</th>
												<th>权限编码</th>
												<th>权限名称</th>
											</thead>
											<tBody id="allRolesList">
												<c:forEach items="${allRoles}" var="role" varStatus="idx">
													<tr id="trAllRoles${role.roleCode }">
														<td>${role.roleId }</td>
														<td>${role.roleCode }</td>
														<td>${role.roleName }</td>
													</tr>
												</c:forEach>
											</tBody>
										</table>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button class="btn" data-dismiss="modal" aria-hidden="false">关闭</button>
					<button class="btn btn-primary" id="affirmSubmit" onclick="javascript:submitSysUserRole();">确定</button>
				</div>
			</div>
		</div>
	</div>
	<!-- SysUser表单弹出框 -->
	<div id="tableInfo" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="tableInfoModalLabel"
		data-backdrop="static">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<form id="sysUserInfoForm" action="${ctx}/account/sysuser/addSysUser" method="post">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3 id="tableInfoModalLabel">添加系统用户</h3>
					</div>
					<div class="modal-body">
						<table class="table table-striped table-hover">
							<tr>
								<th>用户名</th>
								<td><input id="username" name="username" required="" type="text" class="form-control"
									placeholder="Username" value="" aria-describedby="sizing-addon3"></td>
							</tr>
							<tr>
								<th>密码</th>
								<td><input id="password" name="password" required="" type=text class="form-control" placeholder="Password"
									value="" aria-describedby="sizing-addon3"></td>
							</tr>
							<tr>
								<th>真实姓名</th>
								<td><input id="realname" name="realname" required="" type="text" class="form-control"
									placeholder="RealName" value="" aria-describedby="sizing-addon3"></td>
							</tr>
							<tr>
								<th>手机号</th>
								<td><input id="mobile" name="mobile" required="" type="text" class="form-control" placeholder="Mobile"
									value="" aria-describedby="sizing-addon3"></td>
							</tr>
							<tr>
								<th>邮箱</th>
								<td><input id="email" name="email" required="" type="email" class="form-control"
									placeholder="jane@example.com" value="" aria-describedby="sizing-addon3"></td>
							</tr>
						</table>
					</div>
					<div class="modal-footer">
						<input type="submit" value="提交" class="btn btn-primary" />
						<button class="btn" data-dismiss="modal" aria-hidden="false">关闭</button>
					</div>
					<input type="hidden" name="userId" value="${user.userId }" />
				</form>
			</div>
		</div>
	</div>

	<input type="hidden" value="${ctx}/account/sysuser" id="sysUserMainUrl" />
	<input type="hidden" value="${ctx}/account/sysuser/updateSysUser" id="updateUserUrl" />
	<input type="hidden" value="${ctx}/account/sysuser/delSysUser" id="delSysUserUrl" />
	<input type="hidden" value="${ctx}/account/sysuser/addSysUser" id="addSysUserUrl" />
	<input type="hidden" value="${ctx}/account/sysuser/updateMySysUserInfo" id="updateMySysUserInfoUrl" />
	<input type="hidden" value="${ctx}/account/sysuser/getSysUserRoles" id="getSysUserRolesUrl" />
	<input type="hidden" value="${ctx}/account/sysuser/updateSysUserRoles" id="updateSysUserRolesUrl" />
	<input type="hidden" value="${user.username }" id="myUserName" />
	<input type="hidden" value="${user.password }" id="myPassword" />
	<input type="hidden" value="${user.realname }" id="myRealname" />
	<input type="hidden" value="${user.mobile }" id="myMobile" />
	<input type="hidden" value="${user.email }" id="myEmail" />
</body>
</html>