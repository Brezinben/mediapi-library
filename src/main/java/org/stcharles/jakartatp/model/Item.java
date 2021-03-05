package org.stcharles.jakartatp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

/**
 * The class Item
 */
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


    /**
     * It is a constructor.
     *
     * @param state the state
     * @param type  the type
     * @param album the album
     */
    public Item(ItemState state, ItemType type, Album album) {

        this.state = state;
        this.type = type;
        this.album = album;
        this.cratedAt = LocalDate.now();
    }

    /**
     * Constructor used by CDI
     */
    protected Item() {
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
     * Gets the crated at
     *
     * @return the crated at
     */
    public LocalDate getCratedAt() {
        return cratedAt;
    }


    /**
     * It is a constructor.
     */
    public ItemState getState() {
        return state;
    }


    /**
     * Sets the state
     *
     * @param state the state
     */
    public void setState(ItemState state) {
        this.state = state;
    }


    /**
     * It is a constructor.
     */
    public ItemType getType() {
        return type;
    }


    /**
     * Sets the type
     *
     * @param type the type
     */
    public void setType(ItemType type) {
        this.type = type;
    }


    /**
     * Gets the album
     *
     * @return the album
     */
    public Album getAlbum() {
        return album;
    }


    /**
     * Gets the loan
     *
     * @return the loan
     */
    public Loan getLoan() {
        return loan;
    }


    /**
     * Sets the loan
     *
     * @param loan the loan
     */
    public void setLoan(Loan loan) {
        this.loan = loan;
    }
}
