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
        <title>Reset password</title>
    </head>
    <body>
        <h1>Reset Password</h1>
        <form method="post" action="reset">
            <p>Please enter your email address to reset your password.</p>
            Email Address: <input type="email" name="email"><br>
            <input type="submit" name="resetPassword"><br>
        </form>
        <br>${message}
    </body>
</html>
