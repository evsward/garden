	<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="/head/h5header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>春晖园温泉度假酒店</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=7" />
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="apple-mobile-web-app-title" content="">
<meta name="format-detection" content="telephone=no">
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
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
					<h3 style="margin:20px">订单已生成</h3>
						<a class="payBtn" href="#" onclick="payNow();"
							style="display: block; margin-left: 136.5px; width: 207px; height: 60px; line-height: 60px; text-align: center; color: #fff; font-size: 32px; border-radius: 8px; background: #47BCC6;">现在微信支付</a>
					</div>
					<div style="margin:50px 100px 0 100px">
					<p>
					1、入住本酒店须凭有效证件办理入住手续，一人一证。<br>
                    2、办理入住的时间为14：00时后，您早到可能需要等待。<br>
                    3、如需取消订单需致电春晖园预订部，法定节假日提前48小时，其他日期（含周末）提前24小时取消。 <br>
                    4、若未入住酒店或未在上述规定时间内取消预订的，我们将直接扣除您订房时所缴纳的订房款。
                    </p>
                    </div>
					</div>
					<input type="hidden" value="${reserveId }" id="reserveId" /> <input type="hidden"
						value="${ctx}/h5client/bookroom/payment" id="paymentUrl" />
				</div>
			</div>
		</div>
	</div>
</body>
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
				alert("请求jsapi发生错误！");
			}
		});
	}
</script>
</html>
