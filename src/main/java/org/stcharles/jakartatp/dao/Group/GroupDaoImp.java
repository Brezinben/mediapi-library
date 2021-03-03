package org.stcharles.jakartatp.dao.Group;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.stcharles.jakartatp.model.Group;
import org.stcharles.jakartatp.qualifier.Prod;

import java.util.List;

@Prod
public class GroupDaoImp implements GroupDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void persist(Group group) {
        em.persist(group);
    }

    @Override
    public List<Group> getAll() {
        return em.createQuery("SELECT g FROM Group g", Group.class).getResultList();
    }

    @Override
    public Group get(Integer id) {
        return em.find(Group.class, id);
    }

    @Override
    public void remove(Group group) {
        em.remove(group);
    }
}
