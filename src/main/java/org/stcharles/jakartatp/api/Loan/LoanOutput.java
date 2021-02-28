package org.stcharles.jakartatp.api.Loan;

import org.stcharles.jakartatp.model.Loan;
import org.stcharles.jakartatp.model.LoanState;

import java.time.LocalDate;

public class LoanOutput {
    public LocalDate dateStart;
    public LocalDate dateEnd;
    public Integer itemId;
    public LoanState status;

    public LoanOutput(Loan loan) {
        this.dateStart = loan.getDateStart();
        this.dateEnd = loan.getDateEnd();
        this.itemId = loan.getItem().getId();
        this.status = loan.getStatus();
    }
}
