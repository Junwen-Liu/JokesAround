<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<style>
hr {
  border: 5px solid #1978a8;
  border-radius: 5px;
}
</style>
</head>
<body>
<center>
<form action="loginuser" method="post">
		<table>
		<h1>JokesAround</h1>
		<hr><br>
			<tr>
				<td>User Name</td>
				<td><input type="text" name="username"></td>
			</tr>
			<tr>
				<td>Password</td>
				<td><input type="password" name="password"></td>
			</tr>
			<tr><td>&nbsp</td></tr>
			<tr>
				<td></td>
				<td><input type="submit" value="Login"></td>
			</tr>
			<tr>
				<td></td>
				<td><a href="signup">Or sign Up As New User</a></td>
			</tr>
		</table>
	</form>
</center>
</body>
</html>