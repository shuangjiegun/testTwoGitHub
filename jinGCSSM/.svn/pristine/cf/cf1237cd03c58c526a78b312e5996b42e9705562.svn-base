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
<link rel="stylesheet" href="../css/common.css" type="text/css" />
<script src="../js/jquery-1.10.1.min.js"></script>
<script src="../js/idangerous.swiper.min.js"></script>
</head>
<body>
<!--header-->
<h1 class="header"><a href="javascript:window.history.back()"><span class="icon back">&nbsp;</span></a>基金档案<a href="${rc.contextPath}/preFundHomePage.do"><em class="icon home">&nbsp;</em></a></h1>
<!--header end-->
<div class="dataListA">	
  <h2 class="tc">${mpe.secuCode}  ${mpe.chiName}</h2>
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tbody>
      <tr>
        <td width="50%"  class="tl" style="padding-left:10px;">类型：${foe.fundTypeCodeName}</td>
        <td width="50%"  class="tl" style="padding-left:10px;">风险类型：${foe.riskEvaluationName}</td>
      </tr>
      <tr>
        <td class="tl" style="padding-left:10px;"><#if mpe.chiName?index_of("货币")!=-1>万份收益<#else>单位净值</#if>：
        	             <#if (mpe.unitNV>=0)>
		                    <span class="color-red">
		                  		  ${mpe.unitNV?default(0)?string('0.0000')}
		                   </span>
	                    <#else>
	                   		<span class="color-green">
		                  		  ${mpe.unitNV?default(0)?string('0.0000')}
		                    </span>
	                    </#if>
	      </td>
        <td class="tl" style="padding-left:10px;"><#if mpe.chiName?index_of("货币")!=-1>7日年化<#else>日涨幅</#if>：
                      <#if (mpe.nvDailyGrowthRate>=0)>
		                    <span class="color-red">
		                  		  ${mpe.nvDailyGrowthRate?default(0)?string('0.00')}%
		                   </span>
	                    <#else>
	                   		<span class="color-green">
		                  		  ${mpe.nvDailyGrowthRate?default(0)?string('0.00')}%
		                    </span>
	                    </#if>       
        </td>
      </tr>
      <tr>
        <td class="tl" style="padding-left:10px;"><#if mpe.chiName?index_of("货币")!=-1>14日年化<#else>本月涨幅</#if>：
                       <#if (mpe.rrInSelectedMonth>=0)>
		                    <span class="color-red">
		                  		  ${mpe.rrInSelectedMonth?default(0)?string('0.00')}%
		                   </span>
	                    <#else>
	                   		<span class="color-green">
		                  		  ${mpe.rrInSelectedMonth?default(0)?string('0.00')}%
		                    </span>
	                    </#if> 
		</td>
        <td class="tl" style="padding-left:10px;">近1年收益：
                        <#if (mpe.rrSinceThisYear>=0)>
		                    <span class="color-red">
		                  		  ${mpe.rrSinceThisYear?default(0)?string('0.00')}%
		                   </span>
	                    <#else>
	                   		<span class="color-green">
		                  		  ${mpe.rrSinceThisYear?default(0)?string('0.00')}%
		                    </span>
	                    </#if>  
		</td>
      </tr>
      </tbody>
  </table>
  <p class="color-lightgray tr mt5">净值日期：${mpe.endDate?string("yyyy-MM-dd")}</p>
</div>

<!--tab-->
<div class="tabs tabTradeRecord" style="margin-bottom:-5px;">
  <p> <a href="javascript:void(0)" hidefocus="true" class="active" style="width:33.33%;"><em>收益走势</em></a> <a href="javascript:void(0)" hidefocus="true" style="width:33.33%;"><em>基金介绍</em></a> <a href="javascript:void(0)" hidefocus="true" style="width:33.33%;"><em>费率结构</em></a> </p>
