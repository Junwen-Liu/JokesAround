<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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

.align {
	text-align: right;
}

.required:after {
	content: " *";
	color: #e32;
}

.userExist:after {
	content: "Username already exists!";
	color: #e32;
}

.emailExist:after {
	content: "Email alreday exists!";
	color: #e32;
}

.pwNotMatch:after {
	content: "Password not match!";
	color: #e32;
}
</style>
</head>
<body>
	<script>
		// Function to check Whether both passwords 
		// is same or not. 
		function checkBeforeSubmit(form) {
			username = form.username.value;
			password1 = form.password1.value;
			password2 = form.password2.value;
			email = form.email.value;
			var userNameTd = document.getElementById("userNameTd");
			var emailTd = document.getElementById("emailTd");

			if (username == '') {
				alert("Please enter UserName")
				return false;
			} else if (email == '') {
				alert("Please enter Email")
				return false;
			}
			//check if username already exist
			else if(userNameTd.classList.contains("userExist"))
			{
				alert("Please choose another userName!")
				return false;
			}
			//check if email already exist
			else if(emailTd.classList.contains("emailExist"))
			{
				alert("Please choose another email!")
				return false;
			}
			// If password not entered 
			else if (password1 == '') {
				alert("Please enter Password")
				return false;
			}
			// If confirm password not entered 
			else if (password2 == '') {
				alert("Please enter confirm password")
				return false;
			}
			// If Not same return False.     
			else if (password1 != password2) {
				alert("Password did not match: Please try again...")
				return false;
			}
			// If same return True. 
			else {
				alert("Sign up successfully! Welcome to JokeAround!")
				return true;
			}
		}
		
		function checkUsername() {
			var regUserNames = document.getElementById("regUserNames");
			var userName = document.getElementById("username");
			var userNameTd = document.getElementById("userNameTd");
			if (regUserNames.value.includes(userName.value) && userName.value != "")
			{
				userNameTd.classList.add("userExist");
			}else{
				userNameTd.classList.remove("userExist");
			}
			
		}
		
		function checkEmail() {
			var regUserEmails = document.getElementById("regUserEmails");
			var userEmail = document.getElementById("email");
			var userEmailTd = document.getElementById("emailTd");
			if (regUserEmails.value.includes(userEmail.value) && userEmail.value != "")
			{
				userEmailTd.classList.add("emailExist");
			}else{
				userEmailTd.classList.remove("emailExist");
			}
		}
		
		function checkConfirmPw() {
			var password1 = document.getElementById("password1");
			var password2 = document.getElementById("password2");
			var confrimpw = document.getElementById("confirmpw");
			if (password1.value != password2.value)
			{
				confrimpw.classList.add("pwNotMatch");
			}else{
				confrimpw.classList.remove("pwNotMatch");
			}
		}
	</script>
	<center>
		<center>
			<c:if test="${username != null}"> 
			Welcome ${username}
			<form action="logout" method="post">
					<input type="submit" value="Logout">
				</form>
			</c:if>
			<h1>User Form</h1>
			<h3>
				<a href="loginpage">Back to Login Page</a>

			</h3>
			<hr>
		</center>
	</center>
	<br>
	<div align="center">
		<c:if test="${user != null}">
			<form action="editUser" method="post"
				onSubmit="return checkBeforeSubmit(this)">
		</c:if>
		<c:if test="${user == null}">
			<form action="insertUser" method="post"
				onSubmit="return checkBeforeSubmit(this)">
		</c:if>
		<table border="0" cellpadding="5">
			<caption>
				<h2>
					<c:if test="${user != null}">
                        Edit My User Info
                    </c:if>
					<c:if test="${user == null}">
                        Sign Up As New User
                    </c:if>
				</h2>
			</caption>
			<c:if test="${user != null}">
				<input type="hidden" name="userID"
					value="<c:out value='${user.userid}' />" />
			</c:if>
			<c:if test="${registerUsers != null}">
				<c:forEach items="${registerUsers}" var="regUser" varStatus="stat">
					<c:set var="regUserNames"
						value="${stat.first ? '' : regUserNames};${regUser.username}" />
				</c:forEach>
				<input type="hidden" name="regUserNames" id="regUserNames"
					value="<c:out value='${regUserNames}' />" />

				<c:forEach items="${registerUsers}" var="regUser" varStatus="stat">
					<c:set var="regUserEmails"
						value="${stat.first ? '' : regUserEmails};${regUser.email}" />
				</c:forEach>
				<input type="hidden" name="regUserEmails" id="regUserEmails"
					value="<c:out value='${regUserEmails}' />" />
			</c:if>
			<tr>
				<th class="align required">UserName:</th>
				<td id="userNameTd"><c:if test="${user != null}">
						<c:out value='${user.username}' />
					</c:if> <c:if test="${user == null}">
						<input type="text" name="username" id="username" cols="40"
							onblur="checkUsername()" />
					</c:if></td>
			</tr>
			<tr>
				<th class="align required">Email:</th>
				<td id="emailTd"><input type="text" name="email" id="email" cols="40"
					onblur="checkEmail()" value="<c:out value='${user.email}' />" /></td>
			</tr>
			<tr>
				<th class="align">First Name:</th>
				<td><input type="text" name="firstname" cols="40"
					value="<c:out value='${user.firstname}' />" /></td>
			</tr>
			<tr>
				<th class="align">Last Name:</th>
				<td><input type="text" name="lastname" cols="40"
					value="<c:out value='${user.lastname}' />" /></td>
			</tr>
			<tr>
				<th class="align required">Password:</th>
				<td><input type="password" name="password1" id="password1" size="30" /></td>
			</tr>
			<tr>
				<th class="align required">Comfirm Password:</th>
				<td  id="confirmpw"><input type="password" name="password2" id="password2" size="30" onblur="checkConfirmPw()" /></td>
			</tr>
			<tr>
				<td colspan="2" align="center"><input type="submit"
					value="Submit" /></td>
			</tr>
		</table>
		</form>
	</div>
</body>
</html>