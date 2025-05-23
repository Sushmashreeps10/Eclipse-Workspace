package LoginBean;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ControllerServlet {
	void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	response.setContentType("text/html");

	PrintWriter out=response.getWriter();

	String name=request.getParameter("name");

	String password=request.getParameter("password");
	try{ 
	    Class.forName("com.mysql.jdbc.Driver"); 
	    Connection  con=DriverManager.getConnection("jdbc:mysql://localhost:3306/project","root","sushma"); 
	    PreparedStatement st = con.prepareStatement("select * from register where name=? and password=?");
	    
	  
	     st.setString(1, name);
	     st.setString(2, password);
		

	LoginBean bean=new LoginBean();

	bean.setName(name);

	bean.setPassword(password);

	request.setAttribute("bean",bean);

	boolean status=bean.validate();

	 ResultSet rs = st.executeQuery();
		if (rs.next()) {
			response.setContentType("text/html");
			out.print("Welcome " + name);
			RequestDispatcher rd = request.getRequestDispatcher("/login-success.jsp");
			rd.include(request, response);

			
		} else {
			response.setContentType("text/html");
			out.print("Invalid email or password");
			RequestDispatcher rd = request.getRequestDispatcher("/login-error.jsp");
			rd.include(request, response);

		}

}
catch (Exception e) {
	System.out.println(e);
	response.setContentType("text/html");
	out.print("Invalid email or password");
	RequestDispatcher rd = request.getRequestDispatcher("/login-error.jsp");
	rd.include(request, response);


}
}
}
