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
						<i><img src="${ctx }/static/images/img14.png"></i>下单完成
					</h3>
					<div class="con">
						<div style="text-align: center;">
							<a class="payBtn" href="#" onclick="payNow();"
								style="display: block; width: 207px; margin-left: 136.5px; height: 60px; line-height: 60px; text-align: center; color: #fff; font-size: 32px; border-radius: 8px; background: #47BCC6;">现在支付</a>
						</div>
						<div style="margin:50px 0px 0px 0px">
						<p style="color:#000000;">
							（1）温泉会门票所含内容（园林泡池、湖畔泡池、室内泡池、干、湿桑拿区、石板浴休息区、香薰厅、游泳，免费提供浴巾、拖鞋、按摩服以及洗漱用品）；<br>
							（2）支付成功后，请于三十天内到店消费，逾期作废。<br>
							（3）1.2米以下儿童免费，1.2米（含1.2米）以上按照成人收费。<br>

						</p>
					</div>	
					</div>
					<input type="hidden" value="${reserveId }" id="reserveId" /> <input type="hidden"
						value="${ctx}/h5client/bookroom/payment" id="paymentUrl" />
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
	<script>
		$(".naver li").click(function() {
			$(".naver li").removeClass("on");
			$(this).addClass("on");
		});
	</script>
	<script type="text/javascript">
		function payNow() {
			var reserveId = $("#reserveId").val();
			var paymentUrl = $("#paymentUrl").val();
			$.ajax({
				type : "POST",
				url : paymentUrl,
				data : {
					"reserveOrderId" : reserveId
				},
				success : function(msg) {
					//console.log(msg);
					//alert(msg);
					/**
					 * "appId" ： "wx2421b1c4370ec43b",     //公众号名称，由商户传入     
					   "timeStamp"：" 1395712654",         //时间戳，自1970年以来的秒数     
					   "nonceStr" ： "e61463f8efa94090b1f366cccfbbb444", //随机串     
					   "package" ： "prepay_id=u802345jgfjsdfgsdg888",     
					   "signType" ： "MD5",         //微信签名方式：     
					   "paySign" ： "70EA570631E4BB79628FBCA90534C63FF7FADD89" //微信签名 
					 */
					if (msg != null && msg != "") {
						msg = msg.replace(/packageStr/mg, "package");
					}
					var data = eval('(' + msg + ')');
					function onBridgeReady() {
						WeixinJSBridge.invoke('getBrandWCPayRequest', data, function(res) {
							if (res.err_msg == "get_brand_wcpay_request:ok") {
								alert("支付完成!");
							} else {
								alert("支付失败 原因：" + res.err_msg);
							}
						});
					}
					;

					if (typeof WeixinJSBridge == "undefined") {
						if (document.addEventListener) {
							document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
						} else if (document.attachEvent) {
							document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
							document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
						}
					} else {
						onBridgeReady();
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					$("#warnModalMsg").html("更新系统用户出错！");
					$('#warn').modal('show');
				}
			});
		}
	</script>
</body>
</html>
