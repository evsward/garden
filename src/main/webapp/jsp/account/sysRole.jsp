<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/head/header.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<script src="${ctx }/static/js/sysRole.js"></script>
<title>权限管理</title>
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
					<button type="button" class="btn btn-danger myButton" data-toggle="modal" data-target="#tableRoleInfo">添加系统权限</button>
					<table class="table table-hover ">
						<thead>
							<th class="text-nowrap">#</th>
							<th class="text-nowrap">权限编码</th>
							<th class="text-nowrap">权限名称</th>
							<th class="text-nowrap">创建时间</th>
							<th class="text-nowrap">关联URL</th>
							<th class="text-nowrap">状态</th>
							<th class="text-nowrap">权限图标</th>
							<th class="text-nowrap" title="此字段数字越大，菜单位置越在前面。">权重</th>
							<th class="text-nowrap">备注</th>
							<th class="text-nowrap">更新</th>
							<th class="text-nowrap">删除</th>
						</thead>
						<tbody>
							<c:forEach items="${allRoles}" var="role" varStatus="idx">
								<tr id="tr${role.roleId }">
									<td class="text-nowrap">${idx.index+1 }</td>
									<td class="text-nowrap"><input id="roleCode${role.roleId }" class="form-control linput-g move-password"
										type="text" placeholder="role code " value="${role.roleCode }"></td>
									<td class="text-nowrap"><input id="roleName${role.roleId }" class="form-control linput-g" type="text"
										placeholder="role name " value="${role.roleName }"></td>
									<td class="text-nowrap"><fmt:formatDate value="${role.createTime }" pattern="yyyy/MM/dd" /></td>
									<td class="text-nowrap"><input id="relaUrl${role.roleId }" class="form-control linput-g" type="text"
										placeholder="role relate url " value="${role.relaUrl }"></td>
									<td class="text-nowrap"><input type="radio" name="roleState${role.roleId }" value="1"
										<c:if test="${role.roleState==1}">checked</c:if>>有效</input> <input type="radio"
										name="roleState${role.roleId }" value="2" <c:if test="${role.roleState==2}">checked</c:if>>无效</input></td>
									<td class="text-nowrap"><div class="dropdown">
											<button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu${role.roleId }"
												data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
												<span id="icon${role.roleId }" class="glyphicon glyphicon-${role.icon }" aria-hidden="true"></span><span
													class="caret"></span>
											</button>
											<ul class="dropdown-menu" aria-labelledby="dropdownMenu${role.roleId }">
												<li><a
													href="javascript:$('#icon${role.roleId }').attr('class','glyphicon glyphicon-leaf');$('#sysRoleIcon${role.roleId }').val('leaf');"><span
														class="glyphicon glyphicon-leaf" aria-hidden="true"></span></a></li>
												<li><a
													href="javascript:$('#icon${role.roleId }').attr('class','glyphicon glyphicon-eye-open');$('#sysRoleIcon${role.roleId }').val('eye-open');"><span
														class="glyphicon glyphicon-eye-open" aria-hidden="true"></span></a></li>
												<li><a
													href="javascript:$('#icon${role.roleId }').attr('class','glyphicon glyphicon-magnet');$('#sysRoleIcon${role.roleId }').val('magnet');"><span
														class="glyphicon glyphicon-magnet" aria-hidden="true"></span></a></li>
												<li><a
													href="javascript:$('#icon${role.roleId }').attr('class','glyphicon glyphicon-fire');$('#sysRoleIcon${role.roleId }').val('fire');"><span
														class="glyphicon glyphicon-fire" aria-hidden="true"></span></a></li>
												<li><a
													href="javascript:$('#icon${role.roleId }').attr('class','glyphicon glyphicon-globe');$('#sysRoleIcon${role.roleId }').val('globe');"><span
														class="glyphicon glyphicon-globe" aria-hidden="true"></span></a></li>
												<li><a
													href="javascript:$('#icon${role.roleId }').attr('class','glyphicon glyphicon-stats');$('#sysRoleIcon${role.roleId }').val('stats');"><span
														class="glyphicon glyphicon-stats" aria-hidden="true"></span></a></li>
												<li><a
													href="javascript:$('#icon${role.roleId }').attr('class','glyphicon glyphicon-blackboard');$('#sysRoleIcon${role.roleId }').val('blackboard');"><span
														class="glyphicon glyphicon-blackboard" aria-hidden="true"></span></a></li>
												<li><a
													href="javascript:$('#icon${role.roleId }').attr('class','glyphicon glyphicon-bishop');$('#sysRoleIcon${role.roleId }').val('bishop');"><span
														class="glyphicon glyphicon-bishop" aria-hidden="true"></span></a></li>
												<li><a
													href="javascript:$('#icon${role.roleId }').attr('class','glyphicon glyphicon-plus');$('#sysRoleIcon${role.roleId }').val('plus');"><span
														class="glyphicon glyphicon-plus" aria-hidden="true"></span></a></li>
												<li><a
													href="javascript:$('#icon${role.roleId }').attr('class','glyphicon glyphicon-cloud');$('#sysRoleIcon${role.roleId }').val('cloud');"><span
														class="glyphicon glyphicon-cloud" aria-hidden="true"></span></a></li>
												<li><a
													href="javascript:$('#icon${role.roleId }').attr('class','glyphicon glyphicon-heart');$('#sysRoleIcon${role.roleId }').val('heart');"><span
														class="glyphicon glyphicon-heart" aria-hidden="true"></span></a></li>
												<li><a
													href="javascript:$('#icon${role.roleId }').attr('class','glyphicon glyphicon-star-empty');$('#sysRoleIcon${role.roleId }').val('star-empty');"><span
														class="glyphicon glyphicon-star-empty" aria-hidden="true"></span></a></li>
												<li><a
													href="javascript:$('#icon${role.roleId }').attr('class','glyphicon glyphicon-film');$('#sysRoleIcon${role.roleId }').val('film');"><span
														class="glyphicon glyphicon-film" aria-hidden="true"></span></a></li>
												<li><a
													href="javascript:$('#icon${role.roleId }').attr('class','glyphicon glyphicon-signal');$('#sysRoleIcon${role.roleId }').val('signal');"><span
														class="glyphicon glyphicon-signal" aria-hidden="true"></span></a></li>
												<li><a
													href="javascript:$('#icon${role.roleId }').attr('class','glyphicon glyphicon-flag');$('#sysRoleIcon${role.roleId }').val('flag');"><span
														class="glyphicon glyphicon-flag" aria-hidden="true"></span></a></li>
												<li><a
													href="javascript:$('#icon${role.roleId }').attr('class','glyphicon glyphicon-grain');$('#sysRoleIcon${role.roleId }').val('grain');"><span
														class="glyphicon glyphicon-grain" aria-hidden="true"></span></a></li>
												<li><a
													href="javascript:$('#icon${role.roleId }').attr('class','glyphicon glyphicon-scale');$('#sysRoleIcon${role.roleId }').val('scale');"><span
														class="glyphicon glyphicon-scale" aria-hidden="true"></span></a></li>
												<li><a
													href="javascript:$('#icon${role.roleId }').attr('class','glyphicon glyphicon-asterisk');$('#sysRoleIcon${role.roleId }').val('asterisk');"><span
														class="glyphicon glyphicon-asterisk" aria-hidden="true"></span></a></li>
											</ul>
										</div> <input type="hidden" value="${role.icon }" id="sysRoleIcon${role.roleId }" /></td>
									<td class="text-nowrap"><input id="weight${role.roleId }" class="form-control linput-g" type="number"
										placeholder="role weight " min="1" max="999" value="${role.weight }"></td>
									<td class="text-nowrap"><input id="note${role.roleId }" class="form-control linput-g" type="text"
										placeholder="role note " value="${role.note }"></td>
									<td class="text-nowrap"><input class="btn btn-info btn-xs" type="button" value="更新"
										onclick='javascript:updateSysRole(${role.roleId });'></td>
									<td class="text-nowrap"><a href="javascript:delRole(${role.roleId })"><span
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
								<form action="${ctx}/account/sysrole" method="post">
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
	<!-- SysRole表单弹出框 -->
	<div id="tableRoleInfo" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="tableRoleInfoModalLabel"
		data-backdrop="static">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<form id="sysRoleInfoForm" action="${ctx}/account/sysrole/addSysRole" method="post">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3 id="tableRoleInfoModalLabel">添加系统权限</h3>
					</div>
					<div class="modal-body">
						<table class="table table-striped table-hover">
							<tr>
								<th>权限编码</th>
								<td><input id="roleCode" name="roleCode" required="" type="text" class="form-control"
									placeholder="roleCode" value="" aria-describedby="sizing-addon3"></td>
							</tr>
							<tr>
								<th>权限名称</th>
								<td><input id="roleName" name="roleName" required="" type=text class="form-control" placeholder="roleName"
									value="" aria-describedby="sizing-addon3"></td>
							</tr>
							<tr>
								<th>权限图标</th>
								<td><div class="dropdown">
										<button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu" data-toggle="dropdown"
											aria-haspopup="true" aria-expanded="true">
											<span id="iconAddRole" class="glyphicon glyphicon-leaf" aria-hidden="true"></span><span class="caret"></span>
										</button>
										<ul class="dropdown-menu" aria-labelledby="dropdownMenu">
											<li><a
												href="javascript:$('#iconAddRole').attr('class','glyphicon glyphicon-leaf');$('#sysRoleIcon').val('leaf');"><span
													class="glyphicon glyphicon-leaf" aria-hidden="true"></span></a></li>
											<li><a
												href="javascript:$('#iconAddRole').attr('class','glyphicon glyphicon-eye-open');$('#sysRoleIcon').val('eye-open');"><span
													class="glyphicon glyphicon-eye-open" aria-hidden="true"></span></a></li>
											<li><a
												href="javascript:$('#iconAddRole').attr('class','glyphicon glyphicon-magnet');$('#sysRoleIcon').val('magnet');"><span
													class="glyphicon glyphicon-magnet" aria-hidden="true"></span></a></li>
											<li><a
												href="javascript:$('#iconAddRole').attr('class','glyphicon glyphicon-fire');$('#sysRoleIcon').val('fire');"><span
													class="glyphicon glyphicon-fire" aria-hidden="true"></span></a></li>
											<li><a
												href="javascript:$('#iconAddRole').attr('class','glyphicon glyphicon-globe');$('#sysRoleIcon').val('globe');"><span
													class="glyphicon glyphicon-globe" aria-hidden="true"></span></a></li>
											<li><a
												href="javascript:$('#iconAddRole').attr('class','glyphicon glyphicon-stats');$('#sysRoleIcon').val('stats');"><span
													class="glyphicon glyphicon-stats" aria-hidden="true"></span></a></li>
											<li><a
												href="javascript:$('#iconAddRole').attr('class','glyphicon glyphicon-blackboard');$('#sysRoleIcon').val('blackboard');"><span
													class="glyphicon glyphicon-blackboard" aria-hidden="true"></span></a></li>
											<li><a
												href="javascript:$('#iconAddRole').attr('class','glyphicon glyphicon-bishop');$('#sysRoleIcon').val('bishop');"><span
													class="glyphicon glyphicon-bishop" aria-hidden="true"></span></a></li>
											<li><a
												href="javascript:$('#iconAddRole').attr('class','glyphicon glyphicon-plus');$('#sysRoleIcon').val('plus');"><span
													class="glyphicon glyphicon-plus" aria-hidden="true"></span></a></li>
											<li><a
												href="javascript:$('#iconAddRole').attr('class','glyphicon glyphicon-cloud');$('#sysRoleIcon').val('cloud');"><span
													class="glyphicon glyphicon-cloud" aria-hidden="true"></span></a></li>
											<li><a
												href="javascript:$('#iconAddRole').attr('class','glyphicon glyphicon-heart');$('#sysRoleIcon').val('heart');"><span
													class="glyphicon glyphicon-heart" aria-hidden="true"></span></a></li>
											<li><a
												href="javascript:$('#iconAddRole').attr('class','glyphicon glyphicon-star-empty');$('#sysRoleIcon').val('star-empty');"><span
													class="glyphicon glyphicon-star-empty" aria-hidden="true"></span></a></li>
											<li><a
												href="javascript:$('#iconAddRole').attr('class','glyphicon glyphicon-film');$('#sysRoleIcon').val('film');"><span
													class="glyphicon glyphicon-film" aria-hidden="true"></span></a></li>
											<li><a
												href="javascript:$('#iconAddRole').attr('class','glyphicon glyphicon-signal');$('#sysRoleIcon').val('signal');"><span
													class="glyphicon glyphicon-signal" aria-hidden="true"></span></a></li>
											<li><a
												href="javascript:$('#iconAddRole').attr('class','glyphicon glyphicon-flag');$('#sysRoleIcon').val('flag');"><span
													class="glyphicon glyphicon-flag" aria-hidden="true"></span></a></li>
											<li><a
												href="javascript:$('#iconAddRole').attr('class','glyphicon glyphicon-grain');$('#sysRoleIcon').val('grain');"><span
													class="glyphicon glyphicon-grain" aria-hidden="true"></span></a></li>
											<li><a
												href="javascript:$('#iconAddRole').attr('class','glyphicon glyphicon-scale');$('#sysRoleIcon').val('scale');"><span
													class="glyphicon glyphicon-scale" aria-hidden="true"></span></a></li>
											<li><a
												href="javascript:$('#iconAddRole').attr('class','glyphicon glyphicon-asterisk');$('#sysRoleIcon').val('asterisk');"><span
													class="glyphicon glyphicon-asterisk" aria-hidden="true"></span></a></li>
										</ul>
									</div> <input type="hidden" value="" id="sysRoleIcon" name="icon" /></td>
							</tr>
							<tr>
								<th>关联URL</th>
								<td><input id="relaUrl" name="relaUrl" required="" type="text" class="form-control"
									placeholder="/account/sysuser" value="" aria-describedby="sizing-addon3"></td>
							</tr>
							<tr>
								<th>权重</th>
								<td><input id="weight" name="weight" type="number" required class="form-control" placeholder="note"
									value="" type="text" min="1" max="999" aria-describedby="sizing-addon3"></td>
							</tr>
							<tr>
								<th>备注</th>
								<td><input id="note" name="note" type="text" class="form-control" placeholder="note" value=""
									aria-describedby="sizing-addon3"></td>
							</tr>
						</table>
					</div>
					<div class="modal-footer">
						<input type="submit" value="提交" class="btn btn-primary" />
						<button class="btn" data-dismiss="modal" aria-hidden="false">关闭</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	<input type="hidden" value="${ctx}/account/sysrole/delSysRole" id="delSysRoleUrl" />
	<input type="hidden" value="${ctx}/account/sysrole/updateSysRole" id="updateSysRoleUrl" />
</body>
</html>