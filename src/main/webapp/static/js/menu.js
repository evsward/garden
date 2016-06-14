function initMenuTree() {
	var getMenusUrl = $("#getMenusUrl").val();
	var setting = {
		view : {
			selectedMulti : false,
			fontCSS : {
				'color' : 'red',
				'font-weight' : 'bold',
				'font-family' : '微软雅黑',
				'font-size' : '20px'
			}
		},
		async : {
			enable : true,
			autoParam : [ "id=parentMenuId" ],
			url : getMenusUrl
		},
		callback : {
			// beforeClick:beforeClick,
			// onClick : onClick,
			onAsyncSuccess : zTreeOnAsyncSuccess
		}
	};

	$.fn.zTree.init($("#menuTree"), setting);

}
function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
	var treeObj = $.fn.zTree.getZTreeObj("menuTree");
	var sNodes = treeObj.getNodes();
	console.log(sNodes);
	treeObj.expandNode(sNodes[0], true);
	if (sNodes.length > 1) {
		treeObj.expandNode(sNodes[1], true);
	}
	if (sNodes.length > 2) {
		treeObj.expandNode(sNodes[2], true);
	}
}
function updateMenu(menuId) {
	var updateMenuUrl = $("#updateMenuUrl").val();
	var menuState = $('input[name="menuState' + menuId + '"]:checked').val();
	var menuName = $("#menuName" + menuId).val();
	var paramValue = $("#paramValue" + menuId).val();
	var userId = $("#userId").val();
	$.ajax({
		type : "POST",
		url : updateMenuUrl,
		data : {
			"menuState" : menuState,
			"menuId" : menuId,
			"menuName" : menuName,
			"paramValue" : paramValue,
			"userId" : userId
		},
		success : function(msg) {
			$("#warnModalMsg").html("更新菜单成功！");
			$('#warn').modal('show');
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$("#warnModalMsg").html("更新菜单出错！");
			$('#warn').modal('show');
		}
	});
}
function addFirstMenu() {
	$("#menuInfoModalLabel").html("添加一级菜单");
	$("#alertWarn").html("一级菜单最多三个有效！");
	$("#menuLevel").val(1);paramValueInput
	$("#secondMenuSelect").hide();
	$("#paramValueInput").hide();
	$("#paramValue").removeAttr("required");
	$("#menuInfo").modal('show');
}
function addSecondMenu() {
	var getMenuListUrl = $("#getMenuListUrl").val();
	$("#menuInfoModalLabel").html("添加二级菜单");
	$("#alertWarn").html("请选择依赖的一级菜单!");
	$("#menuLevel").val(2);
	$("#secondMenuSelect").show();
	$("#paramValue").attr("required","");
	$("#paramValueInput").show();
	$.ajax({
		type : "POST",
		url : getMenuListUrl,
		data : "parentMenuId=0",
		success : function(msg) {
			$("#secondMenuSelect").empty();
			var data = eval('(' + msg + ')');
			for ( var i in data) {
				$("#secondMenuSelect").append("<option value ='" + data[i].menuId + "'>" + data[i].menuName + "</option>");
			}
			$("#secondMenuTR").show();
			$("#menuInfo").modal('show');
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$("#warnModalMsg").html("获取一级菜单出错！");
			$('#warn').modal('show');
		}
	});
}
function syncRemoteMenu() {
	var syncRemoteMenuUrl = $("#syncRemoteMenuUrl").val();
	$('#loading').modal('show');
	$.ajax({
		type : "POST",
		url : syncRemoteMenuUrl,
		success : function(msg) {
			$('#loading').modal('hide');
			if (msg == 1) {
				$("#warnModalMsg").html("同步菜单成功！由于客户端缓存的原因，需要24小时后才会在公众账号上展现出来，您也可以通过取消关注公众账号后再次关注的方式立即查看到最新的菜单效果~");
				$('#warn').modal('show');
			} else {
				$("#warnModalMsg").html("同步菜单出错！");
				$('#warn').modal('show');
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$('#loading').modal('hide');
			$("#warnModalMsg").html("同步菜单异常！");
			$('#warn').modal('show');
		}
	});
}
function delMenu(menuId, parentMenuId) {
	var delMenuUrl = $("#delMenuUrl").val();
	$("#confirm").modal('show');
	$("#confirmModalLabel").html("警告：");
	var menuName = $("#menuName" + menuId).val();
	if (parentMenuId == 0) {
		$("#confirmModalMsg").html("您确定要删除该一级菜单【" + menuName + "】以及其所有的子菜单吗？");
	} else {
		$("#confirmModalMsg").html("您确定要删除该菜单【" + menuName + "】吗？");
	}
	$("#affirmSubmit").on('click', function() {
		$.ajax({
			type : "POST",
			url : delMenuUrl,
			async : false,
			data : "menuId=" + menuId + "&parentMenuId=" + parentMenuId,
			success : function(msg) {
				$("#confirm").modal('hide');
				location.reload(true);
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				$("#confirm").modal('hide');
				$("#warnModalMsg").html("删除菜单出错！");
				$('#warn').modal('show');
			}
		});
	});
}