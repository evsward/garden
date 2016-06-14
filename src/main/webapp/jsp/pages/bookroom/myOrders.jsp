<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="/head/h5header.jsp"%>
<title>春晖园温泉度假酒店</title>
</head>

<body>
	<div class="page">
		<div class="header">
			<a class="return" href="javascript:history.go(-1);">返回</a>
			<div class="title">我的订单</div>
			<a class="home" href="${ctx }/h5client/index">主页</a>
		</div>
		<div class="main">
			<div class="myOrder">
				<div class="tab-hd">
					<ul>
						<li><a href="${ctx }/h5client/hotspring/findMyOrders">温泉会</a></li>
						<li class="on"><a href="${ctx }/h5client/bookroom/findMyOrders">酒店</a></li>
						<li><a href="${ctx }/h5client/activity/findMyActivites">活动</a></li>
					</ul>
				</div>
				<div class="tab-bd">
					<div class="list">
						<ul>
							<c:forEach items="${rOrderList }" var="order" varStatus="idx">
								<li>
									<div class="hd">
										<span>订单编号&nbsp;&nbsp;${order.reserveId }</span><strong>￥ ${order.totalMoney }</strong>
									</div>
									<div class="bd">
										<table width="100%">
											<tr>
												<td width="18%">房型大类</td>
												<td width="32%">${order.columnName }</td>
												<td width="18%">房型小类</td>
												<td width="23%">${order.productName }</td>
												<td width="9%"><strong>${order.nights }晚</strong></td>
											</tr>
											<tr>
												<td width="18%">入住日期</td>
												<td width="32%"><strong><fmt:formatDate value="${order.checkinDate }" pattern="MM月dd日" /></strong></td>
												<td width="18%">离店日期</td>
												<td width="23%"><strong><fmt:formatDate value="${order.checkoutDate }" pattern="MM月dd日" /></strong></td>
												<td width="9%">&nbsp;</td>
											</tr>
											<tr>
												<td>到店时间</td>
												<td>18:00之前</td>
												<td>房间数量</td>
												<td><em>${order.amounts }</em></td>
												<td>&nbsp;</td>
											</tr>
											<tr>
												<td>预定手机</td>
												<td>${order.submitterMobile }</td>
												<td>姓<i></i>名
												</td>
												<td>${order.submitterName }</td>
												<td>&nbsp;</td>
											</tr>
											<tr>
												<td>创建时间</td>
												<td><fmt:formatDate value="${order.createTime }" pattern="yyyy.MM.dd HH:mm" /></td>
												<td>状<i></i>态
												</td>
												<td><span> <c:if test="${order.orderState==11 }">初始订单</c:if> <c:if
															test="${order.orderState==12 }">已预订</c:if> <c:if test="${order.orderState==14 }">付款成功</c:if>
														<c:if test="${order.orderState==15 }">已入住</c:if></span></td>
												<td>&nbsp;</td>
											</tr>
										</table>
									</div> <c:if test="${order.orderState==12 && order.qrCodePath!=null }">
										<div class="bd">
											<img style="display: block; margin-left: auto; margin-right: auto;" src="${order.qrCodePath }" width="400"
												height="400" />
										</div>
									</c:if>
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
