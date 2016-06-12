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
<style>
#tom{cursor:pointer;}
</style>
</head>

<body>

<!--header-->
<h1 class="header mb20"><a href="javascript:window.history.back()"><span class="icon back">&nbsp;</span></a>基金申购<a href="${rc.contextPath}/preFundHomePage.do"><em class="icon home">&nbsp;</em></a></h1>
<!--header end-->
  
<form name="form1" id="form1" method="post" action="${rc.contextPath}/preAllotOrSubscribeSecond.do">	
	<input type="hidden" name="tradeType" value="${tradeType}">
	<input type="hidden" name="secuCode" value="${secuCode}">
	<input type="hidden" name="fundName" value="${fund_name}">
	<input type="hidden" name="riskEvaluationName" value="${foe.riskEvaluationName}">
	

  <div class="dataListB">	
    <h2>申购信息</h2>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tbody>
          <tr>
            <th>基金名称</th>
            <td>${secuCode}  ${fund_name}</td>
          </tr>
              <tr>
                <th>基金类型</th>
                <td>
            			<#if foe??>
		            	${foe.fundTypeCodeName}
		            	<#else>
		            	fake data
		            	</#if>
                </td>
              </tr>
              <tr>
                <th>风险等级</th>
                <td>
                	<#if foe??>
                	${foe.riskEvaluationName}
                	<#else>
                	fake data
                	</#if>
                </td>
              </tr>
              <tr>
                <th>付费方式</th>
                <td>
		            	<#if share_type=="A">
		            		前端付费
		            	<#else>
		            		fake data
		            	</#if>              
                </td>
              </tr>
        </tbody>
    </table>
    </div>	
    	<h2 class="titleh2">填写支付信息</h2>
    	<!-- <p class="error">申购金额不足100元！</p> -->
        <ul class="bg-lightgray formInput mb30">
          <li> <em style="width:100px;">申购金额</em>
            <input type="text" name="money" id="shen" class="textInput"  placeholder="1000元起">
          </li>
          <li> <em style="width:100px;">银行卡</em>
            <select name="bankCode" id="bankCode" value="${bankCode}">
			  <#list uiLi as ui>
		             <option value="${ui.bankCode}">${ui.bankAbbrName}  ${ui.starLikeBankAccount}</option>
			 </#list>          
           </select>           
          </li>
        </ul>        
	<#list uiLi as ui>
      	<input type="hidden" name="bankInfo" value="${ui.bankCode}|${ui.bankAccount}">
      	<input type="hidden" name="displayedBankInfo" value="${ui.bankAbbrName} ${ui.starLikeBankAccount}">
	 </#list> 
    	<input type="button" name="button" id="sel" value="下一步" class="btn btnOne bg-white">
</form>

<div class="position-footer"><p class="hotline">财富热线：400-068-0058</p></div>

<!--弹窗-->
<div id="popLayer-msg">
  <p class="f12">您还未进行风险测评！<br>默认风险等级：<b>保守型</b></p>
  <a href="${rc.contextPath}/prePaperInfoList.do"><button type="button" class="btn btnSmall">现在就去测评</button></a>
<button type="button" class="btn btnSmall"   id="tom">继续申购</button>
</div>
<div id="maskLayer"></div>
</body>

<script src="js/jquery-1.11.1.js"></script>
<script type="text/javascript">
$(function(){
	
	var $info = null;
	var $form = $("#form1");
	var flag = false;
	$("#sel").click(function(){						
		var san = $("#shen").val();

			if($.trim(san) == "" || $.trim(san) == null){
				 alert("请输入金额")
				 $("#shen").focus();
			     return false;
			}
			
			var myreg = /^([1-9]\d*)$/
			if(!myreg.test($.trim(san))){
				 alert('请输入大于100的整数！'); 
				 $("#shen").focus();
				 return false; 			
			}
			
			if(parseInt($.trim(san)) < 1000){
				 alert('请输入大于1000的整数！'); 
				 $("#shen").focus();
				  return false; 	
			}
					
					//风险评测的查询
					$.ajax({
						type:"GET",
						async : false,
						url:"${rc.contextPath}/paperinfoQryAcct.do",
						success:function(data){
							if(parseInt(data.successType)==2){	
								$('#maskLayer').css('height', $(document).height());
								$("#maskLayer").show();
								$('#popLayer-msg').css('left', ($(document).width() - 300) / 2);
								$("#popLayer-msg").fadeIn("fast");
								
								$("#tom").click(function(){
									$form.submit();
								})								
							}							
							if(parseInt(data.successType)==0){
								flag = true;
							}
						}
					})
			
			if(flag){
				$form.submit();
			}
				
	})
})
</script>

</html>