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
<body>

<!--header-->
<h1 class="header mb20"><a href="javascript:window.history.back()"><span class="icon back">&nbsp;</span></a>基金赎回<a href="${rc.contextPath}/preFundHomePage.do"><em class="icon home">&nbsp;</em></a></h1>
<!--header end-->
  
<form name="form1" method="post" action="${rc.contextPath}/preRedeemTradeSecond.do">	
<input type="hidden" name="bankInfo" value="${u.bankCode}|${u.bankAccount}">
<input type="hidden" name="secuCode" value="${secuCode}">
<input type="hidden" name="fundName" value="${fund_name}">
<input type="hidden" name="trade_acco" value="${u.trade_acco}">
<input type="hidden" name="displayedBankInfo" value="${u.bankAbbrName} ${u.starLikeBankAccount}">
<h2 class="titleh2">填写赎回信息</h2>
		<!-- <p class="error">请填写赎回份额！</p> -->
        <ul class="bg-lightgray formInput mb30">
          <li> <em style="width:100px;">赎回基金</em>
          ${fund_name} ${secuCode}
          </li>
          <li> <em style="width:100px;">赎回份额</em>
            <input type="text" name="money" id="shu" class="textInput" style="width:35%;" placeholder="≤${enable_shares?default(0)?string('#.##')}"><span class="all" style="color:blue;cursor:pointer;">全部赎回</span>
          </li>
          <li> <em style="width:100px;">银行卡</em>
            ${u.bankAbbrName}  ${u.starLikeBankAccount}
          </li>
        </ul>   
        <p class="tips">温馨提示：赎回的资金遵循同卡进出原则。</p>     
  <input type="submit" name="button" id="sel" value="下一步" class="btn btnOne bg-white">
</form>

<div class="position-footer"><p class="hotline">财富热线：400-068-0058</p></div>
</body>
<style>
.all{cursor:pointer;}
</style>
<script src="js/jquery-1.11.1.js"></script>
<script type="text/javascript">
$(function(){
	
	var $info = null;
	var $form = $("#form1");
	var flag = false;
	var share = ${enable_shares?default(0)?string('#.##')};
	$("#sel").click(function(){						
		var san = $("#shu").val();
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
			
			if(parseFloat($.trim(san)) > share){
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
	
	$("span.all").click(function(){
		$("#shu").val(share);
	})
	
})
</script>
</html>