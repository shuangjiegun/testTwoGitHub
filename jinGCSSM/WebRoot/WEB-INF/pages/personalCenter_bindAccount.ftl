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
	var ischeck;	
	var error = $("#ptip").html();
	console.log(error)
	if(error){
	$("#ptip").removeClass("none")
	}
	
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
	
	
	$("#button").click(function(){
		
		if($.trim($("#bankAccount").val()) == "" || $.trim($("#bankAccount").val()) == null){
		alert("请输入银行卡号")
		$("#bankAccount").focus();
		return false;
		}	
		
		if($.trim($("#mobile_tel").val()) == "" || $.trim($("#mobile_tel").val()) == null){
		alert("请输入手机号码")
		$("#mobile_tel").focus();
		return false;
		}
		
		if($.trim($("#mobile_tel").val()).length != 11){
		alert("请输入有效的手机号码")
		$("#mobile_tel").focus();
		return false;
		}
		
	   var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/; 
       if(!myreg.test($.trim($("#mobile_tel").val()))) 
       { 
           alert('请输入有效的手机号码！'); 
           $("#mobile_tel").focus();
           return false; 
       }
       
		if(! ($("#agreement").val())){						
			alert("请先阅读并同意协议！")
			return false;
		}
		
		if($.trim($("#verifycode").val()) == "" || $.trim($("#verifycode").val()) == null){
		alert("请输入验证码")
		$("#verifycode").focus();
		return false;		
		}
		
		var bankCode = $("#sel").val();
       	var bankAccount = $("#bankAccount").val();      	
       	var mobile_tel = $("#mobile_tel").val();
       	var original_serial_no = $("#original_serial_no").val();
       	var verifycode = $("#verifycode").val();
       	var mydata = 'bankCode='+bankCode+'&bankAccount='+bankAccount+'&mobile_tel='+mobile_tel+'&flag=2'+'&verifycode='+verifycode+'&original_serial_no='+original_serial_no
       	       	$.ajax({
		             type: "GET",
		             async: false,
		             url: "${rc.contextPath}/mobilePhoneShortMessageCodeFirst.do",
		             data: encodeURI(encodeURI(mydata)),
		             success: function(data){
		             	if(data.status=='N'){
		             		alert('请联系管理员')
		             	}
		             	if(data.status=='Y'){
		             		//alert('正确的')
		             		$('#sign_contract_no').val(data.sign_contract_no);   
		             		$('#form1').submit();
		             	}
		                 			      
		             },
		             error: function (error) {
            			alert('请联系管理员');
        			}
		         });
	})
})
</script>
<body>
<!--header-->
<h1 class="header mb30"><a href="javascript:window.history.back()"><span class="icon back">&nbsp;</span></a>绑定银行卡<a href="${rc.contextPath}/preFundHomePage.do"><em class="icon home">&nbsp;</em></a></h1>
<!--header end-->
<p class="tips">温馨提示：您只能添加本人的银行卡</p>
<form name="acctOpen" id="form1" method="post" action="${rc.contextPath}/tradeAccountAdd.do">
	<input type="hidden" name="bindCard"  id="bindCard" value="Y"/>
	<input type="hidden" name="original_serial_no" id="original_serial_no" value="" />
	<input type="hidden" name="sign_contract_no" id="sign_contract_no" value="" />
	<!--<p class="error">您的银行卡号输入有误！</p> -->
	<p class="error none" id="ptip">${error_info}</p>
    <fieldset>
        <ul class="bg-lightgray formInput mb20">
          <li> <em>选择银行</em>
          <select name="bankCode" id="sel" value="${bankCode}">
			  <#list banks as var>
		             <option value="${var.bankCode}">${var.bankName}</option>   
			 </#list>          
         </select>
          </li>
          <li> <em>银行卡号</em>
          <input type="text" value="${bankAccount}" name="bankAccount" id="bankAccount" class="textInput"  placeholder="请输入银行卡号">
          </li>
        </ul>
    </fieldset>
    <fieldset>
        <ul class="bg-lightgray formInput mb20">
          <li> <em>手机号</em>
            <input type="text" value="${mobile_tel}" name="mobile_tel" id="mobile_tel" class="textInput"  placeholder="请输入预留手机号">
          </li>
          <li> <em>验证码</em>
            <input type="text" name="verifycode" id="verifycode" class="textInput"  placeholder="请输入验证码" style="width:41%;"><input type="button" id="code" class="btn btnGetCode" value="获取验证码" />
          </li>
        </ul>
    </fieldset>
    <p class="mb30 tc"><input name="agreement" id="agreement" type="checkbox" value="${agreement}"><label for="agreement">我同意
    <a href="${rc.contextPath}/protocolTwo.do" class="color-yellow">《金观诚电子交易服务协议》</a></label></p>
    <input type="button" name="button" id="button" value="绑定银行卡" class="btn btnOne bg-white">
