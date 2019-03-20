<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>JokesAround</title>
<style>
hr {
  border: 5px solid #1978a8;
  border-radius: 5px;
}
</style>
</head>
<body>
    <center>
        <center>
			Welcom ${username}
			<form action="logout" method="post">
				<input type="submit" value="Logout">
			</form>
			<h1>Joke Form</h1>
        <h3>
            <a href="listJoke">Back to Home Page</a>
             
        </h3>
        <hr>
		</center>
    </center>
    <br>
    <div align="center">
        <c:if test="${joke != null}">
            <form action="update" method="post">
        </c:if>
        <c:if test="${joke == null}">
            <form action="insert" method="post">
        </c:if>
        <table border="0" cellpadding="5">
            <caption>
                <h2>
                    <c:if test="${joke != null}">
                        Edit Joke
                    </c:if>
                    <c:if test="${joke == null}">
                        Add New Joke
                    </c:if>
                </h2>
            </caption>
                <c:if test="${joke != null}">
                    <input type="hidden" name="jokeID" value="<c:out value='${joke.jokeID}' />" />
                    <input type="hidden" name="userID" value="<c:out value='${joke.userID}' />" />
                </c:if>           
            <tr>
                <th>Title: </th>
                <td>
                    <input type="text" name="title" size="45"
                            value="<c:out value='${joke.title}' />"
                        />
                </td>
            </tr>
            <tr>
                <th>Description: </th>
                <td>
                	<textarea type="text" name="description" cols="40" rows="5"
                	value="<c:out value='${joke.description}' />"></textarea>
                </td>
            </tr>
            <tr>
                <th>Tags: </th>
                <td>
                    <input type="text" name="tags" size="40"
                            value="<c:out value='${joke.tags}' />"
                    />
                </td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <input type="submit" value="Submit" />
                </td>
            </tr>
        </table>
        </form>
    </div>   
</body>
</html>