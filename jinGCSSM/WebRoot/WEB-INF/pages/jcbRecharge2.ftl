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
	$("#button").click(function(){
		var san = $("#pwdAgain").val();

			if($.trim(san) == "" || $.trim(san) == null){
				 alert("请输入金额");
				  $("#pwdAgain").focus();
			     return false;
			}
			
			var myreg = /^([1-9]\d*)$/
			if(!myreg.test($.trim(san))){
				 alert('请输入大于100的整数！'); 	
				  $("#pwdAgain").focus();
			     return false;		
			}
			
			if(parseInt($.trim(san)) < 1000){
				 alert('请输入大于1000的整数！');
				  $("#pwdAgain").focus();
			     return false;	 	
			}
			$("#form1").submit();
	});
	
 	
});		
</script>
<body>

<!--header-->
<h1 class="header mb20"><a href="javascript:window.history.back()"><span class="icon back">&nbsp;</span></a>金诚宝充值  <#if user??><a href="preFundHomePage.do" id="home"><em class="icon home">&nbsp;</em></a><#else><a href="userLogin.do" id="login"><em>登录</em></a></#if>  </h1>
<!--header end-->
  
<form name="form1" method="post" action="${rc.ContextPath}jcbRecharge3.do">		
    	<h2 class="titleh2">充值金额</h2>
        <ul class="bg-lightgray formInput mb30">
          <li> <em style="width:100px;">基金名称</em>
            ${jcbfund.fundcode}  ${jcbfund.fundname}
          </li>
          <li> <em style="width:100px;">金额</em>
            <input type="text" name="amount" id="pwdAgain" class="textInput" vaule="" placeholder="1000元起充">
          </li>
        </ul>        
        <div class="dataListB">	
          <h2>支付方式</h2>
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tbody>
              <tr>
                <th>银行卡</th>
                <td>${apply.bank_name}       （ ${apply.bank_no}）</td>
              </tr>
              </tbody>
          </table>
        </div>
        <p class="tips">温馨提示: 为保证账户安全，资金需同卡进出</p>
        <input type="hidden" name="fundcode"  value="${jcbfund.fundcode}"/> 
        <input type="hidden" name="fundname"  value="${jcbfund.fundname}"/> 
        <input type="hidden" name="fund_code"  value="${apply.fund_code}"/>   
        <input type="hidden" name="bank_no"  value="${apply.bank_no}"/>
         <input type="hidden" name="bank_name"  value="${apply.bank_name}"/>
        <input type="hidden" name="bank_account"  value="${apply.bank_account}"/>   
        <input type="hidden" name="trade_acco"  value="${apply.trade_acco}"/>   
    	<input type="submit" name="button" id="button" value="下一步" class="btn btnOne bg-white">
</form>


<div class="position-footer"><p class="hotline">财富热线：400-068-0058</p></div>
</body>
</html>