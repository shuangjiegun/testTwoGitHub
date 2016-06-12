package com.goldfinance.jinGC.mapper;

import java.util.List;

import com.goldfinance.jinGC.entity.FundQuery;
import com.goldfinance.jinGC.po.AllFundProduct;

//所有基金产品：从恒生查入自库，定时任务扫为了更新，之后查询所有基金查自表就可以了
public interface AllFundProductMapper {
	//查自表：最新所有基金列表
	public List<AllFundProduct> findLatestFundProductList() throws Exception;
	//删除以前所有基金列表
	public void deleteOldFundProductList() throws Exception;
	//插入最新恒生获取到的所有基金列表入自表
	public void insertLatestFundProductList(List<AllFundProduct> list) throws Exception;
	//根据全部基金页面的筛选条件查基金列表，之后去聚源查相关数据，因为目前聚源接口传基金列表就OK了
	public List<String> queryFundProductByCondition(FundQuery fundQuery) throws Exception;
	//根据基金代码查自表中的基金信息
	public AllFundProduct findFundByfundCode(String secuCode) throws Exception;
}
