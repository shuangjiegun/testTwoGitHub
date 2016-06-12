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
<link rel="stylesheet" href="css/common.css" type="text/css" />
</head>
<script src="js/jquery-1.11.1.js"></script>
<script type="text/javascript">
$(function(){
		var $form = $("#form1");
		var error = $("#ptip").html();
		if(error){
		$("#ptip").removeClass("none")
		}
		
		$("#sel").click(function(){
			if($.trim($("#password").val()) == "" || $.trim($("#password").val()) == null){
				alert("请输入密码")
				$("#password").focus();
				return false;
				}	
			
			//用户风险评测、基金风险等级对应性判断的查询
			var mydata = 'secuCode=${pi.secuCode}&fundName=${pi.fundName}'
			$.ajax({
				type:"GET",
				async : false,
				url:"${rc.contextPath}/userAndFundRiskLevelRelation.do",
				data:encodeURI(encodeURI(mydata)),
				success:function(data){
					var riskEvaluationName = data.risk_evaluation_name;
					var invest_risk_tolerance_name = data.invest_risk_tolerance_name; 
					var fundName = data.fundName;
					if(data.onOff==1){   //说明需要提示用户谨慎购买	?
						$('#maskLayer').css('height', $(document).height());
						$("#maskLayer").show();
						$('#popLayer-msg').css('left', ($(document).width() - 300) / 2);
						$("#popLayer-msg").fadeIn("fast");
						$("#fname").html(fundName);
						$("#frisk").html(riskEvaluationName);
						$("#ftolerance").html(invest_risk_tolerance_name);
						$("#tom").click(function(){
							$form.submit();
						})	
						$("#btnClose").click(function(){
							$("#maskLayer").hide();
							$("#popLayer-msg").fadeOut();
							return false;
						});

					}else{
						$form.submit();
					}	
				}			
			})			
	})	
})
</script>
<body>

<!--header-->
<h1 class="header mb20"><a href="javascript:window.history.back()"><span class="icon back">&nbsp;</span></a>基金认购<a href="${rc.contextPath}/preFundHomePage.do"><em class="icon home">&nbsp;</em></a></h1>
<!--header end-->
  
<form name="form1" id="form1" method="post" action="${rc.contextPath}/realAllotOrSubscribe.do">	
	<input type="hidden" name="secuCode" value="${pi.secuCode}">
	<input type="hidden" name="tradeType" value="${pi.tradeType}">
	<input type="hidden" name="fundName" value="${pi.fundName}">
	<input type="hidden" name="bankInfo" value="${pi.bankInfo}">
	<input type="hidden" name="displayedBankInfo" value="${pi.displayedBankInfo}">
	<input type="hidden" name="money" value="${pi.money}">
	<div class="dataListB">	
    <h2>确认认购信息</h2>
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
    	<input type="button" name="button" id="sel" value="提交" class="btn btnOne bg-white">
</form>

<div class="position-footer"><p class="hotline">财富热线：400-068-0058</p></div>
<!--弹窗-->
<div id="popLayer-msg">
  <p class="f12 p15 tl lh150">您所选择的产品是<span id="fname"></span>，风险等级为<span id="frisk"></span>，高出您的风险承受能力：<span id="ftolerance"></span>，请谨慎投资！</p>
<button type="button" class="btn btnSmall" id="tom">确认</button>
<button type="button" id="btnClose"  class="btn btnSmall">取消</button>
</div>
<div id="maskLayer"></div>
</body>
</html>