package com.goldfinance.jinGC.service;

import java.util.List;

import com.goldfinance.jinGC.entity.EarningsTrend;
import com.goldfinance.jinGC.poOra.FundOverviewExtend;
import com.goldfinance.jinGC.poOra.MFNetValuePerformanceExtend;
import com.goldfinance.jinGC.poOra.QueryPo;
import com.goldfinance.jinGC.poOra.TariffStructure;

//自维护banner广告位基金和热销推荐基金信息
public interface FundProductService {
	//根据基金是banner广告位推荐1还是热销推荐2来查基金代码
	public List<String> findFundCodeByType(int type) throws Exception;
	//业务层逻辑应该是用户控制层传String型基金代码就能查聚源数据库基金信息
	public List<MFNetValuePerformanceExtend> findFundProductPageInfo(List<String> ids) throws Exception;
	//控制层传String型基金代码查聚源数据库里的基金介绍，包括类型和风险类型
	public FundOverviewExtend findFundProductIntroduction(String secuCode);
	//分页查询单个基金的收益走势
	public List<EarningsTrend> findEarningsTrend(String secuCode, int pageNumber);
	//根据费率类别查询基金的费率结构信息
	public List<TariffStructure> findSingleFundProductTariffStructure(String secuCode, int i);
	

}
