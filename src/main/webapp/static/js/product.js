function uploadIconFile(productId) {
	$('#loading' + productId).show();
	$('#showImgSRC' + productId).hide();

	var uploadIconFileUrl = $("#uploadIconFileUrl").val();
	var fd = new FormData();
	var file = $('#fileHidden' + productId)[0].files[0];
	fd.append("iconImg", file);
	fd.append("productId", productId);
	$.ajax({
		url : uploadIconFileUrl,
		type : 'POST',
		data : fd,
		processData : false,// 用来回避jquery对formdata的默认序列化，XMLHttpRequest会对其进行正确处理
		contentType : false,// 设为false才会获得正确的conten-Type
		xhr : function() { // 用以显示上传进度
			var xhr = $.ajaxSettings.xhr();
			if (xhr.upload) {
				xhr.upload.addEventListener('progress', function(e) {
					$('#loading' + productId).html(e.loaded / e.total * 100 + "%");
				}, false);
			}
			return xhr;
		},
		async : true,
		success : function(data) {
			var msg = eval('(' + data + ')');
			$('#showImgSRC' + productId).val(msg);
			$('#loading' + productId).hide();
			$('#showImgSRC' + productId).show();
			$("#warnModalMsg").html("上传产品图片成功！");
			$('#warn').modal('show');
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$('#loading' + productId).hide();
			$('#showImgSRC' + productId).show();
			$("#warnModalMsg").html("上传产品图片失败！");
			$('#warn').modal('show');
		}
	});
}

function updateProduct(productId) {
	var updateProductUrl = $("#updateProductUrl").val();
	var productState = $('input[name="productState' + productId + '"]:checked').val();
	var productName = $("#productName" + productId).val();
	var typeCode = $("#typeCode" + productId).val();
	var codeDescription = $("#codeDescription" + productId).val();
	var unitPrice = $("#unitPrice" + productId).val();
	var goldPrice = $("#goldPrice" + productId).val();
	var infinitePrice = $("#infinitePrice" + productId).val();
	var workdayPrice = $("#workdayPrice" + productId).val();
	var holidayPrice = $("#holidayPrice" + productId).val();
	var silverPrice = $("#silverPrice" + productId).val();
	var inventory = $("#inventory" + productId).val();
	var notes = $("#notes" + productId).val();
	var validDay = $('input[name="validDay' + productId + '"]:checked').val();
	if (notes == null || notes == "") {
		notes = $("#userName").val();
	}
	$.ajax({
		type : "POST",
		url : updateProductUrl,
		data : {
			"productState" : productState,
			"productName" : productName,
			"typeCode" : typeCode,
			"codeDescription" : codeDescription,
			"inventory" : inventory,
			"notes" : notes,
			"validDay" : validDay,
			"productId" : productId,
			"unitPrice" : unitPrice,
			"goldPrice" : goldPrice,
			"infinitePrice" : infinitePrice,
			"workdayPrice" : workdayPrice,
			"holidayPrice" : holidayPrice,
			"silverPrice" : silverPrice
		},
		success : function(msg) {
			$("#warnModalMsg").html("更新产品成功！");
			$('#warn').modal('show');
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$("#warnModalMsg").html("更新产品出错！");
			$('#warn').modal('show');
		}
	});
}
function addHotSpringProduct() {
	var getProductColumnsUrl = $("#getProductColumnsUrl").val();
	$("#unitPriceLabel").html("单价");
	$("#validDayInfo").show();
	$("#productType").val(2);
	$("#productInfoModalLabel").html("添加温泉会产品");
	$.ajax({
		type : "POST",
		url : getProductColumnsUrl,
		data : "columnId=5",
		success : function(msg) {
			$("#productColumnSelect").empty();
			var data = eval('(' + msg + ')');
			for ( var i in data) {
				$("#productColumnSelect").append("<option value ='" + data[i].columnId + "'>" + data[i].columnName + "</option>");
			}
			$('#productInfo').modal('show');
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$("#warnModalMsg").html("获取产品栏目出错！");
			$('#warn').modal('show');
		}
	});
}
function addHotelProduct() {
	var getProductColumnsUrl = $("#getProductColumnsUrl").val();
	$("#unitPriceLabel").html("门市价");
	$("#justForHotel1").show();
	$("#justForHotel2").show();
	$("#justForHotel3").show();
	$("#justForHotel4").show();
	$("#justForHotel5").show();
	$("#productType").val(1);
	$("#validDayInfo").hide();
	$("#productInfoModalLabel").html("添加客房产品");
	$.ajax({
		type : "POST",
		url : getProductColumnsUrl,
		data : "parentId=2",
		success : function(msg) {
			$("#productColumnSelect").empty();
			var data = eval('(' + msg + ')');
			for ( var i in data) {
				$("#productColumnSelect").append("<option value ='" + data[i].columnId + "'>" + data[i].columnName + "</option>");
			}
			$('#productInfo').modal('show');
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$("#warnModalMsg").html("获取产品栏目出错！");
			$('#warn').modal('show');
		}
	});
}