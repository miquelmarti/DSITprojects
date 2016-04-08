package web.action;

import cart.ShoppingCart;
import javax.servlet.http.*;
import web.ViewManager;

public class viewCartAction extends Action {

    public viewCartAction(){
        
    }

    public void perform(HttpServletRequest req, HttpServletResponse resp) {

        HttpSession session = req.getSession(true);
        //we need to pass the cart to viewcart page
        req.setAttribute("carrito", session.getAttribute("carrito"));

        ViewManager.nextView(req, resp, "/view/viewcart.jsp");

    }
}
