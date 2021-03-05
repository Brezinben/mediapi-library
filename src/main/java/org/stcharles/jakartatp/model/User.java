package org.stcharles.jakartatp.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * The class User
 */
@Entity
@Table(name = "users")
public class User {
    @OneToMany(targetEntity = Loan.class)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @OrderBy(value = "dateStart")
    private final List<Loan> loans = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;


    /**
     * It is a constructor.
     *
     * @param firstName the first name
     * @param lastName  the last name
     * @param email     the email
     */
    public User(String firstName, String lastName, String email) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    /**
     * Constructor used by CDI
     */
    protected User() {
    }


    /**
     * Gets the identifier
     *
     * @return the identifier
     */
    public Integer getId() {
        return id;
    }


    /**
     * Gets the first name
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }


    /**
     * Sets the first name
     *
     * @param firstName the first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    /**
     * Gets the last name
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }


    /**
     * Sets the last name
     *
     * @param lastName the last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    /**
     * Gets the email
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }


    /**
     * Sets the email
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }


    /**
     * Gets the loans
     *
     * @return the loans
     */
    public List<Loan> getLoans() {
        return loans;
    }


}
