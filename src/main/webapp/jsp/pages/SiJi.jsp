<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="/head/h5header.jsp"%>
<title>春晖园温泉度假酒店-四季餐厅</title>
</head>

<body>
<div class="page">
    <div class="header"> <a class="return" href="javascript:history.go(-1);">返回</a>
        <div class="title">四季餐厅</div>
    </div>
    <div class="main">
        <div class="hotelInfo">
            <div class="head"><img src="${ctx }/static/images/img_chysjct_01_02.png"></div>
            <div class="body">
                <div class="txtCon">
                    <h3>四季餐厅</h3>
                    <h4>营业时间：</h4>
                    <p>早餐：6:30 - 10:00<br>
                        午餐：11:30 - 14:00<br>
                        晚餐：17:30 - 21:00<br>
                    </p>
                    <br>
                    <p>四季餐厅位于会议中心一层，可同时容纳240人用餐，主要以接待早餐和会议团队餐为主。四季餐厅自助餐汇聚传统中式文化精髓，融合西方元素之典范，四季餐厅中西自助餐，融合中西经典菜系，上百道出品。
</p>
                    <div class="imgs clearfix">
                        <img alt="" src="${ctx }/static/images/img_chysjct_01_05.png">
                    </div>
                    <p class="imgs clearfix" style=" padding-bottom:10px;">
                        <img src="${ctx }/static/images/img_chysjct_01_08.png" >
                        <img src="${ctx }/static/images/img_chysjct_01_14.png" style="margin-left: 5px;">
                    </p>
                    <p class="imgs clearfix" style=" padding-bottom:10px;">
                        <img src="${ctx }/static/images/img_chysjct_01_10.png" >
                        <img src="${ctx }/static/images/img_chysjct_01_13.png" style="margin-left: 5px;">
                    </p>
                    <p class="imgs clearfix" style=" padding-bottom:10px;">
                        <img src="${ctx }/static/images/img_chysjct_01_17.png" >
                        <img src="${ctx }/static/images/img_chysjct_01_18.png" style="margin-left: 5px;">
                    </p>
                </div>
            </div>
        </div>
    </div>
  <div class="h110"></div>
    <div class="naver">
        <ul class="clearfix">
             <li><a href="${ctx }/h5client/index"> <i class="i1"><img src="${ctx }/static/images/img7.png"></i> <strong>首 页</strong></a></li>
                <li><a href="${ctx }/h5client/index/user"> <i class="i2"><img src="${ctx }/static/images/img8.png"></i> <strong>会 员</strong></a></li>
                <li><a href="${ctx }/h5client/activity"> <i class="i3"><img src="${ctx }/static/images/img9.png"></i> <strong>活 动</strong></a></li>
        </ul>
    </div>
</div>
      <script>
         $(".naver li").click(function(){
			 $(".naver li").removeClass("on");
			 $(this).addClass("on");
			 });
      </script>

</body>
</html>

