package org.stcharles.jakartatp.dao.Item;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.stcharles.jakartatp.model.Item;
import org.stcharles.jakartatp.qualifier.Prod;

import java.util.List;


/**
 * The class Item dao imp implements item dao
 */
@Prod
@ApplicationScoped
public class ItemDaoImp implements ItemDao {
    @PersistenceContext
    private EntityManager em;


    /**
     * Persist
     *
     * @param item the item
     * @Override
     */
    public void persist(Item item) {
        em.persist(item);
    }


    /**
     * Gets the
     *
     * @param id the id
     * @return the
     * @Override
     */
    public Item get(Integer id) {
        return em.find(Item.class, id);
    }


    /**
     * Gets the all
     *
     * @return the all
     * @Override
     */
    public List<Item> getAll() {
        return null;
    }


    /**
     * Remove
     *
     * @param item the item
     * @Override
     */
    public void remove(Item item) {
        em.remove(item);
    }

}
