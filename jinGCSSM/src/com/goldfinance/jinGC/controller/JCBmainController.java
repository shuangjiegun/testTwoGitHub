package com.goldfinance.jinGC.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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
import com.goldfinance.jinGC.entity.FundBuy;
import com.goldfinance.jinGC.entity.JcbFund;
import com.goldfinance.jinGC.entity.User;
import com.goldfinance.jinGC.po.AllFundProduct;
import com.goldfinance.jinGC.po.ApplyRequ;
import com.goldfinance.jinGC.po.ApplyResp;
import com.goldfinance.jinGC.po.Bank;
import com.goldfinance.jinGC.po.CashBackRequ;
import com.goldfinance.jinGC.po.CertificateType;
import com.goldfinance.jinGC.po.FundInfoRequ;
import com.goldfinance.jinGC.po.ShareRequ;
import com.goldfinance.jinGC.po.ShareResp;
import com.goldfinance.jinGC.po.TradequrRequ;
import com.goldfinance.jinGC.po.TradequrResp;
import com.goldfinance.jinGC.po.UserInfo;
import com.goldfinance.jinGC.service.AllFundProductService;
import com.goldfinance.jinGC.service.BankService;
import com.goldfinance.jinGC.service.CertificateTypeService;
import com.goldfinance.jinGC.service.FundBuyService;
import com.goldfinance.jinGC.service.JcbFundService;
import com.goldfinance.jinGC.service.LogService;
import com.goldfinance.jinGC.service.UserInfoService;
import com.goldfinance.jinGC.util.HSERYCPT;
import com.goldfinance.jinGC.util.Utils;
@Controller
public class JCBmainController extends UfxSDKCommon {
	private static final Logger LOG = LoggerFactory.getLogger(JCBmainController.class);
	private Map<String, Object> request = new LinkedHashMap<String, Object>();
	private Map<String, Object> response = new LinkedHashMap<String, Object>();
    private String url ;
    	@Autowired
    	private FundBuyService fundBuyService;
    	
    	@Autowired
    	private JcbFundService jcbFundService;
    	
    	@Autowired
    	private  UserInfoService userInfoService;
    	
    	@Autowired
    	private BankService bankService;
    	
    	 @Autowired
 	    private LogService logService;
    	
    	 @Autowired
 	    private CertificateTypeService certificateTypeService;
    	 
    	 @Autowired
 	    private AllFundProductService allFundProductService;
	    /**
	     * 
	     * @param req
	     * @param session
	     * @return
	     * @throws Exception
	     * 金诚宝首页
	     */
	@RequestMapping("/jcbindex")
	public ModelAndView jcbIndex(HttpServletRequest req, HttpSession session) throws Exception {
		long startA = System.currentTimeMillis();
   		long kobi = System.currentTimeMillis();
		long jodan = 0;
		User user = Utils.getUser(session);
		float all_total_new=0.0f;
		float no_pay_new=0.0f;
		ModelAndView modelAndView = traderQur(req, session);
		Map<String, Object> ma = modelAndView.getModel();
		List<TradequrResp> list = (List<TradequrResp>) ma.get("list");
        jodan = System.currentTimeMillis();
        LOG.error("走到92共用了 " + (jodan-kobi) +"毫秒");
        kobi = jodan;
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
		String date=simpleDateFormat.format(new Date());
		List<JcbFund> jcbList = jcbFundService.selectJcbFund();
		List<String> jcbCodes = new ArrayList<String>();
		if(jcbList.size()>0){
			int len = jcbList.size();
			for(int i=0;i<len;i++){
				jcbCodes.add(jcbList.get(i).getFundcode());
			}
		}
		List<JcbFund> listfund=jcbFundService.selectAll();
		List<TradequrResp> listcz = new ArrayList<TradequrResp>();
		List<TradequrResp> listtx = new ArrayList<TradequrResp>();
		List<ShareResp> listcc = new ArrayList<ShareResp>();
		List<TradequrResp> listMapztcz = new ArrayList<TradequrResp>();
		List<TradequrResp> listMapzttx = new ArrayList<TradequrResp>();
		for(int i=0;i<listfund.size();i++){
			String funcode=listfund.get(i).getFundcode();
			if(list!=null){
				for (int j = 0; j < list.size(); j++) {
					if(jcbCodes.contains(list.get(j).getFund_code())){
						if (list.get(j).getTaconfirm_flag().equals("9")
								&& list.get(j).getFund_busin_code().equals("022")&&list.get(j).getFund_code().equals(funcode)) {
							JcbFund jcbfund=jcbFundService.selectJcbFundByfundcode(list.get(j).getFund_code());
							list.get(j).setBalance(list.get(j).getBalance().substring(0, list.get(j).getBalance().lastIndexOf(".")));
							list.get(j).setFund_name(jcbfund.getFundname());
							listcz.add(list.get(j));
						}
						if (list.get(j).getTaconfirm_flag().equals("9")
								&& list.get(j).getFund_busin_code().equals("024")&&list.get(j).getFund_code().equals(funcode)) {
							JcbFund jcbfund=jcbFundService.selectJcbFundByfundcode(list.get(j).getFund_code());
							list.get(j).setFund_name(jcbfund.getFundname());
							listtx.add(list.get(j));
						}
					}
				}
			}
		}
        jodan = System.currentTimeMillis();
        LOG.error("走到130共用了 " + (jodan-kobi) +"毫秒");
        kobi = jodan;
        //【
/*		ShareRequ shareRequ = new ShareRequ();
		shareRequ.setRequest_num("50");
		shareRequ.setReqry_recordsum_flag("0");
		shareRequ.setQry_beginrownum("1");
		shareRequ.setClient_id(user.getClient_id());
		request = Utils.ObjectToMap(shareRequ);
		url = "/share_qry";
		String path = "/cwsale/v1" + url;
		List<Map<String, Object>> response = getUfxSDK().query(path, request);
		List<ShareResp> listcc = new ArrayList<ShareResp>();
		//if (response.get(0).get("data")==null && "".equals(response.get(0).get("error_info"))) {
		if ( "".equals(response.get(0).get("error_info")) && response.get(0).get("success_type").equals("0")) {
				for (Map<String, Object> map : response) {
					ShareResp shareResp = new ShareResp();
					shareResp = Utils.getObject(shareResp, map);
					boolean bA = shareResp.getUnpaid_income()!=null && !("").equals(shareResp.getUnpaid_income());
					boolean bB = jcbCodes.contains(shareResp.getFund_code());
					boolean bC = shareResp.getEnable_shares()!=null && !("").equals(shareResp.getEnable_shares());
					boolean bD = bA &&  Double.parseDouble(shareResp.getUnpaid_income())!=0.0;
					boolean bE = bC && Double.parseDouble(shareResp.getEnable_shares())!=0.0;
					if((bD || bE) && bB ){
						//查该基金的名称
					    Map<String,Object> reqA =new LinkedHashMap<String, Object>();
					    reqA.put("trust_way", ConstantUtil.TRUST_WAY);     //交易委托方式
					    reqA.put("request_num", "1");                 //请求行数
					    reqA.put("reqry_recordsum_flag",  "0");    //重新统计总记录数标志
					    reqA.put("qry_beginrownum", "1"); //查询起始行号
					    reqA.put("fund_code", shareResp.getFund_code());                     //基金代码
						List<Map<String, Object>> responseF = getUfxSDK().query("/cwsale/v1/fundinfo_qry" , reqA);
						if( "".equals(responseF.get(0).get("error_info")) ){ 
							if(Integer.parseInt(String.valueOf(responseF.get(0).get("success_type")))==0){			
								String fund_name = String.valueOf(responseF.get(0).get("fund_name"));
								shareResp.setFund_name(fund_name);
							}
						}
						if(shareResp.getEnable_shares()!=null&&!shareResp.getEnable_shares().equals("")){
							all_total_new = all_total_new+Float.parseFloat(shareResp.getEnable_shares());
						}
						if(shareResp.getUnpaid_income()!=null&&!shareResp.getUnpaid_income().equals("")){
							all_total_new = all_total_new+Float.parseFloat(shareResp.getUnpaid_income());
						}
						if(shareResp.getUnpaid_income()!=null&&!shareResp.getUnpaid_income().equals("")){
							no_pay_new = no_pay_new+Float.parseFloat(shareResp.getUnpaid_income());
						}
						listcc.add(shareResp);
					}
				}
				//】
		        jodan = System.currentTimeMillis();
		        LOG.error("走到180共用了 " + (jodan-kobi) +"毫秒");
		        kobi = jodan;
				long endA = System.currentTimeMillis();
				LOG.error("金诚宝首页请求共用了 " + (endA-startA) +"毫秒");
		}//】】 
*/		
        ModelAndView mav = jcbMethod(req);
		all_total_new = Float.parseFloat(mav.getModel().get("all_total_new")+"");
		no_pay_new = Float.parseFloat(mav.getModel().get("no_pay_new")+"");
		listcc = (List<ShareResp>)mav.getModel().get("listcc");
		req.setAttribute("all_total_new", all_total_new);
		req.setAttribute("no_pay_new", no_pay_new);
		modelAndView.addObject("all_total_new", all_total_new);
		modelAndView.addObject("no_pay_new", no_pay_new);
		modelAndView.setViewName("jcbindex");
		req.setAttribute("listcc", listcc);
		req.setAttribute("listcz", listcz);
		req.setAttribute("listtx", listtx);
		req.setAttribute("date", date);
        jodan = System.currentTimeMillis();
        LOG.error("走到202共用了 " + (jodan-kobi) +"毫秒");
		long endA = System.currentTimeMillis();
		LOG.error("金诚宝首页请求共用了 " + (endA-startA) +"毫秒");
		return modelAndView;
	}
   