</form>
<div class="position-footer"><p class="hotline">财富热线：400-068-0058</p></div>
</body>
<script type="text/javascript">
$(function(){
	var fto = ${bankCode}    //拿后台传过来的bankCode值
	if(fto==002){
		$("#sel option[value='002']").attr("selected",true); 
	}else if(fto==003){
		$("#sel option[value='003']").attr("selected",true); 
	}else if(fto==004){
		$("#sel option[value='004']").attr("selected",true); 
	}else if(fto==005){
		$("#sel option[value='005']").attr("selected",true); 
	}else if(fto==007){
		$("#sel option[value='007']").attr("selected",true); 
	}else if(fto==009){
		$("#sel option[value='009']").attr("selected",true); 
	}else if(fto==010){
		$("#sel option[value='010']").attr("selected",true); 
	}else if(fto==014){
		$("#sel option[value='014']").attr("selected",true); 
	}else if(fto==920){
		$("#sel option[value='920']").attr("selected",true); 
	}else if(fto==015){
		$("#sel option[value='015']").attr("selected",true); 
	}else if(fto==016){
		$("#sel option[value='016']").attr("selected",true); 
	}else{
		$("#sel option[value='']").attr("selected",true); 
	}
})
</script>
<script type="text/javascript">

$("#code").click(function(){
	if($.trim($("#bankAccount").val()) == "" || $.trim($("#bankAccount").val()) == null){
		alert("请输入银行卡号")
		$("#bankAccount").focus();
		return false;
		}
		
		if($.trim($("#mobile_tel").val()) == "" || $.trim($("#mobile_tel").val()) == null){
		alert("请输入手机号码")
		$("#mobile_tel").focus();
		return false;
		}
		
		if($.trim($("#mobile_tel").val()).length != 11){
		alert("请输入有效的手机号码")
		$("#mobile_tel").focus();
		return false;
		}
		
	   var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/; 
       if(!myreg.test($.trim($("#mobile_tel").val()))) 
       { 
           alert('请输入有效的手机号码！'); 
           $("#mobile_tel").focus();
           return false; 
       }

			var count = 90;
            var countdown = setInterval(CountDown, 1000);
            function CountDown() {
                $("#code").attr("disabled", true);
                $("#code").val(" 重新获取("+count+")");
                if (count == 0) {
                    $("#code").val("重新获取").removeAttr("disabled");
                    clearInterval(countdown);
                }
                count--;
            }
            
   	var bankCode = $("#sel").val();
   	var bankAccount = $("#bankAccount").val();      	
   	var mobile_tel = $("#mobile_tel").val();
   	var mydata = 'bankCode='+bankCode+'&bankAccount='+bankAccount+'&mobile_tel='+mobile_tel+'&flag=1'
   	       	$.ajax({
	             type: "GET",
	             async: false,
	             url: "${rc.contextPath}/mobilePhoneShortMessageCodeFirst.do",
	             data: encodeURI(encodeURI(mydata)),
	             success: function(data){
	                 $('#original_serial_no').val(data.original_serial_no);    			      
	             },
	             error: function (error) {
        			alert('没有真实数据');
    			}
	         });
   	return true;
})
	
</script>
</html>