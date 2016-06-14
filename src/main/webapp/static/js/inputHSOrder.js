$(".naver li").click(function() {
	$(".naver li").removeClass("on");
	$(this).addClass("on");
});
$('.dialog_blank').height($('body').height());
$('#btn1').click(function() {
	$('.dialog_blank').show();
	$('.dialog').show();
});
$('.dialog_close, .dialog_goback').click(function() {
	$('.dialog_blank').hide();
	$(this).parents('.dialog').hide();
});

var changeAmount = function() {
	var amounts =  $("#amounts").val();
	var productUnitPrice = $("#productUnitPrice").val();
	var productName = $("#productName").html();
	$(".list2").empty();
	if(productUnitPrice * amounts > 5000) {
		return false;
	}
	$("#totalMoneyShow").html('￥' + productUnitPrice * amounts);
	$("#totalMoneyPop").html($("#totalMoneyShow").html());
	$("#totalMoneyPopSpan").html($("#totalMoneyShow").html());
	$("input[name='totalMoney']").val(productUnitPrice * amounts);
	for (var i = 0; i < amounts; i++) {
		$(".list2").append("<li><span>￥" + productUnitPrice + " * 1</span>" + productName + "</li>");
	}
	return true;
};

$("#add").click(function() {
	 var num = $("#amounts").val();
	 num++;
	 $("#amounts").attr("value", num);
	 if(!changeAmount()) {
		 alert("您的消费额度已超出微信单笔交易限额！");
		 $("#amounts").attr("value", --num);
	 }
});
$("#sub").click(function() {
   var num = $("#amounts").val();
   if(num > 1) {
      num--; 
   } else {
  	 alert("至少选择一张!");
   }
   $("#amounts").attr("value", num);
   changeAmount();
});


$("input[name='amounts']").blur(function() {
	var amounts = $(this).val();
	var productUnitPrice = $("#productUnitPrice").val();
	var productName = $("#productName").html();
	$(".list2").empty();
	$("#totalMoneyShow").html('￥' + productUnitPrice * amounts);
	$("#totalMoneyPop").html($("#totalMoneyShow").html());
	$("#totalMoneyPopSpan").html($("#totalMoneyShow").html());
	$("input[name='totalMoney']").val(productUnitPrice * amounts);
	for (var i = 0; i < amounts; i++) {
		$(".list2").append("<li><span>￥" + productUnitPrice + " * 1</span>" + productName + "</li>");
	}
});
var check = function() {
	var amounts = $("#amounts").val();
	if (amounts == null || amounts == "") {
		alert("请填写门票数量！");
		return false;
	}
	var smName = $("#submitterName").val();
	var phoneNum = $("#phoneNumber").val();
	if (smName == null || smName == "" || phoneNum == "" || phoneNum == null) {
		alert("请输入预订人与手机信息！");
		return false;
	}
	return true;
}