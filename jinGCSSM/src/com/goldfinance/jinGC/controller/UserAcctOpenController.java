package com.goldfinance.jinGC.controller;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.goldfinance.jinGC.common.ConstantUtil;
import com.goldfinance.jinGC.common.PrintUtil;
import com.goldfinance.jinGC.common.UfxSDKCommon;
import com.goldfinance.jinGC.entity.TextMessage;
import com.goldfinance.jinGC.entity.User;
import com.goldfinance.jinGC.po.Bank;
import com.goldfinance.jinGC.po.CertificateType;
import com.goldfinance.jinGC.po.UserInfo;
import com.goldfinance.jinGC.service.BankService;
import com.goldfinance.jinGC.service.CertificateTypeService;
import com.goldfinance.jinGC.service.FundProductService;
import com.goldfinance.jinGC.service.LogService;
import com.goldfinance.jinGC.service.UserInfoService;
import com.goldfinance.jinGC.util.HSERYCPT;


@Controller
public class UserAcctOpenController extends UfxSDKCommon {
		private static final Logger LOG = LoggerFactory.getLogger(UserAcctOpenController.class);
		private Map<String, Object> request = new LinkedHashMap<String, Object>();
		private Map<String, Object> response = new LinkedHashMap<String, Object>();
	    private String url ;
	    
	    @Autowired
	    private BankService bankService;
	    @Autowired
	    private CertificateTypeService certificateTypeService;
	    @Autowired
	    private UserInfoService userInfoService;
	    @Autowired
	    private LogService logService;
	    @Autowired
	    private FundProductService fundProductService;
	    
	    
	 
	 @RequestMapping("/preUserAcctOpen")  
	 public   ModelAndView preUserAcctOpen() throws Exception{
		 	List<Bank> banks = bankService.findBankList();
		 	List<CertificateType> certificateTypes = certificateTypeService.findCertificateTypeList();
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.addObject("banks", banks);
			modelAndView.addObject("certificateTypes", certificateTypes);
			modelAndView.setViewName("login_openAccount");  //
			return modelAndView;
	 }
	 
	 @RequestMapping("/logout")  
	 public   ModelAndView logout(HttpSession session) throws Exception{
		 session.removeAttribute("user");
		 ModelAndView modelAndView=new ModelAndView();
		 modelAndView.setViewName("login");
		return modelAndView;
	 }
	    
	  /**
	   * 由该请求根据标记为可能会到设置密码页面或绑定银行卡页面
	   * @param user
	   * @return
	   * @throws Exception
	   */
	@RequestMapping("/preTwoUserAcctOpen")
	public ModelAndView preTwoUserAcctOpen(@ModelAttribute("frmLogin") User user) throws Exception{
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("user", user);
		if(user.getBindCard()!=null && !user.getBindCard().equals("")){
			if(user.getBindCard().equals("Y")){  //说明是到绑定银行卡页面		
				List<Bank> banks = bankService.findBankList();
				modelAndView.addObject("banks", banks);
				modelAndView.addObject("bankCode", user.getBankCode());		
				modelAndView.addObject("bankAccount", user.getBankAccount());		
				modelAndView.addObject("mobile_tel", user.getMobile_tel());		
				modelAndView.addObject("agreement", user.getAgreement());					
				modelAndView.setViewName("personalCenter_bindAccount");  
			}
		}else{
			modelAndView.setViewName("login_setPwd");  
		}	
		return modelAndView;
	}
	
