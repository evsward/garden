<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="/head/h5header.jsp"%>
<title>春晖园温泉度假酒店</title>
</head>

<body>
	<div class="page">
		<div class="header">
			<a class="return" href="javascript:history.go(-1);">返回</a>
			<div class="title">温泉会预售</div>
			<a class="book3" href="${ctx }/h5client/hotspring/findMyOrders">我的订单</a>
		</div>
		<div class="main">
			<div class="mpList">
				<div class="body">
					<ul>
						<c:forEach items="${allProducts}" var="hot" varStatus="idx">
							<li>
								<div class="thumb">
									<a href="#"><img src="${ctx}/service/hotelProduct/showIconImage?productId=${hot.productId}"></a>
								</div>
								<div class="desc">
									<h3>${hot.productName}</h3>
									<h4>门市价：${hot.workdayPrice }元</h4>
									<p>${hot.notes }</p>
								</div>
								<div class="rgt">
									<p>
										<strong>${hot.unitPrice }</strong><!--<span>/</span>-->(元)
									</p>
									<a href="${ctx }/h5client/hotspring/inputOrder?productId=${hot.productId}" class="btn">立即购买</a>
								</div>
							</li>
						</c:forEach>
					</ul>
				</div>
				<div class="foot">
					<!--<h4>备注：</h4>
					<p>（1）门票所含内容（园林泡池、湖畔泡池、室内泡池、干、湿桑拿区、石板浴休息区、香薰厅、游泳，免费提供浴巾、拖鞋、按摩服以及洗漱用品）；</p>
					<p>（2）支付成功后，请于三十天内到店消费，逾期作废。</p>
					<p>（3）1.2米以下儿童免费，1.2米（含1.2米）以上按照成人收费。</p>-->
					
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
	<script>
		$(".naver li").click(function() {
			$(".naver li").removeClass("on");
			$(this).addClass("on");
		});
	</script>
</body>
</html>
