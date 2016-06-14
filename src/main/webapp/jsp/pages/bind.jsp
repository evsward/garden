<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="/head/h5header.jsp"%>
<title>春晖园温泉度假酒店-绑定会员卡</title>
<style type="text/css">
.button {
	display: block;
	display: inline-block;
	font-size: 30px;
	color: white;
	font-weight: bold;
	background-color: grey;
	margin-top: 10px;
	margin-left: 300px;
}
</style>
</head>

<body>
	<div class="page">
		<div class="header">
			<a class="return" href="javascript:history.go(-1);">返回</a>
			<div class="title">绑定会员卡</div>
		</div>
		<div class="main">
			<form action="${ctx }/h5client/index/bindForm" method="post" id="mainForm" onsubmit="return check();">
				<div class="orderInput">
					<div class="body">
						<ul>
							<li><label>VIP卡号</label> <input id="VIPAccountID" name="accountID" required value="" type="text" class="text required"
								placeholder="请输入VIP卡号"></li>
							<li><label>手机号</label> <input id="phoneNumber" type="text" required name="phoneNumber" value=""
								class="text required" placeholder="请输入手机号"><input id="btnSendCode" type="button" class="button" value="发送验证码" onclick="sendMessage()" /></li>
							<li><label>验证码</label> <input id="verifyCode" required name="verifyCode" value="" type="text" class="text required" placeholder="请输入验证码"></li>
						</ul>
						<input type="hidden" value="${ctx}/h5client/index/sendVerifyMsg" id="sendVerifyMsgUrl" />
					</div>
					<div class="foot">
						<p>注：如长时间未接到短信，请稍后再试或联系客服。</p>
					</div>
					<div class="bottom clearfix">
						<div class="rgt">
							<input type="submit" class="submit" value="提交" />
						</div>
					</div>
				</div>
			</form>
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
		var InterValObj; //timer变量，控制时间
		var curCount = 30;//当前剩余秒数
		function sendMessage() {
			var phoneNumber = $("#phoneNumber").val();//用户手机号
			var sendVerifyMsgUrl = $("#sendVerifyMsgUrl").val();//发送短信Url
			var phoneRe = /^(1[0-9]{10})$/;
			if (phoneRe.test(phoneNumber)) {
				//设置button效果，开始计时
				$("#btnSendCode").attr("disabled", "true");
				$("#btnSendCode").val("请在" + curCount + "秒内输入验证码");
				InterValObj = window.setInterval(SetRemainTime, 1000); //启动计时器，1秒执行一次
				//向后台发送处理数据
				$.ajax({
					type : "POST", //用POST方式传输
					url : sendVerifyMsgUrl,
					data : {
						"phoneNumber" : phoneNumber
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
					},
					success : function(msg) {
					}
				});
			} else {
				alert("请输入正确的手机号码！");
			}
		}
		//timer处理函数
		function SetRemainTime() {
			if (curCount == 0) {
				window.clearInterval(InterValObj);//停止计时器
				$("#btnSendCode").removeAttr("disabled");//启用按钮
				$("#btnSendCode").val("重新发送验证码");
			} else {
				curCount--;
				$("#btnSendCode").val("请在" + curCount + "秒内输入验证码");
			}
		}

		$(".naver li").click(function() {
			$(".naver li").removeClass("on");
			$(this).addClass("on");
		});
		var check = function() {
			var verifycodeRe = /^([0-9]{6})$/;
			var phoneRe = /^(1[0-9]{10})$/;
			var phoneNumber = $("#phoneNumber").val();
			var VIPAccountID = $("#VIPAccountID").val();
			var verifyCode = $("#verifyCode").val();
			if (!phoneRe.test(phoneNumber)) {
				alert("请输入正确的手机号码！");
				return false;
			}
			if (!verifycodeRe.test(verifyCode)) {
				alert("请输入正确的验证码！");
				return false;
			}
			if (VIPAccountID==null) {
				alert("请输入VIP卡号！");
				return false;
			}
			return true;
		}
	</script>
</body>
</html>