	@RequestMapping("/userAcctOpen")
	public ModelAndView userAcctOpen(@ModelAttribute("acctOpen") User user) throws Exception{
			ModelAndView modelAndView = new ModelAndView();
	      	Bank bank = bankService.findBankByBankCode(user.getBankCode());
	        //【user对象里已经有了签约协议号了 sign_contract_no	      	
	      	request.put("trust_way", "2");
	        request.put("cust_type", "1");   //1个人  0机构
	        request.put("client_full_name", user.getUserName());
	        request.put("client_name", user.getUserName());
	        request.put("id_kind_gb", user.getId_kind_gb());
	        request.put("id_no", user.getId_no());
	        String password = user.getPassword();
	        HSERYCPT miTool = new HSERYCPT();
			String miText = miTool.encrypt(password);
	        request.put("password", miText);
	        request.put("mobile_tel", user.getMobile_tel());
	        request.put("bank_no", user.getBankCode());
	        request.put("bank_name", bank.getBankName());
	        request.put("bank_account", user.getBankAccount());
	        request.put("bank_account_name", user.getUserName());
	        request.put("capital_mode", ConstantUtil.CAPITAL_MODE);
	        request.put("ta_no", ConstantUtil.TA_NO);
	        request.put("detail_fund_way", ConstantUtil.DETAIL_FUND_WAY);
	        request.put("fund_card", user.getSign_contract_no());
	        request.put("id_enddate", "99991231");  //证件有效截止日期    1231新加
	        url = "/fundacct_direct_open_acct";
	        String path = "/cwsale/v1" + url;
	        Map<String, Object> response = getUfxSDK().transact(path, request);
	        if(response.get("success_type").equals("") || response.get("success_type") == null || (Integer.parseInt(String.valueOf(response.get("success_type")))!=0)){
			 	List<Bank> banks = bankService.findBankList();
			 	List<CertificateType> certificateTypes = certificateTypeService.findCertificateTypeList();
			 	logService.insertLog(user.getId_no(), "开户", "", "开户失败 ", String.valueOf(response.get("error_info")));
				modelAndView.addObject("banks", banks);
				modelAndView.addObject("certificateTypes", certificateTypes);
				modelAndView.addObject("bankName", bank.getBankName());
				modelAndView.addObject("bankAccount", user.getBankAccount());
				modelAndView.addObject("userName", user.getUserName());
				modelAndView.addObject("certificateName", "身份证");  //证件名称怎么得到
				modelAndView.addObject("id_no", user.getId_no());  
				modelAndView.addObject("mobile_tel", user.getMobile_tel());  
	        	modelAndView.addObject("error_info", response.get("error_info") );
	        	modelAndView.setViewName("login_openAccount"); //login_openAccount.ftl
	        	return modelAndView;
	        }
	        
	        PrintUtil.printResponse(response);
	        logService.insertLog(user.getId_no(), "开户", "", "开户成功", "");
	        UserInfo userInfo = new UserInfo();
	        userInfo.setBankAccount(user.getBankAccount());
	        userInfo.setBankCode(user.getBankCode());
	        userInfo.setCreatedatetime(new Date());
	        userInfo.setId_kind_gb("0");     //暂时是身份证类型
	        userInfo.setId_no(user.getId_no());
	        userInfo.setMobile_tel(user.getMobile_tel());
	        userInfo.setTa_no(ConstantUtil.TA_NO);            //TA编号    暂时是 B0
	        userInfo.setCapital_mode(ConstantUtil.CAPITAL_MODE);
	        userInfo.setTrade_acco(String.valueOf(response.get("trade_acco")));
	        userInfo.setClient_id(String.valueOf(response.get("client_id")));
	        userInfo.setUserName(user.getUserName());
	        userInfoService.insertUserInfo(userInfo);
	        modelAndView.addObject("userName", user.getUserName());
	        modelAndView.addObject("id_no", user.getId_no());
			modelAndView.setViewName("login_openAccount-success");
			return modelAndView;
	}
	
	@RequestMapping("/preLogin")
	public String preLogin(HttpServletRequest request) throws Exception{		
		return "login";
	}

	
	
