package juyuan;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.goldfinance.jinGC.mapper.AllFundProductMapper;
import com.goldfinance.jinGC.mapperOra.JYFundProductMapper;
import com.goldfinance.jinGC.po.AllFundProduct;
import com.goldfinance.jinGC.poOra.MFNetValuePerformanceExtend;
import com.goldfinance.jinGC.poOra.QueryPo;

public class JYTest2 {
	private ApplicationContext applicationContext;

	@Before
	public void setUp() throws Exception {
		applicationContext = new ClassPathXmlApplicationContext("classpath:spring/beans-*.xml");
	}

	@Test
	public void test() throws Exception {
		AllFundProductMapper mapper = (AllFundProductMapper) applicationContext.getBean("allFundProductMapper");
/*		QueryPo queryPo = new QueryPo();
		List<Integer> inn = new ArrayList<Integer>();
		inn.add(5205);
		inn.add(14609);
		queryPo.setInnerCodes(inn);*/
		//List<MFNetValuePerformanceExtend> yy = mapper.findInnerCodeBySecuCode(queryPo);
		//List<MFNetValuePerformanceExtend> yy = mapper.findFundProductPageInfo(queryPo);
		//List<MFNetValuePerformanceExtend> yy = mapper.findFundProductPageInfo(queryPo);
/*		for(MFNetValuePerformanceExtend tt : yy){
			System.out.println(tt);

		}*/
/*		List<AllFundProduct> yy = mapper.findLatestFundProductList();
		for(AllFundProduct tt : yy){
			System.out.println(tt);
		}*/
		//mapper.deleteOldFundProductList();
		List<AllFundProduct> list = new ArrayList<AllFundProduct>();
		for(int i =0; i< 10; i++){
			AllFundProduct ap = new AllFundProduct();
			ap.setFlag(1);
			ap.setFund_code("2_"+i);
			ap.setOfund_type("1_"+i);
			ap.setCreatedatetime(new Date());
			ap.setError_code("3_"+i);
			ap.setError_info("33_"+i);
			ap.setFund_name("hell_"+i);
/*			ap.setFund_status("5_"+i);
			ap.setRowcount("44_"+i);
			ap.setSuccess_type("44_"+i);
			ap.setTa_no("44_"+i);
			ap.setTotal_count("44_"+i);*/
			list.add(ap);
		}
		mapper.insertLatestFundProductList(list);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}

}
