package com.goldfinance.jinGC.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.goldfinance.jinGC.common.ConstantUtil;
import com.goldfinance.jinGC.common.UfxSDKCommon;
import com.goldfinance.jinGC.entity.JcbFund;
import com.goldfinance.jinGC.entity.MyAsset;
import com.goldfinance.jinGC.entity.PurchaseInfo;
import com.goldfinance.jinGC.entity.User;
import com.goldfinance.jinGC.po.AllFundProduct;
import com.goldfinance.jinGC.po.Bank;
import com.goldfinance.jinGC.po.CertificateType;
import com.goldfinance.jinGC.po.UserInfo;
import com.goldfinance.jinGC.poOra.FundOverviewExtend;
import com.goldfinance.jinGC.poOra.MFNetValuePerformanceExtend;
import com.goldfinance.jinGC.service.AllFundProductService;
import com.goldfinance.jinGC.service.BankService;
import com.goldfinance.jinGC.service.CertificateTypeService;
import com.goldfinance.jinGC.service.FundProductService;
import com.goldfinance.jinGC.service.JcbFundService;
import com.goldfinance.jinGC.service.LogService;
import com.goldfinance.jinGC.service.UserInfoService;
import com.goldfinance.jinGC.util.HSERYCPT;

@Controller
public class FundPurchaseOperationController extends UfxSDKCommon {
	
	private static final Logger LOG = LoggerFactory.getLogger(FundPurchaseOperationController.class);
	    
	    @Autowired
	    private LogService logService;
	    @Autowired
	    private FundProductService fundProductService;
	    @Autowired
	    private AllFundProductService allFundProductService;
	    @Autowired
	    private UserInfoService userInfoService;
	    @Autowired
	    private BankService bankService;
	    @Autowired
	    private CertificateTypeService certificateTypeService;
	    @Autowired
	    private JcbFundService jcbFundService;
	    @Autowired
	    private JCBmainController jcb;
	    
	    private Map<String, Object> request;
	    private Map<String, Object> responseMap;
	    private List<Map<String, Object>> responseList;
	    
