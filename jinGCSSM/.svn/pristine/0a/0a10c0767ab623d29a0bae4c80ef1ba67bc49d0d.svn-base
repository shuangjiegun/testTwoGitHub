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
<style>
.loadMore{cursor:pointer;}
</style>
<script src="../js/jquery-1.11.1.js"></script>
<script type="text/javascript">
	$(function(){
		var temp = ${fundType!''};
		$("#sel").val(temp);

	})
</script>
<body>

<!--header-->
<h1 class="header"><a href="javascript:window.history.back()"><span class="icon back">&nbsp;</span></a>全部基金<a href="#"><em>&nbsp;</em></a></h1>
<!--header end-->
<div class="search">
    <form name="queryForm" method="post" action="${rc.contextPath}/queryFundProductByCondition.do">
      <select name="fundType" id="sel" value="">
        <option value="">--请选择--</option>
        <option value="0">偏股型</option> 
        <option value="2">货币型</option>
        <option value="5">QDII</option>
        <option value="1">短债型</option>
      </select>
      <input type="text" name="keyWord" value="${keyWord}" id="keyWord" placeholder="基金代码/名称/简拼">
      <input type="submit" name="button" id="button" value="搜索">
    </form>
</div>
<div class="dataListA">	
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <thead>
      <tr>
        <th width="50%">名称</th>
        <th width="25%">最新净值</th>
        <th width="25%">近1年收益</th>
      </tr>
  </thead>
  <#list allFundProduct as all>
			  <tbody>
			      <tr>
			        <td style="text-align:left;"><a href="${rc.contextPath}/singleFundProductDetails.do?secuCode=${all.secuCode}">${all.secuCode} ${all.secuAbbr!''} </a></td>
			        <td>
			        	<#if (all.fundTypeCode==1109)>1<#else>${all.unitNV?default(0)?string('#.####')}</#if>
			        </td>			    
				      <#if (all.rrSinceThisYear>=0)>
				      	<td class="color-red">${all.rrSinceThisYear?default(0)?string('#.##')}%</td>
				      <#else>
				      	<td class="color-green">${all.rrSinceThisYear?default(0)?string('#.##')}%</td>
				      </#if>
			      </tr>      
			      </tbody>
	     </#list>
  </table>
  <p class="loadMore" id="myLoadMore">加载更多<i class="icon pagedown"></i></p>
</div>
<div class="footerDiv"></div>
 <ul class="position-footer menu">
    <a href="${rc.contextPath}/jcbindex.do"><li><i class="jcb"></i>金诚宝</li></a>
    <a href="${rc.contextPath}/myAssetPage.do"><li><i class="myasset"></i>我的资产</li></a>
    <a href="${rc.contextPath}/preFundHomePage.do" class="current"><li><i class="fund"></i>基金产品</li></a>
    <a href="${rc.contextPath}/accountManager_index.do"><li><i class="account"></i>帐户管理</li></a>
</ul>
</body>
<script type="text/javascript">
	var loadNum=1;
	$("#myLoadMore").click(function(){
			var myLoading ="<img src=\"../images/loading.gif\" style=\"margin:0 10px -10px 0\"/>努力加载中...";
			var myLoaded = "加载更多<i class=\"icon pagedown\"></i>";
			$(this).html(myLoading);
	
			loadNum++;
			var fundType = $("#sel").val();
			var keyWord = $("#keyWord").val();
			$.ajax({
		             type: "GET",
		             url: "${rc.contextPath}/loadMoreFundProduct.do",
		             data: 'fundType='+fundType+'&keyWord='+keyWord+'&pageNumber='+loadNum,
		             success: function(data){
		             			$("#myLoadMore").html(myLoaded);
								var tr = '';
		                         $.each(data, function(fundProductIndex, fundProduct){
			                         var vv = Math.pow(10, 4);
			                         var unitNV = fundProduct.fundTypeCode==1109?1:Math.round(fundProduct.unitNV*vv)/vv
			                         var yy = Math.pow(10, 2);	
			                         var rrSinceThisYear = Math.round(fundProduct.rrSinceThisYear*yy)/yy	                            
		                            var rrtd;
		                            if(rrSinceThisYear>=0){
		                            rrtd = "<td class=\"color-red\">" 
		                            }else{
		                            rrtd = "<td class=\"color-green\">"
		                            }
		                            tr += "<tbody><tr><td style=\"text-align:left;\"><a href='${rc.contextPath}/singleFundProductDetails.do?secuCode="+fundProduct.secuCode+"'>"+fundProduct.secuCode+" "+ fundProduct.secuAbbr +"</a></td><td>"+unitNV+"</td>"+rrtd+rrSinceThisYear+"%</td></tr></tbody>";
		                         });	                         		                       
								$("table").append(tr);		                         			      
		                }
		         });		
	})
</script>
</html>