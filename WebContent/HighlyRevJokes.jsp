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

li a:hover:not(.active) {
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
.select{
width: 130px !important;
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
		  <li><a  href="stats">Users posted multi-jokes on same day</a></li>
		  <li><a class="active" href="highlyRevJokes">Highly reviewed Jokes</a></li>
		  <li><a  href="mostActiveUser">Most Active User since 3/1/2019</a></li>
		  <li><a  href="checkCommonFrnd">Check common friends</a></li>
		  <li><a  href="userNeverPstExcellentJ">User never post excellent jokes</a></li>
		  <li><a  href="userNeverPstPoorR">User never post poor review</a></li>
		  <li><a  href="userAlwaysPstPoorR">User always post poor reviews</a></li>
		  <li><a  href="userNeverRecPoorR">User never receive poor reviews</a></li>
		  <li><a  href="userPairExcellentR">User pair gives excellent reviews</a></li>
		</ul>
		<div style="margin-left:25%;padding:1px 10px;">
		  <c:if test="${not empty listJokes}">
		  <h3>Here is the list of jokes by selected user, such that all comments are "Excellent" or "good"</h3>
			<table border="0" cellpadding="7">
			<th class="title">Title</th>
                <th class="title">Description</th>
                <th class="title">CreatedDate</th>
                <th class="title">Score</th>
			<c:forEach var="joke" items="${listJokes}">
				<tr><td class="toprow">${joke.title}</td>
				<td class="toprow">${joke.description}</td>
				<td class="toprow">${joke.createDate}</td>
				<td class="toprow">${joke.score}</td></tr>
			</c:forEach>
			</table>
		</c:if>
		<c:if test="${empty listJokes}">
			<form action="highlyRevJokes" method="post">
				<table border="0" cellpadding="5">
					<h3>Get all jokes by selected user, such that all comments are "Excellent" or "good"</h3>
					</caption>
					<h4>Select one of registered user in dropdown menu:</h4>
					<select class="select" name="selectedUser">
					    <c:forEach var="user" items="${listRegUsers}">
					        <option value="${user.username}"><c:out value="${user.username}" /></option>
					    </c:forEach>
					</select>
					<tr><td></td></tr>
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