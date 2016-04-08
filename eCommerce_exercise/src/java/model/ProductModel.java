/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import entity.Category;
import entity.Product;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.UserTransaction;

/**
 *
 * @author juanluis
 */
public class ProductModel {

    UserTransaction utx;
    EntityManager em;

    public ProductModel(EntityManager em, UserTransaction utx) {
        this.utx = utx;
        this.em = em;
    }
    
    public void create(Product product) throws Exception{
        utx.begin();
        em.persist(product);
        utx.commit();
    }
    public Product retrieve(int id){
        return em.find(Product.class, id);
    }
    public void update(Product product) throws Exception{
            utx.begin();
            em.merge(product);
            utx.commit();
    }
    public void delete(int id) throws Exception{
        utx.begin();
        Product entity = retrieve(id);
        entity = em.merge(entity);
        em.remove(entity);
        utx.commit();
    }
    
    public int getNumProducts(){
        Query q = em.createQuery("select o from Product as o");
        return q.getResultList().size();
    }
    
    public List<Product> retrieveByCategory(int id){
        //get a list of products within the same category family
        Query q = em.createQuery("select o from Product as o where o.categoryid="+id);
        //q.setParameter("categoryid", id);
        
        List<Product> productsById = q.getResultList();
        return productsById;
    }
    
    public List<Product> retrieveAll(){
        Query q = em.createQuery("select o from Product as o");
        return q.getResultList();
    }

}
