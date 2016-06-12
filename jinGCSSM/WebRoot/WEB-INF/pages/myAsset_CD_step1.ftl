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
<body>

<!--header-->
<h1 class="header mb20"><a href="javascript:window.history.back()"><span class="icon back">&nbsp;</span></a>撤单  <#if user??><a href="preFundHomePage.do" id="home"><em class="icon home">&nbsp;</em></a><#else><a href="userLogin.do" id="login"><em>登录</em></a></#if>  </h1>
<!--header end-->
  
<form name="form1" id="form1" method="post" action="undotradeapply_trade2.do">	 
<div class="dataListB">
<h2>选择撤销订单</h2>
    	<p class="error">请选择撤销订单！</p>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tbody>
      <#list list as tradequrResp>
		      <#if (tradequrResp.fund_busin_code="020" ||tradequrResp.fund_busin_code="022" ) && tradequrResp.deduct_status==2>
			    	<tr>
			    	<input type="hidden" name="fund_code" id="fund_code" value="${tradequrResp.fund_code}" />
			    	<input type="hidden" name="fund_name" id="fund_name" value="${tradequrResp.fund_name}" />
			          <th width="17%">
			          <input type="radio" name="businessType" id="SG" value="${tradequrResp.allot_no}">
			           <label for="SG">
			           			<#if tradequrResp.fund_busin_code="020">认购</#if>
			           			<#if tradequrResp.fund_busin_code="022">申购</#if>
			           	</label>
			          </th>
			          <td width="60%">${tradequrResp.fund_code} ${tradequrResp.fund_name}</td>
			          <td width="23%">${tradequrResp.balance}元</td>
			        </tr>
		      <#elseif tradequrResp.fund_busin_code="024" || tradequrResp.fund_busin_code="036">
			      	<tr>
			      	<input type="hidden" name="fund_code" id="fund_code" value="${tradequrResp.fund_code}" />
			      	<input type="hidden" name="fund_name" id="fund_name" value="${tradequrResp.fund_name}" />
			            <th width="17%">
			            <input type="radio" name="businessType" id="SG" value="${tradequrResp.allot_no}">
			              <label for="SG">
			              	<#if tradequrResp.fund_busin_code="024">赎回</#if>
			              	<#if tradequrResp.fund_busin_code="036">转换</#if>
			              	</label>
			            </th>
			            <td width="60%">${tradequrResp.fund_code} ${tradequrResp.fund_name}</td>
			            <td width="23%">${tradequrResp.balance}份</td>
			          </tr>
		      </#if>    
      </#list>
        </tbody>
    </table>
    </div>	 
  <input type="button" name="button" id="btn" value="下一步" class="btn btnOne bg-white" onclick="checkOrder()">
</form>
<!--<div class="position-footer"><p class="hotline">财富热线：400-068-0058</p></div>-->
<script type="text/javascript">
function checkOrder(){
	var flagA = <#if list?size==0>'NO'<#else>'YES'</#if>
	if(flagA=='NO'){
		alert("没有可撤订单");
		return;
	}
	var danxuan = document.getElementsByName("businessType");
	var tag = false;
	for(var i=0;i<danxuan.length;i++) {
	   if(danxuan[i].checked) {
	      tag = true;
	      break;
	   }
	}
	if(!tag) {
	  alert("请选择订单");
	  return;
	}
	$("#form1").submit();
}
</script>
</body>
</html>