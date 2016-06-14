<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=7" />
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="apple-mobile-web-app-title" content="">
<meta name="format-detection" content="telephone=no">
<c:set var="rd"><%=java.lang.Math.random()%></c:set>
<meta id="viewport" name="viewport" content="width=750,target-densitydpi=device-dpi,minimum-scale=0.5,maximum-scale=0.5," />
<link rel="stylesheet" type="text/css" href="${ctx }/static/css/style.css?${rd}">
<script src="${ctx }/static/js/jquery-2.0.0.min.js"></script>
<script type="text/javascript" src="${ctx }/static/js/TouchSlide.1.1.js"></script>
<script type="text/javascript" src="${ctx }/static/js/jquery.Spinner.js"></script>
<script type="text/javascript" src="${ctx }/static/js/ios.js?${rd}"></script>
