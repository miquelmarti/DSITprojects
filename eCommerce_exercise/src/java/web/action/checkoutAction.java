package web.action;

import cart.ShoppingCart;
import entity.Category;
import entity.Product;
import javax.servlet.http.*;
import model.CategoryModel;
import model.ProductModel;
import web.ViewManager;

public class checkoutAction extends Action {

    ProductModel productModel;
    CategoryModel categoryModel;

    public checkoutAction() {
    }

    public void perform(HttpServletRequest req, HttpServletResponse resp) {
        //recuperar informacion de la pregunta
        //proposito de esa accion 
        HttpSession session = req.getSession(true);

        //checkout page needs the cart for showing all the items selected before bought it
        req.setAttribute("carrito", session.getAttribute("carrito"));

        ViewManager.nextView(req, resp, "/view/checkout.jsp");
    }
}
