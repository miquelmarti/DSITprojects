<%-- 
    Document   : login
    Created on : 20-mar-2015, 11:54:29
    Author     : Agusti
--%>

<%@page contentType="text/html" pageEncoding="windows-1252"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>Log in to the grocery shop</title>
        <style>
            body {
                background-color: beige;
            }
        </style>
    </head>
    <body>
        <h1>Welcome to the shop! Please enter your name and password:</h1>
        <br>
        <table>
            <form method="post" action="j_security_check">
                <tr>
                    <td>
                        User: 
                    </td>
                    <td>
                        <input type="text" name="j_username"> 
                    </td>
                </tr>
                <tr>
                    <td>
                        Password: 
                    </td>
                    <td>
                        <input type="password" name= "j_password">
                    </td>
                </tr>
                <tr>
                    <td>
                        <input type="submit" value="Log in">
                    </td>
                </tr>
            </form>
        </table>

    </body>
</html>
