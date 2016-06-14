<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/head/header.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>登录</title>
</head>
<body>
	<div class="container">
		<div class="col-lg-4 col-lg-offset-4 col-sm-6 col-sm-offset-3 col-xs-8 col-xs-offset-2" id="logindev">
			<div class="panel panel-primary">
				<div class="panel-body">
					<form class="form" action="${ctx}/account/index/checkLogin" method="post">
						<h2 style="font-weight: bold; font-family: 微软雅黑; font-size: 24px; padding: 1px 5px 1px 5px; text-align: center;">登
							录</h2>
						<label for="username" class="sr-only">账号：</label> <input type="text" id="username" name="username" value=""
							class="form-control" placeholder="Username" required="" autofocus=""> <label for="password"
							class="sr-only">密码：</label> <input type="password" id="password" name="password" value=""
							class="form-control" placeholder="Password" required="">
						<button class="btn btn-lg btn-primary btn-block" type="submit">提交</button>
						<c:if test="${sysError!=null }">
							<div class="alert alert-warning" role="alert">${sysError }</div>
						</c:if>
						<input type="hidden" id="nextUrl" value="${nextUrl }">
					</form>
				</div>
				<div class="panel-footer">
					<p class="text-right">
						<em
							style="font-weight: bold; text-align: center; color: #30AED4; font-family: 微软雅黑; font-size: 18px; padding: 1px 5px 1px 5px;">
							春晖园温泉度假酒店 </em>
					</p>
				</div>
			</div>
		</div>
	</div>
</body>
</html>