	@RequestMapping("/userLogin")
	public ModelAndView userLogin(User user, HttpSession session) throws Exception{
		long startA = System.currentTimeMillis();
   		long kobi = System.currentTimeMillis();
		long jodan = 0;
		ModelAndView modelAndView = new ModelAndView();
		String lastUrl = session.getAttribute("lastRequest")+"";
        Map<String,Object> loginMap = new HashMap<String, Object>();
        loginMap.put("trust_way", "2");
      //证件类型：登录页面必传，7月2号与产品方确认;确认结果暂定死，即  身份证
        loginMap.put("id_kind_gb", "0");			 			//应该通过user.getId_kind_gb() 获得
        loginMap.put("id_no", user.getCodeNumber());
		HSERYCPT miTool = new HSERYCPT();
		String miText = miTool.encrypt(user.getPassword());
        loginMap.put("password", miText);
        user.setId_kind_gb(String.valueOf(loginMap.get("id_kind_gb")));
        user.setPassword(miText);
        url = "/login_acct";
        String lgpath = "/cwsale/v1" + url;
        Map<String, Object> lgresponse = getUfxSDK().transact(lgpath, loginMap);
        	 if(lgresponse.get("success_type").equals("") || lgresponse.get("success_type") == null || (Integer.parseInt(String.valueOf(lgresponse.get("success_type")))!=0)){
        		 logService.insertLog(user.getCodeNumber(), "登录", "", "登录失败 ", String.valueOf(lgresponse.get("error_info")));
        		 modelAndView.addObject("error_info", lgresponse.get("error_info") );
	        	modelAndView.addObject("id_no", user.getCodeNumber());
	        	modelAndView.setViewName("login");
	        	return modelAndView;
        }
        PrintUtil.printResponse(lgresponse);
        jodan = System.currentTimeMillis();
        LOG.error("登录207共用了 " + (jodan-kobi) +"毫秒");
        kobi = jodan;
        //删除用户userinfo信息，下面重新查询，重新插入值
        String client_id = String.valueOf(lgresponse.get("client_id"));
        user.setClient_id(client_id);
        int i = userInfoService.deleteUserInfoByClientId(client_id);
        user.setId_no(user.getCodeNumber());
        tonglian(user);
        logService.insertLog(user.getCodeNumber(), "登录", "", "登录成功", "");
		session.setAttribute("id_no", user.getCodeNumber());  //登录成功，session中存用户证件号码
		session.setAttribute("user", user);
		session.setAttribute("info", "登录成功");
        jodan = System.currentTimeMillis();
        LOG.error("登录220共用了 " + (jodan-kobi) +"毫秒");
		List<String> urlList = new ArrayList<String>();
		urlList.add("/jcbindex.do");
		urlList.add("/myAssetPage.do");
		urlList.add("/preFundHomePage.do");
		urlList.add("/accountManager_index.do");
		if(!urlList.contains(lastUrl)){
			lastUrl = "/preFundHomePage.do";
		}
		long endA = System.currentTimeMillis();
		LOG.error("用户登录请求共用了 " + (endA-startA) +"毫秒");
		return new ModelAndView("redirect:"+lastUrl);
	}
	
	@RequestMapping("/protocolOne")
	public ModelAndView protocolOne(User user) throws Exception{
		ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user", user);
		modelAndView.setViewName("login_agreement_ZQ");
		return modelAndView;
	}
	
	@RequestMapping("/protocolTwo")
	public ModelAndView protocolTwo(User user) throws Exception{
		ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user", user);
		modelAndView.setViewName("login_agreement_JGC");
		return modelAndView;
	}
	
	@RequestMapping("/forgetPassword")
	public ModelAndView forgetPassword() throws Exception{
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login_findPwd");
		return modelAndView;
	}
	
	@RequestMapping("/contactUs")
	public ModelAndView contactUs() throws Exception{
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("contactUs");
		return modelAndView;
	}
	
	@RequestMapping("/shareToFriends")
	public ModelAndView shareToFriends() throws Exception{
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("shareToFriends");
		return modelAndView;
	}
	
