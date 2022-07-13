package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

import beans.Dbconnect;
import beans.IssuedBook;

public class IssuedBookDao {
	public static int saveIssueBook(IssuedBook issuedbook) {
		String callno = issuedbook.getCallno();
		boolean checkstatus = BookDao.checkIssued(callno);
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
					pst2.setInt(1,BookDao.getIssued(callno)+1);
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

	public static int returnBook(String callno,int studentid){
		int status = 0;
		try {
			Connection con = Dbconnect.getMySQLConnection();
			PreparedStatement pst = con.prepareStatement("update e_issued_book set return_status='yes' where call_no=? and student_id=?");
			pst.setString(1, callno);
			pst.setInt(2, studentid);
			status = pst.executeUpdate();
			
			if(status > 0) {
				PreparedStatement pst2 = con.prepareStatement("update ebook set issued=? where call_no=?");
				pst2.setInt(1, BookDao.getIssued(callno) -1);
				System.out.println(BookDao.getIssued(callno));
				pst2.setString(2, callno);
				status = pst2.executeUpdate();
			}
			con.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
		return status;
	}
	
	public static List<IssuedBook> getAllIssuedBook() {
		List<IssuedBook> list = new ArrayList<IssuedBook>();
		try {
			Connection con = Dbconnect.getMySQLConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from e_issued_book");
			
			while(rs.next()) {
				IssuedBook ib = new IssuedBook();
				ib.setCallno(rs.getString("call_no"));
				ib.setStudentid(rs.getString("student_id"));
				ib.setStudentname(rs.getString("student_name"));
				ib.setStudentmobile(rs.getInt("student_mobile"));
				ib.setIssueddate(rs.getDate("issued_date"));
				ib.setReturnstatus(rs.getString("return_status"));
				list.add(ib);
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
		return list;
	}
}
