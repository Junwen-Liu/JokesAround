<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<script type="text/javascript" src="<%%>"></script>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<%
	System.out.println(request.getContextPath());

		if (session.getAttribute("username") == null) {
			response.sendRedirect("Login.jsp");
		}
	%>
	<table>
		<center>
			Welcome ${username}
			<form action="logout" method="post">
				<input type="submit" value="Logout">
			</form>
		</center>
		<div align="center">
			<table border="1" cellpadding="5">
				You are root user, do you want to initialize the database?
				<form action="initdb">
					<input type="submit" value="Yes" onclick="return confirm('Are you sure you want to continue to initialize database?')" />
				</form>
				<form action="home">
					<input type="submit" value="Cancel" />
				</form>
			</table>
		</div>

	</table>
</body>
</html>