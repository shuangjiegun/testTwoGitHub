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
		var email=$("#email").val();
		if(""==$.trim(email)){
			alert("请输入电子邮箱");
			return false;
		}
		
		var filter=/^\s*([A-Za-z0-9_-]+(\.\w+)*@(\w+\.)+\w{2,3})\s*$/;
		if (!filter.test(email)){
			 alert('您的电子邮箱格式不正确，请重新输入');
			 $("#email").focus();
			 return false;
		}
		
		$.ajax({
		  type: "GET",
		  async : false,
			url:"${rc.contextPath}/clientinfo_mod_acct.do?email="+email,
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
<h1 class="header mb20"><a href="javascript:window.history.back()"><span class="icon back">&nbsp;</span></a>邮箱管理  <#if user??><a href="preFundHomePage.do" id="home"><em class="icon home">&nbsp;</em></a><#else><a href="userLogin.do" id="login"><em>登录</em></a></#if>  </h1>
<!--header end-->
<form name="form1" method="post" action="">
        <ul class="bg-lightgray formInput mb20">
          <li> <em>电子邮箱</em>
            <input type="text" name="email" id="email" class="textInput"  placeholder="请输入新的电子邮箱" value="${e_mail!''}" >
          </li>
        </ul>
    <input type="button" name="button" id="btnMsg" value="保存" class="btn btnOne bg-white">
</form>
<div class="position-footer"><p class="hotline">财富热线：400-068-0058</p></div>

<!--弹窗-->
<div id="popLayer-msg">
  <p class="f12"><i class="icon success"></i>邮箱更改成功！</p>
  <button type="button" id="btnClose" class="btn btnSmall">确定</button> 
</div>

<div id="maskLayer"></div>
</body>
</html>