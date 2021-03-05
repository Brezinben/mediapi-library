package org.stcharles.jakartatp.dao.User;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.stcharles.jakartatp.model.User;
import org.stcharles.jakartatp.qualifier.Prod;

import java.util.List;


/**
 * The class User dao imp implements user dao
 */
@Prod
@ApplicationScoped
public class UserDaoImp implements UserDao {
    @PersistenceContext
    private EntityManager em;


    /**
     * Persist
     *
     * @param user the user
     * @Override
     */
    public void persist(User user) {
        em.persist(user);
    }


    /**
     * Gets the all
     *
     * @return the all
     * @Override
     */
    public List<User> getAll() {
        return em.createQuery("select u from User u ", User.class)
                .getResultList();
    }


    /**
     * Gets the
     *
     * @param id the id
     * @return the
     * @Override
     */
    public User get(Integer id) {
        return em.find(User.class, id);
    }


    /**
     * Count
     *
     * @return Integer
     * @Override
     */
    public Integer count() {
        return getAll().size();
    }


    /**
     * Gets the by email
     *
     * @param email the email
     * @return the by email
     * @Override
     */
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


    /**
     * Remove
     *
     * @param user the user
     * @Override
     */
    public void remove(User user) {
        em.remove(user);
    }
}
