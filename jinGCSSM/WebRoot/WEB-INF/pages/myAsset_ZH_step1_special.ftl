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
	var total=${shareResp.enable_shares};
	$("span.all").click(function(){
		$("#ZH_money").val(total);
	});
	
	$("#button").click(function(){						
		var san = $("#ZH_money").val();
		var firstWord = $.trim(san).substring(0,1);

			if($.trim(san) == "" || $.trim(san) == null){
				 alert("请输入赎回份额")
				 $("#shu").focus();
			     return false;
			}
			
			var myreg = /^[0-9]+(\.[0-9]+)?$/
			if(!myreg.test($.trim(san))){
				 alert('请输入正确的份额！'); 
				 $("#shu").focus();
				 return false; 			
			}
			
			if(parseFloat($.trim(san)) > total){
				 alert('赎回份额应小于等于总份额！'); 
				 $("#shu").focus();
				  return false; 	
			}
			
			if(parseInt(firstWord) <=0){
				 alert('请输入正确的份额！'); 
				 $("#shu").focus();
				 return false; 
			}
			
			return true;
	})
	
});		
</script>
<body>

<!--header-->
<h1 class="header mb20"><a href="javascript:window.history.back()"><span class="icon back">&nbsp;</span></a>基金转换  <#if user??><a href="preFundHomePage.do" id="home"><em class="icon home">&nbsp;</em></a><#else><a href="userLogin.do" id="login"><em>登录</em></a></#if>  </h1>
<!--header end-->
  
<form name="form1" method="post" action="convert_trade2.do">	 
<h2 class="titleh2">填写转换信息</h2>
        <ul class="bg-lightgray formInput mb30">
          <li> <em style="width:100px;">转出基金</em>
            ${fundInfo.fund_code}   ${fundInfo.fund_name}
          </li>
          <li> <em style="width:100px;">转入基金</em>
            <select name="product_in" id="select">
            	<#list listFundInfoResp as fundInfoResp>
            		<option value="${fundInfoResp.fund_code}">${fundInfoResp.fund_code} ${fundInfoResp.fund_name}</option>
            	</#list>
                
            </select>
          </li>
          <li> <em style="width:145px;">转换份额(份)</em>
            <input type="text" name="ZH_money" id="ZH_money" class="textInput" style="width:35%;" placeholder="≤${shareResp.enable_shares?default(0)}"><span class="all" style="color:blue;cursor:pointer;">全部转换</span>
          </li>
          <li> <em style="width:100px;">银行卡</em>
            ${apply.bank_name}        ${apply.bank_no}
          </li>
        </ul>   
        <p class="tips">温馨提示：一般基金转换费=赎回费+申购补差费（以招募说明书载明费率进行补差计算，最低为0），无折扣，实际费用以基金公司确认为准。</p> 
         <input type="hidden" name="fundcode"  value="${fundInfo.fund_code}"/> 
         <input type="hidden" name="bankName"  value="${apply.bank_name}"/> 
         <input type="hidden" name="bankNo"  value="${apply.bank_no}"/> 
         <input type="hidden" name="trade_acco"  value="${trade_acco}"/> 
  		<input type="submit" name="button" id="button" value="下一步" class="btn btnOne bg-white">
</form>

<div class="position-footer"><p class="hotline">财富热线：400-068-0058</p></div>
</body>
</html>