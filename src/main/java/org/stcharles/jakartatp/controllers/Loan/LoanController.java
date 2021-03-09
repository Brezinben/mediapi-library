package org.stcharles.jakartatp.controllers.Loan;

import org.stcharles.jakartatp.model.Loan;
import org.stcharles.jakartatp.model.LoanState;

import java.util.List;

public interface LoanController {
    List<Loan> getAll(Integer userId);

    Loan get(Integer userId, Integer loanId);

    Loan changeStatus(Integer userId, Integer loanId, LoanState status);

    Loan create(Integer userId, Integer itemId);

    List<Loan> createMultiple(Integer userId, List<Integer> loans);

    List<Loan> getByStatus(Integer userId, LoanState status);

    Loan update(Integer userId, Integer loanId, LoanState state);

    Boolean remove(Integer userId, Integer loanId);
}
