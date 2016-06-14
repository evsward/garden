<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="/head/h5header.jsp"%>
<title>春晖园温泉度假酒店-我的活动订单</title>
</head>

<body>
	<div class="page">
		<div class="header">
			<a class="return" href="javascript:history.go(-1);">返回</a>
			<div class="title">我的活动订单</div>
			<a class="home" href="${ctx }/h5client/index">主页</a>
		</div>
		<div class="main">
			<div class="myOrder">
				<div class="tab-hd">
					<ul>
						<li><a href="${ctx }/h5client/hotspring/findMyOrders">温泉会</a></li>
						<li><a href="${ctx }/h5client/bookroom/findMyOrders">酒店</a></li>
						<li class="on"><a href="${ctx }/h5client/activity/findMyActivites">活动</a></li>
					</ul>
				</div>
				<div class="tab-bd">
					<div class="list">
						<ul>
							<c:forEach items="${myAOrderList }" var="order" varStatus="idx">
								<li>
									<div class="hd">
										<span>订单编号&nbsp;&nbsp;00000${order.activityOrderId }</span><strong>小鬼当家</strong>
									</div>
									<div class="bd">
										<table width="100%">
											<tr>
												<td width="18%">家长姓名</td>
												<td width="36%">${order.submitterName1 }</td>
												<td width="18%">孩子姓名</td>
												<td width="28%">${order.submitterName2 }</td>
											</tr>
											<tr>
												<td width="18%">活动日期</td>
												<td width="36%"><strong>${order.submitterDate1 }</strong></td>
												<td width="18%">特别说明</td>
												<td width="28%"><strong><c:choose>
															<c:when test="${order.specialNotes == ''||order.specialNotes == null }">报名成功</c:when>
															<c:otherwise>${order.specialNotes}</c:otherwise>
														</c:choose></strong></td>
											</tr>
											<tr>
												<td>联系方式</td>
												<td>${order.submitterMobile }</td>
												<td>孩子年龄</td>
												<td>${order.submitterAge2 }</td>
											</tr>

										</table>
									</div>
								</li>
							</c:forEach>
						</ul>
					</div>
				</div>
			</div>
		</div>
		<div class="h110"></div>
		<div class="naver">
			<ul class="clearfix">
				<li><a href="${ctx }/h5client/index"> <i class="i1"><img src="${ctx }/static/images/img7.png"></i> <strong>首
							页</strong></a></li>
				<li><a href="${ctx }/h5client/index/user"> <i class="i2"><img src="${ctx }/static/images/img8.png"></i>
						<strong>会 员</strong></a></li>
				<li><a href="${ctx }/h5client/activity"> <i class="i3"><img src="${ctx }/static/images/img9.png"></i>
						<strong>活 动</strong></a></li>
			</ul>
		</div>
	</div>
</body>
</html>
