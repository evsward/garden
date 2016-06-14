<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="/head/h5header.jsp"%>
<title>春晖园温泉度假酒店-明晖阁</title>
</head>

<body>
<div class="page">
    <div class="header"> <a class="return" href="javascript:history.go(-1);">返回</a>
        <div class="title">明晖阁</div>
    </div>
    <div class="main">
        <div class="hotelInfo">
            <div class="head"><img src="${ctx }/static/images/img_chymhgct_01_01.png"></div>
            <div class="body">
                <div class="txtCon">
                    <h3>明晖阁</h3>
                    <h4>营业时间：</h4>
                    <p>上午11：00-14：00<br>
                        晚上17：30-21：00</p>
                    <br>
                    <h4>订餐电话：</h4>
                    <p>010-69454433转1009</p>
                    <br>
                    <p>明晖阁四季火锅有着独特的火锅吃法和潮流，主营高品质的牛肉、锡盟羊肉、海鲜、蔬菜等绿色健康食品，公司的采购团队不停地从世界各地搜寻最新鲜、最健康、无污染的原料，在香港名厨的精心调理下，竭求为顾客提供超乎预期的美食。</p>
                    <div class="imgs clearfix">
                        <img alt="" src="${ctx }/static/images/img_chymhgct_01_04.png">
                    </div>
                    <p class="imgs clearfix" style=" padding-bottom:5px;">
                        <img src="${ctx }/static/images/img_chymhgct_01_17.png">
                        <img src="${ctx }/static/images/img_chymhgct_01_09.png" style="margin-left: 5px;">
                    </p>
                    <h4>极品浓汤底：</h4>
                    <p>含有老鸡（味浓）、猪瘦肉、猪脚、猪皮、金华火腿（猪后腿经腌制放入地中五到七年后），瑶柱，猪棒骨（猪大腿）经大火熬制八小时而成的。口味醇厚，养颜美容，油而不腻，适宜老人和小朋友食用。
                    </p>
                    <p class="imgs clearfix" style=" padding-bottom:5px;">
                        <img src="${ctx }/static/images/img_chymhgct_01_13.png" >
                        <img src="${ctx }/static/images/img_chymhgct_01_12.png" style="margin-left: 5px;" >
                    </p>
                    <h4>雪龙牛肉：</h4>
                    <p>雪龙拥有着如雪花一般美妙的外观及入口即溶的松软肉质，具有世界上最高级别的肉牛品种之一，完全引用了日本最先进的和牛饲养技术，雪龙黑牛与日本和牛一样是听音乐、喝啤酒、吃稻草和谷物的牛，色泽及优美的雪花状纹理、鲜嫩爽滑的口感，会使你回味无穷。
                    </p>
                    <p class="imgs clearfix" style=" padding-bottom:5px;">
                        <img src="${ctx }/static/images/img_chymhgct_01_16.png" >
                        <img src="${ctx }/static/images/img_chymhgct_01_07.png" style="margin-left: 5px;" >
                    </p>
                    <h4>脆肉鲩鱼：</h4>
                    <p>产于广东省中山市，经人工养殖，以黄豆喂养，采取密集养殖法，口感爽脆，久煮不烂；一条鱼做七到八份。其中脆肉鲩鱼腩：有头、腩、通脊；一条鱼可做三份腩；也有特殊客人只点腩的。
                    </p>
                    <p class="imgs clearfix" style=" padding-bottom:5px;">
                        <img src="${ctx }/static/images/img_chymhgct_01_18.png" >
                        <img src="${ctx }/static/images/img_chymhgct_01_19.png" style="margin-left: 5px;" >
                    </p>
                    <h4>锡盟羊肉：</h4>
                    <p>本店的锡盟羊肉品种来自内蒙古锡林郭勒盟大羊原牧民家养的羔羊和大羊（羔羊：8个月的羊为羔羊。大羊：2岁的羊为大羊）。我们所售卖的羊肉特点为无膻味，口感滑嫩，保持羊肉的原味，主要是所有羔羊和大羊均采用放养式，食用青草，宰杀时放血时间极短、分割手法专业，排酸手法独特。
                    <p class="imgs clearfix" style=" padding-bottom:5px;">
                        <img src="${ctx }/static/images/img_chymxmyr_gyr_01.jpg" >
                        <img src="${ctx }/static/images/img_chymxmyr_gyr_02.jpg" style="margin-left: 5px;" >
                    </p>
                    <p class="imgs clearfix" style=" padding-bottom:5px;">
                        <img src="${ctx }/static/images/img_chymxmyr_gyr_03.jpg" >
                        <img src="${ctx }/static/images/img_chymxmyr_gyr_05.jpg" style="margin-left: 5px;" >
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
