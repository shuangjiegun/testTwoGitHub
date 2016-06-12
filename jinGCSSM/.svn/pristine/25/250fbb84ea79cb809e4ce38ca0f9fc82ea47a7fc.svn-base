package com.goldfinance.jinGC.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;

import org.springframework.beans.factory.annotation.Autowired;

import com.goldfinance.jinGC.po.AllFundProduct;
import com.goldfinance.jinGC.service.AllFundProductService;
import com.goldfinance.jinGC.service.LogService;

/**
 * 该定时任务的主要作用是每日(暂定10:00)去恒生查询所有fund_status状态
 * 是01234578的基金入自库
 * @author liumj
 *
 */

public class AllFundProductTask extends UfxSDKCommon{
	

	private Map<String, Object> request = new LinkedHashMap<String, Object>();
	@Autowired
	private AllFundProductService allFundProductService;
	@Autowired
	private LogService logService;

	public void run() {
		
			System.out.println("定时任务开始执行");
			//查 ---塞-- 删------插
			//查询当前自表中的所有基金列表
			//能否一步到位：删除自表中的旧基金列表，从恒生查基金列表，并插入到自表中
			try {
				
				//【
				
					String requestnum = "50";   //请求行数维护  request_num 【等会改为50
			        int num=0;
			        String statusStr = "01234578";		//基金状态值维护 fund_status
			        
		 		  	request.put("trust_way",   "2");     //交易委托方式
			        //request.put("request_num",   "50");        //请求行数
		 		  	request.put("request_num",  requestnum);   
			        request.put("reqry_recordsum_flag",  "1");   //重新统计总记录数标志    说明：rowcount是本次返回的条数，
			        																			//如果不是最后一次则total_num的值表示总记录，最后一次查时该字段无值
			        																			//即reqry_recordsum_flag=1，total_num有值，reqry_recordsum_flag=0，total_num无值
			        request.put("qry_beginrownum","1");           //查询起始行号	        

			        List<Map<String, Object>> response = getUfxSDK().query("/cwsale/v1/fundinfo_qry", request);
			        List<AllFundProduct> all = new ArrayList<AllFundProduct>();
			        
			        for(Map<String, Object> info : response){
			        	String status1 = String.valueOf(info.get("fund_status"));
			        	if(statusStr.indexOf(status1)!=-1){
					        	String initialpinyin = "";  //initialpinyin
					            char[] nameChar = String.valueOf(info.get("fund_name")).toCharArray();  
					            HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();  
					            defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);  
					            defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);  
					            for (int i = 0; i < nameChar.length; i++) {  
					            	String s1 = String.valueOf(nameChar[i]);
					            	if (s1.matches("[\\u4e00-\\u9fa5]")) { 
					                	initialpinyin += PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat)[0].charAt(0);                  
					                }else{  
					                	initialpinyin += nameChar[i];  
					                }  
					            }  
					            
					    		String quanpinyin = "";  //quanpinyin
					            char[] nameChar2 = String.valueOf(info.get("fund_name")).toCharArray();  
					            HanyuPinyinOutputFormat defaultFormat2 = new HanyuPinyinOutputFormat();  
					            defaultFormat2.setCaseType(HanyuPinyinCaseType.LOWERCASE);  
					            defaultFormat2.setToneType(HanyuPinyinToneType.WITHOUT_TONE);  
					            for (int i = 0; i < nameChar2.length; i++) {  
					            	String s2 = String.valueOf(nameChar2[i]);
					                if (s2.matches("[\\u4e00-\\u9fa5]")) {  
					                	quanpinyin += PinyinHelper.toHanyuPinyinStringArray(nameChar2[i], defaultFormat2)[0];    
					                }else{  
					                	quanpinyin += nameChar2[i];  
					                }  
					            }  
					        	
						        AllFundProduct 	ap = new AllFundProduct();
						        ap.setCreatedatetime(new Date());
						        ap.setError_code(String.valueOf(info.get("error_code")));
						        ap.setError_info(String.valueOf(info.get("error_info")));
						        ap.setFlag(1);
						        ap.setFund_code(String.valueOf(info.get("fund_code")));
						        ap.setFund_name(String.valueOf(info.get("fund_name")));
						        ap.setQuanpinyin(quanpinyin);
						        ap.setInitialpinyin(initialpinyin);
						        ap.setFund_status(String.valueOf(info.get("fund_status")));
						        ap.setOfund_type(String.valueOf(info.get("ofund_type")));
						        ap.setRowcount(String.valueOf(info.get("rowcount")));
						        ap.setSuccess_type(String.valueOf(info.get("success_type")));
						        ap.setTa_no(String.valueOf(info.get("ta_no")));
						        ap.setTotal_count(String.valueOf(info.get("total_count")));
						        all.add(ap);
					        	System.out.println(info);
					        	Double i = Double.parseDouble(String.valueOf(info.get("total_count")) );
					        	//Double j = (double) 50;
					        	Double j = Double.parseDouble(requestnum);
					        	num =  (int)Math.ceil( i / j) ;	        	
					        }
			        }

			        	 if(num>1){   //说明需要继续查   如num=3  一共查3次，上面已经查过一次了，还需查两次
				         		for(int i=1;i<num;i++){        				        			
					         	        request.put("trust_way",   "2");     //交易委托方式
					         	        //request.put("request_num",   "50");        //请求行数 
					         	       request.put("request_num",   requestnum);
					         	        String flag = "";
					         	        if(i==num-1){
					         	        	flag = "0";  //0 不再统计
					         	        }else{
					         	        	flag = "1";  //1 还需统计
					         	        }	        	        
					         	        request.put("reqry_recordsum_flag",  flag);   //重新统计总记录数标志	        	        
					         	       // String start = 50*i + 1 +"";       
					         	       String start =  Integer.parseInt(requestnum)*i + 1 +"";   
					         	        request.put("qry_beginrownum", start);           //查询起始行号	        	        
					         	        List<Map<String, Object>> responseYY = getUfxSDK().query("/cwsale/v1/fundinfo_qry", request);
					         	        for(Map<String, Object> infoYY : responseYY){
					         	        	String status2 = String.valueOf(infoYY.get("fund_status"));
								        	if(statusStr.indexOf(status2)!=-1){
									         	       	String initialpinyinY = "";  //initialpinyinY
											            char[] nameCharT = String.valueOf(infoYY.get("fund_name")).toCharArray();  
											            HanyuPinyinOutputFormat defaultFormatT = new HanyuPinyinOutputFormat();  
											            defaultFormatT.setCaseType(HanyuPinyinCaseType.LOWERCASE);  
											            defaultFormatT.setToneType(HanyuPinyinToneType.WITHOUT_TONE);  
											            for (int k = 0; k < nameCharT.length; k++) {  
											            	String s3 = String.valueOf(nameCharT[k]);
											                if (s3.matches("[\\u4e00-\\u9fa5]")) {                 
											                	initialpinyinY += PinyinHelper.toHanyuPinyinStringArray(nameCharT[k], defaultFormatT)[0].charAt(0);                  
											                }else{  
											                	initialpinyinY += nameCharT[k];  
											                }  
											            }  
											            
											    		String quanpinyinT = "";  //quanpinyinT
											            char[] nameCharY = String.valueOf(infoYY.get("fund_name")).toCharArray();  
											            HanyuPinyinOutputFormat defaultFormatY = new HanyuPinyinOutputFormat();  
											            defaultFormatY.setCaseType(HanyuPinyinCaseType.LOWERCASE);  
											            defaultFormatY.setToneType(HanyuPinyinToneType.WITHOUT_TONE);  
											            for (int n = 0; n < nameCharY.length; n++) {  
											            	String s4 = String.valueOf(nameCharY[n]);
											                if (s4.matches("[\\u4e00-\\u9fa5]")) {  
											                	quanpinyinT += PinyinHelper.toHanyuPinyinStringArray(nameCharY[n], defaultFormatY)[0];    
											                }else{  
											                	quanpinyinT += nameCharY[n];  
											                }  
											            }
					         	        	
												        AllFundProduct 	ap = new AllFundProduct();
												        ap.setCreatedatetime(new Date());
												        ap.setError_code(String.valueOf(infoYY.get("error_code")));
												        ap.setError_info(String.valueOf(infoYY.get("error_info")));
												        ap.setFlag(1);
												        ap.setFund_code(String.valueOf(infoYY.get("fund_code")));
												        ap.setFund_name(String.valueOf(infoYY.get("fund_name")));
												        ap.setQuanpinyin(quanpinyinT);
												        ap.setInitialpinyin(initialpinyinY);
												        ap.setFund_status(String.valueOf(infoYY.get("fund_status")));
												        ap.setOfund_type(String.valueOf(infoYY.get("ofund_type")));
												        ap.setRowcount(String.valueOf(infoYY.get("rowcount")));
												        ap.setSuccess_type(String.valueOf(infoYY.get("success_type")));
												        ap.setTa_no(String.valueOf(infoYY.get("ta_no")));
												        ap.setTotal_count(String.valueOf(infoYY.get("total_count")));
												        all.add(ap);
								        	}	
					         	        }
				         		}
			 	        }	 
				//查 ---塞-- 删------插】 
			     //先删除表中的历史数据
			    if(all.size()>0){
			    	allFundProductService.deleteOldFundProductList();	
			    	//将最新查到的全部基金插入表中
			    	allFundProductService.insertLatestFundProductList(all);
			    	logService.insertLog("定时任务自发执行", "全部基金", "", "", "每日10:00查恒生全部基金更新到自表中");
			    }    	 
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("定时任务执行结束");
	}
}