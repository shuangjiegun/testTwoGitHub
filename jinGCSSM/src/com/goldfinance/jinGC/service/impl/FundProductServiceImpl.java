package com.goldfinance.jinGC.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.goldfinance.jinGC.controller.FundProductController;
import com.goldfinance.jinGC.entity.EarningsTrend;
import com.goldfinance.jinGC.mapper.FundProductMapper;
import com.goldfinance.jinGC.mapperOra.JYFundProductMapper;
import com.goldfinance.jinGC.poOra.FundOverviewExtend;
import com.goldfinance.jinGC.poOra.MFNetValuePerformanceExtend;
import com.goldfinance.jinGC.poOra.QueryPo;
import com.goldfinance.jinGC.poOra.TariffStructure;
import com.goldfinance.jinGC.service.FundProductService;

public class FundProductServiceImpl implements FundProductService {
	private static final Logger LOG = LoggerFactory.getLogger(FundProductServiceImpl.class);
	
	@Autowired
	private FundProductMapper fundProductMapper;
	@Autowired
	private JYFundProductMapper jyFundProductMapper;

	@Override
	public List<String> findFundCodeByType(int type) throws Exception {
		List<String> fundCodeList = fundProductMapper.findFundCodeByType(type);
		return fundCodeList;
	}

	@Override
	public List<MFNetValuePerformanceExtend> findFundProductPageInfo(List<String> ids) throws Exception {
		List<Integer> listHuo = new ArrayList<Integer>();
		List<Integer> listFei = new ArrayList<Integer>();
		List<Integer> listAll = new ArrayList<Integer>();
		List<MFNetValuePerformanceExtend> fundAll = new ArrayList<MFNetValuePerformanceExtend>();
		List<MFNetValuePerformanceExtend> fundInfo = new ArrayList<MFNetValuePerformanceExtend>();
		List<MFNetValuePerformanceExtend> fundHuo = new ArrayList<MFNetValuePerformanceExtend>();
		List<MFNetValuePerformanceExtend> onlyHengSheng = new ArrayList<MFNetValuePerformanceExtend>();
		//按照基金代码查询在聚源里没有的，做特殊处理
/*		if(ids.size()>0){
			int len = ids.size();
			for(int i=0;i<len;i++){
				String secucode = ids.get(i);
				int count = jyFundProductMapper.findJuYuanNoExistBySecuCode(secucode);
				if(count==0){
					MFNetValuePerformanceExtend mem = new MFNetValuePerformanceExtend();
					mem.setSecuCode(secucode);
					onlyHengSheng.add(mem);
				}
			}
		}
		
		fundAll.addAll(onlyHengSheng);*/
		
		
		QueryPo queryPo = new QueryPo();
		queryPo.setSecuCodes(ids);
		List<MFNetValuePerformanceExtend> innerCodes = jyFundProductMapper.findInnerCodeBySecuCode(queryPo);
		if(innerCodes != null && !innerCodes.isEmpty()){
			for(MFNetValuePerformanceExtend mfnv : innerCodes){
				listAll.add(mfnv.getInnerCode());
			}
		}
		//将innerCodes分两类：货币基金(1109) 和  非货币基金
		//查 基金概况 MF_FundArchives 表：基金类型是货币还是非货币
		//select t.innercode, t.FundTypeCode from jydb.MF_FundArchives t where t.innercode in (4087,2623)
		QueryPo query = new QueryPo();
		if(listAll.size()>0){
			query.setInnerCodes(listAll);
			List<MFNetValuePerformanceExtend> type = jyFundProductMapper.findFundType(query);
			if(type.size()>0){
				int len = type.size();
				for(int i=0;i<len;i++){
					if(type.get(i).getFundTypeCode()==1109){//如果是货币基金
						listHuo.add(type.get(i).getInnerCode());
					}else{//否则是非货币基金；0120 需要在基金概况附表中按条件TypeCode=10 and DataCode=1106查记录，如果能查到记录需要特别处理，当做货币基金
						int count = jyFundProductMapper.findRecordExist(type.get(i).getInnerCode());
						if(count==1){
							listHuo.add(type.get(i).getInnerCode());
						}else{
							listFei.add(type.get(i).getInnerCode());
						}
					}
				}
			}
		}
		
		//非货币基金的信息查询
		if(listFei.size()>0){
			queryPo.setInnerCodes(listFei);
			fundInfo = jyFundProductMapper.findFundProductPageInfo(queryPo);
		}
			
		//货币基金的信息查询
		if(listHuo.size()>0){
			QueryPo queryHuo = new QueryPo();
			queryHuo.setInnerCodes(listHuo);	
			fundHuo = jyFundProductMapper.findHuoBiFundProductPageInfo(queryHuo);
		}
	
		fundAll.addAll(fundInfo);
		if(fundHuo.size()>0){
			int len = fundHuo.size();
			for(int i=0;i<len;i++){
				fundHuo.get(i).setFundTypeCode(1109);   
			}
		}
		fundAll.addAll(fundHuo);
		return fundAll;
	}
	
