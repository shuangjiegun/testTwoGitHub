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
<script src="../js/jquery-1.10.1.min.js"></script>
<script src="../js/showHide.js"></script>
</head>
<script src="../js/jquery-1.11.1.js"></script>
<script type="text/javascript">
$(function(){
	function addZero(str) {
        return str < 10 ? '0' + str:str;
    }
	
	function clock(){
		var date = new Date();
	    var oYear = date.getFullYear();
	    var oMonth = date.getMonth() + 1;
	    var oDate = date.getDate();
	    var time = oYear +"-"+ addZero(oMonth) +"-"+ addZero(oDate)
	    $("#inSpa").find("em").html("更新时间：" + time);			    		
	}
clock();				
})
</script>
<body>
<!--header-->
<h1 class="header"><a href="javascript:window.history.back()"><span class="icon back">&nbsp;</span></a>我的资产<a href="${rc.contextPath}/tradingRecord_index.do"><em>交易记录</em></a></h1>
<!--header end-->

<!-- <div class="accountOverview">
	<p class="greeting">${userName}，您好！</p>
    <ul class="income" id="inSpa">
        <li>持仓收益（元）<b class="color-red">${positionBenefits?default(0)?string('#.##')}</b></li>
        <li>总资产<b>${worthValueSum?default(0)?string('#.##')}</b></li>
        <li>昨日收益（元）<b class="color-red">${yesterdayBenefits?default(0)?string('#.##')}</b></li>
        <li>更新时间<span></span></li>
    </ul>
    <p class="btnGroup">
    	<a href="${rc.contextPath}/preFundHomePage.do"><button type="button" class="rightBorder" style="width:33.33%;">认购</button></a><a href="${rc.contextPath}/preFundHomePage.do"><button type="button" class="rightBorder" style="width:33.33%;">申购</button></a><a href="${rc.contextPath}/undotradeapply_trade.do"><button type="button" class="rightBorder" style="width:33.33%;">撤单</button></a>
    </p>
</div> -->

<div class="accountOverview">
<p class="welcome">${userName}，您好！</p>    
<p class="total" id="inSpa">总资产(元)<b>${all_total_all_new?string('0.00')}</b><em></em></p>
<!--<ul class="income">
    <li>持仓收益(元)
	 <#if (positionBenefits>=0)>
	 	<b class="color-red">
    		  ${positionBenefits?default(0)?string('#.##')}
    	</b>
    	<#else>
   		<b class="color-green">
    		  ${positionBenefits?default(0)?string('#.##')}
    	</b>
    </#if> 
    </li>
    <li>昨日收益(元)
    	 <#if (yesterdayBenefits>=0)>
	    	<b class="color-red">
	     		  ${yesterdayBenefits?default(0)?string('#.##')}
	     	</b>
	     	<#else>
	    	<b class="color-green">
	     		  ${yesterdayBenefits?default(0)?string('#.##')}
	     	</b>
	   </#if> 
    </li>
</ul> -->
<p class="btnGroup">
<a href="${rc.contextPath}/preFundHomePage.do"><button type="button" class="rightBorder" style="width:33.33%;">认购</button></a><a href="${rc.contextPath}/preFundHomePage.do"><button type="button" class="rightBorder" style="width:33.33%;">申购</button></a><a href="${rc.contextPath}/undotradeapply_trade.do"><button type="button" class="rightBorder" style="width:33.33%;">撤单</button></a>
</p>
</div>

<a href="${rc.contextPath}/jcbindex.do"><h2 class="titleh2">金诚宝</h2>
<ul class="income bg-lightgray">
    <li>资产(元)<b> ${all_total_new?string('0.00')}</b></li>
    <li>未结转收益(元)
	    <#if (no_pay_new>=0)>
	    	<b class="color-red">
	     		  ${no_pay_new?string('0.00')}
	     	</b>
	   <#else>
	    	<b class="color-green">
	     		  ${no_pay_new?string('0.00')}
	     	</b>
	   </#if> 
    </li>
</ul></a>
<p class="tc bc mt10 mb10" style="width:80%;">
<a href="${rc.contextPath}/jcbRecharge1.do"><button type="button" class="btn btnTwo bg-white">充值</button></a><a href="${rc.contextPath}/cashBack1.do"><button type="button" class="btn btnTwo bg-white">提现</button></a>
</p>

<div class="dataListA">	
  <h2>持仓基金</h2>
  
  <#if myAssetListExcepJCB?size==0>
  <p class="noData" >您还没购买基金！</p>
  <#else>
	  <table width="100%" border="0" cellspacing="0" cellpadding="0">
	  <thead>
	      <tr>
	        <th width="36%">名称</th>
	        <th width="15%">净值/万份收益</th>
	        <th width="32%">市值</th>
	        <th width="17%">日涨跌幅/7日年化</th>
	      </tr>
	  </thead>
	  <tbody>
	  		<#list myAssetListExcepJCB as asset>
			      <tr onClick="showButton(${asset_index+1})">
			        <td class="pr">${asset.secuAbbr}<i class="icon arrowDown pa" style="right:0;"></i></td>
			        <td>${asset.unitNV?default(0)?string('0.0000')}</td>
			        <td>${asset.worthValue}元</td>
			        
			        <#if (asset.nvDailyGrowthRate>=0)>
			        <td class="color-red">${asset.nvDailyGrowthRate?default(0)?string('0.00')}%</td>
			        <#else>
			        <td class="color-green">${asset.nvDailyGrowthRate?default(0)?string('0.00')}%</td>
			        </#if>
			        
			      </tr>
			      <tr>
			        <td colspan="4" class="tc none" id="buttonGroup_${asset_index+1}">
			        <a href="${rc.contextPath}/convert_trade.do?fundCode=${asset.fundCode}"><button type="button" class="btn btnThree bg-white">转换</button></a><a href="${rc.contextPath}/preRedeemTradeFirst.do?secuCode=${asset.fundCode}"><button type="button" class="btn btnThree bg-white">赎回</button></a><a href="${rc.contextPath}/singleFundProductDetails.do?secuCode=${asset.fundCode}"><button type="button" class="btn btnThree bg-white">查档案</button></a>
			        </td>
			      </tr>	      
			 </#list>
		  </tbody>
	  </table>
  </#if>
</div>
<div class="footerDiv"></div>
  <ul class="position-footer menu">
    <a href="${rc.contextPath}/jcbindex.do"><li><i class="jcb"></i>金诚宝</li></a>
    <a href="${rc.contextPath}/myAssetPage.do" class="current"><li><i class="myasset"></i>我的资产</li></a>
    <a href="${rc.contextPath}/preFundHomePage.do"><li><i class="fund"></i>基金产品</li></a>
    <a href="${rc.contextPath}/accountManager_index.do"><li><i class="account"></i>帐户管理</li></a>
</ul> 
</body>
<style>
tbody tr{cursor:pointer;}
.tom{height:30px;width:100%;background:red; }
.hide{display:none;}
</style>
</html>