package com.goldfinance.jinGC.controller;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.goldfinance.jinGC.common.ConstantUtil;
import com.goldfinance.jinGC.common.UfxSDKCommon;
import com.goldfinance.jinGC.entity.PersonInfo;
import com.goldfinance.jinGC.entity.User;
import com.goldfinance.jinGC.po.ApplyRequ;
import com.goldfinance.jinGC.po.Bank;
import com.goldfinance.jinGC.po.CustomerInformation;
import com.goldfinance.jinGC.po.FundInfoRequ;
import com.goldfinance.jinGC.po.FundInfoResp;
import com.goldfinance.jinGC.po.Log;
import com.goldfinance.jinGC.po.Result;
import com.goldfinance.jinGC.po.ShareRequ;
import com.goldfinance.jinGC.po.ShareResp;
import com.goldfinance.jinGC.po.TradequrRequ;
import com.goldfinance.jinGC.po.TradequrResp;
import com.goldfinance.jinGC.po.UserInfo;
import com.goldfinance.jinGC.service.BankService;
import com.goldfinance.jinGC.service.JcbFundService;
import com.goldfinance.jinGC.service.LogService;
import com.goldfinance.jinGC.service.UserInfoService;
import com.goldfinance.jinGC.util.HSERYCPT;
import com.goldfinance.jinGC.util.Utils;


@Controller
public class AccountManagerController extends UfxSDKCommon {

	private Map<String, Object> request = new LinkedHashMap<String, Object>();
	private Map<String, Object> response = new LinkedHashMap<String, Object>();
	private String url;
	@Autowired
	private LogService logService;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private BankService bankService;
	@Autowired
	private JcbFundService jcbFundService;

	@RequestMapping("/accountManager_index")
	public ModelAndView accountManager_index(HttpServletRequest req,
			HttpSession session) throws Exception {
		User user = Utils.getUser(session);
		ModelAndView modelAndView = new ModelAndView();
		Log log = logService.selectTimeByUserId(user);
		modelAndView.setViewName("personalCenter_index");
		req.setAttribute("log", log);
		req.setAttribute("user", user);
		return modelAndView;

	}

	@RequestMapping("/personalCenter")
	public ModelAndView personalCenter(HttpServletRequest req,
			HttpSession session) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		ModelAndView modelAndView2 = new ModelAndView();
		modelAndView = clientinfo_qry_acct(req, session);
		modelAndView2 = paperinfo_qry_acct(req, session);
		Map<String, Object> map = modelAndView.getModel();
		Map<String, Object> map2 = modelAndView2.getModel();
		String invest_risk_tolerance = (String) map2
				.get("invest_risk_tolerance");
		PersonInfo personInfo = (PersonInfo) map.get("personInfo");
		personInfo.setOfund_risklevel(invest_risk_tolerance);
		String idnum = personInfo.getId_no();
		String id = Utils.toreplace(idnum, "(\\d{6})\\d*(\\d{2})",
				"$1**********$2");
		personInfo.setMobile_tel(Utils.toreplace(personInfo.getMobile_tel(),
				"(\\d{3})\\d*(\\d{3})", "$1*****$2"));
		personInfo.setId_no(id);
		User user = Utils.getUser(session);

