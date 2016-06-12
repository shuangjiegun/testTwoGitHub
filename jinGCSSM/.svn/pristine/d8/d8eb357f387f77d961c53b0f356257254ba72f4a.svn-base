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
	
	$("#button").click(function(){
	
		if($.trim($("#userName").val()) == "" || $.trim($("#userName").val()) == null){
		alert("请填写真实姓名")
		$("#userName").focus();
		return false;
		}
		
		if($.trim($("#id_no").val()) == "" || $.trim($("#id_no").val()) == null){
		alert("请填写证件号码")
		$("#id_no").focus();
		return false;
		}
		
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
       
		if($.trim($("#verifycode").val()) == "" || $.trim($("#verifycode").val()) == null){
		alert("请输入验证码")
		$("#verifycode").focus();
		return false;		
		}
		
		var bankCode = $("#bankCode").val();
       	var bankAccount = $("#bankAccount").val();
       	var userName = $("#userName").val();
       	var id_kind_gb = $("#id_kind_gb").val();
       	var id_no = $("#id_no").val();       	
       	var mobile_tel = $("#mobile_tel").val();
       	var original_serial_no = $("#original_serial_no").val();
       	var verifycode = $("#verifycode").val();
       	var mydata = 'bankCode='+bankCode+'&bankAccount='+bankAccount+'&userName='+userName+'&id_kind_gb='+id_kind_gb+'&id_no='+id_no+'&mobile_tel='+mobile_tel+'&flag=2'+'&verifycode='+verifycode+'&original_serial_no='+original_serial_no
       	       	$.ajax({
		             type: "GET",
		             async: false,
		             url: "${rc.contextPath}/mobilePhoneShortMessageCodeFirst.do",
		             data: encodeURI(encodeURI(mydata)),
		             success: function(data){
		             	if(data.status=='N'){
		             		alert('出错啦')
		             	}
		             	if(data.status=='Y'){
		             		$('#sign_contract_no').val(data.sign_contract_no);   
		             		$("#frmLogin").submit(); 
		             	}
		                 			      
		             },
		             error: function (error) {
            			alert(error);
        			}
		         });
		
	})
})
</script>
<body>
<!--header-->
<h1 class="header mb30"><a href="javascript:window.history.back()"><span class="icon back">&nbsp;</span></a>快速开户<a href="${rc.contextPath}/preFundHomePage.do"><em class="icon home">&nbsp;</em></a></h1>
<!--header end-->
<form name="frmLogin" id="frmLogin" method="post" action="preTwoUserAcctOpen.do">
	<input type="hidden" name="original_serial_no" id="original_serial_no" value="" />
	<input type="hidden" name="sign_contract_no" id="sign_contract_no" value="" />
	<p class="error none" id="ptip">${error_info}</p>
    <fieldset>
        <ul class="bg-lightgray formInput mb20">
          <li> <em>真实姓名</em>
            <input type="text"  value="${userName}" name="userName" id="userName" class="textInput"  placeholder="请输入真实姓名">
          </li>
          <li> <em>证件类型</em>
            <select name="id_kind_gb" id="id_kind_gb" value="${certificateName}">
				<#list certificateTypes as var>
             		 <option value="${var.certificateCode}">${var.certificateName} </option>
				</#list>
            </select>
          </li>
          <li> <em>证件号码</em>
            <input type="text" value="${id_no}" name="id_no" id="id_no" class="textInput"  placeholder="请输入证件号码">
          </li>
        </ul>
    </fieldset>
        <fieldset>
        <ul class="bg-lightgray formInput mb20">
          <li> <em>银行名称</em>
             <select name="bankCode" id="bankCode" value="${bankCode}">
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
            <input type="text" value="${mobile_tel}" name="mobile_tel" id="mobile_tel" class="textInput"  placeholder="银行预留手机号" style="width:40%;"><input type="button" id="code" class="btn btnGetCode" value="获取验证码" />
          </li>
          <li> <em>验证码</em>
            <input type="text" name="verifycode" id="verifycode" class="textInput"  placeholder="请输入验证码">
          </li>
        </ul>
    </fieldset>
    <input type="button" name="button" id="button" value="下一步" class="btn btnOne bg-white">
</form>
<dl class="tips">
	<dt>温馨提示：</dt>
    <dd>1. 输入的手机号，需要和您在银行预留的手机号一致。</dd>
    <dd>2. 绑定银行卡前请先获取手机验证码进行验证操作。</dd>
</dl>
<p class="hotline bg-lightgray" style="border-top:1px solid #e3e4de;">财富热线：400-068-0058</p>
</body>
<script type="text/javascript">

		var error = $("#ptip").html();
		console.log(error)
		if(error){
		$("#ptip").removeClass("none")
		}
		
</script>

<script type="text/javascript">

	$("#code").click(function(){
		if($.trim($("#bankAccount").val()) == "" || $.trim($("#bankAccount").val()) == null){
			alert("请输入银行卡号")
			$("#bankAccount").focus();
			return false;
			}
			
			if($.trim($("#userName").val()) == "" || $.trim($("#userName").val()) == null){
			alert("请填写真实姓名")
			$("#userName").focus();
			return false;
			}
			
			if($.trim($("#id_no").val()) == "" || $.trim($("#id_no").val()) == null){
			alert("请填写证件号码")
			$("#id_no").focus();
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
                    $("#code").val( " 重新获取("+count+")");
                    if (count == 0) {
                        $("#code").val("重新获取").removeAttr("disabled");
                        clearInterval(countdown);
                    }
                    count--;
                }
                
       	var bankCode = $("#bankCode").val();
       	var bankAccount = $("#bankAccount").val();
       	var userName = $("#userName").val();
       	var id_kind_gb = $("#id_kind_gb").val();
       	var id_no = $("#id_no").val();       	
       	var mobile_tel = $("#mobile_tel").val();
       	var mydata = 'bankCode='+bankCode+'&bankAccount='+bankAccount+'&userName='+userName+'&id_kind_gb='+id_kind_gb+'&id_no='+id_no+'&mobile_tel='+mobile_tel+'&flag=1'
       	       	$.ajax({
		             type: "GET",
		             async: false,
		             url: "${rc.contextPath}/mobilePhoneShortMessageCodeFirst.do",
		             data: encodeURI(encodeURI(mydata)),
		             success: function(data){
		                 $('#original_serial_no').val(data.original_serial_no);    			      
		             },
		             error: function (error) {
            			alert(error);
        			}
		         });
		return true;
	})
</script>

</html>