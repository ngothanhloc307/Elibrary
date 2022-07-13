package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/servlets.AdminLogin")
public class AdminLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		out.print("<!DOCTYPE html>");
		out.print("<html>");
		out.println("<head>");
		out.println("<title>Admin Section</title>");
		out.println("<link href=\"//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap-glyphicons.css\" rel=\"stylesheet\">");
		out.println("<link rel='stylesheet' href='bootstrap.min.css'/>");
		out.println("</head>");
		out.println("<body>");
		
		String email = "admin@admin.com";
		String password = "admin";
		
		String e1 = request.getParameter("email");
		String p1 = request.getParameter("password");
		
		if(email.equals(e1) && password.equals(p1)) {
			HttpSession session = request.getSession();
			session.setAttribute("admin", "true");
			
			request.getRequestDispatcher("navadmin.html").include(request, response);
			request.getRequestDispatcher("admincarousel.html").include(request, response);
			
		}else if (e1.isBlank() | p1.isBlank()) {	
			request.getRequestDispatcher("navhome.html").include(request, response);
			out.println("<div class= 'container'>");
			out.println("<h3 style= 'color:red;'>Username or password is not blank</h>");
			request.getRequestDispatcher("adminloginform.html").include(request, response);
			out.println("</div>");	
		}
		
		else {
			request.getRequestDispatcher("navhome.html").include(request, response);
			out.println("<div class= 'container'>");
			out.println("<h3 style='color:red;'> Username or password error</h3>");
			request.getRequestDispatcher("adminloginform.html").include(request, response);
			out.println("</div>");
		}
		
		request.getRequestDispatcher("footer.html").include(request, response);
		out.close();
	}

}
