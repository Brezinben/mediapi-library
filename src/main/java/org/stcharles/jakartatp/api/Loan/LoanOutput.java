package org.stcharles.jakartatp.api.Loan;

import com.sun.xml.internal.ws.developer.Serialization;
import org.stcharles.jakartatp.model.Loan;
import org.stcharles.jakartatp.model.LoanState;

import java.time.LocalDate;

@Serialization(encoding = "json")
public class LoanOutput {
    public LocalDate dateStart;
    public LocalDate dateEnd;
    public Integer itemId;
    public LoanState status;

    /**
     * @param loan
     */
    public LoanOutput(Loan loan) {
        this.dateStart = loan.getDateStart();
        this.dateEnd = loan.getDateEnd();
        this.itemId = loan.getItem().getId();
        this.status = loan.getStatus();
    }
}
