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
<link href="../css/common.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="../js/jquery-1.10.1.min.js"></script>
<script src="../js/jquery-1.11.1.js"></script>
<script type="text/javascript">
$(function(){
	$("#popLayer-msg").hide();
	$("#btnClose").unbind( "click" );
	 $("#btnClose").bind( "click", function() {
	  location.href="accountManager_index.do";
	});
	$("#btnMsg").click(function(){
		var mobilephone=$("#mobilephone").val();
		$.ajax({
		  type: "GET",
		  async : false,
			url:"${rc.contextPath}/clientinfo_mod_acct.do?mobilephone="+mobilephone,
			success:function(data){
				if(data.result=="success"){
					$('#maskLayer').css('height', $(document).height());
					$("#maskLayer").show();
					$('#popLayer-msg').css('left', ($(document).width() - 300) / 2);
					$("#popLayer-msg").fadeIn("fast");
				}else{
					alert(data.result)
					//$("#maskLayer").html(data.result);
				}
			}
		});
	});
});		
</script>
</head>
<body>

<!--header-->
<h1 class="header mb20"><a href="javascript:window.history.back()"><span class="icon back">&nbsp;</span></a>手机管理<#if user??><a href="preFundHomePage.do" id="home"><em class="icon home">&nbsp;</em></a><#else><a href="userLogin.do" id="login"><em>登录</em></a></#if>  </h1>
<!--header end-->
<form name="form1" method="post" action="">
	<p class="tc f14 mb10">现绑定手机号：${phone}</p>
	<!--<p class="error">请输入正确的手机号！</p>-->
        <ul class="bg-lightgray formInput mb20">
          <li> <em>新手机号</em>
            <input type="text" name="mobilephone" id="mobilephone" class="textInput"  placeholder="请输入新的手机号" >
          </li>
          <li> <em>验证码</em>
            <input type="text" name="verifycode" id="verifycode" class="textInput"  placeholder="请输入六位验证码" style="width:49%;"><button type="button" class="btn btnGetCode">获取验证码</button>
          </li>
        </ul>
    <input type="button" name="button" id="btnMsg" value="保存" class="btn btnOne bg-white">
</form>
<div class="position-footer"><p class="hotline">财富热线：400-068-0058</p></div>

<!--弹窗-->
<div id="popLayer-msg">
  <p class="f12"><i class="icon success"></i>手机号更改成功！</p>
  <button type="button" id="btnClose" class="btn btnSmall">确定</button> 
</div>
<div id="maskLayer"></div>
</body>
</html>