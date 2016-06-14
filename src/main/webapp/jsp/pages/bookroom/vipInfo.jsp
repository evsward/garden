<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="/head/h5header.jsp"%>
<title>春晖园温泉度假酒店-我的会员卡</title>
</head>

<body>
	<div class="page">
		<div class="header">
			<a class="return" href="javascript:history.go(-1);">返回</a>
			<div class="title">我的会员卡</div>
			<a class="home" href="${ctx }/h5client/index">主页</a>
		</div>
		<div class="main">
			<div class="myOrder">
				<div class="tab-bd">
					<div class="list">
						<ul>
							<c:forEach items="${members }" var="info" varStatus="idx">
								<li>
									<div class="hd">
										<span>会员卡卡号&nbsp;&nbsp;${info.accountID }</span>
									</div>
									<div class="bd">
										<table width="100%">
											<tr>
												<td width="25%">卡名称</td>
												<td width="20%">卡等级</td>
												<td width="35%">卡类型</td>
												<td width="20%">卡余额</td>
											</tr>
											<tr>
												<td width="25%">${info.genericName }</td>
												<td width="20%">${info.programCode }</td>
												<td width="35%">${info.programTypeDescript }</td>
												<td width="20%">￥${info.balance }</td>
											</tr>
										</table>
									</div>
								</li>
							</c:forEach>
							<!-- <li>
							 <p class="hd">
							     <div align="center"><h1>温馨提示</h1></div> 
							 </p>
							 <p class="bd">
							    为了让每一位持VIP卡的客户，能够更加便捷的享受我方提供的线上优质服务，烦请各持卡客户（金卡、白金卡、无限卡），于2015年11月15日-2015年12月15日在春晖园度假酒店前台办理VIP卡实名认证手续，对此造成的不便，我公司深表歉意！
							    <br>
							    <br>
								<h4>办理时间：</h4>
								11月15日-12月15日
								<p>09：00-18：00</p>
								<br>
								<br>
								<h4>办理地点：</h4>
								春晖园度假酒店前台<br>
								
							 </p>
							</li> -->
						</ul>
					</div>
				</div>
			</div>
		</div>
		<div class="h110"></div>
		<div class="naver">
			<ul class="clearfix">
				<li><a href="${ctx }/h5client/index"> <i class="i1"><img src="${ctx }/static/images/img7.png"></i> <strong>首 页</strong></a></li>
				<li><a href="${ctx }/h5client/index/user"> <i class="i2"><img src="${ctx }/static/images/img8.png"></i> <strong>会 员</strong></a></li>
				<li><a href="${ctx }/h5client/activity"> <i class="i3"><img src="${ctx }/static/images/img9.png"></i> <strong>活 动</strong></a></li>
			</ul>
		</div>
	</div>
</body>
</html>
