package org.stcharles.jakartatp.dao.Loan;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.stcharles.jakartatp.model.Loan;
import org.stcharles.jakartatp.model.User;
import org.stcharles.jakartatp.qualifier.Prod;

import java.util.List;


/**
 * The class Loan dao imp implements loan dao
 */
@Prod
@ApplicationScoped
public class LoanDaoImp implements LoanDao {
    @PersistenceContext
    private EntityManager em;


    /**
     * Persist
     *
     * @param loan the loan
     * @Override
     */
    public void persist(Loan loan) {
        em.persist(loan);
    }


    /**
     * Gets the
     *
     * @param id the id
     * @return the
     * @Override
     */
    public Loan get(Integer id) {
        return em.find(Loan.class, id);
    }


    /**
     * Gets the all
     *
     * @return the all
     * @Override
     */
    public List<Loan> getAll() {
        return em.createQuery("select l from Loan l  order by l.dateStart", Loan.class)
                .getResultList();
    }


    /**
     * Gets the loans
     *
     * @param user the user
     * @return the loans
     * @Override
     */
    public List<Loan> getLoans(User user) {
        return em.createQuery("select l from Loan l where  l.user = :user order by l.dateStart", Loan.class)
                .setParameter("user", user)
                .getResultList();
    }


    /**
     * Gets the one loan from user
     *
     * @param user   the user
     * @param loanId the loan identifier
     * @return the one loan from user
     * @Override
     */
    public Loan getOneLoanFromUser(User user, Integer loanId) {
        return getLoans(user).get(loanId);
    }


    /**
     * Remove
     *
     * @param loan the loan
     * @Override
     */
    public void remove(Loan loan) {
        em.remove(loan);
    }
}
