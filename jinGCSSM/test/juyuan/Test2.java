package juyuan;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.goldfinance.jinGC.common.UfxSDKCommon;

public class Test2 extends UfxSDKCommon{
	  private Map<String, Object> request = new HashMap<String, Object>();
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		 	request.put("trust_way",   "2");     //交易委托方式
	        request.put("request_num",   "50");        //请求行数
	        request.put("reqry_recordsum_flag",  "1");   //重新统计总记录数标志
	        request.put("qry_beginrownum","1");           //查询起始行号	        
      
	        List<String> fundCodes = new ArrayList<String>();
	        int num=3;

	        	 if(num>1){   //说明需要继续查   如num=3  一共查3次，上面已经查过一次了，还需查两次
	         		for(int i=1;i<num;i++){        				        			
	         	        request.put("trust_way",   "2");     //交易委托方式
	         	        request.put("request_num",   "50");        //请求行数        	        
	         	        String flag = "";
	         	        if(i==num-1){
	         	        	flag = "0";  //0 不再统计
	         	        }else{
	         	        	flag = "1";  //1 还需统计
	         	        }	        	        
	         	        request.put("reqry_recordsum_flag",  flag);   //重新统计总记录数标志	        	        
	         	        String start = 50*i + 1 +"";        	        
	         	        request.put("qry_beginrownum", start);           //查询起始行号	        	        
	         	        List<Map<String, Object>> responseYY = getUfxSDK().query("/cwsale/v1/fundinfo_qry", request);
	         	        for(Map<String, Object> infoYY : responseYY){
	         	        	System.out.println(infoYY);
	         	        	String statusYY = String.valueOf(infoYY.get("fund_status"));
	         	        	String statusStrYY = "01234578";
	         	        	if(statusStrYY.indexOf(statusYY)!=-1){
	         	        		fundCodes.add(String.valueOf(infoYY.get("fund_code")));
	         	        	}
	         	        }
	         		}
	 	        }	
		
		
	}

}
