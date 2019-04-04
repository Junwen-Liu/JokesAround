<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
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
		  <li><a  href="highlyRevJokes">Highly reviewed Jokes</a></li>
		  <li><a class="active" href="mostActiveUser">Most Active User since 3/1/2019</a></li>
		  <li><a  href="checkCommonFrnd">Check common friends</a></li>
		  <li><a  href="userNeverPstExcellentJ">User never post excellent jokes</a></li>
		  <li><a  href="userNeverPstPoorR">User never post poor review</a></li>
		  <li><a  href="userAlwaysPstPoorR">User always post poor reviews</a></li>
		  <li><a  href="userNeverRecPoorR">User never receive poor reviews</a></li>
		  <li><a  href="userPairExcellentR">User pair gives excellent reviews</a></li>
		</ul>
		<div style="margin-left:25%;padding:1px 10px;">
		  <h2>List the users who posted the most number of jokes since 3/1/2019</h2>
		  
		</div>


</body>
</html>