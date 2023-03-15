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
@WebServlet(value = "/fetch")
public class FetchServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id=Integer.parseInt(req.getParameter("id"));
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		String qry="select * from utility where id=?";
		try {
			Class.forName(UserUtility.DRIVER);
			con=DriverManager.getConnection(UserUtility.URL,UserUtility.USER,UserUtility.PASSWORD);
			pst=con.prepareStatement(qry);
			pst.setInt(1, id);
			rs=pst.executeQuery();
			PrintWriter writer=resp.getWriter();
			RequestDispatcher dispatcher=null;
			if(rs.next())
			{
				dispatcher=req.getRequestDispatcher("print.jsp");
				req.setAttribute("id", rs.getInt("id"));
				req.setAttribute("name", rs.getString(2));
				req.setAttribute("age", rs.getInt(3));
				req.setAttribute("phone", rs.getLong("phone"));
				dispatcher.forward(req, resp);
			}
			else
			{
				writer.write("<html><body><h1>Enter id is invalid </h1></body></html>");
			}
			
		}
		catch(SQLException | ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(con!=null)
			{
				try {
					con.close();
					System.out.println("connection closed ");
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
