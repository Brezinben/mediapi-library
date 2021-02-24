package org.stcharles.jakartatp.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany
    private List<Album> albums;

    @Column(name = "name")
    private String name;

    @Column(name = "created_at")
    private Date created_at;

}
