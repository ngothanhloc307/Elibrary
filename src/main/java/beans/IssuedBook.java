package beans;

import java.util.Date;

public class IssuedBook {
	
	private String callno;
	private String studentid;
	private String studentname;
	private long studentmobile;
	private Date issueddate;
	private String returnstatus;
	
	public IssuedBook() {}

	public IssuedBook(String callno, String studentid, String studentname, long studentmobile) {
		super();
		this.callno = callno;
		this.studentid = studentid;
		this.studentname = studentname;
		this.studentmobile = studentmobile;
	}

	public String getCallno() {
		return callno;
	}

	public void setCallno(String callno) {
		this.callno = callno;
	}

	public String getStudentid() {
		return studentid;
	}

	public void setStudentid(String studentid) {
		this.studentid = studentid;
	}

	public String getStudentname() {
		return studentname;
	}

	public void setStudentname(String studentname) {
		this.studentname = studentname;
	}

	public long getStudentmobile() {
		return studentmobile;
	}

	public void setStudentmobile(long studentmobile) {
		this.studentmobile = studentmobile;
	}

	public Date getIssueddate() {
		return issueddate;
	}

	public void setIssueddate(Date issueddate) {
		this.issueddate = issueddate;
	}

	public String getReturnstatus() {
		return returnstatus;
	}

	public void setReturnstatus(String returnstatus) {
		this.returnstatus = returnstatus;
	}
	
	
}
