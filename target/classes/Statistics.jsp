<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Statistic</title>
<style>
hr {
	border: 5px solid #1978a8;
	border-radius: 5px;
}

body {
	margin: 0;
}

ul {
	list-style-type: none;
	margin: 0;
	padding: 0;
	width: 25%;
	background-color: #f1f1f1;
	position: absolute;
	top: 17S0px;
	height: 100%;
	overflow: auto;
}

li a {
	display: block;
	color: #000;
	padding: 8px 16px;
	text-decoration: none;
}

li a.active {
	background-color: #1978a8;
	color: white;
}

li a:hover:not (.active ) {
	background-color: #555;
	color: white;
}
.title{
border-right: 2px solid black;
border-left: 2px solid black;
border-top:2px solid black;
}
.toprow{
border-top: 5px solid #58a2aa;
}
input[type=text] {
  padding: 7px 20px;
  box-sizing: border-box;
  border: 1px solid black;
  border-radius: 4px;
}
</style>
</head>
<body>
	<center>
		Welcome ${username}
		<form action="logout" method="post">
			<input type="submit" value="Logout">
		</form>
		<h1>Statistics</h1>
		<h3>
			<a href="listJoke">Back to Home Page</a>
		</h3>
	</center>
	<hr>
	<ul>
		<li><a class="active" href="stats">Users posted multi-jokes
				on same day</a></li>
		<li><a href="highlyRevJokes">Highly reviewed Jokes</a></li>
		<li><a href="mostActiveUser">Most Active User since 3/1/2019</a></li>
		<li><a href="checkCommonFrnd">Check common friends</a></li>
		<li><a href="userNeverPstExcellentJ">User never post
				excellent jokes</a></li>
		<li><a href="userNeverPstPoorR">User never post poor review</a></li>
		<li><a href="userAlwaysPstPoorR">User always post poor
				reviews</a></li>
		<li><a href="userNeverRecPoorR">User never receive poor
				reviews</a></li>
		<li><a href="userPairExcellentR">User pair gives excellent
				reviews</a></li>
	</ul>
	<div style="margin-left: 25%; padding: 1px 10px;">
		<c:if test="${not empty listUsers}">
		<h3>Here is the list of users who posted at least two jokes on same day, respectively with specified tag in two jokes:</h3>
			<table border="0" cellpadding="7">
			<th class="title">UserName</th>
                <th class="title">createdDate</th>
			<c:forEach var="user" items="${listUsers}">
				<tr><td class="toprow">${user.username}</td>
				<td class="toprow">${user.createdDateJ}</tr>
			</c:forEach>
			</table>
		</c:if>
		<c:if test="${empty listUsers}">
			<form action="stats" method="post">
				<table border="0" cellpadding="5">
					<h3>Get list of users who posted at least two jokes on same day, respectively with specified tag in two jokes</h3>
					</caption>
					<tr>
						<th>Tag one:</th>
						<td><input type="text" name="tag1" cols="35"></td>
					</tr>
					<tr>
						<th>Tag two:</th>
						<td><input type="text" name="tag2" cols="35"></td>
					</tr>
					<tr>
						<td colspan="2" align="center"><input type="submit"
							value="Submit" /></td>
					</tr>
				</table>
			</form>
		</c:if>

	</div>


</body>
</html>