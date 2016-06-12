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
  

<div class="dataListB mb20">	
    <h2>操作成功</h2>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tbody>
          <tr>
            <th>操作</th>
            <td>基金转换</td>
          </tr>
         <tr>
            <th>转出基金</th>
            <td>${fundInfoResp1.fund_code} ${fundInfoResp1.fund_name}</td>
          </tr>
          <tr>
            <th>转入基金</th>
            <td>${fundInfoResp2.fund_code} ${fundInfoResp2.fund_name}</td>
          </tr>
          <tr>
            <th>转换份额(份)</th>
            <td>${shares}</td>              
          </tr>

          <tr>
            <th>银行卡</th>
            <td>${apply.bank_name}        （${apply.bank_no}）</td>                
          </tr>
        </tbody>
    </table>
    <p class="tips">温馨提示：转换申请成功，基金转换正在处理中，预计T+1个交易日确认。</p>
    <a href="${rc.contextPath}/myAssetPage.do"><button type="button" class="btn btnOne bg-white">确认</button></a>
</div>	

<div class="position-footer"><p class="hotline">财富热线：400-068-0058</p></div>
</body>
</html>