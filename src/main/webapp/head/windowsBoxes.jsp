<%@page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=750,target-densitydpi=device-dpi,minimum-scale=0.5,maximum-scale=0.5," />
</head>
<body>
	<!-- 警告信息提示弹出框 -->
	<div id="warn" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="warnModalLabel">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
					<h3 id="warnModalLabel">警告</h3>
				</div>
				<div class="modal-body">
					<p class="alert alert-danger" role="alert" id="warnModalMsg">弹出框显示信息主体</p>
				</div>
				<div class="modal-footer">
					<button class="btn" data-dismiss="modal" aria-hidden="false">关闭</button>
				</div>
			</div>
		</div>
	</div>
	<!-- 确认信息提示弹出框 -->
	<div id="confirm" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="confirmModalLabel">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
					<h3 id="confirmModalLabel">确认信息</h3>
				</div>
				<div class="modal-body">
					<p class="alert alert-block alert-error text-info" id="confirmModalMsg"
						style="font-family: 微软雅黑; font-size: 25px; font-weight: bold; color: red;">弹出框显示信息主体</p>
				</div>
				<div class="modal-footer">
					<button class="btn" data-dismiss="modal" aria-hidden="false">关闭</button>
					<button class="btn btn-primary" id="affirmSubmit">确定</button>
				</div>
			</div>
		</div>
	</div>
	<!-- Loading提示弹出框 -->
	<div id="loading" class="progress modal fade">
		<div class="progress-bar progress-bar-striped active" role="progressbar" aria-valuenow="45" aria-valuemin="0"
			aria-valuemax="100" style="width: 100%"></div>
	</div>
</body>
</html>