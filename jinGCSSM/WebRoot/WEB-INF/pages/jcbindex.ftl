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
</head>
<script src="../js/jquery-1.11.1.js"></script>
<script src="../js/swipe.js"></script>
<script src="../js/showHide.js"></script>
<script type="text/javascript">
$(function(){
	$("#recharge").unbind( "click" );
	 $("#recharge").bind( "click", function() {
	  location.href="jcbRecharge1.do";
	});
	$("#cashback").unbind( "click" );
	 $("#cashback").bind( "click", function() {
	  location.href="cashBack1.do";
	});

});		
</script>
<body>
<h1 class="header"><a href="#"><span>&nbsp;</span></a>我的金诚宝<a href="#"><em>&nbsp;</em></a></h1>
<div class="accountOverview">
<p class="total">资产(元)<b>${all_total_new?string("0.00")}</b><em>更新时间： ${date}</em></p>
    <ul class="income">
    <!-- <li>资产(元)
        <#if all_total_new gt 0>
        <b class="color-red" >
        <#elseif all_total_new lt 0>
        <b class="color-green" >
        <#else><b>
        </#if>
        ${all_total_new!0?string("##.##")}
        </b></li>-->
        <li>未结转收益(元)
        <#if no_pay_new gt 0>
        <b class="color-red" >
        <#elseif no_pay_new lt 0>
        <b class="color-green" >
        <#else><b>
        </#if>
        ${no_pay_new?string("0.00")}</b></li>
    </ul>
    <p class="btnGroup">																		
    	<button type="button" id="recharge" class="rightBorder" style="width:50%">充值</button><button id="cashback" type="button" style="width:50%">提现</button>
    </p>
</div>
<div class="dataListA">	
  <h2>金诚宝持仓</h2>
  
  <tbody>
  <#if (listcc?size>0)>
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <thead>
      <tr>
        <th width="47%">基金名称</th>
        <th width="25%">金额(元)</th>
        <th width="28%">未付收益(元)</th>
      </tr>
  </thead>
  	<#list listcc as shareResp>
		<tr>
        <td class="pr" >${shareResp.fund_name}<i class="icon arrowDown pa" onClick="showButton(${shareResp_index})" style="right:0;"></i></td>
        <td>${shareResp.enable_shares}</td>
        <td>${shareResp.unpaid_income}</td>
      	</tr>
       <tr>
        <td colspan="3" class="tc none" id="buttonGroup_${shareResp_index}">
        <button type="button" class="btn btnThree bg-white" onclick="window.location.href='${rc.ContextPath}jcbRecharge2.do?fund_code=${shareResp.fund_code}'">充值</button><button type="button" class="btn btnThree bg-white" onclick="window.location.href='${rc.ContextPath}cashBackspe.do?fund_code=${shareResp.fund_code}'">提现</button><a href="${rc.contextPath}/convert_trade.do?fundCode=${shareResp.fund_code}"><button type="button" class="btn btnThree bg-white">转换</button></a>
        </td>
      </tr>
	</#list>
	<#else>
	
	<p class="noData" >您还没购买金诚宝！</p>
	</#if> 
  		
  <!--<#if fund1?exists>
  	<tr>
        <td class="pr" >${fund1.fundname}<i class="icon arrowDown pa" onClick="showButton(1)" style="right:0;"></i></td>
        <td>${fund1.fundshares}</td>
        <td>0</td>
      </tr>
       <tr>
        <td colspan="3" class="tc none" id="buttonGroup_1">
        <button type="button" class="btn btnTwo bg-white" id="a">充值</button><button type="button" class="btn btnTwo bg-white">提现</button>
        </td>
      </tr>
      </#if>
  		<#if fund2?exists>
			<tr>
	        <td class="pr" >${fund2.fundname}<i class="icon arrowDown pa" onClick="showButton(2)" style="right:0;"></i></td>
	        <td>${fund1.fundshares}</td>
	        <td>0</td>
	      </tr>
	      <tr>
	        <td colspan="3" class="tc none" id="buttonGroup_1">
	        <button type="button" class="btn btnTwo bg-white" id="a">充值</button><button type="button" class="btn btnTwo bg-white">提现</button>
	        </td>
	      </tr>
	</#if>-->
      
      </tbody>
  </table>
</div>
<div class="dataListB">	
<#if listcz?exists || listtx?exists>
<h2>在途交易</h2>
<#else>

</#if>
  
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tbody>
  	<#list listcz as shareResp>
  		<tr> 
		 <th>在途充值</th>
		 <td>${shareResp.fund_name}  ${shareResp.balance} 元</th> 
		 </tr> 
  		
  	</#list>
  	<#list listtx as shareResp>
  		<tr> 
		 <th>在途取现</th>
		 <td>${shareResp.fund_name}  ${shareResp.shares} 元</th> 
		 </tr> 
  		
  	</#list>
  <#if unfund1?exists>
  		<tr> 
		 <th>在途充值</th>
		 <td>${unfund1.fundname}  ${unfund1.fundshares} 元</th> 
		 </tr> 
	</#if>
	<#if unfund2?exists>
  		<tr> 
		 <th>在途充值</th>
		 <td>${unfund2.fundname}  ${unfund2.fundshares} 元</th> 
		 </tr> 
	</#if>
      </tbody>
  </table>
</div>
<div class="footerDiv"></div>
<ul class="position-footer menu">
    <a href="${rc.contextPath}/jcbindex.do" class="current"><li><i class="jcb"></i>金诚宝</li></a>
    <a href="${rc.contextPath}/myAssetPage.do"><li><i class="myasset"></i>我的资产</li></a>
    <a href="${rc.contextPath}/preFundHomePage.do"><li><i class="fund"></i>基金产品</li></a>
    <a href="${rc.contextPath}/accountManager_index.do"><li><i class="account"></i>帐户管理</li></a>
</ul>
</body>
</html>
  