package com.goldfinance.jinGC.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.goldfinance.jinGC.common.UfxSDKCommon;
import com.goldfinance.jinGC.entity.EarningsTrend;
import com.goldfinance.jinGC.entity.FundQuery;
import com.goldfinance.jinGC.poOra.FundOverviewExtend;
import com.goldfinance.jinGC.poOra.MFNetValuePerformanceExtend;
import com.goldfinance.jinGC.poOra.TariffStructure;
import com.goldfinance.jinGC.service.AllFundProductService;
import com.goldfinance.jinGC.service.FundProductService;
import com.goldfinance.jinGC.service.LogService;

@Controller
public class FundProductController extends UfxSDKCommon {
	   private static final Logger LOG = LoggerFactory.getLogger(FundProductController.class);
	   private Map<String, Object> request = new LinkedHashMap<String, Object>();
	    
	    @Autowired
	    private LogService logService;
	    @Autowired
	    private FundProductService fundProductService;
	    @Autowired
	    private AllFundProductService allFundProductService;
	    
		@RequestMapping("/preFundHomePage")
		public ModelAndView preFundHomePage(HttpSession session) throws Exception{
			ModelAndView modelAndView = new ModelAndView();
	        //banner广告位和热销推荐基金产品查询(查出的是基金代码列表，还得去聚源查数据)
	        List<String> bannerList = fundProductService.findFundCodeByType(1);
	        List<MFNetValuePerformanceExtend> bannerMFNVList = new ArrayList<MFNetValuePerformanceExtend>();
	        if(bannerList.size()>0){
	        	bannerMFNVList = fundProductService.findFundProductPageInfo(bannerList);
	        }
	        List<String> topSellingList = fundProductService.findFundCodeByType(2);
	        List<MFNetValuePerformanceExtend> topSellingMFNVList = new ArrayList<MFNetValuePerformanceExtend>();
	        if(topSellingList.size()>0){
	        	topSellingMFNVList = fundProductService.findFundProductPageInfo(topSellingList);
	        }
	        
	        modelAndView.addObject("bannerList", bannerMFNVList);
	        modelAndView.addObject("topSellingList", topSellingMFNVList);
	        
	        System.out.println(session.getAttribute("user"));  //查看用户是否登录的判断
	        modelAndView.addObject("user", session.getAttribute("user"));
			modelAndView.setViewName("fund_index");
			return modelAndView;
		}


		@RequestMapping("/queryFundProductByCondition")
		public ModelAndView queryFundProductByCondition(FundQuery fundQuery) throws Exception{
			long startA = System.currentTimeMillis();
			ModelAndView modelAndView = new ModelAndView();
			System.out.println(fundQuery.getFundType());
			System.out.println(fundQuery.getKeyWord());
			System.out.println(fundQuery.getPageNumber());
			System.out.println(fundQuery.getPageSize());
			//没传页码默认第一页
			int pageNumber = fundQuery.getPageNumber();
			if(pageNumber==0){
				pageNumber=1;
			}	
			fundQuery.setPageNumber(pageNumber);		
			//根据全部基金页面的筛选条件查基金代码列表，之后去聚源查相关数据，因为目前聚源接口传基金列表就OK了
			List<String> secuCodes = allFundProductService.queryFundProductByCondition(fundQuery);
			List<MFNetValuePerformanceExtend> allFundProduct = new ArrayList<MFNetValuePerformanceExtend>();
			//如果恒生能搜索到相关基金代码，才去聚源查数据，否则聚源查所有了，一定要注意
			if(secuCodes!=null && secuCodes.size()!=0){
				//控制层传String型基金代码查聚源数据库里的基金信息
				allFundProduct = fundProductService.findFundProductPageInfo(secuCodes);
			}
			
	        modelAndView.addObject("allFundProduct", allFundProduct);
	        modelAndView.addObject("fundType", fundQuery.getFundType());
	        modelAndView.addObject("keyWord", fundQuery.getKeyWord());
			modelAndView.setViewName("fund_all");
			long endA = System.currentTimeMillis();
			LOG.error("queryFundProductByCondition请求共用了 " + (endA-startA) +"毫秒");
			return modelAndView;
		}

	    
		
		@RequestMapping("/loadMoreFundProduct")
		public @ResponseBody List<MFNetValuePerformanceExtend> loadMoreFundProduct(FundQuery fundQuery) throws Exception{
			long startA = System.currentTimeMillis();
			List<MFNetValuePerformanceExtend> allFundProduct = new ArrayList<MFNetValuePerformanceExtend>();
			List<String> secuCodes = allFundProductService.queryFundProductByCondition(fundQuery);
			//如果恒生能搜索到相关基金代码，才去聚源查数据，否则聚源查所有了，一定要注意
			if(secuCodes!=null && secuCodes.size()!=0){
				//控制层传String型基金代码查聚源数据库里的基金信息
				allFundProduct = fundProductService.findFundProductPageInfo(secuCodes);				
			}
			long endA = System.currentTimeMillis();
			LOG.error("loadMoreFundProduct请求共用了 " + (endA-startA) +"毫秒");
			return allFundProduct;
		}

