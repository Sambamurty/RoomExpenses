<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html>
<head>
<title>Brain Buster</title>
<style>
body {
    font-family: Arial, sans-serif;
    margin: 0;
    padding: 0;
}

header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    background-color: #f4f4f4;
    padding: 10px 20px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.left-section {
    display: flex;
    align-items: center;
}

.left-section h1 {
    margin: 0;
    margin-right: 20px;
}
.left-section a {
    margin-left: 5px;
    padding: 2px 5px;
    background-color: #0078d4;
    color: white;
    text-decoration: none;
    border: 1px;
    border-radius: 4px;
    cursor: pointer;
}

.button {
    margin-right: 10px;
    padding: 8px 15px;
    background-color: #0078d4;
    color: white;
    text-decoration: none;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    position: relative; /* Enables proper dropdown positioning */
}

.button:hover {
    background-color: #005aa3;
}


.right-section a {
    margin-left: 10px;
    padding: 8px 15px;
    background-color: #0078d4;
    color: white;
    text-decoration: none;
    border: none;
    border-radius: 4px;
    cursor: pointer;
}

.right-section a:hover {
    background-color: #005aa3;
}
</style>
</head>
<body>
    <header>
        <div class="left-section">
            <h1>Brain Buster</h1>
            <a href="/home/createRoom">CreateRoom</a>
            <a href="/home/getListOfRooms">Display Rooms</a>
        </div>
        <div class="right-section">
            <% 
                // Check if the session contains a user attribute
                if (session.getAttribute("SPRING_SECURITY_CONTEXT") != null) { 
            %>
                <a href="/logout">Logout</a>
            <% 
                } else { 
            %>
                <a href="/login">Login</a>
                <a href="/register">Register</a>
            <% 
                } 
            %>
        </div>
    </header>
</body>
</html>
