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
<link rel="stylesheet" href="../css/base.css" type="text/css" />
<link rel="stylesheet" href="../css/layout.css" type="text/css" />
<link rel="stylesheet" href="../css/common.css" type="text/css" />
</head>
<body>

<!--header-->
<h1 class="header"><a href="javascript:window.history.back()"><span class="icon back">&nbsp;</span></a>基金产品
<#if user??>
<#else>
<a href="${rc.contextPath}/preLogin.do"><em>登录</em></a>
</#if></h1>
<!--header end-->
<div class="banner">
  <div class="page-swipe">
    <header>
      <div id="slider" class="swipe">
        <div class="swipe-wrap">
          <figure>
            <div class="wrap">
              <div class="image" style="background:url(images/banner1.jpg) center no-repeat;background-size: cover"> </div>
            </div>
          </figure>
          <figure>
            <div class="wrap">
              <div class="image" style="background:url(images/banner2.jpg) center no-repeat;background-size: cover"> </div>
            </div>
          </figure>
          <figure>
            <div class="wrap">
              <div class="image" style="background:url(images/banner3.jpg) center no-repeat;background-size: cover"> </div>
            </div>
          </figure>
        </div>
      </div>
      <nav>
        <ul id="position">
          <li class="on"></li>
          <li class=""></li>
          <li class=""></li>
        </ul>
      </nav>
    </header>
  </div>
</div>
<div class="hotPro">
	<div class="hotProTitle"><h1>热销推荐</h1><a href="${rc.contextPath}/queryFundProductByCondition.do">全部基金<i class="icon more"></i></a></div>
    <ul>
	    	<#list topSellingList as topSelling>
		    	<a href="${rc.contextPath}/singleFundProductDetails.do?secuCode=${topSelling.secuCode}">
			    	<li>
			        	${topSelling.secuCode} ${topSelling.chiName}
			            <p><span>
			                <#if (topSelling.fundTypeCode==1109)>万份收益：<#else>单位净值：</#if> 	
			            	${topSelling.unitNV?default(0)?string('0.0000')}</span>
				            <#if (topSelling.rrInSelectedMonth>=0)>
				            		<span> <#if (topSelling.fundTypeCode==1109)>14日年化：<#else>本月收益：</#if> <em class="color-red">${topSelling.rrInSelectedMonth?default(0)?string('0.00')}%</em></span>
				            <#else>
				           			 <span> <#if (topSelling.fundTypeCode==1109)>14日年化：<#else>本月收益：</#if> <em class="color-green">${topSelling.rrInSelectedMonth?default(0)?string('0.00')}%</em></span>
				            </#if>
			            </p>
			            <p>
			            	<#if (topSelling.nvDailyGrowthRate>=0)>
			            		<span><#if (topSelling.fundTypeCode==1109)>7日年化：<#else>日涨跌幅：</#if><em class="color-red">${topSelling.nvDailyGrowthRate?default(0)?string('0.00')}%</em></span>
			            	<#else>	
			            		<span><#if (topSelling.fundTypeCode==1109)>7日年化：<#else>日涨跌幅：</#if><em class="color-green">${topSelling.nvDailyGrowthRate?default(0)?string('0.00')}%</em></span>
			            	 </#if>			            		
			            	<#if (topSelling.rrSinceThisYear>=0)>	
			            		<span><#if (topSelling.fundTypeCode==1109)>近1年收益：<#else>近1年收益：</#if><em class="color-red">${topSelling.rrSinceThisYear?default(0)?string('0.00')}%</em></span>
			            	<#else>
			            		<span><#if (topSelling.fundTypeCode==1109)>近1年收益：<#else>近1年收益：</#if><em class="color-green">${topSelling.rrSinceThisYear?default(0)?string('0.00')}%</em></span>
			           		</#if>
			            </p>
			        </li>
		        </a>
			</#list>
    </ul>
</div>
<div class="footerDiv"></div>
<ul class="position-footer menu">
    <a href="${rc.contextPath}/jcbindex.do"><li><i class="jcb"></i>金诚宝</li></a>
    <a href="${rc.contextPath}/myAssetPage.do"><li><i class="myasset"></i>我的资产</li></a>
    <a href="${rc.contextPath}/preFundHomePage.do" class="current"><li><i class="fund"></i>基金产品</li></a>
    <a href="${rc.contextPath}/accountManager_index.do"><li><i class="account"></i>帐户管理</li></a>
</ul>
<script src="js/swipe.js"></script>
</body>
</html>