package org.stcharles.jakartatp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

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

    public Loan(User user, Item item) {
        this.dateStart = LocalDate.now();
        //La date de fin doit être inférieur a trois semaine
        this.dateEnd = dateStart.plusWeeks(3);
        this.user = user;
        this.item = item;
        this.status = LoanState.EN_COURS;
    }

    protected Loan() {
    }

    public Integer getId() {
        return id;
    }

    public LocalDate getDateStart() {
        return dateStart;
    }

    public LocalDate getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(LocalDate dateEnd) {
        this.dateEnd = dateEnd;
    }

    public LoanState getStatus() {
        return status;
    }

    public Loan setStatus(LoanState status) {
        this.status = status;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Item getItem() {
        return item;
    }
}