	    /**
	     * 申购or认购 步骤一
	     * @param tradeType  操作类型  a表示申购 allot的简称， s表示认购 subscribe的简称
	     * @param secuCode   基金代码
	     * @param session
	     * @return
	     * @throws Exception
	     */
		@RequestMapping("/preAllotOrSubscribeFirst")
		public ModelAndView preAllotOrSubscribeFirst(String tradeType, String secuCode, HttpSession session) throws Exception{
			ModelAndView modelAndView = new ModelAndView();
			//根据基金代码查询ta_no    接口：基金信息查询
			request =new LinkedHashMap<String, Object>();
			String ta_no ="";
			String fund_name ="";
	        request.put("trust_way",  "2");     //交易委托方式
	        request.put("request_num", "1");                 //请求行数
	        request.put("reqry_recordsum_flag", "0");    //重新统计总记录数标志
	        request.put("qry_beginrownum","1"); //查询起始行号
	        request.put("fund_code",secuCode);                     //基金代码
	        responseList = getUfxSDK().query("/cwsale/v1/fundinfo_qry" , request);
	        //if(responseList.get(0).get("data")==null){
	        if( "".equals(responseList.get(0).get("error_info"))){
	            for(Map<String, Object> info : responseList){
        			ta_no = (String) info.get("ta_no");
        			fund_name = (String) info.get("fund_name");
	            }
	        }
	        
	        String id_kind_gb="";
	        String id_no="";
	        String client_id = "";
	        User user = (User) session.getAttribute("user");  //session中的user只有一些简单信息，其他所需信息得再查
	        if(user!=null){
		        id_kind_gb = user.getId_kind_gb();
		        id_no = user.getId_no();
		        client_id = user.getClient_id();
	        }
	        //根据id_kind_gb 、 id_no 查总交易账户数 distinct(trade_acco) 若=0需增开交易账户	 
	        int num = userInfoService.findDistinctTradeAccoNumber(id_kind_gb, id_no);
	        //####################start##########
	        //此处num=0，并非增的需要增开交易账户，还得查恒生的接口 accobank_qry，查看用户有无通联支付方式的交易账户
	        //可能有多张开通了通联支付方式的卡，目前的处理是取其中的一条，落地时，再查出ta_no，这样就可能一次插多条记录到userinfo表
	        if(num==0){
	        	tonglian(user);
	        }
	        num = userInfoService.findDistinctTradeAccoNumber(id_kind_gb, id_no);
	        //####################end##########
	        if(num==0){  
	        	//需增开交易账户	
				modelAndView.setViewName("personalCenter_bankManage");  
				return modelAndView;	        		        	
	        }
	        //根据id_kind_gb 、 id_no、ta_no查记录，若=0 则需后台完成开户，若!=0 则正常往下写代码
	        List<UserInfo> uiList = new ArrayList<UserInfo>();
	        //查询用户针对某家TA公司的开户信息
	        uiList = userInfoService.findTACompanyOpenAccoInfo(id_kind_gb, id_no, ta_no);
	        if(uiList.size()==0){
	        	//【增开TA账户 也叫 增开基金账户
	        	//从用户开户信息中任取一条，获取用户的基本信息   交易账号也得获取一个
	        	List<UserInfo> uList =  userInfoService.findTACompanyOpenAccoInfo(id_kind_gb, id_no, "");
	        	UserInfo u = new UserInfo();
	        	String trade_acco = "";
			        	if(uList.size()>0){
			        		u = uList.get(0);	   
			        		trade_acco = u.getTrade_acco();   
			        	}
	        	//有些信息是从session中的user里取的，有些信息是u里面的，按需取
	        	request.put("trust_way", ConstantUtil.TRUST_WAY); 	//恒生言：网上委托
	  	        request.put("trade_acco", trade_acco);
	  	        request.put("password", user.getPassword());     
	  	        request.put("ta_no", ta_no);
	  	        request.put("account_type", "A");
	  	        Bank bank = bankService.findBankByBankCode(u.getBankCode());  	        
	  	        Map<String, Object> response = getUfxSDK().transact("/cwsale/v1/fundaccount_add_trade", request);
	  	        
	  	        if(response.get("success_type").equals("") || response.get("success_type") == null || (Integer.parseInt(String.valueOf(response.get("success_type")))!=0)){
	  			 	List<Bank> banks = bankService.findBankList();
	  			 	List<CertificateType> certificateTypes = certificateTypeService.findCertificateTypeList();		 	
	  			 	logService.insertLog(user.getId_no(), "增开基金账户", "", "增开基金账户失败 ", String.valueOf(response.get("error_info")));			 	
	  				modelAndView.addObject("banks", banks);
	  				modelAndView.addObject("certificateTypes", certificateTypes);
	  				String bn = "";
	  				if(bank!=null){
	  					bn = bank.getBankName();
	  				}
	  				modelAndView.addObject("bankName", bn);
	  				modelAndView.addObject("bankAccount", u.getBankAccount());
	  				modelAndView.addObject("userName", u.getUserName());
	  				modelAndView.addObject("certificateName", "身份证");  //证件名称怎么得到
	  				modelAndView.addObject("id_no", user.getId_no());  
	  				modelAndView.addObject("mobile_tel", u.getMobile_tel());  
	  	        	modelAndView.addObject("error_info", response.get("error_info") );	        	
	  	        	modelAndView.setViewName("login_openAccount"); //login_openAccount.ftl
	  	        	return modelAndView;
	  	        }
	  	        
	  	        logService.insertLog(user.getId_no(), "增开基金账户", "", "增开基金账户成功", String.valueOf(response.get("ta_acco")));
	  	        UserInfo userInfo = new UserInfo();	  	        
	  	        userInfo.setBankAccount(u.getBankAccount());
	  	        userInfo.setBankCode(u.getBankCode());
	  	        userInfo.setCreatedatetime(new Date());
	  	        userInfo.setId_kind_gb("0");     //暂时是身份证类型
	  	        userInfo.setId_no(user.getId_no());
	  	        userInfo.setMobile_tel(u.getMobile_tel());
	  	        userInfo.setTa_no(ta_no);        
	  	        userInfo.setClient_id(client_id);
	  	        userInfo.setCapital_mode(ConstantUtil.CAPITAL_MODE);  //【暂时是1，以后需改为M 通联支付
	  	        userInfo.setTrade_acco(trade_acco);
	  	        userInfo.setUserName(u.getUserName());	        
	  	        userInfoService.insertUserInfo(userInfo);
	  	        
	        }
	        
	        //能走到此处，说明针对该TA已开过户了，则正常到申购或认购页面，于是准备页面数据
	        //【如果是金诚宝里的基金，导航到的页面应该是jcbRecharge1.ftl
        	List<JcbFund>	jcbFundList = jcbFundService.selectJcbFund();
        	List<String> secuList = new ArrayList<String>();
        	if(jcbFundList.size()>0){
        		for(int j=0; j<jcbFundList.size();j++){
        			secuList.add(jcbFundList.get(j).getFundcode());
        		}
        	}
        	if(secuList.contains(secuCode)){
    			List<JcbFund> list=jcbFundService.selectJcbFund();
    			modelAndView.addObject("list", list);
    			modelAndView.setViewName("jcbRecharge1");
    			return modelAndView;
        	}
	        
	        //付费方式查询
	        String share_type = ConstantUtil.SHARE_TYPE;  //目前写死，A代表前收费      
			
			FundOverviewExtend foe = new FundOverviewExtend();
			foe = fundTypeAndRisk(secuCode);
			
			List<UserInfo> uiLi = new ArrayList<UserInfo>();
			uiList = userInfoService.findTACompanyOpenAccoInfo(id_kind_gb, id_no, ta_no);
			for(int i=0;i<uiList.size();i++){
				UserInfo u = new UserInfo();
				String str = uiList.get(i).getBankAccount();
				String str1 = str.substring(str.length()-4);
				String starLikeBankAccount = "******"+str1;
				u.setBankAccount(str);
				u.setStarLikeBankAccount(starLikeBankAccount);
				u.setBankAbbrName(uiList.get(i).getBankAbbrName());
				u.setBankCode(uiList.get(i).getBankCode());
				uiLi.add(u);
			}
						
			modelAndView.addObject("uiLi", uiLi); //银行卡部分的数据
	        modelAndView.addObject("secuCode", secuCode); //基金基本信息部分的数据
	        modelAndView.addObject("fund_name", fund_name); //基金基本信息部分的数据
	        modelAndView.addObject("foe", foe); //基金类型部分的数据
	        modelAndView.addObject("share_type", share_type);  //付费方式，上面暂时写死了为A
	        modelAndView.addObject("tradeType", tradeType);  //需要将操作类型如申购or认购传入页面隐藏域，且继续往后传
	        if(tradeType.equals("a")){  //去申购页面
	        	modelAndView.setViewName("myAsset_SG_step1");   //根据tradeType来判断该跳转的页面
	        }
	        if(tradeType.equals("s")){ //去认购页面
	        	modelAndView.setViewName("myAsset_RG_step1");   //根据tradeType来判断该跳转的页面
	        }   
			return modelAndView;
		}
  
	    /**
	     * 去增开交易账户页面
	     * @return
	     * @throws Exception
	     */
		@RequestMapping("/preTradeAccountAdd")
		public ModelAndView preTradeAccountAdd(User user) throws Exception{
			ModelAndView modelAndView = new ModelAndView();
			List<Bank> banks = bankService.findBankList();
			modelAndView.addObject("banks", banks);
	        modelAndView.addObject("user", user);
			modelAndView.setViewName("personalCenter_bindAccount");
			return modelAndView;
		}

