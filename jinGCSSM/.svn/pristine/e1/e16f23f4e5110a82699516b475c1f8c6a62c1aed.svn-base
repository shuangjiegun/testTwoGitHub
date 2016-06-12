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
});		
</script>
<body>

<!--header-->
<h1 class="header mb20"><a href="javascript:window.history.back()"><span class="icon back">&nbsp;</span></a>金诚宝取现 <#if user??><a href="preFundHomePage.do" id="home"><em class="icon home">&nbsp;</em></a><#else><a href="userLogin.do" id="login"><em>登录</em></a></#if>  </h1>
<!--header end-->
<div class="dataListA">	
  <h2>选择可取现基金</h2>
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <thead>
      <tr>
        <th width="50%">基金名称</th>
        <th width="50%">可取金额</th>
      </tr>
  </thead>
  <tbody>
  <#list list as shareRequ>  
  		<tr>
        <td><a href="${rc.ContextPath}cashBack2.do?fund_code=${shareRequ.fund_code}&shares=${shareRequ.shares}&fund_name=${shareRequ.fund_name}&bank_no=${shareRequ.bank_no}&bank_account=${shareRequ.bank_account}&trade_acco=${shareRequ.trade_acco}&capital_mode=${shareRequ.capital_mode}">${shareRequ.fund_code}  ${shareRequ.fund_name}</a></td>
        <td>${shareRequ.shares}</td>
      </tr>
	</#list>  
      </tbody>
  </table>

</div>

<div class="position-footer"><p class="hotline">财富热线：400-068-0058</p></div>
</body>
</html>