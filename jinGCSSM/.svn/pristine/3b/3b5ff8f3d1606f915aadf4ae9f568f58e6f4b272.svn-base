<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>金观诚帐户及交易确认</title>
<link rel="stylesheet" href="css/global.css" type="text/css" />  
<script type="text/javascript" src="js/WdatePicker/calendar.js"></script>
<script type="text/javascript" src="js/WdatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="js/jquery-1.10.1.min.js"></script>
<script type="text/javascript">
$(function(){
	$("#sbtn").click(function(){
		var ssdate = $("#sdate").val();
		if(!ssdate){
			$(".error").html("请选择申请日期");
		}else{
			$("#timeSearch").submit();
		}
	})
})
</script>
<script type="text/javascript">
$(function(){

	$(".btnSend").click(function(){
		var str = $(this).attr("id");
		var newStr = str.replace("A","B");
		var idno = $("#"+newStr).val();
		var qryDate = $("#qryDate").val()
		
					$.ajax({
						type:"GET",
						async : false,
						url:"${rc.contextPath}/accountEmailSend.do?idno="+idno+"&qryDate="+qryDate,
						success:function(data){
							if(data=='Y'){
								alert("邮件发送成功")
							}else{
								alert("邮件发送失败")
							}					
						}
					})
	})
	
})
</script>
</head>
<body>
<h1 class="headline"><img src="images/logoHead.png">&nbsp;帐户、交易确认管理</h1>

<div class="tabbox">
  <ul class="tabbtn">
    <li class="current">帐户确认</li>
    <li><a href="${rc.contextPath}/tradeEmailFirst.do">交易确认</a></li>
    <li><a href="${rc.contextPath}/shortMessageFirst.do">短信系统</a></li>
  </ul>
  <div class="tabcon">
	<form id="timeSearch" name="form1" method="post" action="${rc.contextPath}/accountEmailMsgQuery.do">
    申请日期：<input type="text" name="applyDate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  id="sdate" value="${rawDate}">
    <input type="button" name="sbtn" id="sbtn" value="查询" class="btn btnSearch"><span class="error"></span>
    </form>
  	<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <thead>
  	  <tr>
  	    <th width="18%" style=" padding:6px 0; border:1px solid #cca97a; background:#e4cdae; color:#963;">客户名称</th>
  	    <th width="17%" style=" padding:6px 0; border:1px solid #cca97a; background:#e4cdae; color:#963;">基金账号</th>
  	    <th width="17%" style=" padding:6px 0; border:1px solid #cca97a; background:#e4cdae; color:#963;">申请日期</th>
  	    <th width="30%" style=" padding:6px 0; border:1px solid #cca97a; background:#e4cdae; color:#963;">业务类别</th>
  	    <th width="18%" style=" padding:6px 0; border:1px solid #cca97a; background:#e4cdae; color:#963;">确认结果</th>
      </tr>
      </thead>
      <tbody>
      <input type="hidden" id="qryDate" value="${qryDate}">
      <#if (emailUsefulList?size>0)>
	      <#list emailUsefulList as outList>
			  	<#list outList as inList>
			  		<input type="hidden" id="B_${outList_index}" value="${inList.id_no}">
			    	  <tr>
			    	    <td style="text-align:center;padding:6px 0; border:1px solid #ccc;">${inList.client_name}</td>
			    	    <td style="text-align:center;padding:6px 0; border:1px solid #ccc;">${inList.ta_acco}</td>
			    	    <td style="text-align:center;padding:6px 0; border:1px solid #ccc;">${inList.apply_date}</td>
			    	    <td style="text-align:center;padding:6px 0; border:1px solid #ccc;">${inList.fund_busin_code_name}</td>
			    	    <td style="text-align:center;padding:6px 0; border:1px solid #ccc;">${inList.taconfirm_flag}</td>
			        </tr>
			  	</#list>
		    	  <tr>
		    	    <td colspan="5"><input type="button" class="btn btnSend" value="发送邮件" id="A_${outList_index}"></input>&nbsp;</td>
		    	  </tr>   
		  </#list>
		<#else>
      <td colspan="5">未查到任何记录，请输入申请日期或更换申请日期再查询 (注：只有留存了邮箱的用户才会被查到)&nbsp;</td>
		</#if> 
      </tbody>
    </table>
  </div>
</div>

</body>
</html>