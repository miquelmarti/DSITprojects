<%-- 
    Document   : category
    Created on : 12-mar-2015, 9:37:01
    Author     : Agusti
--%>
<%@ page import="entity.Category" %>
<%@ page import="cart.ShoppingCart" %>
<%@ page import="java.util.List" %>
<%@ page import="entity.Product" %>
<%@page contentType="text/html" pageEncoding="windows-1252"%>
<!DOCTYPE html>
<html>
    <%
        Category categorySel = (Category) request.getAttribute("categorySelected");
        String categoryName = categorySel.getName().toUpperCase();
        ShoppingCart carrito = (ShoppingCart) request.getAttribute("carrito");
    %>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title><%=categoryName%> products</title>
        <style>
            body {
                background-color: beige;
            }
        </style>
        <script type="text/javascript">

            // Wait for the page to load
            window.onload = function () {

                //Get a reference to the link on the page
                var a = document.getElementById("viewCart");

                //An alert appears when you try to access to an empty cart
                a.onclick = function () {
                    if (<%=carrito%> == null) {
                        alert("You have no items!\nTry to buy something ;)");
                        return false;
                    } else {
                        return true;
                    }
                }
            }
        </script>
    </head>

    <body>
        <h1>Products for category <%=categoryName%></h1>
        <% //code to show the numbers of products that are in the cart
            String numItems = "";
            if (carrito == null) {
                numItems = "0";
            } else {
                numItems = Integer.toString(carrito.getNumberOfItems());

            }
        %>

        <a href="init.do">Init page</a><br><br>

        <a id="viewCart" href="viewcart.do"><img src="img/cart.gif" 
                                                 alt="carret" >  <%=numItems%> items in cart </img>
        </a>
        <br>
        <br>

        <table style="width: 80%;  border: none">
            <tr>
                <td>
                    <table style="width: 100%; text-align: center" border="1">
                        <tr>
                            <th>Categories:</th>
                        </tr>

                        <%
                            List<Category> categories = (List<Category>) request.getAttribute("categories");
                            //Code to show the categories on the left part of the table
                            for (Category category : categories) {
                        %>

                        <tr>
                            <td>
                                <a href="category.do?categoryid=<%=category.getId()%>">
                                    <%=category.getName()%> </a>
                            </td>
                        </tr>

                        <% }%>
                    </table>
                <td>
                    <table style="width: auto" border="1">
                        <tr> <font size="2" face="Verdana">
                        <%
                            List<Product> products = (List<Product>) request.getAttribute("productsById");
                            //code to show the products for the selected category
                            for (Product product : products) {

                        %>

                        <td>
                            <a href="img/products/<%=product.getName()%>.png">
                                <img src="img/products/<%=product.getName()%>.png"
                                     alt="<%=product.getName()%>" >
                            </a>
                        </td>
                        <td style="text-align:left; padding: 7px; width: 300px">

                            <b><%=product.getName()%></b>
                            <p><%=product.getDescription()%></p>
                            <p style="color:green"><%=product.getPrice().toString()%> €</p>
                        </td>
                        <td style="width: auto; text-align: center">
                            <img src="img/cart.gif" alt="carret" ></img>
                            <form action="neworder.do" method="post">
                                <input type="hidden" name="productid" value ="<%=product.getId()%>">
                                <input type="submit" name="submit" value="Add to cart">
                            </form> 
                        </td>
                </font> </tr>

            <% }%>

        </td>
    </td>
</tr>
</table>
</body>
</html>
