$(".naver li").click(function() {
	$(".naver li").removeClass("on");
	$(this).addClass("on");
});

$('.dialog_blank').height($('body').height());
$('#btn1').click(function() {
	$('.dialog_blank').show();
	$('#detail').show();
});
$('.dialog_close, .dialog_goback').click(function() {
	$('.dialog_blank').hide();
	$(this).parents('#detail').hide();
});
var changeAmount = function() {
	var amounts = $("#amounts").val();
	$("#roomNum").html(" * " + amounts);
	var totalMoney = $("#totalMoney").val();
	if(totalMoney*amounts > 5000) {
		return false;
	}
	$("#totalMoneyShow").html("￥" + totalMoney * amounts);
	$("#totalMoneyPopSpan").html("￥" + totalMoney * amounts);
	$("#totalMoneyPop").html("￥" + totalMoney * amounts);
	return true;
};

$("input[name='amounts']").change(changeAmount);

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
   	 alert("至少选择一间");
    }
    $("#amounts").attr("value", num);
    changeAmount();
});

var check = function() {
	var amounts = $("#amounts").val();
	if (amounts == null || amounts == "") {
		alert("请添加房间数！");
		return false;
	}
	var smName = $("#submitterName").val();
	var phoneNum = $("#phoneNumber").val();
	var phoneRe = /^(1[0-9]{10})$/;
	if (!phoneRe.test(phoneNum)) {
		alert("请输入正确的手机号码！");
		return false;
	}
	if (smName == null || smName == "" || phoneNum == "" || phoneNum == null) {
		alert("请输入预订人与手机信息！");
		return false;
	}
	var tMoney = $("#totalMoney").val();
	if (tMoney > 5000) {
		alert("您的消费额度已超出微信单笔交易限额！");
		return false;
	}
	return true;
}
