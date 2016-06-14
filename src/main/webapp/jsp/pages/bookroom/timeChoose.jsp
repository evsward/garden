<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="/head/h5header.jsp"%>
<title>春晖园温泉度假酒店</title>
</head>
<body>
	<div class="page">
		<div class="header">
			<a class="return" href="javascript:history.go(-1);">返回</a>
			<div class="title">在线预订客房</div>
			<a class="book3" href="${ctx }/h5client/bookroom/findMyOrders">我的订单</a>
		</div>
		<div class="main">
			<form action="${ctx }/h5client/bookroom/changeTimeChoose" method="post" onsubmit="return check();">
				<div class="orderInput">
					<div class="dateBar">
						<table width="100%">
							<tr>
								<td width="200px">入住时间</td>
								<td><input type="date" class="box" name="user_checkinDate" id="user_checkinDate" min="${startDate }" max="${endDate }" required
									value="${checkinDate }" /></td>
							</tr>
							<tr>
								<td width="200px"><strong>住几晚</strong></td>
								<td><input class="box" name="user_nights" id="user_nights" type="number" required min="1" max="${maxNights }" /></td>
							</tr>
						</table>
					</div>
					<div class="foot2">
						<input type="submit" class="submit" value="完成" />
					</div>
				</div>
				<input type="hidden" name="returnUrl" value="${return_url }" />
			</form>
		</div>
	</div>
	<script>
		var check = function() {
			var user_checkinDate = $("#user_checkinDate").val();
			if (user_checkinDate == null || user_checkinDate == "") {
				alert("请选择入住时间！");
				return false;
			}
			var user_nights = $("#user_nights").val();
			if (user_nights == null || user_nights == "") {
				alert("请填写入住几晚！");
				return false;
			}
			return true;
		}
	</script>
</body>
</html>
