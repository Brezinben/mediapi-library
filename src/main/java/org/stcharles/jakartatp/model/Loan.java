package org.stcharles.jakartatp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

/**
 * The class Loan
 */
@Entity
@Table(name = "loans")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "date_start")
    @NotNull
    private LocalDate dateStart;

    @Column(name = "date_end")
    @NotNull
    private LocalDate dateEnd;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @NotNull
    private LoanState status;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    private User user;

    @OneToOne(targetEntity = Item.class)
    @JoinColumn(name = "item_id", nullable = false)
    @NotNull
    private Item item;


    /**
     * It is a constructor.
     *
     * @param user the user
     * @param item the item
     */
    public Loan(User user, Item item) {

        this.dateStart = LocalDate.now();
        //La date de fin doit être inférieur a trois semaine
        this.dateEnd = dateStart.plusWeeks(3);
        this.user = user;
        this.item = item;
        this.status = LoanState.EN_COURS;
    }

    /**
     * Constructor used by CDI
     */
    protected Loan() {
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
     * Gets the date start
     *
     * @return the date start
     */
    public LocalDate getDateStart() {
        return dateStart;
    }


    /**
     * Gets the date end
     *
     * @return the date end
     */
    public LocalDate getDateEnd() {
        return dateEnd;
    }


    /**
     * Sets the date end
     *
     * @param dateEnd the date end
     */
    public void setDateEnd(LocalDate dateEnd) {
        this.dateEnd = dateEnd;
    }


    /**
     * Gets the status
     *
     * @return the status
     */
    public LoanState getStatus() {
        return status;
    }


    /**
     * Sets the status
     *
     * @param status the date end
     */
    public void setStatus(LoanState status) {
        this.status = status;
    }


    /**
     * Gets the user
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }


    /**
     * Gets the item
     *
     * @return the item
     */
    public Item getItem() {
        return item;
    }
}
