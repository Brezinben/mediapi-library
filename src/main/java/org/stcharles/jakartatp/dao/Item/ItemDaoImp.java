package org.stcharles.jakartatp.dao.Item;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.stcharles.jakartatp.model.Item;
import org.stcharles.jakartatp.qualifier.Prod;

import java.util.List;

@Prod
public class ItemDaoImp implements ItemDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void persist(Item item) {
        em.persist(item);
    }

    @Override
    public Item get(Integer id) {
        return em.find(Item.class, id);
    }

    @Override
    public List<Item> getAll() {
        return null;
    }

}