		/**
		 * 增开交易账户
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("/tradeAccountAdd")
		public ModelAndView tradeAccountAdd(User user, HttpSession session) throws Exception{
			ModelAndView modelAndView = new ModelAndView();
			String password = "";
			String client_id = "";
			String mobile_tel = user.getMobile_tel();
			String bank_no = user.getBankCode();
			String bank_account = user.getBankAccount();
			request =new LinkedHashMap<String, Object>();
			
			//【TA账号查询  为了获取用户的主交易账号
	        request.put("trust_way", "2");
	        request.put("request_num", "50");
	        request.put("reqry_recordsum_flag", "0");
	        request.put("qry_beginrownum", "1");
	        User u = (User) session.getAttribute("user");  //从session中的User里，取用户的密码，证件类型和证件号码	        
	        if(u!=null){	        	
	        	request.put("id_kind_gb", u.getId_kind_gb());
	        	request.put("id_no", u.getCodeNumber());	
	        	password = u.getPassword();
	        	client_id = u.getClient_id();
	        }	        
	        List<Map<String, Object>> response = getUfxSDK().query("/cwsale/v1/account_qry", request);
	         //未完待续：TA账号查询失败的处理
	        String main_account = "";
	        System.out.println(response);
	        if( !"".equals(response.get(0).get("error_info"))){
	      		 modelAndView.addObject("error_info", response.get(0).get("error_info") );
	      		return new ModelAndView("redirect:/preTwoUserAcctOpen.do");
	           }
	        
	        for(Map<String, Object> userSun : response){
	        	if(String.valueOf(userSun.get("main_account_flag")).equals("1")){
	        		main_account = String.valueOf(userSun.get("trade_acco"));
	        	}
	        }		
			//】

	        request.put("en_entrust_way", "0|1|0|0|1|0|0|");
	        request.put("trust_way", "2");
	        request.put("trade_acco", main_account);   //需要传主交易账户
	        request.put("password", password);	             
	        request.put("mobile_tel", mobile_tel);        
	        request.put("bank_no", bank_no);        
	        Bank bank = bankService.findBankByBankCode(bank_no);
	        String bank_name ="";		
	        if(bank!=null){
	        	bank_name = bank.getBankName();
	        }       
	        request.put("bank_name", bank_name);  			//需要传银行名
	        request.put("bank_account_name", u.getUserName());   //需要传银行账户名
	        request.put("bank_account", bank_account);
	        request.put("capital_mode", ConstantUtil.CAPITAL_MODE);			
	        request.put("fund_card", user.getSign_contract_no());
	        responseMap = getUfxSDK().transact("/cwsale/v1/tradeaccount_add_acct", request);
	        if(String.valueOf(responseMap.get("success_type")).equals("0")){  //成功
	        	String tailNum = bank_account.substring(bank_account.length()-4);
	        	logService.insertLog(u.getCodeNumber(), "绑卡即增开交易账户", "", "绑卡即增开交易账户成功 ", ""); //成功：日志记录
		        int k = userInfoService.deleteUserInfoByClientId(client_id);
		        tonglian(user);
		        
		        //【导航到银行卡列表页面  展示用户的银行卡列表
		        List<UserInfo> userInfoList = userInfoService.findDistinctBankAccount(client_id);  //同一张卡可能用在了不同的TA，所以需要去重
		        if(userInfoList.size()>0){
		        	for(int i=0;i<userInfoList.size();i++){
		        		userInfoList.get(i).setStarLikeBankAccount(userInfoList.get(i).getBankAccount().substring(userInfoList.get(i).getBankAccount().length()-4));
		        	}
		        }
	        		modelAndView.addObject("userInfoList", userInfoList);	 
		        	modelAndView.setViewName("personalCenter_bankList");  
	        }else{  //失败
	        	modelAndView.addObject("error_info", String.valueOf(responseMap.get("error_info")));
	        	logService.insertLog(user.getId_no(), "绑卡即增开交易账户", "", "绑卡即增开交易账户失败 ", String.valueOf(responseMap.get("error_info"))); //失败：日志记录
				List<Bank> banks = bankService.findBankList();
				modelAndView.addObject("banks", banks);
				modelAndView.addObject("bankCode", bank_no);		
				modelAndView.addObject("bankAccount", bank_account);		
				modelAndView.addObject("mobile_tel", mobile_tel);		
				modelAndView.addObject("agreement", user.getAgreement());	           	
	        	modelAndView.setViewName("personalCenter_bindAccount");
	        }	        
			return modelAndView;
		}
		/**
		 * 基金申购or认购步骤二   由此导航到确认页面，即为确认页面准备数据
		 * @param tradeType
		 * @param secuCode
		 * @param session
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("/preAllotOrSubscribeSecond")
		public ModelAndView preAllotOrSubscribeSecond(PurchaseInfo pi) throws Exception{
			ModelAndView modelAndView = new ModelAndView();
			String tradeType = pi.getTradeType();
	        String bankCode = pi.getBankCode();
	        String bankInfo = pi.getBankInfo();
	        String displayedBankInfo = pi.getDisplayedBankInfo();
	        String neededBankInfo = "";
	        String neededDisplayedBankInfo = "";
	        if(bankInfo.indexOf(",")!=-1){   //如果有逗号分隔，说明有多个银行账号，于是需要split，然后拿到用户选择的那个
	        	String[] arr = bankInfo.split(",");
	        	for(int i=0;i<arr.length;i++){ //找到开头是bankCode的那个值，即为用户所选
	        		if(arr[i].substring(0, 3).equals(bankCode)){
	        			neededBankInfo = arr[i];
	        			//将neededBankInfo设置为pi的bankInfo值
	        			pi.setBankInfo(neededBankInfo);
	        		}
	        	}
	        }
	        Bank bank = bankService.findBankByBankCode(bankCode);
	        String bankAbbrName = bank.getBankAbbrName();
	        if(displayedBankInfo.indexOf(",")!=-1){
	        	String[] acc = displayedBankInfo.split(",");
	        	for(int i=0;i<acc.length;i++){
	        		if(acc[i].substring(0, 4).equals(bankAbbrName)){
	        			neededDisplayedBankInfo = acc[i];
	        			//将neededDisplayedBankInfo设置为pi的displayedBankInfo值
	        			pi.setDisplayedBankInfo(neededDisplayedBankInfo);
	        		}
	        	}
	        }
	        
	        modelAndView.addObject("pi", pi);
	        if(tradeType.equals("a")){  //申购确认页面
	        	modelAndView.setViewName("myAsset_SG_step2");   //根据tradeType来判断该跳转的页面
	        }
	        if(tradeType.equals("s")){ //认购确认页面
	        	modelAndView.setViewName("myAsset_RG_step2");   //根据tradeType来判断该跳转的页面
	        }   
			return modelAndView;
		}
		
		
		/**
		 * 真正调用 申购or认购 接口，导航到成功or失败页面
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("realAllotOrSubscribe")
		public ModelAndView realAllotOrSubscribe(PurchaseInfo pi, HttpSession session) throws Exception{
			ModelAndView modelAndView = new ModelAndView();
			User user = (User) session.getAttribute("user");
			String id_no = "";
			if(user!=null){
				id_no = user.getId_no();
			}
			String password = pi.getPassword();
			String fund_code = pi.getSecuCode();
			String str = pi.getBankInfo();
			int index = str.indexOf("|");
			String bank_no = str.substring(0, index);
			String bank_account = str.substring(index+1);
			//【根据银行编号、银行卡号、身份证号查询交易账号，可能查出多条记录(主要是表里有其他多对多关系)，但银行卡号与交易账号是一对一的关系，所以任取一条即可
			List<UserInfo> uList = userInfoService.findUserInfo(bank_no, bank_account, id_no);
			String trade_acco = "";
			if(uList.size()>0){
				trade_acco = uList.get(0).getTrade_acco();
			}
			String share_type = ConstantUtil.SHARE_TYPE;
			String balance = pi.getMoney();
			String money_type = ConstantUtil.MONEY_TYPE;
			String capital_mode = ConstantUtil.CAPITAL_MODE;

			request = new LinkedHashMap<String, Object>(); 
	        request.put("trust_way", "2");
	        request.put("trade_acco", trade_acco);
	        HSERYCPT miTool = new HSERYCPT();
			String miText = miTool.encrypt(password);
	        request.put("password", miText);
	        request.put("fund_code", fund_code);
	        request.put("bank_no", bank_no);
	        request.put("bank_account", bank_account);
	        request.put("share_type", share_type);
	        request.put("balance", balance);
	        request.put("money_type", money_type);
	        request.put("capital_mode", capital_mode);
	        request.put("detail_fund_way", ConstantUtil.DETAIL_FUND_WAY);
			
	        Map<String, Object> response = new HashMap<String, Object>();
	        String tradeType = pi.getTradeType();
	        if(tradeType.equals("a")){
	        	response = getUfxSDK().transact("/cwsale/v1/allot_trade", request);
		 		if(String.valueOf(response.get("success_type")).equals("0")){  //成功				
			        	logService.insertLog(user.getId_no(), "基金申购", balance, "成功", "申请编号 " + String.valueOf(response.get("allot_no"))+ ";" + "申请日期 " + String.valueOf(response.get("apply_date"))+ ";" + "申请时间 " + String.valueOf(response.get("apply_time"))); //成功：日志记录	        		        		        	        	
			        	modelAndView.setViewName("myAsset_SG_step3success");	        	
			      }else{  //失败
			        	modelAndView.addObject("error_info", String.valueOf(response.get("error_info")));
			        	logService.insertLog(user.getId_no(), "基金申购", balance, "失败", String.valueOf(response.get("error_info"))); //失败：日志记录			     	
			        	modelAndView.setViewName("myAsset_SG_step2");
			     }
	        }
	        
	        if(tradeType.equals("s")){
	        	 	response = getUfxSDK().transact("/cwsale/v1/subscribe_trade", request);
			 		if(String.valueOf(response.get("success_type")).equals("0")){  //成功				
			        	logService.insertLog(user.getId_no(), "基金认购", balance, "成功", "申请编号 " + String.valueOf(response.get("allot_no"))+ ";" + "申请日期 " + String.valueOf(response.get("apply_date"))+ ";" + "申请时间 " + String.valueOf(response.get("apply_time"))); //成功：日志记录	        		        		        	        	
			        	modelAndView.setViewName("myAsset_RG_step3success");	        	
			      }else{  //失败
			        	modelAndView.addObject("error_info", String.valueOf(response.get("error_info")));
			        	logService.insertLog(user.getId_no(), "基金认购", balance, "失败", String.valueOf(response.get("error_info"))); //失败：日志记录			     	
			        	modelAndView.setViewName("myAsset_RG_step2");
			     }
	        }
	        
	        //申购认购成功之后，依据申请编号查询交易申请查询接口，查询扣款状态
	        if(!response.isEmpty() && String.valueOf(response.get("success_type")).equals("0")){  //成功
	        	String allot_no = String.valueOf(response.get("allot_no"));
	        	request.clear();
	  	        request.put("trust_way", "2");
		        request.put("reqry_recordsum_flag", "0");
		        request.put("qry_beginrownum", "1");
		        request.put("allot_no", allot_no);
	     	    List<Map<String, Object>> responseYY = getUfxSDK().query("/cwsale/v1/trade_apply_qry", request);
	    		if ( "".equals(responseYY.get(0).get("error_info")) && responseYY.get(0).get("success_type").equals("0")) {
	    			pi.setDeduct_status(String.valueOf(responseYY.get(0).get("deduct_status")));
	    		}else{
	    			pi.setDeduct_status("9");  //不成功时随便设了一个值
	    		}
	        }
	        modelAndView.addObject("pi", pi);		        
			return modelAndView;
		}

		/**
		 * 我的资产
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("/myAssetPage")
		public ModelAndView myAssetPage(HttpServletRequest req, HttpSession session) throws Exception{
			long startA = System.currentTimeMillis();
			long kobi = System.currentTimeMillis();
			long jodan = 0;
			ModelAndView modelAndView = new ModelAndView();
			//TA账号查询接口，查询当前用户的交易账号
			request =new LinkedHashMap<String, Object>();
	        request.put("trust_way", "2");
	        request.put("request_num", "50");
	        request.put("reqry_recordsum_flag", "0");
	        request.put("qry_beginrownum", "1");
	        User u = (User) session.getAttribute("user");  //从session中的User里，取用户的证件类型和证件号码，用户信息也要为页面准备
	        String userName = "";
	        if(u!=null){	        	
	        	request.put("id_kind_gb", u.getId_kind_gb());
	        	request.put("id_no", u.getCodeNumber());	
	        	userName = u.getUserName();
	        }	        
	        List<Map<String, Object>> response = getUfxSDK().query("/cwsale/v1/account_qry", request);
	        List<String> tradeAccoList = new ArrayList<String>();
	        for(Map<String, Object> leo : response){
	        	tradeAccoList.add(String.valueOf(leo.get("trade_acco")));
	        }	
	        List<String> uniqueTradeAccoList = new ArrayList<String>();
	        for(int i=0;i<tradeAccoList.size();i++){
	        	if(!uniqueTradeAccoList.contains(tradeAccoList.get(i))){
	        		uniqueTradeAccoList.add(tradeAccoList.get(i));
	        	}
	        }
	        jodan = System.currentTimeMillis();
	        LOG.error("走到514共用了 " + (jodan-kobi) +"毫秒");
	        kobi = jodan;
			//【循环查询每个不重复交易账号的基金份额(份额查询接口)，但还得再细分  货币基金和非货币基金
	        Map<String, Object> requestTwo = new LinkedHashMap<String, Object>();
	        List<MyAsset> myAssetList = new ArrayList<MyAsset>();
	        for(int k=0; k<uniqueTradeAccoList.size(); k++){
	        	String trade_acco = uniqueTradeAccoList.get(k);
	        	String requestnum = "50";   //请求行数维护  request_num
	        	 int num=0;
		        requestTwo.put("request_num", "50");
		        requestTwo.put("reqry_recordsum_flag", "1");
		        requestTwo.put("qry_beginrownum", "1");
		        requestTwo.put("trade_acco", trade_acco);
		        
		        List<Map<String, Object>> responseTwo = getUfxSDK().query("/cwsale/v1/share_qry", requestTwo);		        
		        //if(responseTwo.get(0).get("data")==null){    //能进这里说明有内容并正常返回
		        if("".equals(responseTwo.get(0).get("error_info"))){    //能进这里说明有内容并正常返回	
		        	for(Map<String, Object> info : responseTwo){ //页面只展示有可用份额的基金
			        	System.out.println(info);
			        	String enableSharesStr = String.valueOf(info.get("enable_shares"));
			        	double enableShares = 0.0;
			        	if(enableSharesStr!=null && !("").equals(enableSharesStr)){
			        		enableShares = Double.parseDouble(enableSharesStr);
			        	}
			        	if(enableShares!=0.0){
				        	MyAsset ma = new MyAsset();
				        	ma.setTradeAcco(String.valueOf(info.get("trade_acco")));
				        	ma.setTaAcco(String.valueOf(info.get("ta_acco")));
				        	ma.setFundCode(String.valueOf(info.get("fund_code")));
				        	ma.setCapitalMode(String.valueOf(info.get("capital_mode")));
				        	ma.setBankNo(String.valueOf(info.get("bank_no")));
				        	ma.setBankAccount(String.valueOf(info.get("bank_account")));
				        	ma.setTodayIncome(String.valueOf(info.get("today_income")));
				        	ma.setWorthValue(String.valueOf(info.get("worth_value")));   //市值
				        	ma.setAccumIncome(String.valueOf(info.get("accum_income")));  //累计收益
				        	ma.setEnableShares(String.valueOf(info.get("enable_shares")));  
     	       				ma.setUnpaid_income(String.valueOf(info.get("unpaid_income")));  
				        	myAssetList.add(ma);
			        	}
			        	Double i = Double.parseDouble(String.valueOf(info.get("total_count")) );
			        	Double j = Double.parseDouble(requestnum);
			        	num =  (int)Math.ceil( i / j) ;	
			        }
		        } 
		        if(num>1){  //说明需要继续查   如num=3  一共查3次，上面已经查过一次了，还需查两次
		        	for(int i=1;i<num;i++){   
		        		requestTwo.put("request_num",   requestnum);
	         	        String flag = "";
	         	        if(i==num-1){
	         	        	flag = "0";  //0 不再统计
	         	        }else{
	         	        	flag = "1";  //1 还需统计
	         	        }	        	        
	         	       requestTwo.put("reqry_recordsum_flag",  flag);   //重新统计总记录数标志	        	             
	         	       String start =  Integer.parseInt(requestnum)*i + 1 +"";   
	         	      requestTwo.put("qry_beginrownum", start);           //查询起始行号	
	         	      requestTwo.put("trade_acco", trade_acco);
	         	      List<Map<String, Object>> responseYY = getUfxSDK().query("/cwsale/v1/share_qry", request);
		         	  //if(responseYY.get(0).get("data")==null){    //能进这里说明有内容并正常返回
		         	     if( "".equals(responseYY.get(0).get("error_info")) ){    //能进这里说明有内容并正常返回
		         	       		for(Map<String, Object> info : responseYY){  //页面只展示有可用份额的基金
		        			        	String enableSharesStr = String.valueOf(info.get("enable_shares"));
		        			        	double enableShares = 0.0;
		        			        	if(enableSharesStr!=null && ("").equals(enableSharesStr)){
		        			        		enableShares = Double.parseDouble(enableSharesStr);
		        			        	}
		        			        	if(enableShares!=0.0){
		         	       				MyAsset ma = new MyAsset();
		         	       				ma.setTradeAcco(String.valueOf(info.get("trade_acco")));
		         	       				ma.setTaAcco(String.valueOf(info.get("ta_acco")));
		         	       				ma.setFundCode(String.valueOf(info.get("fund_code")));
		         	       				ma.setCapitalMode(String.valueOf(info.get("capital_mode")));
		         	       				ma.setBankNo(String.valueOf(info.get("bank_no")));
		         	       				ma.setBankAccount(String.valueOf(info.get("bank_account")));
		         	       				ma.setTodayIncome(String.valueOf(info.get("today_income")));
		         	       				ma.setWorthValue(String.valueOf(info.get("worth_value")));
		         	       				ma.setAccumIncome(String.valueOf(info.get("accum_income")));  //累计收益
		    				        	ma.setEnableShares(String.valueOf(info.get("enable_shares")));  
		         	       				ma.setUnpaid_income(String.valueOf(info.get("unpaid_income"))); 
		         	       				myAssetList.add(ma);
		         	       			}
				         	      }
		         	     }
		        	}
		        }  
	        }    //】myAssetList这里面就有了最重要的信息了
	        jodan = System.currentTimeMillis();
	        LOG.error("走到601共用了 " + (jodan-kobi) +"毫秒");
	        kobi = jodan;
	        //myAssetListExcepJCB  不包含金诚宝里的其他所有基金   持仓基金 需要  只针对有可用份额的才展示
	        List<MyAsset> myAssetListExcepJCB = new ArrayList<MyAsset>();
        	List<JcbFund>	jcbFundList = jcbFundService.selectJcbFund();
        	List<String> secuList = new ArrayList<String>();
        	if(jcbFundList.size()>0){
        		for(int j=0; j<jcbFundList.size();j++){
        			secuList.add(jcbFundList.get(j).getFundcode());
        		}
        	}
	        //获取基金代码，查净值，日涨跌幅
	        for(int i=0; i<myAssetList.size();i++){
	        	MyAsset ma = myAssetList.get(i);
	        	String secuCode = ma.getFundCode();	   
	        	AllFundProduct allFundProduct = allFundProductService.findFundByfundCode(secuCode);
	        	String fundName = allFundProduct.getFund_name();
	        	ma.setSecuAbbr(fundName);   //聚源返回的证券简称不太好用，改为查恒生的基金名称，设置到证券简称里
	        	List<String> ids = new ArrayList<String>();
	        	ids.add(secuCode);
	        	List<MFNetValuePerformanceExtend> mvpList= fundProductService.findFundProductPageInfo(ids);
	        	if(mvpList.size()>0){
	        		Float unitNV = mvpList.get(0).getUnitNV();  //净值
	        		Float nvDailyGrowthRate = mvpList.get(0).getNvDailyGrowthRate();  //日涨跌幅
	        		ma.setUnitNV(unitNV);
	        		ma.setNvDailyGrowthRate(nvDailyGrowthRate);
	        	}
	        	if(!secuList.contains(secuCode)){   
	        		myAssetListExcepJCB.add(ma);
	        	}
	        }
	        
	        //myAssetList是所有的  算总市值，总累计盈亏， 总昨日盈亏  需要
	        jodan = System.currentTimeMillis();
	        LOG.error("走到635共用了 " + (jodan-kobi) +"毫秒");
	        kobi = jodan;
	        float all_total_all_new = 0.0f; //总资产
	        String clientid = u.getClient_id();
	       List<MyAsset> shareDetailNoBiList = new ArrayList<MyAsset>();   //筛选出非货币基金，放入shareDetailNoBiList
	       List<MyAsset> shareDetailHuoBiList = new ArrayList<MyAsset>();   //筛选出货币基金，放入shareDetailHuoBiList
	       if(myAssetList.size()>0){
	    	   Map<String, Object> requestB = new LinkedHashMap<String, Object>();
    		   requestB.put("trust_way",   "2");     //交易委托方式
    		   requestB.put("request_num",   "1");        //请求行数
    		   requestB.put("reqry_recordsum_flag",  "0");   //重新统计总记录数标志
    		   requestB.put("qry_beginrownum","1");           //查询起始行号
	    	   for(int i=0;i<myAssetList.size();i++){ //此处只想查基金类型：货币or非货币，所以简单一点
	    		   requestB.put("fund_code",myAssetList.get(i).getFundCode());   
	    		   List<Map<String, Object>> responseC = getUfxSDK().query("/cwsale/v1/fundinfo_qry" , requestB);
	    		   		if( "".equals(responseC.get(0).get("error_info"))){    //能进这里说明有内容并正常返回
	    		   		String 	ofundType = String.valueOf(responseC.get(0).get("ofund_type")); //只取第一条记录就可以了
	    		   			if(!ofundType.equals("2")){ //如果是非货币基金
	    		   				shareDetailNoBiList.add(myAssetList.get(i));
	    		   			}else{//否则就是货币基金，但得去掉金诚宝里的货币基金，0107新需求：不去掉金诚宝里的基金;0118 还是要去掉金诚宝里的基金
	    		   				List<JcbFund> list=jcbFundService.selectJcbFund();
	    		   				List<String> jcbList = new ArrayList<String>();
	    		   				if(list.size()>0){
	    		   					int len = list.size();
	    		   					for(int j=0;j<len;j++){
	    		   						jcbList.add(list.get(j).getFundcode());
	    		   					}
	    		   				}
	    		   				if(!jcbList.contains(myAssetList.get(i).getFundCode())){ //筛选出非金诚宝里的货币基金
	    		   					shareDetailHuoBiList.add(myAssetList.get(i));
	    		   				}
	    		   			}
	    		   		}
	    		   }
	       }
	       if(shareDetailNoBiList.size()>0){  //非货币基金	    	   
	    	   for(int i=0;i<shareDetailNoBiList.size();i++){
	    		   float keYongShare = Float.parseFloat(shareDetailNoBiList.get(i).getEnableShares()); //可用份额
	    		   float yesterdayNav = 0.0f; //前一天净值
	    		   Map<String, Object> requestD = new LinkedHashMap<String, Object>(); //前一天净值数据
	    		   Calendar   calA   =   Calendar.getInstance();
	    		   int navDay = -1;  //前一天
	    		   int linshi = -1;
	    		   String yesterday = "";
	    		   for(int k=0;k<20;k++){ //循环查询恒生接口，获取前一天的净值（节假日没有净值）
	    			   requestD.clear();
		    		   yesterday = new SimpleDateFormat( "yyyyMMdd").format(calA.getTime());
		    		   calA.add(Calendar.DATE,   navDay);
		    		   requestD.put("trust_way",   "2");     //交易委托方式
		    		   requestD.put("request_num", "1");
		    		   requestD.put("reqry_recordsum_flag", "0");
		    		   requestD.put("qry_beginrownum", "1");
		    		   requestD.put("fund_code", shareDetailNoBiList.get(i).getFundCode());
		    		   requestD.put("nav_begin_date", yesterday);
		    		   requestD.put("nav_end_date", yesterday);
		    		   List<Map<String, Object>> responseD = getUfxSDK().query("/cwsale/v1/oldhq_qry", requestD);
			    		   if( "无记录".equals(responseD.get(0).get("error_info"))){    //如果是周末，可能没数据，所以需要重复查询
			    			   linshi--;
			    		   }else if("".equals(responseD.get(0).get("error_info")) && !"".equals(responseD.get(0).get("nav"))){
			    			   String 	yesterdayNavStr = String.valueOf(responseD.get(0).get("nav")); //只取第一条记录就可以了
		    				   yesterdayNav = Float.parseFloat(yesterdayNavStr);
		    				   break;
			    		   }
	    		   }
	    		   all_total_all_new =  all_total_all_new + yesterdayNav*keYongShare;
	    	   }
	       } 
	       if(shareDetailHuoBiList.size()>0){  //货币基金
	    	   int length = shareDetailHuoBiList.size();
	    	   for(int i=0;i<length;i++){
	    		   String 	enable_shares_str = "";
	    		   String 	unpaid_income_str ="";
	    		   enable_shares_str = shareDetailHuoBiList.get(i).getEnableShares();
	    		   unpaid_income_str = shareDetailHuoBiList.get(i).getUnpaid_income();
    			   if(!"".equals(enable_shares_str) && enable_shares_str!=null){
    				   float enable_shares = Float.parseFloat(enable_shares_str);
    				   all_total_all_new =  all_total_all_new+ enable_shares;
    			   }
    			   if(!"".equals(unpaid_income_str) && unpaid_income_str!=null){
    				   float unpaid_income = Float.parseFloat(unpaid_income_str);
    				   all_total_all_new =  all_total_all_new+ unpaid_income;
    			   }
	    	   }
	       }
	       
	        //昨日收益  持仓收益 计算完毕】】
	        jodan = System.currentTimeMillis();
	        LOG.error("走到722共用了 " + (jodan-kobi) +"毫秒");
	        kobi = jodan;
	        //ModelAndView mav = jcb.jcbIndex(req,session);
	        ModelAndView mav = jcb.jcbMethod(req);
	        jodan = System.currentTimeMillis();
	        LOG.error("走到727共用了 " + (jodan-kobi) +"毫秒");
	        kobi = jodan;
	        float all_total_new = 0.0f;
	        float no_pay_new = 0.0f;
	        if(mav!=null){
	        	all_total_new = Float.parseFloat(mav.getModel().get("all_total_new")+"");
	        	no_pay_new = Float.parseFloat(mav.getModel().get("no_pay_new")+"");
	        }

	        all_total_all_new = all_total_all_new+all_total_new;  //我的资产中的总资产应该包括金诚宝里的资产
	        modelAndView.addObject("all_total_new", all_total_new);   //金诚宝总资产
	        modelAndView.addObject("no_pay_new", no_pay_new);  //金诚宝未付收益
	        modelAndView.addObject("all_total_all_new", all_total_all_new);  //总资产
	        //modelAndView.addObject("yesterdayBenefits", yesterdayBenefits);  //昨日收益
	       // modelAndView.addObject("positionBenefits", positionBenefits);  //持仓收益
	        modelAndView.addObject("userName", userName);
	        //modelAndView.addObject("accumIncomeSum", accumIncomeSum);
	        //modelAndView.addObject("todayIncomeSum", todayIncomeSum);
	        //modelAndView.addObject("worthValueSum", worthValueSum);
	        modelAndView.addObject("myAssetListExcepJCB", myAssetListExcepJCB);
			modelAndView.setViewName("myAsset_index");
			long endA = System.currentTimeMillis();
			LOG.error("我的资产请求共用了 " + (endA-startA) +"毫秒");
			return modelAndView;
		}

		/**
		 * 去基金赎回页面 步骤一
		 * @param info
		 * @param session
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("/preRedeemTradeFirst")
		public ModelAndView preRedeemTradeFirst(String secuCode, HttpSession session) throws Exception{
			ModelAndView modelAndView = new ModelAndView();
	        AllFundProduct allFundProduct = allFundProductService.findFundByfundCode(secuCode);
        	String fund_name = allFundProduct.getFund_name();
			System.out.println(secuCode);
			User user = (User) session.getAttribute("user");
	        String client_id = user.getClient_id();  //用户编号
	        //调份额查询接口，查询用户当前要赎回基金的可用份额，以及当时购买它的银行卡信息
	        Float enable_shares = 0.0f;   //可用份额
	        String bank_no = "";  			//银行代码
	        String bank_account = "";   //银行账号
	        String trade_acco = "";
	  	  	request = new LinkedHashMap<String, Object>();
	        request.put("request_num", "50");
	        request.put("reqry_recordsum_flag", "0");
	        request.put("qry_beginrownum", "1");
	        request.put("fund_code", secuCode);
	        request.put("client_id", client_id);
	        responseList = getUfxSDK().query("/cwsale/v1/share_qry", request);
	       // if(responseList.get(0).get("data")==null){ //能进来说明有内容正常返回
	        if( "".equals(responseList.get(0).get("error_info"))){ //能进来说明有内容正常返回
	        	Map<String, Object> resp = responseList.get(0); //因为是按用户的编号和基金代码查的，所以只有一条数据
	        	if(String.valueOf(resp.get("enable_shares"))!=null &&  !String.valueOf(resp.get("enable_shares")).equals("")){
	        		enable_shares = Float.parseFloat(String.valueOf(resp.get("enable_shares")));	        		
	        	}
	        	bank_no = String.valueOf(resp.get("bank_no"));
	            bank_account = String.valueOf(resp.get("bank_account"));	
	            trade_acco = String.valueOf(resp.get("trade_acco"));	
	        }
	        Bank bank = bankService.findBankByBankCode(bank_no);
			UserInfo u = new UserInfo();
			String str = bank_account;
			String str1 = str.substring(str.length()-4);
			String starLikeBankAccount = "******"+str1;
			u.setBankCode(bank_no);  //银行代码
			u.setBankAccount(str);      //银行账号
			u.setTrade_acco(trade_acco);
			u.setStarLikeBankAccount(starLikeBankAccount);  //星形银行账户    ****1942
			u.setBankAbbrName(bank.getBankAbbrName());   //银行中文名简称
			modelAndView.addObject("secuCode", secuCode);
			modelAndView.addObject("fund_name", fund_name);
			modelAndView.addObject("enable_shares", enable_shares);
	        modelAndView.addObject("u", u);
			modelAndView.setViewName("myAsset_SH_step1_special");
			return modelAndView;
		}

		/**
		 * 赎回步骤二
		 * @param pi
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("/preRedeemTradeSecond")
		public ModelAndView preRedeemTradeSecond(PurchaseInfo pi) throws Exception{
			ModelAndView modelAndView = new ModelAndView();	        
	        modelAndView.addObject("pi", pi);
			modelAndView.setViewName("myAsset_SH_step2");
			return modelAndView;
		}

		/**
		 * 真正调用赎回接口
		 * @param pi
		 * @param session
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("/realRedeemTrade")
		public ModelAndView realRedeemTrade(PurchaseInfo pi, HttpSession session) throws Exception{
			ModelAndView modelAndView = new ModelAndView();
			String trade_acco = pi.getTrade_acco();
			String password = pi.getPassword();
			String fund_code = pi.getSecuCode();
			String shares = pi.getMoney();
			User user = (User) session.getAttribute("user");
	        request.put("trust_way", ConstantUtil.TRUST_WAY);
	        request.put("trade_acco", trade_acco);
	        HSERYCPT miTool = new HSERYCPT();
			String miText = miTool.encrypt(password);
	        request.put("password", miText);
	        request.put("fund_code", fund_code);
	        request.put("share_type", ConstantUtil.SHARE_TYPE);
	        request.put("shares", shares);
	        request.put("fund_exceed_flag", ConstantUtil.FUND_EXCEED_FLAG);
	        Map<String, Object> response = getUfxSDK().transact("/cwsale/v1/redeem_trade", request);
	 		if(String.valueOf(response.get("success_type")).equals("0")){  //成功				
	        	logService.insertLog(user.getId_no(), "基金赎回", shares, "成功", "申请编号 " + String.valueOf(response.get("allot_no"))+ ";" + "申请日期 " + String.valueOf(response.get("apply_date"))); //成功：日志记录	        		        		        	        	
	        	modelAndView.setViewName("myAsset_SH_step3success");	        	
	      }else{  //失败
	        	modelAndView.addObject("error_info", String.valueOf(response.get("error_info")));
	        	logService.insertLog(user.getId_no(), "基金赎回", shares, "失败", String.valueOf(response.get("error_info"))); //失败：日志记录			     	
	        	modelAndView.setViewName("myAsset_SH_step2");
	     }
	        modelAndView.addObject("pi", pi);
			return modelAndView;
		}
		
		/**
		 * 账户管理中的银行卡管理，点击时当用户已绑过卡，导航到银行卡列表页面，否则导航到绑卡页面
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("/personalCenterBankCardManage")
		public ModelAndView personalCenterBankCardManage(HttpSession session) throws Exception{
			ModelAndView modelAndView = new ModelAndView();
			User user = (User) session.getAttribute("user");
			String client_id = "";
			String id_kind_gb = "";
			String id_no = "";
			if(user!=null){
				id_kind_gb = user.getId_kind_gb();
				id_no = user.getId_no();
				client_id = user.getClient_id();
			}
	        
	        int num = userInfoService.findDistinctTradeAccoNumber(id_kind_gb, id_no);
	        //####################start##########
	        //此处num=0，并非增的需要增开交易账户，还得查恒生的接口 accobank_qry，查看用户有无通联支付方式的交易账户
	        //可能有多张开通了通联支付方式的卡，目前的处理是取其中的一条，落地时，再查出ta_no，这样就可能一次插多条记录到userinfo表
	        if(num==0){
	        	tonglian(user);
	        }
	        num = userInfoService.findDistinctTradeAccoNumber(id_kind_gb, id_no);
	        //####################end##########
	        if(num==0){  //【需增开交易账户，即绑卡
				modelAndView.setViewName("personalCenter_bankManage");  
				return modelAndView;	        		        	
	        }else{  //展示用户的银行卡列表
	        List<UserInfo> userInfoList = userInfoService.findDistinctBankAccount(client_id);  //同一张卡可能用在了不同的TA，所以需要去重
	        if(userInfoList.size()>0){
	        	for(int i=0;i<userInfoList.size();i++){
	        		userInfoList.get(i).setStarLikeBankAccount(userInfoList.get(i).getBankAccount().substring(userInfoList.get(i).getBankAccount().length()-4));
	        	}
	        }
        		modelAndView.addObject("userInfoList", userInfoList);	 
	        	modelAndView.setViewName("personalCenter_bankList");  
				return modelAndView;		        
	        }
		}
		
		
		
		/**
		 * 根据基金代码查询基金类型和风险等级
		 * @param secuCode
		 * @return
		 * @throws Exception
		 */
		public FundOverviewExtend fundTypeAndRisk(String secuCode) throws Exception{
			FundOverviewExtend foe = new FundOverviewExtend();

			//基金类型和风险等级从恒生获取，设置到FundOverviewExtend中
			String ofund_type = "";
			String ofund_risklevel = "";
			String riskEvaluationName = "";
			String fundTypeCodeName = "";
			    request =new LinkedHashMap<String, Object>();
		        request.put("request_num", "1");                 //请求行数
		        request.put("reqry_recordsum_flag", "0");    //重新统计总记录数标志
		        request.put("qry_beginrownum","1"); //查询起始行号
		        request.put("fund_code", secuCode);                     //基金代码
		        List<Map<String, Object>> responseY = getUfxSDK().query("/cwsale/v1/newhq_qry" , request);
		        if( "".equals(responseY.get(0).get("error_info"))){
		        	ofund_type = (String) responseY.get(0).get("ofund_type");
		        	ofund_risklevel = (String) responseY.get(0).get("ofund_risklevel");
		        	if(ofund_risklevel.equals("0")){
		        		riskEvaluationName = "低风险";
		        	}else if(ofund_risklevel.equals("1")){
		        		riskEvaluationName = "中风险";
		        	}else if(ofund_risklevel.equals("2")){
		        		riskEvaluationName = "高风险";
		        	}else{
		        		riskEvaluationName = "";
		        	}
		        	
		        	if(ofund_type.equals("1")){
		        		fundTypeCodeName = "股票型";
		        	}else if(ofund_type.equals("2")){
		        		fundTypeCodeName = "货币型";
		        	}else if(ofund_type.equals("3")){
		        		fundTypeCodeName = "偏股型";
		        	}else if(ofund_type.equals("4")){
		        		fundTypeCodeName = "股债平衡型";
		        	}else if(ofund_type.equals("5")){
		        		fundTypeCodeName = "偏债型";
		        	}else if(ofund_type.equals("6")){
		        		fundTypeCodeName = "债券型";
		        	}else if(ofund_type.equals("7")){
		        		fundTypeCodeName = "保本型";
		        	}else if(ofund_type.equals("8")){
		        		fundTypeCodeName = "指数型";
		        	}else if(ofund_type.equals("9")){
		        		fundTypeCodeName = "短债型";
		        	}else if(ofund_type.equals("a")){
		        		fundTypeCodeName = "QDII";
		        	}else if(ofund_type.equals("b")){
		        		fundTypeCodeName = "混合基金";
		        	}else if(ofund_type.equals("c")){
		        		fundTypeCodeName = "券商理财";
		        	}else if(ofund_type.equals("d")){
		        		fundTypeCodeName = "牛熊宝";
		        	}else if(ofund_type.equals("m")){
		        		fundTypeCodeName = "现金产品";
		        	}else if(ofund_type.equals("n")){
		        		fundTypeCodeName = "一对多产品";
		        	}else if(ofund_type.equals("s")){
		        		fundTypeCodeName = "短期理财产品";
		        	}else if(ofund_type.equals("0")){
		        		fundTypeCodeName = "普通型";
		        	}else{
		        		fundTypeCodeName = "";
		        	}
		        }
		        foe.setRiskEvaluationName(riskEvaluationName);
		        foe.setFundTypeCodeName(fundTypeCodeName);
		        return foe;
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
	        			}
	        	}
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
			        			}
			        	}
					}
				}
		        user.setTrade_acco_list(trade_acco_list);
		        user.setTa_no_list(ta_no_list);
	        }
		}

	    
}
