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
<h1 class="header mb20"><a href="javascript:window.history.back()"><span class="icon back">&nbsp;</span></a>基金认购<a href="${rc.contextPath}/preFundHomePage.do"><em class="icon home">&nbsp;</em></a></h1>
<!--header end-->
  

<div class="dataListB mb20">	
    <h2>操作成功</h2>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tbody>
          <tr>
            <th>操作</th>
            <td>基金认购</td>
          </tr>
          <tr>
            <th>基金名称</th>
            <td>${pi.secuCode} ${pi.fundName}</td>
          </tr>
          <tr>
            <th>认购金额</th>
            <td>${pi.money}元</td>              
          </tr>
          <!-- <tr>
            <th>手续费</th>
            <td>6元 fake data</td>                                
          </tr> -->
          <tr>
          <th>支付状态</th>
          <td>
        	<#if pi.deduct_status=='0'>请稍后查询
        	<#elseif pi.deduct_status=='1'>支付失败
        	<#elseif pi.deduct_status=='2'>支付成功
        	<#elseif pi.deduct_status=='3'>支付中，请稍后查询
        	<#else>请您稍后查询
        	</#if>
          </td>                                
        </tr>
          <tr>
            <th>银行卡</th>
            <td>${pi.displayedBankInfo}</td>                
          </tr>
        </tbody>
    </table>
    <p class="tips">温馨提示：扣款成功，预计T+1个交易日后完成认购确认。</p>
    <a href="${rc.contextPath}/myAssetPage.do"><button type="button" class="btn btnOne bg-white">确认</button></a>
</div>	

<div class="position-footer"><p class="hotline">财富热线：400-068-0058</p></div>
</body>
</html>