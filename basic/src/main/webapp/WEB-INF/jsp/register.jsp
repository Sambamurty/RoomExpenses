<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html>
<html>
<head>
<title>Registration Page</title>
<style>
body {
	font-family: Arial, sans-serif;
	background: linear-gradient(120deg, #f6d365, #fda085);
	display: flex;
	justify-content: center;
	align-items: center;
	height: 100vh;
	margin: 0;
}

.container {
	background: #fff;
	padding: 25px;
	border-radius: 10px;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
	width: 300px;
	text-align: center;
}

h2 {
	color: #333;
	margin-bottom: 20px;
}

input[type="text"], input[type="email"], input[type="password"] {
	box-sizing: border-box;
	/* Include padding and borders in the element width/height */
	width: 100%; /* Ensure the input doesn't overflow */
	padding: 10px;
	margin: 10px 0;
	border: 1px solid #ddd;
	border-radius: 5px;
	transition: border-color 0.3s ease;
}

input[type="text"]:focus, input[type="email"]:focus, input[type="password"]:focus
	{
	border-color: #fda085;
}

input[type="submit"] {
	background: #fda085;
	color: #fff;
	padding: 10px;
	width: 100%;
	border: none;
	border-radius: 5px;
	cursor: pointer;
	transition: background-color 0.3s ease;
}

input[type="submit"]:hover {
	background: #f6d365;
}

.error {
	color: red;
	margin: 10px 0;
}
</style>
<script>
	function validateForm() {
		var password = document.getElementById("password").value;
		var confirmPassword = document.getElementById("confirmPassword").value;
		var errorElement = document.getElementById("error");

		if (password !== confirmPassword) {
			errorElement.textContent = "Passwords do not match!";
			return false;
		}
		errorElement.textContent = "";
		return true;
	}
</script>
</head>
<body>
	<div class="container">
		<h2>Register</h2>
		<form:form action="register" method="post"
			onsubmit="return validateForm()" ModelAttribute="user">
			<div id="error" class="error"></div>
				<input type="text" id="username" name="username" placeholder="Username" required> 
				<input type="email" id="email" name="email" placeholder="Email" required> 
				<input type="password" id="password" name="password" placeholder="Password" required> 
				<input type="password" id="confirmPassword" name="confirmPassword" placeholder="Confirm Password" required>
				<div>
					<label>
						<input type="checkbox" name="subscribe" value="true"/> Subscribe
					</label>
				</div>
			<input type="submit" value="Register">
		</form:form>
	</div>
</body>
</html>
