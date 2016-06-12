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
<script src="js/jquery-1.11.1.js"></script>
<script type="text/javascript">
$(function(){
	/*$("#back").unbind( "click" );
	 $("#back").bind( "click", function() {
	  history.back();
	});
	$("#home").unbind( "click" );
	 $("#home").bind( "click", function() {
	  location.href="preFundHomePage.do";
	});
	$("#login").unbind( "click" );
	 $("#login").bind( "click", function() {
	  location.href="userLogin.do";
	});
 	*/
});		
</script>
<body>

<!--header-->
<h1 class="header mb20"><a href="javascript:window.history.back()"><span class="icon back">&nbsp;</span></a>金诚宝<#if businessType=="normal">普通取现<#elseif businessType=="fast">快速取现<#else>出错</#if><#if user??><a href="preFundHomePage.do" id="home"><em class="icon home">&nbsp;</em></a><#else><a href="userLogin.do" id="login"><em>登录</em></a></#if>  </h1>
<!--header end-->
  
<form name="form1" method="post" action="${rc.ContextPath}backOK.do">	
  <div class="dataListB mb20">	
    <h2>确认订单信息</h2>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tbody>
          <tr>
            <th>操作</th>
            <td>金诚宝<#if businessType=="normal">普通取现<#elseif businessType=="fast">快速取现<#else>出错</#if></td>
          </tr>
          <tr>
            <th>取现基金</th>
            <td>${cashBack.fund_name}</td>                
          </tr>
          <tr>
            <th>取现金额</th>
            <td>${cashBack.shares}元</td>
          </tr>
          <tr>          
            <th>银行卡</th>
            <td>${apply.bank_name}       （ ${apply.bank_no}）</td>
          </tr>
        </tbody>
    </table>
    </div>	
    	<h2 class="titleh2">填写交易密码</h2>
    	<p class="error">${error}</p>
        <ul class="bg-lightgray formInput mb30">
          <li> <em style="width:100px;">交易密码</em>
            <input type="password" name="password" id="password" class="textInput"  placeholder="请输入交易密码">
          </li>
        </ul>
        <input type="hidden" name="fund_code"  value="${cashBack.fund_code}"/>
        <input type="hidden" name="businessType"  value="${businessType}"/>
        <input type="hidden" name="shares"  value="${cashBack.shares}"/>
        <input type="hidden" name="fund_name"  value="${cashBack.fund_name}"/>
		<input type="hidden" name="bank_no"  value="${cashBack.bank_no}"/>
		<input type="hidden" name="bank_account"  value="${cashBack.bank_account}"/>
		<input type="hidden" name="trade_acco"  value="${cashBack.trade_acco}"/>
		<input type="hidden" name="capital_mode"  value="${cashBack.capital_mode}"/>
    	<input type="submit" name="button" id="button" value="提交" class="btn btnOne bg-white">
</form>


<div class="position-footer"><p class="hotline">财富热线：400-068-0058</p></div>
</body>
</html>