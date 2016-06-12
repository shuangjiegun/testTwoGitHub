<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>金观诚帐户及交易确认</title>
<link rel="stylesheet" href="css/global.css" type="text/css" />
<script type="text/javascript" src="js/jquery-1.10.1.min.js"></script>
<script type="text/javascript" src="js/WdatePicker/calendar.js"></script>
<script type="text/javascript" src="js/WdatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="js/validate/jquery.validate.js"></script>
<script type="text/javascript">
$(function(){

	$(".tom").click(function(){
		
		var client_name = $(this).parent().parent().prev().children('td').eq(0).html();
		var mobile_tel = $(this).parent().parent().prev().children('td').eq(2).html();
		var apply_date = $(this).parent().parent().prev().children('td').eq(3).html();
		var taconfirm_flag = $(this).parent().parent().prev().children('td').eq(4).html();
		
					$.ajax({
						type:"POST",
						async : false,
			            data:'client_name='+client_name+'&mobile_tel='+mobile_tel+'&apply_date='+apply_date+'&taconfirm_flag='+taconfirm_flag,
						url:"${rc.contextPath}/shortMessageSendAccount.do",
						success:function(data){
							if(data=='Y'){
								alert("短信发送成功")
							}else{
								alert("短信发送失败")
							}					
						}
					})
	})	
})
</script>
<script type="text/javascript">
//客户端验证
$(document).ready(function() {	
	validator=$("#timeSearch").validate({
		
	    /* 设置验证规则 */
	    rules: {
			chooseDate:{
			    required:true
		  	},
		  	serviceCode:{
			    required:true
		  	},
	    },
	    
	    /* 设置错误信息 */
	    messages: {
	    	chooseDate:{
			    required: "请选择日期"
		  	},
		  	serviceCode:{
			    required: "请选择业务类型"
		  	},
	    },
		errorPlacement: function(error, element) {
			error.appendTo( element.parent().find("span") );
		}
	});
});
</script>
</head>
<body>
<h1 class="headline"><img src="images/logoHead.png">&nbsp;帐户、交易确认管理</h1>

