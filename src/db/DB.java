package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class DB {

	private static Connection conn = null;
	
	public static Connection getConnection() {
		try {
			Properties props = loadProperties();
			String url = props.getProperty("dburl");
			conn = DriverManager.getConnection(url, props);
			
		}
		catch(SQLException ex) {
			throw new DbException(ex.getMessage());
		}
		
		return conn;
	}
	
	public static void closeConnection() {
		
		if(conn != null) {
			try {
				conn.close();
			}
			catch(SQLException ex) {
				throw new DbException(ex.getMessage());
			}
		}
		
	}
	
	public static Properties loadProperties() {	
		Properties props = null;
		try(FileInputStream fs = new FileInputStream ("db.properties")){
			props = new Properties();
			props.load(fs);
		}
		catch(IOException ex) {
			ex.printStackTrace();
		}
		return props;	
	}
	
	
	public static void closePreparedStatement(PreparedStatement  pst) {
		if(pst != null) {
			try {
			   pst.close();
			}
			catch(SQLException ex) {
				throw new DbException(ex.getMessage());
			}
		}
	}
	
	public static void closeResultSet(ResultSet rs) {
		if(rs != null) {
			try {
			rs.close();
			}
			catch(SQLException ex) {
				throw new DbException(ex.getMessage());
			}
		}
	}
	
}