		Log log = logService.selectTimeByUserId(user);
		modelAndView.setViewName("personalCenter_info");
		req.setAttribute("log", log);
		req.setAttribute("personInfo", personInfo);
		return modelAndView;

	}

	/**
	 * 
	 * @param req
	 * @param session
	 * @return
	 * @throws Exception
	 *             客户资料查询
	 */
	@RequestMapping("/clientinfo_qry_acct")
	public ModelAndView clientinfo_qry_acct(HttpServletRequest req,
			HttpSession session) throws Exception {
		User user = Utils.getUser(session);
		ModelAndView modelAndView = new ModelAndView();
		request.put("trust_way", ConstantUtil.TRUST_WAY);
		request.put("trade_acco", user.getTrade_acco_list().get(0));
		request.put("password", user.getPassword());
		url = "/clientinfo_qry_acct";
		String path = "/cwsale/v1" + url;
		Map<String, Object> response = getUfxSDK().transact(path, request);
		System.out.println(response);
		if (response.get("success_type").equals("0")) {
			CustomerInformation information = new CustomerInformation();
			information = Utils.getObject(information, response);
			PersonInfo personInfo = new PersonInfo();
			personInfo.setClient_name(String.valueOf(response
					.get("client_full_name")));
			personInfo
					.setId_kind_gb(String.valueOf(response.get("id_kind_gb")));
			personInfo.setId_no(String.valueOf(response.get("id_no")));
			personInfo.setAddress(String.valueOf(response.get("address")));
			personInfo.setE_mail(String.valueOf(response.get("e_mail")));
			personInfo
					.setMobile_tel(String.valueOf(response.get("mobile_tel")));
			modelAndView.addObject("personInfo", personInfo);// 个人资料
			modelAndView.addObject("information", information);// 客户信息
			return modelAndView;
		} else {
			return null;
		}

	}

	/**
	 * 
	 * @param req
	 * @param session
	 * @return
	 * @throws Exception
	 *             问卷调查内容查询
	 */
	@RequestMapping("/paperinfo_qry_acct")
	public ModelAndView paperinfo_qry_acct(HttpServletRequest req,
			HttpSession session) throws Exception {
		User user = Utils.getUser(session);
		ModelAndView modelAndView = new ModelAndView();
		request.put("trust_way", ConstantUtil.TRUST_WAY); // 交易委托方式
		request.put("query_type", "1"); // 查询类别
		request.put("ta_acco", ""); // TA帐号
		request.put("trade_acco", user.getTrade_acco_list().get(0)); // 交易账号
		request.put("paper_client_type", "0");
		url = "/paperinfo_qry_acct";
		String path = "/cwsale/v1" + url;
		List<Map<String, Object>> res = getUfxSDK().query(path, request);
		response = res.get(0);
		System.out.println(response);
		if (response.get("success_type").equals("0")) {
			String invest_risk_tolerance = String.valueOf(response
					.get("invest_risk_tolerance"));
			modelAndView.addObject("invest_risk_tolerance",
					invest_risk_tolerance);
			return modelAndView;
		} else if (response.get("success_type").equals("2")) {
			modelAndView.addObject("invest_risk_tolerance", "1");
			return modelAndView;
		} else {
			modelAndView.addObject("invest_risk_tolerance", "1");
			return modelAndView;
		}

	}

	/**
	 * 
	 * @param req
	 * @param session
	 * @return
	 * @throws Exception
	 *             修改地址
	 */
	@RequestMapping("/personalCenter_modifyAddress")
	public ModelAndView personalCenter_modifyAddress(HttpServletRequest req,
			HttpSession session) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		String address = req.getParameter("address");
		if (address != null) {
			modelAndView.addObject("address", address);
		}
		modelAndView.setViewName("personalCenter_modifyAddress");
		return modelAndView;

	}

	/**
	 * 
	 * @param req
	 * @param session
	 * @return
	 * @throws Exception
	 *             修改电子邮箱
	 */
	@RequestMapping("/personalCenter_modifymail")
	public ModelAndView personalCenter_modifymail(HttpServletRequest req,
			HttpSession session) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		String e_mail = req.getParameter("e_mail");
		if (e_mail != null) {
			modelAndView.addObject("e_mail", e_mail);
		}
		modelAndView.setViewName("personalCenter_modifymail");
		return modelAndView;

	}

	/**
	 * 
	 * @param req
	 * @param session
	 * @return
	 * @throws Exception
	 *             修改手机号
	 */
