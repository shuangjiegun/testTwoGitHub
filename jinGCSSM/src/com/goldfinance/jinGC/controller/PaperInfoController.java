package com.goldfinance.jinGC.controller;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.goldfinance.jinGC.common.ConstantUtil;
import com.goldfinance.jinGC.common.PrintUtil;
import com.goldfinance.jinGC.common.UfxSDKCommon;
import com.goldfinance.jinGC.entity.AccountEmail;
import com.goldfinance.jinGC.entity.AccountMessage;
import com.goldfinance.jinGC.entity.MyAsset;
import com.goldfinance.jinGC.entity.PaperResult;
import com.goldfinance.jinGC.entity.PersonInfo;
import com.goldfinance.jinGC.entity.TradeEmail;
import com.goldfinance.jinGC.entity.TradeMessage;
import com.goldfinance.jinGC.entity.User;
import com.goldfinance.jinGC.entity.UserAndFund;
import com.goldfinance.jinGC.po.PaperInfo;
import com.goldfinance.jinGC.po.PaperInfoSection;
import com.goldfinance.jinGC.poOra.FundOverviewExtend;
import com.goldfinance.jinGC.service.EmailService;
import com.goldfinance.jinGC.service.FundProductService;
import com.goldfinance.jinGC.service.LogService;
import com.goldfinance.jinGC.service.SmsPortal;
import com.goldfinance.jinGC.service.UserInfoService;

@Controller
public class PaperInfoController extends UfxSDKCommon {
	   private Map<String, Object> request = new LinkedHashMap<String, Object>();
	    private String url ;
	    
	    @Autowired
	    private UserInfoService userInfoService;
	    @Autowired
	    private LogService logService;
	    @Autowired
	    private FundProductService fundProductService;
	    
		@Autowired
		private EmailService emailService;
		
		@Autowired
		private SmsPortal smsPortal;  
		
	    
		@RequestMapping("/prePaperInfoList")
		public ModelAndView prePaperInfoList() throws Exception{
			ModelAndView modelAndView = new ModelAndView();
			String requestnum = "40"; 
			 int num=0;
			 String rowcount = "";
			 String total_count = "";
	        request.put("trust_way",   "2");     //交易委托方式
	        request.put("request_num",  requestnum);        //请求行数
	        request.put("reqry_recordsum_flag",  "1");   //重新统计总记录数标志
	        request.put("qry_beginrownum","1");           //查询起始行号
	        request.put("question_no","");                //问题编号，这么写每次清空
	        request.put("answer_object",  "1");          //回答对象  0机构；1个人
			
	        //查题目
	        List<Map<String, Object>> response = getUfxSDK().query("/cwsale/v1/paperinfo_question_qry" , request);
	        
	        List<PaperInfo> paperInfoList = new ArrayList<PaperInfo>();
	        List<PaperInfoSection> paperInfoSectionList = new ArrayList<PaperInfoSection>();
	        List<String> questionNoList = new ArrayList<String>();
	        for(Map<String, Object> info : response){	        	
	        	PaperInfo paperInfo = new PaperInfo();
	        	paperInfo.setAnswer_object("1");
	        	paperInfo.setQuestion_content(String.valueOf(info.get("question_content")));
	        	paperInfo.setQuestion_no(String.valueOf(info.get("question_no")));
	        	paperInfo.setRowcount(String.valueOf(info.get("rowcount")));  
	        	paperInfo.setTotal_count(String.valueOf(info.get("total_count")));  
	        	total_count = String.valueOf(info.get("total_count"));
	        	questionNoList.add(String.valueOf(info.get("question_no")));
	        	Double i = Double.parseDouble(String.valueOf(info.get("total_count")) );
	        	rowcount = String.valueOf(info.get("rowcount"));
	        	Double j = Double.parseDouble(rowcount);
	        	num =  (int)Math.ceil( i / j) ;	
	        	
	            request.put("trust_way",   "2");     //交易委托方式
	            request.put("request_num",   requestnum);        //请求行数
	            request.put("question_no",info.get("question_no"));                //问题编号
	        	
		        //查题目的选项
		        List<Map<String, Object>> responseQuestionSection = getUfxSDK().query("/cwsale/v1/paperinfo_question_section_qry" , request);
	        		for(Map<String, Object> section : responseQuestionSection){
	        			
	        			PaperInfoSection paperInfoSection = new PaperInfoSection();
	        			paperInfoSection.setOption_content(String.valueOf(section.get("option_content")));  
	        			paperInfoSection.setOption_no(String.valueOf(section.get("option_no"))); 
	        			paperInfoSection.setOption_score(String.valueOf(section.get("option_score")));
	        			paperInfoSection.setQuestion_no(String.valueOf(section.get("question_no")));
	        			paperInfoSection.setRowcount(String.valueOf(section.get("rowcount")));
	        			paperInfoSection.setTotal_count(String.valueOf(section.get("total_count")));
	        			paperInfoSectionList.add(paperInfoSection);
	        		}
	        		paperInfo.setPaperInfoSectionList(paperInfoSectionList);
	        		paperInfoList.add(paperInfo);
	        }
	        
       	 if(num>1){   //说明需要继续查   如num=3  一共查3次，上面已经查过一次了，还需查两次
      		for(int i=1;i<num;i++){ 
      				   request.clear();
	         	       request.put("trust_way",   "2");     //交易委托方式
	         	       request.put("request_num",   requestnum); //请求行数 
	         	        String flag = "";
	         	        if(i==num-1){
	         	        	flag = "0";  //0 不再统计
	         	        }else{
	         	        	flag = "1";  //1 还需统计
	         	        }	        	        
	         	        request.put("reqry_recordsum_flag",  flag);   //重新统计总记录数标志	        	        
	         	       String start =  Integer.parseInt(rowcount)*i + 1 +"";   
	         	        request.put("qry_beginrownum", start);           //查询起始行号	     
	         	        request.put("answer_object",  "1");          //回答对象  0机构；1个人
	         	        List<Map<String, Object>> responseYY = getUfxSDK().query("/cwsale/v1/paperinfo_question_qry" , request);
	         	        for(Map<String, Object> info : responseYY){
	         	        	PaperInfo paperInfo = new PaperInfo();
	        	        	paperInfo.setAnswer_object("1");
	        	        	paperInfo.setQuestion_content(String.valueOf(info.get("question_content")));
	        	        	paperInfo.setQuestion_no(String.valueOf(info.get("question_no")));
	        	        	paperInfo.setRowcount(String.valueOf(info.get("rowcount")));  
	        	        	paperInfo.setTotal_count(String.valueOf(info.get("total_count")));  
	        	        	questionNoList.add(String.valueOf(info.get("question_no")));
	        	        	request.clear();
	        	            request.put("trust_way",   "2");     //交易委托方式
	        	            request.put("request_num",   requestnum);        //请求行数
	        	            request.put("question_no",info.get("question_no"));                //问题编号
	        	        	
	        		        //查题目的选项
	        		        List<Map<String, Object>> responseQuestionSection = getUfxSDK().query("/cwsale/v1/paperinfo_question_section_qry" , request);
	        	        		for(Map<String, Object> section : responseQuestionSection){
	        	        			PaperInfoSection paperInfoSection = new PaperInfoSection();
	        	        			paperInfoSection.setOption_content(String.valueOf(section.get("option_content")));  
	        	        			paperInfoSection.setOption_no(String.valueOf(section.get("option_no"))); 
	        	        			paperInfoSection.setOption_score(String.valueOf(section.get("option_score")));
	        	        			paperInfoSection.setQuestion_no(String.valueOf(section.get("question_no")));
	        	        			paperInfoSection.setRowcount(String.valueOf(section.get("rowcount")));
	        	        			paperInfoSection.setTotal_count(String.valueOf(section.get("total_count")));
	        	        			paperInfoSectionList.add(paperInfoSection);
	        	        		}
	        	        		paperInfo.setPaperInfoSectionList(paperInfoSectionList);
	        	        		paperInfoList.add(paperInfo);	    
	         	        }
      		}
      }
	        
	        modelAndView.addObject("questionNoList", questionNoList);
       	 	modelAndView.addObject("total_count", total_count);
	        modelAndView.addObject("paperInfoList", paperInfoList);
			modelAndView.setViewName("risk_test");   
			return modelAndView;
		}