	/** ajax请求：中文一定要特别处理，否则乱码
	 * 短信验证码步骤一
	 * @param tx
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/mobilePhoneShortMessageCodeFirst")
	public  @ResponseBody TextMessage mobilePhoneShortMessageCodeFirst(TextMessage tx, HttpSession session) throws Exception{
		User u = (User) session.getAttribute("user");
		System.out.println(u);
		String userName = "";
		String id_kind_gb = "";
		String id_no = "";
		if(u!=null){ //已登录，如下值从session中取
			if(u.getUserName()!=null){
				userName = u.getUserName();
			}
			if(u.getId_kind_gb()!=null){
				id_kind_gb = u.getId_kind_gb();
			}
			if(u.getId_no()!=null){
				id_no = u.getId_no();
			}
		}else{  //未登录，如下值从页面传来
			userName =URLDecoder.decode(tx.getUserName(),"UTF-8");
			tx.setUserName(userName);
			id_kind_gb = tx.getId_kind_gb();
			id_no = tx.getId_no();
		}
		
		String bankCode = tx.getBankCode();
		String bankAccount = tx.getBankAccount();
		String mobile_tel = tx.getMobile_tel();
		String flag = tx.getFlag();
		String verifycode = tx.getVerifycode();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String format = sdf.format(new Date());  
        String orderDate = format.substring(0, 8);
        String orderTime = format.substring(8);
        String orderId = format ; //【现
        
        if(flag.equals("1")){
        	tx.setOriginal_serial_no(orderId);  //需要保存第一次的流水号
        }
        
        Map<String, Object> request = new LinkedHashMap<String, Object>();  
        request.put("serial_no", orderId);
        request.put("capital_mode", ConstantUtil.CAPITAL_MODE);
        request.put("order_date",  orderDate);        
        request.put("order_time", orderTime);
        request.put("merchant_name", "JCCF");
        request.put("id_kind_gb", id_kind_gb);
        request.put("id_no", id_no);
        request.put("real_name", userName);
        request.put("mobile_tel", mobile_tel);
        request.put("bank_account_type", "0");
        request.put("bank_account", bankAccount);
        request.put("bank_no", bankCode);
        System.out.println(sdf.format(new Date()));
        System.out.println(tx.getOriginal_serial_no());
        Map<String, Object> requestConfirm = new HashMap<String, Object>();        
        requestConfirm.putAll(request);        
        requestConfirm.put("serial_no", sdf.format(new Date())+ "0517");        
        requestConfirm.put("original_serial_no", tx.getOriginal_serial_no());        
        requestConfirm.put("mobile_code", verifycode);  //原
		
        if(flag.equals("1")){  //flag为1，说明是步骤一，获取验证码
	        Map<String, Object> response = getUfxSDK().transact("/cwpay/v1/pay_sign_contract_sms", request);
	        PrintUtil.printResponse(response);
		}
		if(flag.equals("2")){  //flag为2，说明是步骤二，验证
	        Map<String, Object> response = getUfxSDK().transact("/cwpay/v1/pay_sign_contract_sms", requestConfirm);
	        //步骤二时接口调用会返回 签约协议号 sign_contract_no ，设置给页面上的隐藏域，不断的往后面传，直到传给开户接口调用
	        System.out.println(response.get("protocol_no"));
	        if(response.get("error_info")==null && response.get("status").equals("1" )){ //接口正常调用
	        	tx.setStatus("Y");
				tx.setSign_contract_no(String.valueOf(response.get("protocol_no")));
	        }else{ //接口有问题
	        	tx.setStatus("N");
	        }
	        //接口调用正常，则将返回的签约协议号设置给tx传到页面
			//tx.setSign_contract_no(String.valueOf(response.get("sign_contract_no")));
	        PrintUtil.printResponse(response);
		}
		return tx;
	}
	
	
	public void tonglian(User user) throws Exception{
		List<String> bankAccountList = new ArrayList<String>();
        List<String> trade_acco_list = new ArrayList<String>();  
        List<String> ta_no_list = new ArrayList<String>();
		Map<String, Object> request = new LinkedHashMap<String, Object>();
        request.put("trust_way", "2");
        request.put("request_num", "50");
        request.put("reqry_recordsum_flag", "1");
        request.put("qry_beginrownum", "1");
        request.put("client_id", user.getClient_id());
        List<Map<String, Object>> response = getUfxSDK().query("/cwsale/v1/accobank_qry", request);
        if( "".equals(response.get(0).get("error_info")) && response.get(0).get("success_type").equals("0")){
        	for (Map<String, Object> map : response) {
        		String capital_mode = String.valueOf(map.get("capital_mode"));
        		//【
        		if(ConstantUtil.CAPITAL_MODE.equals(capital_mode)){
        			String ta_no = "";
        			String trade_acco = String.valueOf(map.get("trade_acco"));
        			if(!trade_acco_list.contains(trade_acco)){
        				trade_acco_list.add(trade_acco);
        			}
                    //登录成功后，查询用户的信息，此处需求查手机号
                	Map<String, Object> reqTT = new LinkedHashMap<String, Object>();
                	reqTT.put("trust_way", "2");
                	reqTT.put("trade_acco", trade_acco);
                	reqTT.put("password", user.getPassword()); 
                	Map<String, Object> respTT = getUfxSDK().transact("/cwsale/v1/clientinfo_qry_acct", reqTT);
                	if("0".equals(respTT.get("success_type")) && String.valueOf(respTT.get("mobile_tel")).length()==11 ){
                		user.setMobile_tel(String.valueOf(respTT.get("mobile_tel")));
                	}
        			String ta_acco = String.valueOf(map.get("ta_acco"));
        			String bank_no = String.valueOf(map.get("bank_no"));
        			String bank_account = String.valueOf(map.get("bank_account"));
        			//还未塞值时，或者 塞了同一个银行卡账户时  存信息
        			boolean A = bankAccountList.size()==0;
        			boolean B = bankAccountList.size()!=0 && bankAccountList.contains(bank_account);
        			if(A||B){
        				bankAccountList.add(bank_account);
	        			//【TA账号查询
	        			Map<String, Object> requestB = new LinkedHashMap<String, Object>();
	        			requestB.put("trust_way", "2");
	        			requestB.put("request_num", "1");
	        			requestB.put("reqry_recordsum_flag", "0");
	        			requestB.put("qry_beginrownum", "1");
	        			requestB.put("ta_acco", ta_acco);
	        	        List<Map<String, Object>> responseB = getUfxSDK().query("/cwsale/v1/account_qry", requestB);
	        	        if( "".equals(responseB.get(0).get("error_info")) && responseB.get(0).get("success_type").equals("0")){
	        	        	for (Map<String, Object> mapB : responseB) {
	        	        		ta_no = String.valueOf(mapB.get("ta_no"));
	        	        		ta_no_list.add(ta_no);
	        	        		user.setCust_type(String.valueOf(mapB.get("cust_type")));
	        	        		user.setUserName(String.valueOf(mapB.get("client_name")));
	        	        	}
	        	        }
	    	  	        UserInfo userInfo = new UserInfo();	  	        
	    	  	        userInfo.setBankAccount(bank_account);
	    	  	        userInfo.setBankCode(bank_no);
	    	  	        userInfo.setCreatedatetime(new Date());
	    	  	        userInfo.setId_kind_gb("0");   
	    	  	        userInfo.setId_no(user.getId_no());
	    	  	        userInfo.setMobile_tel(user.getMobile_tel());
	    	  	        userInfo.setTa_no(ta_no);        
	    	  	        userInfo.setClient_id(user.getClient_id());
	    	  	        userInfo.setCapital_mode(ConstantUtil.CAPITAL_MODE); 
	    	  	        userInfo.setTrade_acco(trade_acco);
	    	  	        userInfo.setUserName(user.getUserName());	        
	    	  	        userInfoService.insertUserInfo(userInfo);
	        		}
	        		//】
        			}
        	}
        	//【
        	Double total = Double.parseDouble((String) response.get(0).get("total_count")) ;
			int num = (int) Math.ceil(total / 50);
			if (num > 1) {
				String flag;
				for (int i = 1; i < num; i++) {
					if (i == num - 1) {
						flag = "0"; // 0 不再统计
					} else {
						flag = "1"; // 1 还需统计
					}
					request.clear();
			        request.put("trust_way", "2");
			        request.put("request_num", "50");
			        request.put("reqry_recordsum_flag", flag);
			        request.put("qry_beginrownum", (50 * i + 1) + "");
			        request.put("client_id", user.getClient_id());
			        List<Map<String, Object>> responseC = getUfxSDK().query("/cwsale/v1/accobank_qry", request);
		        	for (Map<String, Object> map : responseC) {
		        		String capital_mode = String.valueOf(map.get("capital_mode"));
		        		//【
		        		if(ConstantUtil.CAPITAL_MODE.equals(capital_mode)){
		        			String ta_no = "";
		        			String trade_acco = String.valueOf(map.get("trade_acco"));
		        			if(!trade_acco_list.contains(trade_acco)){
		            				trade_acco_list.add(trade_acco);
		            			}
		                        //登录成功后，查询用户的信息，此处需求查手机号
		                    	Map<String, Object> reqTT = new LinkedHashMap<String, Object>();
		                    	reqTT.put("trust_way", "2");
		                    	reqTT.put("trade_acco", trade_acco);
		                    	reqTT.put("password", user.getPassword()); 
		                    	Map<String, Object> respTT = getUfxSDK().transact("/cwsale/v1/clientinfo_qry_acct", reqTT);
		                    	if("0".equals(respTT.get("success_type")) && String.valueOf(respTT.get("mobile_tel")).length()==11 ){
		                    		user.setMobile_tel(String.valueOf(respTT.get("mobile_tel")));
		                    	}
		        			String ta_acco = String.valueOf(map.get("ta_acco"));
		        			String bank_no = String.valueOf(map.get("bank_no"));
		        			String bank_account = String.valueOf(map.get("bank_account"));
		        			//还未塞值时，或者 塞了同一个银行卡账户时  存信息
		        			boolean A = bankAccountList.size()==0;
		        			boolean B = bankAccountList.size()!=0 && bankAccountList.contains(bank_account);
		        			if(A||B){
		        				bankAccountList.add(bank_account);
			        			//【TA账号查询
			        			Map<String, Object> requestB = new LinkedHashMap<String, Object>();
			        			requestB.put("trust_way", "2");
			        			requestB.put("request_num", "1");
			        			requestB.put("reqry_recordsum_flag", "0");
			        			requestB.put("qry_beginrownum", "1");
			        			requestB.put("ta_acco", ta_acco);
			        	        List<Map<String, Object>> responseB = getUfxSDK().query("/cwsale/v1/account_qry", requestB);
			        	        if( "".equals(responseB.get(0).get("error_info")) && responseB.get(0).get("success_type").equals("0")){
			        	        	for (Map<String, Object> mapB : responseB) {
			        	        		ta_no = String.valueOf(mapB.get("ta_no"));
			        	        		ta_no_list.add(ta_no);
			    	        	        user.setCust_type(String.valueOf(mapB.get("cust_type")));
			    	        	        user.setUserName(String.valueOf(mapB.get("client_name")));
			        	        	}
			        	        }
			    	  	        UserInfo userInfo = new UserInfo();	  	        
			    	  	        userInfo.setBankAccount(bank_account);
			    	  	        userInfo.setBankCode(bank_no);
			    	  	        userInfo.setCreatedatetime(new Date());
			    	  	        userInfo.setId_kind_gb("0");   
			    	  	        userInfo.setId_no(user.getId_no());
			    	  	        userInfo.setMobile_tel(user.getMobile_tel());
			    	  	        userInfo.setTa_no(ta_no);        
			    	  	        userInfo.setClient_id(user.getClient_id());
			    	  	        userInfo.setCapital_mode(ConstantUtil.CAPITAL_MODE); 
			    	  	        userInfo.setTrade_acco(trade_acco);
			    	  	        userInfo.setUserName(user.getUserName());	        
			    	  	        userInfoService.insertUserInfo(userInfo);
			        		}
			        		//】
		        			}
		        	}
				}
			}
			//】
	        user.setTrade_acco_list(trade_acco_list);
	        user.setTa_no_list(ta_no_list);
        }
	}

}
