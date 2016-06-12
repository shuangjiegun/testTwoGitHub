package juyuan;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Before;
import org.junit.Test;

public class myTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		try { 
			Class.forName("com.mysql.jdbc.Driver"); 
			String url = "jdbc:mysql:///ssm"; 
			Connection conn = DriverManager.getConnection(url, "root", "root"); 
			System.out.println(conn); 
			Statement stmt = conn.createStatement(); 
			
			//ResultSet rs = stmt.executeQuery("select t.InnerCode, t.CompanyCode, t.SecuCode, t.ChiName, t.ChiNameAbbr, t.EngName, t.EngNameAbbr, t.SecuAbbr, t.ChiSpelling, t.SecuMarket, t.SecuCategory, t.ListedDate, t.ListedSector, t.ListedState, t.ISIN, t.XGRQ from jydb.SecuMain t where t.InnerCode=16753"); 		
			//ResultSet rs = stmt.executeQuery("select t.LB, t.LBMC, t.MS, t.DM, t.XGRQ from jydb.CT_SystemConst t where t.LB=201"); 		
			//ResultSet rs = stmt.executeQuery("select t.InnerCode, t.InfoPublDate, t.InfoSource, t.EndDate, t.NV, t.UnitNV, t.AccumulatedUnitNV, t.XGRQ from jydb.MF_NetValue t ORDER BY EndDate DESC"); 		
			//ResultSet rs = stmt.executeQuery("select t.InnerCode, t.TradingDay, t.UnitNV, t.NVDailyGrowthRate, t.RRInSelectedMonth, t.RRSinceThisYear, t.UpdateTime from jydb.MF_NetValuePerformance t where t.InnerCode=16753"); 		
			//ResultSet rs = stmt.executeQuery("select t1.InnerCode, t1.TradingDay, t1.UnitNV, t1.NVDailyGrowthRate, t1.RRInSelectedMonth, t1.RRSinceThisYear, t1.UpdateTime, t2.CompanyCode, t2.SecuCode, t2.ChiName, t2.ChiNameAbbr, t2.SecuAbbr, t2.SecuMarket, t2.SecuCategory, t2.ISIN from jydb.MF_NetValuePerformance t1, jydb.SecuMain t2  where t1.InnerCode=t2.InnerCode AND t2.SecuCode IN ('850333', '150283', '150284', '168205', '580020', '122690')"); 		 //AND t2.SecuCode IN ('850333', '150283', '150284', '168205', '580020', '122690')
			//ResultSet rs = stmt.executeQuery("select t1.InnerCode, t1.TradingDay, t1.UnitNV, t1.NVDailyGrowthRate, t1.RRInSelectedMonth, t1.RRSinceThisYear, t1.UpdateTime from jydb.MF_NetValuePerformance t1"); 		
			ResultSet rs = stmt.executeQuery("select * from  bank  "); 		
			while (rs.next()) { 		
			//System.out.println("【证券内部编码】" + rs.getInt("InnerCode") +"【公司代码】" + rs.getInt("CompanyCode") +"【证券代码】" + rs.getString("SecuCode") +"【中文名称】" + rs.getString("ChiName") +"【中文名称缩写】" + rs.getString("ChiNameAbbr") +"【英文名称】" + rs.getString("EngName") +"【英文名称缩写】" + rs.getString("EngNameAbbr") +"【证券简称】" + rs.getString("SecuAbbr") +"【拼音证券简称】" + rs.getString("ChiSpelling") +"【证券市场】" + rs.getInt("SecuMarket") +"【证券类别】" + rs.getInt("SecuCategory") +"【上市日期】" + rs.getTime("ListedDate") +"【上市板块】" + rs.getInt("ListedSector") +"【上市状态】" + rs.getInt("ListedState") +"【ISIN代码】" + rs.getString("ISIN") +"【更新时间】" + rs.getTimestamp("XGRQ") );
			//System.out.println("【常量分类编码】" + rs.getInt("LB") +"【常量分类名称】" + rs.getString("LBMC") +"【常量描述】" + rs.getString("MS") +"【常量代码】" + rs.getInt("DM") +"【修改日期】" + rs.getTimestamp("XGRQ"));
			//System.out.println("【基金内部编码】" + rs.getInt("InnerCode") +"【信息发布日期】" + rs.getTimestamp("InfoPublDate") +"【信息来源】" + rs.getString("InfoSource") +"【截止日期】" + rs.getTimestamp("EndDate") +"【净资产值】" + rs.getDouble("NV") +"【单位净值】" + rs.getDouble("UnitNV") +"【单位累计净值】" + rs.getDouble("AccumulatedUnitNV") +"【更新日期】" + rs.getTimestamp("XGRQ") );
			//System.out.println("【证券内部编码】" + rs.getInt("InnerCode") +"【交易日】" + rs.getTimestamp("TradingDay") +"【最新单位净值（元）】" + rs.getDouble("UnitNV") +"【日回报率（%）】" + rs.getDouble("NVDailyGrowthRate") +"【本月以来回报率（%）】" + rs.getDouble("RRInSelectedMonth") +"【今年以来回报率（%）】" + rs.getDouble("RRSinceThisYear") +"【更新时间】" + rs.getTimestamp("UpdateTime"));
			//System.out.println("【证券内部编码】" + rs.getInt("InnerCode") +"【交易日】" + rs.getTimestamp("TradingDay") +"【最新单位净值（元）】" + rs.getDouble("UnitNV") +"【日回报率（%）】" + rs.getDouble("NVDailyGrowthRate") +"【本月以来回报率（%）】" + rs.getDouble("RRInSelectedMonth") +"【今年以来回报率（%）】" + rs.getDouble("RRSinceThisYear") +"【更新时间】" + rs.getTimestamp("UpdateTime") +"【公司代码】" + rs.getInt("CompanyCode") +"【证券代码】" + rs.getString("SecuCode") +"【中文名称】" + rs.getString("ChiName") +"【中文名称缩写】" + rs.getString("ChiNameAbbr") +"【证券简称】" + rs.getString("SecuAbbr") +"【证券市场】" + rs.getInt("SecuMarket") +"【证券类别】" + rs.getInt("SecuCategory") +"【ISIN代码】" + rs.getString("ISIN") );
			//System.out.println("【证券内部编码】" + rs.getInt("InnerCode") +"【交易日】" + rs.getTimestamp("TradingDay") +"【最新单位净值（元）】" + rs.getDouble("UnitNV") +"【日回报率（%）】" + rs.getDouble("NVDailyGrowthRate") +"【本月以来回报率（%）】" + rs.getDouble("RRInSelectedMonth") +"【今年以来回报率（%）】" + rs.getDouble("RRSinceThisYear") +"【更新时间】" + rs.getTimestamp("UpdateTime")  );
			System.out.println("---" + rs.getInt("id") +"---" + rs.getInt("bankCode") +"----" + rs.getString("bankName") );
				
			
			} 	
			
			
		} catch (SQLException e) { 
			e.printStackTrace(); 
		} catch (ClassNotFoundException e) { 
			e.printStackTrace(); 
		} 
	}
}
