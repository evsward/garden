<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/head/header.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<script src="${ctx }/static/js/product.js"></script>
<title>酒店基础商品管理</title>
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
					<button type="button" class="btn btn-info myButton" onclick="javascript:addHotSpringProduct();">添加温泉会产品</button>
					<h3>温泉会产品：</h3>
					<table class="table table-hover">
						<thead>
							<th class="text-nowrap">#</th>
							<th class="text-nowrap">产品名称</th>
							<th class="text-nowrap">栏目</th>
							<th class="text-nowrap" width="80px">门市价</th>
							<th class="text-nowrap" width="80px">单价</th>
							<th class="text-nowrap">有效日</th>
							<th class="text-nowrap" width="80px">库存</th>
							<th class="text-nowrap" width="280px">门票说明</th>
							<th class="text-nowrap">产品状态</th>
							<th class="text-nowrap">产品图片</th>
							<th class="text-nowrap">更新</th>
						</thead>
						<tbody>
							<c:forEach items="${allProducts}" var="product" varStatus="idx">
								<c:if test="${product.productType==2 }">
									<tr>
										<td class="text-nowrap">${idx.index+1 }</td>
										<td class="text-nowrap"><input id="productName${product.productId }" class="form-control input-sm"
											type="text" placeholder="product name" value="${product.productName }"></td>
										<td class="text-nowrap"><c:forEach items="${allColumn}" var="column">
												<c:if test="${column.columnId==product.columnId }">${column.columnName }</c:if>
											</c:forEach></td>
										<td class="text-nowrap"><input id="workdayPrice${product.productId }" class="form-control input-sm"
											type="text" placeholder="￥" value="${product.workdayPrice }"></td>
										<td class="text-nowrap"><input id="unitPrice${product.productId }" class="form-control input-sm"
											type="text" placeholder="￥" value="${product.unitPrice }"></td>
										<td class="text-nowrap"><input type="radio" name="validDay${product.productId }" value="holiday"
											<c:if test='${product.validDay=="holiday"}'>checked</c:if>>节假日</input> <input type="radio"
											name="validDay${product.productId }" value="workday"
											<c:if test='${product.validDay=="workday"}'>checked</c:if>>工作日</input></td>
										<td class="text-nowrap"><input id="inventory${product.productId }" class="form-control input-sm"
											type="text" placeholder="inventory" value="${product.inventory }"></td>
										<td class="text-nowrap"><input id="notes${product.productId }" class="form-control input-sm" type="text"
											placeholder="notes" value="${product.notes }"></td>
										</td>
										<td class="text-nowrap"><input type="radio" name="productState${product.productId }" value="1"
											<c:if test="${product.productState==1}">checked</c:if>>上架</input> <input type="radio"
											name="productState${product.productId }" value="2" <c:if test="${product.productState==2}">checked</c:if>>下架</input></td>
										<td class="text-nowrap"><input type="file" id="fileHidden${product.productId }" style="display: none"
											accept="image/*" value="${product.iconsrc}"
											onchange="javascript:$('#showImgSRC${product.productId }').val($(this).val());" /> <input type="text"
											style="font-family: 微软雅黑;" value="${product.iconsrc}" id="showImgSRC${product.productId }">
											<div id='loading${product.productId }' style="display: none"></div> <input type="button"
											onclick="javascript:$('#fileHidden${product.productId }').click();" class="btn btn-info btn-xs" value="浏览" />
											<input class="btn btn-info btn-xs" type="button" value="上传图片"
											onclick='javascript:uploadIconFile(${product.productId });'></td>
										<td class="text-nowrap"><input class="btn btn-info btn-xs" type="button" value="更新"
											onclick='javascript:updateProduct(${product.productId });'></td>
									</tr>
								</c:if>
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
								<form action="${ctx}/service/hotSpring" method="post">
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
	<!-- 添加产品弹出框 -->
	<div id="productInfo" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="productInfoModalLabel"
		data-backdrop="static">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<form id="productInfoForm" class="form-horizontal" action="${ctx}/service/hotSpring/addproductItem" method="post">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3 id="productInfoModalLabel">添加客房产品</h3>
					</div>
					<div class="modal-body">
						<div class="form-group">
							<label for="productName" class="col-sm-2 control-label">产品名称</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" name="productName" required="" id="productName"
									placeholder="product name">
							</div>
						</div>
						<div class="form-group">
							<label for="productColumnSelect" class="col-sm-2 control-label">产品栏目</label>
							<div class="col-sm-10">
								<select id="productColumnSelect" class="form-control" name="columnId">
								</select>
							</div>
						</div>
						<div class="form-group">
							<label for="typeCode" class="col-sm-2 control-label">产品编码</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" name="typeCode" required="" id="typeCode" placeholder="typeCode">
							</div>
						</div>
						<div class="form-group">
							<label id="unitPriceLabel" for="unitPrice" class="col-sm-2 control-label">单价</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" name="unitPrice" required="" id="unitPrice" placeholder="unitPrice">
							</div>
						</div>
						<div class="form-group">
							<label for="inventory" class="col-sm-2 control-label">库存</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" name="inventory" required="" id="inventory" placeholder="inventory">
							</div>
						</div>
						<div class="form-group" id="validDayInfo">
							<label for="validDayDiv" class="col-sm-2 control-label">有效日</label>
							<div class="col-sm-10" id="validDayDiv">
								<input type="radio" name="validDay" value="holiday" checked>节假日</input> <input type="radio" name="validDay"
									value="workday">工作日</input>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<input type="submit" value="提交" class="btn btn-primary" />
						<button class="btn" data-dismiss="modal" aria-hidden="false">关闭</button>
					</div>
					<input type="hidden" name="productType" id="productType" value="1" /> <input type="hidden" name="productState"
						id="productState" value="1" />
				</form>
			</div>
		</div>
	</div>
	<input type="hidden" id="userName" value="${user.username }" />
	<input type="hidden" value="${ctx}/service/hotelProduct/uploadIconFile" id="uploadIconFileUrl" />
	<input type="hidden" value="${ctx}/service/hotelProduct/updateProduct" id="updateProductUrl" />
	<input type="hidden" value="${ctx}/service/hotelProduct/getProductColumns" id="getProductColumnsUrl" />
</body>
</html>