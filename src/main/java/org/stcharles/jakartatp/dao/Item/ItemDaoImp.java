package org.stcharles.jakartatp.dao.Item;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.stcharles.jakartatp.model.Album;
import org.stcharles.jakartatp.model.Item;
import org.stcharles.jakartatp.qualifier.Prod;

import java.util.List;

@Prod
public class ItemDaoImp implements ItemDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void persist(Item shape) {

    }

    @Override
    public Item get(Integer id) {
        return null;
    }

    @Override
    public List<Item> getAll() {
        return null;
    }

    @Override
    public List<Item> getAllFromAlbum(Album album) {
        return em.createQuery("select i from Item i where i.album = :album", Item.class)
                .setParameter("album", album)
                .getResultList();
    }

    @Override
    public Item getOneFromAlbum(Album album, Integer itemId) {
        return null;
    }


}
