<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>金诚财富内部门户网站</title>
<script src="js/jquery-1.11.1.js"></script>
<script type="text/javascript">
$(function(){

	$("input").click(function(){
		var str = $(this).attr("id");
		var newStr = str.replace("A","B");
		var idno = $("#"+newStr).val();
		var qryDate = $("#qryDate").val()
		
					$.ajax({
						type:"GET",
						async : false,
						url:"${rc.contextPath}/accountEmailSend.do?idno="+idno+"&qryDate="+qryDate,
						success:function(data){
							if(data=='Y'){
								alert("邮件发送成功")
							}else{
								alert("邮件发送失败")
							}					
						}
					})
					
	})

				
})
</script>
</head>
<body>

<h1>邮件发送了波</h1>
<input type="hidden" id="qryDate" value="${qryDate}">
<#list emailUsefulList as outList>
	<#list outList as inList>
		${inList.id_no}<br>
		<input type="hidden" id="B_${outList_index}" value="${inList.id_no}">
		${inList.client_name}<br>
	</#list>
	<input type="button" value="发送邮件" id="A_${outList_index}"/><br>
</#list>

*****************<br>
<#assign i=strValue/>
${i} <br>
<#list 0..3 as t>
	<#list list_0 as asset>
		${asset.fund_busin_code_name}
		${asset.client_name}
	</#list> "****"
</#list>

---------------------------------------------------------------------<br>
---------------------------------------------------------------------<br>


<#list allFundProduct as ban><br>
基金代码：${ban.secuCode}    基金简称：${ban.secuAbbr}<br>
单位净值： ${ban.unitNV?default(0)?string('#.####')}      本月收益：${ban.RRInSelectedMonth?default(0)?string('#.####')}%	 <br>
日涨跌幅：${ban.NVDailyGrowthRate?default(0)?string('#.####')}%		今年以来：${ban.RRSinceThisYear?default(0)?string('#.####')}%	<br>
净值日期：${ban.endDate?string("yyyy-MM-dd")} 

</#list>
<br>

---------------------------------------------------------------------<br>
---------------------------------------------------------------------<br>

${user}  <br/>
${username}<br/>
${info}<br/><br/>
<br/>
<a href="prePaperInfoList.do">去 问卷列表页面 </a><br/>

${invest_risk_tolerance}

<#if (invest_risk_tolerance=="1")>  
安全型 
<#elseif (invest_risk_tolerance=="2")>    
保守型
<#elseif (invest_risk_tolerance=="3")>    
稳健型
<#elseif (invest_risk_tolerance=="4")>    
积极型
<#elseif (invest_risk_tolerance=="5")>    
进取型
<#else>  
未定义
</#if>
*******************************************************************<br>
banner部分  <br>
<#list bannerList as banner>
 <br>
基金代码：${banner.secuCode}    基金简称：${banner.secuAbbr}<br>
单位净值： ${banner.unitNV?default(0)?string('#.####')}      本月收益：${banner.RRInSelectedMonth?default(0)?string('#.####')}%	 <br>
日涨跌幅：${banner.NVDailyGrowthRate?default(0)?string('#.####')}%		今年以来：${banner.RRSinceThisYear?default(0)?string('#.####')}%	<br>
</#list>
<br>
topSelling部分  <br>
<#list topSellingList as topSelling>
<br>
基金代码：${topSelling.secuCode}   			 基金简称：${topSelling.secuAbbr}<br>
单位净值： ${topSelling.unitNV?default(0)?string('#.####')}                      本月收益：${topSelling.RRInSelectedMonth?default(0)?string('#.####')}%	 <br>
日涨跌幅：${topSelling.NVDailyGrowthRate?default(0)?string('#.####')}%	 		今年以来：${topSelling.RRSinceThisYear?default(0)?string('#.####')}%	 <br>
</#list>

<font color="red">----------全部基金--------</font><br>
<#list allFundProduct as all>
<br>
基金代码：${all.secuCode}   			 基金简称：${all.secuAbbr}<br>
单位净值： ${all.unitNV?default(0)?string('#.####')}      今年以来：${all.RRSinceThisYear?default(0)?string('#.####')}%	 <br>
</#list>

**************************************************
**************************************************<br>
<#list mfvp as all>
<br>
基金代码：${all.secuCode}   			 基金简称：${all.secuAbbr}<br>
单位净值： ${all.unitNV?default(0)?string('#.####')}      今年以来：${all.RRSinceThisYear?default(0)?string('#.####')}%	 <br>
</#list>




</body>
</html>

















