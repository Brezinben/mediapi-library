package org.stcharles.jakartatp.dao.Album;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.stcharles.jakartatp.model.Album;
import org.stcharles.jakartatp.model.Group;
import org.stcharles.jakartatp.qualifier.Prod;

@Prod
public class AlbumDaoImp implements AlbumDao {
    @PersistenceContext
    private EntityManager em;


    @Override
    public void persist(Album album) {
        em.persist(album);
    }

    @Override
    public Group get(Integer id) {
        return null;
    }

    @Override
    public void remove(Album album) {
        em.remove(album);
    }
}