	    /**
	     * 单个基金详情
	     * @return
	     * @throws Exception
	     */
		@RequestMapping("/singleFundProductDetails")
		public ModelAndView singleFundProductDetails(String secuCode) throws Exception{
			long startA = System.currentTimeMillis();
	   		long kobi = System.currentTimeMillis();
			long jodan = 0;
			ModelAndView modelAndView = new ModelAndView();
			MFNetValuePerformanceExtend mpe = new MFNetValuePerformanceExtend();
			List<String> secuCodes = new ArrayList<String>();
			secuCodes.add(secuCode);   //传入单个基金代码
			List<MFNetValuePerformanceExtend> allFundProduct = new ArrayList<MFNetValuePerformanceExtend>();
			
	        jodan = System.currentTimeMillis();
	        LOG.error("基金详情132共用了 " + (jodan-kobi) +"毫秒");
	        kobi = jodan;
	        
			//控制层传String型基金代码查聚源数据库里的基金基本信息：基金代码，名称，单位净值，日/月/年收益，净值日期
			allFundProduct = fundProductService.findFundProductPageInfo(secuCodes);	
			//List中只有一个对象，将其赋值给mpe，在页面显示
			if(allFundProduct.size()>0){
				mpe = allFundProduct.get(0);
			}
			
	        jodan = System.currentTimeMillis();
	        LOG.error("基金详情143共用了 " + (jodan-kobi) +"毫秒");
	        kobi = jodan;
	        
			//控制层传String型基金代码查聚源数据库里的基金介绍，包括类型和风险类型
			FundOverviewExtend foe = new FundOverviewExtend();
			foe = fundProductService.findFundProductIntroduction(secuCode);
			
	        jodan = System.currentTimeMillis();
	        LOG.error("基金详情151共用了 " + (jodan-kobi) +"毫秒");
	        kobi = jodan;
	        
			//基金类型和风险等级还是从恒生来查
			FundOverviewExtend foeB = fundTypeAndRisk(secuCode);
			foe.setFundTypeCodeName(foeB.getFundTypeCodeName());
			foe.setRiskEvaluationName(foeB.getRiskEvaluationName());
			
	        jodan = System.currentTimeMillis();
	        LOG.error("基金详情160共用了 " + (jodan-kobi) +"毫秒");
	        kobi = jodan;
	        
			//查收益走势：默认第一页，加载10条，其余放到ajax里查
			int pageNumber = 1;
			List<EarningsTrend> etList = new ArrayList<EarningsTrend>();
			etList = fundProductService.findEarningsTrend(secuCode, pageNumber);
			
	        jodan = System.currentTimeMillis();
	        LOG.error("基金详情169共用了 " + (jodan-kobi) +"毫秒");
	        kobi = jodan;
	        
			//费率类别:  2 -日常申购费率,  3 -日常赎回费率,  8 -年管理费,   9 -年托管费
			//申购费率 2 
			List<TariffStructure> allotTariffList = new ArrayList<TariffStructure>();
			allotTariffList = fundProductService.findSingleFundProductTariffStructure(secuCode, 2);
				
	        jodan = System.currentTimeMillis();
	        LOG.error("基金详情178共用了 " + (jodan-kobi) +"毫秒");
	        kobi = jodan;
	        
			//处理微信购买费率优惠的问题
			Float wcr=0.0f;
			String weChatRate ="";
			Float eve = 0.0f;
			
			for(int i=0;i<allotTariffList.size();i++){
			String chargeRateDesciption =	allotTariffList.get(i).getChargeRateDesciption();
				if(chargeRateDesciption.indexOf("%")!=-1){  //有百分号，按百分比计算费率
					String str = chargeRateDesciption.substring(0, chargeRateDesciption.indexOf("%"));
					Float rate = Float.parseFloat(str);
					if(rate>=1.5){ //1.5*0.4=0.6
						wcr = rate*0.4f;
					}else{ //否则直接按千六计算
						wcr = 0.6f;
					}
					weChatRate = wcr+"%";
				}else{  //没百分号，按笔计算费率    如 每笔1000元
					String lin1 = "";
					for(int j=0;j<chargeRateDesciption.length();j++){
						if(chargeRateDesciption.charAt(j)>=48 && chargeRateDesciption.charAt(j)<=57){
							//str2+=chargeRateDesciption.charAt(j);
							lin1 = lin1 + chargeRateDesciption.charAt(j);
							}
					}
					eve = Float.parseFloat(lin1);
					weChatRate = (int)(eve*0.6f)+"元/笔";
				}				
				allotTariffList.get(i).setWeChatRate(weChatRate);												
			}
			
	        jodan = System.currentTimeMillis();
	        LOG.error("基金详情212共用了 " + (jodan-kobi) +"毫秒");
	        kobi = jodan;
	        
			//赎回费率 3 
			List<TariffStructure> redeemTariffList = new ArrayList<TariffStructure>();
			redeemTariffList = fundProductService.findSingleFundProductTariffStructure(secuCode, 3);
			
			//年管理费 8
			List<TariffStructure> manageTariffList = new ArrayList<TariffStructure>();
			manageTariffList = fundProductService.findSingleFundProductTariffStructure(secuCode, 8);
			
			//年托管费 9
			List<TariffStructure>  trusteeTariffList = new ArrayList<TariffStructure>();
			trusteeTariffList = fundProductService.findSingleFundProductTariffStructure(secuCode, 9);
			
	        jodan = System.currentTimeMillis();
	        LOG.error("基金详情228共用了 " + (jodan-kobi) +"毫秒");
	        kobi = jodan;
	        
			//查询基金状态，比如可申购、可认购
			Map<String,Object>request =new LinkedHashMap<String, Object>();
	        request.put("trust_way", "2");     //交易委托方式
	        request.put("request_num", "1");                 //请求行数
	        request.put("reqry_recordsum_flag",  "0");    //重新统计总记录数标志
	        request.put("qry_beginrownum", "1"); //查询起始行号
	        request.put("fund_code", secuCode);                     //基金代码
			List<Map<String, Object>> response = getUfxSDK().query("/cwsale/v1/fundinfo_qry" , request);
			int allotFlag=0; //0 不可申购，1 可申购
			int subscribeFlag=0; //0不可认购 ， 1 可认购
			
			//if(response.get(0).get("data")==null && "".equals(response.get(0).get("error_info"))){ //有内容正常返回才会进来，否则返回的是形如{data=[]}，不进来
			if( "".equals(response.get(0).get("error_info")) ){ 
				if(Integer.parseInt(String.valueOf(response.get(0).get("success_type")))==0){			
					String str = String.valueOf(response.get(0).get("fund_status"));
					String allotStr = "078";
					String subscribeStr = "1";
					if(allotStr.indexOf(str)!=-1){
						allotFlag = 1;
					}
					if(subscribeStr.indexOf(str)!=-1){
						subscribeFlag = 1;
					}
				}
			}
	        jodan = System.currentTimeMillis();
	        LOG.error("基金详情257共用了 " + (jodan-kobi) +"毫秒");
	        
			modelAndView.addObject("allotFlag", allotFlag);
			modelAndView.addObject("subscribeFlag", subscribeFlag);
	        modelAndView.addObject("etList", etList);
	        modelAndView.addObject("allotTariffList", allotTariffList);
	        modelAndView.addObject("redeemTariffList", redeemTariffList);
	        modelAndView.addObject("manageTariffList", manageTariffList);
	        modelAndView.addObject("trusteeTariffList", trusteeTariffList);
	        modelAndView.addObject("mpe", mpe);
	        modelAndView.addObject("foe", foe);
			modelAndView.setViewName("fund_detail");
			long endA = System.currentTimeMillis() ;
			LOG.error("singleFundProductDetails请求共用了 " + (endA-startA) +"毫秒");
			return modelAndView;
		}
		
