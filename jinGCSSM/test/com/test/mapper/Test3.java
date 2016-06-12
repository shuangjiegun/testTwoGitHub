package com.test.mapper;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Before;
import org.junit.Test;

public class Test3 {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		try { 
		Class.forName("oracle.jdbc.driver.OracleDriver"); 
		String url = "jdbc:oracle:thin:@10.0.28.149:1521:WEBDB"; 
		Connection conn = DriverManager.getConnection(url, "jysee", "jysee"); 
		System.out.println(conn); 
		Statement stmt = conn.createStatement(); 
		ResultSet rs = stmt.executeQuery("select * from jydb.MF_NetValue where InnerCode=19701"); 
		
		//ResultSet rs = stmt.executeQuery("select t.InnerCode, t.InfoPublDate, t.NV from  jydb.MF_NetValue t where t.InfoPublDate in (select max(InfoPublDate) FROM jydb.MF_NetValue group by InnerCode)"); 
		
		//ResultSet rs = stmt.executeQuery("select InnerCode, max(InfoPublDate) FROM jydb.MF_NetValue group by InnerCode"); 
		//ResultSet rs = stmt.executeQuery("select temp.InnerCode, temp.InfoPublDate, t.NV from  jydb.MF_NetValue t, (select InnerCode, max(InfoPublDate) InfoPublDate FROM jydb.MF_NetValue group by InnerCode) temp where t.InnerCode = temp.InnerCode"); 
		//ResultSet rs = stmt.executeQuery("select * from jydb.MF_NetValue t where t.InfoPublDate = to_date('2001-11-03','yyyy-mm-dd')"); 
		//ResultSet rs = stmt.executeQuery("select * from jydb.CT_SystemConst cs where cs.LB=1031"); 
		//ResultSet rs = stmt.executeQuery("select * from jydb.MF_FundRecommend"); 
		//ResultSet rs = stmt.executeQuery("select * from (select a.*,ROWNUM rn from jydb.MF_FundRecommend a where ROWNUM<=10) where rn>0"); 
		while (rs.next()) { 
			//System.out.println(rs.getDouble("ID") +   "-----" + rs.getString("LBMC") + "-----" +rs.getInt("LB")+ "-----" + rs.getString("MS") + "-----" + rs.getString("DM") ); 
			//System.out.println(rs.getInt("InnerCode") +   "-----" + rs.getString("InfoSource") 
					//+ "-----" +rs.getDouble("NV")+ "-----" + rs.getDouble("UnitNV") + "-----" + rs.getDouble("AccumulatedUnitNV") ); 
			//System.out.println(rs.getInt("InvolvedFund") + "------- " + rs.getInt("OpinionCode") );
		
			System.out.println(rs.getInt("InnerCode") + "------- " +rs.getDate("InfoPublDate") +"---"+ rs.getString("InfoSource") +"---"+ rs.getDate("EndDate")+ "-----" +rs.getDouble("NV")+ "-----" + rs.getDouble("UnitNV") + "-----" + rs.getDouble("AccumulatedUnitNV") + "-----" +rs.getDate("XGRQ")  );
		//System.out.println( "------" + rs.getInt("InnerCode"));
			//System.out.println(rs.getInt("InnerCode") + "--------->>>>>" + rs.getDate(2));
			//System.out.println(rs.getInt(1) + "--------->" + rs.getDate(2)+ "--------->" + rs.getDouble(3));
		
		} 
		
		} catch (SQLException e) { 
		e.printStackTrace(); 
		} catch (ClassNotFoundException e) { 
		e.printStackTrace(); 
		} 
		
	}

}
