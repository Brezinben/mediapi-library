package org.stcharles.jakartatp.dao.Loan;

import org.stcharles.jakartatp.model.Loan;
import org.stcharles.jakartatp.model.LoanState;
import org.stcharles.jakartatp.model.User;

import java.util.List;

public interface LoanDao {

    void persist(Loan shape);

    Loan get(Integer id);

    List<Loan> getAll();

    List<Loan> getLoans(User user);

    List<Loan> getAllByStatus(LoanState status);

    void remove(Loan loan);
}
