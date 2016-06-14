<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="/head/h5header.jsp"%>
<title>春晖园温泉度假酒店</title>
<script src="${ctx }/static/js/mobiscroll.custom-2.6.2.min.js" type="text/javascript"></script>
<link href="${ctx }/static/css/mobiscroll.custom-2.6.2.min.css" rel="stylesheet" type="text/css" />
<style type="text/css">
.dwbg .dwb {
    font-size: 34px!important;
}
.android-ics .dwv {
    font-size:34px!important;
    padding: 20px;
}
.dwbw {
    padding: 20px 0;
}
.android-ics .dww .dw-li
{
    font-size:34px!important;
}
</style>
<script type="text/javascript">
$(document).ready(function() {
	function init() {
	    $('#startTime').mobiscroll().date({
	    	preset:'date',
	        theme: 'android-ics light',
	        mode: 'scroller',
	        minDate:new Date() ,
	        display: 'modal',
	        dateFormat: 'yyyy-mm-dd',
	        //btnClass : 'sqh_color_56 font_16 sqh_line_height_15 margin_10 sqh_font_type',
	        lang: 'zh',
	        dateOrder:'yyyymmdd',
	        endYear:2020,
	        onChange:sel_mobiscroll_change,
	        width:200,
	    });
	    $('#endTime').mobiscroll().date({
	    	preset:'date',
	        theme: 'android-ics light',
	        mode: 'scroller',
	        minDate:new Date() ,
	        display: 'modal',
	        dateFormat: 'yyyy-mm-dd',
	        dateOrder:'yyyymmdd',
	        //btnClass : 'sqh_color_56 font_16 sqh_line_height_15 margin_10 sqh_font_type',
	        lang: 'zh',
	        width:200,
	        endYear:2020,
	        onChange:sel_end_mobiscroll_change
	    });
	}
	
	$("#selDate").click(function(){
	    $("#startTime").mobiscroll("show");
	});
	$("#endSelDate").click(function(){
	    $("#endTime").mobiscroll("show");
	});
	init();
});
function sel_mobiscroll_change(valueText,inst) {
	$('#selDate').text(valueText);
}
function sel_end_mobiscroll_change(valueText,inst) {
	$('#endSelDate').text(valueText);
}

function order(url) {
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();
	if(startTime == "" || endTime == "") {
		alert("日期选择不正确，请重新选择！");
        return;
	}
	var d = new Date(startTime.replace(/-/g,"/")); 
	var d1 = new Date(endTime.replace(/-/g,"/")); 
	if(d1.getTime() - d.getTime() <= 0) {
		alert("日期选择不正确，请重新选择！");
		return;
	}
	location.href = url + "?startTime=" + startTime + "&endTime=" + endTime; 
}
</script>
</head>

<body>
<div class="page">
    <div class="header"> <a class="return" href="javascript:history.go(-1);">返回</a>
        <div class="title">在线预订客房</div>
    </div>
    <div class="main">
        <div class="orderBook">
            <div class="head clearfix">
                <div class="item lft">
                    <p>入住时间</p>
<%--                     <a href="${ctx }/h5client/bookroom/timeChoose?return_url=/h5client/bookroom" class="dateBtn"> <fmt:formatDate value="${checkinDate }" pattern="MM月dd日" /></a> </div> --%>
                    <a href="#" class="dateBtn" id="selDate"> <fmt:formatDate value="${checkinDate }" pattern="yyyy-MM-dd" /></a> </div>
                <div class="item rgt">
                    <p>离店时间</p>
<%--                     <a href="${ctx }/h5client/bookroom/timeChoose?return_url=/h5client/bookroom" class="dateBtn"> <fmt:formatDate value="${checkoutDate }" pattern="MM月dd日" /></a> </div>
 --%>                    <a href="#" class="dateBtn" id="endSelDate"> <fmt:formatDate value="${checkoutDate }" pattern="yyyy-MM-dd" /></a> </div>
            </div>
            <div class="body">
                <div class="lists">
                    <ul>
                        <li class="item">
                            <div class="hd"> 度假酒店 </div>
                            <div class="bd"> <img src="${ctx }/static/images/img50.jpg"> </div>
                            <div class="ft"> <a href="${ctx }/h5client/bookroom/holidayHotelIntro" class="btn lft" >了解更多</a> <a href="#" onclick="order('${ctx }/h5client/bookroom/holidayHotelReservation')" class="btn rgt" >开始预订</a> </div>
                        </li>
                        <li class="item">
                            <div class="hd"> 花园酒店 </div>
                            <div class="bd"> <img src="${ctx }/static/images/img47.jpg"> </div>
                            <div class="ft"> <a href="${ctx }/h5client/bookroom/gardenHotelIntro" class="btn lft" >了解更多</a> <a href="#" onclick="order('${ctx }/h5client/bookroom/gardenHotelReservation')" class="btn rgt" >开始预订</a> </div>
                        </li>
                        <li class="item">
                            <!-- <div class="hd"> 湖畔别墅 </div> -->
                            <div class="hd"> 会所酒店 </div>
                            <div class="bd"> <img src="${ctx }/static/images/img48.jpg"> </div>
                            <div class="ft"> <a href="${ctx }/h5client/bookroom/lakeVillaIntro" class="btn lft" >了解更多</a> <a href="#" onclick="order('${ctx }/h5client/bookroom/lakeVillaReservation')" class="btn rgt" >开始预订</a> </div>
                        </li>
                        <!--
                        <li class="item">
                            <div class="hd"> 观湖别墅 </div>
                            <div class="bd"> <img src="${ctx }/static/images/img49.jpg"> </div>
                            <div class="ft"> <a href="${ctx }/h5client/bookroom/viewLakeVillaIntro" class="btn lft" >了解更多</a> <a href="${ctx }/h5client/bookroom/viewLakeVillaReservation" class="btn rgt" >开始预订</a> </div>
                        </li>-->
                    </ul>
                </div>
            </div>
            <input type="hidden" id="startTime" name="startTime" value="<fmt:formatDate value="${checkinDate }" pattern="yyyy-MM-dd" />"/>
            <input type="hidden" id="endTime" name="endTime" value="<fmt:formatDate value="${checkoutDate }" pattern="yyyy-MM-dd" />"/>
        </div>
    </div>
</div>
</body>
</html>
