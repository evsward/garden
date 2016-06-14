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
					<h3 style="margin: 20px" id="CBMsg">订单已生成</h3>
					<a class="payBtn" href="#" onclick="payNow();" id="payButton"
						style="display: block; margin-left: 136.5px; width: 207px; height: 60px; line-height: 60px; text-align: center; color: #fff; font-size: 32px; border-radius: 8px; background: #47BCC6;">VIP卡支付</a>
				</div>
				<div style="margin: 50px 100px 0 100px">
					<p>
						1、入住本酒店须凭有效证件办理入住手续，一人一证。<br> 2、办理入住的时间为14：00时后，您早到可能需要等待。<br> 3、如需取消订单需致电春晖园预订部，法定节假日提前48小时，其他日期（含周末）提前24小时取消。 <br>
						4、若未入住酒店或未在上述规定时间内取消预订的，我们将直接扣除您订房时所缴纳的订房款。
					</p>
				</div>
			</div>
			<input type="hidden" value="${reserveId }" id="reserveId" /> <input type="hidden" value="${vipId }" id="vipId" /> <input type="hidden"
				value="${ctx}/h5client/bookroom/vipPayment" id="vipPaymentUrl" />
		</div>
	</div>
	</div>
	</div>
</body>
<script type="text/javascript">
	function payNow() {
		var vipId = $("#vipId").val();
		var reserveId = $("#reserveId").val();
		var paymentUrl = $("#vipPaymentUrl").val();
		$.ajax({
			type : "POST",
			dataType: 'json',
			url : paymentUrl,
			data : {
				"reserveOrderId" : reserveId,
				"vipId" : vipId
			},
			success : function(data) {
				if (data.result == 1) {
					$("#payButton").hide();
					$("#CBMsg").html("支付成功！");
				} else {
					alert("异常：" + data.msg);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert("请求VIP支付发生错误！");
			}
		});
	}
</script>
</html>
