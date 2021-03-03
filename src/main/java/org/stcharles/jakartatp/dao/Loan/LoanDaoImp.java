package org.stcharles.jakartatp.dao.Loan;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.stcharles.jakartatp.model.Loan;
import org.stcharles.jakartatp.model.User;
import org.stcharles.jakartatp.qualifier.Prod;

import java.util.List;


@Prod
public class LoanDaoImp implements LoanDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void persist(Loan loan) {
        em.persist(loan);
    }

    @Override
    public Loan get(Integer id) {
        return em.find(Loan.class, id);
    }

    @Override
    public List<Loan> getAll() {
        return em.createQuery("select l from Loan l  order by l.dateStart", Loan.class)
                .getResultList();
    }

    @Override
    public List<Loan> getLoans(User user) {
        return em.createQuery("select l from Loan l where  l.user = :user order by l.dateStart", Loan.class)
                .setParameter("user", user)
                .getResultList();
    }

    @Override
    public Loan getOneLoanFromUser(User user, Integer loanId) {
        return getLoans(user).get(loanId);
    }

    @Override
    public void remove(Loan loan) {
        em.remove(loan);
    }
}
