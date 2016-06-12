package com.goldfinance.jinGC.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.goldfinance.jinGC.entity.FundQuery;
import com.goldfinance.jinGC.mapper.AllFundProductMapper;
import com.goldfinance.jinGC.po.AllFundProduct;
import com.goldfinance.jinGC.service.AllFundProductService;

public class AllFundProductServiceImpl implements AllFundProductService {
	
	@Autowired
	private AllFundProductMapper allFundProductMapper;

	//查询自表中所有基金列表(启用状态的)
	@Override
	public List<AllFundProduct> findLatestFundProductList() throws Exception {
		List<AllFundProduct> allFundProductList = allFundProductMapper.findLatestFundProductList();
		return allFundProductList;
	}
	//删除自表中的所有基金列表，其实是更新使用状态flag为2即禁用
	@Override
	public void deleteOldFundProductList() throws Exception {
		allFundProductMapper.deleteOldFundProductList();
	}
	//插入最新从恒生获取到的所有基金列表入自表
	@Override
	public void insertLatestFundProductList(List<AllFundProduct> list) throws Exception {
		allFundProductMapper.insertLatestFundProductList(list);
	}
	//根据全部基金页面的筛选条件查基金列表
	@Override
	public List<String> queryFundProductByCondition(FundQuery fundQuery) throws Exception {
		List<String> list = allFundProductMapper.queryFundProductByCondition(fundQuery);
		return list;
	}
	@Override
	public AllFundProduct findFundByfundCode(String secuCode) throws Exception {
		AllFundProduct allFundProduct = allFundProductMapper.findFundByfundCode(secuCode);
		return allFundProduct;
	}

}
