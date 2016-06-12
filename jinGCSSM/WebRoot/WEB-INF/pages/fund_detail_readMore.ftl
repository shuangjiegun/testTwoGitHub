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
<style>
.loadMore{cursor:pointer;}
</style>
<script src="js/jquery-1.11.1.js"></script>
<script type="text/javascript">
$(function(){

function addZero(str) {
        return str < 10 ? '0' + str:str;
    }
    
	var loadNum=1;
	var secuCode = $("#tom").val();
	$(".loadMore").click(function(){
			var myLoading ="<img src=\"../images/loading.gif\" style=\"margin:0 10px -10px 0\"/>努力加载中...";
			var myLoaded = "加载更多<i class=\"icon pagedown\"></i>";
			$(this).html(myLoading);
			loadNum++;
			$.ajax({
		             type: "GET",
		             url: "${rc.contextPath}/loadMoreEarningsTrend.do",
		             data: 'secuCode='+secuCode+'&pageNumber='+loadNum,
		             success: function(data){
		             			$(".loadMore").html(myLoaded);
								var tr = '';
		                         $.each(data, function(EarningsTrendIndex, EarningsTrend){
		                         	 var data = new Date(EarningsTrend['enddate']);
			                   		 var year = data.getFullYear();  
								     var month = data.getMonth() + 1;  
								     var day = data.getDate();  
								     var enddate = year+"-"+addZero(month)+"-"+addZero(day)
		                         	var vv = Math.pow(10, 4);
		                         	var yy = Math.pow(10, 2);
		                         	
			                         var unitNV = Math.round(EarningsTrend.unitNV*vv)/vv	
			                         var utd;
			                         if(unitNV>=0){
			                         	utd = "<td class=\"\">"
			                         }else{
			                         	utd = "<td class=\"\">"
			                         }

			                         var flag = '<#if chiName?index_of("货币")!=-1>22<#else>33</#if>'
			                         if(flag==22){
			                         var accumulatedUnitNV = Math.round(EarningsTrend.accumulatedUnitNV*yy)/yy	
			                         var autd;
			                         	  if(accumulatedUnitNV>=0){
			                         		autd = "<td class=\"color-red\">"
			                         	}else{
			                         		autd = "<td class=\"color-green\">"
			                         }
			                         accumulatedUnitNV = accumulatedUnitNV +"%";
			                         }else{
			                         	var accumulatedUnitNV = Math.round(EarningsTrend.accumulatedUnitNV*vv)/vv		
			                         	var autd = "<td>"
			                         }
		
			                         var nVDailyGrowthRate = Math.round(EarningsTrend.nVDailyGrowthRate*yy)/yy		
			                         var nvtd;
			                         if(nVDailyGrowthRate>=0){
			                         	nvtd = "<td class=\"color-red\">"
			                         }else{
			                         	nvtd = "<td class=\"color-green\">"
			                         }	                   		

		                            tr += "<tbody><tr><td>"+enddate+"</td>"+utd+ unitNV+"</td>"+autd+accumulatedUnitNV+"</td>"+nvtd+nVDailyGrowthRate+"%</td></tr></tbody>";
		                         });	                         		                       
								$("#table_1").append(tr);		                         			      
		                      }
		         });		
	})
})
</script>
<body>
<!--header-->
<h1 class="header"><a href="javascript:window.history.back()"><span class="icon back">&nbsp;</span></a>收益走势<a href="${rc.contextPath}/preFundHomePage.do"><em class="icon home">&nbsp;</em></a></h1>
<!--header end-->

<div class="dataListA mt10">	
  <table width="100%" border="0" cellspacing="0" cellpadding="0" id="table_1">
      <thead>
          <tr>
            <th width="31%">日期</th>
            <th width="24%"><#if chiName?index_of("货币")!=-1>万份收益<#else>单位净值</#if></th>
            <th width="23%"><#if chiName?index_of("货币")!=-1>7日年化<#else>累计净值</#if></th>
            <th width="22%"><#if chiName?index_of("货币")!=-1>14日年化<#else>日涨跌幅</#if></th>
          </tr>
      </thead>
      <#list etList as et>
              <tbody>
                  <tr>
                    <td width="31%">${et.enddate?string("yyyy-MM-dd")}</td>
                    <#if (et.unitNV>=0)>
		                    <td width="24%">
		                  		  ${et.unitNV?default(0)?string('0.0000')}
		                    </td>
	                    <#else>
	                   		<td width="24%">
		                  		  ${et.unitNV?default(0)?string('0.0000')}
		                    </td>
	                    </#if>
	                    
	                    
	                    <#if chiName?index_of("货币")!=-1>
	                    	<#if (et.accumulatedUnitNV>=0)>
		                    	<td width="24%" class="color-red">
		                  		  ${et.accumulatedUnitNV?default(0)?string('0.00')}%
		                    	</td>
	                    	<#else>
	                   			<td width="24%" class="color-green">
		                  		  ${et.accumulatedUnitNV?default(0)?string('0.00')}%
		                    	</td>
	                    	</#if>
	                    <#else>
	                    		 <td width="24%">
		                  		  	${et.accumulatedUnitNV?default(0)?string('0.0000')}
		                    	</td>
	                    </#if>
	                    
	                    
	                   <#if (et.nVDailyGrowthRate>=0)>
		                    <td width="24%" class="color-red">
		                  		  ${et.nVDailyGrowthRate?default(0)?string('0.00')}%
		                    </td>
	                    <#else>
	                   		<td width="24%" class="color-green">
		                  		  ${et.nVDailyGrowthRate?default(0)?string('0.00')}%
		                    </td>
	                    </#if>
                  </tr>
              </tbody>
            </#list>
  </table>
  <p class="loadMore"  id="myLoadMore">加载更多<i class="icon pagedown"></i></p>
  <input type="hidden" name="secuCode" value="${secuCode}" id="tom" />
</div>
</body>
</html>