package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import beans.Dbconnect;
import beans.Librarian;

public class LibrarianDao {
	public static boolean authenticate(String email,String password) {
		boolean status = false;
		
		try {
			Connection con = Dbconnect.getMySQLConnection();
			String sql = "select * from e_librarian where email=? and password=?";
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, email);
			pst.setString(2, password);
			ResultSet rs = pst.executeQuery();
			
			if(rs.next()) {
				status = true;
			}
			con.close();
		} catch (SQLException e) {
			System.out.println("ERROR -> SQLException");
			System.out.println(e);
		}
		return status;
	}
	
	public static Librarian viewById(int id) {
		Librarian templb = new Librarian();
		try {
			Connection con = Dbconnect.getMySQLConnection();
			PreparedStatement pst = con.prepareStatement("select * from e_librarian where id=?");
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			if(rs.next()) {
				templb.setId(rs.getInt(1));
				templb.setName(rs.getString(2));
				templb.setPassword(rs.getString(3));
				templb.setEmail(rs.getString(4));
				templb.setMobile(rs.getLong(5));
				
			}
			con.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
		return templb;
	}
	public static int add(Librarian librarian) {
		int status = 0;
		
		try {
			Connection con = Dbconnect.getMySQLConnection();
			String sql = "insert into e_librarian(name, password, email, mobile) values(?,?,?,?)";
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, librarian.getName());
			pst.setString(2, librarian.getPassword());
			pst.setString(3, librarian.getEmail());
			pst.setLong(4, librarian.getMobile());
			status = pst.executeUpdate();
			con.close();
			
		} catch (SQLException e) {
			System.out.println("ERROR -> SQL Exception");
			System.out.println(e);
		}
		return status;
		
	}
	
	public static int update(Librarian librarian) {
		int status = 0;
		try {
			Connection con = Dbconnect.getMySQLConnection();
			PreparedStatement pst = con.prepareStatement("update e_librarian set name=?,email=?,password=?,mobile=? where id=?");
			pst.setString(1, librarian.getName());
			pst.setString(2, librarian.getEmail());
			pst.setString(3, librarian.getPassword());
			pst.setLong(4, librarian.getMobile());
			pst.setInt(5, librarian.getId());
			int val = pst.executeUpdate();
			con.close();
			if(val > 0) {
				status = 1;
				System.out.println("Updated Succesfully!!");
			}
			else {
				status = -1;
				System.out.println("ERROR -> Connect status falied");
			}
			
			
		} catch (SQLException e) {
			status = 2;
			System.out.println(e);
		}
		return status;
	}
	
	public static List<Librarian> listLibrarian() {
		List<Librarian> tempList = new ArrayList<>();
		try {
			tempList.clear();
			Connection con = Dbconnect.getMySQLConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from e_librarian");
			while (rs.next()) {
				Librarian templb = new Librarian();
				templb.setId(rs.getInt("id"));
				templb.setPassword(rs.getString("password"));
				templb.setEmail(rs.getString("email"));
				templb.setName(rs.getString("name"));
				templb.setMobile(rs.getLong("mobile"));
				tempList.add(templb);
			}
			con.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
		return tempList;
	}
	
	public static int delete(int id) {
		int status = 0;
		try {
			Connection con = Dbconnect.getMySQLConnection();
			PreparedStatement pst = con.prepareStatement("delete from e_librarian where id=?");
			pst.setInt(1, id);
			status = pst.executeUpdate();
			con.close();
			
		} catch (SQLException e) {
			System.out.println(e);
		}
		return status;
	}
}
