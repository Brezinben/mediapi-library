package org.stcharles.jakartatp.api.User;

import com.sun.xml.internal.ws.developer.Serialization;
import org.stcharles.jakartatp.model.Loan;
import org.stcharles.jakartatp.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Serialization(encoding = "json")
public class UserOutput {
    public String firstName;
    public String lastName;
    public String email;
    public String fullName;
    public Integer id;
    public List<Integer> loans;

    /**
     * @param u
     */
    public UserOutput(User u) {
        this.email = u.getEmail();
        this.lastName = u.getLastName();
        this.firstName = u.getFirstName();
        this.fullName = firstName + ' ' + lastName;
        this.id = u.getId();
        this.loans = u.getLoans().stream().map(Loan::getId).collect(Collectors.toList());
    }
}