	//控制层传String型基金代码查聚源数据库里的基金介绍，包括类型和风险类型
	@Override
	public FundOverviewExtend findFundProductIntroduction(String secuCode)  {
   		long kobi = System.currentTimeMillis();
		long jodan = 0;
		//根据基金代码查聚源数据库中对应的基金内部编码
		FundOverviewExtend foe = new FundOverviewExtend();
		List<String> ids = new ArrayList<String>();
		QueryPo queryPo = new QueryPo();
		ids.add(secuCode);
		queryPo.setSecuCodes(ids);
		try {
			List<MFNetValuePerformanceExtend> innerCodes = jyFundProductMapper.findInnerCodeBySecuCode(queryPo);
			if(innerCodes!=null && innerCodes.size()>0){
				for(int i=0;i<innerCodes.size();i++){
					foe.setInnercode(innerCodes.get(i).getInnerCode());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
        jodan = System.currentTimeMillis();
        LOG.error("聚源138共用了 " + (jodan-kobi) +"毫秒");
        kobi = jodan;
		//类型：股票型     风险类型：高风险     基金介绍 等等
		//基金介绍  主 查询单个基金的主要信息 (类型，成立日期，基金经理等，投资目标等)
		try {
			foe = jyFundProductMapper.findFundProductMainInformation(foe);
		} catch (Exception e3) {
			e3.printStackTrace();
		}
		//基金介绍  辅 (查询基金的风险类型)
/*		if(foe!=null){
			try {
				FundOverviewExtend	fo = jyFundProductMapper.findFundProductRiskInformation(foe);
				if(fo!=null){
					foe.setRiskEvaluation(fo.getRiskEvaluation());
					foe.setRiskEvaluationName(fo.getRiskEvaluationName());
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}*/
		
		//最新规模(期末基金资产净值)和最新份额
		if(foe!=null){
			try {
				FundOverviewExtend	f = jyFundProductMapper.findFundProductNetAssetsValueAndEndShares(foe);
				if(f!=null){
					foe.setLastestAssetsValue(f.getLastestAssetsValue());
					foe.setLastestShares(f.getLastestShares());
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

        jodan = System.currentTimeMillis();
        LOG.error("聚源174共用了 " + (jodan-kobi) +"毫秒");
        kobi = jodan;
		//根据   基金管理人编码--->基金管理人名称 ，   基金托管人编号---->基金托管人名称
		if(foe!=null){
			try {
				FundOverviewExtend	ff = jyFundProductMapper.findFundProductInvestAdvisorNameAndTrusteeName(foe);
				if(ff!=null){
					foe.setInvestAdvisorCodeName(ff.getInvestAdvisorCodeName());
					foe.setTrusteeCodeName(ff.getTrusteeCodeName());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
        jodan = System.currentTimeMillis();
        LOG.error("聚源189共用了 " + (jodan-kobi) +"毫秒");
        kobi = jodan;
		return foe;
	}

	/**
	 * 分页查询单个基金的收益走势数据
	 */
	@Override
	public List<EarningsTrend> findEarningsTrend(String secuCode, int pageNumber) {
   		long kobi = System.currentTimeMillis();
		long jodan = 0;
		EarningsTrend et = new EarningsTrend();
		EarningsTrend etHuo = new EarningsTrend();
		QueryPo qp = new QueryPo();
		List<String> secuCodes = new ArrayList<String>();
		secuCodes.add(secuCode);
		qp.setSecuCodes(secuCodes);
		List<MFNetValuePerformanceExtend> mvp;
		int innerCode=0;
		List<EarningsTrend> etList = new ArrayList<EarningsTrend>();
			//根据基金代码先查出对应的证券内部编码
			try {
				mvp = jyFundProductMapper.findInnerCodeBySecuCode(qp);
				if(mvp!=null){
					MFNetValuePerformanceExtend singleMvp = mvp.get(0);
					innerCode = singleMvp.getInnerCode();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		//【
	        jodan = System.currentTimeMillis();
	        LOG.error("聚源222共用了 " + (jodan-kobi) +"毫秒");
	        kobi = jodan;	
		List<Integer> list = new ArrayList<Integer>();
		list.add(innerCode);
		QueryPo query = new QueryPo();
		query.setInnerCodes(list);
		List<MFNetValuePerformanceExtend> type = jyFundProductMapper.findFundType(query);
        jodan = System.currentTimeMillis();
        LOG.error("聚源230共用了 " + (jodan-kobi) +"毫秒");
        kobi = jodan;
		if(type.size()>0){
			int len = type.size();
			for(int i=0;i<len;i++){
				if(type.get(i).getFundTypeCode()==1109){//如果是货币基金
					et.setInnerCode(innerCode);
					et.setPageNumber(pageNumber);
						try {
							etList = jyFundProductMapper.findHuoBiSingleFundEarningsTrend(et);
					        jodan = System.currentTimeMillis();
					        LOG.error("聚源241共用了 " + (jodan-kobi) +"毫秒");
					        kobi = jodan;
						} catch (Exception e) {
							e.printStackTrace();
						}
				}else{//否则是非货币基金
					et.setInnerCode(innerCode);
					et.setPageNumber(pageNumber);
						try {
							etList = jyFundProductMapper.findSingleFundEarningsTrend(et);
					        jodan = System.currentTimeMillis();
					        LOG.error("聚源252共用了 " + (jodan-kobi) +"毫秒");
					        kobi = jodan;
						} catch (Exception e) {
							e.printStackTrace();
						}
				}
			}
		}
		//】
        jodan = System.currentTimeMillis();
        LOG.error("聚源262共用了 " + (jodan-kobi) +"毫秒");
		return etList;
	}

	/**
	 * //根据费率类别查询基金的费率结构信息
	 */
	@Override
	public List<TariffStructure> findSingleFundProductTariffStructure(String secuCode, int type) {
		List<TariffStructure> list = new ArrayList<TariffStructure>();
		TariffStructure ts = new TariffStructure();
		List<String> ids = new ArrayList<String>();
		QueryPo queryPo = new QueryPo();
		ids.add(secuCode);
		queryPo.setSecuCodes(ids);
		List<MFNetValuePerformanceExtend> innerCodes;
		try {
			innerCodes = jyFundProductMapper.findInnerCodeBySecuCode(queryPo);
			if(innerCodes!=null){
					ts.setInnerCode(innerCodes.get(0).getInnerCode());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		ts.setChargeRateType(type);
		list = jyFundProductMapper.findSingleFundProductTariffStructure(ts);
		return list;
	}
}
