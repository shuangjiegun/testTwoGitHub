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
		var error = $("#ptip").html();
		if(error){
		$("#ptip").removeClass("none")
		}
		
		$("#button").click(function(){
			if($.trim($("#password").val()) == "" || $.trim($("#password").val()) == null){
				alert("请输入密码")
				$("#password").focus();
				return false;
				}			
			return true;
		})
})		
</script>
<body>

<!--header-->
<h1 class="header mb20"><a href="javascript:window.history.back()"><span class="icon back">&nbsp;</span></a>基金赎回<a href="${rc.contextPath}/preFundHomePage.do"><em class="icon home">&nbsp;</em></a></h1>
<!--header end-->
  
<form name="form1" method="post" action="${rc.contextPath}/realRedeemTrade.do">	
<input type="hidden" name="secuCode" value="${pi.secuCode}">
<input type="hidden" name="trade_acco" value="${pi.trade_acco}">
<input type="hidden" name="money" value="${pi.money}">
<input type="hidden" name="fundName" value="${pi.fundName}">
<input type="hidden" name="displayedBankInfo" value="${pi.displayedBankInfo}">
  <div class="dataListB">	
    <h2>确认赎回信息</h2>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tbody>
          <tr>
            <th>操作</th>
            <td>基金赎回</td>
          </tr>
              <tr>
                <th>基金名称</th>
                <td>${pi.fundName} ${pi.secuCode}</td>
              </tr>
              <tr>
                <th>赎回份额(份)</th>
                <td>${pi.money}</td>
              </tr>
              <tr>
                <th>银行卡</th>
                <td>${pi.displayedBankInfo}</td>
              </tr>
        </tbody>
    </table>
    </div>	
    	<h2 class="titleh2">填写交易密码</h2>
    	<!-- <p class="error">交易密码不正确！</p> -->
    	<p class="error none" id="ptip">${error_info}</p>
        <ul class="bg-lightgray formInput mb30">
          <li> <em style="width:100px;">交易密码</em>
            <input type="password" name="password" id="password" class="textInput" placeholder="请输入交易密码">
          </li>
        </ul>
        <p class="tips">温馨提示：15:00后赎回将顺延1个交易日确认。双休日及节假日将不进行赎回确认。非货币类基金的赎回将根据您持有的时间产生一定赎回费</p>       
    	<input type="submit" name="button" id="button" value="提交" class="btn btnOne bg-white">
</form>

<div class="position-footer"><p class="hotline">财富热线：400-068-0058</p></div>
</body>
</html>