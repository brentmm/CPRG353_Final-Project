<%-- 
    Document   : resetNewPassword
    Created on : Nov 25, 2021, 2:41:32 PM
    Author     : 771684
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>New Password</title>
    </head>
    <body>
        <form method="post" action="reset">
            <h1>Enter a new password</h1>
            Password: <input type="password" name="newPass"><br>
            <input type="submit">
            <input type="hidden" name="uuid" value="${uuid}">
        </form>
            <br>${msg}<br>
        <a href="login">Login</a>
    </body>
</html>
