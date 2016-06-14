<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="/head/h5header.jsp"%>
<title>春晖园温泉度假酒店</title>
</head>

<body>
<div class="page">
    <div id="slideBox" class="slideBox">
        <div class="bd">
            <ul>
                <li> <a class="pic" href="#"><img src="${ctx }/static/images/banner_01.png" /></a> </li>
                <li> <a class="pic" href="#"><img src="${ctx }/static/images/banner_02.png" /></a> </li>
                <li> <a class="pic" href="#"><img src="${ctx }/static/images/banner_03.png" /></a> </li>
            </ul>
        </div>
        <div class="hd">
            <ul>
            </ul>
        </div>
    </div>
    <script type="text/javascript">
				TouchSlide({ 
					slideCell:"#slideBox",
					titCell:".hd ul", //开启自动分页 autoPage:true ，此时设置 titCell 为导航元素包裹层
					mainCell:".bd ul", 
					effect:"leftLoop", 
					autoPage:true,//自动分页
					autoPlay:true,//自动播放
					interTime:4000
				});
			</script>
    <div class="main">
        <div class="menu">
            <ul class="clearfix">
                <li><a href="${ctx }/h5client/index/hotelIntro"><span class="ico1"></span> <strong>酒店简介</strong> </a></li>
                <li><a href="${ctx }/h5client/bookroom"><span class="ico2"></span> <strong>客房预订</strong> </a></li>
                <li><a href="${ctx }/h5client/index/foodspotting"><span class="ico3"></span> <strong>美食推荐</strong> </a></li>
                <li><a href="${ctx }/h5client/index/conference"><span class="ico4"></span> <strong>会议服务</strong> </a></li>
                <li><a href="${ctx }/h5client/hotspring"><span class="ico5"></span> <strong>温泉会</strong> </a></li>
                <li><a href="${ctx }/h5client/activity"><span class="ico6"></span> <strong>精彩活动</strong> </a></li>
            </ul>
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
