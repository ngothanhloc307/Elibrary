package beans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Dbconnect {
	
	public static Connection getMySQLConnection() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try {
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library","root","root");
			} catch (SQLException e) {
				System.out.println("ERROR -> SQLException");
				Logger.getLogger(Dbconnect.class.getName()).log(Level.SEVERE, null, e);
			}
			
		} catch (ClassNotFoundException e) {
			System.out.println("ERROR -> ClassNotFoundException");
			Logger.getLogger(Dbconnect.class.getName()).log(Level.SEVERE, null, e);	
		}
		return con;
	}
}
