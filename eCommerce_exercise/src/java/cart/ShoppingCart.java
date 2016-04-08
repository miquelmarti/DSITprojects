/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cart;

import entity.Product;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Agusti
 */
public class ShoppingCart {
    
    private List<ShoppingCartItem> carrito;
    
    public ShoppingCart(int numProducts){
        carrito = new ArrayList<ShoppingCartItem>(numProducts);
        
        //we add null object in all the positions in order to distinguish
        //filled items with empty items. We will use productID as the index
        //to add the product in the list.
        for(int i=0;i<numProducts;i++)
            carrito.add(i,null);
    }

    public synchronized void addItem(Product product){
        //we check if the product that we want to add is in the list
        //in case it isn't, we add it. Otherwise, we increase the quantity
        
        ShoppingCartItem item = carrito.get(product.getId());
        int new_quantity;
        
        if(item==null){
            carrito.set(product.getId(), new ShoppingCartItem(product));
            new_quantity = 1;
        }else{
            new_quantity = item.getQuantity()+1;
        }
        //even if it's added or not, we call update method to update the quantity of the product
        this.update(product,Integer.toString(new_quantity));
    }
    
    public synchronized void deleteItem(Product product){
        //we set null in the position of the productID 
        carrito.set(product.getId(), null);
    }

    public synchronized boolean isEmpty(){
        //to check if the cart is empty
        boolean isEmpty = true;
        for(ShoppingCartItem item : getItems()){
            if(item!=null)
                isEmpty=false;
        }
        return isEmpty;
    }
    
    public synchronized void update(Product product, String quantity){
        //update the number of items given a product and a string with the number of items
        ShoppingCartItem item = carrito.get(product.getId());
        item.setQuantity(Integer.parseInt(quantity));
    }

    public synchronized List<ShoppingCartItem> getItems(){
        //get all the items in the list that are not null
        List<ShoppingCartItem> cart = new ArrayList<ShoppingCartItem>();
        
        for(ShoppingCartItem cartItem : carrito){
            if(cartItem!=null)
                cart.add(cartItem);
        }
        
        return cart;
    }

    public synchronized int getNumberOfItems(){
        //get the number of total items in the cart
        int totalItems=0;
        
        for(ShoppingCartItem item : getItems()){
            //we sum the quantity of products for each item
                totalItems += item.getQuantity();
        }
       
        return totalItems;
    }

    public synchronized double getTotal(){
        //get the total price of all the items in the cart
        double totalPrice=0;
        
        for(ShoppingCartItem item : getItems()){
                totalPrice += item.getTotal();
        }
        
        return totalPrice;
    }

    public synchronized void clear(){
        carrito.clear();
    }
    
}
