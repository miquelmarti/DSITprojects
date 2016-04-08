/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import entity.Category;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.UserTransaction;

/**
 *
 * @author juanluis
 */
public class CategoryModel {

    UserTransaction utx;
    EntityManager em;

    public CategoryModel(EntityManager em, UserTransaction utx) {
        this.utx = utx;
        this.em = em;
    }
    
    public void create(Category category) throws Exception{
        utx.begin();
        em.persist(category);
        utx.commit();
    }
    public Category retrieve(int id){
        return em.find(Category.class, id);
    }
    public void update(Category category) throws Exception{
            utx.begin();
            em.merge(category);
            utx.commit();
    }
    public void delete(int id) throws Exception{
        utx.begin();
        Category entity = retrieve(id);
        entity = em.merge(entity);
        em.remove(entity);
        utx.commit();
    }

    public List<Category> retrieveAll(){
        Query q = em.createQuery("select o from Category as o");
        return q.getResultList();
    }

}
