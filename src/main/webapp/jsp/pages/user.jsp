<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="/head/h5header.jsp"%>
<title>春晖园温泉度假酒店-个人专区</title>
</head>

<body>
<div class="page">


    <div class="header"> <a class="return" href="javascript:history.go(-1);">返回</a>
        <div class="title">个人专区</div>
        </div>


       
        
        <div id="slideBox" class="slideBox">
        <div class="bd">
            <ul>
                <li> <a class="pic" href="#"><img src="${ctx }/static/images/user_banner1.png" /></a> </li>
               <li> <a class="pic" href="#"><img src="${ctx }/static/images/user_banner1.png" /></a> </li>
                <li> <a class="pic" href="#"><img src="${ctx }/static/images/user_banner1.png" /></a> </li>
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
        <div class="msMenu">
         <ul>
         <li><a href="">VIP会员卡</a></li>
            <li><a href="">微信会员卡</a></li>
            
            <!--<li><a href="${ctx }/h5client/bookroom/findVipInfo">VIP会员卡</a></li>
            <li><a href="${ctx }/h5client/bookroom/findVipInfo">微信会员卡</a></li>
            <li><a href="${ctx }/h5client/bookroom/findMyOrders">我的订单</a></li>
            <li><a href="${ctx }/h5client/activity/findMyActivites">我的活动</a></li>-->
        </ul>
        </div>
    </div>
     <div class="main">
            <div class="myOrder">
                <div class="tab-bd">
                    <div class="list">
                        <ul>
                            <c:forEach items="${infos }" var="info" varStatus="idx">
                                <li>
                                    <div class="hd">
                                        <span>编号&nbsp;&nbsp;${info.vipId }</span>
                                    </div>
                                    <div class="bd">
                                        <table width="100%">
                                            <tr>
                                                <td width="18%">会员卡卡号</td>
                                                <td width="32%">${info.idNumber }</td>
                                                <td width="18%">会员卡名称</td>
                                                <td width="23%">${info.vipName }</td>
                                            </tr>
                                        </table>
                                    </div>
                                </li>
                            </c:forEach>
                            <li>
                             <p class="hd">
                                 <div align="center"><h1>温馨提示</h1></div> 
                             </p>
                             <p class="bd2">
                                春晖园温泉度假酒店会员管理功能开发中，全新功能即将上线，给您造成的不便敬请谅解！
                                <br>
                                <br>
                            
                                
                             </p>
                            </li>
                        </ul>
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

