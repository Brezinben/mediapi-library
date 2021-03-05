package org.stcharles.jakartatp.dao.Album;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.stcharles.jakartatp.model.Album;
import org.stcharles.jakartatp.qualifier.Prod;

import java.util.List;


/**
 * The class Album dao imp implements album dao
 */
@Prod
@ApplicationScoped
public class AlbumDaoImp implements AlbumDao {
    @PersistenceContext
    private EntityManager em;


    /**
     * Persist
     *
     * @param album the album
     * @Override
     */
    public void persist(Album album) {
        em.persist(album);
    }


    /**
     * Gets the
     *
     * @param id the id
     * @return the
     * @Override
     */
    public Album get(Integer id) {
        return em.find(Album.class, id);
    }


    /**
     * Remove
     *
     * @param album the album
     * @Override
     */
    public void remove(Album album) {
        em.remove(album);
    }


    /**
     * Gets all album
     *
     * @return the all
     * @Override
     */
    public List<Album> getAll() {
        return em.createQuery("select a from Album a", Album.class).getResultList();
    }


    /**
     * Refresh le cache des albums
     *
     * @Override
     */
    public void refresh() {
        //le cache est bien gérer lorsque on remove un album et que le fait une recherche dessus en fuzzy finding alors il ne sera pas trouvé.
        em.getEntityManagerFactory().getCache().evict(Album.class);
    }
}
