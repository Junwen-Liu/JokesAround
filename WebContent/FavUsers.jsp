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
			Welcom ${username}
			<form action="logout" method="post">
				<input type="submit" value="Logout">
			</form>
			<h1>Favorite Users</h1>
			 <h3>
            <a href="listJoke">Back to Home Page</a>
            
             <br><hr>
        </h3>
		</center>
		<div align="center">
        <table id="jokeTable" border="0" cellpadding="15">
            <tr>
                <th>userName</th>
                <th>status</th>
                <th>action</th>
            </tr>
            <c:forEach var="user" items="${listUser}">
                <tr>
                	<td><c:out value="${user.username}" /></td>
                	<td><c:out value="${user.isfrnd?'Already Favorite Friend!': 'Not Favorite Friend yet'}" /></td>
                    <td>
                    <c:if test="${user.isfrnd==true}">
                    	<form action="delfavuser" type="post">
				        <input type="hidden" name="friendID" value="<c:out value='${user.userid}' />"/>
						<button>Remove from Favorite User</button>
						</form>
                    </c:if>
                    <c:if test="${user.isfrnd==false}">
	                    <form action="addfavuser" type="post">
				       <input type="hidden" name="friendID" value="<c:out value='${user.userid}' />"/>
						<button>Add to Favorite User</button>
						</form>
                    </c:if>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>   
</body>
</html>