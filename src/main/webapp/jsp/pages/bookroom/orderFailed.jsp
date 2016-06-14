<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="/head/h5header.jsp"%>
<title>春晖园温泉度假酒店</title>
</head>

<body>
	<div class="page">
		<div class="header">
			<a class="return" href="javascript:history.go(-1);">返回</a>
			<div class="title">订单已完成</div>
			<a class="home" href="${ctx }/h5client/index">主页</a>
		</div>
		<div class="main">
			<div class="orderTip">
				<div class="txtCon">
					<h3>
						<i><img src="${ctx }/static/images/img13.png"></i>预定失败
					</h3>
					<div>
						<p style="font-size: 30px; color: red;">${msg }</p>
					</div>
					<div class="con">
						温馨提示：<br> 1、入住本酒店须凭有效证件办理入住手续，一人一证。<br> 2、办理入住的时间为14：00时后，您早到可能需要等待。<br>
						3、如需取消订单需致电春晖园预订部，法定节假日提前48小时，其他日期（含周末）提前24小时取消。 <br> 4、若未入住酒店或未在上述规定时间内取消预订的，我们将直接扣除您订房时所缴纳的订房款。
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
