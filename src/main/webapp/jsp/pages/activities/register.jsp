<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="/head/h5header.jsp"%>
<title>春晖园温泉度假酒店-活动报名</title>
</head>
<body>
	<div class="page">
		<div class="header">
			<a class="return" href="javascript:history.go(-1);">返回</a>
			<div class="title">活动报名</div>
		</div>
		<div class="main">
			<form action="${ctx }/h5client/activity/inputFinish" method="post" id="mainForm" onsubmit="return check();">
				<div class="orderInput">
					<div class="body">
						<ul>
							<li><label>家长姓名</label> <input id="submitterName1" name="submitterName1" required type="text" class="text"
								placeholder="请输入真实姓名"></li>
							<li><label>联系方式</label> <input id="submitterMobile" type="text" required name="submitterMobile" class="text"
								type="number" placeholder="请输入手机号"></li>
							<li><label>孩子姓名</label> <input id="submitterName2" name="submitterName2" required type="text" class="text"
								placeholder="请输入真实姓名"></li>
							<li><label>孩子年龄</label> <input id="submitterAge2" name="submitterAge2" required type="number" class="text"
								placeholder="请输入孩子年龄"></li>
							<li><label>选择活动时间</label> <input id="submitterDate1" name="submitterDate1" required type="date" class="text"
								placeholder="请选择日期"></li>
							<li><label>备注说明</label> <textarea name="specialNotes" class="text" style="height: 280px;"
									placeholder="目前活动名额有限，每期仅设100人，（每个名额限一个家庭；1个孩子1-2名家长），报名成功后，请提前电话咨询相关活动事宜。"></textarea></li>
						</ul>
					</div>
					<div class="bottom clearfix">
						<div class="rgt">
							<input type="submit" class="submit" value="提交报名" />
						</div>
					</div>
				</div>
			</form>
		</div>
		<div class="h120"></div>
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
</body>
<script>
	var check = function() {
		var submitterName1 = $("#submitterName1").val();
		var submitterName2 = $("#submitterName2").val();
		var submitterMobile = $("#submitterMobile").val();
		var submitterDate1 = $("#submitterDate1").val();
		var submitterAge2 = $("#submitterAge2").val();
		if (submitterAge2 == null || submitterAge2 == "") {
			alert("请填写孩子年龄！");
			return false;
		}
		if (submitterName1 == null || submitterName1 == "" || submitterName2 == null || submitterName2 == "") {
			alert("请填写全部姓名信息！");
			return false;
		}
		if (submitterMobile == null || submitterMobile == "") {
			alert("请输入联系方式！");
			return false;
		}
		if (submitterDate1 == null || submitterDate1 == "") {
			alert("请选择活动时间！");
			return false;
		}
		return true;
	}
</script>
</html>
