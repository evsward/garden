<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="/head/h5header.jsp"%>
<title>春晖园温泉度假酒店-绿茵阁</title>
</head>

<body>
<div class="page">
    <div class="header"> <a class="return" href="javascript:history.go(-1);">返回</a>
        <div class="title">绿茵阁</div>
    </div>
    <div class="main">
        <div class="hotelInfo">
            <div class="head"><img src="${ctx }/static/images/img_chylygct_01_02.png"></div>
            <div class="body">
                <div class="txtCon">
                    <h3>绿茵阁</h3>
                    <h4>营业时间：</h4>
                    <p>早餐：06：30—10：00</p>
                    <p>午餐：11：00－14：00</p>
                    <p>晚餐：17：00－21：00</p>
                    <p>宵夜：21：00－02：00</p>
                    <p>送餐时间：21:00——02:00</p>
                    <br>
                    <h4>订餐电话：</h4>
                    <p>010-69454433转3008、3009</p>
                    <br>
                        <p>绿茵阁餐厅位于度假酒店首层，临窗而坐可远观绿意盎然的园林景致，就餐区配备两部LED大屏幕电视，分别满足就餐者体坛娱乐与新闻资讯的摄取需求。餐厅专聘的顶级大厨为阁下打造的特色菜系，川菜为主的美味佳肴，为阁下演绎美食的魔幻魅力。</p>
                    <!--  <ul class="imgs clearfix">
                       <li><img src="${ctx }/static/images/img37.jpg" width="319" height="213"></li>
                        <li><img src="${ctx }/static/images/img38.jpg" width="319" height="213"></li> 
                    </ul>-->
                    <div class="imgs clearfix">
                        <img alt="" src="${ctx }/static/images/img_chylygct_01_05.png">
                    </div>
                    <p class="imgs clearfix">
                        <img alt="" src="${ctx }/static/images/img_chylygct_01_13.png" class="lft">
                        <img alt="" src="${ctx }/static/images/img_chylygct_01_10.png" style="margin-left: 13px;">
                    </p>
                    <p class="imgs clearfix">
                        <img alt="" src="${ctx }/static/images/img_chylygct_01_08.png" class="lft">
                        <img alt="" src="${ctx }/static/images/img_chylygct_01_14.png" style="margin-left: 13px;">
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
