package web.action;

import cart.ShoppingCart;
import entity.Category;
import entity.Product;
import javax.servlet.http.*;
import model.CategoryModel;
import model.ProductModel;
import web.ViewManager;

public class clearCartAction extends Action {

    ProductModel productModel;
    CategoryModel categoryModel;

    public clearCartAction(ProductModel productModel, CategoryModel categoryModel) {
        this.productModel = productModel;
        this.categoryModel = categoryModel;
    }

    public void perform(HttpServletRequest req, HttpServletResponse resp) {
        //recuperar informacion de la pregunta
        //proposito de esa accion 
        HttpSession session = req.getSession(true);

        ShoppingCart carrito = (ShoppingCart) session.getAttribute("carrito");

        //we remove the cart if the ClearCart link is click
        session.removeAttribute("carrito");
        
        //set the necessary attributes for category page
        req.setAttribute("productsById", productModel.retrieveByCategory(0));
        req.setAttribute("categorySelected", categoryModel.retrieve(0));
        req.setAttribute("categories", categoryModel.retrieveAll());

        ViewManager.nextView(req, resp, "/view/category.jsp");
    }
}
