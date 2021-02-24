package org.stcharles.jakartatp.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "loans")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "date_start")
    private Date DateStart;

    @Column(name = "date_end")
    private Date DateEnd;

    @Column(name = "status")
    private LoanStatus status;

    @ManyToOne
    @JoinColumn( name="user_id" )
    private User user;

    @OneToOne
    private Item item;

}