		/**
		 * 由单个基金详情页导航到更多收益走势详情页
		 * @return
		 * @throws Exception
		 */		
		@RequestMapping("/singleFundProductDetailsNavigateMoreEarningsTrend")
		public ModelAndView singleFundProductDetailsNavigateMoreEarningsTrend(HttpServletRequest request, String secuCode) throws Exception{
			long startA = System.currentTimeMillis();
			ModelAndView modelAndView = new ModelAndView();
			String chiName = request.getParameter("chiName");
			//查收益走势：默认第一页，加载10条，其余放到ajax里查
			int pageNumber = 1;
			List<EarningsTrend> etList = new ArrayList<EarningsTrend>();
			etList = fundProductService.findEarningsTrend(secuCode, pageNumber);	
			modelAndView.addObject("secuCode", secuCode);
			modelAndView.addObject("chiName", chiName);
	        modelAndView.addObject("etList", etList);
			modelAndView.setViewName("fund_detail_readMore");
			long endA = System.currentTimeMillis();
			LOG.error("基金更多收益走势请求共用了 " + (endA-startA) +"毫秒");
			return modelAndView;
		}

		/**
		 * 加载更多单个基金的收益走势数据
		 * @param earningsTrend
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("/loadMoreEarningsTrend")
		public @ResponseBody List<EarningsTrend> loadMoreEarningsTrend(EarningsTrend  earningsTrend) throws Exception{
			long startA = System.currentTimeMillis();
			List<EarningsTrend> etList = new ArrayList<EarningsTrend>();
			etList = fundProductService.findEarningsTrend(earningsTrend.getSecuCode(), earningsTrend.getPageNumber());
			long endA = System.currentTimeMillis();
			LOG.error("loadMoreEarningsTrend请求共用了 " + (endA-startA) +"毫秒");
			return etList;
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
	    
	    
}
