package org.stcharles.jakartatp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    @NotNull
    private ItemState state;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    @NotNull
    private ItemType type;

    public void setType(ItemType type) {
        this.type = type;
    }

    @Column(name = "created_at")
    @NotNull
    private LocalDate cratedAt;

    @ManyToOne
    @JoinColumn(name = "album_id")
    @NotNull
    private Album album;

    @OneToOne(targetEntity = Loan.class)
    @JoinColumn(name = "active_loan_id", unique = true)
    private Loan loan;

    public Item(ItemState state, ItemType type, Album album) {
        this.state = state;
        this.type = type;
        this.album = album;
        this.cratedAt = LocalDate.now();
    }

    protected Item() {
    }

    public Integer getId() {
        return id;
    }

    public LocalDate getCratedAt() {
        return cratedAt;
    }

    public ItemState getState() {
        return state;
    }

    public void setState(ItemState state) {
        this.state = state;
    }

    public ItemType getType() {
        return type;
    }

    public Album getAlbum() {
        return album;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }
}
