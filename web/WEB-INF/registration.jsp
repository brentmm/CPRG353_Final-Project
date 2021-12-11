<%-- 
    Document   : registration
    Created on : Dec 5, 2021, 12:13:47 PM
    Author     : 771684
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>HOME nVentory: Registration</title>
    </head>
    <body>
        <h1>User Registration</h1>
        <p>Create an account:</p>
        <form method="POST" action="Register">
            <label>Email:</label>
            <input class="input" type="email" name="reg_email" placeholder="Email"><br>
            <label>Password:</label>
            <input  class="input"type="password" name="reg_password" placeholder="Password"><br>           
            <label>First name:</label>
            <input class="input" type="text" name="reg_fName" placeholder="First Name"><br>
            <label>Last name:</label>
            <input class="input" type="text" name="reg_lName" placeholder="Last Name"><br>
            <br>
            <input type="submit" value="Register"> 
            <input type="hidden" name="userAction" value="regUser">
            <br>
            ${message}
        </form>       
        <br>
        <a href="login">Login</a>  
    </body>
</html>
