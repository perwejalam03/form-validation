package org.jsp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		long phone=Long.parseLong(req.getParameter("ph"));
		String password=req.getParameter("ps");
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		String qry="select * from utility where phone=? and password=?";
		try
		{
			Class.forName(UserUtility.DRIVER);
			con=DriverManager.getConnection(UserUtility.URL,UserUtility.USER,UserUtility.PASSWORD);
			pst=con.prepareStatement(qry);
			pst.setLong(1, phone);
			pst.setString(2, password);
			rs=pst.executeQuery();
			RequestDispatcher dispatcher=null;
			if(rs.next())
			{
				dispatcher=req.getRequestDispatcher("home.jsp");
				HttpSession session=req.getSession();
				session.setAttribute("id", rs.getInt("id"));
				session.setAttribute("name", rs.getString(2));
				session.setAttribute("age", rs.getInt(3));
				session.setAttribute("phone", rs.getLong(4));
				session.setAttribute("password", rs.getString(5));
				dispatcher.forward(req, resp);
			}
			else {
				PrintWriter writer=resp.getWriter();
				writer.write("<html><body><h1>Invalid phone or password </h1></body></html>");
				dispatcher=req.getRequestDispatcher("login.jsp");
				dispatcher.include(req, resp);
			}
		}
		catch(SQLException | ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		finally {
			if(con!=null)
			{
				try {
					con.close();
					System.out.println("Connection closed");
				}
				catch(SQLException e)
				{
					e.printStackTrace();
				}
			}
			if(pst!=null)
			{
				try {
					pst.close();
				}
				catch(SQLException e)
				{
					e.printStackTrace();
				}
			}
			if(rs!=null)
			{
				try {
					rs.close();
				}
				catch(SQLException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
}
