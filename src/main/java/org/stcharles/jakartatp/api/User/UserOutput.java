package org.stcharles.jakartatp.api.User;

import org.stcharles.jakartatp.model.User;

public class UserOutput {
    public String firstName;
    public String lastName;
    public String email;
    public String fullName;
    public Integer id;

    public UserOutput(User u) {
        this.email = u.getEmail();
        this.lastName = u.getLastName();
        this.firstName = u.getFirstName();
        this.fullName = firstName + ' ' + lastName;
        this.id = u.getId();
    }
}
