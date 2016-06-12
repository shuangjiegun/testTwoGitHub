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
<script type="text/javascript">
$(function(){
	$("#personinfo").unbind( "click" );
	 $("#personinfo").bind( "click", function() {
	  location.href="personalCenter.do";
	});
	$("#password").unbind( "click" );
	 $("#password").bind( "click", function() {
	  location.href="personalCenter_modifyPwd.do";
	});
		$("#bankMa").unbind( "click" );
		 $("#bankMa").bind( "click", function() {
		  location.href="personalCenterBankCardManage.do";
		});
 	
});		
</script>
<body>

<!--header-->
<h1 class="header mb20"><a href="javascript:window.history.back()"><span class="icon back">&nbsp;</span></a>账户管理 <#if user??><a href="preFundHomePage.do" id="home"><em class="icon home">&nbsp;</em></a><#else><a href="userLogin.do" id="login"><em>登录</em></a></#if>  </h1>
<!--header end-->
<p class="greeting"><em>${user.userName}，您好！</em><span>上次登录：${log.createdatetime?datetime!}</span></p>
<div class="manageList mb20">	
	<ul>
    	<a href="${rc.contextPath}/personalCenter.do"><li><i class="icon info" id="personinfo"></i>个人资料<i class="icon arrowRight"></i></li></a>
        <a href="${rc.contextPath}/personalCenter_modifyPwd.do"><li><i class="icon pwd" id="password"></i>密码管理<i class="icon arrowRight"></i></li></a>
        <a href="${rc.contextPath}/personalCenterBankCardManage.do"><li><i class="icon bank" id="bankMa" ></i>银行卡管理<i class="icon arrowRight"></i></li></a>
    </ul>
	<ul class="mt10">
	<a href="${rc.contextPath}/contactUs.do"><li><i class="icon contact"></i>联系我们<i class="icon arrowRight"></i></li></a>
	<a href="${rc.contextPath}/shareToFriends.do"> <li><i class="icon shareToFriend"></i>推荐给好友<i class="icon arrowRight"></i></li></a>
    </ul>
</div>
<form name="form1" method="post" action="logout.do">
	<input type="submit" name="button" id="button" value="退出登录" class="btn btnOne bg-white">
</form>
<ul class="position-footer menu">
    <a href="${rc.contextPath}/jcbindex.do"><li><i class="jcb"></i>金诚宝</li></a>
    <a href="${rc.contextPath}/myAssetPage.do"><li><i class="myasset"></i>我的资产</li></a>
    <a href="${rc.contextPath}/preFundHomePage.do"><li><i class="fund"></i>基金产品</li></a>
    <a href="${rc.contextPath}/accountManager_index.do" class="current"><li><i class="account"></i>帐户管理</li></a>
</ul>
</body>
</html>