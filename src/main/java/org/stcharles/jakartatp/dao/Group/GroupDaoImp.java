package org.stcharles.jakartatp.dao.Group;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.stcharles.jakartatp.model.Group;
import org.stcharles.jakartatp.qualifier.Prod;

import java.util.List;


/**
 * The class Group dao imp implements group dao
 */
@Prod
@ApplicationScoped
public class GroupDaoImp implements GroupDao {
    @PersistenceContext
    private EntityManager em;


    /**
     * Persist
     *
     * @param group the group
     * @Override
     */
    public void persist(Group group) {
        em.persist(group);
    }


    /**
     * Gets the all
     *
     * @return the all
     * @Override
     */
    public List<Group> getAll() {
        return em.createQuery("SELECT g FROM Group g", Group.class).getResultList();
    }


    /**
     * Gets the
     *
     * @param id the id
     * @return the
     * @Override
     */
    public Group get(Integer id) {
        return em.find(Group.class, id);
    }


    /**
     * Remove
     *
     * @param group the group
     * @Override
     */
    public void remove(Group group) {
        em.remove(group);
    }


    /**
     * Refresh
     *
     * @Override
     */
    public void refresh() {
        em.getEntityManagerFactory().getCache().evict(Group.class);
    }
}
