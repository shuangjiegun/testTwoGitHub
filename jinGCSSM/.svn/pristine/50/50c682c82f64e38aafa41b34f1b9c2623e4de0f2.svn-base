package com.goldfinance.jinGC.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class OracleConnection {
	private static final Logger LOG = LoggerFactory.getLogger(OracleConnection.class);
	/** oracle连接参数 **/
/*	private static final String drive = "oracle.jdbc.driver.OracleDriver";
	private static final String url = "jdbc:oracle:thin:@121.41.104.110:1521:orcl";
	private static final String name = "sms999";
	private static final String password = "sms999";*/
	
	private static final String drive = "oracle.jdbc.driver.OracleDriver";
	private static final String url = "jdbc:oracle:thin:@10.0.61.65:1521:orcl";
	private static final String name = "sms";
	private static final String password = "sms";
	
	private static OracleConnection conn;
	
	/**
	 * 获取连接池实例
	 * 
	 * @return
	 */
	public synchronized static OracleConnection getInstance() {
		if (conn == null) {
			conn = new OracleConnection();
		}
		return conn;
	}

	// 初始化连接池
	public OracleConnection() {
	}

	/**
	 * 获取连接
	 */
	public Connection getConnection() {
		Connection conn = null;
		try {
			// 加载驱动
			Class.forName(drive);
			// 获取一个连接
			conn = DriverManager.getConnection(url, name, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * 释放连接
	 * 
	 * @param con
	 */
	public void freeConnection(Connection con) {
		try {
			if (null != con) {
				con.close();
				con = null;
			}
		} catch (SQLException e) {
			LOG.error("数据库连接关闭异常:{}",e.getMessage());
		} finally {
			if (null != con) {
				try {
					con.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				con = null;
			}
		}
	}

	/**
	 * 释放Statement资源
	 * 
	 * @param statement
	 */
	public void freeStatement(Statement statement) {
		try {
			if (null != statement) {
				statement.close();
				statement = null;
			}
		} catch (Exception e) {
			LOG.error("数据库关闭异常:{}",e.getMessage());
		} finally {
			if (null != statement) {
				try {
					statement.close();
				} catch (Exception e) {
					LOG.error("数据库释放异常:{}",e.getMessage());
				}
				statement = null;
			}
		}
	}

	/**
	 * 释放查询结果
	 * 
	 * @param statement
	 */
	public void freeResultSet(ResultSet rs) {
		try {
			if (null != rs) {
				rs.close();
				rs = null;
			}
		} catch (Exception e) {
			LOG.error("数据库异常:{}",e.getMessage());
		} finally {
			if (null != rs) {
				try {
					rs.close();
				} catch (Exception e) {
					LOG.error("数据库对象关闭异常:{}",e.getMessage());
				}
				rs = null;
			}
		}
	}

	public Boolean sendSMS(String phone, String content) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		conn = getConnection();
		String vsql = "select S_SM_SEND_SM_LIST.nextval as id from dual";
		PreparedStatement pstmt;
		try {
			pstmt = (PreparedStatement) conn.prepareStatement(vsql);
			rs = pstmt.executeQuery();
			rs.next();
			int id = rs.getInt(1);
			SendSms sendSms = new SendSms();
			sendSms.setSendTarget(phone);
			sendSms.setsMContent(content);
			sendSms.init();
			vsql = "insert into SM_SEND_SM_LIST(SERIALNO,SERVICEID,"
					+ "SMCONTENT,SENDTARGET,PRIORITY,"
					+ "RCOMPLETETIMEBEGIN,RCOMPLETETIMEEND,RCOMPLETEHOURBEGIN,"
					+ "RCOMPLETEHOUREND,REQUESTTIME,"
					+ "ROADBY,SENDTARGETDESC) values(?,?,?,?,?,?,?,?,?,?,?,?)";
			pstmt = (PreparedStatement) conn.prepareStatement(vsql);
			pstmt.setInt(1, id);
			pstmt.setString(2, sendSms.getServiceID());
			pstmt.setString(3, sendSms.getsMContent());
			pstmt.setString(4, sendSms.getSendTarget());
			pstmt.setInt(5, sendSms.getPriority());
			pstmt.setDate(6, new java.sql.Date(sendSms.getRcompleteTimeBegin()
					.getTime()));
			pstmt.setDate(7, new java.sql.Date(sendSms.getRcompleteTimeEnd()
					.getTime()));
			pstmt.setInt(8, sendSms.getRcompleteHourBegin());
			pstmt.setInt(9, sendSms.getRcompleteHourEnd());
			pstmt.setDate(10, new java.sql.Date(sendSms.getRequestTime()
					.getTime()));
			pstmt.setInt(11, sendSms.getRoadby());
			pstmt.setString(12, sendSms.getSendTargetDesc());
			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			this.freeResultSet(rs);
			this.freeStatement(ps);
			this.freeConnection(conn);
		}

	}
	
	public static OracleConnection getConn() {
		if(conn == null){
			return getInstance();
		}
		return conn;
	}
}
