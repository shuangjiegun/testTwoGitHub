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
		var myreg = /^\d{6}$/;		
		var ischeck;		
		
		$("#button").click(function(){		
					var password = $("#password").val();
					var password2 = $("#password2").val();
			
					if( (password!='') && myreg.test(password)){
								if( (password2!= '')){
		
									if(!(password==password2)){
										alert("两次输入密码不一致！")
										$("#password").focus();
										return false;
									}							
									
								}else{
									alert("请输入确认密码！")
									$("#password").focus();
									return false;
								}
						
					}else{
						alert("请输入6位数字密码")
						$("#password").focus();
						return false;
					}
					
					if(! ($("#agreement").val())){						
						alert("请先阅读并同意协议！")
						return false;
					}

			return true;
		})		
		
		
		$("#agreement").click(function(){		
			ischeck = $(this).prop("checked")
			if(ischeck){
			$("#agreement").val(true);
			}else{
			$("#agreement").val(false);
			}
		})
		
		$('a').click(function(){					
					var aLi = $(this).attr("href")					
					$("#form1").attr("action", aLi).submit()
			return false;
		})
		
		if($("#agreement").val()){		
			$("#agreement").attr("checked", true)
		}
				
	})

</script>

<body>

<!--header-->
<h1 class="header mb30"><a href="javascript:window.history.back()"><span class="icon back">&nbsp;</span></a>设置密码<em class="icon home">&nbsp;</em></h1>
<!--header end-->
<form name="acctOpen" id="form1" method="post" action="${rc.contextPath}/userAcctOpen.do">
	<p class="error none" id="ptip">${error_info}</p>
    <ul class="bg-lightgray formInput mb30">
      <li> <em style="width:100px;">设置密码</em>
		<input type="password" value="${user.password}"  name="password" id="password" class="textInput"  placeholder="请输入六位数字交易密码">
      </li>
      <li> <em style="width:100px;">再次输入密码</em>
        <input type="password" value="${user.password2}" name="password2" id="password2" class="textInput"  placeholder="请输入六位数字交易密码">
      </li>
    </ul>
    <p class="mb30 tl lh200 pl50"><input name="agreement" id="agreement" type="checkbox" value="${user.agreement}"><label for="agreement">我已阅读并同意<br>
    <a href="protocolOne.do" class="color-yellow">《证券基金投资人权益须知》</a><br><a href="${rc.contextPath}/protocolTwo.do" class="color-yellow">《金观诚电子交易服务协议》</a></label></p>
        	<input type="hidden" name="bankCode"  value="${user.bankCode}"/>
			<input type="hidden" name="bankAccount"  value="${user.bankAccount}"/>
			<input type="hidden" name="userName"  value="${user.userName}"/>
			<input type="hidden" name="id_kind_gb"  value="${user.id_kind_gb}"/>
			<input type="hidden" name="id_no"  value="${user.id_no}"/>
			<input type="hidden" name="mobile_tel"  value="${user.mobile_tel}"/>
			<input type="hidden" name="sign_contract_no"  value="${user.sign_contract_no}"/>
    <input type="submit" name="button" id="button" value="完成" class="btn btnOne bg-white">
</form>

<div class="position-footer"><p class="hotline">财富热线：400-068-0058</p></div>
</body>
<script type="text/javascript">

		var error = $("#ptip").html();
		console.log(error)
		if(error){
		$("#ptip").removeClass("none")
		}
		
</script>
</html>