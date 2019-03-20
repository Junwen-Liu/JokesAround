<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>JokesAround</title>
</head>
<style>
hr {
  border: 5px solid #1978a8;
  border-radius: 5px;
}
</style>
<body>
<center>
			Welcome ${username}
			<form action="logout" method="post">
				<input type="submit" value="Logout">
			</form>
			<h1>Comment Joke</h1>
			 <h3>
            <a href="listJoke">Back to Home Page</a>
             <br><br>
        </h3>
        <hr>
			<table border="0" cellpadding="15">
				 <tr>
	                <th>UserName</th>
	                <th>title</th>
	                <th>description</th>
	                <th>Tags</th>
	            </tr>
				<tr>
					<td><%=request.getParameter("userName") %></td>
					<td><%=request.getParameter("title") %></td>
					<td><%=request.getParameter("description") %></td>
					<td><%=request.getParameter("tags") %></td>
				</tr>
				<input type="hidden" name="userName" value="<%=request.getParameter("userName") %>" />
				<input type="hidden" name="score" value="<%=request.getParameter("score") %>" />
				<input type="hidden" name="rev" value="<%=request.getParameter("rev") %>" />
			</table>
			<hr>
			<br>
			<c:if test="${param.userName != username}">
			<c:if test="${param.score==''}">
			<form action="submitReview" method="post">
				<input type="hidden" name="jokeID" value="<%=request.getParameter("jokeID") %>" />
				<font size=4><b>Leave your comment here: </b></font>
				<select name="score">
				  <option value="excellent">excellent</option>
				  <option value="good">good</option>
				  <option value="fair">fair</option>
				  <option value="poor">poor</option>
				</select>
				<br>
				<textarea type="text" name="remark" cols="40" rows="5"></textarea>
				<br>
				<input type="submit" value="Submit" />
			</form>
			</c:if>
			<c:if test="${param.score!=''}">
			<form action="editReview" method="post">
				<input type="hidden" name="jokeID" value="<%=request.getParameter("jokeID") %>" />
				<font size=4>You've already reviewed this joke:</font><br>
				<span style="font-size:23px" ><font color='red' >Score:</font> ${param.score}&nbsp&nbsp&nbsp&nbsp<font color='red'>Remark: </font>${param.rev}</font></span>
				<br><br><br><br>
				<input type="submit" value="Edit your review" />
			</form>
			</c:if>
			</c:if>
			<c:if test="${param.userName == username}">
			Cannot comment your own jokes.
			</c:if>
		</center>
</body>
</html>