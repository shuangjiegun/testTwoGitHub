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
	$("#config").unbind( "click" );
	 $("#config").bind( "click", function() {
	  location.href="myAssetPage.do";
	});
});		
</script>
<body>

<!--header-->
<h1 class="header mb20"><a href="javascript:window.history.back()"><span class="icon back">&nbsp;</span></a>撤单  <#if user??><a href="preFundHomePage.do" id="home"><em class="icon home">&nbsp;</em></a><#else><a href="userLogin.do" id="login"><em>登录</em></a></#if>  </h1>
<!--header end-->
  

<div class="dataListB mb20">	
    <h2>操作成功</h2>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tbody>
          <tr>
            <th>操作</th>
            <td><#if tradequrResp.fund_busin_code="022">申购</#if>
              	<#if tradequrResp.fund_busin_code="024">赎回</#if>
              	<#if tradequrResp.fund_busin_code="036">转换</#if></td>
          </tr>
              <tr>
                <th>基金</th>
                <td>${tradequrResp.fund_code}   ${tradequrResp.fund_name}</td>
              </tr>
              <tr>
              	 <#if tradequrResp.fund_busin_code="022"> <th>涉及金额</th><td>${tradequrResp.balance}元</td><#else><th>涉及份额</th><td>${tradequrResp.balance}份</td></#if>
                <!--<th>涉及金额</th>
                <td>${tradequrResp.balance}元</td>
                -->
              </tr>
              <!--<tr>
                <th>涉及份额</th>
                <td>0</td>
              </tr>-->
              <tr>
                <th>银行卡</th>
                <td>${apply.bank_name}       （ ${apply.bank_no}）</td>
              </tr>
              <tr>
                <th>申请时间</th>
                <td>${tradequrResp.apply_date}</td>
              </tr>
        </tbody>
    </table>
    <p class="tips">温馨提示：撤单申请成功，正在处理中，资金预计T+2个交易日到账。</p>
    <button type="button" class="btn btnOne bg-white" id="config">确认</button>
</div>	

<div class="position-footer"><p class="hotline">财富热线：400-068-0058</p></div>
</body>
</html>