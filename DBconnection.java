package Question_Answer_System;

import java.sql.*;

public class DBconnection {
	private static final String URL = "jdbc:postgresql://localhost:5432/QuizDB";
	private static final String USER = "postgres";
	private static final String PASSWORD = "n26261";
	private static Connection con = null;

	static {
		try {
			Class.forName("org.postgresql.Driver");
			System.out.println("DB loaded successfully");
			con = getConnection();
			
		}catch(SQLException sqlE) {
			System.out.println("SQL Error " + sqlE.getMessage());
		}catch (Exception e) {
			System.out.println("Error " + e.getMessage());
		}
	
	}
	
	public static Connection getConnection() throws SQLException {
		if (con == null) {
			con = DriverManager.getConnection(URL,USER,PASSWORD); 
		}
		return con;
	}
	
}
