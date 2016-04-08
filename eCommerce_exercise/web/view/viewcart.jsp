<%-- 
    Document   : viewcart
    Created on : 17-mar-2015, 19:17:34
    Author     : Agusti
--%>

<%@page import="java.text.DecimalFormat"%>
<%@page import="cart.ShoppingCartItem"%>
<%@page import="cart.ShoppingCart"%>
<%@page contentType="text/html" pageEncoding="windows-1252"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>View Cart Page</title>
        <style>
            table,td,th{
                border: 1px solid black;
            }
            td,th{
                padding: 7px;
            }
            body {
                background-color: beige;
            }
        </style>
        <script>

            //function to validate the form when updating the quantity of an item
            function validate(form) {
                var quantity = form.itemQuantity.value;
                if (quantity == 0) {
                    return confirm("Are you sure you want to delete this product from your cart?");
                } else if (quantity < 0) {
                    alert("You can not have negative items! ;)");
                    form.itemQuantity.value = 1;
                    return false;
                } else
                    return true;

            }
        </script>
    </head>
    <body>
        <h1>Shopping Cart <img src="img/cart.gif" alt="cart" ></h1>

        <%
            ShoppingCart carrito = (ShoppingCart) request.getAttribute("carrito");
            DecimalFormat df = new DecimalFormat("0.00"); //show prices with 2 decimals
        %>

        <p>Your shopping cart contains <%=carrito.getNumberOfItems()%> items</p>

        <a href="clearcart.do">Clear Cart</a>
        <br><br>
        <a href="checkout.do">Proceed checkout</a>
        <br><br>
        <a href="category.do?categoryid=0">Continue shopping</a>
        <br><br>
        <table>
            <tr>
                <th>Product</th>
                <th>Description</th>
                <th>Price</th>
                <th>Quantity</th>
                <th>Total Price</th>
            </tr>

            <%
                for (ShoppingCartItem cartItem : carrito.getItems()) {

            %>

            <tr style="text-align: center">
                <td><img src="img/products/<%=cartItem.getProduct().getName()%>.png"
                         alt="<%=cartItem.getProduct().getName()%>"></td>
                <td style="width: 300px"><b><%=cartItem.getProduct().getName()%></b>
                    <br>
                    <%=cartItem.getProduct().getDescription()%></td>
                <td><%=df.format(cartItem.getProduct().getPrice())%> €/unit</td>
                <td>
                    <form name="updateForm" action="updatecart.do" onsubmit="return validate(this)" method="post">
                        <input type="hidden" name="productid" value="<%=cartItem.getProduct().getId()%>">
                        <input type="number" style="text-align: right" name="itemQuantity" value="<%=cartItem.getQuantity()%>" required>
                        <input type="submit" value="update">
                    </form></td>
                <td><%=df.format(cartItem.getTotal())%> €</td>
            </tr>

            <%
                }%>

            <tr>
                <td colspan="5" style="text-align: right"><b>Total: <%=df.format(carrito.getTotal())%> €</b></td>
            </tr>

        </table>

    </body>
</html>
