<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="/head/h5header.jsp"%>
<title>春晖园温泉度假酒店</title>
</head>

<body>
	<div class="page">
		<div class="header">
			<a class="return" href="javascript:history.go(-1);">返回</a>
			<div class="title">在线预订客房</div>
		</div>
		<div id="slideBox" class="slideBox">
			<div class="bd">
				<ul>
					<li><a class="pic" href="#"><img src="${ctx }/static/images/img11.jpg" /></a></li>
					<li><a class="pic" href="#"><img src="${ctx }/static/images/img11.jpg" /></a></li>
					<li><a class="pic" href="#"><img src="${ctx }/static/images/img11.jpg" /></a></li>
				</ul>
			</div>
			<div class="hd" style="display: none;">
				<ul>
				</ul>
			</div>
		</div>
		<script type="text/javascript">
			TouchSlide({
				slideCell : "#slideBox",
				titCell : ".hd ul", //开启自动分页 autoPage:true ，此时设置 titCell 为导航元素包裹层
				mainCell : ".bd ul",
				effect : "leftLoop",
				autoPage : true,//自动分页
				autoPlay : true,//自动播放
				interTime : 5000
			});
		</script>
		<div class="main">
			<div class="orderList">
				<div class="head">
					<h2>观湖别墅</h2>
					<p>
						<a href="${ctx }/h5client/bookroom/timeChoose?return_url=/h5client/bookroom/viewLakeVillaReservation" class="btn">&nbsp;</a>
						<span class="s1"> 入住 <fmt:formatDate value="${checkinDate }" pattern="MM/dd" />
						</span> <span class="s2"> 离店 <fmt:formatDate value="${checkoutDate }" pattern="MM/dd" /></span> <span class="s3">
							${nights }晚 </span>
					</p>
				</div>
				<div class="body">
					<div class="list">
						<c:forEach items="${allProducts}" var="hotel" varStatus="idx">
							<li>
								<div class="hd">
									<div class="thumb">
										<a href="#"><img src="${ctx}/service/hotelProduct/showIconImage?productId=${hotel.productId}"></a>
									</div>
									<div class="title">
										<table>
											<tr>
												<td>${hotel.productName }</td>
											</tr>
										</table>
									</div>
									<div class="price">
										<span>周末价</span> <strong>￥${hotel.holidayPrice }</strong>
									</div>
									<div class="price">
										<span>非周末价</span> <em>￥${hotel.workdayPrice }</em>
									</div>
									<a class="orderBtn" href="${ctx }/h5client/bookroom/inputOrder?productId=${hotel.productId}">预订</a>
								</div>
							</li>
						</c:forEach>
					</div>
				</div>
			</div>
			<script type="text/javascript">
				$(function() {
					$(".orderList .hd").click(function() {
						$(this).toggleClass("current").siblings(".hd").removeClass(".current");
						$(this).next(".bd").slideToggle(300).siblings(".bd").slideUp(300);
					})
				})//下拉展开
			</script>
		</div>
	</div>
</body>
</html>
