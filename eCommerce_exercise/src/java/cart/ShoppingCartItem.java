/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cart;

import entity.Product;

/**
 *
 * @author Agusti
 */
public class ShoppingCartItem {

    Product productItem;
    int quantity;

    public ShoppingCartItem(Product product) {
        productItem = product;
    }

    public Product getProduct(){
        return productItem;
    }

    public int getQuantity(){
        return quantity;
    }

    public void setQuantity(int quantity){
        this.quantity=quantity;
    }

    public double getTotal(){
        double price = productItem.getPrice().doubleValue();
        return price*(double)quantity;
    }
}
