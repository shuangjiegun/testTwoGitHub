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
		var error = $("#ptip").html();
		if(error){
		$("#ptip").removeClass("none")
		}
})		
</script>
<body class="bg-pattern">
<!--header-->
<h1 class="header"><a href="${rc.contextPath}/preFundHomePage.do"><em class="icon home">&nbsp;</em></a></h1>
<!--header end-->
<div class="logoLogin"><img src="images/logoLogin.png"></div>
<form name="lgform" method="post" action="${rc.contextPath}/userLogin.do">
	<p class="error none" id="ptip">${error_info}</p>
    <ul class="bg-lightgray formInput">
           <li> <i class="icon username"></i>
        <input type="text" value="${id_no}" name="codeNumber" id="codeNumber" class="textInput mt10 mb10" placeholder="证件号码">
      </li>
      <li> <i class="icon pwdLogin"></i>
        <input type="password" name="password" id="password" class="textInput mt10 mb10"  placeholder="请输入交易密码">
      </li>
    </ul>
  	<p class="aGroup"><a href="${rc.contextPath}/preUserAcctOpen.do">快速开户</a><a href="${rc.contextPath}/forgetPassword.do">忘记密码</a></p>
    <input type="submit" name="button" id="btn" value="登录" class="btn btnOne bg-pattern">
</form>
<script type="text/javascript">
$("#btn").click(function(){

		if($.trim($("#codeNumber").val()) == "" || $.trim($("#codeNumber").val()) == null){
		alert("请填写证件号码")
		$("#codeNumber").focus();
		return false;
		}
		
		if($.trim($("#password").val()) == "" || $.trim($("#password").val()) == null){
		alert("请输入密码")
		$("#password").focus();
		return false;
		}
		
return true;		
})
</script>
</body>
</html>