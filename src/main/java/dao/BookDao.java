package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import beans.Book;
import beans.Dbconnect;
import beans.IssuedBook;

public class BookDao {
	
	public static List<Book> listBook() {
		List<Book> list = new ArrayList<Book>();
		try {
			Connection con = Dbconnect.getMySQLConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from ebook");
			while(rs.next()) {
				Book tempbook = new Book();
				tempbook.setCallNo(rs.getString("call_no"));
				tempbook.setName(rs.getString("name"));
				tempbook.setAuthor(rs.getString("author"));
				tempbook.setPublisher(rs.getString("publisher"));
				tempbook.setQuantity(rs.getInt("quantity"));
				tempbook.setQuantity(rs.getInt("issued"));
				list.add(tempbook);
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
		return list;
	}
	
	public static int add(Book book) {
		int status = 0;
		try {
			Connection con = Dbconnect.getMySQLConnection();
			PreparedStatement pst = con.prepareStatement("insert into ebook(call_no, name, author, publisher, quantity, issued) values(?,?,?,?,?,?)");
			pst.setString(1, book.getCallNo());
			pst.setString(2, book.getName());
			pst.setString(3, book.getAuthor());
			pst.setString(4, book.getPublisher());
			pst.setInt(5, book.getQuantity());
			pst.setInt(6, 0);
			status = pst.executeUpdate();
			con.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
		return status;
	}
	
	public static int delete(String callno) {
		int status = 0;
		try {
			Connection con = Dbconnect.getMySQLConnection();
			PreparedStatement pst = con.prepareStatement("delete from ebook where call_no=?");
			pst.setString(1, callno);
			status = pst.executeUpdate();
			con.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
		return status;
		
	}
	
	public static int getIssued(String callno) {
		int issued = 0;
		try {
			Connection con = Dbconnect.getMySQLConnection();
			PreparedStatement pst = con.prepareStatement("select * from ebook where call_no=?");
			pst.setString(1,callno);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				issued = rs.getInt("issued");
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
		return issued;
		
	}
	
	public static boolean checkIssued(String callno) {
		boolean status = false;
		try {
			Connection con = Dbconnect.getMySQLConnection();
			PreparedStatement pst = con.prepareStatement("select * from ebook where call_no=? and quantity>issued");
			pst.setString(1, callno);
			ResultSet rs = pst.executeQuery();
			if(rs.next()) {
				status = true;
			}
			con.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
		return status;
	}
	
	public static int saveIssueBook(IssuedBook issuedbook) {
		String callno = issuedbook.getCallno();
		boolean checkstatus = checkIssued(callno);
		System.out.println("Check status: "+checkstatus);
		if(checkstatus) {
			int status = 0;
			try {
				Connection con = Dbconnect.getMySQLConnection();				
				PreparedStatement pst = con.prepareStatement("insert into e_issued_book(call_no,student_id, student_name, student_mobile, issued_date, return_status) "
															  + "values(?,?,?,?,?,?)");
				pst.setString(1, issuedbook.getCallno());
				pst.setString(2, issuedbook.getStudentid());
				pst.setString(3, issuedbook.getStudentname());
				pst.setLong(4, issuedbook.getStudentmobile());
				Date currentDate =new Date(System.currentTimeMillis());
				pst.setDate(5, currentDate);
				pst.setString(6,"no");
				status = pst.executeUpdate();
				
				if(status > 0) {
					PreparedStatement pst2 = con.prepareStatement("update ebook set issued=? where call_no=?");
					pst2.setInt(1,getIssued(callno)+1);
					pst2.setString(2, callno);
					status = pst2.executeUpdate();
					
				}
				con.close();
				
				} catch (SQLException e) {
				System.out.println(e);
			}
			return status;
		}
		else {
			return 0;
		}
	}
}
