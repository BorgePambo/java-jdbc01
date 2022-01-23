package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import db.DB;
import db.DbException;

public class program {

	public static void main(String[] args) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Connection conn = null;
		PreparedStatement pst = null;
		
		try {
			conn = DB.getConnection();
			pst = conn.prepareStatement(
					"INSERT INTO seller"
					+ "(Name, Email, BirthDate, BaseSalary, DepartmentId)"
					+ "VALUES" 
					+ "(?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS );
			
			pst.setString(1, "Rita Helias");
			pst.setString(2, "rita@gmail.com");
			pst.setDate  (3, new java.sql.Date(sdf.parse("17/08/2000").getTime()) );
			pst.setDouble(4, 5000);
			pst.setInt   (5, 1);
			
			int rowsAffected = pst.executeUpdate();
			if(rowsAffected > 0) {
				ResultSet rs = pst.getGeneratedKeys();
				while(rs.next()) {
					int id = rs.getInt(1);
					System.out.println("Done! Id = " + id);
				}
			}
			else {
				System.out.println("No row Affected! ");
			}
		}
		catch(SQLException ex) {
			throw new DbException(ex.getMessage());
			//ex.printStackTrace();
		}
		catch(ParseException ex) {
			ex.printStackTrace();
		}
		finally{
			DB.closeConnection();
			DB.closePreparedStatement(pst);
		}
		
		
	}

}
