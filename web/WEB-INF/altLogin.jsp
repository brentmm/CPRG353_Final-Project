<%-- 
    Document   : reset
    Created on : Nov 25, 2021, 1:43:55 PM
    Author     : 771684
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>One Time Login</title>
    </head>
    <body>
        <h1>One Time Login</h1>
        <form method="post">
            <p>Please enter your email address to get a one time login link.</p>
            Email Address: <input type="email" name="email"><br>
            <input type="submit" name="Submit"><br>
            <input type="hidden" name="loginType" value="alternate">
        </form>
        <br>${message}
    </body>
</html>
