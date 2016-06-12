package com.goldfinance.jinGC.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.goldfinance.jinGC.entity.User;
import com.goldfinance.jinGC.mapper.OpenURLMapper;

public class LoginInterceptor implements HandlerInterceptor {
	
	@Autowired
	private OpenURLMapper openURLMapper;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {
		String url = request.getRequestURI();

		//我就是测试一下编码
		String str1 = request.getServletPath();
		String str2 = request.getContextPath();
		System.out.println("url ---->" + url +"\n" + "ServletPath --->" + str1 +"\n" + "ContextPath --->" + str2);
		
		/*ServletPath /queryUserById.do
			ContextPath/jinGCSSM
			url = ContextPath + ServletPath*/
		
		List<String> urlList = openURLMapper.findOpenURLList();
		if(urlList.contains(url)){
			System.out.println("*******公共URL********");
			return true;
		}
		
		System.out.println("******不是公共URL******");
	
		HttpSession session  = request.getSession();
		//String id_no = (String) session.getAttribute("id_no");   //session中有用户证件号码，说明已登录
		User user = (User) session.getAttribute("user");
		if(user!=null){
			return true;
		}    
		//没有登录的话，保存上次请求地址，并定向到登录页，登录完成后返回上次请求地址
		session.setAttribute("lastRequest", url);
		request.getRequestDispatcher("preLogin.do").forward(request, response);
		//request.getRequestDispatcher("login.ftl").forward(request, response);
		return false;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object obj, ModelAndView mv) throws Exception {
		
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object obj, Exception ex) throws Exception {

	}



}
