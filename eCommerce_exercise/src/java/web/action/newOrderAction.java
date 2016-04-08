package web.action;

import cart.ShoppingCart;
import entity.Category;
import entity.Product;
import javax.servlet.http.*;
import model.CategoryModel;
import model.ProductModel;
import web.ViewManager;

public class newOrderAction extends Action {

    CategoryModel categoryModel;
    ProductModel productModel;

    public newOrderAction(CategoryModel categoryModel, ProductModel productModel){
        this.categoryModel = categoryModel;
        this.productModel = productModel;
    }

    public void perform(HttpServletRequest req, HttpServletResponse resp) {
        //recuperar informacion de la pregunta
        //proposito de esa accion 
        HttpSession session = req.getSession(true);
        
        //Primer comprovem si tenim el carrito, si no el tenim el creem. Després el recuperem
        
        if(session.getAttribute("carrito")==null){
            session.setAttribute("carrito", new ShoppingCart(productModel.getNumProducts()));
        }
        
        ShoppingCart carrito = (ShoppingCart) session.getAttribute("carrito");
        //Ja tenim el carrito, ara hem d'afegir allò que volem
        Product product = (Product) productModel.retrieve(Integer.parseInt(req.getParameter("productid")));
        carrito.addItem(product);
        //forward hacia un jsp. hay que pasar datos al jsp
        req.setAttribute("carrito", carrito);
        
        int categoryId = product.getCategoryid();
        
        
        req.setAttribute("categorySelected", categoryModel.retrieve(categoryId));
        req.setAttribute("productsById", productModel.retrieveByCategory(categoryId));
        req.setAttribute("categories", categoryModel.retrieveAll());

        ViewManager.nextView(req, resp, "/view/category.jsp");
    }
}
