package org.winters.tomcat8tests;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.sql.DataSource;

/**
 * Servlet implementation class DirectoryRetrieval
 */
@WebServlet("/DirectoryRetrieval")
public class DirectoryRetrieval extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public DirectoryRetrieval() {
        
    }

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		performTask(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		performTask(request, response);
	}

	private void performTask(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("Order Details");
		out.println("<br/>");
		out.println(getOrderDetails());
	}

	public String getOrderDetails() {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		try {
			InitialContext ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/productdb");

			

			conn = ds.getConnection();

			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM orders");

			while (rs.next()) {
				String id = rs.getString("id");
				String name = rs.getString("name");
				String quantity = rs.getString("quantity");
				String price = rs.getString("totalprice");
				sb.append("ID: " + id + ", Name: " + name
						+ ", Quantity: " + quantity + ", Total Price: " + price +"<br/>");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
			try { if (st != null) st.close(); } catch (SQLException e) { e.printStackTrace(); }
			//try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
		}
		return sb.toString();
	}

}
