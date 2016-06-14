function updateSysRole(roleId) {
	var updateSysRoleUrl = $("#updateSysRoleUrl").val();
	var roleState = $('input[name="roleState' + roleId + '"]:checked').val();
	var roleName = $("#roleName" + roleId).val();
	var roleCode = $("#roleCode" + roleId).val();
	var relaUrl = $("#relaUrl" + roleId).val();
	var icon = $("#sysRoleIcon" + roleId).val();
	var weight = $("#weight" + roleId).val();
	var note = $("#note" + roleId).val();
	$.ajax({
		type : "POST",
		url : updateSysRoleUrl,
		data : {
			"roleState" : roleState,
			"roleCode" : roleCode,
			"roleName" : roleName,
			"roleId" : roleId,
			"relaUrl" : relaUrl,
			"note" : note,
			"weight" : weight,
			"icon" : icon
		},
		success : function(msg) {
			$("#warnModalMsg").html("更新系统权限成功！");
			$('#warn').modal('show');
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$("#warnModalMsg").html("更新系统权限出错！");
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
function delRole(roleId) {
	var delSysRoleUrl = $("#delSysRoleUrl").val();
	$("#confirm").modal('show');
	$("#confirmModalLabel").html("警告：");
	var roleName = $("#roleName" + roleId).val();
	$("#confirmModalMsg").html("您确定要删除该权限【"+roleName+"】吗？");
	$("#affirmSubmit").on('click', function() {
		$.ajax({
			type : "POST",
			url : delSysRoleUrl,
			async : false,
			data : "roleId=" + roleId,
			success : function(msg) {
				$("#confirm").modal('hide');
				location.reload(true); 
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				$("#confirm").modal('hide');
				$("#warnModalMsg").html("删除系统权限出错！");
				$('#warn').modal('show');
			}
		});
	});
}