/*	@RequestMapping("/personalCenter_modifyPhone")
	public ModelAndView personalCenter_modifyPhone(HttpServletRequest req, HttpSession session) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView = clientinfo_qry_acct(req, session);
		Map<String, Object> map = modelAndView.getModel();
		PersonInfo personInfo = (PersonInfo) map.get("personInfo");
		String phone = Utils.toreplace(personInfo.getMobile_tel(), "(\\d{3})\\d*(\\d{3})", "$1*****$2");
		modelAndView.setViewName("personalCenter_modifyPhone");
		modelAndView.addObject("phone", phone);
		return modelAndView;
	}*/

	/**
	 * 
	 * @param req
	 * @param session
	 * @return
	 * @throws Exception
	 *             客户资料修改
	 */
	@RequestMapping("/clientinfo_mod_acct")
	public @ResponseBody Result clientinfo_mod_acct(HttpServletRequest req,
			HttpSession session, HttpServletResponse resp) throws Exception {
		User user = Utils.getUser(session);
		Result result = new Result();
		ModelAndView modelAndView = new ModelAndView();
		modelAndView = clientinfo_qry_acct(req, session);
		Map<String, Object> map = modelAndView.getModel();
		CustomerInformation information = (CustomerInformation) map
				.get("information");
		String address = req.getParameter("address");
		if (address != null) {
			address = URLDecoder.decode(address, "UTF-8");
		}

		if (address != null) {
			request.put("address", address);
			request.put("e_mail", information.getE_mail());
			request.put("mobile_tel", information.getMobile_tel());
		}
		String email = req.getParameter("email");
		if (email != null) {
			request.put("e_mail", email);
			request.put("mobile_tel", information.getMobile_tel());
			request.put("address", information.getAddress());

		}
		String mobilephone = req.getParameter("mobilephone");
		if (mobilephone != null) {
			request.put("mobile_tel", mobilephone);
			request.put("e_mail", information.getE_mail());
			request.put("address", information.getAddress());
		}
		request.put("trust_way", ConstantUtil.TRUST_WAY); // 交易委托方式
		request.put("trade_acco", user.getTrade_acco_list().get(0)); // 交易账号
		request.put("password", user.getPassword());
		request.put("client_full_name", information.getClient_full_name());
		request.put("client_name", information.getClient_name());
		request.put("id_kind_gb", information.getId_kind_gb());
		request.put("id_no", information.getId_no());
		request.put("trade_account_name", information.getClient_full_name());
		request.put("contact_name", information.getContact_name());
		request.put("id_no", information.getId_no());
		request.put("contract_id_kind_gb", information.getContract_id_kind_gb());
		request.put("contact_id_no", information.getContact_id_no());

		url = "/clientinfo_mod_acct";
		String path = "/cwsale/v1" + url;
		Map<String, Object> response = getUfxSDK().transact(path, request);
		System.out.println(response);
		if (response.get("success_type").equals("0")) {
			result.setResult("success");
			return result;

		} else if (response.get("success_type").equals("2")) {
			result.setResult(String.valueOf(response.get("error_info")));
			return result;
		} else {
			result.setResult(String.valueOf(response.get("error_info")));
			return result;
		}

	}

	/**
	 * 
	 * @param req
	 * @param session
	 * @return
	 * @throws Exception
	 *             密码管理
	 */
	@RequestMapping("/personalCenter_modifyPwd")
	public ModelAndView personalCenter_modifyPwd(HttpServletRequest req,
			HttpSession session) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("personalCenter_modifyPwd");
		return modelAndView;

	}

	/**
	 * 
	 * @param req
	 * @param session
	 * @return
	 * @throws Exception
	 *             交易密码修该
	 */
	@RequestMapping("/tradepassword_mod_acct")
	public @ResponseBody Result tradepassword_mod_acct(HttpServletRequest req,
			HttpSession session) throws Exception {
		Result result = new Result();
		User user = Utils.getUser(session);
		String new_password = req.getParameter("newPwd");
		String pwd = req.getParameter("pwd");
		ModelAndView modelAndView = new ModelAndView();
		request.put("trust_way", ConstantUtil.TRUST_WAY); // 交易委托方式
		HSERYCPT miTool = new HSERYCPT();
		String miText = miTool.encrypt(pwd);
		request.put("password", miText);
		request.put("trade_acco", user.getTrade_acco_list().get(0)); // 交易账号
		HSERYCPT miTool2 = new HSERYCPT();
		String miText2 = miTool2.encrypt(new_password);
		request.put("new_password", miText2);
		url = "/tradepassword_mod_acct";
		String path = "/cwsale/v1" + url;
		Map<String, Object> response = getUfxSDK().transact(path, request);
		if (response.get("success_type").equals("0")) {
			result.setResult("success");
			return result;
		} else {
			result.setResult(String.valueOf(response.get("error_info")));
			return result;
		}
	}

	@RequestMapping("/allot_trade")
	public ModelAndView allot_trade(HttpServletRequest req, HttpSession session)
			throws Exception {
		request.put("trust_way", ConstantUtil.TRUST_WAY);
		request.put("trade_acco", "666Z00000003");
		request.put("password", "469888");
		request.put("fund_code", "320101");
		request.put("bank_no", "007");
		request.put("bank_account", "6225768744121383");
		request.put("share_type", ConstantUtil.SHARE_TYPE);
		request.put("balance", "1000");
		request.put("money_type", ConstantUtil.MONEY_TYPE);
		request.put("capital_mode", ConstantUtil.CAPITAL_MODE);
		ModelAndView modelAndView = new ModelAndView();
		url = "/allot_trade";
		String path = "/cwsale/v1" + url;
		Map<String, Object> response = getUfxSDK().transact(path, request);
		System.out.println(response);
		modelAndView.setViewName("personalCenter_modifyPwd");
		return modelAndView;
	}

	/**
	 * 
	 * @param req
	 * @param session
	 * @return
	 * @throws Exception
	 *             基金信息查询
	 */
	@RequestMapping("/fund_info")
	public ModelAndView info(HttpServletRequest req, HttpSession session) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
    	String requestnum = "50";   //请求行数维护  request_num
   	 	int num=0;
		FundInfoRequ fundInfoRequ = new FundInfoRequ();
		fundInfoRequ.setTrust_way(ConstantUtil.TRUST_WAY);
		fundInfoRequ.setRequest_num("50");
		fundInfoRequ.setReqry_recordsum_flag("1");
		fundInfoRequ.setQry_beginrownum("1");
		request = Utils.ObjectToMap(fundInfoRequ);
		List<Map<String, Object>> response = getUfxSDK().query("/cwsale/v1/fundinfo_qry", request);
		List<FundInfoResp> list = new ArrayList<FundInfoResp>();
		//if (response.get(0).get("data") == null) {
		if ( "".equals(response.get(0).get("error_info")) && response.get(0).get("success_type").equals("0")) {
				for (Map<String, Object> map : response) {
					FundInfoResp fundInfoResp = new FundInfoResp();
					fundInfoResp = Utils.getObject(fundInfoResp, map);
					list.add(fundInfoResp);
					Double i = Double.parseDouble(String.valueOf(map.get("total_count")) );
					Double j = Double.parseDouble(requestnum);
					num =  (int)Math.ceil( i / j) ;	
				}
		} 
        if(num>1){  //说明需要继续查   如num=3  一共查3次，上面已经查过一次了，还需查两次
        	for(int i=1;i<num;i++){   
        		request.clear();
        		request.put("trust_way",   ConstantUtil.TRUST_WAY);
        		request.put("request_num",   requestnum);
     	        String flag = "";
     	        if(i==num-1){
     	        	flag = "0";  //0 不再统计
     	        }else{
     	        	flag = "1";  //1 还需统计
     	        }	        	        
     	      request.put("reqry_recordsum_flag",  flag);   //重新统计总记录数标志	        	             
     	      String start =  Integer.parseInt(requestnum)*i + 1 +"";   
     	      request.put("qry_beginrownum", start);           //查询起始行号	
     	     List<Map<String, Object>> responseB = getUfxSDK().query("/cwsale/v1/fundinfo_qry", request);
     		if ( "".equals(responseB.get(0).get("error_info")) && responseB.get(0).get("success_type").equals("0")) {
				for (Map<String, Object> map : responseB) {
					FundInfoResp fundInfoResp = new FundInfoResp();
					fundInfoResp = Utils.getObject(fundInfoResp, map);
					list.add(fundInfoResp);
				}
     		   }   
        	}	
        }
        return modelAndView.addObject("list", list);
	}

	@RequestMapping("/convert_trade")
	public ModelAndView convert_trade(HttpServletRequest req, HttpSession session) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		ApplyRequ apply = new ApplyRequ();
		String fund_code = req.getParameter("fundCode");
		String ta_no = "";
		String ofund_type = "";
		String fundcode = "";
		request.clear();
		request.put("trust_way", ConstantUtil.TRUST_WAY); // 恒生言：网上委托
		request.put("request_num", "1"); // 恒生言：网上委托 手机委托
		request.put("reqry_recordsum_flag", "0");
		request.put("qry_beginrownum", "1"); // 自定为227
		request.put("fund_code", fund_code);
		List<Map<String, Object>> response = getUfxSDK().query("/cwsale/v1/fundinfo_qry", request);
		FundInfoResp fundInfoResp = null;
		//if (response.get(0).get("data") == null) {
		//if ( "".equals(response.get(0).get("error_info")) && !"无记录".equals(response.get(0).get("error_info"))) {
		if ( "".equals(response.get(0).get("error_info")) && response.get(0).get("success_type").equals("0")) {
				for (Map<String, Object> map : response) {
					fundInfoResp = new FundInfoResp();
					fundInfoResp = Utils.getObject(fundInfoResp, map);
					ta_no = fundInfoResp.getTa_no();
					ofund_type = fundInfoResp.getOfund_type();
					fundcode = fundInfoResp.getFund_code();
				}
			modelAndView = info(req, session);
			Map<String, Object> map = modelAndView.getModel();
			List<FundInfoResp> listFundInfoResp = new ArrayList<FundInfoResp>();
			List<FundInfoResp> list = (List<FundInfoResp>) map.get("list");
			ModelAndView modelAndView2 = shareqry(fundcode, req, session);
			Map<String, Object> map2 = modelAndView2.getModel();
			ShareResp shareResp = (ShareResp) map2.get("shareResp");
			String trade_acco = "";
			if (shareResp != null) {
				trade_acco = shareResp.getTrade_acco();
				String bank_no = shareResp.getBank_no();
				String bank_account = shareResp.getBank_account();
				Bank bank = bankService.findBankByBankCode(bank_no);
				apply.setBank_name(bank.getBankName());
				String banknum = Utils.toreplace(bank_account, "\\d*(\\d{4})", "*****$1");
				apply.setBank_no(banknum);
				apply.setBank_account(bank_account);
				modelAndView.addObject("shareResp", shareResp);
			}
			for (int i = 0; i < list.size(); i++) {
				if (ta_no.equals(list.get(i).getTa_no()) && ("0").equals(list.get(i).getFund_status()) && !list.get(i).getFund_code().equals(fundcode)) {
					listFundInfoResp.add(list.get(i));
				}
			}
			modelAndView.addObject("listFundInfoResp", listFundInfoResp);
			modelAndView.addObject("fundInfo", fundInfoResp);
			modelAndView.addObject("apply", apply);
			modelAndView.addObject("trade_acco", trade_acco);
			modelAndView.setViewName("myAsset_ZH_step1_special");
			return modelAndView;
		} else {
			return null;
		}
	}

	@RequestMapping("/convert_trade2")
	public ModelAndView convert_trade2(HttpServletRequest req, HttpSession session) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		ApplyRequ apply = new ApplyRequ();
		String trade_acco = req.getParameter("trade_acco");
		String ZH_money = req.getParameter("ZH_money");
		String fundcode1 = req.getParameter("fundcode");
		String fundcode2 = req.getParameter("product_in");
		String bankName = req.getParameter("bankName");
		String bankNo = req.getParameter("bankNo");
		apply.setBank_name(bankName);
		apply.setBank_no(bankNo);
		FundInfoResp fundInfoResp1 = new FundInfoResp();
		FundInfoResp fundInfoResp2 =new FundInfoResp();
		//查该基金的名称
	    Map<String,Object> reqA =new LinkedHashMap<String, Object>();
	    reqA.put("trust_way", ConstantUtil.TRUST_WAY);     //交易委托方式
	    reqA.put("request_num", "1");                 //请求行数
	    reqA.put("reqry_recordsum_flag",  "0");    //重新统计总记录数标志
	    reqA.put("qry_beginrownum", "1"); //查询起始行号
	    reqA.put("fund_code", fundcode1);                     //基金代码
		List<Map<String, Object>> responseF = getUfxSDK().query("/cwsale/v1/fundinfo_qry" , reqA);
		if( "".equals(responseF.get(0).get("error_info")) ){ 
			if(Integer.parseInt(String.valueOf(responseF.get(0).get("success_type")))==0){			
				String fund_name = String.valueOf(responseF.get(0).get("fund_name"));
				fundInfoResp1.setFund_name(fund_name);
				fundInfoResp1.setFund_code(fundcode1);
			}
		}
	    Map<String,Object> reqB =new LinkedHashMap<String, Object>();
	    reqB.put("trust_way", ConstantUtil.TRUST_WAY);     //交易委托方式
	    reqB.put("request_num", "1");                 //请求行数
	    reqB.put("reqry_recordsum_flag",  "0");    //重新统计总记录数标志
	    reqB.put("qry_beginrownum", "1"); //查询起始行号
	    reqB.put("fund_code", fundcode2);                     //基金代码
		List<Map<String, Object>> responseG = getUfxSDK().query("/cwsale/v1/fundinfo_qry" , reqB);
		if( "".equals(responseG.get(0).get("error_info")) ){ 
			if(Integer.parseInt(String.valueOf(responseG.get(0).get("success_type")))==0){			
				String fund_name = String.valueOf(responseG.get(0).get("fund_name"));
				fundInfoResp2.setFund_name(fund_name);
				fundInfoResp2.setFund_code(fundcode2);
			}
		}
	//】
		modelAndView.addObject("apply", apply);
		modelAndView.addObject("trade_acco", trade_acco);
		modelAndView.addObject("ZH_money", ZH_money);
		modelAndView.addObject("fundInfoResp1", fundInfoResp1);
		modelAndView.addObject("fundInfoResp2", fundInfoResp2);
		modelAndView.setViewName("myAsset_ZH_step2");
		return modelAndView;

	}

	@RequestMapping("/convert_trade3")
	public ModelAndView convert_trade3(HttpServletRequest req, HttpSession session) throws Exception {
		ApplyRequ apply = new ApplyRequ();
		String bankName = req.getParameter("bankName");
		String bankNo = req.getParameter("bankNo");
		apply.setBank_name(bankName);
		apply.setBank_no(bankNo);
		String fund_code1 = req.getParameter("fundCode1");
		String fund_code2 = req.getParameter("fundCode2");
		String trade_acco = req.getParameter("trade_acco");
		String shares = req.getParameter("shares");
		String password = req.getParameter("password");
		ModelAndView modelAndView = new ModelAndView();
		FundInfoResp fundInfoResp1 = new FundInfoResp();
		FundInfoResp fundInfoResp2 = new FundInfoResp();
		
		//查该基金的名称
	    Map<String,Object> reqA =new LinkedHashMap<String, Object>();
	    reqA.put("trust_way", ConstantUtil.TRUST_WAY);     //交易委托方式
	    reqA.put("request_num", "1");                 //请求行数
	    reqA.put("reqry_recordsum_flag",  "0");    //重新统计总记录数标志
	    reqA.put("qry_beginrownum", "1"); //查询起始行号
	    reqA.put("fund_code", fund_code1);                     //基金代码
		List<Map<String, Object>> responseF = getUfxSDK().query("/cwsale/v1/fundinfo_qry" , reqA);
		if( "".equals(responseF.get(0).get("error_info")) ){ 
			if(Integer.parseInt(String.valueOf(responseF.get(0).get("success_type")))==0){			
				String fund_name = String.valueOf(responseF.get(0).get("fund_name"));
				fundInfoResp1.setFund_name(fund_name);
				fundInfoResp1.setFund_code(fund_code1);
			}
		}
	    Map<String,Object> reqB =new LinkedHashMap<String, Object>();
	    reqB.put("trust_way", ConstantUtil.TRUST_WAY);     //交易委托方式
	    reqB.put("request_num", "1");                 //请求行数
	    reqB.put("reqry_recordsum_flag",  "0");    //重新统计总记录数标志
	    reqB.put("qry_beginrownum", "1"); //查询起始行号
	    reqB.put("fund_code", fund_code2);                     //基金代码
		List<Map<String, Object>> responseG = getUfxSDK().query("/cwsale/v1/fundinfo_qry" , reqB);
		if( "".equals(responseG.get(0).get("error_info")) ){ 
			if(Integer.parseInt(String.valueOf(responseG.get(0).get("success_type")))==0){			
				String fund_name = String.valueOf(responseG.get(0).get("fund_name"));
				fundInfoResp2.setFund_name(fund_name);
				fundInfoResp2.setFund_code(fund_code2);
			}
		}
		
		request.put("trust_way", ConstantUtil.TRUST_WAY);
		request.put("trade_acco", trade_acco);
		HSERYCPT miTool = new HSERYCPT();
		String miText = miTool.encrypt(password);
		request.put("password", miText);
		request.put("fund_code", fund_code1);
		request.put("share_type", ConstantUtil.SHARE_TYPE);
		request.put("target_fund_code", fund_code2);
		request.put("target_share_type", "A");
		request.put("shares", shares);
		request.put("fund_exceed_flag", ConstantUtil.FUND_EXCEED_FLAG);
		url = "/convert_trade";
		String path = "/cwsale/v1" + url;
		Map<String, Object> response = getUfxSDK().transact(path, request);
		if (response.get("success_type").equals("0")) {
			modelAndView.addObject("apply", apply);
			modelAndView.addObject("fundInfoResp1", fundInfoResp1);
			modelAndView.addObject("fundInfoResp2", fundInfoResp2);
			modelAndView.addObject("shares", shares);
			modelAndView.setViewName("myAsset_ZH_step3success");
			return modelAndView;
		} else {
			modelAndView.addObject("apply", apply);
			modelAndView.addObject("fundInfoResp1", fundInfoResp1);
			modelAndView.addObject("fundInfoResp2", fundInfoResp2);
			modelAndView.addObject("ZH_money", shares);
			modelAndView.addObject("error", String.valueOf(response.get("error_info")));
			modelAndView.setViewName("myAsset_ZH_step2");
			return modelAndView;
		}
	}

	@RequestMapping("/shareqry")
	public ModelAndView shareqry(String fundcode, HttpServletRequest req, HttpSession session) throws Exception {
		User user = Utils.getUser(session);
		ShareRequ shareRequ = new ShareRequ();
		shareRequ.setRequest_num("50");
		shareRequ.setReqry_recordsum_flag("1");
		shareRequ.setQry_beginrownum("1");
		shareRequ.setClient_id(user.getClient_id());
		shareRequ.setFund_code(fundcode);
		request = Utils.ObjectToMap(shareRequ);
		ModelAndView modelAndView = new ModelAndView();
		url = "/share_qry";
		String path = "/cwsale/v1" + url;
		List<Map<String, Object>> response = getUfxSDK().query(path, request);
		ShareResp shareResp = null;
		//if (response.get(0).get("data") == null) {
		//if ( "".equals(response.get(0).get("error_info")) && !"无记录".equals(response.get(0).get("error_info"))) {
		if ( "".equals(response.get(0).get("error_info")) && response.get(0).get("success_type").equals("0")) {
				for (Map<String, Object> map : response) {
					shareResp = new ShareResp();
					shareResp = Utils.getObject(shareResp, map);
				}
			modelAndView.addObject("shareResp", shareResp);
			return modelAndView;
		} else {
			return null;
		}
	}

	@RequestMapping("/undotradeapply_trade")
	public ModelAndView undotradeapply_trade(String fundcode,HttpServletRequest req, HttpSession session) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		User user = Utils.getUser(session);
    	String requestnum = "50";   //请求行数维护  request_num
   	 	int num=0;
		
		TradequrRequ tradequr = new TradequrRequ();
		tradequr.setTrust_way(ConstantUtil.TRUST_WAY);
		tradequr.setReqry_recordsum_flag("1");
		tradequr.setQry_beginrownum("1");
		tradequr.setClient_id(user.getClient_id());
		tradequr.setRequest_num("50");
		Calendar calendar = Calendar.getInstance(); 
		 int hour=calendar.get(Calendar.HOUR_OF_DAY);//小时
		 int minute=calendar.get(Calendar.MINUTE);//分
		//撤单只能撤当天且15点以前，15点以后的话需要将日期加一再去恒生接口查可撤单的记录
		if(hour<15){ //小于15点的，就传当天的日期
			calendar.add(Calendar.DATE, 0);
			tradequr.setBegin_date(Utils.dateStrFormat(calendar.getTime(), "yyyyMMdd"));
			tradequr.setEnd_date(Utils.dateStrFormat(calendar.getTime(), "yyyyMMdd"));
		}else if((hour==15 && minute>0)||(hour>15)){  //查询日期加一   以后可能会改成下一个工作日
			calendar.add(Calendar.DATE, 1);
			tradequr.setBegin_date(Utils.dateStrFormat(calendar.getTime(), "yyyyMMdd"));
			tradequr.setEnd_date(Utils.dateStrFormat(calendar.getTime(), "yyyyMMdd"));
		}
		request = Utils.ObjectToMap(tradequr);
		url = "/trade_apply_qry";
		String path = "/cwsale/v1" + url;
		List<Map<String, Object>> response = getUfxSDK().query(path, request);
		List<TradequrResp> list = new ArrayList<TradequrResp>();
		//if (response.get(0).get("data") == null) {
		if ( "".equals(response.get(0).get("error_info")) ) {
			if (response.get(0).get("success_type").equals("0")) {
				for (Map<String, Object> map : response) {
					TradequrResp tradequrResp = new TradequrResp();
					tradequrResp = Utils.getObject(tradequrResp, map);
					if (	!tradequrResp.getTaconfirm_flag().equals("1")
							&& !tradequrResp.getTaconfirm_flag().equals("4")
							&& !tradequrResp.getFund_busin_code().equals("053")) {
						    Map<String,Object> reqA =new LinkedHashMap<String, Object>();
						    reqA.put("trust_way", "2");     //交易委托方式
						    reqA.put("request_num", "1");                 //请求行数
						    reqA.put("reqry_recordsum_flag",  "0");    //重新统计总记录数标志
						    reqA.put("qry_beginrownum", "1"); //查询起始行号
						    reqA.put("fund_code", tradequrResp.getFund_code());                     //基金代码
							List<Map<String, Object>> responseF = getUfxSDK().query("/cwsale/v1/fundinfo_qry" , reqA);
							if( "".equals(responseF.get(0).get("error_info")) ){ 
								if(Integer.parseInt(String.valueOf(responseF.get(0).get("success_type")))==0){			
									String fund_name = String.valueOf(responseF.get(0).get("fund_name"));
									tradequrResp.setFund_name(fund_name);
								}
							}
							if (tradequrResp.getFund_busin_code().equals("024")|| tradequrResp.getFund_busin_code().equals("036")) {
								tradequrResp.setBalance(tradequrResp.getShares().substring(0,tradequrResp.getShares().lastIndexOf(".")));
							} else {
								tradequrResp.setBalance(Utils.StringToString(tradequrResp.getBalance()));
							}
							list.add(tradequrResp);
					}
		        	Double i = Double.parseDouble(String.valueOf(map.get("total_count")) );
		        	Double j = Double.parseDouble(requestnum);
		        	num =  (int)Math.ceil( i / j) ;	
				}
			}
		}
		
        if(num>1){  //说明需要继续查   如num=3  一共查3次，上面已经查过一次了，还需查两次
        	for(int i=1;i<num;i++){   
        		request.put("request_num",   requestnum);
     	        String flag = "";
     	        if(i==num-1){
     	        	flag = "0";  //0 不再统计
     	        }else{
     	        	flag = "1";  //1 还需统计
     	        }	        	        
     	      request.put("reqry_recordsum_flag",  flag);   //重新统计总记录数标志	        	             
     	      String start =  Integer.parseInt(requestnum)*i + 1 +"";   
     	      request.put("qry_beginrownum", start);           //查询起始行号	
     	      List<Map<String, Object>> responseYY = getUfxSDK().query("/cwsale/v1/trade_apply_qry", request);
     			if ( "".equals(response.get(0).get("error_info")) ) {
     				if (response.get(0).get("success_type").equals("0")) {
     					for (Map<String, Object> mapB : responseYY) {
     						TradequrResp tradequrResp = new TradequrResp();
     						tradequrResp = Utils.getObject(tradequrResp, mapB);
     						if (  !tradequrResp.getTaconfirm_flag().equals("1")
     								&& !tradequrResp.getTaconfirm_flag().equals("4")
     								&& !tradequrResp.getFund_busin_code().equals("053")) {
     							    Map<String,Object> reqB =new LinkedHashMap<String, Object>();
	     							 reqB.put("trust_way", "2");     //交易委托方式
	     							 reqB.put("request_num", "1");                 //请求行数
	     							 reqB.put("reqry_recordsum_flag",  "0");    //重新统计总记录数标志
	     							 reqB.put("qry_beginrownum", "1"); //查询起始行号
	     							 reqB.put("fund_code", tradequrResp.getFund_code());                     //基金代码
     								List<Map<String, Object>> responseG = getUfxSDK().query("/cwsale/v1/fundinfo_qry" , reqB);
     								if( "".equals(responseG.get(0).get("error_info")) ){ 
     									if(Integer.parseInt(String.valueOf(responseG.get(0).get("success_type")))==0){			
     										String fund_name = String.valueOf(responseG.get(0).get("fund_name"));
     										tradequrResp.setFund_name(fund_name);
     									}
     								}
     								if (tradequrResp.getFund_busin_code().equals("024")|| tradequrResp.getFund_busin_code().equals("036")) {
     									tradequrResp.setBalance(tradequrResp.getShares().substring(0,tradequrResp.getShares().lastIndexOf(".")));
     								} else {
     									tradequrResp.setBalance(Utils.StringToString(tradequrResp.getBalance()));
     								}
     								list.add(tradequrResp);
     						}
     					}
     				}
     			}
        	}
        }
		
		modelAndView.addObject("list", list);
		modelAndView.setViewName("myAsset_CD_step1");
		return modelAndView;

	}

	@RequestMapping("/undotradeapply_trade2")
	public ModelAndView undotradeapply_trade2(HttpServletRequest req, HttpSession session) throws Exception {
		String original_appno = req.getParameter("businessType");
		String fund_name = req.getParameter("fund_name");
		ModelAndView modelAndView = new ModelAndView();
		User user = Utils.getUser(session);
		ApplyRequ apply = new ApplyRequ();
		TradequrResp tradequrResp = qry_trade_applyByallot_no(original_appno,user);
		Bank bankA = bankService.findBankByBankCode(tradequrResp.getBank_no());
		apply.setBank_name(bankA.getBankName());
		String banknum = Utils.toreplace(tradequrResp.getReceivable_account(),"\\d*(\\d{4})", "*****$1");
		apply.setBank_no(banknum);
		apply.setBank_account(tradequrResp.getReceivable_account());
		modelAndView.addObject("original_appno", original_appno);
		modelAndView.setViewName("myAsset_CD_step2");
		if (tradequrResp != null) {
					tradequrResp.setFund_name(fund_name);
					if (tradequrResp.getFund_busin_code().equals("024")|| tradequrResp.getFund_busin_code().equals("036")) {
						tradequrResp.setBalance(tradequrResp.getShares());
					} else {
						tradequrResp.setBalance(Utils.StringToString(tradequrResp.getBalance()));
					}
					String date = tradequrResp.getApply_date();
					SimpleDateFormat sFormat1 = new SimpleDateFormat("yyyyMMdd");
					Date sdate = sFormat1.parse(date);
					SimpleDateFormat sFormat2 = new SimpleDateFormat("yyyy-MM-dd");
					String datetime = sFormat2.format(sdate);
					tradequrResp.setApply_date(datetime);
					modelAndView.addObject("tradequrResp", tradequrResp);
		} 
		modelAndView.addObject("apply", apply);
		return modelAndView;
	}

	public TradequrResp qry_trade_applyByallot_no(String original_appno,User user) {
		TradequrRequ tradequr = new TradequrRequ();
		tradequr.setTrust_way(ConstantUtil.TRUST_WAY);
		tradequr.setReqry_recordsum_flag("0");
		tradequr.setQry_beginrownum("1");
		tradequr.setClient_id(user.getClient_id());
		tradequr.setRequest_num("1");
		tradequr.setAllot_no(original_appno);
		request = Utils.ObjectToMap(tradequr);
		url = "/trade_apply_qry";
		String path = "/cwsale/v1" + url;
		List<Map<String, Object>> response = getUfxSDK().query(path, request);
		//if (response.get(0).get("data") == null) {
		if ( "".equals(response.get(0).get("error_info")) ) {
			if (response.get(0).get("success_type").equals("0")) {
				for (Map<String, Object> map : response) {
					TradequrResp tradequrResp = new TradequrResp();
					tradequrResp = Utils.getObject(tradequrResp, map);
					return tradequrResp;
				}
			}
		}
		return null;
	}

	@RequestMapping("/undotradeapply_trade3")
	public ModelAndView undotradeapply_trade3(TradequrResp tradequrResp, HttpServletRequest req, HttpSession session) throws Exception {
		User user = Utils.getUser(session);
		ModelAndView modelAndView = new ModelAndView();
		String original_appno = req.getParameter("original_appno");
		String fund_name = req.getParameter("fund_name");
		String password = req.getParameter("pwd");
		ApplyRequ apply = new ApplyRequ();
		TradequrResp tradequrRespB = qry_trade_applyByallot_no(original_appno,user);
		Bank bankA = bankService.findBankByBankCode(tradequrRespB.getBank_no());
		apply.setBank_name(bankA.getBankName());
		String banknumB = Utils.toreplace(tradequrRespB.getReceivable_account(),"\\d*(\\d{4})", "*****$1");
		apply.setBank_no(banknumB);
		apply.setBank_account(tradequrRespB.getReceivable_account());
		//撤单时得传当时购买时的交易账号
		String trade_acco = tradequrRespB.getTrade_acco();
		request.clear();
		request.put("trust_way", ConstantUtil.TRUST_WAY);
		request.put("trade_acco", trade_acco);
        HSERYCPT miTool = new HSERYCPT();
		String miText = miTool.encrypt(password);
        request.put("password", miText);
		request.put("original_appno", original_appno);
		url = "/undotradeapply_trade";
		String path = "/cwsale/v1" + url;
		Map<String, Object> response = getUfxSDK().transact(path, request);
		if (response.get("success_type").equals("0")) {
			tradequrResp = qry_trade_applyByallot_no(original_appno, user);
			if (tradequrResp != null) {
						tradequrResp.setFund_name(fund_name);
						if (tradequrResp.getFund_busin_code().equals("024")|| tradequrResp.getFund_busin_code().equals("036")) {
							tradequrResp.setBalance(tradequrResp.getShares());
						} else {
							tradequrResp.setBalance(Utils.StringToString(tradequrResp.getBalance()));
						}
						String date = tradequrResp.getApply_date();
						SimpleDateFormat sFormat1 = new SimpleDateFormat("yyyyMMdd");
						Date sdate = sFormat1.parse(date);
						SimpleDateFormat sFormat2 = new SimpleDateFormat("yyyy-MM-dd");
						String datetime = sFormat2.format(sdate);
						tradequrResp.setApply_date(datetime);
						modelAndView.addObject("tradequrResp", tradequrResp);
				modelAndView.setViewName("myAsset_CD_step3success");
				modelAndView.addObject("apply", apply);
				return modelAndView;
			} else {
				modelAndView.setViewName("myAsset_CD_step3success");
				modelAndView.addObject("apply", apply);
				return modelAndView;
			}
		}
		modelAndView.setViewName("myAsset_CD_step2");
		modelAndView.addObject("tradequrResp", tradequrResp);
		modelAndView.addObject("apply", apply);
		modelAndView.addObject("original_appno", original_appno);
		modelAndView.addObject("error", String.valueOf(response.get("error_info")));
		return modelAndView;

	}

	@RequestMapping("/newhq_qry")
	public ModelAndView newhq_qry(HttpServletRequest req, HttpSession session)
			throws Exception {
		User user = Utils.getUser(session);
		request.put("trust_way", ConstantUtil.TRUST_WAY);
		request.put("request_num", "50");
		request.put("qry_beginrownum", "1");

		request.put("trade_acco", user.getTrade_acco_list().get(0));
		url = "/newhq_qry";
		String path = "/cwsale/v1" + url;
		List<Map<String, Object>> response = getUfxSDK().query(path, request);
		System.out.println(response);
		//if (response.get(0).get("data") == null) {
		if ( "".equals(response.get(0).get("error_info"))) {

		}
		return null;
	}
	
}
