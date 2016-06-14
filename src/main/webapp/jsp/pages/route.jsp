<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<style type="text/css">
body, html {
	width: 100%;
	height: 100%;
	margin: 0;
	font-family: "微软雅黑";
}

#l-map {
	height: 80%;
	width: 100%;
}

#r-result, #r-result table {
	height: 20%;
	width: 100%;
	font-size: 12px;
}
#bu-header{
	margin:5px;
}
.button {
	display: block;
	display: inline-block;
	height: 30px;
	font-size: 18px;
	color: white;
	font-weight: bold;
	background-color: blue;
}
</style>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=Y11e5fDZvfWjAuQFGZ3lCEs3"></script>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script src="${ctx }/static/js/jquery-2.0.0.min.js"></script>
<title>路线规划</title>
</head>
<body>
	<div id="bu-header">
		<input type="button" value="公交" class="button" onclick="javascript:bus();" /> <input type="button" value="驾车"
			class="button" onclick="javascript:driving();" /> <input type="button" value="打车费用" class="button"
			onclick="javascript:tax();" />
	</div>
	<div id="l-map"></div>
	<div id="r-result"></div>
	<input type="hidden" value="${ctx}/h5client/bookroom/jsapi" id="jsapiUrl" />
</body>
</html>
<script type="text/javascript">
	//创建地图并显示春晖园位置
	var map = new BMap.Map("l-map");
	var point = new BMap.Point(116.498615, 40.136459);
	map.centerAndZoom(point, 15);
	var marker = new BMap.Marker(point);
	map.addOverlay(marker);
	var label = new BMap.Label("北京春晖园温泉度假酒店", {
		offset : new BMap.Size(20, -10)
	});
	marker.setLabel(label);
	map.enableScrollWheelZoom(true);

	var currentLocation = null;
	var jsapiUrl = $("#jsapiUrl").val();
	$.ajax({
		type : "POST",
		url : jsapiUrl,
		data : {
			"urlStr" : window.location.href
		},
		success : function(msg) {
			var data = eval('(' + msg + ')');
			wx.config({
				debug : false,
				appId : data.appid,
				timestamp : data.timestamp,
				nonceStr : data.noncestr,
				signature : data.signature,
				jsApiList : [ 'getLocation' ]
			});
			//微信js接口获取用户当前位置
			wx.ready(function() {
				wx.checkJsApi({
					jsApiList : [ 'getLocation' ],
					success : function(res) {
						if (res.checkResult.getLocation == false) {
							alert('你的微信版本太低，不支持微信JS接口，请升级到最新的微信版本！');
							return;
						}
					}
				});
				wx.getLocation({
					type : 'wgs84', // 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
					success : function(res) {
						var latitude = res.latitude; // 纬度，浮点数，范围为90 ~ -90
						var longitude = res.longitude; // 经度，浮点数，范围为180 ~ -180。
						var gpsLocation = new BMap.Point(longitude, latitude);
						//坐标转换完之后的回调函数
						translateCallback = function(data) {
							if (data.status === 0) {
								var marker = new BMap.Marker(data.points[0]);
								map.addOverlay(marker);
								var label = new BMap.Label("我的位置", {
									offset : new BMap.Size(20, -10)
								});
								marker.setLabel(label); //添加百度label
								currentLocation = new BMap.Point(data.points[0].lng, data.points[0].lat);
								// 默认驾车出行
								var driving = new BMap.DrivingRoute(map, {
									renderOptions : {
										map : map,
										panel : "r-result",
										autoViewport : true
									}
								});
								driving.search(currentLocation, point);
							}
						}

						setTimeout(function() {
							var convertor = new BMap.Convertor();
							var pointArr = [];
							pointArr.push(gpsLocation);
							convertor.translate(pointArr, 1, 5, translateCallback)
						}, 1000);
					},
					cancel : function(res) {
						alert('用户拒绝授权获取地理位置');
					}
				});
			});
			wx.error(function(res) {
				alert(res);
			});
		}
	});

	function driving() {
		var driving = new BMap.DrivingRoute(map, {
			renderOptions : {
				map : map,
				panel : "r-result",
				autoViewport : true
			}
		});
		driving.search(currentLocation, point);
	}
	function bus() {
		var transit = new BMap.TransitRoute(map, {
			renderOptions : {
				map : map,
				panel : "r-result"
			}
		});
		transit.search(currentLocation, point);
	}
	function tax() {
		var driving = new BMap.DrivingRoute(map, {
			onSearchComplete : tip,
			renderOptions : {
				map : map,
				autoViewport : true
			}
		});
		driving.search(currentLocation, point); //驾车查询
		function tip(rs) {
			alert("从当前位置到春晖园打车总费用为：" + rs.taxiFare.day.totalFare + "元"); //计算出白天的打车费用的总价
		}
	}
</script>
