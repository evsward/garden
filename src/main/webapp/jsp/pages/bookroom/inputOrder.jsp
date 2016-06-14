<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="/head/h5header.jsp"%>
<title>春晖园温泉度假酒店-在线预订客房</title>
</head>

<body>
	<div class="page">
		<div class="header">
			<a class="return" href="javascript:history.go(-1);">返回</a>
			<div class="title">在线预订客房</div>
		</div>
		<div class="main">
			<form action="${ctx }/h5client/bookroom/orderFinish" method="post" id="mainForm" onsubmit="return check();">
				<div class="orderInput">
					<div class="head2">
						<ul>
							<li><a href="${ctx }/h5client/bookroom/timeChoose?return_url=/h5client/bookroom/inputOrder%3fproductId=${product.productId}"><span
									class="s1">入住 <fmt:formatDate value="${checkinDate }" pattern="MM/dd" /></span><span class="s2">离店 <fmt:formatDate
											value="${checkoutDate }" pattern="MM/dd" />
								</span><span class="s3">${nights }晚</span></a></li>
							<li><a href="${ctx }/h5client/bookroom"> <span class="s1">${product.productName }</span><span class="s2">&nbsp;</span></a><span class="s3">
									<input type="button" class="add" value="+" id="add" /> <input type="number" value="1" min="1" max="99" id="amounts" class="box"
									name="amounts" class="required" placeholder="1" readonly="readonly" required /> <input type="button" class="add" value="-" id="sub" />间
							</span></li>
						</ul>
					</div>
					<div class="body">
						<ul>
							<li><label>预订人</label> <input id="submitterName" name="submitterName" required value="${reserveOrder.submitterName }" type="text"
								class="text required" placeholder="请输入真实姓名"></li>
							<li><label>手机</label> <input id="phoneNumber" type="text" required name="submitterMobile" value="${reserveOrder.submitterMobile }"
								type="text" class="text required" placeholder="请输入手机号"></li>
							<li><label>支付方式</label> <select name="payMethod">
									<option value="WXpay">微信支付</option>
									<c:forEach items="${members }" var="info" varStatus="idx">
										<option value="${info.accountID }">${info.genericName }</option>
									</c:forEach>
							</select></li>
							<li><label>特殊要求</label> <textarea name="specialNotes" value="${reserveOrder.specialNotes }" placeholder="选填，可以告诉卖家您对商品的特殊需求。">无</textarea></li>
						</ul>
						<input type="hidden" value="${product.productId }" name="productId"> <input type="hidden" value="${ctx}/h5client/bookroom/checkVip"
							id="checkVipUrl" /> <input type="hidden" value="${checkinDate }" name="checkinDate"> <input type="hidden" value="${totalMoney }"
							name="totalMoney" id="totalMoney"> <input type="hidden" value="${checkoutDate }" name="checkoutDate"> <input type="hidden"
							value="${nights }" name="nights"> <input type="hidden" value="${vipType }" name="vipType">
					</div>
					<div class="foot">
						<p>注：如需发票，请到酒店前台索取。</p>
					</div>
					<div class="bottom clearfix">

						<div class="lft">
							订单总额：<strong id="totalMoneyShow">￥${totalMoney }</strong><span><a href="javascript:;" class="" id="btn1">明细</a></span>
						</div>
						<div class="rgt">
							<input type="submit" class="submit" value="提交订单" />
						</div>
					</div>
				</div>
			</form>
		</div>
		<div class="h120"></div>
		<div class="naver">
			<ul class="clearfix">
				<li><a href="${ctx }/h5client/index"> <i class="i1"><img src="${ctx }/static/images/img7.png"></i> <strong>首 页</strong></a></li>
				<li><a href="${ctx }/h5client/index/user"> <i class="i2"><img src="${ctx }/static/images/img8.png"></i> <strong>会 员</strong></a></li>
				<li><a href="${ctx }/h5client/activity"> <i class="i3"><img src="${ctx }/static/images/img9.png"></i> <strong>活 动</strong></a></li>
			</ul>
		</div>
	</div>
	<!--弹出-->
	<div class="dialog_blank"></div>
	<div class="dialog" id="detail">
		<a href="javascript:void(0);" class="dialog_close">关闭</a>
		<div class="dialog_body">
			<ul class="list1">
				<li>费用明细</li>
				<li><strong id="vipTypeShow">${vipType }</strong></li>
				<li class="clearfix"><em>房费</em><span id="totalMoneyPopSpan">￥ ${totalMoney }</span></li>
			</ul>
			<ul class="list2">
				<c:forEach items="${productDetails}" var="oneDay" varStatus="idx">
					<li><span>￥${oneDay.unitPrice} * 1</span> <fmt:formatDate value="${oneDay.validFrom }" pattern="yyyy-MM-dd EEEE" /></li>
				</c:forEach>
			</ul>
			<div class="ft">
				<p>
					房间数<strong id="roomNum"> * 1</strong>
				</p>
				<p>
					总额<strong id="totalMoneyPop">￥ ${totalMoney }</strong>
				</p>
			</div>
		</div>
	</div>
	<script src="${ctx }/static/js/inputOrder.js"></script>
</body>
</html>
