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
<script src="../js/jquery-1.11.1.js"></script>
<script type="text/javascript">
$(function(){
});		
</script>
<body>

<!--header-->
<h1 class="header mb20"><a href="javascript:window.history.back()"><span class="icon back">&nbsp;</span></a>个人资料 <#if user??><a href="preFundHomePage.do" id="home"><em class="icon home">&nbsp;</em></a><#else><a href="userLogin.do" id="login"><em>登录</em></a></#if>  </h1>
<!--header end-->
<div class="dataListB">	
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tbody>
          <tr>
            <th width="26%">真实姓名</th>
            <td width="74%">${personInfo.client_name}</td>
          </tr>
              <tr>
                <th>证件类型</th>
                <td>
                <#if  personInfo.id_kind_gb=="0">
                	身份证
                <#else> 
                	帅哥证
                </#if>
               </td>
              </tr>
              <tr>
                <th>证件号码</th>
                <td>${personInfo.id_no}</td>
              </tr>
              
              <tr>
              <th>手机号</th>
              	<td>
	               <#if personInfo.mobile_tel=="">
	          		<a href="personalCenter_modifyPhone.do">添加手机号</a> <i class="icon arrowRight"></i>
	          		<#elseif personInfo.mobile_tel!="">
	          			${personInfo.mobile_tel}
	          		<#else>
	          		<a href="personalCenter_modifyPhone.do">添加手机号</a> <i class="icon arrowRight"></i>
	          		</#if>
          		</td>
            </tr>
              
        </tbody>
    </table>

    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="mt15">
      <tbody>
          <tr>
            <th width="26%">地址</th>
            <td width="74%">
            <#if personInfo.address=="">
            	<a href="personalCenter_modifyAddress.do">添加地址</a> <i class="icon arrowRight"></i>
            <#elseif personInfo.address!="">
           	 	<a href="personalCenter_modifyAddress.do?address=${personInfo.address}">${personInfo.address}</a> <i class="icon arrowRight"></i>
            <#else>
            	<a href="personalCenter_modifyAddress.do">添加地址</a> <i class="icon arrowRight"></i>
            </#if>
            </td>
          </tr>
              <tr>
                <th>电子邮箱</th>
                <td>
                 <#if personInfo.e_mail=="">
            		<a href="personalCenter_modifymail.do">添加邮箱</a> <i class="icon arrowRight"></i>
            	<#elseif personInfo.e_mail!="">
            		<a href="personalCenter_modifymail.do?e_mail=${personInfo.e_mail}">${personInfo.e_mail}</a> <i class="icon arrowRight"></i>
            	<#else>	
            		<a href="personalCenter_modifymail.do">添加邮箱</a> <i class="icon arrowRight"></i>
            	</#if>
               </td>
              </tr>

              <tr>
                <th>风险等级</th>
                <td><a href="${rc.contextPath}/paperinfoQryAcctNormal.do">
                <#if  personInfo.ofund_risklevel=="1">
                	保守型
                <#elseif personInfo.ofund_risklevel=="2">
                	稳健型
                <#elseif personInfo.ofund_risklevel=="3">
                	激进型
                </#if></a>
               <i class="icon arrowRight"></i></td>
              </tr>
        </tbody>
    </table>
</div>	
<div class="position-footer"><p class="hotline">财富热线：400-068-0058</p></div>
</body>
</html>