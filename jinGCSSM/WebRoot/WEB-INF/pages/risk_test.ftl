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
	$("#isb").click(function(){		
			var obj = new Array();						 
			   <#list questionNoList as questionNo>
			   	 obj.push(paperinfo["question_${questionNo}"]);
			   </#list>

			  var questioncount=obj.length;
			  var count=0;

			  for(var i=0; i<questioncount;i++){
			  		for(var j=0;j<obj[i].length;j++){
			  			if(obj[i][j].checked==true){
			  				count++
			  			}
			  		}
			  }

			  console.log(count +":"+questioncount)
			  if(count!=obj.length){
			   alert("请完成所有题目后再提交")
			   return false
			  }
			
		$("#paperinfo").submit() 
	})
})

</script>
<body>

<!--header-->
<h1 class="header"><a href="javascript:window.history.back()"><span class="icon back">&nbsp;</span></a>风险测评<a href="${rc.contextPath}/preFundHomePage.do"><em class="icon home">&nbsp;</em></a></h1>
<!--header end-->
<p class=" p15 f11 lh200 t2">提示：根据中国证监会发布的《证券投资基金销售适用指导意见》的相关要求，请您进行投资人风险能力承受评测，以下为《基金投资人风险评测问卷》，请您提供准确、完整的信息，谢谢！</p>
<form name="paperinfo" id="paperinfo" method="post" action="${rc.contextPath}/riskEvaluation.do">
<input type="hidden" name="rowcountStr" value="${total_count}" id="rowcount">
  <ul class="questionList">
  <#list paperInfoList as paperInfo>
  
    <li>
      <h1>${paperInfo.question_content} </h1>
	      <#list paperInfo.paperInfoSectionList as paperInfoSection>
	      		<#if paperInfo.question_no==paperInfoSection.question_no>
			      <p>
			      <input type="radio" name="question_${paperInfoSection.question_no}" id="${paperInfoSection.question_no}:${paperInfoSection.option_no}" value="${paperInfoSection.question_no}:${paperInfoSection.option_no}">			      
			      <label for="${paperInfoSection.question_no}:${paperInfoSection.option_no}">${paperInfoSection.option_content}</label>
			      </p>      
	      		</#if> 
	      </#list>
    </li>
    </#list>
  </ul>

   <input type="button" name="button" id="isb" value="提交"  class="btn btnOne bg-white"/>
</form>
</body>
</html>