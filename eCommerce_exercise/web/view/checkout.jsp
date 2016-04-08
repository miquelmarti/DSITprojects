<%-- 
    Document   : checkout
    Created on : 18-mar-2015, 19:36:15
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
        <title>JSP Page</title>
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
    </head>
    <body>
        <h1>Our website uses PayPal for purchases</h1>

        <%
            ShoppingCart carrito = (ShoppingCart) request.getAttribute("carrito");
            DecimalFormat df = new DecimalFormat("0.00"); //to show the prices with 2 decimals
        %>

        <p>Here there is a summary of the items you will buy:</p>

        <table>
            <tr>
                <th>Product</th>
                <th>Price</th>
                <th>Quantity</th>
                <th>Total Price</th>
            </tr>

            <%
                //show the items added to the cart
                for (ShoppingCartItem cartItem : carrito.getItems()) {
            %>

            <tr style="text-align: center">
                <td><img src="img/products/<%=cartItem.getProduct().getName()%>.png"
                         alt="<%=cartItem.getProduct().getName()%>"></td>
                <td><%=df.format(cartItem.getProduct().getPrice())%> €/unit</td>
                <td><%=cartItem.getQuantity()%></td>
                <td><%=df.format(cartItem.getTotal())%> €</td>
            </tr>

            <%
                }%>
            <tr>
                <td colspan="5" style="text-align: right"><b>Total: <%=df.format(carrito.getTotal())%> €</b>

                    <br><br>
                    <!-- SINGLE PAYMENT
                                        <form name="_xclick" action="https://www.paypal.com/es/cgi-bin/webscr" method="post">
                                            <input type="hidden" name="cmd" value="_xclick">
                                            <input type="hidden" name="business" value="aguscsp@gmail.com">
                                            <input type="hidden" name="currency_code" value="EUR">
                                            <input type="hidden" name="item_name" value="DSIT purchase">
                                            <input type="hidden" name="amount" value="<%=Double.toString(carrito.getTotal())%>">
                                            <input type="image" src="http://www.paypal.com/es_ES/i/btn/x-click-but01.gif" border="0" name="submit" alt="Realice pagos con PayPal: es rápido, gratis y seguro">
                                        </form>
                    -->
                    <form name="_xclick" action="https://www.paypal.com/es/cgi-bin/webscr" method="post">
                        <input type="hidden" name="cmd" value="_cart">
                        <input type="hidden" name="upload" value="1">
                        <input type="hidden" name="business" value="aguscsp@gmail.com">
                        <input type="hidden" name="currency_code" value="EUR">
                        <%
                            int i = 1;
                            for (ShoppingCartItem cartItem : carrito.getItems()) {

                        %>

                        <input type="hidden" name="item_name_<%=i%>" value="<%=cartItem.getProduct().getName()%>">
                        <input type="hidden" name="amount_<%=i%>" value="<%=cartItem.getProduct().getPrice()%>">
                        <input type="hidden" name="quantity_<%=i%>" value="<%=cartItem.getQuantity()%>">

                        <%
                                i++;
                            }%>
                        <input type="image" src="http://www.paypal.com/es_ES/i/btn/x-click-but01.gif" border="0" name="submit" alt="Realice pagos con PayPal: es rápido, gratis y seguro">
                    </form>

                </td></tr>
        </table>
    </body>
</html>
