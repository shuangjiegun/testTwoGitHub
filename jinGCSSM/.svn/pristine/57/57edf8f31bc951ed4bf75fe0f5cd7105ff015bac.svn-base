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
$(document).ready(function(){
  $("#but").click( function () { 
  	location.href="${rc.ContextPath}jcbindex.do";
  	});
});

</script>
<body>

<!--header-->
<h1 class="header mb20"><a href="javascript:window.history.back()"><span class="icon back">&nbsp;</span></a>金诚宝充值  <#if user??><a href="preFundHomePage.do" id="home"><em class="icon home">&nbsp;</em></a><#else><a href="userLogin.do" id="login"><em>登录</em></a></#if>  </h1>
<!--header end-->
  

<div class="dataListB mb20">	
    <h2>操作成功</h2>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tbody>
          <tr>
            <th>操作</th>
            <td>金诚宝充值</td>
          </tr>
          <tr>
            <th>基金名称</th>
            <td>${jcbfund.fundcode}  ${jcbfund.fundname}</td>
          </tr>
          <tr>
            <th>金额</th>
            <td>${apply.balance}元</td>              
          </tr>
          <tr>
            <th>手续费</th>
            <td>免手续费</td>                                
          </tr>
          <tr>
            <th>银行卡</th>
            <td>${apply.bank_name}       （ ${apply.bank_no}）</td>                
          </tr>
        </tbody>
    </table>
    <p class="tips">温馨提示：扣款成功，预计T+1个交易日后完成份额确认。</p>
    <button type="button" class="btn btnOne bg-white" id="but">确认</button>
</div>	

<div class="position-footer"><p class="hotline">财富热线：400-068-0058</p></div>
</body>
</html>