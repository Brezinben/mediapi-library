package org.stcharles.jakartatp.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "loans")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "date_start")
    private LocalDate dateStart;

    @Column(name = "date_end")
    private LocalDate dateEnd;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private LoanState status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    private Item item;

    public Loan(LocalDate dateStart, LocalDate dateEnd, User user, Item item) {
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.user = user;
        this.item = item;
        this.status = LoanState.EN_COURS;
    }

    protected Loan() {
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

    public void setStatus(LoanState status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public Item getItem() {
        return item;
    }
}
