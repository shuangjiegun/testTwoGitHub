<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="description" content="金观诚基金" />
<meta name="keywords" content="金观诚基金" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black-translucent" />
<title>金观诚基金</title>
<meta content="width=device-width, initial-scale=0.9, minimum-scale=0.8, maximum-scale=1.3,user-scalable=no" name="viewport" id="viewport" />
<link rel="stylesheet" href="css/base.css" type="text/css" />
<link rel="stylesheet" href="css/layout.css" type="text/css" />
</head>
<body>

<!--header-->
<h1 class="header mb30"><a href="javascript:window.history.back()"><span class="icon back">&nbsp;</span></a>测评结果<a href="${rc.contextPath}/preFundHomePage.do"><em class="icon home">&nbsp;</em></a></h1>
<!--header end-->
<p class="m30 tc f11 lh200">经测评，您为<b class=" color-gold f13">
<#if (invest_risk_tolerance=="1")>  
保守型 
<#elseif (invest_risk_tolerance=="2")>    
稳健型
<#elseif (invest_risk_tolerance=="3")>    
激进型
<#else>  
出错啦
</#if>
</b>投资者</p>

<a href="${rc.contextPath}/preFundHomePage.do"><input type="button" name="button" id="isb" value="完成"  class="btn btnOne bg-white"/></a>
<div class="position-footer"><p class="hotline">财富热线：400-068-0058</p></div>
</body>
</html>