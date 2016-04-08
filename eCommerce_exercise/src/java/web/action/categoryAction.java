package web.action;

import cart.ShoppingCart;
import entity.Category;
import javax.servlet.http.*;
import model.CategoryModel;
import model.ProductModel;
import web.ViewManager;

public class categoryAction extends Action {

    CategoryModel categoryModel;
    ProductModel productModel;

    public categoryAction(CategoryModel categoryModel, ProductModel productModel){
        this.categoryModel = categoryModel;
        this.productModel = productModel;
    }

    public void perform(HttpServletRequest req, HttpServletResponse resp) {
        //recuperar informacion de la pregunta
        //proposito de esa accion 
        int categoryId = Integer.parseInt(req.getParameter("categoryid"));
        
        
        HttpSession session = req.getSession(true);
        
        //When access to category page we need to create the cart object in case it is not created
        if(session.getAttribute("carrito")!=null){
                    req.setAttribute("carrito", session.getAttribute("carrito"));
        }
        
        //necessary attributes for category.jsp view
        req.setAttribute("categorySelected", categoryModel.retrieve(categoryId));
        req.setAttribute("productsById", productModel.retrieveByCategory(categoryId));
        req.setAttribute("categories", categoryModel.retrieveAll());
        
        //forward hacia un jsp. hay que pasar datos al jsp
        ViewManager.nextView(req, resp, "/view/category.jsp");
    }
}
