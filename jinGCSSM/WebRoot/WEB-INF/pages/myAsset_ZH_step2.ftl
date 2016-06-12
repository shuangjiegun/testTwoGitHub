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
});		
</script>
<body>

<!--header-->
<h1 class="header mb20"><a href="javascript:window.history.back()"><span class="icon back">&nbsp;</span></a>基金转换  <#if user??><a href="preFundHomePage.do" id="home"><em class="icon home">&nbsp;</em></a><#else><a href="userLogin.do" id="login"><em>登录</em></a></#if>  </h1>
<!--header end-->
  
<form name="form1" method="post" action="convert_trade3.do">	
  <div class="dataListB">	
    <h2>确认转换信息</h2>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tbody>
          <tr>
            <th>转出基金</th>
            <td>${fundInfoResp1.fund_code} ${fundInfoResp1.fund_name} </td>
          </tr>
              <tr>
                <th>转入基金</th>
                <td>${fundInfoResp2.fund_code} ${fundInfoResp2.fund_name}</td>
              </tr>
              <tr>
                <th>转换金额(份)</th>
                <td>${ZH_money}</td>
              </tr>

              <tr>
                <th>银行卡</th>
                <td>${apply.bank_name}        （${apply.bank_no}）</td>
              </tr>
        </tbody>
    </table>
    </div>	
    	<h2 class="titleh2">填写交易密码</h2>
    	<p class="error">${error}</p>
        <ul class="bg-lightgray formInput mb30">
          <li> <em style="width:100px;">交易密码</em>
            <input type="password" name="password" id="pwdAgain" class="textInput" placeholder="请输入交易密码">
          </li>
        </ul>
        <input type="hidden" name="fundCode1"  value="${fundInfoResp1.fund_code}"/> 
        <input type="hidden" name="fundCode2"  value="${fundInfoResp2.fund_code}"/> 
        <input type="hidden" name="bankName"  value="${apply.bank_name}"/> 
        <input type="hidden" name="bankNo"  value="${apply.bank_no}"/> 
         <input type="hidden" name="shares"  value="${ZH_money}"/>
         <input type="hidden" name="trade_acco"  value="${trade_acco}"/>
    	<input type="submit" name="button" id="button" value="提交" class="btn btnOne bg-white">
</form>

<div class="position-footer"><p class="hotline">财富热线：400-068-0058</p></div>
</body>
</html>