</div>
<!--end tab--> 
<!--swiper container-->
<div class="swiper-container">
  <div class="swiper-wrapper"> 
    <!--收益走势-->
    <div class="swiper-slide">
      <div class="content-slide" id="a1"> 
        <div class="dataListA">	
          <h2>收益走势<a href="${rc.contextPath}/singleFundProductDetailsNavigateMoreEarningsTrend.do?secuCode=${mpe.secuCode}&chiName=${mpe.chiName}" class="readMore">更多</a></h2>
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <thead>
                  <tr>
                    <th width="31%">日期</th>
                    <th width="24%"><#if mpe.chiName?index_of("货币")!=-1>万份收益<#else>单位净值</#if></th>
                    <th width="23%"><#if mpe.chiName?index_of("货币")!=-1>7日年化<#else>累计净值</#if></th>
                    <th width="22%"><#if mpe.chiName?index_of("货币")!=-1>14日年化<#else>日涨跌幅</#if></th>
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
	                    
	                    <#if mpe.chiName?index_of("货币")!=-1>
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
        </div>
      </div>
    </div>
    <!--end 收益走势--> 
    <!--基金介绍-->
    <div class="swiper-slide">
      <div class="content-slide" id="a2"> 
      	<div class="dataListB">	
          <h2>基金概况</h2>
		  <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tbody>
                  <tr>
                    <th width="28%">基金名称</th>
                    <td width="72%">${mpe.chiName}</td>
                  </tr>
                  <tr>
                    <th>基金类型</th>
                    <td>${foe.fundTypeCodeName}</td>
                  </tr>
                  <tr>
                    <th>成立日期</th>
                    <td>${foe.establishmentDate?string("yyyy-MM-dd")}</td>
                  </tr>
                  <tr>
                    <th>最新规模</th>
                    <td>${foe.lastestAssetsValue}元</td>
                  </tr>
                  <tr>
                    <th>最新份额</th>
                    <td>${foe.lastestShares}份</td>
                  </tr>
                  <tr>
                    <th>基金经理</th>
                    <td>${foe.manager}</td>
                  </tr>
                  <tr>
                    <th>基金管理人</th>
                    <td>${foe.investAdvisorCodeName}</td>
                  </tr>
                  <tr>
                    <th>基金托管人</th>
                    <td>${foe.trusteeCodeName}</td>
                  </tr>
                  <tr>
                    <th>投资目标</th>
                    <td>${foe.investTarget}</td>
                  </tr>
                  <tr>
                    <th>投资范围</th>
                    <td>${foe.investField}</td>
                  </tr>
                  <tr>
                    <th>投资策略</th>
                    <td>${foe.investOrientation}</td>
                  </tr>
              </tbody>
          </table>
        </div>
      </div>
    </div>
    <!--end 基金介绍--> 
    <!--费率结构-->
    <div class="swiper-slide">
      <div class="content-slide" id="a3"> 
      	<div class="dataListA">	
          <h2>申购费</h2>
		  <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <thead>
                  <tr>
                    <th width="40%">金额</th>
                    <th width="30%">费率</th>
                    <th width="30%">微信购买优惠</th>
                  </tr>
              </thead>
               <#list allotTariffList as allot>
              <tbody>
                  <tr>
                    <td width="40%">${allot.intervalDescription}</td>
                    <td width="30%">${allot.chargeRateDesciption}</td>
                    <td width="30%">${allot.weChatRate}</td>
                  </tr>                 
              </tbody>
              </#list>
          </table>
        </div>
        <div class="dataListA">	
          <h2>赎回费</h2>
		  <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <thead>
                  <tr>
                    <th width="57%">期限</th>
                    <th width="43%">费率</th>
                  </tr>
              </thead>
              <#list redeemTariffList as redeem>
              <tbody>
                  <tr>
                    <td width="57%">${redeem.intervalDescription}</td>
                    <td width="43%">${redeem.chargeRateDesciption}</td>
                  </tr>                 
              </tbody>
              </#list>
          </table>
        </div>
		<div class="dataListB">	
          <h2>其他</h2>
		  <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tbody>
                  <tr>
                    <th width="50%">管理费</th>
                    <#list manageTariffList as manage>
                    <td width="50%">${manage.chargeRateDesciption}</td>
                   </#list>
                  </tr>
                  <tr>
                    <th>托管费</th>
                    <#list trusteeTariffList as trustee>
                    <td>${trustee.chargeRateDesciption}</td>
                     </#list>
                  </tr>
              </tbody>
          </table>
        </div>
      </div>
    </div>
    <!--end 费率结构-->
  </div>
</div>
<!--end swiper container-->
<div class="footerDiv"></div>
    <p class="position-footer btnGroup">
    	<button type="button" class="rightBorder" style="width:50%;"  id="btn1">认购</button><button type="button" style="width:50%;"  id="btn2">申购</button>
    </p>
      
<input type="hidden" name="allotFlag" value="${allotFlag}" id="allotFlag" />
<input type="hidden" name="subscribeFlag" value="${subscribeFlag}" id="subscribeFlag" />
<script src="../js/tabs3wiper.js"></script>
</body>
<script src="../js/jquery-1.11.1.js"></script>
<script type="text/javascript">
$(function(){
		
		var sFlag = ${subscribeFlag};
		var aFlag = ${allotFlag};
		
		if(sFlag!=1){
			$("#btn1").addClass("btnGray");
		}	
		if(aFlag!=1){
			$("#btn2").addClass("btnGray");
		}
		
		$("#btn1").click(function(){
			if(sFlag==1){
				window.location.href='${rc.contextPath}/preAllotOrSubscribeFirst.do?tradeType=s&secuCode=${mpe.secuCode}';				
			}else{
				alert("该基金目前无法认购")
			}
		})
		
		$("#btn2").click(function(){
			if(aFlag==1){
				window.location.href='${rc.contextPath}/preAllotOrSubscribeFirst.do?tradeType=a&secuCode=${mpe.secuCode}';				
			}else{
				alert("该基金目前无法申购")
			}
		})
})
</script>
</html>