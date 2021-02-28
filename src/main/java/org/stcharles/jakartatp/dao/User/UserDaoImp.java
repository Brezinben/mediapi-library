package org.stcharles.jakartatp.dao.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.stcharles.jakartatp.model.User;
import org.stcharles.jakartatp.qualifier.Prod;

import java.util.List;

@Prod
public class UserDaoImp implements UserDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void persist(User user) {
        em.persist(user);
    }

    @Override
    public List<User> getAll() {
        return em.createQuery("select u from User u ", User.class)
                .getResultList();
    }

    @Override
    public User get(Integer id) {
        return em.find(User.class, id);
    }

    @Override
    public Integer count() {
        return getAll().size();
    }

    @Override
    public User getByEmail(String email) {
        try {
            return em.createQuery("select u from User u where u.email = :email ", User.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (Exception e) {
            //Alors on n'a pas de résultat
            return null;
        }
    }
}
