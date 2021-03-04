package org.stcharles.jakartatp.dao.Album;

import jakarta.persistence.Cache;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.stcharles.jakartatp.model.Album;
import org.stcharles.jakartatp.qualifier.Prod;

import java.util.List;

@Prod
public class AlbumDaoImp implements AlbumDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void persist(Album album) {
        em.persist(album);
    }

    @Override
    public Album get(Integer id) {
        return em.find(Album.class, id);
    }

    @Override
    public void remove(Album album) {
        Cache cache = em.getEntityManagerFactory().getCache();
        if (cache.contains(Album.class, album.getId())) {
            cache.evict(Album.class, album.getId());
        }
        em.remove(album);
    }

    @Override
    public List<Album> getAll() {
        return em.createQuery("select a from Album a", Album.class).getResultList();
    }
}
