function updateSysUser(userId) {
	var userState = $('input[name="userState' + userId + '"]:checked').val();
	var updateUserUrl = $("#updateUserUrl").val();
	var username = $("#username" + userId).val();
	var password = $("#password" + userId).val();
	var realname = $("#realname" + userId).val();
	var mobile = $("#mobile" + userId).val();
	var email = $("#email" + userId).val();
	$.ajax({
		type : "POST",
		url : updateUserUrl,
		data : {
			"userState" : userState,
			"username" : username,
			"password" : password,
			"realname" : realname,
			"mobile" : mobile,
			"email" : email,
			"userId" : userId
		},
		success : function(msg) {
			$("#warnModalMsg").html("更新系统用户成功！");
			$('#warn').modal('show');
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$("#warnModalMsg").html("更新系统用户出错！");
			$('#warn').modal('show');
		}
	});
}
function addSysUserInfo() {
	var addSysUserUrl = $("#addSysUserUrl").val();
	$("#tableInfoModalLabel").html("添加系统用户");
	$("#username").val("");
	$("#password").val("");
	$("#realname").val("");
	$("#mobile").val("");
	$("#email").val("");
	$("#tableInfo").modal("show");
	$('#sysUserInfoForm').attr("action", addSysUserUrl);
}
function modifyMySysUserInfo() {
	var updateMySysUserInfoUrl = $("#updateMySysUserInfoUrl").val();
	var myUserName = $("#myUserName").val();
	var myPassword = $("#myPassword").val();
	var myRealname = $("#myRealname").val();
	var myMobile = $("#myMobile").val();
	var myEmail = $("#myEmail").val();
	$("#tableInfoModalLabel").html("修改个人信息");
	$("#username").val(myUserName);
	$("#password").val(myPassword);
	$("#realname").val(myRealname);
	$("#mobile").val(myMobile);
	$("#email").val(myEmail);
	$("#tableInfo").modal("show");
	$('#sysUserInfoForm').attr("action", updateMySysUserInfoUrl);
}
function configUserRole(userId) {
	var getSysUserRolesUrl = $("#getSysUserRolesUrl").val();
	$("#userIdChoosed").val(userId);
	$.ajax({
		type : "POST",
		url : getSysUserRolesUrl,
		data : "userId=" + userId,
		success : function(msg) {
			$("#userRolesList").empty();
			var data = eval('(' + msg + ')');
			for ( var i in data) {
				$("#userRolesList").append("<tr id=trSysUserRole" + data[i].roleCode + "><td>" + data[i].roleId + "</td><td>" + data[i].roleCode + "</td><td>" + data[i].roleName + "</td></tr>");
			}
			$('#userRoleModal').modal('show');
			$('#userRolesList tr').click(function() {
				$(this).remove();
			});
			$('#allRolesList tr').click(
					function() {
						var roleCodeChoosed = ($(this).attr("id") + "").split("trAllRoles")[1];
						var ifExsit = false;
						$('#userRolesList tr').each(function() {
							if (roleCodeChoosed == ($(this).attr("id") + "").split("trSysUserRole")[1]) {
								ifExsit = true;
								return false;
							}
						});
						if (!ifExsit) {
							$("#userRolesList").append(
									"<tr id=trSysUserRole" + $("#trAllRoles" + roleCodeChoosed).children('td').eq(1).html() + "><td>" + $("#trAllRoles" + roleCodeChoosed).children('td').eq(0).html()
											+ "</td><td>" + $("#trAllRoles" + roleCodeChoosed).children('td').eq(1).html() + "</td><td>"
											+ $("#trAllRoles" + roleCodeChoosed).children('td').eq(2).html() + "</td></tr>");
							$('#userRolesList tr').click(function() {
								$(this).remove();
							});
						}
					});
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$("#warnModalMsg").html("更新系统用户出错！");
			$('#warn').modal('show');
		}
	});
}

function submitSysUserRole() {
	var userId = $("#userIdChoosed").val();
	$('#userRoleModal').modal('hide');
	var updateSysUserRolesUrl = $("#updateSysUserRolesUrl").val();
	var sysUserMainUrl = $("#sysUserMainUrl").val();
	var roleCodes = new Array();
	$('#userRolesList tr').each(function() {
		roleCodes.push(($(this).attr("id") + "").split("trSysUserRole")[1]);
	});
	$.ajax({
		type : "POST",
		traditional : true,
		url : updateSysUserRolesUrl,
		data : {
			'roleCodes' : roleCodes,
			'userId' : userId
		},
		success : function(msg) {
			location.href = sysUserMainUrl;
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$("#warnModalMsg").html("更新系统用户权限出错！");
			$('#warn').modal('show');
		}
	});
}

function delSysUser(userId) {
	var currentUserId = $('input[name="userId"]').val();
	if(userId==currentUserId){
		$("#warnModalMsg").html("不能删除当前用户！");
		$('#warn').modal('show');
		return;
	}
	var delSysUserUrl = $("#delSysUserUrl").val();
	$("#confirm").modal('show');
	$("#confirmModalLabel").html("警告：");
	var username = $("#username" + userId).val();
	$("#confirmModalMsg").html("您确定要删除该用户【" + username + "】吗？");
	$("#affirmSubmit").on('click', function() {
		$.ajax({
			type : "POST",
			url : delSysUserUrl,
			async : false,
			data : "userId=" + userId,
			success : function(msg) {
				$("#confirm").modal('hide');
				location.reload(true);
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				$("#confirm").modal('hide');
				$("#warnModalMsg").html("删除系统用户出错！");
				$('#warn').modal('show');
			}
		});
	});
}