package org.stcharles.jakartatp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private ItemState state;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private ItemType type;

    @ManyToOne
    @JoinColumn(name = "album_id")
    private Album album;

    @OneToOne
    private Loan loan;

    public Item(ItemState state, ItemType type, Album album) {
        this.state = state;
        this.type = type;
        this.album = album;
    }

    protected Item() {
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
