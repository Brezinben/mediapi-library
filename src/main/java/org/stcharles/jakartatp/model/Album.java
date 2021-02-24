package org.stcharles.jakartatp.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "albums")
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn( name="group_id" )
    private Group group;

    @Column(name = "title")
    private String title;

    @Column(name = "release")
    private Date release;

    @OneToMany
    private List<Item> items;
}
