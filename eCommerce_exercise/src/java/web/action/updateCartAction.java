package web.action;

import cart.ShoppingCart;
import entity.Category;
import entity.Product;
import javax.servlet.http.*;
import model.CategoryModel;
import model.ProductModel;
import web.ViewManager;

public class updateCartAction extends Action {

    ProductModel productModel;
    CategoryModel categoryModel;

    public updateCartAction(ProductModel productModel, CategoryModel categoryModel) {
        this.productModel = productModel;
        this.categoryModel = categoryModel;
    }

    public void perform(HttpServletRequest req, HttpServletResponse resp) {
        //recuperar informacion de la pregunta
        //proposito de esa accion 
        HttpSession session = req.getSession(true);

        //information needed to proccess the updatecart action
        ShoppingCart carrito = (ShoppingCart) session.getAttribute("carrito");
        Product product = productModel.retrieve(Integer.parseInt(req.getParameter("productid")));
        int newQuantity = Integer.parseInt(req.getParameter("itemQuantity"));

        //if the quantity is set to zero we need to delete the product from the cart
        if (newQuantity == 0) {
            carrito.deleteItem(product);
        } else {//if not, we just update the quantity
            carrito.update(product, Integer.toString(newQuantity));
        }

        
        //in case we have deleted the last item of the cart, we need to remove the cart from the session because there isn't any product in it.
        if (carrito.isEmpty() == true) {
            session.removeAttribute("carrito");
            
            //if the cart is empty we will forward the request to category page
            req.setAttribute("productsById", productModel.retrieveByCategory(product.getCategoryid()));
            req.setAttribute("categorySelected", categoryModel.retrieve(product.getCategoryid()));
            req.setAttribute("categories", categoryModel.retrieveAll());

            ViewManager.nextView(req, resp, "/view/category.jsp");
        } else {
            //otherwise we will show the cart updated
            req.setAttribute("carrito", session.getAttribute("carrito"));
            ViewManager.nextView(req, resp, "/view/viewcart.jsp");
        }
    }
}
