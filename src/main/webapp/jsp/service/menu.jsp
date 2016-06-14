<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/head/header.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<script src="${ctx }/static/js/menu.js"></script>
<title>菜单管理</title>
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
					<button type="button" class="btn btn-success myButton" onclick="javascript:addFirstMenu();">增加一级菜单</button>
					<button type="button" class="btn btn-info myButton" onclick="javascript:addSecondMenu();">增加二级菜单</button>
					<button type="button" class="btn btn-primary myButton" onclick="javascript:syncRemoteMenu();">同步菜单到公众号</button>
					<table class="table table-hover ">
						<thead>
							<th class="text-nowrap">菜单#</th>
							<th class="text-nowrap">菜单名称（一级菜单最多4个字，二级菜单最多7个字）</th>
							<th class="text-nowrap">菜单级别</th>
							<th class="text-nowrap">菜单参数值</th>
							<th class="text-nowrap">创建时间</th>
							<th class="text-nowrap">更新时间</th>
							<th class="text-nowrap">菜单状态</th>
							<th class="text-nowrap">更新</th>
							<th class="text-nowrap">删除</th>
						</thead>
						<tbody>
							<c:forEach items="${allMenus}" var="menuOne">
								<%-- <c:if test="${menuOne.menuLevel==1 }"> --%>
								<tr>
									<td class="text-nowrap">${menuOne.menuId }</td>
									<td class="text-nowrap"><input id="menuName${menuOne.menuId }" class="form-control linput-g" type="text"
										placeholder="menu name" value="${menuOne.menuName }"></td>
									<td class="text-nowrap">${menuOne.menuLevel }</td>
									<c:if test="${menuOne.menuLevel==1 }">
										<td class="text-nowrap">一级菜单无需配置参数值<input id="paramValue${menuOne.menuId }" class="form-control linput-g" type="hidden"
											placeholder="menu param value" value="${menuOne.paramValue }"></td>
									</c:if>
									<c:if test="${menuOne.menuLevel==2 }">
										<td class="text-nowrap"><input id="paramValue${menuOne.menuId }" class="form-control linput-g" type="text"
											placeholder="menu param value" value="${menuOne.paramValue }"></td>
									</c:if>
									<td class="text-nowrap"><fmt:formatDate value="${menuOne.createTime }" pattern="yyyy/MM/dd" /></td>
									<td class="text-nowrap"><fmt:formatDate value="${menuOne.updateTime }" pattern="yyyy/MM/dd HH:mm:ss" /></td>
									<td class="text-nowrap"><input type="radio" name="menuState${menuOne.menuId }" value="1"
										<c:if test="${menuOne.menuState==1}">checked</c:if>>有效</input> <input type="radio"
										name="menuState${menuOne.menuId }" value="2" <c:if test="${menuOne.menuState==2}">checked</c:if>>无效</input></td>
									<td class="text-nowrap"><input class="btn btn-info btn-xs" type="button" value="更新"
										onclick='javascript:updateMenu(${menuOne.menuId });'></td>
									<td class="text-nowrap"><a href="javascript:delMenu(${menuOne.menuId },0)"><span class="glyphicon glyphicon-remove"
											aria-hidden="true"></span></a></td>
								</tr>
								<%-- <c:forEach items="${allMenus}" var="menuSecond">
										<c:if test="${menuSecond.parentMenuId==menuOne.menuId }">
											<tr>
												<td>${menuOne.menuId }</td>
												<td><input id="menuName${menuSecond.menuId }" class="form-control linput-g" type="text" placeholder="menu name"
													value="${menuSecond.menuName }"></td>
												<td>${menuSecond.menuLevel }</td>
												<td><input id="paramValue${menuSecond.menuId }" class="form-control linput-g" type="text" placeholder="menu param value"
													value="${menuSecond.paramValue }"></td>
												<td><fmt:formatDate value="${menuSecond.createTime }" pattern="yyyy/MM/dd" /></td>
												<td><fmt:formatDate value="${menuSecond.updateTime }" pattern="yyyy/MM/dd HH:mm:ss" /></td>
												<td><input type="radio" name="menuState${menuSecond.menuId }" value="1" <c:if test="${menuSecond.menuState==1}">checked</c:if>>有效</input>
													<input type="radio" name="menuState${menuSecond.menuId }" value="2" <c:if test="${menuSecond.menuState==2}">checked</c:if>>无效</input></td>
												<td><input class="btn btn-info btn-xs" type="button" value="更新" onclick='javascript:updateMenu(${menuSecond.menuId });'></td>
												<td><a href="javascript:delMenu(${menuSecond.menuId },${menuSecond.parentMenuId })"><span class="glyphicon glyphicon-remove"
														aria-hidden="true"></span></a></td>
											</tr>
										<%-- </c:if> --%>
								<%-- </c:forEach>  --%>
								<%-- </c:if> --%>
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
								<form action="${ctx}/service/menu" method="post">
									<span style="font-family: 微软雅黑; font-size: 16px;"> 每页显示<input style="width: 50px;" type="number" min="1"
										max="${rows }" value="${pageSize }" name="pageSize" />条 <input class="btn btn-info btn-xs" type="submit"
										onclick="javascript:$('#pageNO').val(1);" value="首页" /> <input class="btn btn-info btn-xs" type="submit"
										onclick="javascript:$('#pageNO').val(${pageNO-1});" value="上一页" /> <input class="btn btn-info btn-xs"
										type="submit" onclick="javascript:$('#pageNO').val(${pageNO + 1});" value="下一页" /> <input
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
	<!-- 新增菜单弹出框 -->
	<div id="menuInfo" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="menuInfoModalLabel"
		data-backdrop="static">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<form id="menuInfoForm" action="${ctx}/service/menu/addMenuItem" method="post">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3 id="menuInfoModalLabel">添加一级菜单</h3>
					</div>
					<div class="modal-body">
						<div class="form-group">
							<label for="menuName">菜单名</label> <input type="text" class="form-control" name="menuName" required=""
								id="menuName" placeholder="menu name">
						</div>
						<div class="form-group" id="paramValueInput">
							<label for="paramValue">参数值</label> <input type="text" class="form-control" name="paramValue" required=""
								id="paramValue" placeholder="http://117.79.224.80:8080/garden/h5client/index">
						</div>
						<select id="secondMenuSelect" class="form-control" name="parentMenuId">
						</select>
						<div class="alert alert-warning" role="alert" id="alertWarn"></div>
					</div>
					<input type="hidden" name="menuLevel" id="menuLevel" value="1" />
					<div class="modal-footer">
						<input type="submit" value="提交" class="btn btn-primary" />
						<button class="btn" data-dismiss="modal" aria-hidden="false">关闭</button>
					</div>

					<input type="hidden" name="menuType" id="menuType" value="1" /> <input type="hidden" name="userId" id="userId"
						value="${user.userId }" /> <input type="hidden" name="menuState" id="menuState" value="1" />
				</form>
			</div>
		</div>
	</div>
	<input type="hidden" value="${ctx}/service/menu/getMenus" id="getMenusUrl" />
	<input type="hidden" value="${ctx}/service/menu/delMenu" id="delMenuUrl" />
	<input type="hidden" value="${ctx}/service/menu/updateMenu" id="updateMenuUrl" />
	<input type="hidden" value="${ctx}/service/menu/getMenuList" id="getMenuListUrl" />
	<input type="hidden" value="${ctx}/service/menu/syncRemoteMenu" id="syncRemoteMenuUrl" />
</body>
</html>
<script>
	$(function() {
		initMenuTree();
	});
</script>