	    //风险评测
		@RequestMapping("/riskEvaluation")
		public ModelAndView riskEvaluation(PaperInfo paperInfo, HttpSession session) throws Exception{
			ModelAndView modelAndView = new ModelAndView();
			StringBuffer elig_content= new StringBuffer();
			for(int i=1; i<= 30; i++){
				switch(i)
				{ 
					case 1:
						if(paperInfo.getQuestion_1()!=null){
							elig_content.append(paperInfo.getQuestion_1());
							elig_content.append("|");
						}
						break;
					case 2:
						if(paperInfo.getQuestion_2()!=null){
							elig_content.append(paperInfo.getQuestion_2());
							elig_content.append("|");
						}
						break;
					case 3:
						if(paperInfo.getQuestion_3()!=null){
							elig_content.append(paperInfo.getQuestion_3());
							elig_content.append("|");
						}
						break;
					case 4:
						if(paperInfo.getQuestion_4()!=null){
							elig_content.append(paperInfo.getQuestion_4());
							elig_content.append("|");
						}
						break;
					case 5:
						if(paperInfo.getQuestion_5()!=null){
							elig_content.append(paperInfo.getQuestion_5());
							elig_content.append("|");
						}
						break;
					case 6:
						if(paperInfo.getQuestion_6()!=null){
							elig_content.append(paperInfo.getQuestion_6());
							elig_content.append("|");
						}
						break;
					case 7:
						if(paperInfo.getQuestion_7()!=null){
							elig_content.append(paperInfo.getQuestion_7());
							elig_content.append("|");
						}
						break;
					case 8:
						if(paperInfo.getQuestion_8()!=null){
							elig_content.append(paperInfo.getQuestion_8());
							elig_content.append("|");
						}
						break;
					case 9:
						if(paperInfo.getQuestion_9()!=null){
							elig_content.append(paperInfo.getQuestion_9());
							elig_content.append("|");
						}
						break;
					case 10:
						if(paperInfo.getQuestion_10()!=null){
							elig_content.append(paperInfo.getQuestion_10());
							elig_content.append("|");
						}
						break;
					case 11:
						if(paperInfo.getQuestion_11()!=null){
							elig_content.append(paperInfo.getQuestion_11());
							elig_content.append("|");
						}
						break;
					case 12:
						if(paperInfo.getQuestion_12()!=null){
							elig_content.append(paperInfo.getQuestion_12());
							elig_content.append("|");
						}
						break;
					case 13:
						if(paperInfo.getQuestion_13()!=null){
							elig_content.append(paperInfo.getQuestion_13());
							elig_content.append("|");
						}
						break;
					case 14:
						if(paperInfo.getQuestion_14()!=null){
							elig_content.append(paperInfo.getQuestion_14());
							elig_content.append("|");
						}
						break;
					case 15:
						if(paperInfo.getQuestion_15()!=null){
							elig_content.append(paperInfo.getQuestion_15());
							elig_content.append("|");
						}
						break;
					case 16:
						if(paperInfo.getQuestion_16()!=null){
							elig_content.append(paperInfo.getQuestion_16());
							elig_content.append("|");
						}
						break;
					case 17:
						if(paperInfo.getQuestion_17()!=null){
							elig_content.append(paperInfo.getQuestion_17());
							elig_content.append("|");
						}
						break;
					case 18:
						if(paperInfo.getQuestion_18()!=null){
							elig_content.append(paperInfo.getQuestion_18());
							elig_content.append("|");
						}
						break;
					case 19:
						if(paperInfo.getQuestion_19()!=null){
							elig_content.append(paperInfo.getQuestion_19());
							elig_content.append("|");
						}
						break;
					case 20:
						if(paperInfo.getQuestion_20()!=null){
							elig_content.append(paperInfo.getQuestion_20());
							elig_content.append("|");
						}
						break;
					case 21:
						if(paperInfo.getQuestion_21()!=null){
							elig_content.append(paperInfo.getQuestion_21());
							elig_content.append("|");
						}
						break;
					case 22:
						if(paperInfo.getQuestion_22()!=null){
							elig_content.append(paperInfo.getQuestion_22());
							elig_content.append("|");
						}
						break;
					case 23:
						if(paperInfo.getQuestion_23()!=null){
							elig_content.append(paperInfo.getQuestion_23());
							elig_content.append("|");
						}
						break;
					case 24:
						if(paperInfo.getQuestion_24()!=null){
							elig_content.append(paperInfo.getQuestion_24());
							elig_content.append("|");
						}
						break;
					case 25:
						if(paperInfo.getQuestion_25()!=null){
							elig_content.append(paperInfo.getQuestion_25());
							elig_content.append("|");
						}
						break;
					case 26:
						if(paperInfo.getQuestion_26()!=null){
							elig_content.append(paperInfo.getQuestion_26());
							elig_content.append("|");
						}
						break;
					case 27:
						if(paperInfo.getQuestion_27()!=null){
							elig_content.append(paperInfo.getQuestion_27());
							elig_content.append("|");
						}
						break;
					case 28:
						if(paperInfo.getQuestion_28()!=null){
							elig_content.append(paperInfo.getQuestion_28());
							elig_content.append("|");
						}
						break;
					case 29:
						if(paperInfo.getQuestion_29()!=null){
							elig_content.append(paperInfo.getQuestion_29());
							elig_content.append("|");
						}
						break;
					case 30:
						if(paperInfo.getQuestion_30()!=null){
							elig_content.append(paperInfo.getQuestion_30());
							elig_content.append("|");
						}
						break;
					default:
						System.out.println("出错啦！");
				}
				}
				
			for(int i=96; i<= 108; i++){
				switch(i)
				{ 
					case 96:
						if(paperInfo.getQuestion_96()!=null){
							elig_content.append(paperInfo.getQuestion_96());
							elig_content.append("|");
						}
						break;
					case 97:
						if(paperInfo.getQuestion_97()!=null){
							elig_content.append(paperInfo.getQuestion_97());
							elig_content.append("|");
						}
						break;
					case 98:
						if(paperInfo.getQuestion_98()!=null){
							elig_content.append(paperInfo.getQuestion_98());
							elig_content.append("|");
						}
						break;
					case 99:
						if(paperInfo.getQuestion_99()!=null){
							elig_content.append(paperInfo.getQuestion_99());
							elig_content.append("|");
						}
						break;
					case 100:
						if(paperInfo.getQuestion_100()!=null){
							elig_content.append(paperInfo.getQuestion_100());
							elig_content.append("|");
						}
						break;
					case 101:
						if(paperInfo.getQuestion_101()!=null){
							elig_content.append(paperInfo.getQuestion_101());
							elig_content.append("|");
						}
						break;
					case 102:
						if(paperInfo.getQuestion_102()!=null){
							elig_content.append(paperInfo.getQuestion_102());
							elig_content.append("|");
						}
						break;
					case 103:
						if(paperInfo.getQuestion_103()!=null){
							elig_content.append(paperInfo.getQuestion_103());
							elig_content.append("|");
						}
						break;
					case 104:
						if(paperInfo.getQuestion_104()!=null){
							elig_content.append(paperInfo.getQuestion_104());
							elig_content.append("|");
						}
						break;
					case 105:
						if(paperInfo.getQuestion_105()!=null){
							elig_content.append(paperInfo.getQuestion_105());
							elig_content.append("|");
						}
						break;
					case 106:
						if(paperInfo.getQuestion_106()!=null){
							elig_content.append(paperInfo.getQuestion_106());
							elig_content.append("|");
						}
						break;
					case 107:
						if(paperInfo.getQuestion_107()!=null){
							elig_content.append(paperInfo.getQuestion_107());
							elig_content.append("|");
						}
						break;
					case 108:
						if(paperInfo.getQuestion_108()!=null){
							elig_content.append(paperInfo.getQuestion_108());
							elig_content.append("|");
						}
						break;
				   }
				}
	        User user = (User) session.getAttribute("user");
	        request.put("trust_way",   "2");     //交易委托方式
	        request.put("cust_type",user.getCust_type());                  //客户类别
	        request.put("full_name", user.getUserName());                 //账户全称
	        request.put("id_kind_gb",user.getId_kind_gb());                 //证件类别
	        request.put("id_no",user.getId_no());                       //证件号码
	        request.put("elig_content",elig_content);    //题目和选项
	        request.put("paper_client_type","0");         //问卷客户类别
	        Map<String, Object> response = getUfxSDK().transact("/cwsale/v1/paperinfo_add_acct", request);
	        PrintUtil.printResponse(response);  //【未完待续：测评不成功的处理
	        logService.insertLog(user.getId_no(), "风险评测", "", "评测成功", "invest_risk_tolerance值为 " +response.get("invest_risk_tolerance"));
	        modelAndView.addObject("invest_risk_tolerance", response.get("invest_risk_tolerance"));
			modelAndView.setViewName("risk_result");   //mysuccess
			return modelAndView;
		}
		
