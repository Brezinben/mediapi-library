package org.stcharles.jakartatp.dao.Album;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.stcharles.jakartatp.model.Album;
import org.stcharles.jakartatp.model.Group;
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
    public Group get(Integer id) {
        return null;
    }

    @Override
    public List<Album> getAlbumsFromGroup(Group group) {
        return em.createQuery("select a from Album a where a.group = :group order by a.release", Album.class)
                .setParameter("group", group)
                .getResultList();
    }

    @Override
    public Album getAlbumFromGroup(Group group, int id) {
        return this.getAlbumsFromGroup(group).get(id);
    }
}
