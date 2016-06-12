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
	
	var share = ${cashBack.shares};
	
 	$("#btn").click(function(){
		var san = $("#amount").val();
		var san1 = ${cashBack.shares};
			if($.trim(san) == "" || $.trim(san) == null){
				 alert("请输入金额");
				  $("#amount").focus();
			     return false;
			}
			
			var myreg = /^[0-9]+(\.[0-9]+)?$/
			if(!myreg.test($.trim(san))){
				 alert('请输入你要取得的份额！'); 	
				  $("#amount").focus();
			     return false;		
			}
			
			if(parseFloat($.trim(san)) > parseFloat($.trim(san1))){
				 alert('请输入不大于'+san1+'的数字！');
				  $("#amount").focus();
			     return false;	 	
			}
			$("#form1").submit();
	});
 	
	$("span.all").click(function(){
		$("#amount").val(share);
	})
 	
});		
</script>
<body>

<!--header-->
<h1 class="header mb20"><a href="javascript:window.history.back()"><span class="icon back">&nbsp;</span></a>金诚宝取现  <#if user??><a href="preFundHomePage.do" id="home"><em class="icon home">&nbsp;</em></a><#else><a href="userLogin.do" id="login"><em>登录</em></a></#if>  </h1>
<!--header end-->
  
<form name="form1"  id="form1" method="post" action="${rc.ContextPath}cashBack3.do">		
		<input type="hidden" name="fund_code"  value="${cashBack.fund_code}"/>
		<input type="hidden" name="fund_name"  value="${cashBack.fund_name}"/>
		<input type="hidden" name="bank_no"  value="${cashBack.bank_no}"/>
		<input type="hidden" name="bank_account"  value="${cashBack.bank_account}"/>
		<input type="hidden" name="trade_acco"  value="${cashBack.trade_acco}"/>
		<input type="hidden" name="capital_mode"  value="${cashBack.capital_mode}"/>
    	<h2 class="titleh2">取现金额</h2>
        <ul class="bg-lightgray formInput mb30">
          <li> <em style="width:100px;">基金名称</em>
            ${cashBack.fund_code}  ${cashBack.fund_name}
          </li>
          <li> <em style="width:100px;">可取金额</em>
            ${cashBack.shares}元
          </li>
          <li> <em style="width:100px;">取现金额</em>
            <input type="text" name="shares" id="amount" class="textInput" placeholder="≤${cashBack.shares}"><span class="all" style="color:blue;cursor:pointer;">全部取现</span>
          </li>
        </ul>      
        
    	<h2 class="titleh2">取现方式</h2>
        <ul class="bg-lightgray formInput mb30">
          <li><em style="width:100px;"></em>
          		<input type="radio" name="businessType" id="rdA" value="fast" checked="checked"><label for="rdA">&nbsp;&nbsp;&nbsp;快速取现：T+0</label>
          </li>
          <li> <em style="width:100px;"></em>
          		<input type="radio" name="businessType" id="rdB" value="normal"><label for="rdB">&nbsp;&nbsp;&nbsp;普通取现：T+1</label>
          </li>
        </ul>  
        
        <div class="dataListB mb20">	
          <h2>收款方式</h2>
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tbody>
              <tr>
                <th>银行卡</th>
                <td>${apply.bank_name}       （ ${apply.bank_no}）</td>
              </tr>
              </tbody>
          </table>
        </div>
    	<input type="button" name="button" id="btn" value="下一步" class="btn btnOne bg-white">
</form>


<div class="position-footer"><p class="hotline">财富热线：400-068-0058</p></div>
</body>
</html>