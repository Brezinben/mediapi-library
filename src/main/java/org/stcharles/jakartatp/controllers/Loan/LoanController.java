package org.stcharles.jakartatp.controllers.Loan;

import org.stcharles.jakartatp.api.Loan.LoanInput;
import org.stcharles.jakartatp.api.Loan.LoanOutput;
import org.stcharles.jakartatp.model.LoanState;

import java.util.List;

public interface LoanController {
    List<LoanOutput> getAll(Integer userId);

    LoanOutput get(Integer userId, Integer loanId);

    LoanOutput changeStatus(Integer userId, Integer loanId, LoanState status);

    LoanOutput create(Integer userId, LoanInput loanInput);

    List<LoanOutput> createMultiple(Integer userId, List<LoanInput> loanInputs);

    List<LoanOutput> getByStatus(Integer userId, LoanState status);

}