		/**
		 * Ajax请求：问卷调查内容查询，查询有无做过风险评测
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("/paperinfoQryAcct")
		public @ResponseBody PaperResult paperinfoQryAcct(HttpSession session) throws Exception{
			User user = (User) session.getAttribute("user");
			String result = "";
			String full_name = "";
			String id_no = "";
			String password = "";
						
	        if(user!=null){
	        	full_name = user.getUserName();
	        	id_no = user.getId_no();
	        	password = user.getPassword();
	        }
	        request.put("trust_way",   "2");     //交易委托方式
	        request.put("query_type", "2");        //查询类别
	        request.put("cust_type","1");                  //客户类别
	        request.put("full_name", full_name);                 //账户全称
	        request.put("id_kind_gb","0");                 //证件类别
	        request.put("id_no",id_no);                       //证件号码
	        request.put("password",password);                    //密码
	        request.put("paper_client_type","0");         //问卷客户类别
	        List<Map<String, Object>> response = getUfxSDK().query("/cwsale/v1/paperinfo_qry_acct" , request);
	        PaperResult pr = new PaperResult();
		        if(response.get(0).get("data")==null ){  //这里需要返回接口的信息，不管是啥，所以判断方法不一样
		        //if( "".equals(response.get(0).get("error_info")) && !"无记录".equals(response.get(0).get("error_info"))){
		            for(Map<String, Object> info : response){
		            String	success_type = String.valueOf(info.get("success_type"));		           
		            String	error_info = String.valueOf(info.get("error_info"));	
		            pr.setSuccessType(success_type);
		            pr.setErrorInfo(error_info);  
		            }
		        }
			return pr;
		}

		/**
		 * 用户风险承受能力与基金风险等级对应性关系
		 * @param session
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("/userAndFundRiskLevelRelation")
		public @ResponseBody UserAndFund userAndFundRiskLevelRelation(String secuCode, String fundName, HttpSession session) throws Exception{
			User user = (User) session.getAttribute("user");
			String fund_Name =URLDecoder.decode(fundName,"UTF-8");
			String full_name = "";
			String id_no = "";
			String password = "";
			int invest_risk_tolerance=1;  //用户等级 (默认1)
			String invest_risk_tolerance_name="";
			//int risk_evaluation = 10;    //基金等级(默认10)  3低，7较低，10中，15较高，20高(3对1, 7对2, 10对3, 15对4, 20对5)
			//String risk_evaluation_name ="默认中";
			int risk_evaluation = 0; //【舍弃聚源，用恒生
			String risk_evaluation_name ="低风险";
			int flag = 0;
			int onOff = 0;
	        if(user!=null){
	        	full_name = user.getUserName();
	        	id_no = user.getId_no();
	        	password = user.getPassword();
	        }
	        request.put("trust_way",   "2");     //交易委托方式
	        request.put("query_type", "2");        //查询类别
	        request.put("cust_type","1");                  //客户类别
	        request.put("full_name", full_name);                 //账户全称
	        request.put("id_kind_gb","0");                 //证件类别
	        request.put("id_no",id_no);                       //证件号码
	        request.put("password",password);                    //密码
	        request.put("paper_client_type","0");         //问卷客户类别
	        List<Map<String, Object>> response = getUfxSDK().query("/cwsale/v1/paperinfo_qry_acct" , request);
	        UserAndFund uf = new UserAndFund();
		        //if(response.get(0).get("data")==null && "".equals(response.get(0).get("error_info"))){
		        if( "".equals(response.get(0).get("error_info"))){
		        	//问卷调查内容查询 接口 只返回最新的一条记录
		        	Map<String, Object> info = response.get(0);

		        	if(String.valueOf(info.get("success_type")).equals("2")  && String.valueOf(info.get("error_code")).equals("2506")){ //说明用户没做过风险评测，默认为保守型
		        		invest_risk_tolerance = 1;		        		
		        	}else{
		        		invest_risk_tolerance = Integer.parseInt(String.valueOf(info.get("invest_risk_tolerance")));
		        	}
		        	
		        	if(invest_risk_tolerance ==1){
		        		invest_risk_tolerance_name = "保守型";
		        	}else if(invest_risk_tolerance ==2){
		        		invest_risk_tolerance_name = "稳健型";
		        	}else if(invest_risk_tolerance ==3){
		        		invest_risk_tolerance_name = "激进型";
		        	}else{
		        		invest_risk_tolerance_name = "未定义";
		        	}
		        	
					String ofund_risklevel = "";
					int intofund_risklevel = 0 ;
					String riskEvaluationName = "";
					    request =new LinkedHashMap<String, Object>();
				        request.put("request_num", "10");                 //请求行数
				        request.put("reqry_recordsum_flag", "0");    //重新统计总记录数标志
				        request.put("qry_beginrownum","1"); //查询起始行号
				        request.put("fund_code", secuCode);                     //基金代码
				        List<Map<String, Object>> responseY = getUfxSDK().query("/cwsale/v1/newhq_qry" , request);
				       // if(responseY.get(0).get("data")==null){
				        if( "".equals(responseY.get(0).get("error_info"))){
				        	ofund_risklevel = (String) responseY.get(0).get("ofund_risklevel");
				        	intofund_risklevel = Integer.parseInt(ofund_risklevel);
				        	if(ofund_risklevel.equals("0")){
				        		riskEvaluationName = "低风险";
				        	}else if(ofund_risklevel.equals("1")){
				        		riskEvaluationName = "中风险";
				        	}else if(ofund_risklevel.equals("2")){
				        		riskEvaluationName = "高风险";
				        	}
				        	risk_evaluation = 	intofund_risklevel;
				        	risk_evaluation_name = riskEvaluationName;
					if((risk_evaluation-1)>invest_risk_tolerance){ //如果基金风险等级高于用户评测等级，将开关打开，在页面给与提示
						onOff=1;						
					}
					uf.setOnOff(onOff);
					uf.setInvest_risk_tolerance(invest_risk_tolerance);
					uf.setInvest_risk_tolerance_name(invest_risk_tolerance_name);
					uf.setRisk_evaluation(risk_evaluation);
					uf.setRisk_evaluation_name(risk_evaluation_name);
					uf.setFundName(fund_Name);
				        }
		        }
			return uf;
		}
		
		/**
		 * 正常请求，查询用户问卷调查情况
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("/paperinfoQryAcctNormal")
		public ModelAndView paperinfoQryAcctNormal(HttpSession session) throws Exception{
			ModelAndView modelAndView = new ModelAndView();
			User user = (User) session.getAttribute("user");
			String result = "";
			String full_name = "";
			String id_no = "";
			String password = "";
						
	        if(user!=null){
	        	full_name = user.getUserName();
	        	id_no = user.getId_no();
	        	password = user.getPassword();
	        }
	        request.put("trust_way",   "2");     //交易委托方式
	        request.put("query_type", "2");        //查询类别
	        request.put("cust_type","1");                  //客户类别
	        request.put("full_name", full_name);    //账户全称
	        request.put("id_kind_gb","0");                 //证件类别
	        request.put("id_no", id_no);                       //证件号码
	        request.put("password",password);                    //密码
	        request.put("paper_client_type","0");         //问卷客户类别
	        List<Map<String, Object>> response = getUfxSDK().query("/cwsale/v1/paperinfo_qry_acct" , request);
	        PaperResult pr = new PaperResult();
		        //if(response.get(0).get("data")==null && "".equals(response.get(0).get("error_info"))){
		        if( "".equals(response.get(0).get("error_info"))){
		        	Map<String, Object> infomap = response.get(0);
		            String	success_type = String.valueOf(infomap.get("success_type"));		           
		            String	invest_risk_tolerance = String.valueOf(infomap.get("invest_risk_tolerance"));	
		            if(success_type.equals("0")){//返回0说明做过测评了
		            	pr.setInvest_risk_tolerance(invest_risk_tolerance);
		            	if(invest_risk_tolerance.equals("1")){
		            		pr.setInvest_risk_tolerance_name("保守型");	
		            	}else if(invest_risk_tolerance.equals("2")){
		            		pr.setInvest_risk_tolerance_name("稳健型");	
		            	}else if(invest_risk_tolerance.equals("3")){
		            		pr.setInvest_risk_tolerance_name("激进型");	
		            	}else{
		            		pr.setInvest_risk_tolerance_name("未定义");
		            	}
		            }
		            pr.setSuccessType(success_type); //页面可以根据success_type值判断有无做过测评		            
		        }else if(String.valueOf(response.get(0).get("error_info")).indexOf("投资者没")!=-1){
		        	String	success_type = String.valueOf(response.get(0).get("success_type"));
		        	pr.setSuccessType(success_type); //页面可以根据success_type值判断有无做过测评		
		        }

	        modelAndView.addObject("pr", pr);
			modelAndView.setViewName("risk_index");
			return modelAndView;
		}
		
		/**
		 * 账户类去邮件发送页面
		 * @param session
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("/accountEmailFirst")
		public ModelAndView accountEmailFirst(HttpSession session) throws Exception{
			ModelAndView modelAndView = new ModelAndView();
			List emailUsefulList = new ArrayList(); 
	        modelAndView.addObject("emailUsefulList", emailUsefulList);
			modelAndView.setViewName("accountEmail");
			return modelAndView;
		}
		
		/**
		 * 账户类邮件发送页面查询按钮
		 * @param session
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("/accountEmailMsgQuery")
		public ModelAndView accountEmailMsgQuery(String applyDate) throws Exception{
			String rawDate = applyDate;
			String qryDate = applyDate;
			ModelAndView modelAndView = new ModelAndView();
			qryDate = qryDate.replace("-", "");
			//账户申请查询
			List<AccountEmail> emailTotalList = new ArrayList<AccountEmail>(); //所有人的记录
			List emailUsefulList = new ArrayList(); 
			//查询AccountEmail有无qryDate日期的任何记录，不要求email非空，没有的话从恒生接口再查并存入字表；注意下面几个方法email非空为必要条件
			//SELECT * FROM accountemail WHERE  apply_date='20150729'
			emailTotalList = userInfoService.findAccountEmailRecordList(qryDate);
			//如果以前查询过，先将数据库中的数据删除，重新查
			if(emailTotalList.size()>0){
				userInfoService.deleteAccountEmailRecordList(qryDate);
			}
				int num=0;
				request.clear();
				String requestnum = "50"; 
		        request.put("trust_way",   "2");     //交易委托方式
		        request.put("request_num",   requestnum);        //请求行数
		        request.put("reqry_recordsum_flag",  "1");   //重新统计总记录数标志
		        request.put("qry_beginrownum","1");           //查询起始行号
		        request.put("begin_date",qryDate);        //20150922   
		        request.put("end_date",qryDate);   
		        List<Map<String, Object>> response = getUfxSDK().query("/cwsale/v1/account_apply_qry" , request);
		        //if(response.get(0).get("data")==null && "".equals(response.get(0).get("error_info"))){
		        if( "".equals(response.get(0).get("error_info"))){
		        	for(Map<String, Object> info : response){ 
		        		AccountEmail email = new AccountEmail();
		        		email.setClient_name(String.valueOf(info.get("client_name"))); 
		        		email.setTa_acco(String.valueOf(info.get("ta_acco")));
		        		email.setApply_date(Integer.parseInt(String.valueOf(info.get("apply_date"))));
		        		email.setApply_time(Integer.parseInt(String.valueOf(info.get("apply_time"))));
		        		email.setId_no(String.valueOf(info.get("id_no")));
		        		email.setE_mail(String.valueOf(info.get("e_mail")));
		        		email.setTrade_acco(String.valueOf(info.get("trade_acco")));
		        		email.setFund_busin_code(String.valueOf(info.get("fund_busin_code")));
		        		String str = String.valueOf(info.get("taconfirm_flag"));
		        		String strZh = "";
		        		if("0".equals(str)){
		        			strZh = "确认失败";
		        		}else if("1".equals(str)){
		        			strZh = "确认成功";
		        		}else if("2".equals(str)){
		        			strZh = "部分确认";
		        		}else if("3".equals(str)){
		        			strZh = "实时确认成功";
		        		}else if("4".equals(str)){
		        			strZh = "撤单";
		        		}else if("5".equals(str)){
		        			strZh = "行为确认";
		        		}else if("9".equals(str)){
		        			strZh = "未确认";
		        		}
		        		email.setTaconfirm_flag(strZh);
		        		userInfoService.insertAccountEmail(email);
		        		Double i = Double.parseDouble(String.valueOf(info.get("total_count")) );
			        	Double j = Double.parseDouble(requestnum);
			        	num =  (int)Math.ceil( i / j) ;
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
	         	      List<Map<String, Object>> responseYY = getUfxSDK().query("/cwsale/v1/account_apply_qry", request);
		         	  //if(responseYY.get(0).get("data")==null){    //能进这里说明有内容并正常返回
		         	if( "".equals(responseYY.get(0).get("error_info"))){    //能进这里说明有内容并正常返回
		         	       		for(Map<String, Object> info : responseYY){  //页面只展示有可用份额的基金
				    	        		AccountEmail email = new AccountEmail();
				    	        		email.setClient_name(String.valueOf(info.get("client_name"))); 
				    	        		email.setTa_acco(String.valueOf(info.get("ta_acco")));
				    	        		email.setApply_date(Integer.parseInt(String.valueOf(info.get("apply_date"))));
				    	        		email.setApply_time(Integer.parseInt(String.valueOf(info.get("apply_time"))));
				    	        		email.setId_no(String.valueOf(info.get("id_no")));
				    	        		email.setE_mail(String.valueOf(info.get("e_mail")));
				    	        		email.setTrade_acco(String.valueOf(info.get("trade_acco")));
				    	        		email.setFund_busin_code(String.valueOf(info.get("fund_busin_code")));
						        		String str = String.valueOf(info.get("taconfirm_flag"));
						        		String strZh = "";
						        		if("0".equals(str)){
						        			strZh = "确认失败";
						        		}else if("1".equals(str)){
						        			strZh = "确认成功";
						        		}else if("2".equals(str)){
						        			strZh = "部分确认";
						        		}else if("3".equals(str)){
						        			strZh = "实时确认成功";
						        		}else if("4".equals(str)){
						        			strZh = "撤单";
						        		}else if("5".equals(str)){
						        			strZh = "行为确认";
						        		}else if("9".equals(str)){
						        			strZh = "未确认";
						        		}
						        		email.setTaconfirm_flag(strZh);
				    	        		userInfoService.insertAccountEmail(email);
				         	      }
		         	     }
		        	}
		        }
			//}
	        List<String> idNoList = new ArrayList<String>();
	      //查询AccountEmail表中某申请日期下所有有email地址的且不重复id_no集合
	        idNoList = userInfoService.findAccountEmailDistinctIdNo(qryDate);
	        System.out.println(idNoList);
	        if(idNoList.size()>0){
	        	//循环查询每个id_no下的账户类记录
	        	//SELECT a.*, s.name fund_busin_code_name FROM accountemail a 
	        	//INNER JOIN servicecode s ON a.fund_busin_code = s.code WHERE s.type='0' AND s.servicetype='1'
	        	//AND e_mail!="" AND apply_date='20150729' AND id_no='370883197807243602'
	        	for(int i=0;i<idNoList.size();i++){
	        		//有几个人则创建几个大list,每个大list里面是各账户类记录
	        		List<AccountEmail> list_i = new ArrayList<AccountEmail>();
	        		AccountEmail query = new AccountEmail();
	        		query.setApply_date(Integer.parseInt(qryDate));
	        		query.setId_no(idNoList.get(i));
	        		String lastestEmail = userInfoService.findAccountEmailLastestEmail(query);
	        		if(!"".equals(lastestEmail) && lastestEmail!=null){ // 只要有email信息，就把所有记录全查出来
	        			list_i = userInfoService.findAccountEmailByApplyDateAndIdNo(query); //查的都是有email信息的记录；20160127 只要有最新的email信息就可以了，避免开户时没email信息，后来资料修改时添加了email信息，应该全查出来
	        		}
	        		//查询每个人的最新的email
	        		//SELECT e_mail FROM accountemail WHERE e_mail!="" AND 
	        		//apply_date='20150727' AND id_no='340621198901057039' ORDER BY apply_time DESC LIMIT 0,1
	        		//循环修改List中的email信息
	        		if(list_i.size()>1){ //如果就1条记录就没必要重新设置email了
	        			//String lastestEmail = userInfoService.findAccountEmailLastestEmail(query);
	        			for(int j=0;j<list_i.size();j++){
	        				list_i.get(j).setE_mail(lastestEmail);
	        			}
	        		}
	        		System.out.println(list_i);
	        		String arr = "list_"+i;
	        		emailUsefulList.add(list_i);
	        		modelAndView.addObject(arr, list_i);
	        	}
	        }
	        modelAndView.addObject("emailUsefulList", emailUsefulList);
	        modelAndView.addObject("qryDate", qryDate);
	        modelAndView.addObject("rawDate", rawDate);
			modelAndView.setViewName("accountEmail");
			return modelAndView;
		}
		
		/**
		 * 真正发送邮件(账户类)
		 * @param session
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("/accountEmailSend")
		public @ResponseBody String accountEmailSend(String idno, String qryDate) throws Exception{
			String mailAddress = "";		
			String emailContent = "";
			String title = "您的金观诚账户确认结果";
			String str = "N";
			String msgContent =""; 
			String tailContent = "";

	        	//查询id_no下的账户类记录
	        	//SELECT a.*, s.name fund_busin_code_name FROM accountemail a 
	        	//INNER JOIN servicecode s ON a.fund_busin_code = s.code WHERE s.type='0' AND s.servicetype='1'
	        	//AND e_mail!="" AND apply_date='20150729' AND id_no='370883197807243602'
        		List<AccountEmail> singleList = new ArrayList<AccountEmail>();
        		AccountEmail query = new AccountEmail();
        		query.setApply_date(Integer.parseInt(qryDate));
        		query.setId_no(idno);
        		String lastestEmail = userInfoService.findAccountEmailLastestEmail(query);
        		if(!"".equals(lastestEmail) && lastestEmail!=null){ // 只要有email信息，就把所有记录全查出来
        			singleList = userInfoService.findAccountEmailByApplyDateAndIdNo(query); //查的都是有email信息的记录
        		}
        		//查询这个人的最新的email
        		//SELECT e_mail FROM accountemail WHERE e_mail!="" AND 
        		//apply_date='20150727' AND id_no='340621198901057039' ORDER BY apply_time DESC LIMIT 0,1
        		//循环修改List中的email信息
        		if(singleList.size()>1){ //多条记录取最新的email数据
        			mailAddress = lastestEmail;
        		}
        		if(singleList.size()==1){ //如果就1条记录取出email即可
        			mailAddress =singleList.get(0).getE_mail();
        		}
        		System.out.println(mailAddress);
			
	  	    if(singleList.size()>0){
	  	    	for(int i=0;i<singleList.size();i++){
	  	    		msgContent += "<tr>"+
					  	    "<td style=\"text-align:center;padding:6px 0; border:1px solid #ccc;\">"+singleList.get(i).getClient_name()+"</td>"+
					  	    "<td style=\"text-align:center;padding:6px 0; border:1px solid #ccc;\">"+singleList.get(i).getTa_acco()+"</td>"+
					  	    "<td style=\"text-align:center;padding:6px 0; border:1px solid #ccc;\">"+singleList.get(i).getApply_date()+"</td>"+
					  	    "<td style=\"text-align:center;padding:6px 0; border:1px solid #ccc;\">"+singleList.get(i).getFund_busin_code_name()+"</td>"+
					  	    "<td style=\"text-align:center;padding:6px 0; border:1px solid #ccc;\">"+singleList.get(i).getTaconfirm_flag()+"</td></tr>";
	  	    	}
	  	    }
	  	    
	  	  tailContent = "<p>温馨提示：</p>"+
									"<ol>"+
										"<li>若您想了解基金账户详细情况，可拨打客服热线400-068-0058或携带有效身份证件前往营业网点柜面咨询。</li>"+
									    "<li>本邮件所记载的基金账户及交易状况仅供客户参考，基金账户及交易明细应以浙江金观诚财富管理公司注册登记人的登记记录为准。</li>"+
									"</ol>"+
									"<p>感谢您对金观诚的信赖和支持！</p>"+
									"<p>"+
									"浙江金观诚财富管理有限公司<br>"+
									"客服热线：400-068-0058<br>"+
									"客服邮箱：King-Service@gold-finance.com.cn<br>"+
									"邮编：310000<br>"+
									"地址：浙江省杭州市拱墅区登云路43号锦昌大厦1楼金观诚财富"+
									"</p>"+
									"</body>"+
									"</html>";
			
			emailContent = "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" /><title>金观诚帐户及交易确认</title></head><body>"+
					"<p>尊敬的"+singleList.get(0).getClient_name()+"，您好！</p>"+
					"<p style=\"text-indent:24px;\">以下是"+singleList.get(0).getApply_date()+"您基金账户的确认信息，请查阅。</p>"+
					"<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"><thead><tr>"+
				  	    "<th width=\"18%\" style=\" padding:6px 0; border:1px solid #cca97a; background:#e4cdae; color:#963;\">客户名称</th>"+
				  	    "<th width=\"17%\" style=\" padding:6px 0; border:1px solid #cca97a; background:#e4cdae; color:#963;\">基金账号</th>"+
				  	    "<th width=\"17%\" style=\" padding:6px 0; border:1px solid #cca97a; background:#e4cdae; color:#963;\">申请日期</th>"+
				  	    "<th width=\"30%\" style=\" padding:6px 0; border:1px solid #cca97a; background:#e4cdae; color:#963;\">业务类别</th>"+
				  	    "<th width=\"18%\" style=\" padding:6px 0; border:1px solid #cca97a; background:#e4cdae; color:#963;\">确认结果</th></tr></thead>"+
				  	    "<tbody>"+msgContent+"</tbody></table>"+tailContent;
			
		try {
				emailService.sendMail(mailAddress, title,emailContent, true); //mailAddress
				//emailService.sendMail("liumj@gold-finance.com.cn", title,emailContent, true);
				str = "Y";
			} catch (MessagingException e) {
				str = "N";
				e.getMessage();
			}	
			return str;
		}
		
		/**
		 * 交易类邮件发送页面
		 * @param session
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("/tradeEmailFirst")
		public ModelAndView tradeEmailFirst() throws Exception{
			ModelAndView modelAndView = new ModelAndView();
			List emailUsefulList = new ArrayList(); 
	        modelAndView.addObject("emailUsefulList", emailUsefulList);
			modelAndView.setViewName("tradeEmail");
			return modelAndView;
		}
		
		/**
		 * 交易类邮件发送页面查询按钮
		 * @param session
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("/tradeEmailMsgQuery")
		public ModelAndView tradeEmailMsgQuery(String affirmDate) throws Exception{
			ModelAndView modelAndView = new ModelAndView();
			String rawDate = affirmDate;
			affirmDate = affirmDate.replace("-", "");
			List<TradeEmail> emailTotalList = new ArrayList<TradeEmail>(); //所有人的记录
			List emailUsefulList = new ArrayList(); 
			//查询TradeEmail表中有无affirmDate日期的任何记录，有的话先删，没有的话从恒生接口再查并存入自表；
			//SELECT * FROM tradeemail WHERE  affirm_date='20150729'
			//emailTotalList = userInfoService.findAccountEmailRecordList(qryDate);
			emailTotalList = userInfoService.findTradeEmailRecordList(affirmDate);
			if(emailTotalList.size()>0){ //先删除自己表里面的数据，从恒生重新查
				userInfoService.deleteTradeEmailRecordList(affirmDate);
			}
				int num=0;
				String requestnum = "50"; 
				request.clear();
		        request.put("request_num",   requestnum);        //请求行数
		        request.put("reqry_recordsum_flag",  "1");   //重新统计总记录数标志
		        request.put("qry_beginrownum","1");           //查询起始行号
		        request.put("start_date",affirmDate);        //20150922   
		        request.put("end_date",affirmDate);   
		        List<Map<String, Object>> response = getUfxSDK().query("/cwsale/v1/trade_confirm_qry" , request);
		        //if(response.get(0).get("data")==null && "".equals(response.get(0).get("error_info"))){
		        if( "".equals(response.get(0).get("error_info"))){
		        	for(Map<String, Object> info : response){ 
		        		TradeEmail email = new TradeEmail();
		        		email.setApply_date(String.valueOf(info.get("apply_date")));  //
		        		email.setAffirm_date(String.valueOf(info.get("affirm_date")));
		        		email.setFund_code(String.valueOf(info.get("fund_code")));
		        		email.setTrade_acco(String.valueOf(info.get("trade_acco"))); 
		        		email.setTa_acco(String.valueOf(info.get("ta_acco")));
		        		email.setFund_busin_code(String.valueOf(info.get("fund_busin_code")));
		        		email.setNet_value(String.valueOf(info.get("net_value"))); //
		        		email.setTrade_confirm_balance(String.valueOf(info.get("trade_confirm_balance")));
		        		email.setTrade_confirm_type(String.valueOf(info.get("trade_confirm_type"))); //
		        		email.setFare_sx(String.valueOf(info.get("fare_sx"))); //String.valueOf(info.get("trade_confirm_balance"))
		        		String str = String.valueOf(info.get("taconfirm_flag"));
		        		String strZh = "";
		        		if("0".equals(str)){
		        			strZh = "确认失败";
		        		}else if("1".equals(str)){
		        			strZh = "确认成功";
		        		}else if("2".equals(str)){
		        			strZh = "部分确认";
		        		}else if("3".equals(str)){
		        			strZh = "实时确认成功";
		        		}else if("4".equals(str)){
		        			strZh = "撤单";
		        		}else if("5".equals(str)){
		        			strZh = "行为确认";
		        		}else if("9".equals(str)){
		        			strZh = "未确认";
		        		}
		        		email.setTaconfirm_flag(strZh);
		        		userInfoService.insertTradeEmail(email);
		        		Double i = Double.parseDouble(String.valueOf(info.get("total_count")) );
			        	Double j = Double.parseDouble(requestnum);
			        	num =  (int)Math.ceil( i / j) ;
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
	         	      List<Map<String, Object>> responseYY = getUfxSDK().query("/cwsale/v1/trade_confirm_qry", request);
		         	  //if(responseYY.get(0).get("data")==null){    //能进这里说明有内容并正常返回
		         	if( "".equals(responseYY.get(0).get("error_info"))){    //能进这里说明有内容并正常返回
		         	       		for(Map<String, Object> info : responseYY){  //页面只展示有可用份额的基金
		    		        		TradeEmail email = new TradeEmail();
		    		        		email.setApply_date(String.valueOf(info.get("apply_date")));  //
		    		        		email.setAffirm_date(String.valueOf(info.get("affirm_date")));
		    		        		email.setFund_code(String.valueOf(info.get("fund_code")));
		    		        		email.setTrade_acco(String.valueOf(info.get("trade_acco"))); 
		    		        		email.setTa_acco(String.valueOf(info.get("ta_acco")));
		    		        		email.setFund_busin_code(String.valueOf(info.get("fund_busin_code")));
		    		        		email.setNet_value(String.valueOf(info.get("net_value"))); //
		    		        		email.setTrade_confirm_balance(String.valueOf(info.get("trade_confirm_balance")));
		    		        		email.setTrade_confirm_type(String.valueOf(info.get("trade_confirm_type"))); //
		    		        		email.setFare_sx(String.valueOf(info.get("fare_sx"))); //String.valueOf(info.get("trade_confirm_balance"))
		    		        		String str = String.valueOf(info.get("taconfirm_flag"));
		    		        		String strZh = "";
		    		        		if("0".equals(str)){
		    		        			strZh = "确认失败";
		    		        		}else if("1".equals(str)){
		    		        			strZh = "确认成功";
		    		        		}else if("2".equals(str)){
		    		        			strZh = "部分确认";
		    		        		}else if("3".equals(str)){
		    		        			strZh = "实时确认成功";
		    		        		}else if("4".equals(str)){
		    		        			strZh = "撤单";
		    		        		}else if("5".equals(str)){
		    		        			strZh = "行为确认";
		    		        		}else if("9".equals(str)){
		    		        			strZh = "未确认";
		    		        		}
		    		        		email.setTaconfirm_flag(strZh);
		    		        		userInfoService.insertTradeEmail(email);
				         	      }
		         	     }
		        	}
		        }
			//}
			
			//tradeemail表中，查不重复trade_acco交易账号，然后去客户资料查询接口查询用户姓名和邮箱
	        List<String> tradeAccoList = new ArrayList<String>();
	        tradeAccoList = userInfoService.findTradeEmailDistinctTradeAcco(affirmDate);
	        System.out.println(tradeAccoList);
	        if(tradeAccoList.size()>0){
	        	//循环查询每个trade_acco对应的用户信息；多交易账号情况就存多次
	        	Map<String, Object> reqTT = new LinkedHashMap<String, Object>();
	        	
	        	/*	SELECT t.fund_code, a.fund_name fund_code_name, t.fund_busin_code, s.name fund_busin_code_name
	        	FROM tradeemail t
	        	INNER JOIN allfundproduct a ON t.fund_code = a.fund_code
	        	INNER JOIN servicecode s ON t.fund_busin_code =  s.code
	        	WHERE s.type=1 AND s.servicetype=2 AND a.flag=1 AND t.affirm_date = '20150723'*/
	        	reqTT.put("trust_way", "2");
	        	reqTT.put("password", "NOPASSWORD"); //NOPASSWORD密码不校验
	        	for(int i=0;i<tradeAccoList.size();i++){
	        		String tradeAcco = tradeAccoList.get(i);
	        		reqTT.put("trade_acco", tradeAcco);
	        		Map<String, Object> respTT = getUfxSDK().transact("/cwsale/v1/clientinfo_qry_acct", reqTT);
	        		if("0".equals(respTT.get("success_type"))){
	        			if(!"".equals(respTT.get("e_mail"))){ 
	        				//如果用户邮箱不为空，则保存用户信息到 usertrade表 id_no,client_name,e_mail,trade_acco
	        				//得先查usertrade表中有无trade_acco是当前值的记录，有，先删除再插入
	        				PersonInfo per = userInfoService.findUserTradeByTradeAcco(tradeAcco);
	        				if(per!=null){
	        					userInfoService.deleteUserTradeByTradeAcco(tradeAcco);
	        				}
        					PersonInfo pi = new PersonInfo(); //respTT.get("trade_acco")
        					pi.setId_no(respTT.get("id_no")+""); //id_no, client_name, e_mail, trade_acco
        					pi.setClient_name(respTT.get("client_full_name")+"");
        					pi.setE_mail(respTT.get("e_mail")+"");
        					pi.setTrade_acco(tradeAcco);
        					userInfoService.insertUserTrade(pi);
	        			}
	        		}
	        	}
	       }
	        		
	        //usertrade表是根据交易账号查恒生用户信息接口，保存的是有邮箱的用户
	        //根据tradeemail，usertrade两张表，查某个确认日期下的不重复id_no集合
	        //SELECT DISTINCT(u.id_no) FROM tradeemail t INNER JOIN usertrade u ON t.trade_acco = u.trade_acco WHERE t.affirm_date = '20150723'
	        List<String> idNoList = new ArrayList<String>();
	        idNoList = userInfoService.findTradeEmailUserTradeDistinctIdNo(affirmDate);
	        if(idNoList.size()>0){
	        	for(int i=0;i<idNoList.size();i++){
	        		//拿着每一个id_no
	        		  /*依据tradeemail，allfundproduct，servicecode，usertrade四张表，查询交易类邮件需要的信息
	  	            SELECT t.fund_code, a.fund_name fund_code_name, t.fund_busin_code, s.name fund_busin_code_name, u.client_name, u.e_mail
	  				FROM tradeemail t
	  				INNER JOIN allfundproduct a ON t.fund_code = a.fund_code
	  				INNER JOIN servicecode s ON t.fund_busin_code =  s.code
	  				INNER JOIN usertrade u ON t.trade_acco = u.trade_acco
	  				WHERE s.type=1 AND s.servicetype=2 AND a.flag=1 AND t.affirm_date = '20150725' AND u.id_no='340621198901057040'
	  	          */	
	        		List<TradeEmail> list_i = new ArrayList<TradeEmail>();
	        		TradeEmail query = new TradeEmail();
	        		query.setAffirm_date(affirmDate);
	        		query.setId_no(idNoList.get(i));
	        		list_i = userInfoService.findTradeEmailByAffirmDateAndIdNo(query); 
	        		emailUsefulList.add(list_i);
	        	}
	        }
	        modelAndView.addObject("emailUsefulList", emailUsefulList);
	        modelAndView.addObject("qryDate", affirmDate);
	        modelAndView.addObject("rawDate", rawDate);
			modelAndView.setViewName("tradeEmail");
			return modelAndView;
		}
		
		/**
		 * 真正发送邮件(交易类)
		 * @param session
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("/tradeEmailSend")
		public @ResponseBody String tradeEmailSend(String idno, String qryDate) throws Exception{
			String mailAddress = "";		
			String emailContent = "";
			String title = "您的金观诚基金交易确认对账单";
			String str = "N";
			String msgContent =""; 
			String tailContent = "";
        		List<TradeEmail> singleList = new ArrayList<TradeEmail>();
        		TradeEmail que = new TradeEmail();
        		que.setAffirm_date(qryDate);
        		que.setId_no(idno);
        		singleList = userInfoService.findTradeEmailByAffirmDateAndIdNo(que); 
        		if(singleList.size()>0){
        			mailAddress = singleList.get(0).getE_mail();
        		}
	  	    if(singleList.size()>0){
	  	    	for(int i=0;i<singleList.size();i++){
	  	    		msgContent += "<tr>"+
					  	    "<td style=\"text-align:center;padding:6px 0; border:1px solid #ccc;\">"+singleList.get(i).getClient_name()+"</td>"+
					  	    "<td style=\"text-align:center;padding:6px 0; border:1px solid #ccc;\">"+singleList.get(i).getAffirm_date()+"</td>"+
					  	    "<td style=\"text-align:center;padding:6px 0; border:1px solid #ccc;\">"+singleList.get(i).getFund_code_name()+"</td>"+
					  	    "<td style=\"text-align:center;padding:6px 0; border:1px solid #ccc;\">"+singleList.get(i).getFund_busin_code_name()+"</td>"+
						  	"<td style=\"text-align:center;padding:6px 0; border:1px solid #ccc;\">"+singleList.get(i).getNet_value()+"</td>"+
						  	"<td style=\"text-align:center;padding:6px 0; border:1px solid #ccc;\">"+singleList.get(i).getTrade_confirm_balance()+"</td>"+
						  	"<td style=\"text-align:center;padding:6px 0; border:1px solid #ccc;\">"+singleList.get(i).getTrade_confirm_type()+"</td>"+
						  	"<td style=\"text-align:center;padding:6px 0; border:1px solid #ccc;\">"+singleList.get(i).getFare_sx()+"</td>"+
					  	    "<td style=\"text-align:center;padding:6px 0; border:1px solid #ccc;\">"+singleList.get(i).getTaconfirm_flag()+"</td></tr>";
	  	    	}
	  	    }
	  	    
	  	  tailContent = "<p>温馨提示：</p>"+
									"<ol>"+
										"<li>若您想了解基金账户详细情况，可拨打客服热线400-068-0058或携带有效身份证件前往营业网点柜面咨询。</li>"+
									    "<li>本邮件所记载的基金账户及交易状况仅供客户参考，基金账户及交易明细应以浙江金观诚财富管理公司注册登记人的登记记录为准。</li>"+
									"</ol>"+
									"<p>感谢您对金观诚的信赖和支持！</p>"+
									"<p>"+
									"浙江金观诚财富管理有限公司<br>"+
									"客服热线：400-068-0058<br>"+
									"客服邮箱：King-Service@gold-finance.com.cn<br>"+
									"邮编：310000<br>"+
									"地址：浙江省杭州市拱墅区登云路43号锦昌大厦1楼金观诚财富"+
									"</p>"+
									"</body>"+
									"</html>";
			
			emailContent = "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" /><title>金观诚帐户及交易确认</title></head><body>"+
					"<p>尊敬的"+singleList.get(0).getClient_name()+"，您好！</p>"+
					"<p style=\"text-indent:24px;\">以下是"+singleList.get(0).getAffirm_date()+"您的交易确认信息，请查阅。</p>"+
					"<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"><thead><tr>"+
			  	    "<th width=\"9%\" style=\" padding:6px 0; border:1px solid #cca97a; background:#e4cdae; color:#963;\">客户名称</th>"+
			  	    "<th width=\"10%\" style=\" padding:6px 0; border:1px solid #cca97a; background:#e4cdae; color:#963;\">确认日期</th>"+
			  	    "<th width=\"14%\" style=\" padding:6px 0; border:1px solid #cca97a; background:#e4cdae; color:#963;\">基金名称</th>"+
			  	    "<th width=\"9%\" style=\" padding:6px 0; border:1px solid #cca97a; background:#e4cdae; color:#963;\">业务类别</th>"+
			  	    "<th width=\"11%\" style=\" padding:6px 0; border:1px solid #cca97a; background:#e4cdae; color:#963;\">成交净值</th>"+
			  	    "<th width=\"12%\" style=\" padding:6px 0; border:1px solid #cca97a; background:#e4cdae; color:#963;\">确认金额(元)</th>"+
			  	    "<th width=\"13%\" style=\" padding:6px 0; border:1px solid #cca97a; background:#e4cdae; color:#963;\">确认份额(份)</th>"+
			  	    "<th width=\"10%\" style=\" padding:6px 0; border:1px solid #cca97a; background:#e4cdae; color:#963;\">手续费(元)</th>"+
			  	    "<th width=\"12%\" style=\" padding:6px 0; border:1px solid #cca97a; background:#e4cdae; color:#963;\">确认状态</th></tr></thead>"+
				  	    "<tbody>"+msgContent+"</tbody></table>"+tailContent;
		try {
				emailService.sendMail(mailAddress, title,emailContent, true);
				//emailService.sendMail("liumj@gold-finance.com.cn", title,emailContent, true);
				str = "Y";
			} catch (MessagingException e) {
				str = "N";
				e.getMessage();
			}	
			return str;
		}
		
		/**
		 * 短信系统页面
		 * @param session
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("/shortMessageFirst")
		public ModelAndView shortMessageFirst() throws Exception{
			ModelAndView modelAndView = new ModelAndView();
			List shortMessageList = new ArrayList(); 
	        modelAndView.addObject("shortMessageList", shortMessageList);
			modelAndView.setViewName("shortMessageSystem");
			return modelAndView;
		}
		
		/**
		 * 对应短信系统页面查询按钮
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("/shortMessageQuery")
		public ModelAndView shortMessageQuery(String chooseDate, String serviceCode) throws Exception{
			ModelAndView modelAndView = new ModelAndView();
			String qryDate = chooseDate.replace("-", "");
			//开户001  申购122 认购130 赎回 124 转换出138
			//如果是开户则查询"账户申请查询"，其他则查询"交易确认查询"
					List<AccountMessage> accountMessageList = new ArrayList<AccountMessage>(); ////账户申请查询,所有人的记录
					List shortMessageList = new ArrayList(); 
					if("001".equals(serviceCode)){//如果是开户查询
						int num=0;
						String requestnum = "50"; 
						request.clear();
				        request.put("trust_way",   "2");     //交易委托方式
				        request.put("request_num",   requestnum);        //请求行数
				        request.put("reqry_recordsum_flag",  "1");   //重新统计总记录数标志
				        request.put("qry_beginrownum","1");           //查询起始行号
				        request.put("fund_busin_code",serviceCode);           
				        request.put("begin_date",qryDate);        //20150922   
				        request.put("end_date",qryDate);   
				        List<Map<String, Object>> response = getUfxSDK().query("/cwsale/v1/account_apply_qry" , request);
				       // if(response.get(0).get("data")==null && "".equals(response.get(0).get("error_info"))){
				        if( "".equals(response.get(0).get("error_info"))){
				        	for(Map<String, Object> info : response){ 
				        		//只过滤TA编号是固定值的，该值目前是B0；即快速开户时分配的TA编号，其他后台自动开户的暂不考虑 【【【
				        		if(String.valueOf(info.get("mobile_tel")).length()==11){
				        			AccountMessage msg = new AccountMessage();
					        		msg.setClient_name(String.valueOf(info.get("client_name"))); 
					        		msg.setId_no(String.valueOf(info.get("id_no")));
					        		msg.setMobile_tel(String.valueOf(info.get("mobile_tel")));
					        		msg.setApply_date(String.valueOf(info.get("apply_date")));
					        		String str = String.valueOf(info.get("taconfirm_flag"));
					        		String strZh = "";
					        		if("0".equals(str)){
					        			strZh = "确认失败";
					        		}else if("1".equals(str)){
					        			strZh = "确认成功";
					        		}else if("2".equals(str)){
					        			strZh = "部分确认";
					        		}else if("3".equals(str)){
					        			strZh = "实时确认成功";
					        		}else if("4".equals(str)){
					        			strZh = "撤单";
					        		}else if("5".equals(str)){
					        			strZh = "行为确认";
					        		}else if("9".equals(str)){
					        			strZh = "未确认";
					        		}
					        		msg.setTaconfirm_flag(strZh);
					        		accountMessageList.add(msg);
					        		Double i = Double.parseDouble(String.valueOf(info.get("total_count")) );
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
			         	      List<Map<String, Object>> responseYY = getUfxSDK().query("/cwsale/v1/account_apply_qry", request);
				         	  //if(responseYY.get(0).get("data")==null && "".equals(response.get(0).get("error_info"))){    //能进这里说明有内容并正常返回
				         	if( "".equals(responseYY.get(0).get("error_info"))){    //能进这里说明有内容并正常返回
				         	       		for(Map<String, Object> info : responseYY){  
				         	       			//【
				         	       	  	    //只过滤TA编号是固定值的，该值目前是B0；即快速开户时分配的TA编号，其他后台自动开户的暂不考虑 【【【
							        		if(String.valueOf(info.get("mobile_tel")).length()==11){
							        			AccountMessage msg = new AccountMessage();
								        		msg.setClient_name(String.valueOf(info.get("client_name"))); 
								        		msg.setId_no(String.valueOf(info.get("id_no")));
								        		msg.setMobile_tel(String.valueOf(info.get("mobile_tel")));
								        		msg.setApply_date(String.valueOf(info.get("apply_date")));
								        		String str = String.valueOf(info.get("taconfirm_flag"));
								        		String strZh = "";
								        		if("0".equals(str)){
								        			strZh = "确认失败";
								        		}else if("1".equals(str)){
								        			strZh = "确认成功";
								        		}else if("2".equals(str)){
								        			strZh = "部分确认";
								        		}else if("3".equals(str)){
								        			strZh = "实时确认成功";
								        		}else if("4".equals(str)){
								        			strZh = "撤单";
								        		}else if("5".equals(str)){
								        			strZh = "行为确认";
								        		}else if("9".equals(str)){
								        			strZh = "未确认";
								        		}
								        		msg.setTaconfirm_flag(strZh);
								        		accountMessageList.add(msg);
				         	       			//】
							        	}
				         	     }
				        	}
				        }
				   }	
				}else{//不是开户查询，其他都走交易确认查询
						//【
										List<TradeMessage> tradeMessageTotalList = new ArrayList<TradeMessage>(); //所有人的记录
										
										//查询TradeMessage表中有无qryDate日期且业务代码是serviceCode的任何记录，有先删，没有的话从恒生接口再查并存入自表；
										TradeMessage query = new TradeMessage();
										query.setAffirm_date(qryDate);
										query.setFund_busin_code(serviceCode);
										tradeMessageTotalList = userInfoService.findTradeMessageRecordList(query);
										if(tradeMessageTotalList.size()>0){
											userInfoService.deleteTradeMessageRecordList(query);
										}
										//if(tradeMessageTotalList.size()<=0){
											int num=0;
											String requestnum = "50"; 
											request.clear();
									        request.put("request_num",   requestnum);        //请求行数
									        request.put("reqry_recordsum_flag",  "1");   //重新统计总记录数标志
									        request.put("qry_beginrownum","1");           //查询起始行号
									        request.put("fund_busin_code", serviceCode);           //业务代码
									        request.put("start_date",qryDate);        //20150922   
									        request.put("end_date",qryDate);   
									        List<Map<String, Object>> response = getUfxSDK().query("/cwsale/v1/trade_confirm_qry" , request);
									        //if(response.get(0).get("data")==null && "".equals(response.get(0).get("error_info"))){
									        if( "".equals(response.get(0).get("error_info"))){
									        	for(Map<String, Object> info : response){ 
									        		TradeMessage msg = new TradeMessage();
									        		msg.setApply_date(String.valueOf(info.get("apply_date")));  //
									        		msg.setAffirm_date(String.valueOf(info.get("affirm_date")));
									        		msg.setFund_code(String.valueOf(info.get("fund_code")));
									        		msg.setTarget_fund_code(String.valueOf(info.get("target_fund_code")));
									        		msg.setTrade_acco(String.valueOf(info.get("trade_acco"))); 
									        		msg.setTa_acco(String.valueOf(info.get("ta_acco")));
									        		msg.setFund_busin_code(String.valueOf(info.get("fund_busin_code")));
									        		msg.setNet_value(String.valueOf(info.get("net_value"))); //
									        		msg.setTrade_confirm_balance(String.valueOf(info.get("trade_confirm_balance")));
									        		msg.setTrade_confirm_type(String.valueOf(info.get("trade_confirm_type"))); //
									        		String str1 = String.valueOf(info.get("fail_cause"));
									        		String str2 = "0000".equals(str1)?"":str1;
									        		msg.setFail_cause(str2);
									        		
									        		String str = String.valueOf(info.get("taconfirm_flag"));
									        		String strZh = "";
									        		if("0".equals(str)){
									        			strZh = "确认失败";
									        		}else if("1".equals(str)){
									        			strZh = "确认成功";
									        		}else if("2".equals(str)){
									        			strZh = "部分确认";
									        		}else if("3".equals(str)){
									        			strZh = "实时确认成功";
									        		}else if("4".equals(str)){
									        			strZh = "撤单";
									        		}else if("5".equals(str)){
									        			strZh = "行为确认";
									        		}else if("9".equals(str)){
									        			strZh = "未确认";
									        		}
									        		msg.setTaconfirm_flag(strZh);
									        		userInfoService.insertTradeMessage(msg);
									        		Double i = Double.parseDouble(String.valueOf(info.get("total_count")) );
										        	Double j = Double.parseDouble(requestnum);
										        	num =  (int)Math.ceil( i / j) ;
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
								         	      List<Map<String, Object>> responseYY = getUfxSDK().query("/cwsale/v1/trade_confirm_qry", request);
									         	  //if(responseYY.get(0).get("data")==null && "".equals(response.get(0).get("error_info"))){    //能进这里说明有内容并正常返回
									         	if( "".equals(responseYY.get(0).get("error_info"))){    //能进这里说明有内容并正常返回
									         	       		for(Map<String, Object> info : responseYY){  //页面只展示有可用份额的基金
									         	       		TradeMessage msg = new TradeMessage();
											        		msg.setApply_date(String.valueOf(info.get("apply_date")));  //
											        		msg.setAffirm_date(String.valueOf(info.get("affirm_date")));
											        		msg.setFund_code(String.valueOf(info.get("fund_code")));
											        		msg.setTarget_fund_code(String.valueOf(info.get("target_fund_code")));
											        		msg.setTrade_acco(String.valueOf(info.get("trade_acco"))); 
											        		msg.setTa_acco(String.valueOf(info.get("ta_acco")));
											        		msg.setFund_busin_code(String.valueOf(info.get("fund_busin_code")));
											        		msg.setNet_value(String.valueOf(info.get("net_value"))); //
											        		msg.setTrade_confirm_balance(String.valueOf(info.get("trade_confirm_balance")));
											        		msg.setTrade_confirm_type(String.valueOf(info.get("trade_confirm_type"))); //
											        		msg.setFail_cause(String.valueOf(info.get("fail_cause")));
											        		
											        		String str = String.valueOf(info.get("taconfirm_flag"));
											        		String strZh = "";
											        		if("0".equals(str)){
											        			strZh = "确认失败";
											        		}else if("1".equals(str)){
											        			strZh = "确认成功";
											        		}else if("2".equals(str)){
											        			strZh = "部分确认";
											        		}else if("3".equals(str)){
											        			strZh = "实时确认成功";
											        		}else if("4".equals(str)){
											        			strZh = "撤单";
											        		}else if("5".equals(str)){
											        			strZh = "行为确认";
											        		}else if("9".equals(str)){
											        			strZh = "未确认";
											        		}
											        		msg.setTaconfirm_flag(strZh);
											        		userInfoService.insertTradeMessage(msg);
											         	  }
									         	     }
									        	}
									        }
										//}
										
										//trademessage表中，查不重复trade_acco交易账号，然后去客户资料查询接口查询用户姓名、身份证号、手机号码
								        List<String> tradeAccoList = new ArrayList<String>();
								        tradeAccoList = userInfoService.findTradeMessageDistinctTradeAcco(query);
								        System.out.println(tradeAccoList);
								        if(tradeAccoList.size()>0){
								        	//循环查询每个trade_acco对应的用户信息；多交易账号情况就存多次
								        	Map<String, Object> reqTT = new LinkedHashMap<String, Object>();
								        	
								        	/*	SELECT t.fund_code, a.fund_name fund_code_name, t.fund_busin_code, s.name fund_busin_code_name
								        	FROM tradeemail t
								        	INNER JOIN allfundproduct a ON t.fund_code = a.fund_code
								        	INNER JOIN servicecode s ON t.fund_busin_code =  s.code
								        	WHERE s.type=1 AND s.servicetype=2 AND a.flag=1 AND t.affirm_date = '20150723'*/
								        	reqTT.put("trust_way", "2");
								        	reqTT.put("password", "NOPASSWORD"); //NOPASSWORD密码不校验
								        	for(int i=0;i<tradeAccoList.size();i++){
								        		String tradeAcco = tradeAccoList.get(i);
								        		reqTT.put("trade_acco", tradeAcco);
								        		Map<String, Object> respTT = getUfxSDK().transact("/cwsale/v1/clientinfo_qry_acct", reqTT);
								        		if("0".equals(respTT.get("success_type")) && String.valueOf(respTT.get("mobile_tel")).length()==11 ){
								        				//得先查usertrademessage表中有无trade_acco是当前值的记录，有，先删除再插入
								        				PersonInfo per = userInfoService.findUserTradeMessageByTradeAcco(tradeAcco);
								        				if(per!=null){
								        					userInfoService.deleteUserTradeMessageByTradeAcco(tradeAcco);
								        				}
								        					PersonInfo pi = new PersonInfo(); //respTT.get("trade_acco")
								        					pi.setId_no(respTT.get("id_no")+""); //id_no, client_name, mobile_tel, trade_acco
								        					pi.setClient_name(respTT.get("client_full_name")+"");
								        					pi.setMobile_tel(respTT.get("mobile_tel")+"");
								        					pi.setTrade_acco(tradeAcco);
								        					userInfoService.insertUserTradeMessage(pi);
								        		}
								        	}
								       }
								        		
								        //usertrademessage表是根据交易账号查恒生用户信息接口
								        //根据trademessage，usertrademessage两张表，查某个确认日期和业务类型下的不重复id_no集合
								        //SELECT DISTINCT(u.id_no) FROM trademessage t INNER JOIN usertrademessage u ON t.trade_acco = u.trade_acco WHERE t.affirm_date = '20150723' and t.fund_busin_code='122'
								        List<String> idNoList = new ArrayList<String>();
								       idNoList = userInfoService.findTradeMessageUserTradeMessageDistinctIdNo(query);
								        System.out.println(idNoList);
								        if(idNoList.size()>0){
								        	for(int i=0;i<idNoList.size();i++){
								        		//拿着每一个id_no
								        		  /*依据trademessage，allfundproduct，usertrademessage三张表，查询交易类短信需要的信息
								        		   * SELECT u.client_name, u.mobile_tel, u.id_no, a1.fund_name fund_code_name, a2.fund_name target_fund_code_name, t.apply_date, t.affirm_date, t.trade_confirm_type, t.trade_confirm_balance, t.net_value,t.taconfirm_flag,t.fail_cause
														FROM trademessage t
														LEFT JOIN allfundproduct a1 ON t.fund_code = a1.fund_code 
														LEFT JOIN allfundproduct a2 ON t.target_fund_code = a2.fund_code
														INNER JOIN usertrademessage u ON t.trade_acco = u.trade_acco
														WHERE a1.flag=1 AND t.affirm_date = '20150818' AND t.fund_busin_code = '122' AND u.id_no='330102197802150976'
								        		   */	
								        		List<TradeMessage> list_i = new ArrayList<TradeMessage>();
								        		query.setId_no(idNoList.get(i));
								        		list_i = userInfoService.findTradeMessageByAffirmDateAndFundBusinCodeAndIdNo(query); 
								        		shortMessageList.add(list_i);
								        	}
								        }
					     //】
				}
			//】
	        modelAndView.addObject("accountMessageList", accountMessageList);
	        modelAndView.addObject("shortMessageList", shortMessageList);
	        modelAndView.addObject("qryDate", qryDate);
	        modelAndView.addObject("serviceCode", serviceCode);
			modelAndView.setViewName("shortMessageSystem");
			return modelAndView;
		}
		
		/**
		 * 发短信：开户
		 * @param am
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("/shortMessageSendAccount")
		public @ResponseBody String shortMessageSendAccount(AccountMessage am) throws Exception{
			String mobile_tel = am.getMobile_tel();
			String content = "尊敬的"+am.getClient_name()+"，您于"+am.getApply_date()+"提交的开户申请确认结果为："+am.getTaconfirm_flag()+"。详询4000680058。";
			//开户：尊敬的XXX，您于XXXX年XX月XX日提交的开户申请已确认成功。详询4000680058。
			String flag = smsPortal.send(mobile_tel, content);
			String status = "";
			if("ok".equals(flag)){status = "Y";}
			if("error".equals(flag)){status = "N";}
			return status;
		}
		
		
		@RequestMapping("/shortMessageSendTrade")
		public @ResponseBody String shortMessageSendTrade(TradeMessage tm) throws Exception{
			String id_no = tm.getId_no();
			String affirm_date = tm.getAffirm_date();
			String apply_date = tm.getApply_date();
			String fund_busin_code = tm.getFund_busin_code();
			String fund_busin_code_name = "";
			if("122".equals(fund_busin_code)){fund_busin_code_name = "申购";}
			else if("130".equals(fund_busin_code)){fund_busin_code_name = "认购";}
			else if("124".equals(fund_busin_code)){fund_busin_code_name = "赎回";}
			else if("138".equals(fund_busin_code)){fund_busin_code_name = "转换";}
			TradeMessage query = new TradeMessage();
			query.setId_no(id_no);
			query.setAffirm_date(affirm_date);
			query.setFund_busin_code(fund_busin_code);
			List<TradeMessage> singList = new ArrayList<TradeMessage>();
    		singList = userInfoService.findTradeMessageByAffirmDateAndFundBusinCodeAndIdNo(query); 
			String mobile_tel ="";
			String client_name = "";
			String str = "";
			String flag="";
    		if(singList.size()>0){
    			mobile_tel = singList.get(0).getMobile_tel();
    			client_name = singList.get(0).getClient_name();
    			for(int i=0;i<singList.size();i++){
    				//如果是转换业务(转换出)
    				if("138".equals(fund_busin_code)){
    					if("确认成功".equals(singList.get(i).getTaconfirm_flag())){
    						str = singList.get(i).getFund_code_name() +"转换为"+ singList.get(i).getTarget_fund_code_name() +"的基金转换申请已确认成功，转入份额为"+singList.get(i).getTrade_confirm_type()+"份。";
    						String content = "尊敬的"+client_name+"，您于"+apply_date+"提交的"+str+"如您需查询基金净值及账户资产，可关注官方微信“XXX”。详询4000680058。";
    						 flag = smsPortal.send(mobile_tel, content);
    					}else{
    						str = singList.get(i).getFund_code_name() +"转换为"+ singList.get(i).getTarget_fund_code_name() +"的基金转换申请"+singList.get(i).getTaconfirm_flag()+"，失败原因为"+singList.get(i).getFail_cause()+"；";
    						String content = "尊敬的"+client_name+"，您于"+apply_date+"提交的"+str+"如您需查询基金净值及账户资产，可关注官方微信“XXX”。详询4000680058。";
    						 flag = smsPortal.send(mobile_tel, content);
    					}
    				//如果是非转换操作
    				}else{
    					if("确认成功".equals(singList.get(i).getTaconfirm_flag())){
    						str = singList.get(i).getFund_code_name() +"的"+fund_busin_code_name+"申请已确认成功，确认金额为"+singList.get(i).getTrade_confirm_balance()+"元，确认份额为"+singList.get(i).getTrade_confirm_type()+"份，净值为"+singList.get(i).getNet_value()+"元。";
    						String content = "尊敬的"+client_name+"，您于"+apply_date+"提交的"+str+"如您需查询基金净值及账户资产，可关注官方微信“XXX”。详询4000680058。";
    						 flag = smsPortal.send(mobile_tel, content);
    					}else{
    						str = singList.get(i).getFund_code_name() +"的"+fund_busin_code_name+"申请"+singList.get(i).getTaconfirm_flag()+"，失败原因为"+singList.get(i).getFail_cause()+"；";
    						String content = "尊敬的"+client_name+"，您于"+apply_date+"提交的"+str+"如您需查询基金净值及账户资产，可关注官方微信“XXX”。详询4000680058。";
    						 flag = smsPortal.send(mobile_tel, content);
    					}
    				}
    			}
    		}

			String status = "";
			if("ok".equals(flag)){status = "Y";}
			if("error".equals(flag)){status = "N";}
			return status;
		}
		

}
