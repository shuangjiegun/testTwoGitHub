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
<h1 class="header mb20"><a href="javascript:window.history.back()"><span class="icon back">&nbsp;</span></a>金诚宝充值  <#if user??><a href="preFundHomePage.do" id="home"><em class="icon home">&nbsp;</em></a><#else><a href="userLogin.do" id="login"><em>登录</em></a></#if>  </h1>
<!--header end-->
<div class="dataListA">	
  <h2>选择充值基金</h2>
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <thead>
      <tr>
        <th width="50%">基金名称</th>
        <th width="50%">7日年化收益率</th>
      </tr>
  </thead>
  <tbody>
  <#list list as jcbfund>
  	<tr>
        <td><a href="${rc.ContextPath}jcbRecharge2.do?fund_code=${jcbfund.fundcode}">${jcbfund.fundname}</a></td>
        <td>${rc.ContextPath}4.80%</td>
      </tr>
  
  </#list>
   </tbody>
  </table>

</div>

<div class="position-footer"><p class="hotline">财富热线：400-068-0058</p></div>
</body>
</html>