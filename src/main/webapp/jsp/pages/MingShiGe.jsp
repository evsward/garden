<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="/head/h5header.jsp"%>
<title>春晖园温泉度假酒店-名仕阁</title>
</head>

<body>
<div class="page">
    <div class="header"> <a class="return" href="javascript:history.go(-1);">返回</a>
        <div class="title">名仕阁</div>
    </div>
    <div class="main">
        <div class="hotelInfo">
            <div class="head"><img src="${ctx }/static/images/img_chymsgct_01_02.png"></div>
            <div class="body">
                <div class="txtCon">
                    <h3>名仕阁</h3>
                    <h4>营业时间：</h4>
                    <p>11：00----14：00<br>
                        17：30----21：00（周末延迟1小时）</p>
                    <br>
                    <h4>订餐电话：</h4>
                    <p>010-69454433转5600、5678</p>
                    <br>
                    <p>名仕阁经营粤菜、川菜、京郊菜、鲁菜为主，店内31个个性洋溢的包房，可容纳360人同时就餐的主题大厅，西式气息华丽典雅的丽晶殿（可容纳200人同时就餐），名仕阁总体可容纳800人同时用餐，在这京城的餐饮业是非长之手笔。现代豪华的欧洲装饰风格,配上独具特色的西式服务,成功地打破了原有会议中心的传统格局,造就了北京会议行业当中的又一大亮点。从小型的朋友聚会，到大气磅礴的商务餐会、喜寿宴请皆可罗纳于其内。</p>
                    <div class="imgs clearfix">
                        <img alt="" src="${ctx }/static/images/img_chymsgct_01_20.jpg">
                    </div>
                    <p class="imgs clearfix" style=" padding-bottom:10px;">
                        <img src="${ctx }/static/images/img_chymsgct_01_13.png" >
                        <img src="${ctx }/static/images/img_chymsgct_01_10.png" style="margin-left: 5px;">
                    </p>
                    <p>华贵包房</p>
                  <div class="imgs clearfix">
                        <img alt="" src="${ctx }/static/images/img_chymsgct_01_19.jpg">
                    </div>
                    <p>私厨粤菜</p>
                    <p class="imgs clearfix" style=" padding-bottom:10px;">
                        <img src="${ctx }/static/images/img_chymsgct_01_08.png" >
                        <img src="${ctx }/static/images/img_chymsgct_01_22.jpg" style="margin-left: 5px;">
                    </p>
                    <p>齐鲁佳肴</p>
                    <p class="imgs clearfix" style=" padding-bottom:10px;">
                        <img src="${ctx }/static/images/img_chymsgct_01_14.png" >
                        <img src="${ctx }/static/images/img_chymsgct_01_18.png" style="margin-left: 5px;">
                    </p>
                    <p>驰名烧味</p>
                    <p class="imgs clearfix" style=" padding-bottom:10px;">
                        <img src="${ctx }/static/images/img_chymsgct_01_23.jpg" >
                        <img src="${ctx }/static/images/img_chymsgct_01_17.png" style="margin-left: 5px;">
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