	/**
	 * 查询金诚宝的资产和未结转收益
	 * @param req
	 * @return
	 * @throws Exception
	 */
		public ModelAndView jcbMethod(HttpServletRequest req) throws Exception {
			HttpSession session = req.getSession(true);
			float all_total_new=0.0f;
			float no_pay_new=0.0f;
			User user = Utils.getUser(session);
			List<JcbFund> jcbList = jcbFundService.selectJcbFund();
			List<String> jcbCodes = new ArrayList<String>();
			if(jcbList.size()>0){
				int len = jcbList.size();
				for(int i=0;i<len;i++){
					jcbCodes.add(jcbList.get(i).getFundcode());
				}
			}
			ModelAndView modelAndView = new ModelAndView();
			ShareRequ shareRequ = new ShareRequ();
			shareRequ.setRequest_num("50");
			shareRequ.setReqry_recordsum_flag("0");
			shareRequ.setQry_beginrownum("1");
			shareRequ.setClient_id(user.getClient_id());
			Map<String, Object> request  = Utils.ObjectToMap(shareRequ);
			url = "/share_qry";
			String path = "/cwsale/v1" + url;
			List<Map<String, Object>> response = getUfxSDK().query(path, request);
			List<ShareResp> listcc = new ArrayList<ShareResp>();
			if ( "".equals(response.get(0).get("error_info")) && response.get(0).get("success_type").equals("0")) {
					for (Map<String, Object> map : response) {
						ShareResp shareResp = new ShareResp();
						shareResp = Utils.getObject(shareResp, map);
						boolean bA = shareResp.getUnpaid_income()!=null && !("").equals(shareResp.getUnpaid_income());
						boolean bB = jcbCodes.contains(shareResp.getFund_code());
						boolean bC = shareResp.getEnable_shares()!=null && !("").equals(shareResp.getEnable_shares());
						boolean bD = bA &&  Double.parseDouble(shareResp.getUnpaid_income())!=0.0;
						boolean bE = bC && Double.parseDouble(shareResp.getEnable_shares())!=0.0;
						if((bD || bE) && bB ){
							//查该基金的名称
/*						    Map<String,Object> reqA =new LinkedHashMap<String, Object>();
						    reqA.put("trust_way", ConstantUtil.TRUST_WAY);     //交易委托方式
						    reqA.put("request_num", "1");                 //请求行数
						    reqA.put("reqry_recordsum_flag",  "0");    //重新统计总记录数标志
						    reqA.put("qry_beginrownum", "1"); //查询起始行号
						    reqA.put("fund_code", shareResp.getFund_code());                     //基金代码
							List<Map<String, Object>> responseF = getUfxSDK().query("/cwsale/v1/fundinfo_qry" , reqA);
							if( "".equals(responseF.get(0).get("error_info")) ){ 
								if(Integer.parseInt(String.valueOf(responseF.get(0).get("success_type")))==0){			
									String fund_name = String.valueOf(responseF.get(0).get("fund_name"));
									shareResp.setFund_name(fund_name);
								}
							}*/
							//】
							//【 基金名称换着方式查
							String fund_name ="";
							AllFundProduct fund = allFundProductService.findFundByfundCode(shareResp.getFund_code());
							if(fund!=null){
								fund_name = fund.getFund_name();
							}
							shareResp.setFund_name(fund_name);
							//】
							
							if(shareResp.getEnable_shares()!=null&&!shareResp.getEnable_shares().equals("")){
								all_total_new = all_total_new+Float.parseFloat(shareResp.getEnable_shares());
							}
							if(shareResp.getUnpaid_income()!=null&&!shareResp.getUnpaid_income().equals("")){
								all_total_new = all_total_new+Float.parseFloat(shareResp.getUnpaid_income());
							}
							if(shareResp.getUnpaid_income()!=null&&!shareResp.getUnpaid_income().equals("")){
								no_pay_new = no_pay_new+Float.parseFloat(shareResp.getUnpaid_income());
							}
							listcc.add(shareResp);
						}
					}	
			}
			modelAndView.addObject("listcc", listcc);
			modelAndView.addObject("all_total_new", all_total_new);
			modelAndView.addObject("no_pay_new", no_pay_new);
			return modelAndView;
		}	
	
	
		/**
		 * 
		 * @param apply
		 * @param req
		 * @param session
		 * @return
		 * @throws Exception
		 * 金诚宝申购
		 */
		@RequestMapping("/fund_apply")
		public ModelAndView apply(JcbFund jcbFund,ApplyRequ apply,HttpServletRequest req,HttpSession session) throws Exception{
			User user=Utils.getUser(session);
			ModelAndView modelAndView = new ModelAndView();
			apply.setDetail_fund_way(ConstantUtil.DETAIL_FUND_WAY);
			//密码要加密
			String password = req.getParameter("password");
			HSERYCPT miTool = new HSERYCPT();
			String miText = miTool.encrypt(password);
			apply.setPassword(miText);
	        request=Utils.ObjectToMap(apply);
	        url = "/allot_trade";
	        String path = "/cwsale/v1" + url;
	        Map<String, Object> response = getUfxSDK().transact(path, request);
	        if(response.get("success_type").equals("0") ){
	        	ApplyResp resp=new ApplyResp();
	        	resp=Utils.getObject(resp, response);
	        	FundBuy fundBuy=new FundBuy();
	        	fundBuy.setFundid(apply.getFund_code());
	        	fundBuy.setFundshares(apply.getBalance());
	        	fundBuy.setUserid(user.getId_no());
	        	fundBuy.setIssuccess("0");
	        	fundBuy.setFundname(jcbFund.getFundname());
	        	fundBuy.setApplytime(String.valueOf(response.get("apply_date")));
	        	fundBuyService.insertFundBuy(fundBuy);
	        	modelAndView.setViewName("rechargeOk");
	        	modelAndView.addObject("apply", apply);
	        	req.setAttribute("jcbfund", jcbFund);
	        	req.setAttribute("resp", resp);
	        	return modelAndView;
	        }else{
	        	modelAndView.setViewName("jcbRecharge3");
	        	modelAndView.addObject("apply", apply);
	        	modelAndView.addObject("jcbfund", jcbFund);
	        	modelAndView.addObject("error", String.valueOf(response.get("error_info")));
				return modelAndView;
	        }
			
		}
		/**
		 * 
		 * @return
		 * @throws Exception
		 * 金诚宝充值第一步
		 */
		@RequestMapping("/jcbRecharge1")
		public ModelAndView jcbRecharge1(HttpSession session) throws Exception{
			ModelAndView modelAndView = new ModelAndView();
			List<JcbFund> list=jcbFundService.selectJcbFund();
			modelAndView.addObject("list", list);
			modelAndView.setViewName("jcbRecharge1");
			return modelAndView;
		}
		/**
		 * 
		 * @param request
		 * @param session
		 * @return
		 * @throws Exception
		 * 金诚宝充值第二步
		 */
		@RequestMapping("/jcbRecharge2")
		public ModelAndView jcbRecharge2(String fund_code,HttpServletRequest req,HttpSession session) throws Exception{
			ModelAndView modelAndView = new ModelAndView();
			String ta_no ="";
			String fund_name ="";
			FundInfoRequ fundInfoRequ = new FundInfoRequ();
			fundInfoRequ.setTrust_way(ConstantUtil.TRUST_WAY);
			fundInfoRequ.setRequest_num("50");
			fundInfoRequ.setReqry_recordsum_flag("1");
			fundInfoRequ.setQry_beginrownum("1");
			fundInfoRequ.setFund_code(fund_code);
			request = Utils.ObjectToMap(fundInfoRequ);
			url = "/fundinfo_qry";
			String path = "/cwsale/v1" + url;
			List<Map<String, Object>> response = getUfxSDK().query(path, request);
			//if(response.get(0).get("data")==null && "".equals(response.get(0).get("error_info"))){
			if( "".equals(response.get(0).get("error_info"))){
		           for(Map<String, Object> info : response){
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
	        if(num==0){  //【等会改成   num==0
	        	//需增开交易账户	
	        	System.out.println("需增开交易账户");
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
	  	        Map<String, Object> response1 = getUfxSDK().transact("/cwsale/v1/fundaccount_add_trade", request);
	  	        
	  	        if(response1.get("success_type").equals("") || response1.get("success_type") == null || (Integer.parseInt(String.valueOf(response1.get("success_type")))!=0)){
	  			 	List<Bank> banks = bankService.findBankList();
	  			 	List<CertificateType> certificateTypes = certificateTypeService.findCertificateTypeList();		 	
	  			 	logService.insertLog(user.getId_no(), "增开基金账户", "", "增开基金账户失败 ", String.valueOf(response1.get("error_info")));			 	
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
	  	        	modelAndView.addObject("error_info", response1.get("error_info") );	        	
	  	        	modelAndView.setViewName("login_openAccount"); //login_openAccount.ftl
	  	        	return modelAndView;
	  	        }
	  	        
	  	        logService.insertLog(user.getId_no(), "增开基金账户", "", "增开基金账户成功", String.valueOf(response1.get("ta_acco")));
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
			JcbFund jcbfund=jcbFundService.selectJcbFundByfundcode(fund_code);
			User use=Utils.getUser(session);
			UserInfo userInfo= userInfoService.findUserInfoByClientId(use.getClient_id()).get(0);
			Bank bank=bankService.findBankByBankCode(userInfo.getBankCode());
			ApplyRequ apply=new ApplyRequ();
			apply.setFund_code(fund_code);
			apply.setBank_name(bank.getBankName());
			String  banknum=Utils.toreplace(userInfo.getBankAccount(), "\\d*(\\d{4})","*****$1");
			apply.setBank_no(banknum);
			apply.setBank_account(userInfo.getBankAccount());
			apply.setTrade_acco(userInfo.getTrade_acco());
			req.setAttribute("apply", apply);
			req.setAttribute("jcbfund", jcbfund);
			modelAndView.setViewName("jcbRecharge2");
			return modelAndView;
		}
		/**
		 * 
		 * @param apply
		 * @param request
		 * @param session
		 * @return
		 * @throws Exception
		 * 金诚宝充值第三步
		 */
		@RequestMapping("/jcbRecharge3")
		public ModelAndView jcbRecharge3(JcbFund jcbFund,ApplyRequ apply,HttpServletRequest req,HttpSession session) throws Exception{
			String amount=(String) req.getParameter("amount");
			ModelAndView modelAndView = new ModelAndView();
			apply.setTrust_way(ConstantUtil.TRUST_WAY);
			apply.setBalance(amount);
			apply.setMoney_type(ConstantUtil.MONEY_TYPE);
			apply.setCapital_mode(ConstantUtil.CAPITAL_MODE);
			apply.setShare_type(ConstantUtil.SHARE_TYPE);
			req.setAttribute("apply", apply);
			req.setAttribute("jcbfund", jcbFund);
			modelAndView.setViewName("jcbRecharge3");
			return modelAndView;
		}
		/**
		 * 
		 * @param request
		 * @param session
		 * @return
		 * @throws Exception
		 * 提现第一步
		 */
		@RequestMapping("/cashBack1")
		public ModelAndView cashBack1(HttpServletRequest req,HttpSession session) throws Exception{
			ModelAndView modelAndView=share_qry(req,session);
			List<CashBackRequ> list=new ArrayList<CashBackRequ>();
			Map<String, Object> map=modelAndView.getModel();
			Set<Entry<String, Object>> set=map.entrySet();
			Iterator<Entry<String, Object>> it=set.iterator();
			while(it.hasNext()){
				Entry<String, Object> entry=it.next();
				String key=entry.getKey();
				if(key.equals("fund")){
					List<Map<String, String>> ma= (List<Map<String, String>>) entry.getValue();
					for(int i=0;i<ma.size();i++){
						CashBackRequ cashBackRequ=new CashBackRequ();
						cashBackRequ.setFund_code(ma.get(i).get("fund_code"));
						cashBackRequ.setShares(ma.get(i).get("enable_shares"));
						cashBackRequ.setBank_no(ma.get(i).get("bank_no"));
						cashBackRequ.setBank_account(ma.get(i).get("bank_account"));
						cashBackRequ.setTrade_acco(ma.get(i).get("trade_acco"));
						cashBackRequ.setCapital_mode(ma.get(i).get("capital_mode"));
						//查该基金的名称
					    Map<String,Object> reqA =new LinkedHashMap<String, Object>();
					    reqA.put("trust_way", "2");     //交易委托方式
					    reqA.put("request_num", "1");                 //请求行数
					    reqA.put("reqry_recordsum_flag",  "0");    //重新统计总记录数标志
					    reqA.put("qry_beginrownum", "1"); //查询起始行号
					    reqA.put("fund_code", ma.get(i).get("fund_code"));                     //基金代码
						List<Map<String, Object>> responseF = getUfxSDK().query("/cwsale/v1/fundinfo_qry" , reqA);
						if( "".equals(responseF.get(0).get("error_info")) ){ 
							if(Integer.parseInt(String.valueOf(responseF.get(0).get("success_type")))==0){			
								String fund_name = String.valueOf(responseF.get(0).get("fund_name"));
								cashBackRequ.setFund_name(fund_name);
							}
						}
						list.add(cashBackRequ);
					}
				}
			}
			
			modelAndView.setViewName("cashBack1");
			modelAndView.addObject("list", list);
			return modelAndView;
		}
		/**
		 * 
		 * @param cashBack
		 * @param request
		 * @param session
		 * @return
		 * @throws Exception
		 * 提现第二步
		 */
		@RequestMapping("/cashBack2")
		public ModelAndView cashBack2(CashBackRequ cashBack,HttpServletRequest req,HttpSession session) throws Exception{
			String bank_no = req.getParameter("bank_no");
			String bank_account = req.getParameter("bank_account");
			Bank bank=bankService.findBankByBankCode(bank_no);
			ApplyRequ apply=new ApplyRequ();
			apply.setBank_name(bank.getBankName());
			String  banknum=Utils.toreplace(bank_account, "\\d*(\\d{4})","*****$1");
			apply.setBank_no(banknum);
			apply.setBank_account(bank_account);
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("cashBack2");
			req.setAttribute("cashBack", cashBack);
			req.setAttribute("apply", apply);
			return modelAndView;
		}
		
		@RequestMapping("/cashBackspe")
		public ModelAndView cashBackspe(CashBackRequ cashBack,HttpServletRequest req,HttpSession session) throws Exception{
			ModelAndView modelAndView=share_qry(req,session);
			Map<String, Object> map=modelAndView.getModel();
			List<ShareResp> list1=(List<ShareResp>) map.get("list");
			String bank_no = "";
			String bank_account = "";
			for(int i=0;i<list1.size();i++){
				if(list1.get(i).getFund_code().equals(cashBack.getFund_code())){
					cashBack.setFund_name(list1.get(i).getFund_name());
					cashBack.setShares(list1.get(i).getEnable_shares());
					bank_no = list1.get(i).getBank_no();
					cashBack.setBank_no(bank_no);
					bank_account = list1.get(i).getBank_account();
					cashBack.setBank_account(bank_account);
					cashBack.setTrade_acco(list1.get(i).getTrade_acco());
					cashBack.setCapital_mode(list1.get(i).getCapital_mode());
					//查该基金的名称
					//【
				    Map<String,Object> reqA =new LinkedHashMap<String, Object>();
				    reqA.put("trust_way", "2");     //交易委托方式
				    reqA.put("request_num", "1");                 //请求行数
				    reqA.put("reqry_recordsum_flag",  "0");    //重新统计总记录数标志
				    reqA.put("qry_beginrownum", "1"); //查询起始行号
				    reqA.put("fund_code", cashBack.getFund_code());                     //基金代码
					List<Map<String, Object>> responseF = getUfxSDK().query("/cwsale/v1/fundinfo_qry" , reqA);
					if( "".equals(responseF.get(0).get("error_info")) ){ 
						if(Integer.parseInt(String.valueOf(responseF.get(0).get("success_type")))==0){			
							String fund_name = String.valueOf(responseF.get(0).get("fund_name"));
							cashBack.setFund_name(fund_name);
						}
					}
				//】
				}
			}
			Bank bank=bankService.findBankByBankCode(bank_no);
			ApplyRequ apply=new ApplyRequ();
			apply.setBank_name(bank.getBankName());
			String  banknum=Utils.toreplace(bank_account, "\\d*(\\d{4})","*****$1");
			apply.setBank_no(banknum);
			apply.setBank_account(bank_account);
			modelAndView.setViewName("cashBack2");
			req.setAttribute("cashBack", cashBack);
			req.setAttribute("apply", apply);
			return modelAndView;
		}
		/**
		 * 
		 * @param cashBack
		 * @param request
		 * @param session
		 * @return
		 * @throws Exception
		 * 提现第三步
		 */
		@RequestMapping("/cashBack3")
		public ModelAndView cashBack3(CashBackRequ cashBack,HttpServletRequest req,HttpSession session) throws Exception{
			String businessType = req.getParameter("businessType");
			String bank_no = req.getParameter("bank_no");
			String bank_account = req.getParameter("bank_account");
			Bank bank=bankService.findBankByBankCode(bank_no);
			ApplyRequ apply=new ApplyRequ();
			apply.setBank_name(bank.getBankName());
			String  banknum=Utils.toreplace(bank_account, "\\d*(\\d{4})","*****$1");
			apply.setBank_no(banknum);
			apply.setBank_account(bank_account);
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("cashBack3");
			req.setAttribute("cashBack", cashBack);
			req.setAttribute("apply", apply);
			modelAndView.addObject("businessType", businessType);
			return modelAndView;
		}
		/**
		 * 
		 * @param cashBack
		 * @param req
		 * @param session
		 * @return
		 * @throws Exception
		 * 提现最后一步
		 */
		@RequestMapping("/backOK")
		public ModelAndView backOK(CashBackRequ cashBack, HttpServletRequest req, HttpSession session) throws Exception {
			String password=req.getParameter("password");
			String fund_code=req.getParameter("fund_code");
			String shares=req.getParameter("shares");
			String businessType=req.getParameter("businessType");
			String bank_no = req.getParameter("bank_no");
			String bank_account = req.getParameter("bank_account");
			String trade_acco = req.getParameter("trade_acco");
			String capital_mode = req.getParameter("capital_mode");
			Bank bank=bankService.findBankByBankCode(bank_no);
			ApplyRequ apply=new ApplyRequ();
			apply.setBank_name(bank.getBankName());
			String  banknum=Utils.toreplace(bank_account, "\\d*(\\d{4})","*****$1");
			apply.setBank_no(banknum);
			apply.setBank_account(bank_account);
			ModelAndView modelAndView = new ModelAndView();
			
			Map<String, Object> request = new LinkedHashMap<String, Object>(); 
	        request.put("trust_way", ConstantUtil.TRUST_WAY);
	        request.put("trade_acco", trade_acco);
	        HSERYCPT miTool = new HSERYCPT();
			String miText = miTool.encrypt(password);
	        request.put("password", miText);
	        request.put("fund_code", fund_code);
	        request.put("share_type", ConstantUtil.SHARE_TYPE);		
	        request.put("shares", shares);		
	        request.put("capital_mode", capital_mode);		
					
	        //如果是普通取现，即赎回
	        if("normal".equals(businessType)){
	        	request.put("fund_exceed_flag", ConstantUtil.FUND_EXCEED_FLAG);		
	        	Map<String, Object> response = getUfxSDK().transact("/cwsale/v1/redeem_trade", request);
				if (response.get("success_type").equals("0")) {
					modelAndView.setViewName("backOK");
					req.setAttribute("cashBack", cashBack);
					req.setAttribute("apply", apply);
				}else {
					modelAndView.setViewName("cashBack3");
					req.setAttribute("cashBack", cashBack);
					req.setAttribute("apply", apply);
					modelAndView.addObject("error", String.valueOf(response.get("error_info")));
				}
	        }
	        //如果是快速取现，即快速过户
	        if("fast".equals(businessType)){
	        	Map<String, Object> response = getUfxSDK().transact("/cwsale/v1/fasttransfer_trade_v1.0.2", request);
				if (response.get("success_type").equals("0")) {
					modelAndView.setViewName("backOK");
					req.setAttribute("cashBack", cashBack);
					req.setAttribute("apply", apply);
				}else {
					modelAndView.setViewName("cashBack3");
					req.setAttribute("cashBack", cashBack);
					req.setAttribute("apply", apply);
					modelAndView.addObject("error", String.valueOf(response.get("error_info")));
				}
	        }
	        modelAndView.addObject("businessType", businessType);
	        return modelAndView;
		}
		
		/**
		 * 交易申请查询
		 * @param cashBack
		 * @param req
		 * @param session
		 * @return
		 * @throws Exception
		 */
	@RequestMapping("/traderQur")
	public ModelAndView traderQur(HttpServletRequest req, HttpSession session) throws Exception {
		User user = Utils.getUser(session);
		ModelAndView modelAndView = new ModelAndView();
		TradequrRequ tradequr = new TradequrRequ();
		tradequr.setTrust_way(ConstantUtil.TRUST_WAY);
		tradequr.setReqry_recordsum_flag("1");
		tradequr.setQry_beginrownum("1");
		tradequr.setClient_id(user.getClient_id());
		tradequr.setRequest_num("50");
		request = Utils.ObjectToMap(tradequr);
		url = "/trade_apply_qry";
		String path = "/cwsale/v1" + url;
		List<Map<String, Object>> response = getUfxSDK().query(path, request);
		ArrayList<TradequrResp> list = new ArrayList<TradequrResp>();
		//if(response.get(0).get("data")==null && "".equals(response.get(0).get("error_info"))){	
		if( "".equals(response.get(0).get("error_info"))){	
			if (response.get(0).get("success_type").equals("0")) {
				for (Map<String, Object> map : response) {
					TradequrResp tradequrResp = new TradequrResp();
					tradequrResp = Utils.getObject(tradequrResp, map);
					list.add(tradequrResp);
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
						TradequrRequ tradequr2 = new TradequrRequ();
						tradequr2.setTrust_way(ConstantUtil.TRUST_WAY);
						tradequr2.setReqry_recordsum_flag(flag);
						tradequr2.setQry_beginrownum((50 * i + 1) + "");
						tradequr2.setClient_id(user.getClient_id());
						tradequr2.setRequest_num("50");
						request = Utils.ObjectToMap(tradequr2);
						url = "/trade_apply_qry";
						String path2 = "/cwsale/v1" + url;
						List<Map<String, Object>> response2 = getUfxSDK().query(path2, request);
						for (Map<String, Object> map2 : response2) {
							TradequrResp tradequrResp2 = new TradequrResp();
							tradequrResp2 = Utils.getObject(tradequrResp2, map2);
							list.add(tradequrResp2);
						}
					}
				}
				return modelAndView.addObject("list", list);
			} else {
				return modelAndView;
			}
		} else {
			return modelAndView;
		}
	}
		
		/**
		 * 
		 * @param req
		 * @param session
		 * @return
		 * @throws Exception
		 * 份额查询
		 */
		@RequestMapping("/share_qry")//
		public ModelAndView share_qry(HttpServletRequest req,HttpSession session) throws Exception {
			User user=Utils.getUser(session);
			ModelAndView modelAndView = new ModelAndView();
	    	String requestnum = "50";   //请求行数维护  request_num
	   	 	int num=0;
			List<ShareResp> list=new ArrayList<ShareResp>();
			List<Map<String, String>> fund=new ArrayList<Map<String,String>>();
			List<JcbFund> jcbList = jcbFundService.selectJcbFund();
			List<String> jcbCodes = new ArrayList<String>();
			if(jcbList.size()>0){
				int len = jcbList.size();
				for(int i=0;i<len;i++){
					jcbCodes.add(jcbList.get(i).getFundcode());
				}
			}
			ShareRequ shareRequ=new ShareRequ();
			shareRequ.setRequest_num("50");
			shareRequ.setReqry_recordsum_flag("1");
			shareRequ.setQry_beginrownum("1");
			shareRequ.setClient_id(user.getClient_id());
			request = Utils.ObjectToMap(shareRequ);
			url = "/share_qry";
			String path = "/cwsale/v1" + url;
			List<Map<String, Object>> response = getUfxSDK().query(path, request);
			//if(response.get(0).get("data")==null && "".equals(response.get(0).get("error_info"))){
			if( "".equals(response.get(0).get("error_info")) && response.get(0).get("success_type").equals("0")){
					for(Map<String, Object> map :response){
						ShareResp shareResp=new ShareResp();
						shareResp=Utils.getObject(shareResp, map);
						boolean bA = shareResp.getUnpaid_income()!=null && !("").equals(shareResp.getUnpaid_income());
						boolean bB = jcbCodes.contains(shareResp.getFund_code());
						boolean bC = shareResp.getEnable_shares()!=null && !("").equals(shareResp.getEnable_shares());
						boolean bD = bA &&  Double.parseDouble(shareResp.getUnpaid_income())!=0.0;
						boolean bE = bC && Double.parseDouble(shareResp.getEnable_shares())!=0.0;
						if((bD || bE) && bB ){
							list.add(shareResp);
						}
			        	Double i = Double.parseDouble(String.valueOf(map.get("total_count")) );
			        	Double j = Double.parseDouble(requestnum);
			        	num =  (int)Math.ceil( i / j) ;	
					}
			}	
	        if(num>1){  //说明需要继续查   如num=3  一共查3次，上面已经查过一次了，还需查两次
	        	for(int i=1;i<num;i++){   
	        		request.clear();
	        		request.put("request_num",   requestnum);
	        		request.put("client_id",   user.getClient_id());
	     	        String flag = "";
	     	        if(i==num-1){
	     	        	flag = "0";  //0 不再统计
	     	        }else{
	     	        	flag = "1";  //1 还需统计
	     	        }	        	        
	     	      request.put("reqry_recordsum_flag",  flag);   //重新统计总记录数标志	        	             
	     	      String start =  Integer.parseInt(requestnum)*i + 1 +"";   
	     	      request.put("qry_beginrownum", start);           //查询起始行号	
				  List<Map<String, Object>> responseB = getUfxSDK().query("/cwsale/v1/share_qry", request);
				  //【
					if( "".equals(responseB.get(0).get("error_info")) && responseB.get(0).get("success_type").equals("0")){
						for(Map<String, Object> map :responseB){
							ShareResp shareResp=new ShareResp();
							shareResp=Utils.getObject(shareResp, map);
							
							boolean bA = shareResp.getUnpaid_income()!=null && !("").equals(shareResp.getUnpaid_income());
							boolean bB = jcbCodes.contains(shareResp.getFund_code());
							boolean bC = shareResp.getEnable_shares()!=null && !("").equals(shareResp.getEnable_shares());
							boolean bD = bA &&  Double.parseDouble(shareResp.getUnpaid_income())!=0.0;
							boolean bE = bC && Double.parseDouble(shareResp.getEnable_shares())!=0.0;
							if((bD || bE) && bB ){
								list.add(shareResp);
							}
						}
					}
				  //】
	        	}
	        }
	        if(list.size()>0){
	        	int length = list.size();
				for(int i=0;i<length;i++){
					Map<String, String> map1=new HashMap<String, String>();
					String fund_code=list.get(i).getFund_code();
					String enable_shares=list.get(i).getEnable_shares();
					String bank_no=list.get(i).getBank_no();
					String bank_account=list.get(i).getBank_account();
					String capital_mode=list.get(i).getCapital_mode();
					String trade_acco=list.get(i).getTrade_acco();
					map1.put("fund_code", fund_code);
					map1.put("enable_shares", enable_shares);
					map1.put("bank_no", bank_no);
					map1.put("bank_account", bank_account);
					map1.put("capital_mode", capital_mode);
					map1.put("trade_acco", trade_acco);
					fund.add(map1);
				}
	        }
			modelAndView.addObject("fund", fund);
			modelAndView.addObject("list", list);
			return modelAndView;
		}
		
		@RequestMapping("/confirm")
		public ModelAndView confirm(CashBackRequ cashBack, HttpServletRequest req,
				HttpSession session) throws Exception {
			User user=Utils.getUser(session);
			ModelAndView modelAndView = new ModelAndView();
			request.put("request_num", "50"); 
			request.put("reqry_recordsum_flag", "0"); 
			request.put("qry_beginrownum", "1"); 
			request.put("client_id", user.getClient_id());
			url = "/trade_confirm_qry";
			String path = "/cwsale/v1" + url;
			Map<String, Object> response = getUfxSDK().transact(path, request);
			if (response.get("success_type").equals("0")) {
				modelAndView.setViewName("backOK");
				req.setAttribute("cashBack", cashBack);
				return modelAndView;
			} else {
				modelAndView.setViewName("login");
				return modelAndView;
			}
		}
		
		@RequestMapping("/account_qry")
		public ModelAndView account_qry(CashBackRequ cashBack, HttpServletRequest req,
				HttpSession session) throws Exception {
			ModelAndView modelAndView = new ModelAndView();
			request.put("trust_way", ConstantUtil.TRUST_WAY); 	
			request.put("request_num", "50"); 
			request.put("reqry_recordsum_flag", "0"); 
			request.put("qry_beginrownum", "1"); 
			request.put("id_no", "340621198901057039");
			url = "/account_qry";
			String path = "/cwsale/v1" + url;
			Map<String, Object> response = getUfxSDK().transact(path, request);
			if (response.get("success_type").equals("0")) {
				modelAndView.setViewName("backOK");
				req.setAttribute("cashBack", cashBack);
				return modelAndView;
			} else {
				modelAndView.setViewName("login");
				return modelAndView;
			}
		}
		
		
		@RequestMapping("/capital_in_trade")
		public ModelAndView capital_in_trade(CashBackRequ cashBack, HttpServletRequest req,
				HttpSession session) throws Exception {
			User user=Utils.getUser(session);
			ModelAndView modelAndView = new ModelAndView();
			request.put("trust_way", ConstantUtil.TRUST_WAY); 	
			request.put("password", "469888"); 
			request.put("trade_acco", user.getTrade_acco_list().get(0));
			request.put("money_type", "156"); 
			request.put("balance", "1000000000"); 
			request.put("account_type", "A"); 
			request.put("bank_no", "002");
			request.put("bank_name", "中国工商银行");
			request.put("bank_account_name", "刘会");
			request.put("bank_account", "6222300512054787");
			request.put("capital_mode", "1");
			url = "/capital_in_trade";
			String path = "/cwsale/v1" + url;
			Map<String, Object> response = getUfxSDK().transact(path, request);
			if (response.get("success_type").equals("0")) {
				modelAndView.setViewName("backOK");
				req.setAttribute("cashBack", cashBack);
				return modelAndView;
			} else {
				modelAndView.setViewName("login");
				return modelAndView;
			}
		}
		
		@RequestMapping("/prod_income_detail_qry")
		public ModelAndView prod_income_detail_qry(CashBackRequ cashBack, HttpServletRequest req,
				HttpSession session) throws Exception {
			User user=Utils.getUser(session);
			ModelAndView modelAndView = new ModelAndView();
			request.put("trust_way", ConstantUtil.TRUST_WAY); 	
			request.put("client_id", user.getClient_id());
			request.put("request_num", "50");
			request.put("reqry_recordsum_flag", "1");
			request.put("qry_beginrownum", "1");
			url = "/prod_income_detail_qry";
			String path = "/cwsale/v1" + url;
			List<Map<String, Object>> response = getUfxSDK().query(path, request);
			return modelAndView;
		}
		
	@RequestMapping("/tradingRecord_index")
	public ModelAndView accountManager_index(HttpServletRequest req, HttpSession session) throws Exception {
		long startA = System.currentTimeMillis();
   		long kobi = System.currentTimeMillis();
		long jodan = 0;
		ModelAndView modelAndView = traderQur(req, session);
        jodan = System.currentTimeMillis();
        LOG.error("交易记录1009共用了 " + (jodan-kobi) +"毫秒");
        kobi = jodan;
		User user = Utils.getUser(session);
		Map<String, Object> ma = modelAndView.getModel();
		List<TradequrResp> list = (List<TradequrResp>) ma.get("list");
		List<TradequrResp> listTradequrResp =new ArrayList<TradequrResp>();
		List<TradequrResp> listTradequrRespsg =new ArrayList<TradequrResp>();
		List<TradequrResp> listTradequrRespsh =new ArrayList<TradequrResp>();
		List<TradequrResp> listTradequrResprg =new ArrayList<TradequrResp>();
		List<TradequrResp> listTradequrRespcd =new ArrayList<TradequrResp>();
		List<TradequrResp> listTradequrRespzh =new ArrayList<TradequrResp>();
		if(list!=null && list.size()>0){
			int lenA = list.size();
			for(int i=0;i<lenA;i++){
				//查每条交易记录返回的银行代码，银行名称，银行账号
				Bank bankA = bankService.findBankByBankCode(list.get(i).getBank_no());
				list.get(i).setBankName(bankA.getBankName());
				String banknumA = Utils.toreplace(list.get(i).getReceivable_account(), "\\d*(\\d{4})", "*****$1");
				list.get(i).setReceivable_account(banknumA);
				//查该基金的名称
				//【
/*				String fund_name ="";
			    Map<String,Object> reqA =new LinkedHashMap<String, Object>();
			    reqA.put("trust_way", "2");     //交易委托方式
			    reqA.put("request_num", "1");                 //请求行数
			    reqA.put("reqry_recordsum_flag",  "0");    //重新统计总记录数标志
			    reqA.put("qry_beginrownum", "1"); //查询起始行号
			    reqA.put("fund_code", list.get(i).getFund_code());                     //基金代码
				List<Map<String, Object>> responseF = getUfxSDK().query("/cwsale/v1/fundinfo_qry" , reqA);
				if( "".equals(responseF.get(0).get("error_info")) ){ 
					if(Integer.parseInt(String.valueOf(responseF.get(0).get("success_type")))==0){			
						fund_name = String.valueOf(responseF.get(0).get("fund_name"));
						list.get(i).setFund_name(fund_name);
					}
				}*/
			//】
				//【 基金名称换着方式查
				String fund_name ="";
				AllFundProduct fund = allFundProductService.findFundByfundCode(list.get(i).getFund_code());
				if(fund!=null){
					fund_name = fund.getFund_name();
				}
					list.get(i).setFund_name(fund_name);
				//】
				if(list.get(i).getFund_busin_code().equals("022")||list.get(i).getFund_busin_code().equals("020")||list.get(i).getFund_busin_code().equals("024")||list.get(i).getFund_busin_code().equals("053")||list.get(i).getFund_busin_code().equals("036")){
					listTradequrResp.add(list.get(i));
				}
				if(list.get(i).getFund_busin_code().equals("022")){
					list.get(i).setBalance(list.get(i).getBalance().substring(0, list.get(i).getBalance().lastIndexOf(".")));
					listTradequrRespsg.add(list.get(i));
				}
				if(list.get(i).getFund_busin_code().equals("020")){
					listTradequrResprg.add(list.get(i));
				}
				if(list.get(i).getFund_busin_code().equals("024")){
					listTradequrRespsh.add(list.get(i));
				}
				if(list.get(i).getFund_busin_code().equals("053")){
					if(Double.parseDouble(list.get(i).getBalance())==0.0 && Double.parseDouble(list.get(i).getShares())!=0.0){ //赎回和转换的撤单单位是份
						list.get(i).setBalance(list.get(i).getShares());
						list.get(i).setShare_type("B");
					}else if (Double.parseDouble(list.get(i).getShares())==0.0 && Double.parseDouble(list.get(i).getBalance())!=0.0){ //申购认购的撤单单位是元
						
					}
					listTradequrRespcd.add(list.get(i));
				}
				if(list.get(i).getFund_busin_code().equals("036")){
					listTradequrRespzh.add(list.get(i));
				}
			}
		}

		if(listTradequrResp!=null && listTradequrResp.size()>0){
			int lenB = listTradequrResp.size();
			for (int i = 0; i < lenB; i++) {
				listTradequrResp.get(i).setFund_name(listTradequrResp.get(i).getFund_name());
				if (listTradequrResp.get(i).getFund_busin_code().equals("024")|| listTradequrResp.get(i).getFund_busin_code().equals("036")) { //申购认购的撤单单位是元，赎回和转换的撤单单位是份
					//listTradequrResp.get(i).setBalance(listTradequrResp.get(i).getShares().equals("")?listTradequrResp.get(i).getBalance():listTradequrResp.get(i).getShares());
					listTradequrResp.get(i).setShare_type("B");
					listTradequrResp.get(i).setBalance(listTradequrResp.get(i).getShares());
				}else if(listTradequrResp.get(i).getFund_busin_code().equals("053")){
					if(Double.parseDouble(listTradequrResp.get(i).getBalance())==0.0 && Double.parseDouble(listTradequrResp.get(i).getShares())!=0.0){ //赎回和转换的撤单单位是份
						listTradequrResp.get(i).setBalance(list.get(i).getShares());
						listTradequrResp.get(i).setShare_type("B");
					}else{listTradequrResp.get(i).setBalance(list.get(i).getBalance());}
				} 
				else {
					listTradequrResp.get(i).setBalance(listTradequrResp.get(i).getBalance());   //||listTradequrResp.get(i).getFund_busin_code().equals("053")
				}
				String date = listTradequrResp.get(i).getApply_date();
				SimpleDateFormat sFormat1 = new SimpleDateFormat("yyyyMMdd");
				Date sdate = sFormat1.parse(date);
				SimpleDateFormat sFormat2 = new SimpleDateFormat("yyyy-MM-dd");
				String datetime = sFormat2.format(sdate);
				listTradequrResp.get(i).setApply_date(datetime);
				String apply_time="";
				apply_time=listTradequrResp.get(i).getApply_time().substring(0, 2)+":"+listTradequrResp.get(i).getApply_time().substring(2, 4)+":"+listTradequrResp.get(i).getApply_time().substring(4, 6);
				listTradequrResp.get(i).setApply_time(apply_time);
			}
		}
        jodan = System.currentTimeMillis();
        LOG.error("交易记录1110共用了 " + (jodan-kobi) +"毫秒");
        kobi = jodan;
		modelAndView.addObject("listTradequrResp", listTradequrResp);
		modelAndView.addObject("listTradequrRespsg", listTradequrRespsg);
		modelAndView.addObject("listTradequrRespsh", listTradequrRespsh);
		modelAndView.addObject("listTradequrResprg", listTradequrResprg);
		modelAndView.addObject("listTradequrRespcd", listTradequrRespcd);
		modelAndView.addObject("listTradequrRespzh", listTradequrRespzh);
		modelAndView.setViewName("myAsset_tradeRecord");
		long endA = System.currentTimeMillis();
		LOG.error("交易记录请求共用了 " + (endA-startA) +"毫秒");
		return modelAndView;

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
