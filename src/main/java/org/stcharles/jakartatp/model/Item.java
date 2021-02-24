package org.stcharles.jakartatp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "state")
    private Status state;

    @Column(name = "type")
    private ItemType type;

    @ManyToOne
    @JoinColumn( name="album_id" )
    private Album album;

    @OneToOne
    private Loan loan;

}
