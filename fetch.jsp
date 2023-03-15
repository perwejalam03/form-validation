<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
	String name=(String) session.getAttribute("name");
	int id=(int) session.getAttribute("id");
	int age=(int) session.getAttribute("age");
	long phone=(long) session.getAttribute("phone");
	String password=(String) session.getAttribute("password");
	if(name!=null)
	{
	%>
	<h1>Your details displayed below</h1>
<h1>ID: <%=id %></h1>
<h1>Name: <%=name%></h1>
<h1>Age: <%=age%></h1>
<h1>Phone Number: <%=phone%></h1>
	<%
	}
	else{
		response.sendRedirect("login.jsp");
	}
	%>
</body>
</html>