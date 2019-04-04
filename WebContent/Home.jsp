<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>JokesAround</title>
<style>
a:link {
  text-decoration: none;
}

a:visited {
  text-decoration: none;
}

a:hover {
  text-decoration: underline;
}

a:active {
  text-decoration: underline;
}

.rev{
color:#913252; 
font-style: italic; 
font-weight: bold;
font-size:14px;
background: lightgray;

}

.revtr td{
line-height: 11px;
}

.toprow{
border-top: 5px solid #58a2aa;
}
.hidden{
opacity:0;
}
.title{
border-right: 2px solid black;
border-left: 2px solid black;
border-top:2px solid black;
}
input[type=text] {
  padding: 7px 20px;
  box-sizing: border-box;
  border: 2px solid black;
  border-radius: 4px;
}
hr {
  border: 5px solid #1978a8;
  border-radius: 5px;
}
</style>
</head>
<body>
<script>
function searchFunc() {
  var input, filter, table, tr, td, i, txtValue;
  input = document.getElementById("tagInput");
  filter = input.value.toUpperCase();
  table = document.getElementById("jokeTable");
  tr = table.getElementsByTagName("tr");
  for (i = 0; i < tr.length; i++) {
    td = tr[i].getElementsByTagName("u")[0];
    if (td) {
      txtValue = td.textContent || td.innerText;
      if (txtValue.toUpperCase().indexOf(filter) > -1) {
        tr[i].style.display = "";
      } else {
        tr[i].style.display = "none";
      }
    }       
  }
}
</script>
<%
		if (session.getAttribute("username") == null) {
			response.sendRedirect("Login.jsp");
		}
	%>
	<table>
		<center>
			Welcom ${username}
			<form action="logout" method="post">
				<input type="submit" value="Logout">
			</form>
			<h1>JokesAround</h1>
		<hr>
        <h3>
            <a href="new">Add New Joke</a>
            &nbsp;&nbsp;&nbsp;
            <a href="listfavuser">My Favorite Users</a>
            &nbsp;&nbsp;&nbsp;
            <a href="listJoke">List All Jokes</a>
             &nbsp;&nbsp;&nbsp;
            <a href="stats">Statistics</a>
        </h3>
        <hr>
		</center>
	</table>
	
	<BR>
	<div align="center">
		<font >Search by Tag:</font>
		<input type="text" id="tagInput" size='35' onkeyup="searchFunc()" placeholder="Search for jokes by tag..." title="Type in a tag">
        <table id="jokeTable" border="0" cellpadding="7">
        <br><br><BR>
            <tr>
                <th class="title">UserName</th>
                <th class="title">Title</th>
                <th class="title">Description</th>
                <th class="title">CreateDate</th>
                <th class="title">Tags</th>
                <th class="title">Status</th>
                <th class="title">Action</th>
            </tr>
            <c:forEach var="joke" items="${listJoke}">
                <tr>
                <c:url value="UserProfile" var="userProfileUrl">
                	<c:param name="userName" value="${joke.userName }" />
                	<c:param name="userID" value="${joke.userID }" />
                	<c:param name="isfrnd" value="${joke.isfrnd }"/>
				</c:url>
                <c:url value="CommentJoke.jsp" var="commentUrl">
                	<c:param name="jokeID" value="${joke.jokeID }" />
                	<c:param name="userName" value="${joke.userName }" />
				  	<c:param name="title" value="${joke.title }" />
				  	<c:param name="description" value="${joke.description}" />
				  	<c:param name="tags" value="${joke.tags}" />
				  	<c:param name="score" value="${joke.score }"/>
				  	<c:param name="rev" value="${joke.rev }"/>
				</c:url>
                    <td class="toprow"><b><a href="${userProfileUrl}"><c:out value="${joke.userName}" /></a></b></td>
                    <td class="toprow"><a href="${commentUrl}"><c:out value="${joke.title}" /></a></td>
                    <td class="toprow"><a href="${commentUrl}"><c:out value="${joke.description}" /></a></td>
                    <td class="toprow"><a href="${commentUrl}"><c:out value="${joke.createDate}" /></a></td>
                    <td class="toprow"><a href="${commentUrl}"><u><c:out value="${joke.tags}" /></u></a></td>
                    <c:if test="${joke.userName==username}">
                     <td class="toprow"><a href="${commentUrl}">My Joke</a></td>
                    <td class="toprow">
                    	<button>Edit</button>
                   		<button>Delete</button>
                   	</td>
                    </c:if>
                    <c:if test="${joke.userName!=username}">
                    <c:if test="${joke.fav!='0'}">
                    <td class="toprow"><a href="${commentUrl}">Already Favorite Joke!</a></td>
                    <td class="toprow">
                    <form action="delfavjoke" type="post">
				        <input type="hidden" name="jokeID" value="<c:out value='${joke.jokeID}' />"/>
						<button>Remove from Favorite Joke</button>
					</form>
					</td>
                    </c:if>
                    <c:if test="${joke.fav=='0'}">
                    <td class="toprow"><a href="${commentUrl}">Not Favorite Joke yet</a></td>
                    <td class="toprow">
                    <form action="addfavjoke" type="post">
				        <input type="hidden" name="jokeID" value="<c:out value='${joke.jokeID}' />"/>
						<button>Add to Favorite Joke</button>
					</form>
					</td>
                    </c:if>
                    </c:if>
                    </td>
                </tr>
                <c:if test="${not empty joke.listReview}">
                <c:forEach var="review" items="${joke.listReview}">
                <tr class="revtr"><td class="hidden"><u><c:out value="${joke.tags}" /></u></td>
                <td class="rev">ReviewedBy:&nbsp&nbsp<c:out value="${review.userName}" /></td>
                <td class="rev">Score:&nbsp&nbsp<c:out value="${review.score}" /></td>
                <td class="rev">Remark:&nbsp&nbsp<c:out value="${review.remark}" /></td>
                <td class="rev">CreatedAt:&nbsp&nbsp<c:out value="${review.createdDate}" /></td>
                </tr>
                </c:forEach>
                </c:if>
                
            </c:forEach>
        </table>
    </div>   
</body>
</html>