<div class="tabbox">
  <ul class="tabbtn">
    <li><a href="${rc.contextPath}/accountEmailFirst.do">帐户确认</a></li>
    <li><a href="${rc.contextPath}/tradeEmailFirst.do">交易确认</a></li>
    <li class="current">短信系统</li>
  </ul>
  <div class="tabcon">
  	<form id="timeSearch" name="form1" method="post" action="${rc.contextPath}/shortMessageQuery.do">
    选择日期：<input type="text" name="chooseDate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="${qryDate}">    
    业务类型：
    <select name="serviceCode" id="serviceCode" value="${serviceCode}">
            <option value="">---请选择---</option>   
            <option value="001">----开户----</option>
            <option value="122">----申购----</option>
            <option value="130">----认购----</option>
            <option value="124">----赎回----</option>
            <option value="138">----转换----</option>
   </select>   
    <input type="submit" name="button" id="button" value="查询" class="btn btnSearch"><span class="error"></span>
    </form>
    <#if serviceCode=="001">
					    <table width="100%" border="0" cellspacing="0" cellpadding="0">
					    <thead>
					  	  <tr>
					  	    <th width="10%" style=" padding:6px 0; border:1px solid #cca97a; background:#e4cdae; color:#963;">客户名称</th>
					  	    <th width="10%" style=" padding:6px 0; border:1px solid #cca97a; background:#e4cdae; color:#963;">身份证号</th>
					  	    <th width="10%" style=" padding:6px 0; border:1px solid #cca97a; background:#e4cdae; color:#963;">手机号码</th>
					  	    <th width="10%" style=" padding:6px 0; border:1px solid #cca97a; background:#e4cdae; color:#963;">申请日期</th>
					  	    <th width="10%" style=" padding:6px 0; border:1px solid #cca97a; background:#e4cdae; color:#963;">确认结果</th>
					      </tr>
					      </thead>
					      <tbody>
					      <input type="hidden" id="qryDate" value="${qryDate}">
					      <#if (accountMessageList?size>0)>
						      <#list accountMessageList as inList>
								  		<input type="hidden" id="B_${outList_index}" value="${inList.id_no}">
								    	  <tr>
								    	    <td style="text-align:center;padding:6px 0; border:1px solid #ccc;">${inList.client_name}</td>
								    	    <td style="text-align:center;padding:6px 0; border:1px solid #ccc;">${inList.id_no}</td>
								    	    <td style="text-align:center;padding:6px 0; border:1px solid #ccc;">${inList.mobile_tel}</td>
								    	    <td style="text-align:center;padding:6px 0; border:1px solid #ccc;">${inList.apply_date}</td>
								    	    <td style="text-align:center;padding:6px 0; border:1px solid #ccc;">${inList.taconfirm_flag}</td>
								        </tr>
								    	  <tr>
								    	    <td colspan="5"><input type="button" class="btn btnSend tom" value="发送短信" id="A_${inList_index}"></input>&nbsp;</td>
								    	  </tr>   
							  </#list>
							<#else>
					      <td colspan="5">未查到任何记录，请重新选择日期和业务类型再查询&nbsp;</td>
							</#if> 
					      </tbody>
					    </table>
    <#else>
						    <table width="100%" border="0" cellspacing="0" cellpadding="0">
						    <thead>
						  	  <tr>
						  	    <th width="6%" style=" padding:6px 0; border:1px solid #cca97a; background:#e4cdae; color:#963;">客户名称</th>
						  	     <th width="13%" style=" padding:6px 0; border:1px solid #cca97a; background:#e4cdae; color:#963;">身份证号</th>
						  	    <th width="8%" style=" padding:6px 0; border:1px solid #cca97a; background:#e4cdae; color:#963;">申请日期</th>
						  	    <th width="8%" style=" padding:6px 0; border:1px solid #cca97a; background:#e4cdae; color:#963;">确认日期</th>
						  	    <th width="12%" style=" padding:6px 0; border:1px solid #cca97a; background:#e4cdae; color:#963;">基金名称</th>
						  	    <th width="12%" style=" padding:6px 0; border:1px solid #cca97a; background:#e4cdae; color:#963;">目标基金名称</th>
						  	    <th width="9%" style=" padding:6px 0; border:1px solid #cca97a; background:#e4cdae; color:#963;">确认金额(元)</th>
						  	    <th width="9%" style=" padding:6px 0; border:1px solid #cca97a; background:#e4cdae; color:#963;">确认份额(份)</th>
						  	    <th width="6%" style=" padding:6px 0; border:1px solid #cca97a; background:#e4cdae; color:#963;">成交净值</th>
						  	    <th width="6%" style=" padding:6px 0; border:1px solid #cca97a; background:#e4cdae; color:#963;">确认结果</th>
						  	    <th width="11%" style=" padding:6px 0; border:1px solid #cca97a; background:#e4cdae; color:#963;">失败原因</th>
						      </tr>
						      </thead>
						      <tbody>
							      <input type="hidden" id="qryDate" value="${qryDate}">
							      <#if (shortMessageList?size>0)>
								      <#list shortMessageList as outList>
										  	<#list outList as inList>
										  		<input type="hidden" id="B_${outList_index}" value="${inList.id_no}">
										    	  <tr>
										    	    <td style="text-align:center;padding:6px 0; border:1px solid #ccc;">${inList.client_name}</td>
										    	    <td style="text-align:center;padding:6px 0; border:1px solid #ccc;">${inList.id_no}</td>
										      	    <td style="text-align:center;padding:6px 0; border:1px solid #ccc;">${inList.apply_date}</td>
										      	    <td style="text-align:center;padding:6px 0; border:1px solid #ccc;">${inList.affirm_date}</td>
										      	    <td style="text-align:center;padding:6px 0; border:1px solid #ccc;">${inList.fund_code_name}</td>
										      	    <td style="text-align:center;padding:6px 0; border:1px solid #ccc;">${inList.target_fund_code_name}</td>
										      	    <td style="text-align:center;padding:6px 0; border:1px solid #ccc;">${inList.trade_confirm_balance}</td>
										      	    <td style="text-align:center;padding:6px 0; border:1px solid #ccc;">${inList.trade_confirm_type}</td>
										      	    <td style="text-align:center;padding:6px 0; border:1px solid #ccc;">${inList.net_value}</td>
										      	    <td style="text-align:center;padding:6px 0; border:1px solid #ccc;">${inList.taconfirm_flag}</td>
										      	    <td style="text-align:center;padding:6px 0; border:1px solid #ccc;">${inList.fail_cause}</td>
										        </tr>
										  	</#list>
									    	  <tr>
									    	    <td colspan="11"><input type="button" class="btn btnSend tonny" value="发送短信" id="A_${outList_index}"></input>&nbsp;</td>
									    	  </tr>   
									  </#list>
								<#else>
							    <td colspan="11">未查到任何记录，请重新选择日期和业务类型再查询&nbsp;</td>
								</#if>
						      </tbody>
						    </table>
    
    </#if>
  </div>
</div>
<script type="text/javascript">
$(function(){
	var serviceCode = $("#serviceCode").attr("value");
	$("#serviceCode option[value="+serviceCode+"]").attr("selected","selected");
})
</script>
<script type="text/javascript">
$(function(){

	$(".tonny").click(function(){
		var id_no = $(this).parent().parent().prev().children('td').eq(1).html();
		var apply_date = $(this).parent().parent().prev().children('td').eq(2).html();
		var affirm_date = $(this).parent().parent().prev().children('td').eq(3).html();
		var serviceCode = $("#serviceCode").attr("value");
					$.ajax({
						type:"POST",
						async : false,
			            data:'id_no='+id_no+'&affirm_date='+affirm_date+'&fund_busin_code='+serviceCode+'&apply_date='+apply_date,
						url:"${rc.contextPath}/shortMessageSendTrade.do",
						success:function(data){
							if(data=='Y'){
								alert("短信发送成功")
							}else{
								alert("短信发送失败")
							}					
						}
					})
	})	
})
</script>
</body>
</html>