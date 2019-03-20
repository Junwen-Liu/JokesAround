<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
			<h1>User Profile</h1>
			 <h3>
            <a href="listJoke">Back to Home Page</a>
             <br>
        </h3>
		</center>
		<div align="center">
		<hr>
		<h2>User Name: <font color='red'>${param.userName }</font></h2>
		<c:if test='${param.userName!=username }'>
		<c:if test="${param.isfrnd ==0 }">
		<form action="addfavuser" type="post">
       <input type="hidden" name="friendID" value="<c:out value='${param.userID}' />" />
		<button>Add to Favorite User</button>
		</form>
		</c:if>
		<c:if test="${param.isfrnd !=0 }">
		<form action="delfavuser" type="post">
        <input type="hidden" name="friendID" value="<c:out value='${param.userID}' />" />
		<button>Remove from Favorite User</button>
		</form>
		</c:if>
		</c:if>
		<hr>
		</div>
		<div align="center">
        <table id="jokeTable" border="0" cellpadding="15">
            <tr>
                <th>userName</th>
                <th>title</th>
                <th>description</th>
                <th>createDate</th>
                <th>Tags</th>
                <th>Favorite</th>
            </tr>
            <c:forEach var="joke" items="${listJoke}">
                <tr>
                	<td><c:out value="${joke.userName}" /></td>
                    <td><c:out value="${joke.title}" /></td>
                    <td><c:out value="${joke.description}" /></td>
                    <td><c:out value="${joke.createDate}" /></td>
                    <td><u><c:out value="${joke.tags}" /></u></td>
                    <td>
                    <c:if test="${joke.userName==username}">
                    	<button>Edit</button>
                   		<button>Delete</button>
                    </c:if>
                    <c:if test="${joke.userName!=username}">
                    <form action="delfavjoke" type="post">
                    <input type="hidden" name="jokeID" value="<c:out value='${joke.jokeID}' />" />
                    <c:if test="${joke.fav!='0'}"><button>Delete from Favorite Joke</button></c:if>
                    </form>
                    <form action="addfavjoke" type="post">
                    <input type="hidden" name="jokeID" value="<c:out value='${joke.jokeID}' />" />
                    <c:if test="${joke.fav=='0'}"><button>Add to Favorite Joke</button></c:if>
                    </form>
                    </c:if>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>   
</body>
</html>