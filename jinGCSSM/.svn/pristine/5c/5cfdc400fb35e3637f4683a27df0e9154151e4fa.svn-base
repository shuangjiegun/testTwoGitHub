package com.goldfinance.jinGC.mapperOra;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.goldfinance.jinGC.entity.EarningsTrend;
import com.goldfinance.jinGC.poOra.FundOverviewExtend;
import com.goldfinance.jinGC.poOra.MFNetValuePerformanceExtend;
import com.goldfinance.jinGC.poOra.QueryPo;
import com.goldfinance.jinGC.poOra.TariffStructure;

public interface JYFundProductMapper {
	//根据传入的基金代码查聚源数据库中对应的基金内部编码
	public List<MFNetValuePerformanceExtend> findInnerCodeBySecuCode(QueryPo queryPo) throws Exception;
	//根据传入条件如基金内部编码或xxx查该基金的信息
	public List<MFNetValuePerformanceExtend> findFundProductPageInfo(QueryPo queryPo) throws Exception;
	//查询单个基金的主要信息 (类型，成立日期，基金经理，基金管理人，基金托管人，投资范围，投资目标，投资策略)
	public FundOverviewExtend findFundProductMainInformation(FundOverviewExtend foe) throws Exception;
	//查询基金的风险信息
	public FundOverviewExtend findFundProductRiskInformation(FundOverviewExtend foe) throws Exception;
	//查询基金的最新规模(期末基金资产净值)和最新份额
	public FundOverviewExtend findFundProductNetAssetsValueAndEndShares(FundOverviewExtend foe) throws Exception;
	//根据   基金管理人编码--->基金管理人名称 ，   基金托管人编号---->基金托管人名称
	public FundOverviewExtend findFundProductInvestAdvisorNameAndTrusteeName(FundOverviewExtend foe) throws Exception;
	//	分页查询单个基金的收益走势
	public List<EarningsTrend> findSingleFundEarningsTrend(EarningsTrend et) throws Exception;
	//根据费率类别和证券内部编码封装的对象查询基金的费率结构信息
	public List<TariffStructure> findSingleFundProductTariffStructure(TariffStructure ts);
	
	public List<MFNetValuePerformanceExtend> findFundType(QueryPo query);
	
	public List<MFNetValuePerformanceExtend> findHuoBiFundProductPageInfo(QueryPo queryHuo);
	
	public List<EarningsTrend> findHuoBiSingleFundEarningsTrend(EarningsTrend et);
	
	public int findRecordExist(int innerCode);
	
	public int findJuYuanNoExistBySecuCode(@Param("secucode") String secucode);
}
