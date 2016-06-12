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
<script src="../js/jquery-1.10.1.min.js"></script>
<script src="../js/idangerous.swiper.min.js"></script>
<script src="../js/showHide.js"></script>
</head>
<body>
<!--header-->
<h1 class="header mb20"><a href="javascript:window.history.back()"><span class="icon back">&nbsp;</span></a>交易记录<#if user??><a href="preFundHomePage.do" id="home"><em class="icon home">&nbsp;</em></a><#else><a href="userLogin.do" id="login"><em>登录</em></a></#if>  </h1>
<!--header end--> 

<!--tab-->
<div class="tabs tabTradeRecord">
  <p> <a href="#" hidefocus="true" class="active"><em>全部</em></a> <a href="#" hidefocus="true"><em>申购</em></a> <a href="#" hidefocus="true"><em>赎回</em></a> <a href="#" hidefocus="true"><em>认购</em></a> <a href="#" hidefocus="true"><em>撤单</em></a><a href="#" hidefocus="true"><em>转换</em></a> </p>
</div>
<!--end tab--> 
<!--swiper container-->
<div class="swiper-container">
  <div class="swiper-wrapper"> 
    <!--全部-->
    <div class="swiper-slide">
      <div class="content-slide" id="a1"> 
      	<ul class="tradeList">
      		<#list listTradequrResp as tradequrResp>
      		
      			<li><i class="icon arrowDown" onClick="showTradeAll(${tradequrResp_index})"></i>
            	<p><span>
            	<b>
	            	<#if tradequrResp.fund_busin_code="022">申购</#if>
	              	<#if tradequrResp.fund_busin_code="024">赎回</#if>
	              	<#if tradequrResp.fund_busin_code="020">认购</#if>
	              	<#if tradequrResp.fund_busin_code="053">撤单</#if>
	              	<#if tradequrResp.fund_busin_code="036">转换</#if>
              	</b>${tradequrResp.fund_name}</span><em>${tradequrResp.balance?number?string(",###.##")}<#if (tradequrResp.share_type="B" )>份<#else>元</#if></em></p>
                <p><span class="color-lightgray">${tradequrResp.apply_date}  ${tradequrResp.apply_time}</span><em class="color-lightgray">
                
                <#if (tradequrResp.fund_busin_code="022" || tradequrResp.fund_busin_code="020") && (tradequrResp.deduct_status==0 ||tradequrResp.deduct_status==2)>
			                <#if tradequrResp.taconfirm_flag="0">确认失败</#if>
			                <#if tradequrResp.taconfirm_flag="1">确认成功</#if>
			                <#if tradequrResp.taconfirm_flag="2">部分确认</#if>
			                <#if tradequrResp.taconfirm_flag="3">实时确认成功</#if>
			                <#if tradequrResp.taconfirm_flag="4">撤单</#if>
			                <#if tradequrResp.taconfirm_flag="5">行为确认</#if>
			                <#if tradequrResp.taconfirm_flag="9">未确认</#if>
			     <#elseif (tradequrResp.fund_busin_code="022" || tradequrResp.fund_busin_code="020") && tradequrResp.deduct_status==3>付款中
			     <#elseif (tradequrResp.fund_busin_code="022" || tradequrResp.fund_busin_code="020") && tradequrResp.deduct_status==1>无效订单
			     <#elseif (tradequrResp.fund_busin_code="024" || tradequrResp.fund_busin_code="053" || tradequrResp.fund_busin_code="036")>
		                <#if tradequrResp.taconfirm_flag="0">确认失败</#if>
		                <#if tradequrResp.taconfirm_flag="1">确认成功</#if>
		                <#if tradequrResp.taconfirm_flag="2">部分确认</#if>
		                <#if tradequrResp.taconfirm_flag="3">实时确认成功</#if>
		                <#if tradequrResp.taconfirm_flag="4">撤单</#if>
		                <#if tradequrResp.taconfirm_flag="5">行为确认</#if>
		                <#if tradequrResp.taconfirm_flag="9">未确认</#if>
	             </#if>
                </em></p>
                <p class="none" id="moreTradeAll_${tradequrResp_index}"><cite> ${tradequrResp.fund_code}&nbsp;&nbsp;${tradequrResp.fund_name}</cite><cite>${tradequrResp.bankName}        ${tradequrResp.receivable_account}</cite></p>
            </li>
      		
      		</#list>
        	
        </ul>
      </div>
    </div>
    <!--end 全部--> 
    <!--申购-->
    <div class="swiper-slide">
      <div class="content-slide" id="a2"> 
      	<ul class="tradeList">
      		<#list listTradequrRespsg as tradequrRespsg>
	        	<li><i class="icon arrowDown" onClick="showTradeSG(${tradequrRespsg_index})"></i>
	            	<p><span><b>申购</b>${tradequrRespsg.fund_name}</span><em>${tradequrRespsg.balance?number?string(",###.##")}元</em></p>
	                <p><span class="color-lightgray">${tradequrRespsg.apply_date}  ${tradequrRespsg.apply_time}</span><em class="color-lightgray">
	                
	                <#if (tradequrRespsg.deduct_status==0 ||tradequrRespsg.deduct_status==2) >
			                <#if tradequrRespsg.taconfirm_flag="0">确认失败</#if>
			                <#if tradequrRespsg.taconfirm_flag="1">确认成功</#if>
			                <#if tradequrRespsg.taconfirm_flag="2">部分确认</#if>
			                <#if tradequrRespsg.taconfirm_flag="3">实时确认成功</#if>
			                <#if tradequrRespsg.taconfirm_flag="4">撤单</#if>
			                <#if tradequrRespsg.taconfirm_flag="5">行为确认</#if>
			                <#if tradequrRespsg.taconfirm_flag="9">未确认</#if>
				   <#elseif tradequrRespsg.deduct_status==3>付款中
				   <#else>无效订单
				   </#if>    
                
                </em></p>
	            <p class="none" id="moreTradeSG_${tradequrRespsg_index}"><cite> ${tradequrRespsg.fund_code}&nbsp;&nbsp;${tradequrRespsg.fund_name}</cite><cite>${tradequrRespsg.bankName}        ${tradequrRespsg.receivable_account}</cite></p>
	            </li>
            </#list>
        </ul>
      </div>
    </div>
    <!--end 申购--> 
    <!--赎回-->
    <div class="swiper-slide">
      <div class="content-slide" id="a3"> 
      	<ul class="tradeList">
      		<#list listTradequrRespsh as tradequrResp>
	        	<li><i class="icon arrowDown" onClick="showTradeSH(${tradequrResp_index})"></i>
	            	<p><span><b>赎回</b>${tradequrResp.fund_name}</span><em>${tradequrResp.balance}份</em></p>
	                <p><span class="color-lightgray">${tradequrResp.apply_date}  ${tradequrResp.apply_time}</span><em class="color-lightgray">

		                <#if tradequrResp.taconfirm_flag="0">确认失败</#if>
		                <#if tradequrResp.taconfirm_flag="1">确认成功</#if>
		                <#if tradequrResp.taconfirm_flag="2">部分确认</#if>
		                <#if tradequrResp.taconfirm_flag="3">实时确认成功</#if>
		                <#if tradequrResp.taconfirm_flag="4">撤单</#if>
		                <#if tradequrResp.taconfirm_flag="5">行为确认</#if>
		                <#if tradequrResp.taconfirm_flag="9">未确认</#if>
                
                </em></p>
	            <p class="none" id="moreTradeSH_${tradequrResp_index}"><cite> ${tradequrResp.fund_code}&nbsp;&nbsp;${tradequrResp.fund_name}</cite><cite>${tradequrResp.bankName}        ${tradequrResp.receivable_account}</cite></p>
	            </li>
            </#list>
        </ul>
      </div>
    </div>
    <!--end 赎回--> 
    <!--认购-->
    <div class="swiper-slide">
      <div class="content-slide" id="a4"> 
      	<ul class="tradeList">
      		<#list listTradequrResprg as tradequrResp>
	        	<li><i class="icon arrowDown" onClick="showTradeRG(${tradequrResp_index})"></i>
	            	<p><span><b>认购</b>${tradequrResp.fund_name}</span><em>${tradequrResp.balance?number?string(",###.##")}元</em></p>
	                <p><span class="color-lightgray">${tradequrResp.apply_date}  ${tradequrResp.apply_time}</span><em class="color-lightgray">
	                
	                <#if (tradequrResp.deduct_status==0 ||tradequrResp.deduct_status==2)>
			                <#if tradequrResp.taconfirm_flag="0">确认失败</#if>
			                <#if tradequrResp.taconfirm_flag="1">确认成功</#if>
			                <#if tradequrResp.taconfirm_flag="2">部分确认</#if>
			                <#if tradequrResp.taconfirm_flag="3">实时确认成功</#if>
			                <#if tradequrResp.taconfirm_flag="4">撤单</#if>
			                <#if tradequrResp.taconfirm_flag="5">行为确认</#if>
			                <#if tradequrResp.taconfirm_flag="9">未确认</#if>
				   <#elseif tradequrResp.deduct_status==3>付款中
				   <#else>无效订单
				   </#if>	                
	                
                </em></p>
	            <p class="none" id="moreTradeRG_${tradequrResp_index}"><cite> ${tradequrResp.fund_code}&nbsp;&nbsp;${tradequrResp.fund_name}</cite><cite>${tradequrResp.bankName}        ${tradequrResp.receivable_account}</cite></p>
	            </li>
            </#list>
        </ul>
      </div>
    </div>
    <!--end 认购--> 
    <!--撤单-->
    <div class="swiper-slide">
      <div class="content-slide" id="a5">
      	<ul class="tradeList">
      		<#list listTradequrRespcd as tradequrResp>
	        	<li><i class="icon arrowDown" onClick="showTradeCD(${tradequrResp_index})"></i>
	            	<p><span><b>撤单</b>${tradequrResp.fund_name}</span><em>${tradequrResp.balance?number?string(",###.##")}<#if (tradequrResp.share_type="B" )>份<#else>元</#if></em></p>
	                <p><span class="color-lightgray">${tradequrResp.apply_date}  ${tradequrResp.apply_time}</span><em class="color-lightgray">

	                <#if tradequrResp.taconfirm_flag="0">确认失败</#if>
	                <#if tradequrResp.taconfirm_flag="1">确认成功</#if>
	                <#if tradequrResp.taconfirm_flag="2">部分确认</#if>
	                <#if tradequrResp.taconfirm_flag="3">实时确认成功</#if>
	                <#if tradequrResp.taconfirm_flag="4">撤单</#if>
	                <#if tradequrResp.taconfirm_flag="5">行为确认</#if>
	                <#if tradequrResp.taconfirm_flag="9">未确认</#if>
	                
                </em></p>
	                <p class="none" id="moreTradeCD_${tradequrResp_index}"><cite> ${tradequrResp.fund_code}&nbsp;&nbsp;${tradequrResp.fund_name}</cite><cite>${tradequrResp.bankName}        ${tradequrResp.receivable_account}</cite></p>
	            </li>
            </#list>
        </ul>
      </div>
    </div>
    <!--end 撤单--> 
     <!--转换-->
    <div class="swiper-slide">
      <div class="content-slide" id="a6">
      	<ul class="tradeList">
      		<#list listTradequrRespzh as tradequrResp>
	        	<li><i class="icon arrowDown" onClick="showTradeCD(${tradequrResp_index})"></i>
	            	<p><span><b>转换</b>${tradequrResp.fund_name}</span><em>${tradequrResp.shares}份</em></p>
	                <p><span class="color-lightgray">${tradequrResp.apply_date}  ${tradequrResp.apply_time}</span><em class="color-lightgray">
	                
		                <#if tradequrResp.taconfirm_flag="0">确认失败</#if>
		                <#if tradequrResp.taconfirm_flag="1">确认成功</#if>
		                <#if tradequrResp.taconfirm_flag="2">部分确认</#if>
		                <#if tradequrResp.taconfirm_flag="3">实时确认成功</#if>
		                <#if tradequrResp.taconfirm_flag="4">撤单</#if>
		                <#if tradequrResp.taconfirm_flag="5">行为确认</#if>
		                <#if tradequrResp.taconfirm_flag="9">未确认</#if>
                
                </em></p>
	                <p class="none" id="moreTradeCD_${tradequrResp_index}"><cite> ${tradequrResp.fund_code}&nbsp;&nbsp;${tradequrResp.fund_name}</cite><cite>${tradequrResp.bankName}        ${tradequrResp.receivable_account}</cite></p>
	            </li>
            </#list>
        </ul>
      </div>
    </div>
    <!--end 转换--> 
  </div>
</div>
<!--end swiper container-->
<div class="footerDiv"></div>
<ul class="position-footer menu">
   <a href="${rc.contextPath}/jcbindex.do"><li><i class="jcb"></i>金诚宝</li></a>
    <a href="${rc.contextPath}/myAssetPage.do" class="current"><li><i class="myasset"></i>我的资产</li></a>
    <a href="${rc.contextPath}/preFundHomePage.do"><li><i class="fund"></i>基金产品</li></a>
    <a href="${rc.contextPath}/accountManager_index.do"><li><i class="account"></i>帐户管理</li></a>
</ul>
<script src="js/tabs5wiper.js"></script>
</body>
</html>