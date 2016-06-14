<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="/head/h5header.jsp"%>
<title>春晖园温泉度假酒店-温泉会门票</title>
</head>

<body>
	<div class="page">
		<div class="header">
			<a class="return" href="javascript:history.go(-1);">返回</a>
			<div class="title">温泉会门票</div>
			<a class="book3" href="${ctx }/h5client/hotspring/findMyOrders">我的订单</a>
		</div>
		<div class="main">
			<div class="orderInput">
				<form action="${ctx }/h5client/bookroom/hsorderFinish" method="post" id="mainForm" onsubmit="return check();">
					<div class="head">
						<strong id="productName">${ticket.productName }</strong>&nbsp;&nbsp;&nbsp;&nbsp;<span>
						<input type="button" class="add" value="+" id="add"/> 
						<input type="number" min="1" max="${ticket.inventory }" required value="${reserveOrder.amounts }" 
							name="amounts" id="amounts" class="box" readonly="readonly">
                            <input type="button" class="add"  value="-" id="sub"/>
							</span>
					</div>
					<div class="body">
						<ul>
							<li><label>预订人</label> <input id="submitterName" name="submitterName" required value="${reserveOrder.submitterName }" type="text" class="text" placeholder="请输入真实姓名"></li>
							<li><label>手机</label> <input id="phoneNumber" type="number" required min="10000000000" max="99999999999" name="submitterMobile" value="${reserveOrder.submitterMobile }" type="text" class="text" placeholder="请输入手机号"></li>
							<%-- <li><label>会员卡</label> <input type="number" min="0" max="99999999999" name="vipId" value="${reserveOrder.vipId }" type="text" class="text" placeholder="请输入会员卡号"></li>
							<li><label>密码</label> <input name="password" value="${reserveOrder.password }" type="password" class="text"></li> 
							<li><label>特殊要求</label> <textarea name="specialNotes" value="${reserveOrder.specialNotes }" placeholder="选填，可以告诉卖家您对商品的特殊需求。">无</textarea></li>--%>
						</ul>
						<input type="hidden" value="${ticket.unitPrice }" id="productUnitPrice">
						<input type="hidden" value="${ticket.productId }" name="productId">
						<input type="hidden" value="${ticket.unitPrice }" name="totalMoney">
						<input type="hidden" value="${vipType }" name="vipType">
					</div>
					<div class="foot">
						<p>注：如需发票，请到酒店前台索取。</p>
					</div>
					<div class="bottom clearfix">
						<div class="lft">
							订单总额：<strong  id="totalMoneyShow" >￥${ticket.unitPrice }</strong><span><a href="javascript:;" class="" id="btn1">明细</a></span>
						</div>
						<div class="rgt">
							<input type="submit" class="submit" value="提交订单" />
						</div>
					</div>
				</form>
			</div>
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
	<div class="dialog">
		<a href="javascript:void(0);" class="dialog_close">关闭</a>
		<div class="dialog_body">
			<ul class="list1">
				<li>费用明细</li>
				<li><strong>${vipType }</strong></li>
				<li class="clearfix"><em>消费</em><span id="totalMoneyPopSpan">￥ ${ticket.unitPrice }</span></li>
			</ul>
			<ul class="list2">
			</ul>
			<div class="ft">
				<p>
					总额<strong id="totalMoneyPop">￥ ${ticket.unitPrice }</strong>
				</p>
			</div>
		</div>
	</div>
	<script src="${ctx }/static/js/inputHSOrder.js"></script>
</body>
</html>
