<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<style>
hr {
  border: 5px solid #1978a8;
  border-radius: 5px;
}
</style>
<body>
<center>
			Welcom ${username}
			<form action="logout" method="post">
				<input type="submit" value="Logout">
			</form>
			<hr>
			<h2>Sorry, you've already inserted/review 5 jokes today, please wait until tomorrow to post/review new Jokes.</h2>
        <h3>
            <a href="listJoke">Back to Home Page</a>
             
        </h3>
		</center>
</body>
</html>